package rohan.darshan.amritha.abhi.hackindia;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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

    public static final String IMAGE = "image",LAT = "lat", LNG = "lng", NAME = "name", ADDR = "addr", CITY = "city", DESC = "desc", PRICE = "price";
    GoogleMap map;
    public ArrayList<String> DisplayNames = new ArrayList<>();
    public ArrayList<String> Address = new ArrayList<>();
    public ArrayList<String> Cities = new ArrayList<>();
    public ArrayList<String> Prices = new ArrayList<>();
    public ArrayList<String> Images = new ArrayList<>();
    public ArrayList<String> Descriptions = new ArrayList<>();
    public ArrayList<String> Latitudes = new ArrayList<>();
    public ArrayList<String> Longitudes = new ArrayList<>();


    ArrayList<Double> latVal = new ArrayList<>();
    ArrayList<Double> lonVal = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_1);

        new load_map().execute();

        LatLng i = getLocationFromAddress("Bangalore");
        Toast.makeText(this, "" + i, Toast.LENGTH_LONG).show();

    }

    private void addMap() {
        final ArrayList<Marker> markers = new ArrayList<>(latVal.size());
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();
        for (int k = 0; k < latVal.size(); k++) {


            map.addMarker(new MarkerOptions().position(new LatLng(latVal.get(k), lonVal.get(k))))
                    .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));


        }
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng ll = marker.getPosition();
                String lll = "" + ll;
                String sub1 = lll.substring(lll.indexOf("(") + 1, lll.indexOf(","));
                String sub2 = lll.substring(lll.indexOf(",") + 1, lll.length() - 1);
                sub1 = sub1.substring(0, 8);
                sub2 = sub2.substring(0, 8);


                for (int j = 0; j < DisplayNames.size(); j++) {
                    String sub3 = Latitudes.get(j).substring(0, 8);
                    String sub4 = Longitudes.get(j).substring(0, 8);
                    Log.d("ABHIDARSHAN", "sub1:" + sub1 + " sub2:" + sub2 + " lat:" + Latitudes.get(j) + " long:" + Longitudes.get(j));
                    if (sub1.equals(sub3) && sub2.equals(sub4)) {
                        Intent intent = new Intent(Map_1.this, HotelDetails.class);
                        intent.putExtra(NAME, DisplayNames.get(j));
                        intent.putExtra("darshan", Descriptions.get(j));
                        intent.putExtra(PRICE, Prices.get(j));
                        intent.putExtra(ADDR, Address.get(j));
                        intent.putExtra(CITY, Cities.get(j));
                        intent.putExtra(LAT, sub3);
                        intent.putExtra(LNG, sub4);
                        intent.putExtra(IMAGE,Images.get(j));
                        startActivity(intent);

                        break;
                    }
                }
                Toast.makeText(Map_1.this, "" + marker.getPosition(), Toast.LENGTH_LONG).show();


                return false;
            }
        });

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(12.978301, 77.571945), 14.0f));

        // Move the camera instantly to hamburg with a zoom of 15.
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(customMarkerLoc, 15));

        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }

    public class load_map extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {

            POST();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            for (int j = 0; j < Latitudes.size(); j++) {
                latVal.add(Double.parseDouble(Latitudes.get(j)));
                lonVal.add(Double.parseDouble(Longitudes.get(j)));
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

            p1 = new LatLng(location.getLatitude(), location.getLongitude());

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
                Log.d("DARSHANABHI", "" + jsonObject);
                dispName = jsonObject.getString("displayName");
                DisplayNames.add(dispName);

                addr = jsonObject.getString("address");
                Address.add(addr);

                city = jsonObject.getString("city");
                Cities.add(city);

                price = jsonObject.getString("price");
                Prices.add(price);

                image = jsonObject.getString("imageURL");
                Images.add(image);

                desc = jsonObject.getString("description");
                Descriptions.add(desc);
                Log.d("DARSHANROHAN", "Hey" + desc);

                JSONObject cord = jsonObject.getJSONObject("geoCoordinates");
                lat = cord.getString("lat");
                Latitudes.add(lat);

                lng = cord.getString("lng");
                Longitudes.add(lng);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
