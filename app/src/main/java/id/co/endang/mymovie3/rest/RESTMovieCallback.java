package id.co.endang.mymovie3.rest;

import id.co.endang.mymovie3.model.MovieResponse;

public interface RESTMovieCallback {
    void onSuccess(MovieResponse response);

    void onFailed(String error);
}
