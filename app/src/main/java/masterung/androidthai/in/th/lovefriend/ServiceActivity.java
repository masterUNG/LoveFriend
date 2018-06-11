package masterung.androidthai.in.th.lovefriend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import masterung.androidthai.in.th.lovefriend.fragment.ListFriendFragment;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

//        Get Value Login
        String[] loginStrings = getIntent().getStringArrayExtra("Login");

//        Add Fragment To Activity
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentServiceFragment, new ListFriendFragment())
                    .commit();
        }


    }
}
