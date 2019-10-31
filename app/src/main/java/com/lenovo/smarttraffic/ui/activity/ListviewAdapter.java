package com.lenovo.smarttraffic.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenovo.smarttraffic.R;

import java.util.List;

import static com.lenovo.smarttraffic.util.CommonUtil.getResources;

public class ListviewAdapter extends ArrayAdapter<Parke> {
    private int resourceId;
    private LinearLayout linearLayout;



    public ListviewAdapter(@NonNull Context context, int resource, List objects) {
        super(context, resource,objects);
        resourceId = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        linearLayout = view.findViewById(R.id.itemliner);
        final Parke parke = getItem(position);
        ImageButton imageButton = view.findViewById(R.id.xiangqing);
        final TextView   name = view.findViewById(R.id.cj_list_txt_name);
        final TextView   chewei = view.findViewById(R.id.cj_list_txt_chewei);
        final TextView   address = view.findViewById(R.id.cj_list_txt_address);
        final TextView   id = view.findViewById(R.id.cj_list_txt_id);
        if (parke.getEmpty() == 0){
          linearLayout.setBackgroundColor(Color.GRAY);
            imageButton.setImageDrawable(getResources().getDrawable(R.drawable.close));
            imageButton.setEnabled(false);

        }else {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(),ParkDetails.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("park",parke);
                    Log.d("parke...",parke.getEmpty().toString()+parke.getDistance().toString());
                    intent.putExtras(bundle);
                    getContext().startActivity(intent);
                }
            });
        }

        name.setText(parke.getName()+"");
        chewei.setText("空车位"+parke.getEmpty()+"个,"+"停车费"+parke.getCost()+"/小时");
        address.setText(parke.getAddress()+" "+parke.getDistance()+"m");
        id.setText(parke.getId()+"");

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),Map0602.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("xiangqing",parke);
                Log.d("xiangqing",parke.getEmpty().toString()+parke.getDistance().toString());
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        });

        return view;
    }
}
