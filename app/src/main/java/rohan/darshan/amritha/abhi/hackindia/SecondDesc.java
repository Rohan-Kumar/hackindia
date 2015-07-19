package rohan.darshan.amritha.abhi.hackindia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Rohan on 7/19/2015.
 */
public class SecondDesc extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second, container, false);

        Intent intent = getActivity().getIntent();
        String d = intent.getStringExtra("darshan");
        TextView tv = (TextView) view.findViewById(R.id.D);
        tv.setText(d);
        return view;

    }
}
