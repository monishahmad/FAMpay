package kordoghli.firas.fam_pay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class CreateWallet2Activity extends AppCompatActivity {
    EditText password;
    Button nextStep;
    private ProgressDialog pDialog;
    //private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //session = new SessionHandler(getApplicationContext());
        setContentView(R.layout.activity_create_wallet2);

        password = findViewById(R.id.editText);
        nextStep = findViewById(R.id.button4);

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(password.getText().toString());
                createAccount();

            }
        });
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(CreateWallet2Activity.this);
        pDialog.setMessage("Creating your account.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void createAccount() {
        displayLoader();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SIGNIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            String adresse = obj.getString("adresse");
                            //session.login(adresse);
                            Intent intent = new Intent(CreateWallet2Activity.this, CreateWalletActivity.class);
                            intent.putExtra("public_address",adresse);
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "response error", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("password", password.getText().toString());
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private boolean validateInputs() {
        if (password.getText().toString().equals("")) {
            password.setError("required");
            password.requestFocus();
            return false;
        }
        return true;
    }
}
