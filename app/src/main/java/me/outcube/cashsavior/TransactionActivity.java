package me.outcube.cashsavior;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class TransactionActivity extends ActionBarActivity {

    private int typeNum;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        findAllById();
        initialize();
    }

    private void initialize(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent oldIntent = getIntent();
        typeNum = oldIntent.getIntExtra("typeNum", 0);
        if(typeNum == 0){
            Toast.makeText(getApplicationContext(), "Error: typeNum = 0", Toast.LENGTH_SHORT).show();
            finish();
        }

        Toast.makeText(getApplicationContext(), "typeNum = "+typeNum, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transaction, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void findAllById(){
        toolbar = (Toolbar) findViewById(R.id.app_bar);
    }
}
