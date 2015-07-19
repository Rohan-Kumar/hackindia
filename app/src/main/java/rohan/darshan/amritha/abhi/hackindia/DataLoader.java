package rohan.darshan.amritha.abhi.hackindia;

import android.os.AsyncTask;
import android.util.Log;

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

/**
 * Created by Ramesh on 7/19/2015.
 */
public class DataLoader extends AsyncTask<Void, Void, Void> {

    String lat, lng, type;

    DataLoader(String lat, String lng, String type) {
        this.lat = lat;
        this.lng = lng;
        this.type = type;
    }


    String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
            "json?location=" + lat + "," + lng + "&radius=500&types=" + type + "&name=*&key=AIzaSyDm0xyQGJ1mDIMezQZxpUjGbtadDpuhdiU";

    @Override
    protected Void doInBackground(Void... params) {

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            String resp = EntityUtils.toString(httpEntity);
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
                name = geo.getString("name");
                rating = geo.getString("rating");

                Log.d("AMRITHAABHI", latitude + " " + longitude + " " + name + " " + rating);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
