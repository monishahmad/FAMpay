package kordoghli.firas.fam_pay.Data;

public class URLs {
    private static final String ROOT_URL = "http://192.168.1.8:3017/";
    public static final String URL_GET_BALANCE = ROOT_URL + "getBalance";
    public static final String URL_TRANSACTION = ROOT_URL + "transaction";
    public static final String URL_LOGIN = ROOT_URL + "loginToWallet";
    public static final String URL_SIGNIN = ROOT_URL + "createAccount";
    public static final String URL_VALIDATE_ADRESS = ROOT_URL + "VerifAccount";
    public static final String URL_COINS_API = "https://api.coinmarketcap.com/v1/ticker/?limit=10";
}
