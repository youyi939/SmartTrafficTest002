package com.lenovo.smarttraffic.ui.activity;
import android.os.Bundle;
import android.widget.TextView;

import com.lenovo.smarttraffic.R;

import butterknife.BindView;

public class ParkDetails extends BaseActivity {
    private Parke parke;
    @BindView(R.id.cj_name)
    TextView name;
    @BindView(R.id.cj_address2)
    TextView address;

    private TextView empty;
    private TextView cost;
    private TextView distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        empty = findViewById(R.id.cj_emptyyyy);
        cost = findViewById(R.id.cj_costttttttt);
        distance = findViewById(R.id.cj_distanceeeeee);

        parke = (Parke) getIntent().getSerializableExtra("park");
        name.setText(parke.getName());
        address.setText(parke.getAddress());
        empty.setText(parke.getEmpty().toString()+"个/155");
        cost.setText(parke.getCost().toString()+"元/小时");
        distance.setText(parke.getDistance().toString()+"m");





    }

    @Override
    protected int getLayout() {
        return R.layout.activity_park_details;
    }

}
