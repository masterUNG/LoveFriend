package masterung.androidthai.in.th.lovefriend.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import masterung.androidthai.in.th.lovefriend.R;
import masterung.androidthai.in.th.lovefriend.ServiceActivity;
import masterung.androidthai.in.th.lovefriend.utility.GetAllData;
import masterung.androidthai.in.th.lovefriend.utility.MasterAlert;
import masterung.androidthai.in.th.lovefriend.utility.MyConstant;

public class MainFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Register Controller
        registerController();

//        Login Controller
        loginController();

    }

    private void loginController() {
        Button button = getView().findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText userEditText = getView().findViewById(R.id.edtUser);
                EditText passwordEditText = getView().findViewById(R.id.edtPassword);

                String userString = userEditText.getText().toString().trim();
                String passwordString = passwordEditText.getText().toString().trim();
                MasterAlert masterAlert = new MasterAlert(getActivity());

                if (userString.isEmpty() || passwordString.isEmpty()) {
//                    Have Space
                    masterAlert.normalDialog("Have Space",
                            "Please Fill Every Blank");
                } else {
//                    No Space

                    MyConstant myConstant = new MyConstant();
                    boolean b = true;
                    String[] columnUserStrings = myConstant.getColumnUserStrings();
                    String[] userLoginStrings = new String[columnUserStrings.length];

                    try {

                        GetAllData getAllData = new GetAllData(getActivity());
                        getAllData.execute(myConstant.getUrlReadAllData());

                        String jsonString = getAllData.get();
                        Log.d("10JuneV1", "JSON ==> " + jsonString);

                        JSONArray jsonArray = new JSONArray(jsonString);
                        for (int i=0; i<jsonArray.length(); i+=1) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            if (userString.equals(jsonObject.getString(columnUserStrings[2]))) {
                                b = false;
                                for (int i1=0; i1<columnUserStrings.length; i1+=1) {
                                    userLoginStrings[i1] = jsonObject.getString(columnUserStrings[i1]);
                                }
                            }
                        }

                        if (b) {
//                            User False
                            masterAlert.normalDialog("User False",
                                    "No This User in Database");
                        } else if (passwordString.equals(userLoginStrings[3])) {
                            Toast.makeText(getActivity(), "Welcome " + userLoginStrings[1],
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getActivity(), ServiceActivity.class);
                            intent.putExtra("Login", userLoginStrings);
                            startActivity(intent);
                            getActivity().finish();

                        } else {
                            masterAlert.normalDialog("Password False",
                                    "Please Try Again Password False");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void registerController() {
        TextView textView = getView().findViewById(R.id.txtRegister);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,
                container, false);
        return view;
    }
}
