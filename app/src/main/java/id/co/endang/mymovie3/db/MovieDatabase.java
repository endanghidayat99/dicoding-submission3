package id.co.endang.mymovie3.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import id.co.endang.mymovie3.db.dao.DaoAccess;
import id.co.endang.mymovie3.db.model.MovieFavorite;

@Database(entities = {MovieFavorite.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess();
}
