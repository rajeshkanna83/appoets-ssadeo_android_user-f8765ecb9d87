package com.ssadeo.ui.activity.payment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ssadeo.base.BaseActivity;
import com.ssadeo.data.network.model.Card;
import com.ssadeo.ui.activity.add_card.AddCardActivity;
import com.ssadeo.user.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentActivity extends BaseActivity implements PaymentIView {

    public static final int PICK_PAYMENT_METHOD = 12;
    @BindView(R.id.add_card)
    TextView addCard;
    @BindView(R.id.cash)
    TextView cash;
    @BindView(R.id.cards_rv)
    RecyclerView cardsRv;
    List<Card> cardsList = new ArrayList<>();
    private PaymentPresenter<PaymentActivity> presenter = new PaymentPresenter<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_payment;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);

        CardAdapter adapter = new CardAdapter(this, cardsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cardsRv.setLayoutManager(mLayoutManager);
        cardsRv.setItemAnimator(new DefaultItemAnimator());
        cardsRv.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.card();
    }

    @OnClick({R.id.add_card, R.id.cash, R.id.cc_avenue,R.id.flutter_wave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_card:
                startActivity(new Intent(this, AddCardActivity.class));
                break;
            case R.id.cash:
                finishResult("CASH");
                break;
            case R.id.cc_avenue:
                finishResult("CC_AVENUE");
                break;
            case R.id.flutter_wave:
                finishResult("FLUTTERWAVE");
                break;
        }
    }

    public void deleteCard(@NonNull Card card) {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.are_sure_you_want_to_delete))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(getString(R.string.delete), (dialog, whichButton) -> presenter.deleteCard(card.getCardId()))
                .setNegativeButton(getString(R.string.no), null).show();
    }

    public void finishResult(String mode) {
        Intent intent = new Intent();
        intent.putExtra("payment_mode", mode);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onSuccess(Object card) {
        Toast.makeText(activity(), "Card Deleted Successfully", Toast.LENGTH_SHORT).show();
        presenter.card();
    }

    @Override
    public void onSuccess(List<Card> cards) {
        cardsList.clear();
        cardsList.addAll(cards);
        cardsRv.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onError(Throwable e) {

    }


    public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

        private List<Card> list;
        private Context context;

        public CardAdapter(Context context, List<Card> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_card, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Card item = list.get(position);
            holder.card.setText(getString(R.string.card_, item.getLastFour()));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
            private LinearLayout itemView;
            private TextView card;

            MyViewHolder(View view) {
                super(view);
                itemView = (LinearLayout) view.findViewById(R.id.item_view);
                card = (TextView) view.findViewById(R.id.card);
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }

            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                Card card = list.get(position);
                if (view.getId() == R.id.item_view) {
                    Intent intent = new Intent();
                    intent.putExtra("payment_mode", "CARD");
                    intent.putExtra("card_id", card.getCardId());
                    intent.putExtra("card_last_four", card.getLastFour());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }

            }

            @Override
            public boolean onLongClick(View v) {
                int position = getAdapterPosition();
                Card card = list.get(position);
                if (v.getId() == R.id.item_view) {
                    deleteCard(card);
                }
                return true;
            }
        }
    }
}
