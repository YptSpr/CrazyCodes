package com.example.spr_ypt.crazycodes.thread.synctask;

import android.os.AsyncTask;

/**
 * Created by tango on 16/2/22.
 */
public abstract class MoBaseTask extends AsyncTask<Object,Object,Object> {

    public MoBaseTask() {}

    @Override
    protected void onPostExecute(Object msg) {
        onBackground(msg);
    }

    @Override
    protected Object doInBackground(Object... params) {
        return onBackground(params[0]);
    }

    protected void runTask(Object msg) {
        execute(msg);
    }
    //background mission
    protected abstract Object onBackground(Object msg);

    //main ui mission
    protected abstract void onTaskComplete(Object msg);
}
