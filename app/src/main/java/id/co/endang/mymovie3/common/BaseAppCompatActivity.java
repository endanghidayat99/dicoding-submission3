package id.co.endang.mymovie3.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;

import id.co.endang.mymovie3.model.Movie;
import id.co.endang.mymovie3.rest.RESTMovie;
import id.co.endang.mymovie3.view.DetailMovieActivity;

public class BaseAppCompatActivity extends AppCompatActivity {
    public static final String KEY_FRAGMENT = "fragment";
    public static final String STATE_MOVIES = "state_movies";
    protected static final String PREF_LANGUAGE = "language";
    protected static final String ID_LANGUAGE = "id";
    protected static final String EN_LANGUAGE = "en";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidNetworking.initialize(this);
    }

    public String getLanguage() {
        return getSharedPreferences().getString(PREF_LANGUAGE, EN_LANGUAGE);
    }

    public RESTMovie getRESTMovies() {
        return new RESTMovie();
    }

    protected void showDetailMovie(Movie data, Context context, int type, boolean isFavorite) {
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, data);
        intent.putExtra(DetailMovieActivity.EXTRA_STATUS, isFavorite);
        intent.putExtra(DetailMovieActivity.EXTRA_TYPE, type);
        startActivity(intent);
    }

    protected SharedPreferences getSharedPreferences() {
        return getSharedPreferences("SETTING", MODE_PRIVATE);
    }
}
