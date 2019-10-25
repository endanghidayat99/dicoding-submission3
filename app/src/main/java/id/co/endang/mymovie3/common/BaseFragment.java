package id.co.endang.mymovie3.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import id.co.endang.mymovie3.db.model.MovieFavorite;
import id.co.endang.mymovie3.model.Movie;
import id.co.endang.mymovie3.rest.RESTMovie;
import id.co.endang.mymovie3.view.DetailMovieActivity;


public class BaseFragment extends Fragment {
    public static final String STATE_MOVIES = "state_movies";

    public RESTMovie getRESTMovies() {
        return new RESTMovie();
    }

    public String getLanguage(SharedPreferences sharedPreferences) {
        return sharedPreferences.getString("language", "en");
    }

    protected void showDetailMovie(Movie data, Context context,int type,boolean isFavorite) {
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE,data);
        intent.putExtra(DetailMovieActivity.EXTRA_STATUS,isFavorite);
        intent.putExtra(DetailMovieActivity.EXTRA_TYPE,type);
        startActivity(intent);
    }

    protected ArrayList<Movie> getListMovies(List<MovieFavorite> movieFavorites){
        ArrayList<Movie> movies = new ArrayList<>();
        for (MovieFavorite fav : movieFavorites) {
            Movie movie = new Movie();
            movie.setId(fav.getId_tmb());
            movie.setTitle(fav.getTitle());
            movie.setOverview(fav.getOverview());
            movie.setBackdropPath(fav.getBackdropPath());
            movie.setReleaseDate(fav.getReleaseDate());
            movie.setPosterPath(fav.getPosterPath());
            movie.setVoteAverage(fav.getVoteAverage());
            movies.add(movie);
        }
        return movies;
    }

}