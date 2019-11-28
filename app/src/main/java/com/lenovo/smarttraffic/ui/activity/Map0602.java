package com.lenovo.smarttraffic.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.lenovo.smarttraffic.R;

import java.util.ArrayList;
import java.util.List;

public class Map0602 extends AppCompatActivity   {
    private Spinner start;
    private Spinner end;
    MapView mMapView = null;
    private AMap aMap;
    private UiSettings uiSettings;
    private Button button;
    private LatLonPoint mStartPoint;
    private LatLonPoint mEndPoint;
    private List<Site> sites = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map0602);
        start = findViewById(R.id.cj_start);
        button = findViewById(R.id.lujing);
        end = findViewById(R.id.cj_end);
        Parke parke = (Parke) getIntent().getSerializableExtra("xiangqing");
        mMapView = (MapView) findViewById(R.id.mapView2);            //获取地图控件引用
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();                //获取AMap对象
        uiSettings = aMap.getUiSettings();
        uiSettings.setLogoBottomMargin(100);
        uiSettings.setAllGesturesEnabled(true);             //支持所有的手势






        LatLng latLng = new LatLng(parke.getLatitude(),parke.getLongitude());
        dingdian(latLng);
        Toast.makeText(Map0602.this,"您已定位到"+parke.getName()+"停车场",Toast.LENGTH_LONG).show();

        Site site1 = new Site("北京西站",116.32,39.89);
        Site site2 = new Site("天坛",116.41,39.88);
        Site site3 = new Site("军事博物馆",116.32,39.90);
        sites.add(site1);
        sites.add(site2);
        sites.add(site3);

        ArrayAdapter adapter = new ArrayAdapter(this.getApplicationContext(), android.R.layout.simple_spinner_item,sites );
        start.setAdapter(adapter);
        end.setAdapter(adapter);





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Site siteA = (Site) start.getSelectedItem();
                Site siteB = (Site) start.getSelectedItem();
                mStartPoint = new LatLonPoint(siteA.getLatitude(),siteA.getLongitude());
                mEndPoint = new LatLonPoint(siteB.getLatitude(),siteB.getLongitude());

            }
        });

    }
    private void dingdian(LatLng latLng){
        aMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker_one)));
    }




}