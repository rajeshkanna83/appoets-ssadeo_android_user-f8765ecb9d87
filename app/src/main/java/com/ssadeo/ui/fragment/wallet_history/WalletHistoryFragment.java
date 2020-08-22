package com.ssadeo.ui.fragment.wallet_history;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ssadeo.user.R;
import com.ssadeo.base.BaseFragment;
import com.ssadeo.data.network.model.Wallet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalletHistoryFragment extends BaseFragment implements WalletHistoryIView {

    @BindView(R.id.wallet_history_rv)
    RecyclerView walletHistoryRv;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    Unbinder unbinder;

    String numberFormat;
    WalletHistoryAdapter adapter;
    private WalletHistoryPresenter<WalletHistoryFragment> presenter = new WalletHistoryPresenter<>();


    public WalletHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wallet_history;
    }

    @Override
    public View initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);
        numberFormat = getNumberFormat();
        walletHistoryRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        walletHistoryRv.setItemAnimator(new DefaultItemAnimator());

        presenter.wallet();
        return view;
    }

    @Override
    public void onSuccess(List<Wallet> walletList) {
        progressBar.setVisibility(View.GONE);
        adapter = new WalletHistoryAdapter(getActivity(), walletList);
        walletHistoryRv.setAdapter(adapter);

        if (walletList.isEmpty()) {
            errorLayout.setVisibility(View.VISIBLE);
        } else {
            errorLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError(Throwable e) {
        progressBar.setVisibility(View.GONE);

    }


    private class WalletHistoryAdapter extends RecyclerView.Adapter<WalletHistoryAdapter.MyViewHolder> {

        private List<Wallet> list;
        private Context context;

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private CardView itemView;
            private TextView createdAt, via, amount, status;

            MyViewHolder(View view) {
                super(view);
                itemView = (CardView) view.findViewById(R.id.item_view);
                createdAt = (TextView) view.findViewById(R.id.created_at);
                via = (TextView) view.findViewById(R.id.via);
                amount = (TextView) view.findViewById(R.id.amount);
                status = (TextView) view.findViewById(R.id.status);
                //itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {

            }
        }


        private WalletHistoryAdapter(Context context, List<Wallet> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_wallet_history, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Wallet item = list.get(position);
            holder.createdAt.setText(item.getCreatedAt());
            holder.amount.setText(numberFormat + (item.getAmount()));
            holder.via.setText(item.getVia());
            holder.createdAt.setText(item.getCreatedAt());

            if (item.getStatus().equalsIgnoreCase("CREDITED")) {
                holder.status.setText(getString(R.string.credited_by));
            } else if (item.getStatus().equalsIgnoreCase("DEBITED")) {
                holder.status.setText(getString(R.string.debited_from));
            } else {
                holder.status.setText(item.getStatus());
            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

}
