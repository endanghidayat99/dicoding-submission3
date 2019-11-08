package id.co.endang.moviexixfavorite.consumer;

import android.net.Uri;

public class MovieFavoriteUri {
    public static final String AUTHORITY = "id.co.endang.mymovie3.db.provider";
    private static final String SCHEME = "content";
    private static final String TABLE_NAME = "movie_favorite";
    public static final Uri MOVIE_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();
}
