package com.example.hearthstone_app;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SearchFragment extends Fragment {

    Button btn_search;
    EditText et_searchInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        btn_search = view.findViewById(R.id.btn_Search);
        et_searchInput = view.findViewById(R.id.text_for_search);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                String url = "https://omgvamp-hearthstone-v1.p.rapidapi.com/cards/search/" + et_searchInput.getText().toString();

                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
                    String cardName = "";
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject card = response.getJSONObject(0);
                            cardName = card.getString("name");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Toast.makeText(getActivity(), "Card name = " + cardName, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Something wrong!", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("X-RapidAPI-Host", "omgvamp-hearthstone-v1.p.rapidapi.com");
                        headers.put("X-RapidAPI-Key", "a925f48572msh743e225cd065b07p1e98e1jsn65d8c18b5b2c");
                        return headers;
                    }
                };
                queue.add(request);
            }
        });

        return view;
    }
}