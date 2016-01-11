package pom2.poly.com.trythemoviedbapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 11/1/2016.
 */
public class Config {

    private Images images;
    private List<String> changeKeys = new ArrayList<String>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The images
     */
    public Images getImages() {
        return images;
    }

    /**
     *
     * @param images
     * The images
     */
    public void setImages(Images images) {
        this.images = images;
    }

    /**
     *
     * @return
     * The changeKeys
     */
    public List<String> getChangeKeys() {
        return changeKeys;
    }

    /**
     *
     * @param changeKeys
     * The change_keys
     */
    public void setChangeKeys(List<String> changeKeys) {
        this.changeKeys = changeKeys;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
     class Images {

        private String baseUrl;
        private String secureBaseUrl;
        private List<String> backdropSizes = new ArrayList<String>();
        private List<String> logoSizes = new ArrayList<String>();
        private List<String> posterSizes = new ArrayList<String>();
        private List<String> profileSizes = new ArrayList<String>();
        private List<String> stillSizes = new ArrayList<String>();
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         *
         * @return
         * The baseUrl
         */
        public String getBaseUrl() {
            return baseUrl;
        }

        /**
         *
         * @param baseUrl
         * The base_url
         */
        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        /**
         *
         * @return
         * The secureBaseUrl
         */
        public String getSecureBaseUrl() {
            return secureBaseUrl;
        }

        /**
         *
         * @param secureBaseUrl
         * The secure_base_url
         */
        public void setSecureBaseUrl(String secureBaseUrl) {
            this.secureBaseUrl = secureBaseUrl;
        }

        /**
         *
         * @return
         * The backdropSizes
         */
        public List<String> getBackdropSizes() {
            return backdropSizes;
        }

        /**
         *
         * @param backdropSizes
         * The backdrop_sizes
         */
        public void setBackdropSizes(List<String> backdropSizes) {
            this.backdropSizes = backdropSizes;
        }

        /**
         *
         * @return
         * The logoSizes
         */
        public List<String> getLogoSizes() {
            return logoSizes;
        }

        /**
         *
         * @param logoSizes
         * The logo_sizes
         */
        public void setLogoSizes(List<String> logoSizes) {
            this.logoSizes = logoSizes;
        }

        /**
         *
         * @return
         * The posterSizes
         */
        public List<String> getPosterSizes() {
            return posterSizes;
        }

        /**
         *
         * @param posterSizes
         * The poster_sizes
         */
        public void setPosterSizes(List<String> posterSizes) {
            this.posterSizes = posterSizes;
        }

        /**
         *
         * @return
         * The profileSizes
         */
        public List<String> getProfileSizes() {
            return profileSizes;
        }

        /**
         *
         * @param profileSizes
         * The profile_sizes
         */
        public void setProfileSizes(List<String> profileSizes) {
            this.profileSizes = profileSizes;
        }

        /**
         *
         * @return
         * The stillSizes
         */
        public List<String> getStillSizes() {
            return stillSizes;
        }

        /**
         *
         * @param stillSizes
         * The still_sizes
         */
        public void setStillSizes(List<String> stillSizes) {
            this.stillSizes = stillSizes;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

}