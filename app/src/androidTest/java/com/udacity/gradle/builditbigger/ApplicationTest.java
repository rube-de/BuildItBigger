package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.udacity.gradle.builditbigger.endpoint.EndpointsAsyncTask;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
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
}