package id.co.endang.mymovie3.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

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

    protected void showDetailMovie(Movie data, Context context) {
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE,data);
        startActivity(intent);
    }

}