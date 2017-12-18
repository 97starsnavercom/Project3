package com.hansung.android.project3;

        import android.Manifest;
        import android.content.pm.PackageManager;
        import android.location.Location;
        import android.support.v4.app.ActivityCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.widget.Toast;

        import com.google.android.gms.location.FusedLocationProviderClient;
        import com.google.android.gms.location.LocationServices;
        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.MarkerOptions;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;

public class Restaurant_Map extends AppCompatActivity  implements OnMapReadyCallback {

    final int REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION=0;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mCurrentLocation;
    double latitude = 0.0;
    double longitude = 0.0;
    GoogleMap mGoogleMap = null;
    final String TAG = "location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant__map);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        if (!checkLocationPermissions()) {
            requestLocationPermissions(REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION);
        } else
            getLastLocation();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private boolean checkLocationPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.onekm:
                item.setChecked(true);
                return true;
            case R.id.twokm:
                item.setChecked(true);
                return true;
            case R.id.threekm:
                item.setChecked(true);
                return true;
            case R.id.map:
                getLastLocation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }



    private void requestLocationPermissions(int requestCode) {
        ActivityCompat.requestPermissions(
                Restaurant_Map.this,            // MainActivity 액티비티의 객체 인스턴스를 나타냄
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},        // 요청할 권한 목록을 설정한 String 배열
                requestCode    // 사용자 정의 int 상수. 권한 요청 결과를 받을 때
        );
    }
    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        Log.i(TAG,"getLastLocation operate");

        Task task = mFusedLocationClient.getLastLocation();       // Task<Location> 객체 반환
        task.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Log.i(TAG,"onSuccess operate");
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    Log.i(TAG,"loaction is not null");
                    mCurrentLocation = location;
                    latitude=mCurrentLocation.getLatitude();
                    longitude=mCurrentLocation.getLongitude();
                    LatLng local = new LatLng(latitude,longitude);

                    if(mGoogleMap!=null){
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local,15));
                        Log.i(TAG,"Good");
                    }
                    //updateUI();
                }
            }
        });
    }

    public void onMapReady(GoogleMap googleMap) {
       mGoogleMap=googleMap;
        Log.i(TAG,"onMapReady operate");

    }

}
