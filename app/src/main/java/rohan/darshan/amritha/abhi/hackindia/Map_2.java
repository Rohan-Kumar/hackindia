package rohan.darshan.amritha.abhi.hackindia;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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


public class Map_2 extends ActionBarActivity {

    GoogleMap map;


    ArrayList<String> Lat = new ArrayList<>();
    ArrayList<String> Lng = new ArrayList<>();
    ArrayList<Double> lati = new ArrayList<>(), longi = new ArrayList<>();
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_2);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map2))
                .getMap();


        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        String lat = intent.getStringExtra("lat");
        String lng = intent.getStringExtra("lng");
        String type = intent.getStringExtra("type");
        new Load(lat, lng, type).execute();

//        Lng = intent.getStringArrayListExtra("long");
//        Log.d("DARSHANAMRITHA", "" + Lng);

//                    .setTitle(Title.get(k));


//        }

    }

    public class Load extends AsyncTask<Void, Void, Void> {

        String lat, lng, type;

        Load(String lat, String lng, String type) {
            this.lat = lat;
            this.lng = lng;
            this.type = type;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            for (int k = 0; k < Lat.size(); k++) {

                lati.add(Double.parseDouble(Lat.get(k)));
                longi.add(Double.parseDouble(Lng.get(k)));

//                Toast.makeText(Map_2.this, "Marking", Toast.LENGTH_SHORT).show();

                map.addMarker(new MarkerOptions().position(new LatLng(lati.get(k), longi.get(k)))).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(12.978301, 77.571945), 14.0f));

                map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);


            }
        }

        @Override
        protected Void doInBackground(Void... params) {


            String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
                    "json?location=" + lat + "," + lng + "&radius=500&types=" + type + "&name=*&key=AIzaSyDm0xyQGJ1mDIMezQZxpUjGbtadDpuhdiU";


            Log.d("AMRITHAABHI", url);

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
                    Log.d("AMRITHAABHI", longitude);
                    if ((latitude.equals(null) || longitude.equals(null))) {
                        Log.d("AMRITHAABHI", "ERROR");
                    } else {
                        Lat.add(latitude);
                        Lng.add(longitude);
                    }

                    Log.d("AMRITHAABHI", "latitude =" + latitude + " longitude =" + longitude + " ");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

}
