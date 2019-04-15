package kordoghli.firas.fam_pay.Session;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

import kordoghli.firas.fam_pay.HomeActivity;
import kordoghli.firas.fam_pay.R;

public class LoginActivity extends AppCompatActivity {

    PatternLockView mPatternLockView;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        password = preferences.getString("password","0");

        mPatternLockView = findViewById(R.id.pattern_lock_view);
        mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                if (password.equals(PatternLockUtils.patternToString(mPatternLockView, pattern))) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,"wrong password",Toast.LENGTH_LONG).show();
                    mPatternLockView.clearPattern();
                }
            }

            @Override
            public void onCleared() {

            }
        });
    }
}
