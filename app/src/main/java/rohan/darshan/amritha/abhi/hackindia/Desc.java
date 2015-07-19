package rohan.darshan.amritha.abhi.hackindia;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ramesh on 7/18/2015.
 */
public class Desc extends Fragment {

    TextView Name, Addr, Desc;
    Intent intent;
    static String name, addr, city, price, desc, lat, lng;
    public static ArrayList<String> lati = new ArrayList<>(), longi = new ArrayList<>(), title = new ArrayList<>();
    String latit,longit;
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
        latit = intent.getStringExtra("lat");
        longit = intent.getStringExtra("lng");
        Log.d("DARSHANROHAN", "Hello:" + desc);
        Name.setText(name);
        Addr.setText(addr + "\n" + city);
        Desc.setText(price);
        lat = intent.getStringExtra(Map_1.LAT);
        lng = intent.getStringExtra(Map_1.LNG);
        return view;
    }

}