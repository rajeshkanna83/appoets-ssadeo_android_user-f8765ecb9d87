package com.ssadeo.ui.fragment.service;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ssadeo.user.R;
import com.ssadeo.base.BaseFragment;
import com.ssadeo.data.network.model.EstimateFare;
import com.ssadeo.data.network.model.RateCardResponse;
import com.ssadeo.data.network.model.Service;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ssadeo.base.BaseActivity.RIDE_REQUEST;


/**
 * A simple {@link Fragment} subclass.
 */
public class RateCardFragment extends BaseFragment implements ServiceIView{

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.capacity)
    TextView capacity;
    @BindView(R.id.base_fare)
    TextView baseFare;
    @BindView(R.id.fare_km)
    TextView fareKm;
    @BindView(R.id.done)
    Button done;

    String  numberFormat = getNumberFormat();
    private ServicePresenter<RateCardFragment> presenter = new ServicePresenter<>();

    public static Service SERVICE = new Service();

    public RateCardFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_rate_card;
    }

    @Override
    public View initView(View view) {
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        initView(SERVICE);

        return view;
    }

    void initView(@NonNull Service service) {
        name.setText(service.getName());
        capacity.setText(String.valueOf(service.getCapacity()));
        baseFare.setText(numberFormat +(service.getFixed()));
        fareKm.setText(numberFormat +(service.getPrice()));
        Glide.with(activity()).load(service.getImage()).into(image);

        RIDE_REQUEST.put("service_type", service.getId());
        presenter.estimateFareService(RIDE_REQUEST);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.done)
    public void onViewClicked() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onSuccess(List<Service> serviceList) {

    }

    @Override
    public void onSuccess(RateCardResponse service) {
        if(service.getService() != null){
            baseFare.setText(String.format("%s / %sKM", numberFormat + (service.getService().getFixed()), service.getKm()));
        }
        fareKm.setText(String.format("%s / 1KM", numberFormat +(service.getFare())));
    }

    @Override
    public void onSuccess(EstimateFare estimateFare) {

    }

    @Override
    public void onError(Throwable e) {
        hideLoading();
    }
}
