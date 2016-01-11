package pom2.poly.com.trythemoviedbapi;

import retrofit.Call;
import retrofit.http.POST;

/**
 * Created by User on 11/1/2016.
 */
public interface APIService {
//http://api.themoviedb.org/3/configuration?api_key=db9db09d5d4c08b057a2aefbeea458b0
    //http://api.themoviedb.org/3/movie/popular?api_key=db9db09d5d4c08b057a2aefbeea458b0&page=100

    @POST("/configuration?api_key=db9db09d5d4c08b057a2aefbeea458b0")
    Call<Config> loadconfig();

}