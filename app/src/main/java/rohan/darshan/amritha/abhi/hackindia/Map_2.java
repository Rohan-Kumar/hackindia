package rohan.darshan.amritha.abhi.hackindia;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class Map_2 extends ActionBarActivity {

    GoogleMap map;

    ArrayList<String> title,Lat,Lng;
    ArrayList<Double> lati,longi;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_2);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();

        Intent intent = getIntent();
        Lat= intent.getStringArrayListExtra("");
        title= intent.getStringArrayListExtra("");
        Lng= intent.getStringArrayListExtra("");
        type = intent.getStringExtra("");


        for (int k = 0; k < lati.size(); k++) {

            lati.add(Double.parseDouble(Lat.get(k)));
            longi.add(Double.parseDouble(Lng.get(k)));


            map.addMarker(new MarkerOptions().position(new LatLng(lati.get(k), longi.get(k))))
                    .setSnippet(title.get(k));


        }

    }

}
