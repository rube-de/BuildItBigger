package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private ObservablesFactory obsFactory;

    public ApplicationTest() {
        super(Application.class);
    }

    @Before
    public void setUp() {
        obsFactory = new ObservablesFactory();
    }

    @Test
    public void testRxAsyncTaskWithBlocking() {
        String result = null;
        result = obsFactory.getJokeObservable().toBlocking().first();
        Assert.assertNotNull(result);
        Assert.assertNotEquals("", result);

    }
}