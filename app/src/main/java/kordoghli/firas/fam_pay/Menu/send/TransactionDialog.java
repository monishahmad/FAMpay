package kordoghli.firas.fam_pay.Menu.send;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import kordoghli.firas.fam_pay.HomeActivity;
import kordoghli.firas.fam_pay.R;

public class TransactionDialog extends AppCompatDialogFragment {
    EditText password;
    Button confirmTransaction;
    TextView receiver, balance, ammount, fees, newBalance;
    private SessionHandler session;
    private ProgressDialog pDialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        session = new SessionHandler(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.transaction_dialog, null);

        password = view.findViewById(R.id.editText4);
        receiver = view.findViewById(R.id.tvReceiver);
        balance = view.findViewById(R.id.tvBalance);
        ammount = view.findViewById(R.id.tvAmount);
        fees = view.findViewById(R.id.tvFees);
        newBalance = view.findViewById(R.id.tvNewBalance);
        confirmTransaction = view.findViewById(R.id.button7);

        Bundle mArgs = getArguments();
        String valueAddress = mArgs.getString("address");
        String valueAmount = mArgs.getString("amount");

        double valueFees = 0;
        valueFees = Integer.parseInt(valueAmount) * 0.01;

        receiver.setText(valueAddress);
        ammount.setText(valueAmount);
        balance.setText(session.getBalance().toString());
        double valueNewBalance = session.getBalance() - Integer.parseInt(valueAmount) + valueFees;
        newBalance.setText(String.valueOf(valueNewBalance));
        fees.setText(String.valueOf(valueFees));

        confirmTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction();
            }
        });

        builder.setView(view);
        return builder.create();
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    void transaction() {
        displayLoader();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_TRANSACTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("response")) {
                                password.setError("wrong password");
                                password.requestFocus();
                                pDialog.dismiss();
                            } else {
                                pDialog.dismiss();
                                Intent intent = new Intent(getContext(), HomeActivity.class);
                                startActivity(intent);
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
                params.put("sender", session.getAddress());
                params.put("receiver", receiver.getText().toString());
                params.put("amount", ammount.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }


}
