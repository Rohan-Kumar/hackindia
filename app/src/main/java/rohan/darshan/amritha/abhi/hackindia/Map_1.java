package rohan.darshan.amritha.abhi.hackindia;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Map_1 extends ActionBarActivity {


    GoogleMap map;
    ArrayList<String> latitude = new ArrayList<>();
    ArrayList<String> longitude = new ArrayList<>();

    ArrayList<Double> latVal = new ArrayList<>();
    ArrayList<Double> lonVal = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_1);

        new load_map().execute();

        LatLng i = getLocationFromAddress("Bangalore");
        Toast.makeText(this,""+i,Toast.LENGTH_LONG).show();

    }

    private void addMap() {
        final ArrayList<Marker> markers = new ArrayList<>(latVal.size());
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();
        for (int k = 0; k < latVal.size(); k++) {

//            LatLng customMarkerLoc = new LatLng(latVal.get(0), lonVal.get(0));//12.978301, 77.571945
           /* Marker any = map.addMarker(new MarkerOptions()
                            .position(customMarkerLoc)
            );*/

            map.addMarker(new MarkerOptions().position(new LatLng(latVal.get(k), lonVal.get(k)))).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    Toast.makeText(Map_1.this,""+marker.getPosition(),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Map_1.this,MainActivity.class);
                    intent.putExtra("abc",marker.getPosition());
                    startActivity(intent);
                    return false;
                }
            });


        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(12.978301, 77.571945), 14.0f));

        // Move the camera instantly to hamburg with a zoom of 15.
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(customMarkerLoc, 15));

        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }

    public class load_map extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... params) {

            POST();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            for (int j = 0; j < latitude.size(); j++) {
                latVal.add(Double.parseDouble(latitude.get(j)));
                lonVal.add(Double.parseDouble(longitude.get(j)));
            }

            Log.d("DARSHANROHAN", "" + lonVal);
            addMap();

        }
    }

    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    public void POST() {


        String USER_AGENT = "HackIndia Team <TeamName>";

        String url = "https://stayzilla.com/hotels";
        URL obj = null;
        try {
            obj = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) obj.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //add reuqest header
        try {
            con.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "*/*");

        String urlParameters = "lat=12.927&lng=77.627&from=0&to=30";

        // Send post request
        System.out.println("\nSending 'POST' request to URL : " + url);
        con.setDoOutput(true);
        DataOutputStream wr = null;
        try {
            wr = new DataOutputStream(con.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            wr.writeBytes(urlParameters);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            wr.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int responseCode = 0;
        try {
            responseCode = con.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String inputLine;
        StringBuffer response = new StringBuffer();

        try {
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("ABHIDARSHAN", "Printing output..");
        //print result
        Log.d("ABHIDARSHAN", response.toString());
        parseJson(response.toString());
        Log.d("ABHIDARSHAN", "--------------- END ---------------");

        // Parse response.toString() as JSON and do something..

    }

    private void parseJson(String jsonString) {

        String dispName, addr, city, price, image, desc, lat, lng;

        try {
            JSONObject main = new JSONObject(jsonString);
            JSONArray jsonArray = main.getJSONArray("hotels");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                Log.d("DARSHANABHI",""+jsonObject);
//                dispName = jsonObject.getString("displayName");
//                DisplayNames.add(dispName);
//
//                addr = jsonObject.getString("address");
//                Address.add(addr);
//
//                city = jsonObject.getString("city");
//                Cities.add(city);
//
//                price = jsonObject.getString("price");
//                Prices.add(price);
//
//                image = jsonObject.getString("imageURL");
//                MediaStore.Images.add(image);
//
//                desc = jsonObject.getString("description");
//                Descriptions.add(desc);
//
                JSONObject cord = jsonObject.getJSONObject("geoCoordinates");
                lat = cord.getString("lat");
                latitude.add(lat);

                lng = cord.getString("lng");
                longitude.add(lng);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
