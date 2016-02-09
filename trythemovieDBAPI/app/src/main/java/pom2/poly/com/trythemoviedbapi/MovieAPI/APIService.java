package pom2.poly.com.trythemoviedbapi.MovieAPI;


import pom2.poly.com.trythemoviedbapi.MovieAPI.MovieIDResult.MovieIdResult;
import pom2.poly.com.trythemoviedbapi.MovieAPI.ReviewResult.ReviewResult;
import pom2.poly.com.trythemoviedbapi.MovieAPI.TrailerResult.TrailerResult;
import pom2.poly.com.trythemoviedbapi.Utility;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by User on 11/1/2016.
 */
public interface APIService {
//http://api.themoviedb.org/3/configuration?api_key=db9db09d5d4c08b057a2aefbeea458b0
    //http://api.themoviedb.org/3/movie/popular?api_key=db9db09d5d4c08b057a2aefbeea458b0&page=100
    //http://api.themoviedb.org/3/movie/top_rated?api_key=db9db09d5d4c08b057a2aefbeea458b0

    @GET("/3/configuration?api_key="+ Utility.MOVIE_API_KEY)
    Call<Config> loadconfig();
    @GET("3/movie/popular?api_key="+Utility.MOVIE_API_KEY)
    Call<Results> loadPopMovie();
    @GET("/3/movie/top_rated?api_key="+Utility.MOVIE_API_KEY)
    Call<Results> loadTopMovie();

    //http://api.themoviedb.org/3/movie/244786?api_key=db9db09d5d4c08b057a2aefbeea458b0
    @GET("/3/movie/{m_id}?api_key="+Utility.MOVIE_API_KEY)
    Call<MovieIdResult> LoadSingleFavouriteMovie(@Path("m_id") String m_id);

    /*get the trailer
    http://api.themoviedb.org/3/movie/49026/videos?api_key=db9db09d5d4c08b057a2aefbeea458b0*/
    @GET("/3/movie/{m_id}/videos?api_key="+Utility.MOVIE_API_KEY)
    Call<TrailerResult> LoadTrailer(@Path("m_id") String m_id);


    /*get the review
    http://api.themoviedb.org/3/movie/49026/reviews?api_key=db9db09d5d4c08b057a2aefbeea458b0*/
    @GET("/3/movie/{m_id}/reviews?api_key="+Utility.MOVIE_API_KEY)
    Call<ReviewResult> LoadReview(@Path("m_id") String m_id);

}