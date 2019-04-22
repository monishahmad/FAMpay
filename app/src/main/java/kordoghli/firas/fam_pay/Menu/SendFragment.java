package kordoghli.firas.fam_pay.Menu;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import kordoghli.firas.fam_pay.Data.URLs;
import kordoghli.firas.fam_pay.Data.VolleySingleton;
import kordoghli.firas.fam_pay.R;
import kordoghli.firas.fam_pay.ScanCodeActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class SendFragment extends Fragment {


    public SendFragment() {
        // Required empty public constructor
    }

    ImageButton scanBtn;
    Button transactionBtn;
    public static EditText reseivAddress;
    EditText ammount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_send, container, false);

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
                if (validateInputs()){
                    transaction();
                }
            }
        });

        return view;
    }

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
                SharedPreferences sharedPref = getActivity().getSharedPreferences("MyPrefs",0);
                String addressPref = sharedPref.getString("addressKey","");

                Map<String, String> params = new HashMap<>();
                params.put("sender", addressPref);
                params.put("receiver", reseivAddress.getText().toString());
                //params.put("amount", "100");
                params.put("amount", ammount.getText().toString());
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

}
