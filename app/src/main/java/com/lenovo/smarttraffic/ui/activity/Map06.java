package com.lenovo.smarttraffic.ui.activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.lenovo.smarttraffic.MainActivity;
import com.lenovo.smarttraffic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Map06 extends BaseActivity {

    private ListviewAdapter adapter;
    private OkHttpClient okHttpClient;
    private List<Parke> parkes = new ArrayList<>();
    private PopupWindow popupWindow;
    private View root;
    ListView listView;
    private AMap aMap;
    private List<LatLng> latLngs = new ArrayList<>();
    private UiSettings uiSettings;
    @BindView(R.id.cj_txtMap)
    TextView textView;
    @BindView(R.id.cj_menu)
    ImageButton imageMenu;
    @BindView(R.id.cj_back)
    ImageButton imageBack;
    @BindView(R.id.cj_cosating)
    ImageButton imageCoast;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.cj_ding)
    ImageButton imageDing;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);
        init();
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Map06.this, MainActivity.class);
                uiSettings.setLogoBottomMargin(30);
                startActivity(intent);
            }
        });           //返回按钮
        imageCoast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng latLng = new LatLng(39.94, 116.38);
                aMap.addMarker(new MarkerOptions().position(latLng).title("什刹海").icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker_one)));
            }
        });         //第1，2题，什刹海
        imageDing.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                showPopWindow();
                popupWindow.showAtLocation(view, Gravity.RIGHT,0,0);

            }
        });           //3,4,5题，pop，排序,网络请求
        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Map06.this, imageMenu);
                getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getTitle().toString()) {
                            case "卫星视图":
                                aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
                                aMap.setTrafficEnabled(false);//显示实时路况图层，aMap是地图控制器对象。
                                break;
                            case "导航视图":
                                aMap.setMapType(AMap.MAP_TYPE_NAVI);
                                aMap.setTrafficEnabled(false);
                                break;
                            case "导航视图(Night)":
                                aMap.setMapType(AMap.MAP_TYPE_NIGHT);
                                aMap.setTrafficEnabled(false);
                                break;
                            case "正常视图":
                                aMap.setMapType(AMap.MAP_TYPE_NORMAL);
                                aMap.setTrafficEnabled(false);
                                break;
                            case "交通视图":
                                aMap.setMapType(AMap.MAP_TYPE_BUS);
                                aMap.setTrafficEnabled(false);
                                break;
                            case "实时路况":
                                aMap.setTrafficEnabled(true);       //显示实时路况图层，aMap是地图控制器对象，可以在每一个视图上叠加
                                break;
                            default:
                                break;
                        }

                        return true;
                    }
                });
                popupMenu.show();
            }
        });             //切换视图

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_map06;
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    private void dingdian(LatLng latLng){
//        for (int i = 0; i <latLngs.size() ; i++) {
//            aMap.addMarker(new MarkerOptions().position(latLngs.get(i)).icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker_one)));
//        }
        aMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker_one)));

    }
    private void getJson(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    showInListiew(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    @SuppressLint("NewApi")
    private void showInListiew(final String responce) throws JSONException {

        JSONArray jas = new JSONArray(responce);
        for (int i = 0; i < jas.length(); i++) {
            JSONObject jsonObject = jas.getJSONObject(i);
            int idJson = jsonObject.getInt("id");
            String nameJson = jsonObject.getString("name");
            int emptyJson = jsonObject.getInt("empty");
            String addressJson = jsonObject.getString("address");

            int cost = jsonObject.getInt("cost");
            int latitude = jsonObject.getInt("latitude");
            int longitude = jsonObject.getInt("longitude");

            LatLng latLng = new LatLng(latitude,longitude);
            latLngs.add(latLng);
            int distance = juli(latLng);
            parkes.add(new Parke(idJson,nameJson,emptyJson,addressJson,distance,cost,latitude,longitude));
            dingdian(latLng);


        }

        parkes.sort(new Comparator<Parke>() {
            @Override
            public int compare(Parke o1, Parke o2) {
                Integer a = o1.getDistance();
                Integer b = o2.getDistance();
                return b-a;
            }
        });                 //排序

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter = new ListviewAdapter(Map06.this,R.layout.item,parkes);
                listView.setAdapter(adapter);
                latLngs.clear();
                adapter.notifyDataSetChanged();
            }
        });

    }
    private void showPopWindow(){
        popupWindow = new PopupWindow(root,1000,1000);
        popupWindow.setFocusable(true);		//!获取焦点!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
    }                    //打开popWindow
    private int juli(LatLng latLng2){
        DPoint dPoint = new DPoint();
        dPoint.setLatitude(39.94);
        dPoint.setLongitude(116.38);
        DPoint dPoint2 = new DPoint();
        dPoint.setLatitude(latLng2.latitude);
        dPoint.setLongitude(latLng2.longitude);
        float distance = CoordinateConverter.calculateLineDistance(dPoint,dPoint2);
        Log.d("距离:   " , (int)distance+"m");
        return (int)distance;
    }               //计算距离
    private void init(){
        aMap = mapView.getMap();
        uiSettings = aMap.getUiSettings();
        uiSettings.setAllGesturesEnabled(true);
        okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .build();
        root = this.getLayoutInflater().inflate(R.layout.pop,null);
        listView = root.findViewById(R.id.cj_mapListView);
        getJson("https://api.myjson.com/bins/786zs");
    }


}