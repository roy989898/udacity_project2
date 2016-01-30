package pom2.poly.com.trythemoviedbapi.MovieAPI;


import pom2.poly.com.trythemoviedbapi.MovieAPI.MovieIDResult.MovieIdResult;
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

    @GET("/3/configuration?api_key=db9db09d5d4c08b057a2aefbeea458b0")
    Call<Config> loadconfig();
    @GET("3/movie/popular?api_key=db9db09d5d4c08b057a2aefbeea458b0")
    Call<Results> loadPopMovie();
    @GET("/3/movie/top_rated?api_key=db9db09d5d4c08b057a2aefbeea458b0")
    Call<Results> loadTopMovie();

    //http://api.themoviedb.org/3/movie/244786?api_key=db9db09d5d4c08b057a2aefbeea458b0
    @GET("/3/movie/{m_id}?api_key=db9db09d5d4c08b057a2aefbeea458b0")
    Call<MovieIdResult> LoadSingleFavouriteMovie(@Path("m_id") String m_id);

}