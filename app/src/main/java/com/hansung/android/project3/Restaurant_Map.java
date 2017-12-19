package com.hansung.android.project3;

        import android.Manifest;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.location.Address;
        import android.location.Geocoder;
        import android.location.Location;
        import android.support.v4.app.ActivityCompat;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import com.google.android.gms.location.FusedLocationProviderClient;
        import com.google.android.gms.location.LocationServices;
        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;

        import java.io.IOException;
        import java.util.List;
        import java.util.Locale;

public class Restaurant_Map extends AppCompatActivity  implements OnMapReadyCallback {

    final int REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION=0;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mCurrentLocation;
    double latitude = 0.0;
    double longitude = 0.0;
    GoogleMap mGoogleMap = null;
    final String TAG = "location";
    Button button;
    EditText editText;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_map);
        button = (Button)findViewById(R.id.button_local);
        editText = (EditText)findViewById(R.id.edit_text_local);
        textView = (TextView)findViewById(R.id.result_local);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        if (!checkLocationPermissions()) {
            requestLocationPermissions(REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION);
        } else
            getLastLocation();
        Log.i(TAG,"onCreate getLastLocation operate");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.KOREA);
                    List<Address> addresses = geocoder.getFromLocationName(editText.getText().toString(),1);
                    if (addresses.size() >0) {
                        Address bestResult = (Address) addresses.get(0);

                        textView.setText(String.format("[ %s , %s ]",
                                bestResult.getLatitude(),
                                bestResult.getLongitude()));
                        LatLng local2 = new LatLng(bestResult.getLatitude(), bestResult.getLongitude());

                        mGoogleMap.addMarker(new MarkerOptions().position(local2));
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(local2));
                        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {



                                AlertDialog.Builder dialog = new AlertDialog.Builder(
                                        Restaurant_Map.this);




                                // 제목셋팅
                                dialog.setTitle("맛집 등록");

                                // AlertDialog 셋팅
                                dialog
                                        .setMessage("새로운 맛집으로 등록하시겠습니까?")
                                        .setCancelable(false)
                                        .setPositiveButton("예",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(
                                                            DialogInterface dialog, int id) {
                                                        Intent intent = new Intent(getApplicationContext(),Registration_Restaurant.class);
                                                        intent.putExtra("Restaurant Loacation", editText.getText().toString());
                                                        startActivity(intent);

                                                    }
                                                })
                                        .setNegativeButton("아니오",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(
                                                            DialogInterface dialog, int id) {
                                                        // 다이얼로그를 취소한다
                                                        dialog.cancel();
                                                    }
                                                });

                                AlertDialog alert11 = dialog.create();
                                alert11.show();

                                Log.i(TAG,"markerClicke operate");



                                return false;
                            }
                        });


                    }
                } catch (IOException e) {
                    Log.e(getClass().toString(),"Failed in using Geocoder.", e);
                    return;
                }
            }
        });





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
                mGoogleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                Log.i(TAG,"Zoom to 15");
                return true;
            case R.id.twokm:
                item.setChecked(true);
                mGoogleMap.moveCamera(CameraUpdateFactory.zoomTo(14));
                Log.i(TAG,"Zoom to 14");
                return true;
            case R.id.threekm:
                item.setChecked(true);
                mGoogleMap.moveCamera(CameraUpdateFactory.zoomTo(13));
                Log.i(TAG,"Zoom to 13");
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
                if(location==null){
                    Log.i(TAG,"location is null");
                    LatLng local = new LatLng(37.5817891, 127.009854);

                    if(mGoogleMap!=null){
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local,15));
                        Log.i(TAG,"Good");
                    }



                }
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
