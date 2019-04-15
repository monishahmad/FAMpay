package kordoghli.firas.fam_pay.Session;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import kordoghli.firas.fam_pay.CreateWalletActivity;
import kordoghli.firas.fam_pay.R;
import kordoghli.firas.fam_pay.RecoverWalletActivity;

public class WelcomeActivity extends AppCompatActivity {
    Button toLogin,toCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
