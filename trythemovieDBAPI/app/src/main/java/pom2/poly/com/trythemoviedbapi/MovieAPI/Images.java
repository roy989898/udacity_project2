package pom2.poly.com.trythemoviedbapi.MovieAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 12/1/2016.
 */
public class Images {

    public String base_url;
    public String secure_base_url;
    public List<String> backdrop_sizes = new ArrayList<String>();
    public List<String> logo_sizes = new ArrayList<String>();
    public List<String> poster_sizes = new ArrayList<String>();
    public List<String> profile_sizes = new ArrayList<String>();
    public List<String> still_sizes = new ArrayList<String>();

    /**
     *
     * @return
     * The baseUrl
     */
    public String getBaseUrl() {
        return base_url;
    }

    /**
     *
     * @param baseUrl
     * The base_url
     */
    public void setBaseUrl(String baseUrl) {
        this.base_url = baseUrl;
    }

    /**
     *
     * @return
     * The secureBaseUrl
     */
    public String getSecureBaseUrl() {
        return secure_base_url;
    }

    /**
     *
     * @param secureBaseUrl
     * The secure_base_url
     */
    public void setSecureBaseUrl(String secureBaseUrl) {
        this.secure_base_url = secureBaseUrl;
    }

    /**
     *
     * @return
     * The backdropSizes
     */
    public List<String> getBackdropSizes() {
        return backdrop_sizes;
    }

    /**
     *
     * @param backdropSizes
     * The backdrop_sizes
     */
    public void setBackdropSizes(List<String> backdropSizes) {
        this.backdrop_sizes = backdropSizes;
    }

    /**
     *
     * @return
     * The logoSizes
     */
    public List<String> getLogoSizes() {
        return logo_sizes;
    }

    /**
     *
     * @param logoSizes
     * The logo_sizes
     */
    public void setLogoSizes(List<String> logoSizes) {
        this.logo_sizes = logoSizes;
    }

    /**
     *
     * @return
     * The posterSizes
     */
    public List<String> getPosterSizes() {
        return poster_sizes;
    }

    /**
     *
     * @param posterSizes
     * The poster_sizes
     */
    public void setPosterSizes(List<String> posterSizes) {
        this.poster_sizes = posterSizes;
    }

    /**
     *
     * @return
     * The profileSizes
     */
    public List<String> getProfileSizes() {
        return profile_sizes;
    }

    /**
     *
     * @param profileSizes
     * The profile_sizes
     */
    public void setProfileSizes(List<String> profileSizes) {
        this.profile_sizes = profileSizes;
    }

    /**
     *
     * @return
     * The stillSizes
     */
    public List<String> getStillSizes() {
        return still_sizes;
    }

    /**
     *
     * @param stillSizes
     * The still_sizes
     */
    public void setStillSizes(List<String> stillSizes) {
        this.still_sizes = stillSizes;
    }

}