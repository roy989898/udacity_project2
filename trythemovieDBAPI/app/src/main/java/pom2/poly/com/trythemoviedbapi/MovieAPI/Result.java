package pom2.poly.com.trythemoviedbapi.MovieAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 12/1/2016.
 */
public class Result {

    private String poster_path;
    private Boolean adult;
    private String overview;
    private String release_date;
    private List<Integer> genre_ids = new ArrayList<Integer>();
    private Integer id;
    private String original_title;
    private String original_language;
    private String title;
    private String backdrop_path;
    private Double popularity;
    private Integer vote_count;
    private Boolean video;
    private Double vote_average;

    /**
     *
     * @return
     * The posterPath
     */
    public String getPosterPath() {
        return poster_path;
    }

    /**
     *
     * @param posterPath
     * The poster_path
     */
    public void setPosterPath(String posterPath) {
        this.poster_path = posterPath;
    }

    /**
     *
     * @return
     * The adult
     */
    public Boolean getAdult() {
        return adult;
    }

    /**
     *
     * @param adult
     * The adult
     */
    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    /**
     *
     * @return
     * The overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     *
     * @param overview
     * The overview
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     *
     * @return
     * The releaseDate
     */
    public String getReleaseDate() {
        return release_date;
    }

    /**
     *
     * @param releaseDate
     * The release_date
     */
    public void setReleaseDate(String releaseDate) {
        this.release_date = releaseDate;
    }

    /**
     *
     * @return
     * The genreIds
     */
    public List<Integer> getGenreIds() {
        return genre_ids;
    }

    /**
     *
     * @param genreIds
     * The genre_ids
     */
    public void setGenreIds(List<Integer> genreIds) {
        this.genre_ids = genreIds;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The originalTitle
     */
    public String getOriginalTitle() {
        return original_title;
    }

    /**
     *
     * @param originalTitle
     * The original_title
     */
    public void setOriginalTitle(String originalTitle) {
        this.original_title = originalTitle;
    }

    /**
     *
     * @return
     * The originalLanguage
     */
    public String getOriginalLanguage() {
        return original_language;
    }

    /**
     *
     * @param originalLanguage
     * The original_language
     */
    public void setOriginalLanguage(String originalLanguage) {
        this.original_language = originalLanguage;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The backdropPath
     */
    public String getBackdropPath() {
        return backdrop_path;
    }

    /**
     *
     * @param backdropPath
     * The backdrop_path
     */
    public void setBackdropPath(String backdropPath) {
        this.backdrop_path = backdropPath;
    }

    /**
     *
     * @return
     * The popularity
     */
    public Double getPopularity() {
        return popularity;
    }

    /**
     *
     * @param popularity
     * The popularity
     */
    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    /**
     *
     * @return
     * The voteCount
     */
    public Integer getVoteCount() {
        return vote_count;
    }

    /**
     *
     * @param voteCount
     * The vote_count
     */
    public void setVoteCount(Integer voteCount) {
        this.vote_count = voteCount;
    }

    /**
     *
     * @return
     * The video
     */
    public Boolean getVideo() {
        return video;
    }

    /**
     *
     * @param video
     * The video
     */
    public void setVideo(Boolean video) {
        this.video = video;
    }

    /**
     *
     * @return
     * The voteAverage
     */
    public Double getVoteAverage() {
        return vote_average;
    }

    /**
     *
     * @param voteAverage
     * The vote_average
     */
    public void setVoteAverage(Double voteAverage) {
        this.vote_average = voteAverage;
    }

}