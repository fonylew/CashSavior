package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import me.outcube.cashsavior.R;

public class SubTypeGridAdapter extends ArrayAdapter<SubTypeResPair> {
    private LayoutInflater inflater;
    private int itemResource;

    public SubTypeGridAdapter(Context context, int resource, SubTypeResPair[] objects) {
        super(context, resource, objects);
        itemResource = resource;
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

        holder.subTypePic.setImageDrawable(getItem(position).iconDrawable);
        holder.name.setText(getItem(position).name);

        return row;
    }

    private class ItemHolder{
        private ImageView subTypePic;
        private TextView name;

        public ItemHolder(View v){
            subTypePic = (ImageView) v.findViewById(R.id.image);
            name = (TextView) v.findViewById(R.id.name);
        }

    }
}
