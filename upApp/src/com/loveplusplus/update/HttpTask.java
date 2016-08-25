package com.loveplusplus.update;

import android.os.AsyncTask;
import android.util.Log;

public class HttpTask extends AsyncTask<String, Integer, String> {
    private static final String TAG = "HTTP_TASK";

    @Override
    protected String doInBackground(String... params) {
        // Performed on Background Thread
        String url = params[0];
        try {
            String json = new NetworkTool().getContentFromUrl(url);
            return json;
        } catch (Exception e) {
            // TODO handle different exception cases
            Log.e(TAG, e.toString());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String json) {
        // Done on UI Thread
        if (json != null && json != "") {
            taskHandler.taskSuccessful(json);
        } else {
            Log.d(TAG, "taskFailed");
            taskHandler.taskFailed();
        }
    }

    public static interface HttpTaskHandler {
        void taskSuccessful(String json);

        void taskFailed();
    }

    HttpTaskHandler taskHandler;

    public void setTaskHandler(HttpTaskHandler taskHandler) {
        this.taskHandler = taskHandler;
    }

}