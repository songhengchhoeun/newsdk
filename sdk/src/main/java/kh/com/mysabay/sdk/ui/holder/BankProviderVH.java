package kh.com.mysabay.sdk.ui.holder;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;

import kh.com.mysabay.sdk.R;
import kh.com.mysabay.sdk.databinding.PartialBankProviderItemBinding;

/**
 * Created by Tan Phirum on 4/1/20
 * Gmail phirumtan@gmail.com
 */
public class BankProviderVH extends RecyclerView.ViewHolder {

    public PartialBankProviderItemBinding view;

    public BankProviderVH(@NonNull View itemView) {
        super(itemView);
        view = DataBindingUtil.bind(itemView);
    }

    public void setBankName(String bankName) {
        view.bankName.setText(bankName);
    }

    public void showBankIcon(Context context, String url) {
        Glide.with(context)
                .load(url)
//                .centerCrop()
//                .placeholder(R.drawable.ic_game_shop)
//                .error(R.drawable.ic_game_shop)
                .into(view.appCompatImageView2);
    }
}
