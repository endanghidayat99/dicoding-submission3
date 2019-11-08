package id.co.endang.moviexixfavorite.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class MovieFavorite implements Parcelable {
    public static final Creator<MovieFavorite> CREATOR = new Creator<MovieFavorite>() {
        @Override
        public MovieFavorite createFromParcel(Parcel in) {
            return new MovieFavorite(in);
        }

        @Override
        public MovieFavorite[] newArray(int size) {
            return new MovieFavorite[size];
        }
    };
    private long id;
    private int id_tmb;
    private String title;
    private String overview;
    private String releaseDate;
    private String posterPath;
    private String backdropPath;
    private Double voteAverage;
    private int movieType;//0 - Movie, 1 - TV

    protected MovieFavorite(Parcel in) {
        id = in.readLong();
        id_tmb = in.readInt();
        title = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        if (in.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = in.readDouble();
        }
        movieType = in.readInt();
    }

    public MovieFavorite() {
    }

    public MovieFavorite(long id, int id_tmb, String title, String overview, String releaseDate, String posterPath, String backdropPath, Double voteAverage, int movieType) {
        this.id = id;
        this.id_tmb = id_tmb;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.movieType = movieType;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(id_tmb);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        if (voteAverage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(voteAverage);
        }
        dest.writeInt(movieType);
    }
}
