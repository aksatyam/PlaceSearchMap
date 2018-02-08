package com.ashish.satyam.placesearchmaps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

public class SearchActivity extends AppCompatActivity {
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
         autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
             @Override
             public void onPlaceSelected(Place place) {
                 Toast.makeText(SearchActivity.this, ""+place.getAddress(), Toast.LENGTH_SHORT).show();
                 Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber() + place.getLatLng().latitude);

                 Intent intent = new Intent(SearchActivity.this, GoogleMapActivity.class);
                 intent.putExtra("latitude",place.getLatLng().latitude);
                 intent.putExtra("longitute",place.getLatLng().longitude);
                 intent.putExtra("name",place.getName());
                 intent.putExtra("address",place.getAddress());
                 startActivity(intent);
             }

             @Override
             public void onError(Status status) {

             }
         });
    }

    public void findPlace(View view) {
        try {
            Toast.makeText(this, "First", Toast.LENGTH_SHORT).show();
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(this);
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(this, "Second", Toast.LENGTH_SHORT).show();
        if (requestCode == 1) {
            Log.d("REsponse",requestCode+""+resultCode);
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber() + place.getLatLng().latitude);

                Intent intent = new Intent(SearchActivity.this, GoogleMapActivity.class);
                intent.putExtra("latitude",place.getLatLng().latitude);
                intent.putExtra("longitute",place.getLatLng().longitude);
                intent.putExtra("name",place.getName());
                intent.putExtra("address",place.getAddress());
                startActivity(intent);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
