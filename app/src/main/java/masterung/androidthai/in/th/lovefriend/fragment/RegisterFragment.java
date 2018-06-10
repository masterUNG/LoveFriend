package masterung.androidthai.in.th.lovefriend.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import masterung.androidthai.in.th.lovefriend.MainActivity;
import masterung.androidthai.in.th.lovefriend.R;
import masterung.androidthai.in.th.lovefriend.utility.MasterAlert;
import masterung.androidthai.in.th.lovefriend.utility.MyConstant;
import masterung.androidthai.in.th.lovefriend.utility.PostUserToServer;

public class RegisterFragment extends Fragment {

    private Uri uri;
    private ImageView imageView;
    private boolean photoABoolean = true;
    private ProgressDialog progressDialog;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create Toolbar
        createToolbar();

//        Avata Contontroller
        avataContontroller();


    }   // Main Method

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemUpload) {
            uploadToServer();
        }

        return super.onOptionsItemSelected(item);
    }

    private void uploadToServer() {

        MasterAlert masterAlert = new MasterAlert(getActivity());

        EditText nameEditText = getView().findViewById(R.id.edtName);
        EditText userEditText = getView().findViewById(R.id.edtUser);
        EditText passwordEditText = getView().findViewById(R.id.edtPassword);

        String nameString = nameEditText.getText().toString().trim();
        String userString = userEditText.getText().toString().trim();
        String passwordString = passwordEditText.getText().toString().trim();


//        Check Choose Photo
        if (photoABoolean) {
            masterAlert.normalDialog("Choose Avata",
                    "Please Choose Avata");
        } else if (nameString.isEmpty() ||
                userString.isEmpty() ||
                passwordString.isEmpty()) {
            masterAlert.normalDialog("Have Space",
                    "Please Fill Every Blank");
        } else {

//            Upload Photo To Server
            uploadPhoth();

//            upload Value to MySQL
            uploadValue(nameString, userString, passwordString);


        }

    }   // uploadToServer

    private void uploadValue(String nameString,
                             String userString,
                             String passwordString) {

        String avataString =  "http://androidthai.in.th/love/Avata/" +
                findPathString().substring(findPathString().lastIndexOf("/"));
        Log.d("10JuneV1", "avataString ==> " + avataString);

        MyConstant myConstant = new MyConstant();
        MasterAlert masterAlert = new MasterAlert(getActivity());

        try {

            PostUserToServer postUserToServer = new PostUserToServer(getActivity());
            postUserToServer.execute(
                    nameString,
                    userString,
                    passwordString,
                    avataString,
                    myConstant.getUrlAddData());

            if (Boolean.parseBoolean(postUserToServer.get())) {
                Toast.makeText(getActivity(), "Register Success",
                        Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack();
            } else {
                masterAlert.normalDialog("Cannot Register",
                        "Please Try Again");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void uploadPhoth() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Upload Photo");
        progressDialog.setMessage("Please Wait Few Minus ...");


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        FTPClient ftpClient = new FTPClient();
        MyConstant myConstant = new MyConstant();
        try {

            ftpClient.connect(myConstant.getHostFTPString(), myConstant.getPortAnInt());
            ftpClient.login(myConstant.getUserFTPString(), myConstant.getPasswordString());
            ftpClient.setType(FTPClient.TYPE_BINARY);
            ftpClient.changeDirectory("Avata");
            ftpClient.upload(findFild(), new MyFTPDataTransferListener());

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("10JuntV1", "e ==> " + e.toString());
            try {
                ftpClient.disconnect(true);
            } catch (Exception e1) {
                e1.printStackTrace();
                Log.d("10JuntV1", "e1 ==> " + e1.toString());
            }
        }


    }

    private File findFild() {

        String pathPhoto = findPathString();
        File file = new File(pathPhoto);

        return file;
    }

    private String findPathString() {

        String pathPhoto = null;

        String[] strings = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, strings,
                null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            int i = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            pathPhoto = cursor.getString(i);
        } else {
            pathPhoto = uri.getPath();
        }

        return pathPhoto;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_register, menu);

    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {

            photoABoolean = false;

            uri = data.getData();
            try {

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;

                Bitmap bitmap = BitmapFactory
                        .decodeStream(getActivity()
                                .getContentResolver()
                                .openInputStream(uri), null, options);

//                Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap,
//                        800, 600, true);
                imageView.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void avataContontroller() {
        imageView = getView().findViewById(R.id.imvAvata);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,
                        "Please Choos Image my App"), 1);
            }
        });
    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarRegister);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);

//        Setup Title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Register");
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("Please Fill Every Blank");

//        Setup Navigation Icon
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register,
                container, false);
        return view;
    }

    public class MyFTPDataTransferListener implements FTPDataTransferListener{



        @Override
        public void started() {
            progressDialog.show();
            Log.d("10JuneV1", "Upload Start");
        }

        @Override
        public void transferred(int i) {

        }

        @Override
        public void completed() {
            progressDialog.dismiss();
            Log.d("10JuneV1", "Upload Stop");
        }

        @Override
        public void aborted() {

        }

        @Override
        public void failed() {

        }
    }   // MyFTPDataTranferListener

}   // Main Class
