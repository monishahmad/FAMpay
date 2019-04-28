package kordoghli.firas.fam_pay;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TransactionDialog extends AppCompatDialogFragment {
    EditText password;
    TextView receiver,balance,ammount,fees,newBalance;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.transaction_dialog,null);

        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        password = view.findViewById(R.id.editText4);
        receiver = view.findViewById(R.id.tvReceiver);
        balance = view.findViewById(R.id.tvBalance);
        ammount = view.findViewById(R.id.tvAmount);
        fees = view.findViewById(R.id.tvFees);
        newBalance = view.findViewById(R.id.tvNewBalance);

        Bundle mArgs = getArguments();
        String valueAddress = mArgs.getString("address");
        String valueAmount = mArgs.getString("amount");

        double valueFees = 0 ;
        valueFees = Integer.parseInt(valueAmount) * 0.01;

        receiver.setText(valueAddress);
        ammount.setText(valueAmount);
        fees.setText(String.valueOf(valueFees));
        return builder.create();
    }

    private boolean validateInputs() {

        if (password.getText().toString().equals("")) {
            password.setError("required");
            password.requestFocus();
            return false;
        }

        return true;
    }
}
