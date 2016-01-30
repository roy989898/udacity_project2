package pom2.poly.com.trythemoviedbapi;

import java.util.ArrayList;
import java.util.List;

import pom2.poly.com.trythemoviedbapi.MovieAPI.Config;
import pom2.poly.com.trythemoviedbapi.MovieAPI.MovieIDResult.MovieIdResult;
import pom2.poly.com.trythemoviedbapi.MovieAPI.Result;
import pom2.poly.com.trythemoviedbapi.MovieAPI.Results;

/**
 * Created by User on 12/1/2016.
 */

/*the input is the Results object and the config object
the output is the Array of Movie objecy*/
public class MovieFactory {
    static Movie[] startMakeMovieArray(Config config, Results results) {
        ArrayList<Movie> arListofMovie = null;
        if (config == null || results == null) {
            return null;
        } else {
            String poster_size = config.getImages().getPosterSizes().get(3);
            String base_url = config.getImages().getBaseUrl();
            String backdrop_size = config.getImages().getBackdropSizes().get(1);
            List<Result> listofResult = results.getResults();
            arListofMovie = new ArrayList<>();
            for (Result r : listofResult) {
                Movie m = new Movie(poster_size, base_url, backdrop_size);
                m.setBackdrop_path(r.getBackdropPath());
                m.setTitle(r.getTitle());
                m.setRage(r.getVoteAverage());
                m.setOverview(r.getOverview());
                m.setR_date(r.getReleaseDate());
                m.setPoster_path(r.getPosterPath());
                m.setM_id(r.getId().toString());
                m.setPopularity(r.getPopularity());
                arListofMovie.add(m);
            }
        }
        Movie[] movieArr = new Movie[arListofMovie.size()];
        arListofMovie.toArray(movieArr);

        return movieArr;
    }


    public static Movie[] startMakeMovieArrayfromMovideResult(Config config, MovieIdResult[] mra) {

        ArrayList<Movie> arListofMovie = new ArrayList<>();
        if (config == null || mra == null) {
            return null;
        } else {
            String poster_size = config.getImages().getPosterSizes().get(3);
            String base_url = config.getImages().getBaseUrl();
            String backdrop_size = config.getImages().getBackdropSizes().get(1);
            for (MovieIdResult mr:mra) {
                Movie m = new Movie(poster_size, base_url, backdrop_size);
                m.setTitle(mr.getTitle());
                m.setPoster_path(mr.getPosterPath());
                m.setBackdrop_path(mr.getBackdropPath());
                m.setOverview(mr.getOverview());
                m.setRage(mr.getVoteAverage());
                m.setR_date(mr.getReleaseDate());
                m.setM_id(mr.getId().toString());
                m.setPopularity(mr.getPopularity());
                arListofMovie.add(m);
            }
            Movie[] movieArr = new Movie[arListofMovie.size()];
            arListofMovie.toArray(movieArr);
            return movieArr;
        }
    }
}
