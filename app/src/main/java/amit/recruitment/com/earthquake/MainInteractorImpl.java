package amit.recruitment.com.earthquake;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amit on 3/23/18.
 */

public class MainInteractorImpl implements MainInteractor {

    private GetDataListener mGetDatalistener;
    private RequestQueue mRequestQueue;

    private final String REQUEST_TAG= "EQ-Network-Call";

    public MainInteractorImpl(GetDataListener mGetDatalistener) {

        this.mGetDatalistener = mGetDatalistener;
    }

    @Override
    public void initNetworkCall(Context context, String url) {

        cancelAllRequests();

        mRequestQueue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, url, onEQLoaded, onEQError);
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000, /* 10 sec timeout policy */
                0, /*no retry*/
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.setTag(REQUEST_TAG);
        mRequestQueue.add(request);

    }

    private void cancelAllRequests(){
        if(mRequestQueue!=null){
            mRequestQueue.cancelAll(REQUEST_TAG);
        }
    }
    @Override
    public void onDestroy() {
        cancelAllRequests();
    }

    private final Response.Listener<String> onEQLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("EQ-Network", response);


            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("yyyy-mm-dd hh:mm:ss");
            Gson gson = gsonBuilder.create();

            try {
                EarthquakeResponse eqResponse = gson.fromJson(response, EarthquakeResponse.class);
                mGetDatalistener.onSuccess("data success", eqResponse.getEarthquakes());

            } catch (JsonSyntaxException ex) {
                Log.e("EQ-Network", ex.toString());
                mGetDatalistener.onFailure(ex.toString());
            }

        }
    };

    private final Response.ErrorListener onEQError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("EQ-Network", error.toString());
            mGetDatalistener.onFailure(error.toString());
        }
    };
}
