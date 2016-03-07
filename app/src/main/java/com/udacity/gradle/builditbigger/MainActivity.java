package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bdrf.displayjokelibrary.JokeActivity;
import com.google.builditbigger.backend.myApi.MyApi;

import java.util.concurrent.ExecutionException;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static MyApi myApiService = null;
    private Subscription jokeSubscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unsubscribe from joke observer if still persist
        if(jokeSubscription != null && !jokeSubscription.isUnsubscribed()){
            jokeSubscription.unsubscribe();
        }
    }


    //Handle Joke Button click:
    //call Java Lib with async call via rx java observable
    public void tellJoke(View view) throws ExecutionException, InterruptedException {
        ObservablesFactory obsFactory = new ObservablesFactory();

        jokeSubscription = obsFactory.getJokeObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "Observable complete");
                        jokeSubscription.unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Observable error: " + e.getMessage(), e);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, "Observable onNext");
                        if(s != null) {
                            Intent intent = new Intent(MainActivity.this, JokeActivity.class);
                            intent.putExtra(JokeActivity.JOKE_KEY, s);
                            startActivity(intent);
                        }

                    }
                });

    }

}
