package com.cleanup.todoc.model;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class, Project.class}, version = 1)
public abstract class TaskDataBase extends RoomDatabase {

    private static volatile TaskDataBase instance;
    public abstract TaskDao taskDao();
    private static final int NUMBER_OF_THREADS = 5;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static synchronized TaskDataBase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TaskDataBase.class,
                    "task_database")
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private final static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            ContentValues projectContentValues = new ContentValues();

            projectContentValues.put("id", 1L);
            projectContentValues.put("name", "Projet Tartampion");
            projectContentValues.put("color", 0xFFEADAD1);
            db.insert("project_table", OnConflictStrategy.REPLACE, projectContentValues);

            projectContentValues.put("id", 2L);
            projectContentValues.put("name", "Projet Lucidia");
            projectContentValues.put("color", 0xFFB4CDBA);
            db.insert("project_table", OnConflictStrategy.REPLACE, projectContentValues);

            projectContentValues.put("id", 3L);
            projectContentValues.put("name", "Projet Circus");
            projectContentValues.put("color", 0xFFA3CED2);
            db.insert("project_table", OnConflictStrategy.REPLACE, projectContentValues);

            databaseWriteExecutor.execute(() -> {
                TaskDao dao = instance.taskDao();
                dao.getAllTask();

            });
        }
    };

}
