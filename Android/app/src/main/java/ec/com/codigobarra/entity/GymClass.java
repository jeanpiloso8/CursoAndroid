package ec.com.codigobarra.entity;

/**
 * Created by Eddy on 2018/3/3.
 */

public class GymClass {

    public int number;

    public double lat;

    public double lng;

    public String name;

    public String open_now;

    public String photo_reference;

    public String place_id;

    public double rating;

    public String vicinity;

    public int getNumber() { return number; }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getName() {
        return name;
    }

    public String getOpen_now() { return open_now; }

    public String getPhoto_reference() { return photo_reference; }

    public String getPlace_id() {
        return place_id;
    }

    public double getRating() {
        return rating;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setNumber(int number) { this.number = number; }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOpen_now(String open_now) { this.open_now = open_now; }

    public void setPhoto_reference(String photo_reference) { this.photo_reference = photo_reference; }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public void setRating(double rating) { this.rating = rating; }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
}
