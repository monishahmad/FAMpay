package kordoghli.firas.fam_pay.Menu.wallet;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kordoghli.firas.fam_pay.Data.SessionHandler;
import kordoghli.firas.fam_pay.R;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    Context context;
    Dialog detailsDialog;
    private SessionHandler session;
    private ArrayList<TransactionItem> mTransactionList;


    public TransactionAdapter(ArrayList<TransactionItem> transactionItems, Context context) {
        mTransactionList = transactionItems;
        this.context = context;
    }


    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transaction_layout, viewGroup, false);
        final TransactionViewHolder transactionViewHolder = new TransactionViewHolder(view);

        detailsDialog = new Dialog(context);
        detailsDialog.setContentView(R.layout.transaction_details_dialog);


        transactionViewHolder.itemTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView senderD = detailsDialog.findViewById(R.id.tvSenderD);
                TextView receiverD = detailsDialog.findViewById(R.id.tvReceiverD);
                TextView ammountD = detailsDialog.findViewById(R.id.tvAmountD);
                TextView dateD = detailsDialog.findViewById(R.id.dateD);
                TextView feesD = detailsDialog.findViewById(R.id.tvFeesD);

                Double valueofFees = 0.0;
                valueofFees = Integer.parseInt(mTransactionList.get(transactionViewHolder.getAdapterPosition()).getAmmount()) * 0.01;

                senderD.setText(mTransactionList.get(transactionViewHolder.getAdapterPosition()).getSender());
                receiverD.setText(mTransactionList.get(transactionViewHolder.getAdapterPosition()).getReceiver());
                ammountD.setText(mTransactionList.get(transactionViewHolder.getAdapterPosition()).getAmmount());
                dateD.setText(mTransactionList.get(transactionViewHolder.getAdapterPosition()).getDate());
                feesD.setText(valueofFees.toString());
                detailsDialog.show();
            }
        });
        return transactionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.TransactionViewHolder transactionViewHolder, int i) {
        TransactionItem transactionItem = mTransactionList.get(i);

        transactionViewHolder.date.setText(transactionItem.getDate());
        session = new SessionHandler(context);

        if (session.getAddress().equals(transactionItem.getSender())) {
            transactionViewHolder.ammount.setText("- " + transactionItem.getAmmount());
            transactionViewHolder.ammount.setTextColor(Color.RED);
        } else {
            transactionViewHolder.ammount.setText("+ " + transactionItem.getAmmount());
            transactionViewHolder.ammount.setTextColor(Color.GREEN);
        }

    }

    @Override
    public int getItemCount() {
        return mTransactionList.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        public TextView date, ammount;
        public LinearLayout itemTransaction;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            ammount = itemView.findViewById(R.id.textView26);
            date = itemView.findViewById(R.id.textView25);
            itemTransaction = itemView.findViewById(R.id.itemTransaction);
        }
    }
}
