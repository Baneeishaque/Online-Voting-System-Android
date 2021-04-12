package com.example.onlinevotingsystem;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static com.example.onlinevotingsystem.SendOtpNetworkTask.showProgress;

public class GetVoterNetworkTask extends AsyncTask<Void, Void, String[]> {

    String voterId;

    Context activityContext;

    ProgressBar progressBar;
    View formView;

    AsyncResponse asyncResponse;

    public GetVoterNetworkTask(String voterId, Context activityContext, ProgressBar progressBar, View formView, AsyncResponse asyncResponse) {

        this.voterId = voterId;
        this.activityContext = activityContext;
        this.progressBar = progressBar;
        this.formView = formView;
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected String[] doInBackground(Void... voids) {

        try {
            DefaultHttpClient defaultHttpClient;
            HttpPost httpPost;
            String networkActionResponse;

            defaultHttpClient = new DefaultHttpClient();
            httpPost = new HttpPost("https://online-voting-system-a5b27-default-rtdb.firebaseio.com/voters/" + voterId + ".json");

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


            asyncResponse.processFinish(networkActionResponseArray[1]);


        }

    }

    @Override
    protected void onCancelled() {

        showProgress(false, activityContext, progressBar, formView);
    }

    public interface AsyncResponse {

        void processFinish(String response);
    }
}
