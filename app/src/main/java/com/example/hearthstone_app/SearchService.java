package com.example.hearthstone_app;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchService {

    public static final String QUERY_FOR_SEARCH = "https://omgvamp-hearthstone-v1.p.rapidapi.com/cards/search/";
    public static final String QUERY_FOR_SEARCHED_DATA = "https://omgvamp-hearthstone-v1.p.rapidapi.com/cards/search/";
    Context context;
    String cardName;
    public SearchService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(List<SearchModel> searchModelList);
    }

    public void getSearchData(String input, VolleyResponseListener volleyResponseListener){
        List<SearchModel> searchModelList = new ArrayList<>();
        String url = QUERY_FOR_SEARCHED_DATA + input;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                SearchModel justSearched = new SearchModel();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject search = (JSONObject) response.get(i);
                        justSearched.setName(search.getString("name"));
                        justSearched.setCardSet(search.getString("cardSet"));
                        justSearched.setType(search.getString("type"));
                        //justSearched.setFaction(search.getString("faction"));
                        //justSearched.setPlayerClass(search.getString("playerClass"));
                        searchModelList.add(justSearched);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                volleyResponseListener.onResponse(searchModelList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-RapidAPI-Host", "omgvamp-hearthstone-v1.p.rapidapi.com");
                headers.put("X-RapidAPI-Key", "a925f48572msh743e225cd065b07p1e98e1jsn65d8c18b5b2c");
                return headers;
            }
        };;
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}
