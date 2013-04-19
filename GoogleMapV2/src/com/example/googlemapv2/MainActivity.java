package com.example.googlemapv2;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements
		OnMyLocationChangeListener {

	GoogleMap map;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/*
		 * FragmentManager myFragmentManager = getFragmentManager(); MapFragment
		 * myMapFragment = (MapFragment) myFragmentManager
		 * .findFragmentById(R.id.map); map = myMapFragment.getMap();
		 * map.setMyLocationEnabled(true);
		 * 
		 * // map.setMapType(GoogleMap.MAP_TYPE_HYBRID); //
		 * map.setMapType(GoogleMap.MAP_TYPE_NORMAL); //
		 * map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		 * map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		 */

		// Getting Google Play availability status
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());

		// Showing status
		if (status != ConnectionResult.SUCCESS) { // Google Play Services are
													// not available
			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();

		} else { // Google Play Services are available

			// Getting reference to the SupportMapFragment of activity_main.xml

			FragmentManager myFragmentManager = getFragmentManager();
			MapFragment myMapFragment = (MapFragment) myFragmentManager
					.findFragmentById(R.id.map);
			/*
			 * SupportMapFragment fm =
			 * (SupportMapFragment)findFragmentById(R.id.map);
			 */

			// Getting GoogleMap object from the fragment
			map = myMapFragment.getMap();

			// Enabling MyLocation Layer of Google Map
			map.setMyLocationEnabled(true);

			// Setting event handler for location change
			map.setOnMyLocationChangeListener(this);
			
//			04-16 12:36:17.559: I/latitude1_check(3180): 28.6646842 77.3732167

			
			map.addMarker(new MarkerOptions()
	        .position(new LatLng(28.6646842, 77.3732167))
	        .title("my home").icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_red)));
			
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onMyLocationChange(Location location) {
		TextView tvLocation = (TextView) findViewById(R.id.tv_location);

		// Getting latitude of the current location
		double latitude = location.getLatitude();

		// Getting longitude of the current location
		double longitude = location.getLongitude();

		// Creating a LatLng object for the current location
		LatLng latLng = new LatLng(latitude, longitude);

		// Showing the current location in Google Map
		map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

		// Zoom in the Google Map
		map.animateCamera(CameraUpdateFactory.zoomTo(50));

		// Setting latitude and longitude in the TextView tv_location
		tvLocation.setText("Latitude:" + latitude + ", Longitude:" + longitude);
		Log.i("latitude", latitude + " " + longitude);

	}
}
