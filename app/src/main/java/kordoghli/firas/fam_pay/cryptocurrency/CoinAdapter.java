package kordoghli.firas.fam_pay.cryptocurrency;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kordoghli.firas.fam_pay.R;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.CoinViewHolder> {

    private ArrayList<CoinItem> mCoinList;

    public static class CoinViewHolder extends RecyclerView.ViewHolder {
        public ImageView coinIcon;
        public TextView coinSymbol,coinName,coinPrice,oneHourChange,twentyFourChange,sevenDayChange;

        public CoinViewHolder(@NonNull View itemView) {
            super(itemView);
            coinIcon = itemView.findViewById(R.id.coinIcon);
            coinSymbol = itemView.findViewById(R.id.coinSymbol);
            coinName = itemView.findViewById(R.id.coinName);
            coinPrice = itemView.findViewById(R.id.priceUsd);
            oneHourChange = itemView.findViewById(R.id.oneHour);
            twentyFourChange = itemView.findViewById(R.id.twentyFourHour);
            sevenDayChange = itemView.findViewById(R.id.sevenDay);
        }
    }

    public CoinAdapter(ArrayList<CoinItem> coinItems){
        mCoinList = coinItems;
    }

    @NonNull
    @Override
    public CoinViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.coin_layout,viewGroup,false);
        CoinViewHolder coinViewHolder = new CoinViewHolder(view);
        return coinViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CoinViewHolder coinViewHolder, int i) {
        CoinItem coinItem = mCoinList.get(i);

        coinViewHolder.coinName.setText(coinItem.getName());
        coinViewHolder.coinSymbol.setText(coinItem.getSymbol());
        coinViewHolder.coinPrice.setText(coinItem.getPrice_usd());
        coinViewHolder.oneHourChange.setText(coinItem.getPercent_change_1h());
        coinViewHolder.twentyFourChange.setText(coinItem.getPercent_change_24h());
        coinViewHolder.sevenDayChange.setText(coinItem.getPercent_change_7d());
    }

    @Override
    public int getItemCount() {
        return mCoinList.size();
    }
}
