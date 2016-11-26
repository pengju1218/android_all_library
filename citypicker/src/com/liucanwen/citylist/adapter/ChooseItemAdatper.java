package com.liucanwen.citylist.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.liucanwen.citylist.R;

import java.util.List;

/**
 * Created by Administrator on 2016/7/11.
 */
public class ChooseItemAdatper extends ArrayAdapter<String> {

    public Context context;


    public ChooseItemAdatper(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.context = context;
    }


    public ChooseItemAdatper(Context context, int resource, String[] objects) {
        super(context, resource, objects);
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.choose_item, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.bi_eetext);
            convertView.setTag(viewHolder);
        }

        String namet = getItem(position);
        Log.i("tewesesese", namet);
        viewHolder = (ViewHolder) convertView.getTag();
        // viewHolder.name.setTextColor(Color.GREEN);
        viewHolder.name.setText(getItem(position));
        viewHolder.name.setTextColor(context.getResources().getColor(R.color.white));
        convertView.setBackgroundColor(context.getResources().getColor(R.color.bac));
        return convertView;
    }

    class ViewHolder {
        TextView name;
    }
}
