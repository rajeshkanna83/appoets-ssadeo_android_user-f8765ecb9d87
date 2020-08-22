package com.ssadeo.ui.fragment.upcoming_trip;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.core.view.ViewCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ssadeo.user.R;
import com.ssadeo.base.BaseActivity;
import com.ssadeo.base.BaseFragment;
import com.ssadeo.common.CancelRequestInterface;
import com.ssadeo.data.network.model.Datum;
import com.ssadeo.data.network.model.Provider;
import com.ssadeo.ui.activity.upcoming_trip_detail.UpcomingTripDetailActivity;
import com.ssadeo.ui.fragment.cancel_ride.CancelRideDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingTripFragment extends BaseFragment implements UpcomingTripIView, CancelRequestInterface {

    @BindView(R.id.upcoming_trip_rv)
    RecyclerView upcomingTripRv;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    Unbinder unbinder;
    private UpcomingTripPresenter<UpcomingTripFragment> presenter = new UpcomingTripPresenter<>();
    private CancelRequestInterface callback;


    List<Datum> list = new ArrayList<>();
    TripAdapter adapter;

    public UpcomingTripFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_upcoming_trip;
    }
/*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }*/

    @Override
    public View initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);
        callback = this;
        adapter = new TripAdapter(getActivity(), list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        upcomingTripRv.setLayoutManager(mLayoutManager);
        upcomingTripRv.setItemAnimator(new DefaultItemAnimator());
        upcomingTripRv.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        progressBar.setVisibility(View.VISIBLE);
        presenter.upcomingTrip();
    }

    private void alertCancel(Integer requestId) {
        new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.cancel_request))
                .setMessage(R.string.are_sure_you_want_to_cancel_the_request)
                .setCancelable(true)
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                    showLoading();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("request_id", requestId);
                    presenter.cancelRequest(map);
                }).setNegativeButton(getString(R.string.no), (dialog, which) -> dialog.cancel())
                .show();
    }

    @Override
    public void onSuccess(List<Datum> datumList) {
        progressBar.setVisibility(View.GONE);

        list.clear();
        list.addAll(datumList);
        adapter.notifyDataSetChanged();

        if (list.isEmpty()) {
            errorLayout.setVisibility(View.VISIBLE);
        } else {
            errorLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSuccess(Object object) {
        hideLoading();
        presenter.upcomingTrip();
    }

    @Override
    public void onError(Throwable e) {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void cancelRequestMethod() {
        presenter.upcomingTrip();
    }

    private class TripAdapter extends RecyclerView.Adapter<TripAdapter.MyViewHolder> {

        private List<Datum> list;
        private Context context;

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private CardView itemView;
            private TextView bookingId, payable, scheduleAt;
            private ImageView staticMap;
            private Button cancel;
            CircleImageView avatar;

            MyViewHolder(View view) {
                super(view);
                itemView = (CardView) view.findViewById(R.id.item_view);
                bookingId = (TextView) view.findViewById(R.id.booking_id);
                payable = (TextView) view.findViewById(R.id.payable);
                scheduleAt = (TextView) view.findViewById(R.id.schedule_at);
                staticMap = (ImageView) view.findViewById(R.id.static_map);
                cancel = (Button) view.findViewById(R.id.cancel);
                avatar = (CircleImageView) view.findViewById(R.id.avatar);
                itemView.setOnClickListener(this);
                cancel.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                BaseActivity.DATUM = list.get(position);
                if (view.getId() == R.id.item_view) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity(), staticMap, ViewCompat.getTransitionName(staticMap));
                    Intent intent = new Intent(getActivity(), UpcomingTripDetailActivity.class);
                    startActivity(intent, options.toBundle());
                } else if (view.getId() == R.id.cancel) {
                    CancelRideDialogFragment cancelRideDialogFragment = new CancelRideDialogFragment(callback);
                    cancelRideDialogFragment.show(activity().getSupportFragmentManager(), cancelRideDialogFragment.getTag());
                    //alertCancel(BaseActivity.DATUM.getId());
                }

            }
        }


        private TripAdapter(Context context, List<Datum> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_upcoming_trip, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Datum datum = list.get(position);
            holder.scheduleAt.setText(datum.getScheduleAt());
            holder.bookingId.setText(datum.getBookingId());
            Glide.with(activity()).load(datum.getStaticMap()).apply(RequestOptions.placeholderOf(R.drawable.ic_launcher_background).dontAnimate().error(R.drawable.ic_launcher_background)).into(holder.staticMap);

            Provider provider = datum.getProvider();
            if (provider != null) {
                Glide.with(activity()).load(provider.getAvatar()).apply(RequestOptions.placeholderOf(R.drawable.user).dontAnimate().error(R.drawable.user)).into(holder.avatar);
            }


        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}
