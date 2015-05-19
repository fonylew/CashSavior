package me.outcube.cashsavior;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    public static final int SUBTYPE_NAME_ID[][] = {
            {R.string.subtype1_1, R.string.subtype1_2, R.string.subtype1_3,
                    R.string.subtype1_4, R.string.subtype1_5, R.string.subtype1_6,
                    R.string.subtype1_7},
            {R.string.subtype2_1,R.string.subtype2_2,R.string.subtype2_3, },
            {R.string.subtype3_1, R.string.subtype3_2, R.string.subtype3_3,
                    R.string.subtype3_4, R.string.subtype3_5}};

    public static final int SUBTYPE_IMG_ID[][] = {
            {R.drawable.subtype_food,R.drawable.subtype_food,R.drawable.subtype_food,
                    R.drawable.subtype_food,R.drawable.subtype_food,R.drawable.subtype_food
                    ,R.drawable.subtype_food},
            {R.drawable.subtype_food,R.drawable.subtype_food,R.drawable.subtype_food},
            {R.drawable.subtype_food,R.drawable.subtype_food,R.drawable.subtype_food,
                    R.drawable.subtype_food,R.drawable.subtype_food}};

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
        else if(typeNum<=3){
            SubTypeResPair[] thisSubTypeResource = new SubTypeResPair[SUBTYPE_NAME_ID[typeNum-1].length];
            for(int i=0; i<SUBTYPE_NAME_ID[typeNum-1].length; i++){
                Drawable d = getResources().getDrawable(SUBTYPE_IMG_ID[typeNum-1][i]);
                String s = getResources().getString(SUBTYPE_NAME_ID[typeNum-1][i]);
                thisSubTypeResource[i] = new SubTypeResPair(d, s);
            }

            selectedSubTypeNum = 0;
            final SubTypeGridAdapter adapter = new SubTypeGridAdapter(
                    getApplicationContext(),
                    R.layout.subtype_gridview_item,
                    thisSubTypeResource);
            subTypeGridView.setAdapter(adapter);
            subTypeGridView.setSelected(true);
            subTypeGridView.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
            subTypeGridView.setDrawSelectorOnTop(false);
            subTypeGridView.setSelector(R.drawable.curve_yellow);
            subTypeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    for (int j=0; j<adapterView.getCount(); j++) {
                        try {
                            if(j!=i) adapterView.getChildAt(j).setBackgroundResource(R.color.white);
                        } catch (NullPointerException e){
                            //TODO fix error
                            Log.d("myLog", "error at child number:"+j);
                        }
                    }
                    view.setBackgroundResource(R.drawable.curve_yellow);
                    selectedSubTypeNum = i;
                }
            });
        } else {
            selectedSubTypeNum = -2;
            subTypeGridView.setVisibility(View.GONE);
        }


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
