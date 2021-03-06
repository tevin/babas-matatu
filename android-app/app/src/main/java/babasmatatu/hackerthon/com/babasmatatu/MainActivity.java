package babasmatatu.hackerthon.com.babasmatatu;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.Gson;
import com.uber.sdk.android.rides.RideParameters;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import babasmatatu.hackerthon.com.babasmatatu.api_models.routes.Steps;
import babasmatatu.hackerthon.com.babasmatatu.helpers.ILocationCallback;
import babasmatatu.hackerthon.com.babasmatatu.helpers.LeaderboardActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static babasmatatu.hackerthon.com.babasmatatu.helpers.MiscServices.getInitalLocationGlobal;
import static babasmatatu.hackerthon.com.babasmatatu.helpers.MiscServices.getLocationGlobal;

import tyrantgit.explosionfield.ExplosionField;

public class MainActivity extends AppCompatActivity {

    private ExplosionField mExplosionField;

    @BindView(R.id.fromEditText)
    EditText fromEditText;
    @BindView(R.id.toEditText)
    EditText toEditText;


    final int PLACE_AUTOCOMPLETE_REQUEST_CODE_FROM = 2;
    final int PLACE_AUTOCOMPLETE_REQUEST_CODE_TO = 3;

    Place fromPlace = null;
    Place toPlace = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mExplosionField = ExplosionField.attach2Window(this);
        getInitalLocationGlobal(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_dehaze_black_24dp);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add("Title"); //your desired title here
        item.setIcon(R.drawable.coin); //your desired icon here
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new LovelyInfoDialog(MainActivity.this)
                        .setTopColorRes(R.color.colorPrimary)
                        .setTitle("Coming Soon")
                        .setMessage(Html.fromHtml("Points earned can be <b>redeemed</b> for Free Rides, Coupons or Airtime, Discounts etc"))
                        .setIcon(R.drawable.coin)
                        .show();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void triggerAutoComplete(int resultCode) {
        try {
            String query = "";

            if (resultCode == PLACE_AUTOCOMPLETE_REQUEST_CODE_FROM && !fromEditText.getText().toString().toLowerCase().equals("Current Location".toLowerCase())) {
                query = fromEditText.getText().toString();
            }

            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .setBoundsBias(new LatLngBounds(
                                    new LatLng(-1.444861, 36.614063),
                                    new LatLng(-1.008423, 37.078760)))
                            .build(this);
            startActivityForResult(intent, resultCode);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    @OnClick(R.id.fromEditText)
    public void triggerFromAutoComplete(View view) {
        triggerAutoComplete(PLACE_AUTOCOMPLETE_REQUEST_CODE_FROM);
    }

    @OnClick({R.id.award, R.id.trophy, R.id.coins})
    public void imageClick(View v) {
        mExplosionField.explode(v);
        v.animate().setDuration(150).setStartDelay(150).scaleX(1.0f).scaleY(1.0f).alpha(1.0f).start();

        final Handler h = new Handler();

        h.postDelayed(new Runnable() {
            private long time = 0;
            int journeyCount = 1;

            @Override
            public void run() {
                h.removeCallbacks(this);
            }
        }, 200); // 1 second delay (takes millis)

        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.toEditText)
    public void triggerToAutoComplete(View view) {
        triggerAutoComplete(PLACE_AUTOCOMPLETE_REQUEST_CODE_TO);
    }

    @OnClick(R.id.goButton)
    public void gogogo(View view) {
        if (!fromEditText.getText().toString().toLowerCase().equals("Current Location".toLowerCase()) && fromPlace == null) {
            Toast.makeText(this, "You need to enter the location to start from", Toast.LENGTH_LONG).show();
            return;
        }

        if (toPlace == null) {
            Toast.makeText(this, "You need to enter your destination", Toast.LENGTH_LONG).show();
            return;
        }

        if (fromPlace == null) {
            ILocationCallback callback = new ILocationCallback() {
                @Override
                public void onSuccess(final Location location) {
                    fromPlace = new Place() {
                        @Override
                        public String getId() {
                            return toPlace.getId();
                        }

                        @Override
                        public List<Integer> getPlaceTypes() {
                            return toPlace.getPlaceTypes();
                        }

                        @Override
                        public CharSequence getAddress() {
                            return toPlace.getAddress();
                        }

                        @Override
                        public Locale getLocale() {
                            return toPlace.getLocale();
                        }

                        @Override
                        public CharSequence getName() {
                            return "Current Location";
                        }

                        @Override
                        public LatLng getLatLng() {
                            return new LatLng(location.getLatitude(), location.getLongitude());
                        }

                        @Override
                        public LatLngBounds getViewport() {
                            return toPlace.getViewport();
                        }

                        @Override
                        public Uri getWebsiteUri() {
                            return toPlace.getWebsiteUri();
                        }

                        @Override
                        public CharSequence getPhoneNumber() {
                            return toPlace.getPhoneNumber();
                        }

                        @Override
                        public float getRating() {
                            return toPlace.getRating();
                        }

                        @Override
                        public int getPriceLevel() {
                            return toPlace.getPriceLevel();
                        }

                        @Override
                        public CharSequence getAttributions() {
                            return toPlace.getAttributions();
                        }

                        @Override
                        public Place freeze() {
                            return toPlace.freeze();
                        }

                        @Override
                        public boolean isDataValid() {
                            return toPlace.isDataValid();
                        }
                    };
                    generateRoute(fromPlace, toPlace);
                }
            };

            getLocationGlobal(this, callback);
        } else
            generateRoute(fromPlace, toPlace);
    }

    private void generateRoute(Place fromPlace, Place toPlace) {
        String journeyJson = "{   \n" +
                "    \"id\": \"journeyName\",\n" +
                "    \"steps\": [{\n" +
                "        \"type\": \"matatu\",\n" +
                "        \"start\": \"[-1.300615, 36.787747]\",\n" +
                "        \"end\": \"[-1.300615, 36.787747]\",\n" +
                "        \"line\": \"WIMTLineID\",\n" +
                "        \"distance\": \"distanceForEntireLeg\",\n" +
                "        \"instructions\": \"Take #15 at the Kenyatta Bus Stop\",\n" +
                "        \"waypoints\": \"[[stopid_1, stopid_2, ...]]\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"type\": \"walk\",\n" +
                "        \"start\": \"[-1.298395, 36.788047]\",\n" +
                "        \"end\": \"[-1.298395, 36.788047]\",\n" +
                "        \"distance\": \"distanceForEntireLeg\",\n" +
                "        \"instructions\": \"Walk to Pizza In\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"type\": \"uber\",\n" +
                "        \"start\": \"[-1.299897, 36.783949]\",\n" +
                "        \"end\": \"[-1.299897, 36.783949]\",\n" +
                "        \"distance\": \"distanceForEntireLeg\",\n" +
                "        \"instructions\": \"Take an Uber the rest of the way\",\n" +
                "        \"uberTripId\": \"tripID\"\n" +
                "    }]\n" +
                "}\n";

        Intent intent = new Intent(this, JourneyActivity.class);
        intent.putExtra("fromPlaceLong", fromPlace.getLatLng().longitude);
        intent.putExtra("fromPlaceLat", fromPlace.getLatLng().latitude);
        intent.putExtra("toPlaceLong", toPlace.getLatLng().longitude);
        intent.putExtra("toPlaceLat", toPlace.getLatLng().latitude);
        intent.putExtra("fromPlaceName", fromPlace.getName());
        intent.putExtra("toPlaceName", toPlace.getName());
        intent.putExtra("journey", journeyJson);

        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                getInitalLocationGlobal(this);
                break;
            case PLACE_AUTOCOMPLETE_REQUEST_CODE_FROM:
                if (resultCode == RESULT_OK) {
                    Place place = PlaceAutocomplete.getPlace(this, data);
                    if (place != null) {
                        fromEditText.setText(place.getName());
                        fromPlace = place;
                    }
                }
                break;
            case PLACE_AUTOCOMPLETE_REQUEST_CODE_TO:
                if (resultCode == RESULT_OK) {
                    Place place1 = PlaceAutocomplete.getPlace(this, data);

                    if (place1 != null) {
                        toEditText.setText(place1.getName());
                        toPlace = place1;
                    }
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}
