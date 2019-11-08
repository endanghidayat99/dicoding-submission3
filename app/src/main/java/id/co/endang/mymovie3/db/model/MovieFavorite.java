package id.co.endang.mymovie3.db.model;

import android.database.Cursor;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "movie_favorite")
public class MovieFavorite implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private int id_tmb;
    private String title;
    private String overview;
    private String releaseDate;
    private String posterPath;
    private String backdropPath;
    private Double voteAverage;
    private int movieType;//0 - Movie, 1 - TV

    public MovieFavorite() {
    }

    public MovieFavorite(Cursor cursor) {
        this.id = cursor.getLong(cursor.getColumnIndex("id"));
        this.id_tmb = cursor.getInt(cursor.getColumnIndex("id_tmb"));
        this.title = cursor.getString(cursor.getColumnIndex("title"));
        this.overview = cursor.getString(cursor.getColumnIndex("overview"));
        this.releaseDate = cursor.getString(cursor.getColumnIndex("releaseDate"));
        this.posterPath = cursor.getString(cursor.getColumnIndex("posterPath"));
        this.backdropPath = cursor.getString(cursor.getColumnIndex("backdropPath"));
        this.voteAverage = cursor.getDouble(cursor.getColumnIndex("voteAverage"));
        this.movieType = cursor.getInt(cursor.getColumnIndex("movieType"));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getId_tmb() {
        return id_tmb;
    }

    public void setId_tmb(int id_tmb) {
        this.id_tmb = id_tmb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getMovieType() {
        return movieType;
    }

    public void setMovieType(int movieType) {
        this.movieType = movieType;
    }
}
