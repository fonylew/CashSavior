package me.outcube.cashsavior;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class LoginActivity extends ActionBarActivity {
    private Button mainActivityBtn;
    private ProgressDialog prgDialog;

    //facebook
    private TextView facebookTv;
    private LoginButton facebookLoginBtn;
    private UiLifecycleHelper uiHelper;
    private String userId;
    private String userName;

    private Session.StatusCallback statusCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,Exception exception) {
            if (state.isOpened()) {
                Log.d("FacebookSampleActivity", "Facebook session opened");
            } else if (state.isClosed()) {
                Log.d("FacebookSampleActivity", "Facebook session closed");
            }
        }
    };

    //facebook

    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        uiHelper.onSaveInstanceState(savedState);
    }

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //facebook
        uiHelper = new UiLifecycleHelper(this, statusCallback);
        uiHelper.onCreate(savedInstanceState);
        //facebook
        findAllById();
        initialize();

    }

    private void initialize(){
        mainActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userId == null || userId.length() == 0) Toast.makeText(getApplication(),"Please login first.",Toast.LENGTH_LONG).show();
                else {
                    if (isNetworkAvailable(getApplicationContext())) connectUserToServer();
                    else Toast.makeText(getApplicationContext(),"No internet connection.\nPlease connect to the internet and retry.",Toast.LENGTH_LONG).show();
                }
            }
        });
        facebookLoginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                if (user != null) {
                    userId = ""+user.getId();
                    userName = ""+user.getName();
                    facebookTv.setText("Welcome, " + userName + ". Click to logout");
                } else {
                    facebookTv.setText("Click to login with Facebook");
                }
            }
        });
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findAllById(){
        facebookTv = (TextView) findViewById(R.id.facebook_tv);
        facebookLoginBtn = (LoginButton) findViewById(R.id.facebook_btn);
        mainActivityBtn = (Button) findViewById(R.id.main_activity_btn);
    }

    private void connectUserToServer() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        ArrayList<HashMap<String, String>> userList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        userList.add(map);
        Gson gson = new GsonBuilder().create();
        params.put("usersJSON", gson.toJson(userList));
        client.post("http://outcube.me/cashsavior/cash_adduser.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
            }

            @Override
            public void onStart() {
                prgDialog.show();
            }

            @Override
            public void onFinish() {
                Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                mainActivityIntent.putExtra("userId", userId);
                mainActivityIntent.putExtra("userName", userName);
                prgDialog.dismiss();
                startActivity(mainActivityIntent);
                finish();
            }
        });
    }

}
