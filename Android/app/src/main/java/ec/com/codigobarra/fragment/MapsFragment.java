package ec.com.codigobarra.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import ec.com.codigobarra.CodigoBarraAplication;
import ec.com.codigobarra.R;
import ec.com.codigobarra.entity.GymClass;
import ec.com.codigobarra.utils.HttpClientUtil;
import ec.com.codigobarra.utils.PermissionUtils;
import ec.com.codigobarra.utils.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnMyLocationButtonClickListener,
GoogleMap.OnMyLocationClickListener,
GoogleMap.OnMarkerClickListener,
GoogleMap.OnInfoWindowClickListener,
GoogleMap.OnMapClickListener{

    LocationManager locationManager;
    Location mLocation;
    String provider;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mLocation != null){
            LatLng cordenadas = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cordenadas, 17));
            requestGyms(mLocation.getLatitude(), mLocation.getLongitude());
        }
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMapClickListener(this);

        enableMyLocation();
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            PermissionUtils.requestPermission(getActivity(), LOCATION_PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            Criteria criteria = new Criteria();
            locationManager = (LocationManager) CodigoBarraAplication.getmInstance().getSystemService(Context.LOCATION_SERVICE);
            List<String> providers = locationManager.getProviders(true);
            for (String provider : providers) {
                Location location = locationManager.getLastKnownLocation(provider);
                if(location == null){
                    continue;
                }
                if (mLocation == null || location.getAccuracy() < mLocation.getAccuracy()){
                    mLocation = location;
                }
            }

            try {
                provider = locationManager.getBestProvider(criteria, true);
                locationManager.requestLocationUpdates(provider, 5000, 1, mListener);
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }


    }

    private void requestGyms(double latitude, double longitude) {
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latitude + "," + longitude +
                "&radius=500&type=pharmacy&key=AIzaSyA9EJnO5l1_984auwYgXZRaDychH78sd28";
        System.out.println(url);
        HttpClientUtil.sendOkHttpGetRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String responseText = response.body().string().trim();

                final List<GymClass> list = Utility.handleGoogleResponse(responseText);

                if (list != null && list.size() != 0) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateMap(list);
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void updateMap(List<GymClass> gymList) {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()));

        circleOptions.radius(500);
        circleOptions.fillColor(0x30000000);
        circleOptions.strokeColor(Color.TRANSPARENT);

        mMap.addCircle(circleOptions);

        for (int i = 0; i < gymList.size(); i++) {
            int number = gymList.get(i).getNumber();
            String name = gymList.get(i).getName();
            double lat = gymList.get(i).getLat();
            double lng = gymList.get(i).getLng();

            mMap.addMarker(new MarkerOptions().snippet(number+"").position(new LatLng(lat, lng)));
        }


    }

    private LocationListener mListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            Location mLastLocation = location;
            mMap.clear();

            LatLng latlngTableta = new LatLng(location.getLatitude(),location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latlngTableta).title(getString(R.string.ubicacion_actual)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            CameraPosition cameraPosition = new CameraPosition.Builder().target(latlngTableta).zoom(13).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            final Circle circle = mMap.addCircle(new CircleOptions().center(latlngTableta).fillColor(R.color.colorPrimary).radius(1000));

            ValueAnimator vAnimator = new ValueAnimator();
            vAnimator.setRepeatCount(ValueAnimator.INFINITE);
            vAnimator.setRepeatMode(ValueAnimator.RESTART);
            vAnimator.setIntValues(0, 100);
            vAnimator.setDuration(1000);
            vAnimator.setEvaluator(new IntEvaluator());
            vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float fraccionAnimacion = animation.getAnimatedFraction();
                    circle.setRadius(fraccionAnimacion*250);
                    circle.setStrokeWidth(1+ fraccionAnimacion * 7);
                }
            });
            vAnimator.start();
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        if(mGoogleApiClient != null && ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setSmallestDisplacement(0.1F); //cada milisegundo
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        {

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {  Toast.makeText(getContext(),getString(R.string.no_internet),Toast.LENGTH_SHORT).show();  }

    @Override
    public void onInfoWindowClick(Marker marker) {    }

    @Override
    public void onMapClick(LatLng latLng) {
        // Creating a marker
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting the position for the marker
        markerOptions.position(latLng);

        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.title(latLng.latitude + " : " + latLng.longitude);

        // Clears the previously touched position
        mMap.clear();

        // Animating to the touched position
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        // Placing a marker on the touched position
        mMap.addMarker(markerOptions);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
    }
}