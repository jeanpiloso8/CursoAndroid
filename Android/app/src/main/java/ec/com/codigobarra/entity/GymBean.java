package ec.com.codigobarra.entity;

import java.util.List;

/**
 * Created by S.YC on 2018/3/11.
 */

public class GymBean {

    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {

        private GeometryBean geometry;
        private String name;
        private OpeningHoursBean opening_hours;
        private String place_id;
        private double rating;
        private String vicinity;
        private List<PhotosBean> photos;

        public GeometryBean getGeometry() {
            return geometry;
        }

        public void setGeometry(GeometryBean geometry) {
            this.geometry = geometry;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public OpeningHoursBean getOpening_hours() {
            return opening_hours;
        }

        public void setOpening_hours(OpeningHoursBean opening_hours) {
            this.opening_hours = opening_hours;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public String getVicinity() {
            return vicinity;
        }

        public void setVicinity(String vicinity) {
            this.vicinity = vicinity;
        }

        public List<PhotosBean> getPhotos() {
            return photos;
        }

        public void setPhotos(List<PhotosBean> photos) {
            this.photos = photos;
        }

        public static class GeometryBean {

            private LocationBean location;

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public static class LocationBean {

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }
        }

        public static class OpeningHoursBean {

            private String open_now;

            public String isOpen_now() {
                return open_now;
            }

            public void setOpen_now(String open_now) {
                this.open_now = open_now;
            }
        }

        public static class PhotosBean {

            private String photo_reference;

            public String getPhoto_reference() {
                return photo_reference;
            }

            public void setPhoto_reference(String photo_reference) {
                this.photo_reference = photo_reference;
            }
        }
    }
}
