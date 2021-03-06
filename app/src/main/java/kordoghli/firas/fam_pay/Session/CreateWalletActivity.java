package kordoghli.firas.fam_pay.Session;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import kordoghli.firas.fam_pay.Data.SessionHandler;
import kordoghli.firas.fam_pay.R;

public class CreateWalletActivity extends AppCompatActivity {
    String address;
    Button createAccount;
    TextView addressTv;
    private SessionHandler session;
    ClipboardManager clipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        address = extras.getString("public_address");
        session = new SessionHandler(getApplicationContext());
        setContentView(R.layout.activity_create_wallet);

        addressTv = findViewById(R.id.textView10);
        addressTv.setText(address);

        createAccount = findViewById(R.id.toPattern);
/*
        clipboardManager.setText(address);
        Toast.makeText(CreateWalletActivity.this,"copied to clipboard", Toast.LENGTH_LONG).show();*/

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.login(address);
                Intent intent = new Intent(CreateWalletActivity.this, CreataPasswordActivity.class);
                startActivity(intent);
            }
        });
    }


}
