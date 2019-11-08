package id.co.endang.mymovie3.rest;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import id.co.endang.mymovie3.BuildConfig;
import id.co.endang.mymovie3.model.MovieResponse;

public class RESTMovie {
    public static final String MOVIE_TOP_RATED_API = BuildConfig.API_BASE_URL + "movie/top_rated?api_key={apiKey}&language={language}&page=1";
    public static final String TV_TOP_RATED_API = BuildConfig.API_BASE_URL + "tv/top_rated?api_key={apiKey}&language={language}&page=1";
    public static final String TV_SEARCH = BuildConfig.API_BASE_URL + "search/tv?api_key={apiKey}&language={language}&page=1&query={query}";
    public static final String MOVIE_SEARCH = BuildConfig.API_BASE_URL + "search/movie?api_key={apiKey}&language={language}&page=1&query={query}";
    public static final String MOVIE_RELEASE = BuildConfig.API_BASE_URL + "discover/movie?api_key={apiKey}&primary_release_date.gte={query}&primary_release_date.lte={query}";

    public void getMovies(String endpoint, String language, final RESTMovieCallback callback) {
        Log.d("REQ ", endpoint);
        AndroidNetworking.get(endpoint)
                .addPathParameter("apiKey", BuildConfig.API_KEY)
                .addPathParameter("language", language)
                .setTag(RESTMovie.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsObject(MovieResponse.class, new ParsedRequestListener<MovieResponse>() {
                    @Override
                    public void onResponse(MovieResponse response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ERROR", "onError : " + anError);
                        callback.onFailed("Terjadi kesalahan saat konek ke server");
                    }
                });
    }

    public void searchMovies(String endpoint, String language, String query, final RESTMovieCallback callback) {
        Log.d("REQ ", endpoint);
        AndroidNetworking.get(endpoint)
                .addPathParameter("apiKey", BuildConfig.API_KEY)
                .addPathParameter("language", language)
                .addPathParameter("query", query)
                .setTag(RESTMovie.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsObject(MovieResponse.class, new ParsedRequestListener<MovieResponse>() {
                    @Override
                    public void onResponse(MovieResponse response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ERROR", "onError : " + anError);
                        callback.onFailed("Terjadi kesalahan saat konek ke server");
                    }
                });
    }
}
