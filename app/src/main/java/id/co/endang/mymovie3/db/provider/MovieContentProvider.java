package id.co.endang.mymovie3.db.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import id.co.endang.mymovie3.db.repository.MovieRepository;

public class MovieContentProvider extends ContentProvider {

    public static final String AUTHORITY = "id.co.endang.mymovie3.db.provider";
    private static final String SCHEME = "content";
    private static final String TABLE_NAME = "movie_favorite";
    public static final Uri MOVIE_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();
    private static final int MOVIE_LIST = 1;
    private static final int MOVIE_BY_ID = 2;
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, TABLE_NAME, MOVIE_LIST);
        MATCHER.addURI(AUTHORITY, TABLE_NAME + "/#", MOVIE_BY_ID);
    }

    public MovieContentProvider() {
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final int code = MATCHER.match(uri);
        if (code == MOVIE_LIST || code == MOVIE_BY_ID) {
            final Context context = getContext();
            if (context == null)
                return null;

            MovieRepository movieRepository = new MovieRepository(getContext());
            final Cursor cursor;
            if (code == MOVIE_LIST) {
                cursor = movieRepository.getMovieDatabase().daoAccess().selectAll();
            } else {
                cursor = movieRepository.getMovieDatabase().daoAccess().selectById(ContentUris.parseId(uri));
            }

            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else
            throw new IllegalArgumentException("Unknown URI: " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
