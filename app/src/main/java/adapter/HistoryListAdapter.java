package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import history.HistoryLog;
import me.outcube.cashsavior.R;

public class HistoryListAdapter extends ArrayAdapter<HistoryLog> {
    private LayoutInflater inflater;
    private int itemResource;

    public static final int SUBTYPE_NAME_ID[][] = {
            {R.string.subtype1_1, R.string.subtype1_2, R.string.subtype1_3,
                    R.string.subtype1_4, R.string.subtype1_5, R.string.subtype1_6,
                    R.string.subtype1_7},
            {R.string.subtype2_1,R.string.subtype2_2,R.string.subtype2_3, },
            {R.string.subtype3_1, R.string.subtype3_2, R.string.subtype3_3,
                    R.string.subtype3_4, R.string.subtype3_5},
            {R.string.subtype_4_1},
            {R.string.subtype_5_1}};

    public static final int SUBTYPE_IMG_ID[][] = {
            {R.drawable.subtype_food,R.drawable.subtype_drink,R.drawable.subtype_movie,
                    R.drawable.subtype_brand,R.drawable.subtype_vacation,R.drawable.subtype_donate
                    ,R.drawable.subtype_present},
            {R.drawable.subtype_bank,R.drawable.subtype_lottery,R.drawable.subtype_bond},
            {R.drawable.subtype_money,R.drawable.subtype_expand,R.drawable.subtype_stock,
                    R.drawable.subtype_gold,R.drawable.subtype_learn},
            {R.drawable.subtype_fixcost},
            {R.drawable.subtype_income}};

    public HistoryListAdapter(Context context, int resource, HistoryLog[] objects) {
        super(context, resource, objects);
        itemResource = resource;
    }

    public HistoryListAdapter(Context context, int resource, List<HistoryLog> objects) {
        super(context, resource, objects);
        this.itemResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemHolder holder;

        if(row==null){
            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(itemResource, parent, false);
            holder = new ItemHolder(row);
            row.setTag(holder);
        } else {
            holder = (ItemHolder) row.getTag();
        }

        HistoryLog historyLog = getItem(position);

        Log.d("myLog", "in adapter [" + historyLog.getTypeid() + "][" + historyLog.getSubid() + "] " + historyLog.getNote() + " " + historyLog.getAmount());

        holder.amount.setText(historyLog.getAmount()+"");
        holder.icon.setImageDrawable(getContext().getResources().getDrawable(
                SUBTYPE_IMG_ID[historyLog.getTypeid()-1][historyLog.getSubid()-1]
        ));
        holder.subTypeName.setText(getContext().getResources().getString(
                SUBTYPE_NAME_ID[historyLog.getTypeid()-1][historyLog.getSubid()-1]
        ));

        return row;
    }

    private class ItemHolder{
        private ImageView icon;
        private TextView subTypeName, amount;

        public ItemHolder(View v){
            icon = (ImageView) v.findViewById(R.id.icon);
            subTypeName = (TextView) v.findViewById(R.id.subtype_name);
            amount = (TextView) v.findViewById(R.id.amount);
        }

    }
}
