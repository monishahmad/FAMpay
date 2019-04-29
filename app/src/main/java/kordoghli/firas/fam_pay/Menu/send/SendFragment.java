package kordoghli.firas.fam_pay.Menu.send;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import kordoghli.firas.fam_pay.Data.SessionHandler;
import kordoghli.firas.fam_pay.Data.URLs;
import kordoghli.firas.fam_pay.Data.VolleySingleton;
import kordoghli.firas.fam_pay.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SendFragment extends Fragment {


    public static EditText reseivAddress;
    ImageButton scanBtn;
    Button transactionBtn;
    EditText ammount;
    private SessionHandler session;

    public SendFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send, container, false);
        session = new SessionHandler(getContext());
        scanBtn = view.findViewById(R.id.scanBtn);
        transactionBtn = view.findViewById(R.id.transactionBtn);
        reseivAddress = view.findViewById(R.id.ReseivAddress);
        ammount = view.findViewById(R.id.ammount);

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ScanCodeActivity.class);
                startActivity(intent);
            }
        });

        transactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    //transaction();
                    //openDialog();
                    transaction();
                }
            }
        });

        return view;
    }
/*
    void transaction() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_TRANSACTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            String txHash = obj.getString("txHash");
                            Toast.makeText(getContext(), txHash, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "response error", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("sender", session.getAddress());
                params.put("receiver", reseivAddress.getText().toString());
                //params.put("amount", "100");
                params.put("amount", ammount.getText().toString());
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }*/

    void transaction() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_VALIDATE_ADRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            boolean resulta = obj.getBoolean("resultat");
                            int ammountInt = Integer.parseInt(ammount.getText().toString());
                            double tranAmmount = ammountInt + ammountInt * 0.01 ;
                            if (!resulta) {
                                openAccountDialog();
                            } else if (session.getBalance()-tranAmmount<0) {
                                openBalanceDialog();
                            }else {
                                openDialog();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "response error", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("address", reseivAddress.getText().toString());
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private boolean validateInputs() {
        if (reseivAddress.getText().toString().equals("")) {
            reseivAddress.setError("required");
            reseivAddress.requestFocus();
            return false;
        }
        if (ammount.getText().toString().equals("")) {
            ammount.setError("required");
            ammount.requestFocus();
            return false;
        }

        return true;
    }

    public void openDialog() {
        Bundle args = new Bundle();
        args.putString("address", reseivAddress.getText().toString());
        args.putString("amount", ammount.getText().toString());
        TransactionDialog transactionDialog = new TransactionDialog();
        transactionDialog.setArguments(args);
        transactionDialog.show(getFragmentManager(), "transaction dialog");
    }

    public void openBalanceDialog() {
        TransanctionBalanceDialog transanctionBalanceDialog = new TransanctionBalanceDialog();
        transanctionBalanceDialog.show(getFragmentManager(), "transaction balance dialog");
    }

    public void openAccountDialog() {
        TransactionAccountDialog transactionAccountDialog = new TransactionAccountDialog();
        transactionAccountDialog.show(getFragmentManager(), "transaction account dialog");
    }

}
