package com.cleanup.todoc.model;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

public class MyAsyncTask extends AsyncTask <Task, Void, Void> {

    public interface Listeners{
        void doInBackground();
    }

//    private final WeakReference<Listeners> callback;
    private TaskDao mTaskDao;
    private int mMethod;

    public MyAsyncTask(TaskDao taskDao, int method){
//        this.callback = new WeakReference<>(callback);
        mTaskDao = taskDao;
        mMethod = method;
    }


    @Override
    protected Void doInBackground(Task... tasks) {
        switch (mMethod){
            case 1 :
                mTaskDao.insert(tasks[0]);
                break;
            case 2:
                mTaskDao.update(tasks[0]);
                break;
            case 3:
                mTaskDao.delete(tasks[0]);
                break;
        }
        return null;
    }
}
