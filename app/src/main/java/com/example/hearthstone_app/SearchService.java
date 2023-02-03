package com.example.hearthstone_app;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchService {
    final static String appFileName = "appFile.txt";
    final static String appDirName = "/appDir5/";
    final static String appDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + appDirName;
    public static final String QUERY_FOR_SEARCHED_DATA = "https://omgvamp-hearthstone-v1.p.rapidapi.com/cards/search/";
    Context context;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy.HH:mm:ss");
    String timeStamp = df.format(new Date());

    private boolean writeToExternalMemory(String dataToWrite) {
        try {
            File appDir = new File(appDirPath, "");
            if (!appDir.exists()) {
                appDir.mkdir();
            }

            File file = new File(appDir, appFileName);
            FileWriter writer = new FileWriter(file, true);
            writer.write(dataToWrite);
            writer.flush();
            writer.close();
            Log.d(TAG, "ZAPIS JE OK.");
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Chyba zapisu: " + e.toString());
            return false;
        }
    }
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
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            try {
                writeToExternalMemory("---------------------------------------------------\n");
                writeToExternalMemory(timeStamp + "\n");
                for (int i = 0; i < response.length(); i++) {
                    SearchModel justSearched = new SearchModel();
                    JSONObject search = (JSONObject) response.get(i);
                    justSearched.setName(search.getString("name"));
                    justSearched.setCardSet(search.getString("cardSet"));
                    justSearched.setType(search.getString("type"));
                    writeToExternalMemory(search.getString("name") + "\n");
                    writeToExternalMemory(search.getString("cardSet") + "\n");
                    writeToExternalMemory(search.getString("type") + "\n");
                    searchModelList.add(justSearched);
                }
                writeToExternalMemory("---------------------------------------------------\n");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            volleyResponseListener.onResponse(searchModelList);
        }, error -> {

        }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-RapidAPI-Host", "omgvamp-hearthstone-v1.p.rapidapi.com");
                headers.put("X-RapidAPI-Key", "a925f48572msh743e225cd065b07p1e98e1jsn65d8c18b5b2c");
                return headers;
            }
        };;
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}
