package me.outcube.cashsavior;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import adapter.SubTypeGridAdapter;
import adapter.SubTypeResPair;


public class TransactionActivity extends ActionBarActivity {
    public static final String SUBTYPE_NAME[][] = {
            {"อาหารมื้อพิเศษ", "ขนมและเครื่องดื่ม", "ชมภาพยนต์", "เครื่องแต่งกายแบรนด์เนม", "ท่องเที่ยว", "ทำบุญ", "ของขวัญ"},
            {"ฝากธนาคาร", "ซื้อสลากออมสิน", "ซื้อพันธบัตรรัฐบาล"},
            {"เงินลงทุน", "ขยายกิจการ", "ซื้อหุ้น", "ซื้อทองคำ", "เรียนรู้ทักษะ"}};

    public static final int SUBTYPE_imgId[][] = {
            {R.drawable.subtype_food,R.drawable.subtype_food,R.drawable.subtype_food,R.drawable.subtype_food,R.drawable.subtype_food,R.drawable.subtype_food,R.drawable.subtype_food},
            {R.drawable.subtype_food,R.drawable.subtype_food,R.drawable.subtype_food},
            {R.drawable.subtype_food,R.drawable.subtype_food,R.drawable.subtype_food,R.drawable.subtype_food,R.drawable.subtype_food}};

    private int typeNum;
    private Toolbar toolbar;
    private EditText amount, note;
    private GridView subTypeGridView;
    private int selectedSubTypeNum;


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
        if(typeNum==0) {finish();}

        SubTypeResPair[] thisSubTypeResource = new SubTypeResPair[SUBTYPE_NAME[typeNum-1].length];
        for(int i=0; i<SUBTYPE_NAME[typeNum-1].length; i++){
            Drawable d = getResources().getDrawable(SUBTYPE_imgId[typeNum-1][i]);
            thisSubTypeResource[i] = new SubTypeResPair(d, SUBTYPE_NAME[typeNum-1][i]);
        }

        SubTypeGridAdapter adapter = new SubTypeGridAdapter(
                getApplicationContext(),
                R.layout.subtype_gridview_item,
                thisSubTypeResource);
        subTypeGridView.setAdapter(adapter);
        subTypeGridView.setSelector(R.color.accentColor);
        subTypeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSubTypeNum = i;
            }
        });
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
            Intent returnIntent = new Intent();
            returnIntent.putExtra("typeNum", typeNum);
            returnIntent.putExtra("subTypeNum", selectedSubTypeNum+1);
            returnIntent.putExtra("amount", Integer.parseInt(amount.getText().toString()));
            returnIntent.putExtra("note", note.getText().toString());
            setResult(RESULT_OK, returnIntent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void findAllById(){
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        amount = (EditText) findViewById(R.id.amount);
        note = (EditText) findViewById(R.id.note);
        subTypeGridView = (GridView) findViewById(R.id.subtype_grid);
    }
}
