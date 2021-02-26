package ec.com.codigobarra.utils;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ec.com.codigobarra.entity.GymBean;
import ec.com.codigobarra.entity.GymClass;


public class Utility {

    public static List<GymClass> handleGoogleResponse(String response) {

        List<GymClass> list = new ArrayList<>();

        try {
            Gson gson = new Gson();

            GymBean gym = gson.fromJson(response, GymBean.class);

            for (int i = 0; i < gym.getResults().size(); i++) {
                GymClass gymClass = new GymClass();

                gymClass.setNumber(i);
                gymClass.setLat(gym.getResults().get(i).getGeometry().getLocation().getLat());
                gymClass.setLng(gym.getResults().get(i).getGeometry().getLocation().getLng());
                gymClass.setPlace_id(gym.getResults().get(i).getPlace_id());
                gymClass.setName(gym.getResults().get(i).getName());
                if (gym.getResults().get(i).getOpening_hours()!=null) {
                    gymClass.setOpen_now(gym.getResults().get(i).getOpening_hours().isOpen_now());
                } else {
                    gymClass.setOpen_now("No data");
                }

                if (gym.getResults().get(i).getPhotos() != null) {
                    gymClass.setPhoto_reference(gym.getResults().get(i).getPhotos().get(0).getPhoto_reference());
                } else {
                    gymClass.setPhoto_reference("");
                }

                gymClass.setRating(gym.getResults().get(i).getRating());
                gymClass.setVicinity(gym.getResults().get(i).getVicinity());

                list.add(gymClass);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}
