package me.outcube.cashsavior;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.HistoryListAdapter;
import history.HistoryLog;


public class HistoryActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private ListView historyListview;

    //TODO Ong
    private ArrayList<HistoryLog> allHistory;
    private ArrayList<HistoryLog> entHistory;
    private ArrayList<HistoryLog> savHistory;
    private ArrayList<HistoryLog> invHistory;
    private ArrayList<HistoryLog> fixHistory;
    private ArrayList<HistoryLog> incHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_nav_drawer);
        drawerFragment.setup((DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        findAllById();
        initialize();
    }

    private void initialize(){
        allHistory = MainActivity.historyDatabase.getAllHistory();
        entHistory = MainActivity.historyDatabase.getCategoryHistory(1);
        savHistory = MainActivity.historyDatabase.getCategoryHistory(2);
        invHistory = MainActivity.historyDatabase.getCategoryHistory(3);
        fixHistory = MainActivity.historyDatabase.getCategoryHistory(4);
        incHistory = MainActivity.historyDatabase.getCategoryHistory(5);

        ArrayList<HistoryLog> inverseAllHistory = new ArrayList<HistoryLog>();
        for (int i=allHistory.size()-1; i>=0; i--){
            inverseAllHistory.add(allHistory.get(i));
        }

        HistoryListAdapter adapter = new HistoryListAdapter(
                getApplicationContext(), R.layout.history_item, inverseAllHistory
        );
        historyListview.setAdapter(adapter);
        historyListview.setDrawSelectorOnTop(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
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
        historyListview = (ListView) findViewById(R.id.history_listview);
    }
}
