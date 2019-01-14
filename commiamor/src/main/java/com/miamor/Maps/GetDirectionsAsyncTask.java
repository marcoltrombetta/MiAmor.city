package com.miamor.Maps;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by marco on 16/11/15.
 */
public class GetDirectionsAsyncTask extends AsyncTask<Map<String, String>, Object, ArrayList>
{
    public static final String USER_CURRENT_LAT = "user_current_lat";
    public static final String USER_CURRENT_LONG = "user_current_long";
    public static final String DESTINATION_LAT = "destination_lat";
    public static final String DESTINATION_LONG = "destination_long";
    public static final String DIRECTIONS_MODE = "directions_mode";
    private Fragment activity;
    private Exception exception;


    public static interface MapDirections{
        void onComplete(ArrayList result);
    }


    private MapDirections mapDirections;


    public GetDirectionsAsyncTask(MapDirections mapDirections)
    {
        super();
        this.mapDirections=mapDirections;
    }

    public void onPreExecute() { }

    @Override
    public void onPostExecute(ArrayList result)
    {

        if (exception == null)
        {
            mapDirections.onComplete(result);
            // activity.handleGetDirectionsResult(result);
        }
        else
        {
            processException();
        }
    }

    @Override
    protected ArrayList doInBackground(Map<String, String>... params)
    {
        Map<String, String> paramMap = params[0];
        ArrayList directionPoints=null;
        try
        {
            LatLng fromPosition = new LatLng(Double.valueOf(paramMap.get(USER_CURRENT_LAT)) , Double.valueOf(paramMap.get(USER_CURRENT_LONG)));
            LatLng toPosition = new LatLng(Double.valueOf(paramMap.get(DESTINATION_LAT)) , Double.valueOf(paramMap.get(DESTINATION_LONG)));
            GMapV2Direction md = new GMapV2Direction();
            Document doc = md.getDocument(fromPosition, toPosition, paramMap.get(DIRECTIONS_MODE));
            directionPoints = md.getDirection(doc);

        }
        catch (Exception e)
        {
            exception = e;
           // return null;
        }

        return directionPoints;
    }

    private void processException() {  }

}
