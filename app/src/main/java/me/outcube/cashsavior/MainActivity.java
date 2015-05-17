package me.outcube.cashsavior;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.pm.PackageManager;

import com.facebook.Session;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import util.BlurBackgroundDialog;


public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private String userId;
    private ImageButton entImgBtn, savImgBtn, invImgBtn, fixImgBtn, incImgBtn;
    private View entFill, savFill, invFill, fixFill, incFill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        findViewById();
        initialize();
        Intent previousIntent = getIntent();
        userId = previousIntent.getStringExtra("userId");
    }

    private void initialize(){
        View.OnClickListener onClickImgButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.activity_category_popup);
                //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                TextView typeName = (TextView) dialog.findViewById(R.id.type_name);
                TextView amount = (TextView) dialog.findViewById(R.id.amount);
                ImageButton moreInfo = (ImageButton) findViewById(R.id.more_info_btn);
                ImageButton addTransaction = (ImageButton) findViewById(R.id.add_btn);

                int typeNum = 0;

                switch (view.getId()){
                    case R.id.ent_btn:
                        typeName.setText(getResources().getString(R.string.type1Name));
                        amount.setText("1,234");
                        typeNum = 1;
                        break;
                    case R.id.sav_btn:
                        typeName.setText(getResources().getString(R.string.type2Name));
                        amount.setText("2,345");
                        typeNum = 2;
                        break;
                    case R.id.inv_btn:
                        typeName.setText(getResources().getString(R.string.type3Name));
                        amount.setText("3,456");
                        typeNum = 3;
                        break;
                    case R.id.fix_btn:
                        typeName.setText(getResources().getString(R.string.type4Name));
                        amount.setText("4,567");
                        typeNum = 4;
                        break;
                    case R.id.inc_btn:
                        typeName.setText(getResources().getString(R.string.type5Name));
                        amount.setText("5,678");
                        typeNum = 5;
                        break;

                }
                dialog.show();
            }
        };
        entImgBtn.setOnClickListener(onClickImgButtonListener);
        savImgBtn.setOnClickListener(onClickImgButtonListener);
        invImgBtn.setOnClickListener(onClickImgButtonListener);
        fixImgBtn.setOnClickListener(onClickImgButtonListener);
        incImgBtn.setOnClickListener(onClickImgButtonListener);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Toast.makeText(getApplicationContext(), "pressed setting", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void findViewById(){
        entImgBtn = (ImageButton) findViewById(R.id.ent_btn);
        entFill = findViewById(R.id.fill_ent);
        
        savImgBtn = (ImageButton) findViewById(R.id.sav_btn);
        savFill = findViewById(R.id.fill_sav);

        invImgBtn = (ImageButton) findViewById(R.id.inv_btn);
        invFill = findViewById(R.id.fill_inv);

        fixImgBtn = (ImageButton) findViewById(R.id.fix_btn);
        fixFill = findViewById(R.id.fill_fix);

        incImgBtn = (ImageButton) findViewById(R.id.inc_btn);
        incFill = findViewById(R.id.fill_inc);
    }
}
