package com.liucanwen.citylist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.liucanwen.citylist.adapter.RegionAdapter;
import com.liucanwen.citylist.adapter.RegionAdapter2;
import com.liucanwen.citylist.adapter.RegionViewHolder;
import com.liucanwen.citylist.bean.all.Children;
import com.liucanwen.citylist.bean.all.CountryRegion;
import com.liucanwen.citylist.bean.all.Root;
import com.liucanwen.citylist.utils.StreamUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by code on 9/19/16.
 */
public class GlobalActivity extends Activity implements RegionAdapter2.OnItemClickListener {

    private RecyclerView recyclerView;
    private RegionAdapter2 adapter;

    private String region, province, city, district;


    private List<CountryRegion> result_List;
    private List<Children> city_List;
    private List<Children> district_List;


    private List<String> itemList, provinces, cities;

    private int curPage;
    private int resultCode = 0;

    @Override
    protected void onCreate(Bundle ins) {
        super.onCreate(ins);
        setContentView(R.layout.activity_region);
        Intent intent = getIntent();
        if (intent != null) {
            region = intent.getStringExtra("region");
            resultCode = intent.getIntExtra("resultCode", 0);
            String[] tmp = region.split(" ");
            province = tmp[0];
            city = tmp[1];
            district = tmp[2];
            Log.d("TAG", "[当前操作]province=" + province);
            Log.d("TAG", "[当前操作]city=" + city);
            Log.d("TAG", "[当前操作]district=" + district);
        }
        itemList = new ArrayList<>();
        provinces = new ArrayList<>();
        cities = new ArrayList<>();

        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RegionAdapter2(this);
        adapter.setListener(this);

        recyclerView.setAdapter(adapter);
    }


    private static final int PROVINCE = 0;
    private static final int CITY = 1;
    private static final int DISTRICT = 2;

    @Override
    public void onItemClick(RegionViewHolder holder, int position) {
        switch (curPage) {

            case PROVINCE:
                province = itemList.get(position);
                for (CountryRegion result : result_List) {
                    if (province.equals(result.getName()))
                        city_List = result.getChildren();
                }
                provinces.clear();
                provinces.addAll(itemList);
                itemList.clear();
                for (Children city : city_List) {
                    if (this.city.equals(city.getName()))
                        itemList.add(0, city.getName());
                    else
                        itemList.add(city.getName());
                }
                if(city_List.get(0).getChildren()==null||city_List.get(0).getChildren().size()==0){
                    adapter.setLo(true);
                }

                adapter.setData(itemList, city);

                curPage++;
                break;

            case CITY:
                city = itemList.get(position);
                for (Children city : city_List) {
                    if (this.city.equals(city.getName()))
                        district_List = city.getChildren();
                }
                cities.clear();
                cities.addAll(itemList);
                itemList.clear();

                if(district_List==null){
                    curPage=2;
                    break;
                }

                for (Children district : district_List) {
                    if (this.district.equals(district.getName()))
                        itemList.add(0, district.getName());
                    else
                        itemList.add(district.getName());
                }
                if(district_List.get(0).getChildren()==null||district_List.get(0).getChildren().size()==0){
                    adapter.setLo(true);
                }

                adapter.setData(itemList, district);
                curPage++;
                break;

            case DISTRICT:
                Intent mIntent = new Intent();
                if(!itemList.isEmpty()){
                    district = itemList.get(position);
                    mIntent.putExtra("result", province + " " + city + " " + district);
                }else {
                    mIntent.putExtra("result", province + " " + city  );
                }

                this.setResult(resultCode, mIntent);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        switch (curPage) {
            case PROVINCE:
                finish();
                break;

            case CITY:
                itemList.clear();
                itemList.addAll(provinces);
                adapter.setData(itemList, province);
                curPage--;
                break;

            case DISTRICT:
                itemList.clear();
                itemList.addAll(cities);
                adapter.setData(itemList, city);
                curPage--;
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            if (result_List == null) {
                Gson gson = new Gson();
                String loc = StreamUtils.get(this, R.raw.city2);
                Root root = gson.fromJson(loc, Root.class);
                result_List = root.getCountryRegion();
            }

            for (CountryRegion result : result_List) {
                if (province.equals(result.getName()))
                    itemList.add(0, result.getName());
                else
                    itemList.add(result.getName());
            }
            adapter.setData(itemList, province);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
