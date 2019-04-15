package kordoghli.firas.fam_pay;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import kordoghli.firas.fam_pay.Data.URLs;
import kordoghli.firas.fam_pay.Data.VolleySingleton;
import kordoghli.firas.fam_pay.Session.CreataPasswordActivity;
import kordoghli.firas.fam_pay.Session.WelcomeActivity;

public class RecoverWalletActivity extends AppCompatActivity {
    Button login;
    EditText password,adress;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Address = "addressKey";
    public static final String Password = "passwordKey";
    public static final String Session = "sessionKey";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    void login(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            Boolean resp = obj.getBoolean("response");

                            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$"+resp);
                            if (resp){
                                AlertDialog alertDialog = new AlertDialog.Builder(RecoverWalletActivity.this).create(); //Read Update
                                alertDialog.setTitle("Login");
                                alertDialog.setMessage("you have successfully logged in to your wallet");
                                alertDialog.setButton("Continue..", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        //editor.putString(Address, "0x3c817b889fdfd96d8cf39df7f223d8b7cfe2e456");
                                        editor.putString(Address, adress.getText().toString());
                                        editor.putString(Password, password.getText().toString());
                                        editor.putBoolean(Session, true);
                                        editor.apply();

                                        Intent intent = new Intent(RecoverWalletActivity.this, CreataPasswordActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                alertDialog.show();
                            }else if (resp == false){
                                AlertDialog alertDialog = new AlertDialog.Builder(RecoverWalletActivity.this).create(); //Read Update
                                alertDialog.setTitle("Sorry");
                                alertDialog.setMessage("please check your address and password and try again");
                                alertDialog.setButton("retry..", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putBoolean(Session, false);
                                        editor.apply();
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
