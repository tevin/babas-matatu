package babasmatatu.hackerthon.com.babasmatatu;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static babasmatatu.hackerthon.com.babasmatatu.MiscServices.getInitalLocationGlobal;
import static babasmatatu.hackerthon.com.babasmatatu.MiscServices.getLocationGlobal;
import static babasmatatu.hackerthon.com.babasmatatu.MiscServices.hideSoftKeyBoard;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fromEditText)
    EditText fromEditText;
    @BindView(R.id.toEditText)
    EditText toEditText;

    final int PLACE_AUTOCOMPLETE_REQUEST_CODE_FROM = 2;
    final int PLACE_AUTOCOMPLETE_REQUEST_CODE_TO = 3;

    Place fromPlace;
    Place toPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getInitalLocationGlobal(this);
    }

    public void triggerAutoComplete(int resultCode){
        try {
            String query = "";

            if (resultCode == PLACE_AUTOCOMPLETE_REQUEST_CODE_FROM && !fromEditText.getText().toString().toLowerCase().equals("Current Location".toLowerCase())){
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

    @OnClick(R.id.toEditText)
    public void triggerToAutoComplete(View view) {
        triggerAutoComplete(PLACE_AUTOCOMPLETE_REQUEST_CODE_TO);
    }

    @OnClick(R.id.goButton)
    public void gogogo(View view) {
        if (!fromEditText.getText().toString().toLowerCase().equals("Current Location".toLowerCase()) && fromPlace == null){
            Toast.makeText(this, "You need to enter the location to start from", Toast.LENGTH_LONG).show();
            return;
        }

        if (toPlace == null){
            Toast.makeText(this, "You need to enter your destination", Toast.LENGTH_LONG).show();
            return;
        }

        if (fromPlace == null){
            ILocationCallback callback = new ILocationCallback() {
                @Override
                public void onSuccess(final Location location) {
                    fromPlace = new Place() {
                        @Override
                        public String getId() {
                            return null;
                        }

                        @Override
                        public List<Integer> getPlaceTypes() {
                            return null;
                        }

                        @Override
                        public CharSequence getAddress() {
                            return null;
                        }

                        @Override
                        public Locale getLocale() {
                            return null;
                        }

                        @Override
                        public CharSequence getName() {
                            return null;
                        }

                        @Override
                        public LatLng getLatLng() {
                            return new LatLng(location.getLatitude(), location.getLongitude());
                        }

                        @Override
                        public LatLngBounds getViewport() {
                            return null;
                        }

                        @Override
                        public Uri getWebsiteUri() {
                            return null;
                        }

                        @Override
                        public CharSequence getPhoneNumber() {
                            return null;
                        }

                        @Override
                        public float getRating() {
                            return 0;
                        }

                        @Override
                        public int getPriceLevel() {
                            return 0;
                        }

                        @Override
                        public CharSequence getAttributions() {
                            return null;
                        }

                        @Override
                        public Place freeze() {
                            return null;
                        }

                        @Override
                        public boolean isDataValid() {
                            return false;
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
        
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
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
