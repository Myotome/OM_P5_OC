package com.cleanup.todoc.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import java.util.List;

public class TaskRepository {
    private final TaskDao mTaskDao;
    private final LiveData<List<Task>> mAscTaskName;
    private final LiveData<List<Task>> mDescTaskName;
    private final LiveData<List<Task>> mAscTaskDate;
    private final LiveData<List<Task>> mDescTaskDate;
    private final LiveData<List<Task>> mAllTask;

    public TaskRepository(Application application){
        TaskDataBase dataBase = TaskDataBase.getInstance(application);
        mTaskDao = dataBase.taskDao();
        mAscTaskName = mTaskDao.ascendingFilterName();
        mDescTaskName = mTaskDao.descendingFilterName();
        mAscTaskDate = mTaskDao.ascendingFilterDate();
        mDescTaskDate = mTaskDao.descendingFilterDate();
        mAllTask = mTaskDao.getAllTask();
    }

    public void insert(Task task){
//        new InsertTaskAsyncTask(mTaskDao).execute(task);
        final int method = 1;
        new MyAsyncTask(mTaskDao, method).execute(task);
    }
    public void update(Task task){
        final int method = 2;
        new MyAsyncTask(mTaskDao, method).execute(task);
    }
    public void delete(Task task){
        final int method = 3;
        new MyAsyncTask(mTaskDao, method).execute(task);
    }
    public LiveData<List<Task>> ascendingFilterName(){
        return mAscTaskName;
    }
    public LiveData<List<Task>> descendingFilterName(){
        return mDescTaskName;
    }
    public LiveData<List<Task>> ascendingFilterDate(){
        return mAscTaskDate;
    }
    public LiveData<List<Task>> descendingFilterDate(){
        return mDescTaskDate;
    }
    public LiveData<List<Task>> getAllTask(){
        return mAllTask;
    }


//    /**======================  Mieux gérer ici ============================*/
//
//    private static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void>{
//        private TaskDao mTaskDao;
//        private InsertTaskAsyncTask (TaskDao taskDao){
//            mTaskDao = taskDao;
//        }
//
//        @Override
//        protected Void doInBackground(Task... tasks) {
//            mTaskDao.insert(tasks[0]);
//            return null;
//        }
//    }
}
