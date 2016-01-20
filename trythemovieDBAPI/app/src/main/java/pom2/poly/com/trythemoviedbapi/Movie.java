package pom2.poly.com.trythemoviedbapi;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 14/12/2015.
 */
public class Movie implements Parcelable {
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    private String title;
    private String poster_path;
    private String backdrop_path;
    private String overview;
    private String rage;
    private String r_date;
    private String poster_size;
    private String backdrop_size;
    private String base_url;

    public Movie(String poster_size, String base_url, String backdrop_size) {
        this.poster_size = poster_size;
        this.base_url = base_url;
        this.backdrop_size = backdrop_size;


    }

    public Movie(Parcel in) {
        Bundle value = in.readBundle();
        this.title = value.getString("title");
        this.poster_path = value.getString("poster_path");
        this.backdrop_path = value.getString("backdrop_path");
        this.overview = value.getString("overview");
        this.rage = value.getString("rage");
        this.r_date = value.getString("r_date");
        this.poster_size = value.getString("poster_size");
        this.backdrop_size = value.getString("backdrop_size");
        this.base_url = value.getString("base_url");

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putString("title", this.title);
        bundle.putString("poster_path", this.poster_path);
        bundle.putString("backdrop_path", this.backdrop_path);
        bundle.putString("overview", this.overview);
        bundle.putString("rage", this.rage);
        bundle.putString("r_date", this.r_date);
        bundle.putString("poster_size", this.poster_size);
        bundle.putString("backdrop_size", this.backdrop_size);
        bundle.putString("base_url", this.base_url);
        dest.writeBundle(bundle);

    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String path) {
        this.poster_path = base_url + poster_size + path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String path) {
        this.backdrop_path = base_url + backdrop_size + path;
    }

    public String getR_date() {
        return r_date;
    }

    public void setR_date(String r_date) {
        this.r_date = r_date;
    }

    public String getRage() {
        return rage;
    }

    public void setRage(String rage) {
        this.rage = rage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
