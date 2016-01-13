package pom2.poly.com.trythemoviedbapi;

import java.util.ArrayList;
import java.util.List;

import pom2.poly.com.trythemoviedbapi.MovieAPI.Config;
import pom2.poly.com.trythemoviedbapi.MovieAPI.Result;
import pom2.poly.com.trythemoviedbapi.MovieAPI.Results;

/**
 * Created by User on 12/1/2016.
 */

/*the input is the Results object and the config object
the output is the Array of Movie objecy*/
public class MovieFactory {
    static Movie[] startMakeMovieArray(Config config, Results results) {
        ArrayList<Movie> arListofMovie=null;
        if (config == null || results == null) {
            return null;
        } else {
            String poster_size = config.getImages().getPosterSizes().get(4);
            String base_url = config.getImages().getBaseUrl();
            String backdrop_size=config.getImages().getBackdropSizes().get(1);
            List<Result> listofResult = results.getResults();
            arListofMovie=new ArrayList<>();
            for (Result r: listofResult) {
                Movie m=new Movie(poster_size,base_url,backdrop_size);
                m.setBackdrop_path(r.getBackdropPath());
                m.setTitle(r.getTitle());
                m.setRage(r.getVoteAverage().toString());
                m.setOverview(r.getOverview());
                m.setR_date(r.getReleaseDate());
                m.setPoster_path(r.getPosterPath());
                arListofMovie.add(m);
            }
        }
        Movie[] movieArr=new Movie[arListofMovie.size()];
        arListofMovie.toArray(movieArr);

        return movieArr;
    }
}
