package kordoghli.firas.fam_pay.Menu.wallet;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import java.util.ArrayList;

import kordoghli.firas.fam_pay.Data.SessionHandler;
import kordoghli.firas.fam_pay.R;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private SessionHandler session;
    Context context;
    private ArrayList<TransactionItem> mTransactionList;

    public TransactionAdapter(ArrayList<TransactionItem> transactionItems,Context context) {
        mTransactionList = transactionItems;
        this.context = context;
    }


    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transaction_layout, viewGroup, false);
        TransactionViewHolder transactionViewHolder = new TransactionViewHolder(view);
        return transactionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.TransactionViewHolder transactionViewHolder, int i) {
        TransactionItem transactionItem = mTransactionList.get(i);

        transactionViewHolder.date.setText(transactionItem.getDate());
        session = new SessionHandler(context);

        if (session.getAddress().equals(transactionItem.getSender())){
            transactionViewHolder.ammount.setText("- "+transactionItem.getAmmount());
            transactionViewHolder.ammount.setTextColor(Color.RED);
        }else {
            transactionViewHolder.ammount.setText("+ "+transactionItem.getAmmount());
            transactionViewHolder.ammount.setTextColor(Color.GREEN);
        }

    }

    @Override
    public int getItemCount() {
        return mTransactionList.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        public TextView date, ammount;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            ammount = itemView.findViewById(R.id.textView26);
            date = itemView.findViewById(R.id.textView25);
        }
    }
}
