package kordoghli.firas.fam_pay.Menu;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import kordoghli.firas.fam_pay.Session.CreataPasswordActivity;
import kordoghli.firas.fam_pay.R;
import kordoghli.firas.fam_pay.Session.WelcomeActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
    private SessionHandler session;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        session=new SessionHandler(getContext());
        TextView changePattern = view.findViewById(R.id.changePattern);
        TextView logOut = view.findViewById(R.id.logout);

        changePattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreataPasswordActivity.class);
                SettingsFragment.this.startActivity(intent);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logout();
                Intent intent = new Intent(getContext(), WelcomeActivity.class);
                SettingsFragment.this.startActivity(intent);
            }
        });

        return view;
    }
}
