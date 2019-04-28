package kordoghli.firas.fam_pay.cryptocurrency;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

public class CryptocurrencyTrackerActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cryptocurrency_tracker);
/*
        ArrayList<CoinItem> coinItems = new ArrayList<>();

        coinItems.add(new CoinItem("bitcoin", "Bitcoin", "BTC", "5297.3290772", "-0.23", "0.36", "0.22"));
        coinItems.add(new CoinItem("bitcoin", "Bitcoin", "BTC", "5297.3290772", "-0.23", "0.36", "0.22"));
        coinItems.add(new CoinItem("bitcoin", "Bitcoin", "BTC", "5297.3290772", "-0.23", "0.36", "0.22"));
        coinItems.add(new CoinItem("bitcoin", "Bitcoin", "BTC", "5297.3290772", "-0.23", "0.36", "0.22"));

        mRecyclerView = findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CoinAdapter(coinItems);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
*/
        getCoin();
    }

    void getCoin() {

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URLs.URL_COINS_API, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
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

                                ArrayList<CoinItem> coinItems = new ArrayList<>();
                                coinItems.add(coinItem);
                                System.out.println(coinItems);

                                mRecyclerView = findViewById(R.id.recycleView);
                                mRecyclerView.setHasFixedSize(true);
                                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                mAdapter = new CoinAdapter(coinItems);

                                mRecyclerView.setLayoutManager(mLayoutManager);
                                mRecyclerView.setAdapter(mAdapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }


}
