package rohan.darshan.amritha.abhi.hackindia;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

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


public class MainActivity extends ActionBarActivity {

    public ArrayList<String> DisplayNames = new ArrayList<>();
    public ArrayList<String> Address = new ArrayList<>();
    public ArrayList<String> Cities = new ArrayList<>();
    public ArrayList<String> Prices = new ArrayList<>();
    public ArrayList<String> Images = new ArrayList<>();
    public ArrayList<String> Descriptions = new ArrayList<>();
    public ArrayList<String> Latitudes = new ArrayList<>();
    public ArrayList<String> Longitudes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new get_data().execute();
    }


    public class get_data extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            POST();
            return null;
        }
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