package kordoghli.firas.fam_pay.Menu.wallet;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kordoghli.firas.fam_pay.Data.SessionHandler;
import kordoghli.firas.fam_pay.Data.URLs;
import kordoghli.firas.fam_pay.Data.VolleySingleton;
import kordoghli.firas.fam_pay.R;
import kordoghli.firas.fam_pay.cryptocurrency.CryptocurrencyTrackerActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends Fragment {

    TextView wallet;
    private SessionHandler session;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public WalletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        session = new SessionHandler(getContext());
        getBalance();
        getHistoricTransaction();
        //Button toCoin = view.findViewById(R.id.button6);
        ImageButton toCoin = view.findViewById(R.id.imageButton);
        toCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CryptocurrencyTrackerActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    void getBalance() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_BALANCE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            String balance = obj.getString("balance");
                            session.saveBalance(Integer.parseInt(balance));
                            wallet = getView().findViewById(R.id.wallet);
                            wallet.setText(balance + " FAM");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("address", session.getAddress());
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    void getHistoricTransaction() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_HISTORIQU_TRANSANCTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //converting response to json object
                        ArrayList<TransactionItem> transactionItems = new ArrayList<>();
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject transaction = array.getJSONObject(i);
                                TransactionItem transactionItem = new TransactionItem(
                                        transaction.getString("sender"),
                                        transaction.getString("receiver"),
                                        transaction.getString("amount"),
                                        transaction.getString("date")
                                );
                                transactionItems.add(transactionItem);
                            }
                            mRecyclerView = getView().findViewById(R.id.recycleViewTransaction);
                            mLayoutManager = new LinearLayoutManager(getContext());
                            mAdapter = new TransactionAdapter(transactionItems,getContext());
                            mRecyclerView.setLayoutManager(mLayoutManager);
                            mRecyclerView.setAdapter(mAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sender", session.getAddress());
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}
