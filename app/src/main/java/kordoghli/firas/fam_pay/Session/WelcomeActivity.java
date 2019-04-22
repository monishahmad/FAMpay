package kordoghli.firas.fam_pay.Session;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import kordoghli.firas.fam_pay.CreateWalletActivity;
import kordoghli.firas.fam_pay.Data.SessionHandler;
import kordoghli.firas.fam_pay.R;
import kordoghli.firas.fam_pay.RecoverWalletActivity;

public class WelcomeActivity extends AppCompatActivity {
    Button toLogin, toCreate;
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());

        if (session.isLoggedIn()) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            finish();
        }
        setContentView(R.layout.activity_welcome);

        toLogin = this.findViewById(R.id.button2);
        toCreate = this.findViewById(R.id.button3);

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, RecoverWalletActivity.class);
                startActivity(intent);
            }
        });

        toCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, CreateWalletActivity.class);
                startActivity(intent);
            }
        });
    }
}
