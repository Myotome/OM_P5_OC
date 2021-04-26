package com.cleanup.todoc;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskDao;
import com.cleanup.todoc.model.TaskDataBase;
import com.cleanup.todoc.view_model.TaskViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestTaskViewModel {

    private TaskViewModel mViewModel = mock(TaskViewModel.class);
    private Task task = new Task (1, "Test Task", 123456);
    private Task task2 = new Task (2, "Test Task2", 123456);
    private Task task3 = new Task (3, "Test Task3", 123456);
    private List<Task> mTestListTask = new ArrayList<>();
    private MutableLiveData<List<Task>> mMutableLiveData;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void add_from_view_model() {
        //Simulate empty db
        mTestListTask.clear();
        assertEquals(0, mTestListTask.size());

        //Capture how work insert method
        ArgumentCaptor<Task> taskArgumentCaptor = ArgumentCaptor.forClass(Task.class);
        doAnswer(invocation -> {
            mTestListTask.add(task);
            return null;
        })
                .when(mViewModel)
                .insert(taskArgumentCaptor.capture());

        //Perform action
        mViewModel.insert(task);

        //Verify multiples condition to success
        verify(mViewModel).insert(taskArgumentCaptor.getValue());
        verify(mViewModel, never()).insert(task2);
        verify(mViewModel, times(1)).insert(task);
        verifyNoMoreInteractions(mViewModel);
        assertEquals(1, mTestListTask.size());
        assertTrue(mTestListTask.contains(task));
        assertFalse(mTestListTask.contains(task2));

    }

    @Test
    public void delete_from_view_model() {
        //Simulate db with 2 tasks
        mTestListTask.clear();
        mTestListTask.add(task3);
        mTestListTask.add(task2);
        assertEquals(2, mTestListTask.size());
        assertTrue(mTestListTask.contains(task3));
        assertFalse(mTestListTask.contains(task));
        assertTrue(mTestListTask.contains(task2));

        //Capture how work delete method
        ArgumentCaptor<Task> taskArgumentCaptor = ArgumentCaptor.forClass(Task.class);
        doAnswer(invocation -> {
            mTestListTask.remove(task3);
            return null;
        })
                .when(mViewModel)
                .delete(taskArgumentCaptor.capture());

        //Perform action
        mViewModel.delete(task3);

        //Verify multiples condition to success
        verify(mViewModel).delete(taskArgumentCaptor.getValue());
        verify(mViewModel, never()).delete(task2);
        verify(mViewModel, times(1)).delete(task3);
        verifyNoMoreInteractions(mViewModel);
        assertEquals(1, mTestListTask.size());
        assertTrue(mTestListTask.contains(task2));
        assertFalse(mTestListTask.contains(task));
        assertFalse(mTestListTask.contains(task3));

    }

    @Test
    public void get_all_task_from_view_model(){
        when(mViewModel.getAllTask()).thenReturn(liveDataListTaskTest());

        mViewModel.getAllTask();

        assertNotNull(mViewModel);
        assertEquals(mViewModel.getAllTask(),mMutableLiveData);
        verify(mViewModel, times(2)).getAllTask();
        verifyNoMoreInteractions(mViewModel);

    }

    private LiveData<List<Task>> liveDataListTaskTest() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        taskList.add(task2);
        taskList.add(task3);

        if (mMutableLiveData == null) {
            mMutableLiveData = new MutableLiveData<>();
            mMutableLiveData.setValue(taskList);
        }
        assertEquals(mMutableLiveData.getValue(),taskList);
        return mMutableLiveData;
    }
}
