package kordoghli.firas.fam_pay.Menu.wallet;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import kordoghli.firas.fam_pay.R;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private ArrayList<TransactionItem> mTransactionList;

    public TransactionAdapter(ArrayList<TransactionItem> transactionItems) {
        mTransactionList = transactionItems;
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
        transactionViewHolder.ammount.setText(transactionItem.getAmmount());
        transactionViewHolder.date.setText(transactionItem.getDate());
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
