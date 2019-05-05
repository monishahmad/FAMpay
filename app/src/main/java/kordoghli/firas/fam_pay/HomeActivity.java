package kordoghli.firas.fam_pay;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import kordoghli.firas.fam_pay.Menu.market.MarketFragment;
import kordoghli.firas.fam_pay.Menu.receive.ReceiveFragment;
import kordoghli.firas.fam_pay.Menu.send.SendFragment;
import kordoghli.firas.fam_pay.Menu.settings.SettingsFragment;
import kordoghli.firas.fam_pay.Menu.wallet.WalletFragment;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.wallet:
                            selectedFragment = new WalletFragment();
                            break;

                        case R.id.send:
                            selectedFragment = new SendFragment();
                            break;

                        case R.id.receive:
                            selectedFragment = new ReceiveFragment();
                            break;

                        case R.id.settings:
                            selectedFragment = new SettingsFragment();
                            break;
                        case R.id.market:
                            selectedFragment = new MarketFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WalletFragment()).commit();

    }
}
