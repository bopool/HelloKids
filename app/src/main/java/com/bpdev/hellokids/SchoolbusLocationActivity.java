package com.bpdev.hellokids;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bpdev.hellokids.adapter.BusAdapter;
import com.bpdev.hellokids.api.BusApi;
import com.bpdev.hellokids.api.NetworkClient;
import com.bpdev.hellokids.model.BusDailyRecord;
import com.bpdev.hellokids.model.BusDailyRecordList;
import com.bpdev.hellokids.model.Location;
import com.bpdev.hellokids.model.LocationList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SchoolbusLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    double latitude;
    double longitude;

    private GoogleMap map;

    ArrayList<Location> busLoaction = new ArrayList<>();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolbus_location);



        // 메인 파트

        String strId = getIntent().getStringExtra("strId");
        int id = Integer.parseInt(strId);

        Retrofit retrofit = NetworkClient.getRetrofitClient(SchoolbusLocationActivity.this);

        BusApi api = retrofit.create(BusApi.class);

        Call<LocationList> call = api.busLocation(id);
        call.enqueue(new Callback<LocationList>() {
            @Override
            public void onResponse(Call<LocationList> call, Response<LocationList> response) {
                if(response.isSuccessful()){
                    LocationList myLocation = response.body();

                    busLoaction.addAll( myLocation.getItems() );

                    Log.i("myLocation", "위도 : "+busLoaction.get(0).getLat());
                    Log.i("myLocation", "경도 : "+busLoaction.get(0).getLng());

                    latitude = busLoaction.get(0).getLat();
                    longitude = busLoaction.get(0).getLng();

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(SchoolbusLocationActivity.this);

                }

                else{

                }
            }

            @Override
            public void onFailure(Call<LocationList> call, Throwable t) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        map = googleMap;

        LatLng myLocation = new LatLng(latitude, longitude);

        MarkerOptions options = new MarkerOptions();
        options.position(myLocation)
                .title("차량 위치");
               // .snippet("인솔 교사의 위치가 된다");
        map.addMarker(options);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
    }
}