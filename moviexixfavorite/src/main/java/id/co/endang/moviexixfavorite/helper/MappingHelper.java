package id.co.endang.moviexixfavorite.helper;

import android.database.Cursor;

import java.util.ArrayList;

import id.co.endang.moviexixfavorite.model.MovieFavorite;

public class MappingHelper {
    public static ArrayList<MovieFavorite> mapCursorToArrayList(Cursor cursor) {
        ArrayList<MovieFavorite> movieList = new ArrayList<>();

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));

            int id_tmb = cursor.getInt(cursor.getColumnIndex("id_tmb"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String overview = cursor.getString(cursor.getColumnIndex("overview"));
            String releaseDate = cursor.getString(cursor.getColumnIndex("releaseDate"));
            String posterPath = cursor.getString(cursor.getColumnIndex("posterPath"));
            String backdropPath = cursor.getString(cursor.getColumnIndex("backdropPath"));
            Double voteAverage = cursor.getDouble(cursor.getColumnIndex("voteAverage"));
            int movieType = cursor.getInt(cursor.getColumnIndex("movieType"));

            movieList.add(new MovieFavorite(id, id_tmb, title, overview, releaseDate, posterPath, backdropPath, voteAverage, movieType));
        }
        return movieList;
    }

}
