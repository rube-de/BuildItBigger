package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.content.Intent;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.example.bdrf.displayjokelibrary.JokeActivity;
import com.udacity.gradle.builditbigger.endpoint.EndpointsAsyncTask;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private ObservablesFactory obsFactory;

    public ApplicationTest() {
        super(Application.class);
    }

    @Before
    public void setUp(){
        obsFactory =  new ObservablesFactory();
    }
    @Test
    public void testAysncTask() throws ExecutionException, InterruptedException {
        String result = null;
        try {
            EndpointsAsyncTask asyncTask = new EndpointsAsyncTask();
            result = asyncTask.execute().get(30, TimeUnit.SECONDS);
        }catch (Exception e){
            fail("Timed out");
        }
        Assert.assertNotNull(result);
        Assert.assertNotEquals("", result);
    }

    @Test
    public  void testRxAsyncTaskWithBlocking(){
        String result = null;
        result = obsFactory.getJokeObservable().toBlocking().first();
        Assert.assertNotNull(result);
        Assert.assertNotEquals("", result);

    }
}