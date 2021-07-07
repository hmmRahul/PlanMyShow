package com.hmmRahulDev.planmyshow.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hmmRahulDev.planmyshow.dao.TVShowDao;
import com.hmmRahulDev.planmyshow.models.TVShow;

@Database(entities = TVShow.class ,version = 1, exportSchema = false)
public abstract class TVSHowsDatabase extends RoomDatabase {

    private static TVSHowsDatabase tvsHowsDatabase;

    public static synchronized TVSHowsDatabase getTvsHowsDatabase(Context context)
    {
        if (tvsHowsDatabase == null)
        {
            tvsHowsDatabase = Room.databaseBuilder(
                    context,TVSHowsDatabase.class, "tv_shows_db"
            ).build();
        }
        return tvsHowsDatabase;
    }
    public abstract TVShowDao tvShowDao();
}
