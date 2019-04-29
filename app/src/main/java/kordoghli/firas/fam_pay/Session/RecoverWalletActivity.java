package kordoghli.firas.fam_pay.Session;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RecoverWalletActivity extends AppCompatActivity {
    Button login;
    EditText password, adress;
    private ProgressDialog pDialog;
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        setContentView(R.layout.activity_recover_wallet);

        login = this.findViewById(R.id.button5);
        password = this.findViewById(R.id.editText3);
        adress = this.findViewById(R.id.editText2);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(RecoverWalletActivity.this);
        pDialog.setMessage("Logging In.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    void login() {
        //displayLoader();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            Boolean resp = obj.getBoolean("response");

                            if (resp) {
                                session.login(adress.getText().toString());
                                Intent intent = new Intent(RecoverWalletActivity.this, CreataPasswordActivity.class);
                                startActivity(intent);

                            } else if (resp == false) {
                                AlertDialog alertDialog = new AlertDialog.Builder(RecoverWalletActivity.this).create(); //Read Update
                                alertDialog.setTitle("Sorry");
                                alertDialog.setMessage("please check your address and password and try again");
                                alertDialog.setButton("retry..", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                alertDialog.show();
                            }


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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("address", adress.getText().toString());
                //params.put("address", "0x3c817b889fdfd96d8cf39df7f223d8b7cfe2e456");
                params.put("password", password.getText().toString());
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
