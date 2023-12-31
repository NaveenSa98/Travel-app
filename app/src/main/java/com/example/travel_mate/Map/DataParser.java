package com.example.travel_mate.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {

    private HashMap<String, String>getPlace(JSONObject googlePlaceJason)
    {
        HashMap<String, String> googlePlacesMap = new HashMap<>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try {
            if(! googlePlaceJason.isNull("name"))
            {

                    placeName = googlePlaceJason.getString("name");


            }
            if (!googlePlaceJason.isNull("vicinity"))
            {
                vicinity = googlePlaceJason.getString("vicinity");
            }
            latitude =googlePlaceJason.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude =googlePlaceJason.getJSONObject("geometry").getJSONObject("location").getString("lng");

            reference = googlePlaceJason.getString("reference");

            googlePlacesMap.put("place_name", placeName);
            googlePlacesMap.put("vicinity", vicinity);
            googlePlacesMap.put("lat", latitude);
            googlePlacesMap.put("lng", longitude);
            googlePlacesMap.put("reference", reference);



        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return googlePlacesMap;
    }

    private List<HashMap<String,String>> getPlaces(JSONArray jsonArray)
    {
        int count = jsonArray.length();
        List<HashMap<String,String>>placesList = new ArrayList<>();
        HashMap<String,String>placeMap =null;

        for (int i = 0; i<count; i++ )
        {
            try {
                placeMap = getPlace((JSONObject)jsonArray.get(i));
                placesList.add(placeMap);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return placesList;
    }

    public List<HashMap<String,String>> parse(String jsonData)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return getPlaces(jsonArray);
    }
}
