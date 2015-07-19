package rohan.darshan.amritha.abhi.hackindia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Ramesh on 7/18/2015.
 */
public class Desc extends Fragment {

    TextView Name, Addr, Desc;
    Intent intent;
    String name, addr, city, price, desc, lat, lng;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_desc, container, false);
        Name = (TextView) view.findViewById(R.id.nameId);
        Addr = (TextView) view.findViewById(R.id.AddrId);
        Desc = (TextView) view.findViewById(R.id.DescId);
        intent = getActivity().getIntent();
        name = intent.getStringExtra(Map_1.NAME);
        addr = intent.getStringExtra(Map_1.ADDR);
        city = intent.getStringExtra(Map_1.CITY);
        price = intent.getStringExtra(Map_1.PRICE);
        desc = intent.getStringExtra("darshan");
        Log.d("DARSHANROHAN", "Hello:" + desc);
        Name.setText(name + "\n" + price);
        Addr.setText(addr + "\n" + city);
        Desc.setText(desc);
        lat = intent.getStringExtra(Map_1.LAT);
        lng = intent.getStringExtra(Map_1.LNG);
        return view;
    }
}
