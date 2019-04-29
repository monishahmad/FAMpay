package kordoghli.firas.fam_pay.Data;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class SessionHandler {

    private static final String PREF_NAME = "UserSession";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_BALANCE = "balance";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_EMPTY = "";

    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    public SessionHandler(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    public void login (String address){
        mEditor.putString(KEY_ADDRESS,address);
        Date date = new Date();
        //Set user session for next 7 days
        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }

    public void saveBalance (int balance){
        mEditor.putInt(KEY_BALANCE,balance);
        mEditor.commit();
    }

    public boolean isLoggedIn(){
        Date currentDate = new Date();
        long millis = mPreferences.getLong(KEY_EXPIRES, 0);

        /* If shared preferences does not have a value
         then user is not logged in
         */
        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);

        /* Check if session is expired by comparing
        current date and Session expiry date
        */
        return currentDate.before(expiryDate);
    }

    public String getAddress(){
        //Check if user is logged in first
        if (!isLoggedIn()) {
            return null;
        }
        return mPreferences.getString(KEY_ADDRESS,KEY_EMPTY);
    }

    public Integer getBalance(){
        //Check if user is logged in first
        if (!isLoggedIn()) {
            return null;
        }
        return mPreferences.getInt(KEY_BALANCE,0);
    }

    public void logout(){
        mEditor.clear();
        mEditor.commit();
    }

}
