package com.example.hearthstone_app;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class SearchFragment extends Fragment {

    Button btn_search;
    EditText et_searchInput;
    RecyclerView rv_searchList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        btn_search = view.findViewById(R.id.btn_Search);
        et_searchInput = view.findViewById(R.id.text_for_search);
        rv_searchList = view.findViewById(R.id.rv_searchList);
        final SearchService searchService = new SearchService(getActivity());

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchService.getSearchData(et_searchInput.getText().toString(), new SearchService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(getActivity(), "Something wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<SearchModel> searchModelList) {
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        rv_searchList.setLayoutManager(layoutManager);
                        SearchAdapter searchAdapter = new SearchAdapter(searchModelList);
                        rv_searchList.setAdapter(searchAdapter);
                    }
                    class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
                        private final List<SearchModel> searchModelList;

                        SearchAdapter(List<SearchModel> searchModelList) {
                            this.searchModelList = searchModelList;
                        }

                        @NonNull
                        @Override
                        public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_items, parent, false);
                            return new SearchViewHolder(itemView);
                        }

                        @Override
                        public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
                            SearchModel searchModel = searchModelList.get(position);
                            holder.nameView.setText(searchModel.getName());
                            holder.cardSetView.setText(searchModel.getCardSet());
                            holder.typeView.setText(searchModel.getType());
                        }
                        @Override
                        public int getItemCount() {
                            return searchModelList.size();
                        }

                        class SearchViewHolder extends RecyclerView.ViewHolder {
                            final TextView nameView, typeView, cardSetView;


                            SearchViewHolder(@NonNull View itemView) {
                                super(itemView);
                                nameView = itemView.findViewById(R.id.name);
                                typeView = itemView.findViewById(R.id.type);
                                cardSetView = itemView.findViewById(R.id.cardSet);
                            }
                        }
                    }
                });
            }
        });
        return view;
    }
}

