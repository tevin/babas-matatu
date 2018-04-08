package babasmatatu.hackerthon.com.babasmatatu;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.uber.sdk.android.core.UberSdk;
import com.uber.sdk.android.rides.RideParameters;
import com.uber.sdk.android.rides.RideRequestButton;
import com.uber.sdk.rides.client.SessionConfiguration;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import babasmatatu.hackerthon.com.babasmatatu.api_models.routes.Journey;
import babasmatatu.hackerthon.com.babasmatatu.api_models.routes.Steps;
import babasmatatu.hackerthon.com.babasmatatu.helpers.GeofenceBroadcastReceiver;
import babasmatatu.hackerthon.com.babasmatatu.helpers.GeofenceTransitionsIntentService;
import babasmatatu.hackerthon.com.babasmatatu.helpers.InterfaceAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by munene on 4/7/2018.
 */
public class JourneyActivity extends AppCompatActivity {
    Place mFromPlace, mToPlace;
    Double fromPlaceJsonLong, fromPlaceJsonLat, toPlaceJsonLong, toPlaceJsonLat = 0.0;
    private ArrayList<Geofence> mGeofenceList;
    static final float GEOFENCE_RADIUS_IN_METERS = 1609; // 1 mile, 1.6 km
    private static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
    static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
    private GeofencingClient mGeofencingClient;
    private PendingIntent mGeofencePendingIntent;

    @BindView(R.id.fromEditText)
    EditText fromEditText;
    @BindView(R.id.toEditText)
    EditText toEditText;
    @BindView(R.id.journeyList)
    ListView journeyList;
    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout slidingLayout;
    @BindView(R.id.dragView)
    LinearLayout dragView;
    @BindView(R.id.descriptiveTextView)
    TextView descriptiveTextView;
    @BindView(R.id.uberButton)
    RideRequestButton uberButton;
    @BindView(R.id.endRideButton)
    Button endRideButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);
        ButterKnife.bind(this);
        mGeofencePendingIntent = null;
        mGeofencingClient = LocationServices.getGeofencingClient(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        fromPlaceJsonLong = extras.getDouble("fromPlaceLong");
        fromPlaceJsonLat = extras.getDouble("fromPlaceLat");
        toPlaceJsonLong = extras.getDouble("toPlaceLong");
        toPlaceJsonLat = extras.getDouble("toPlaceLat");

        String fromPlaceName = extras.getString("fromPlaceName");
        String toPlaceName = extras.getString("toPlaceName");

        String journeyString = extras.getString("journey");

        Gson gson = new Gson();
        final Journey journey = gson.fromJson(journeyString, Journey.class);

        if (journey == null) {
            Toast.makeText(this, "Where de journey though?? Jeremy made an oopsie. Baba alisema fails automatically.", Toast.LENGTH_LONG).show();
            onBackPressed();
        }

        fromEditText.setText(fromPlaceName);
        toEditText.setText(toPlaceName);

        slidingLayout.setAnchorPoint(0.2f);
        slidingLayout.setOverlayed(true);
        computeSliderHeight(false);

        ArrayList<String> values = new ArrayList<>();
        int count = 0;
        mGeofenceList = new ArrayList<>();

        SessionConfiguration config = new SessionConfiguration.Builder()
                // mandatory
                .setClientId("QmPm7ts3ki67UB37wE07O5_25CLLNNvP")
                // required for enhanced button features
                .setServerToken("NCarzvVpoVFipzz7yxTztA0MMslhz13Tu6PDygUK")
                // required for implicit grant authentication
                .setRedirectUri("https://login.uber.com/oauth/authorize")
                // optional: set sandbox as operating environment
                .setEnvironment(SessionConfiguration.Environment.SANDBOX)
                .build();

        UberSdk.initialize(config);

        final Handler h = new Handler();

        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        for (Steps step :
                journey.getSteps()) {
            values.add(++count + "." + " " + step.getInstructions());

            double longitude = 0.0, latitude = 0.0;

            String longLatString = step.getEnd();
            ObjectMapper mapper = new ObjectMapper();
            try {
                List<Double> participantJsonList = mapper.readValue(longLatString, new TypeReference<List<Double>>() {
                });

                if (!participantJsonList.isEmpty()) {
                    latitude = participantJsonList.get(0);
                    longitude = participantJsonList.get(1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                mGeofenceList.add(new Geofence.Builder()
                        // Set the request ID of the geofence. This is a string to identify this
                        // geofence.
                        .setRequestId(UUID.randomUUID().toString())

                        // Set the circular region of this geofence.
                        .setCircularRegion(
                                latitude,
                                longitude,
                                GEOFENCE_RADIUS_IN_METERS
                        )

                        // Set the expiration duration of the geofence. This geofence gets automatically
                        // removed after this period of time.
                        .setExpirationDuration(GEOFENCE_EXPIRATION_IN_MILLISECONDS)

                        // Set the transition types of interest. Alerts are only generated for these
                        // transition. We track entry and exit transitions in this sample.
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                                Geofence.GEOFENCE_TRANSITION_EXIT)

                        // Create the geofence.
                        .build());
            } catch (Exception e) {
                //Do nothing
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.simple_list_item, R.id.custom_list_item, values);


        // Assign adapter to ListView
        journeyList.setAdapter(adapter);

        try{
            Steps step = journey.getSteps()[0];
            descriptiveTextView.setText(step.getInstructions());

            if (step.getType().toLowerCase().trim().equals("uber".toLowerCase().trim()))
            {
                uberButton.setVisibility(View.VISIBLE);
                computeSliderHeight(true);
            }
            else {
                uberButton.setVisibility(View.GONE);
                computeSliderHeight(false);
            }

            setViewStyles(0, journeyList);
        }catch (Exception e){
            //Do nothing
        }

        h.postDelayed(new Runnable() {
            private long time = 0;
            int journeyCount = 1;

            @Override
            public void run() {
                // do stuff then
                // can call h again after work!
                //time += 1000;
                //Log.d("TimerExample", "Going for... " + time);

                try{
                    if (journeyCount == journey.getSteps().length ){
                        //End trip
                        Toast.makeText(JourneyActivity.this, "Your trip has ended", Toast.LENGTH_LONG).show();
                        onBackPressed();
                        return;
                    }

                    setViewStyles(journeyCount, journeyList);
                    Steps step = journey.getSteps()[journeyCount];
                    descriptiveTextView.setText(step.getInstructions());

                    if (step.getType().toLowerCase().trim().equals("uber".toLowerCase().trim()))
                    {
                        uberButton.setVisibility(View.VISIBLE);

                        String longLatStringStart = step.getStart();
                        String longLatStringEnd = step.getEnd();
                        ObjectMapper mapper = new ObjectMapper();
                        double longitudeStart = 0.0, latitudeStart = 0.0, longitudeEnd = 0.0, latitudeEnd = 0.0;

                        try {
                            List<Double> participantJsonStartList = mapper.readValue(longLatStringStart, new TypeReference<List<Double>>() {
                            });
                            List<Double> participantJsonEndList = mapper.readValue(longLatStringEnd, new TypeReference<List<Double>>() {
                            });

                            if (!participantJsonStartList.isEmpty()) {
                                longitudeStart = participantJsonStartList.get(0);
                                latitudeStart = participantJsonStartList.get(1);
                            }

                            if (!participantJsonEndList.isEmpty()) {
                                latitudeEnd = participantJsonEndList.get(0);
                                longitudeEnd = participantJsonEndList.get(1);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        RideParameters rideParams = new RideParameters.Builder()
                                // Optional product_id from /v1/products endpoint (e.g. UberX). If not provided, most cost-efficient product will be used
                                .setProductId("a1111c8c-c720-46c3-8534-2fcdd730040d")
                                // Required for price estimates; lat (Double), lng (Double), nickname (String), formatted address (String) of dropoff location
                                .setDropoffLocation(
                                        latitudeStart, longitudeStart, "", "")
                                // Required for pickup estimates; lat (Double), lng (Double), nickname (String), formatted address (String) of pickup location
                                .setPickupLocation(latitudeEnd, longitudeEnd, "", "")
                                .build();
// set parameters for the RideRequestButton instance
                        uberButton.setRideParameters(rideParams);

                        computeSliderHeight(true);
                    }
                    else {
                        uberButton.setVisibility(View.GONE);
                        computeSliderHeight(false);
                    }

                    journeyCount ++;

                }catch (Exception e){
                    //Do nothing
                }
                h.postDelayed(this, 5000);
            }
        }, 15000); // 1 second delay (takes millis)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        try {
            mGeofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String a = "";
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String b = "";
                        }
                    });
        } catch (Exception e) {
            //Do nothing
        }
    }

    public void computeSliderHeight(Boolean isUberActive){
        if (isUberActive) slidingLayout.setPanelHeight(560);
        else slidingLayout.setPanelHeight(300);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            mGeofencingClient.removeGeofences(getGeofencePendingIntent()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
        } catch (Exception e) {
            //Do nothing
        }
    }
    public void setViewStyles(int pos, ListView listView) {
        TextView textView = (TextView) listView.getAdapter().getView(pos, null, listView);
        Log.d("Text View", textView.toString());
        textView.setTextSize(16);
        textView.setTextAppearance(this, R.style.journey_in_progress);

    }

    @OnClick(R.id.endRideButton)
    public void endRide(View view) {
        Toast.makeText(this, "Your ride has ended", Toast.LENGTH_LONG).show();
        //onBackPressed();
        new LovelyInfoDialog(this)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle("Journey Ended")
                .setMessage(Html.fromHtml("Congrats on your safe journey! You have received <b>5 new points!</b>"))
                .setIcon(R.drawable.avatar)
                .show();
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        // The INITIAL_TRIGGER_ENTER flag indicates that geofencing service should trigger a
        // GEOFENCE_TRANSITION_ENTER notification when the geofence is added and if the device
        // is already inside that geofence.
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);

        // Add the geofences to be monitored by geofencing service.
        builder.addGeofences(mGeofenceList);

        // Return a GeofencingRequest.
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // addGeofences() and removeGeofences().
        mGeofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return mGeofencePendingIntent;
    }
}
