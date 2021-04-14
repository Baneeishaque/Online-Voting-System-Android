package com.example.onlinevotingsystem;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class SendOtpNetworkTask extends AsyncTask<Void, Void, String[]> {

    String message, mobileNumber;

    Context activityContext;

    ProgressBar progressBar;
    View formView;

    AsyncResponseJSONObject asyncResponseJSONObject;

    public SendOtpNetworkTask(String message, String mobileNumber, Context activityContext, ProgressBar progressBar, View formView, AsyncResponseJSONObject asyncResponseJSONObject) {

        this.message = message;
        this.mobileNumber = mobileNumber;
        this.activityContext = activityContext;
        this.progressBar = progressBar;
        this.formView = formView;
        this.asyncResponseJSONObject = asyncResponseJSONObject;
    }

    @Override
    protected String[] doInBackground(Void... voids) {

        try {
            DefaultHttpClient defaultHttpClient;
            HttpPost httpPost;
            String networkActionResponse;

            defaultHttpClient = new DefaultHttpClient();
            httpPost = new HttpPost("https://www.fast2sms.com/dev/bulkV2");

            httpPost.setHeader("authorization", "Fsa8UfwRSjtz5b6mdQ3rNP4qDW2onTLXCKeAi0cVZ7uEvYlIBH5y9ugFDUcp8NHlX42qK1VidZR7OBYx");
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>(5);
            nameValuePairs.add(new BasicNameValuePair("authorization", "Fsa8UfwRSjtz5b6mdQ3rNP4qDW2onTLXCKeAi0cVZ7uEvYlIBH5y9ugFDUcp8NHlX42qK1VidZR7OBYx"));
            nameValuePairs.add(new BasicNameValuePair("sender_id", "TXTIND"));
            nameValuePairs.add(new BasicNameValuePair("message", "Online Voting System Otp : " + message));
            nameValuePairs.add(new BasicNameValuePair("route", "v3"));
            nameValuePairs.add(new BasicNameValuePair("numbers", mobileNumber));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            ResponseHandler<String> basicResponseHandler = new BasicResponseHandler();

            networkActionResponse = defaultHttpClient.execute(httpPost, basicResponseHandler);

            return new String[]{"0", networkActionResponse};

        } catch (UnsupportedEncodingException e) {

            return new String[]{"1", "UnsupportedEncodingException : " + e.getLocalizedMessage()};

        } catch (ClientProtocolException e) {

            return new String[]{"1", "ClientProtocolException : " + e.getLocalizedMessage()};

        } catch (IOException e) {

            return new String[]{"1", "IOException : " + e.getLocalizedMessage()};
        }
    }

    @Override
    protected void onPostExecute(String[] networkActionResponseArray) {

        showProgress(false, activityContext, progressBar, formView);

        if (networkActionResponseArray[0].equals("1")) {

            Toast.makeText(activityContext, "Error : " + networkActionResponseArray[1] + ", Try again..", Toast.LENGTH_LONG).show();

        } else {

            try {

                JSONObject jsonObject = new JSONObject(networkActionResponseArray[1]);
                asyncResponseJSONObject.processFinish(jsonObject);

            } catch (JSONException e) {

                Toast.makeText(activityContext, "Error : " + e.getLocalizedMessage() + ", Try again..", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected void onCancelled() {

        showProgress(false, activityContext, progressBar, formView);
    }

    public interface AsyncResponseJSONObject {

        void processFinish(JSONObject jsonObject);
    }

    public static void showProgress(final boolean show, Context context, final View progressBarView, final View formView) {

        int shortAnimTime = context.getResources().getInteger(android.R.integer.config_shortAnimTime);

        formView.setVisibility(show ? View.GONE : View.VISIBLE);
        formView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                formView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        progressBarView.setVisibility(show ? View.VISIBLE : View.GONE);
        progressBarView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressBarView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    public static void showProgress(final boolean show, Context context, final View progressBarView) {

        int shortAnimTime = context.getResources().getInteger(android.R.integer.config_shortAnimTime);

        progressBarView.setVisibility(show ? View.VISIBLE : View.GONE);
        progressBarView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressBarView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
}
