package com.cleanup.todoc;

import android.content.ContentValues;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskDao;
import com.cleanup.todoc.model.TaskDataBase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.cleanup.todoc.UnitTestUtils.getOrAwaitValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class TaskDaoInstrumentedTest {

    private TaskDataBase mDataBase;
    private TaskDao mTaskDao;
    private static Task task = new Task (1, "Test Task", 123456);
    private static Task task2 = new Task (1, "Test Task2", 123456);
    private static Task task3 = new Task (1, "Test Task3", 123456);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup(){
        mDataBase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                TaskDataBase.class)
                .allowMainThreadQueries()
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        ContentValues projectContentValues = new ContentValues();

                        projectContentValues.put("id", 1L);
                        projectContentValues.put("name", "Projet Tartampion");
                        projectContentValues.put("color", 0xFFEADAD1);
                        db.insert("project_table", OnConflictStrategy.REPLACE, projectContentValues);
                    }
                })
                .build();
        mTaskDao = mDataBase.taskDao();
    }

    @After
    public void closeDB(){
        mDataBase.close();
    }

    @Test
    public void get_item_when_no_item_inserted() throws InterruptedException {
        List<Task> liveDataTaskTest = getOrAwaitValue(mTaskDao.getAllTask());
        assertEquals(0, liveDataTaskTest.size());
    }

    @Test
    public void insert_one_new_task_in_database_with_dao() throws InterruptedException {

        mTaskDao.insert(task);
        List<Task> liveDataTaskTest = getOrAwaitValue(mTaskDao.getAllTask());

        assertEquals(1, liveDataTaskTest.size());
        assertEquals(liveDataTaskTest.get(0).getName(), task.getName());
        assertNotEquals(liveDataTaskTest.get(0).getName(), task2.getName());
        assertNotEquals(2, liveDataTaskTest.size());

    }

    @Test
    public void insert_multiple_new_task_in_database_with_dao() throws InterruptedException {

        mTaskDao.insert(task);
        mTaskDao.insert(task2);
        mTaskDao.insert(task3);
        List<Task> liveDataTaskTest = getOrAwaitValue(mTaskDao.getAllTask());

        assertEquals(3, liveDataTaskTest.size());
        assertEquals(liveDataTaskTest.get(0).getName(), task.getName());
        assertNotEquals(liveDataTaskTest.get(0).getName(), task2.getName());
        assertNotEquals(2, liveDataTaskTest.size());

    }
    @Test
    public void insert_and_delete_item_in_databas_with_dao() throws InterruptedException {
        //Id is auto generate in app, so for test, need to set it
        task.setId(1);

        //add 3 tasks
        mTaskDao.insert(task);
        mTaskDao.insert(task2);
        mTaskDao.insert(task3);
        List<Task> liveDataTaskTest = getOrAwaitValue(mTaskDao.getAllTask());

        //assert all task added
        assertEquals(3, liveDataTaskTest.size());
        assertEquals(liveDataTaskTest.get(0).getName(), task.getName());
        assertEquals(liveDataTaskTest.get(1).getName(), task2.getName());
        assertNotEquals(2, liveDataTaskTest.size());

        //assert task was deleted
        mTaskDao.delete(task);
        List<Task> liveDataTaskTest2 = getOrAwaitValue(mTaskDao.getAllTask());
        assertEquals(2, liveDataTaskTest2.size());
        assertNotEquals(liveDataTaskTest2.get(0).getName(), task.getName());
        assertEquals(liveDataTaskTest2.get(0).getName(), task2.getName());



    }

}
