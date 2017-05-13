package com.example.owner.enozom;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private final String urlStore = "http://188.166.81.130/staging/public/stores.json";
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<ListItem> listItems;
    DividerItemDecoration dividerItemDecoration;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),new LinearLayoutManager(getApplicationContext()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        listItems = new ArrayList<>();
        loadStoreData();
        text = (EditText) findViewById(R.id.editText);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s = s.toString();
                final List<ListItem> filteredList = new ArrayList<>();
                for(int x=0 ; x<listItems.size() ; x++){
                    String compareText = listItems.get(x).getName();
                    if (compareText.contains(s)) {

                        filteredList.add(listItems.get(x));
                    }
                }

                recyclerViewAdapter = new RecyclerViewAdapter(filteredList,getApplicationContext());
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void loadStoreData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                urlStore, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONArray array = new JSONArray(response);
                    for(int i=0 ; i<array.length() ; i++){
                        int j = i+1;
                      JSONObject jsonObject  = array.getJSONObject(i);
                      ListItem item = new ListItem(jsonObject.getString("StoreName"),jsonObject.getString("StoreDescription"),jsonObject.getString("StoreLogo"));
                        //item.setImageName("img"+j);
                        listItems.add(item);
                    }
                    recyclerViewAdapter = new RecyclerViewAdapter(listItems,getApplicationContext());
                    recyclerView.setAdapter(recyclerViewAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"No Internet Connection ",Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
