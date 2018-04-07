package babasmatatu.hackerthon.com.babasmatatu;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Locale;

import babasmatatu.hackerthon.com.babasmatatu.helpers.InterfaceAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by munene on 4/7/2018.
 */
public class JourneyActivity extends AppCompatActivity {
    Place mFromPlace, mToPlace;

    @BindView(R.id.fromEditText)
    EditText fromEditText;
    @BindView(R.id.toEditText)
    EditText toEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String fromPlaceJsonLong = extras.getString("fromPlaceLong");
        String fromPlaceJsonLat = extras.getString("fromPlaceLat");
        String toPlaceJsonLong = extras.getString("toPlaceLong");
        String toPlaceJsonLat = extras.getString("toPlaceLat");

        String fromPlaceName = extras.getString("fromPlaceName");
        String toPlaceName = extras.getString("toPlaceName");

        fromEditText.setText(fromPlaceName);
        toEditText.setText(toPlaceName);
    }
}
