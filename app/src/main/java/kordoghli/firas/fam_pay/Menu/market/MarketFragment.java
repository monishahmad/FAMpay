package kordoghli.firas.fam_pay.Menu.market;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kordoghli.firas.fam_pay.Data.URLs;
import kordoghli.firas.fam_pay.Data.VolleySingleton;
import kordoghli.firas.fam_pay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MarketFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog pDialog;


    public MarketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_market, container, false);
        displayLoader();
        getCoin();
        return view;
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    void getCoin() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URLs.URL_COINS_API, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<CoinItem> coinItems = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject coin =response.getJSONObject(i);
                                CoinItem coinItem = new CoinItem(
                                        coin.getString("id"),
                                        coin.getString("name"),
                                        coin.getString("symbol"),
                                        coin.getString("price_usd"),
                                        coin.getString("percent_change_1h"),
                                        coin.getString("percent_change_24h"),
                                        coin.getString("percent_change_7d")
                                );
                                coinItems.add(coinItem);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mRecyclerView = getView().findViewById(R.id.recycleView);
                        mRecyclerView.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(getContext());
                        mAdapter = new CoinAdapter(coinItems);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(mAdapter);
                        pDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

}
