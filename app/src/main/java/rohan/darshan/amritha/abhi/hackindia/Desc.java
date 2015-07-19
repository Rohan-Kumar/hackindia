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
import android.widget.ScrollView;
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
    String name, addr, city, price, desc, lat, lng;
    ScrollView scrollView ;
    ArrayList<String> latitudes = new ArrayList<>();
    ArrayList<String> longitudes = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();


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
        new Load(lat, lng, "food").execute();
        return view;
    }

    public class Load extends AsyncTask<Void, Void, Void> {

        String lat, lng, type;

        Load(String lat, String lng, String type) {
            this.lat = lat;
            this.lng = lng;
            this.type = type;
        }


        @Override
        protected Void doInBackground(Void... params) {


            String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
                    "json?location=" + lat + "," + lng + "&radius=500&types=" + type + "&name=*&key=AIzaSyDm0xyQGJ1mDIMezQZxpUjGbtadDpuhdiU";


            Log.d("AMRITHAABHI", "inside");

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            try {
                HttpResponse response = httpClient.execute(httpPost);

                HttpEntity httpEntity = response.getEntity();
                String resp = EntityUtils.toString(httpEntity);
                Log.d("AMRITHAABHI", resp);
                parseJson(resp);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        private void parseJson(String responseString) {
            String latitude, longitude, name, rating;
            try {
                JSONObject main = new JSONObject(responseString);
                JSONArray jsonArray = main.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JSONObject geo = jsonObject.getJSONObject("geometry");
                    JSONObject loc = geo.getJSONObject("location");
                    latitude = loc.getString("lat");
                    longitude = loc.getString("lng");
                    name = jsonObject.getString("name");
//                    rating = "" + jsonObject.getInt("rating");
                    latitudes.add(latitude);
                    longitudes.add(longitude);
                    names.add(name);

                    Log.d("AMRITHAABHI", latitude + " " + longitude + " " + name + " ");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
