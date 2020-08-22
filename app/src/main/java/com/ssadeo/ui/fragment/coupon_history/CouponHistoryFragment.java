package com.ssadeo.ui.fragment.coupon_history;


import android.content.Context;
import androidx.annotation.NonNull;
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
import com.ssadeo.data.network.model.Coupon;
import com.ssadeo.data.network.model.Promocode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CouponHistoryFragment extends BaseFragment implements CouponHistoryIView {

    @BindView(R.id.coupon_history_rv)
    RecyclerView couponHistoryRv;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    Unbinder unbinder;

    CouponHistoryAdapter adapter;

    private CouponHistoryPresenter<CouponHistoryFragment> presenter = new CouponHistoryPresenter<>();
    String numberFormat;
    public CouponHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_coupon_history;
    }

    @Override
    public View initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);
        numberFormat = getNumberFormat();

        couponHistoryRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        couponHistoryRv.setItemAnimator(new DefaultItemAnimator());

        progressBar.setVisibility(View.VISIBLE);
        presenter.coupon();
        return view;
    }

    @Override
    public void onSuccess(List<Coupon> couponList) {
        progressBar.setVisibility(View.GONE);
        adapter = new CouponHistoryAdapter(getActivity(), couponList);
        couponHistoryRv.setAdapter(adapter);

        if (couponList.isEmpty()) {
            errorLayout.setVisibility(View.VISIBLE);
        } else {
            errorLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public void onError(Throwable e) {
        progressBar.setVisibility(View.GONE);
    }

    private class CouponHistoryAdapter extends RecyclerView.Adapter<CouponHistoryAdapter.MyViewHolder> {

        private List<Coupon> list;
        private Context context;

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private CardView itemView;
            private TextView status, promocode, discount, createdAt;

            MyViewHolder(View view) {
                super(view);
                itemView = (CardView) view.findViewById(R.id.item_view);
                status = (TextView) view.findViewById(R.id.status);
                promocode = (TextView) view.findViewById(R.id.promocode);
                discount = (TextView) view.findViewById(R.id.discount);
                createdAt = (TextView) view.findViewById(R.id.created_at);
                //itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
            }
        }


        private CouponHistoryAdapter(Context context, List<Coupon> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_coupon_history, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Coupon item = list.get(position);
            holder.status.setText(item.getStatus());
            if(item.getPromocode() != null){
                Promocode promocode = item.getPromocode();
                holder.promocode.setText(promocode.getPromoCode());
                holder.createdAt.setText(promocode.getCreatedAt());

                if(promocode.getDiscountType().equalsIgnoreCase("percent")){
                    holder.discount.setText(getString(R.string._off, promocode.getDiscount()));
                }else if(promocode.getDiscountType().equalsIgnoreCase("amount")){
                    holder.discount.setText(numberFormat +(promocode.getDiscount()));
                }


            }
            //holder.cardNumber.setText(String.format("XXXX-XXXX-XXXX-%s", obj.getLastFour()));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

}
