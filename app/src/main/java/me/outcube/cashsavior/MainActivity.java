package me.outcube.cashsavior;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import history.HistoryDatabase;
import history.HistoryLog;


public class MainActivity extends ActionBarActivity {
    final private int TRANSACTION_REQUEST = 0;
    private ProgressDialog prgDialog;
    private Toolbar toolbar;
    public static String userId;
    public static HistoryDatabase historyDatabase;
    private ImageButton entImgBtn, savImgBtn, invImgBtn, fixImgBtn, incImgBtn;
    private View entFill, savFill, invFill, fixFill, incFill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_nav_drawer);
        drawerFragment.setup((DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        findViewById();
        initialize();
        Intent previousIntent = getIntent();
        userId = previousIntent.getStringExtra("userId");
    }

    private void initialize(){
        historyDatabase = new HistoryDatabase(this);
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);
        View.OnClickListener onClickImgButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.activity_category_popup);
                //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                TextView typeName = (TextView) dialog.findViewById(R.id.type_name);
                TextView amount = (TextView) dialog.findViewById(R.id.amount);
                ImageButton moreInfo = (ImageButton) dialog.findViewById(R.id.more_info_btn);
                ImageButton addTransaction = (ImageButton) dialog.findViewById(R.id.add_btn);

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
                final int finalTypeNum = typeNum;

                addTransaction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent transactionIntent = new Intent(MainActivity.this, TransactionActivity.class);
                        transactionIntent.putExtra("typeNum", finalTypeNum);
                        startActivityForResult(transactionIntent, TRANSACTION_REQUEST);
                    }
                });
                dialog.show();
            }
        };
        entImgBtn.setOnClickListener(onClickImgButtonListener);
        savImgBtn.setOnClickListener(onClickImgButtonListener);
        invImgBtn.setOnClickListener(onClickImgButtonListener);
        fixImgBtn.setOnClickListener(onClickImgButtonListener);
        incImgBtn.setOnClickListener(onClickImgButtonListener);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TRANSACTION_REQUEST) {
            if (resultCode == RESULT_OK) {
                int typeNum = data.getIntExtra("typeNum", -1);
                int subTypeNum = data.getIntExtra("subTypeNum", -1);
                int amount = data.getIntExtra("amount", -1);
                String note = data.getStringExtra("note");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String datemsg = dateFormat.format(date).toString();
                if (typeNum == 4 || typeNum == 5) subTypeNum = 1;
                historyDatabase.addHistory(new HistoryLog(typeNum,subTypeNum,amount,datemsg,note));
                syncTransection();
            }
        }
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
        if (id == R.id.main2) {

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

    private void syncTransection() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        List<HistoryLog> historyList = historyDatabase.getAllHistory();
        ArrayList<HistoryLog> userList = new ArrayList<HistoryLog>();
        for (int i = 0; i < historyList.size(); i++) {
            userList.add(historyList.get(i));
        }
        if(userList.size()!=0){
            if(historyDatabase.dbSyncCount() != 0){
                params.put("usersJSON", historyDatabase.composeJSONfromSQLite());
                client.post("http://outcube.me/cashsavior/cash_addTransection.php",params ,new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            JSONArray arr = new JSONArray(response);
                            for(int i=0; i<arr.length();i++){
                                JSONObject obj = (JSONObject)arr.get(i);
                                HistoryLog hislog = new HistoryLog();
                                hislog.setTypeid(Integer.parseInt(obj.get("typeid").toString()));
                                hislog.setSubid(Integer.parseInt(obj.get("subid").toString()));
                                hislog.setAmount(Integer.parseInt(obj.get("amount").toString()));
                                hislog.setDate(obj.get("date").toString());
                                hislog.setNote(obj.get("note").toString());
                                historyDatabase.updateSyncStatus(hislog,obj.get("status").toString());
                            }
                            Toast.makeText(getApplicationContext(), "DB Sync completed!", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Throwable error,String content) {
                        if(statusCode == 404){
                            Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                        }else if(statusCode == 500){
                            Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onStart() {
                        prgDialog.show();
                    }

                    @Override
                    public void onFinish() {
                        prgDialog.dismiss();
                    }
                });
            }else{
                Toast.makeText(getApplicationContext(), "SQLite and Remote MySQL DBs are in Sync!", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "No data in SQLite DB, please do enter data to perform Sync action", Toast.LENGTH_LONG).show();
        }
    }

}
