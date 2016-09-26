package com.liucanwen.citylist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liucanwen.citylist.adapter.ChooseItemAdatper;

import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */
public class ChooseActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {


    private String[] namess = null;
    private List<String> list = null;
    private ChooseItemAdatper adatper = null;
    private int resultCode = 0;
    private android.widget.ListView chooselistView;
    private android.widget.ImageView topViewBackHome1;
    private android.widget.TextView topViewCenterText1;
    private android.widget.RelativeLayout top;
    private String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose);
        this.top = (RelativeLayout) findViewById(R.id.top);
        this.topViewCenterText1 = (TextView) findViewById(R.id.topViewCenterText1);
        this.topViewBackHome1 = (ImageView) findViewById(R.id.topViewBackHome1);
        this.chooselistView = (ListView) findViewById(R.id.choose_listView);
        topViewBackHome1.setOnClickListener(this);
        Intent intent = getIntent();
        if (intent != null) {
            resultCode = intent.getIntExtra("resultCode", 0);
            namess = intent.getStringArrayExtra("names");
            list = intent.getStringArrayListExtra("list");
            title = intent.getStringExtra("title");
            topViewCenterText1.setText(title+"");
        }
        if (namess != null) {
            setNames(namess);
        }

        if (list != null) {
            setNames(list);
        }

        chooselistView.setOnItemClickListener(this);
    }


    public void setNames(String[] names) {
        adatper = new ChooseItemAdatper(this, 0, names);
        chooselistView.setAdapter(adatper);
    }

    public void setNames(List<String> list) {
        adatper = new ChooseItemAdatper(this, 0, list);
        chooselistView.setAdapter(adatper);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent mIntent = new Intent();
        if (namess != null) {
            mIntent.putExtra("name", namess[position]);
        }

        if (list != null) {
            mIntent.putExtra("name", list.get(position));
        }

        this.setResult(resultCode, mIntent);
        this.finish();

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.topViewBackHome1) {
            finish();
        }
    }
}
