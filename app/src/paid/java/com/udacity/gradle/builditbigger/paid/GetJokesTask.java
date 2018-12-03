package com.udacity.gradle.builditbigger.paid;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.Pair;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

import baking.training.udacity.com.androidjokelibrary.JokeLibraryActivity;

public class GetJokesTask extends AsyncTask<Pair<Context, String>, Void, String>  {

    private static MyApi myApiService = null;
    private Context context;
    private OnPostTaskListener mOnPostTaskListener;

    public GetJokesTask(OnPostTaskListener mOnPostTaskListener) {
        this.mOnPostTaskListener = mOnPostTaskListener;
    }

    @Override
    protected String doInBackground(Pair<Context, String>... pairs) {

        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        context = pairs[0].first;
        String name = pairs[0].second;

        try {
            return myApiService.getJokeFromLibrary().execute().getData();
        } catch (IOException e) {
            return null;
        }

    }

    @Override
    protected void onPostExecute(String result) {

        mOnPostTaskListener.onTaskCompleted(result);

    }

}
