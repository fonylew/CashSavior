package me.outcube.cashsavior;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class LoginActivity extends ActionBarActivity {
    private ImageButton facebookLoginBtn;
    private Button mainActivityBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findAllById();
        initialize();

    }

    private void initialize(){
        mainActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainAcitiviyIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainAcitiviyIntent);
                finish();
            }
        });

        //TODO set listener for facebookLoginBtn
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
        facebookLoginBtn = (ImageButton) findViewById(R.id.facebook_btn);
        mainActivityBtn = (Button) findViewById(R.id.main_activity_btn);
    }
}
