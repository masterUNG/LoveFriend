package masterung.androidthai.in.th.lovefriend.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import masterung.androidthai.in.th.lovefriend.R;
import masterung.androidthai.in.th.lovefriend.utility.GetAllData;
import masterung.androidthai.in.th.lovefriend.utility.ListFriendAdapter;
import masterung.androidthai.in.th.lovefriend.utility.MyConstant;

public class ListFriendFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create RecyclerView
        createRecyclerView();

    }

    private void createRecyclerView() {

        RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewListFriend);
        MyConstant myConstant = new MyConstant();
        String[] columnStrings = myConstant.getColumnUserStrings();

        List<String> titleNameStringList = new ArrayList<>();
        List<String> lastPostStringList = new ArrayList<>();
        List<String> pathIconStringList = new ArrayList<>();

        try {

            GetAllData getAllData = new GetAllData(getActivity());
            getAllData.execute(myConstant.getUrlReadAllData());

            String jsonString = getAllData.get();

            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i += 1) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                titleNameStringList.add(jsonObject.getString(columnStrings[1]));
                lastPostStringList.add(jsonObject.getString(columnStrings[5]));
                pathIconStringList.add(jsonObject.getString(columnStrings[4]));

            }

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);

            ListFriendAdapter listFriendAdapter = new ListFriendAdapter(getActivity(),
                    titleNameStringList, lastPostStringList, pathIconStringList);
            recyclerView.setAdapter(listFriendAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service,
                container, false);
        return view;
    }
}
