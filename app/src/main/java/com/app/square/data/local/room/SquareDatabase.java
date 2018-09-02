package com.app.square.data.local.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.app.square.common.pojo.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class SquareDatabase extends RoomDatabase {
    public abstract DaoFavMovie daoMovie();
}
