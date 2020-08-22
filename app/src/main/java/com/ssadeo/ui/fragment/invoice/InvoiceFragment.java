package com.ssadeo.ui.fragment.invoice;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RaveUiManager;
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.ssadeo.base.BaseBottomSheetDialogFragment;
import com.ssadeo.common.cc_avenue.AvenuesParams;
import com.ssadeo.common.cc_avenue.CCAvenueWebViewActivity;
import com.ssadeo.common.cc_avenue.ServiceUtility;
import com.ssadeo.data.SharedHelper;
import com.ssadeo.data.network.model.Datum;
import com.ssadeo.data.network.model.Message;
import com.ssadeo.data.network.model.Payment;
import com.ssadeo.ui.activity.main.MainActivity;
import com.ssadeo.user.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.HttpException;
import retrofit2.Response;

import static com.ssadeo.base.BaseActivity.DATUM;
import static com.ssadeo.user.BuildConfig.FLW_ENC_KEY;
import static com.ssadeo.user.BuildConfig.FLW_PUB_KEY;

public class InvoiceFragment extends BaseBottomSheetDialogFragment implements InvoiceIView {

    @BindView(R.id.payment_mode)
    TextView paymentMode;
    @BindView(R.id.pay_now)
    Button payNow;
    @BindView(R.id.done)
    Button done;
    @BindView(R.id.booking_id)
    TextView bookingId;

    NumberFormat numberFormat = getNumberFormat();
    @BindView(R.id.distance)
    TextView distance;
    @BindView(R.id.travel_time)
    TextView travelTime;
    @BindView(R.id.fixed)
    TextView fixed;
    @BindView(R.id.tax)
    TextView tax;
    @BindView(R.id.total)
    TextView total;
    @BindView(R.id.payable)
    TextView payable;
    @BindView(R.id.wallet_detection)
    TextView walletDetection;
    @BindView(R.id.lnrWalletDetection)
    LinearLayout lnrWalletDetection;
    @BindView(R.id.discount)
    TextView discount;
    @BindView(R.id.lnrDiscount)
    LinearLayout lnrDiscount;
    @BindView(R.id.distance_price)
    TextView distancePrice;
    @BindView(R.id.peek_hour_charges)
    TextView peekHourCharges;
    @BindView(R.id.night_fare)
    TextView nightFare;
    //    @BindView(R.id.base_fare_layout)
//    LinearLayout baseFareLayout;
//    @BindView(R.id.driver_allowance_layout)
//    LinearLayout driverAllowanceLayout;
//    @BindView(R.id.driver_beta)
    TextView driverBeta;
    @BindView(R.id.layout_normal_flow)
    LinearLayout layout_normal_flow;
    @BindView(R.id.layout_rental_flow)
    LinearLayout layout_rental_flow;
    @BindView(R.id.rental_normal_price)
    TextView rentalNormalPrice;
    @BindView(R.id.rental_total_distance_km)
    TextView rentalTotalDistance;
    @BindView(R.id.rental_extra_hr_km_price)
    TextView rentalExtraHrKmPrice;
    @BindView(R.id.rental_travel_time)
    TextView rentalTravelTime;
    @BindView(R.id.rental_hours)
    TextView rentalHours;

    @BindView(R.id.layout_outstation_flow)
    LinearLayout layout_outstation_flow;
    @BindView(R.id.outstation_distance_travelled)
    TextView outstationDistanceTravelled;
    @BindView(R.id.outstation_distance_fare)
    TextView outstationDistanceFare;
    @BindView(R.id.outstation_driver_beta)
    TextView outstationDriverBeta;
    @BindView(R.id.start_date)
    TextView startDate;
    @BindView(R.id.end_date)
    TextView endDate;
    @BindView(R.id.outstation_round_single)
    TextView outstationRoundSingle;
    @BindView(R.id.outstation_no_of_days)
    TextView outstationNoOfDays;
    @BindView(R.id.travel_time_charge)
    TextView travelTimeCharge;
    HashMap<String, Object> map = new HashMap<>();
    private Context context;
    private InvoicePresenter<InvoiceFragment> presenter = new InvoicePresenter<>();

    public InvoiceFragment() {
        // Required empty public constructor
    }

    public static void sendPayment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_invoice;
    }

    @Override
    public void initView(View view) {
        setCancelable(false);
        getDialog().setOnShowListener(dialog -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog;
            View bottomSheetInternal = d.findViewById(R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheetInternal).setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        getDialog().setCanceledOnTouchOutside(false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        if (DATUM != null) {
            initView(DATUM);
        }
    }

    String convertDateFormat(String date) {
        if (date != null && date.length() != 0) {
            String newDateString = null;
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
            try {
                Date newDate = spf.parse(date);
                newDateString = new SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(newDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return newDateString;
        } else
            return "-";
    }

    private void initView(@NonNull Datum datum) {
        bookingId.setText(datum.getBookingId());
        startDate.setText(convertDateFormat(datum.getStartedAt()));
        endDate.setText(convertDateFormat(datum.getFinishedAt()));
        distance.setText(String.valueOf(datum.getDistance() + " km"));
        travelTime.setText(getString(R.string._min, datum.getTravelTime()));

        if (datum.getPaid() == 0) {
            if (!datum.getPaymentMode().equalsIgnoreCase("CASH")) {
                payNow.setVisibility(View.VISIBLE);
                done.setVisibility(View.GONE);
            }

        } else if (datum.getPaid() == 1) {
            payNow.setVisibility(View.GONE);
            done.setVisibility(View.VISIBLE);
        }

        initPaymentView(datum.getPaymentMode());


        Payment payment = datum.getPayment();
        if (payment != null) {
            fixed.setText(numberFormat.format(payment.getFixed()));
            distancePrice.setText(numberFormat.format(payment.getDistance()));
            peekHourCharges.setText(numberFormat.format(payment.getPeakPrice()));
            tax.setText(numberFormat.format(payment.getTax()));
            total.setText(numberFormat.format(payment.getTotal()));
            payable.setText(numberFormat.format(payment.getPayable()));
            nightFare.setText(numberFormat.format(payment.getNightFare()));
            lnrWalletDetection.setVisibility(payment.getWallet() > 0 ? View.VISIBLE : View.GONE);
            walletDetection.setText(numberFormat.format(payment.getWallet()));
            lnrDiscount.setVisibility(payment.getDiscount() > 0 ? View.VISIBLE : View.GONE);
            discount.setText(numberFormat.format(payment.getDiscount()));
            travelTimeCharge.setText(numberFormat.format(payment.getMinute()));

            //      rental
            rentalNormalPrice.setText(numberFormat.format(payment.getMinute()));
            rentalTravelTime.setText(getString(R.string._min, datum.getTravelTime()));
            rentalTotalDistance.setText(String.valueOf(datum.getDistance() + " km"));
            rentalExtraHrKmPrice.setText(numberFormat.format(payment.getRentalExtraHrPrice() + payment.getRentalExtraKmPrice()));

            //      outstation
//            outstationDriverBeta.setText(String.format("(%s Days) %s", payment.getOutstationDays(), numberFormat.format(payment.getDriverBeta())));
            outstationDriverBeta.setText(String.format(numberFormat.format(payment.getDriverBeta())));
            outstationDistanceFare.setText(numberFormat.format(payment.getDistance()));
            outstationRoundSingle.setText(datum.getDay());
            outstationDistanceTravelled.setText(String.valueOf(datum.getDistance() + " km"));
//            outstationNoOfDays.setText(String.format("%s Days", payment.getOutstationDays()));
            String serviceReq = datum.getServiceRequired();
            switch (serviceReq) {
                case "none":
                    layout_normal_flow.setVisibility(View.VISIBLE);
                    break;
                case "rental":
                    layout_rental_flow.setVisibility(View.VISIBLE);
                    break;
                case "outstation":
                    layout_outstation_flow.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    }

    void initPaymentView(String value) {
        switch (value) {
            case "CASH":
                paymentMode.setText(getString(R.string.cash));
                paymentMode.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_money, 0, 0, 0);
                break;
            case "CARD":
                paymentMode.setText(getString(R.string.card));
                paymentMode.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_visa, 0, 0, 0);
                break;
            case "FLUTTERWAVE":
                paymentMode.setText(getString(R.string.flutterwave));
                paymentMode.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_visa, 0, 0, 0);
                break;
            case "PAYPAL":
                paymentMode.setText(getString(R.string.paypal));
                paymentMode.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_paypal, 0, 0, 0);
                break;
            case "WALLET":
                paymentMode.setText(getString(R.string.wallet));
                paymentMode.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wallet, 0, 0, 0);
                break;
            case "CC_AVENUE":
                paymentMode.setText(getString(R.string.cc_avenue));
                paymentMode.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_visa, 0, 0, 0);
                break;
            default:
                break;

        }
    }


    @OnClick({R.id.payment_mode, R.id.pay_now, R.id.done})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.payment_mode:
                break;
            case R.id.pay_now:
                if (DATUM != null) {
                    showLoading();
//                    presenter.payment(DATUM.getId());
//                    initCCAvenuePayment(DATUM);
                    map.put("request_id", DATUM.getId());
                    makePayment(String.valueOf(DATUM.getPayment().getPayable()));
                }
                break;
            case R.id.done:
                ((MainActivity) Objects.requireNonNull(getContext())).changeFlow("RATING");
                break;
        }
    }

    @Override
    public void onSuccess(Message message) {
        hideLoading();
        Toast.makeText(context, "You have successfully paid", Toast.LENGTH_SHORT).show();
        ((MainActivity) Objects.requireNonNull(context)).changeFlow("RATING");
    }

    @Override
    public void onError(Throwable e) {
        hideLoading();
        if (e instanceof HttpException) {
            Response response = ((HttpException) e).response();
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                if (jObjError.has("message"))
                    Toast.makeText(activity(), jObjError.optString("message"), Toast.LENGTH_SHORT).show();
                if (jObjError.has("error"))
                    Toast.makeText(activity(), jObjError.optString("error"), Toast.LENGTH_SHORT).show();
            } catch (Exception exp) {
                Log.e("Error", exp.getMessage());
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                try {
                    JSONObject jObj = new JSONObject(message);
                    JSONObject jsonData = new JSONObject(jObj.getString("data"));
                    map.put("tx_id", jsonData.getString("id"));
                    map.put("card_id", "FLUTTERWAVE");
                    Log.i("PAYMENT", new Gson().toJson(map));
                    presenter.payment(map);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(requireActivity(), "ERROR " + message, Toast.LENGTH_SHORT).show();
            } else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(requireActivity(), "CANCELLED " + message, Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    void initCCAvenuePayment(Datum datum) {
        Payment payment = datum.getPayment();

        if (payment == null) {
            return;
        }

        String accessCode = "AVVH81FJ20BW35HVWB";
        String merchantId = "194437";
        String amount = String.valueOf(payment.getPayable());
        String orderId = "trip-" + datum.getId();
        String redirectUrl = "https://diff.com/indipay/ccavanue/response";
        String cancelUrl = "https://diff.com/indipay/ccavanue/cancel/response";
        String rsaKeyUrl = "https://diff.com/rsa/key";

        String vAccessCode = ServiceUtility.chkNull(accessCode).toString().trim();
        String vMerchantId = ServiceUtility.chkNull(merchantId).toString().trim();
        String vCurrency = ServiceUtility.chkNull("GHS").toString().trim();
        String vAmount = ServiceUtility.chkNull(amount).toString().trim();
        if (!vAccessCode.equals("") && !vMerchantId.equals("") && !vCurrency.equals("") && !vAmount.equals("")) {
            Intent intent = new Intent(activity(), CCAvenueWebViewActivity.class);
            intent.putExtra(AvenuesParams.ACCESS_CODE, ServiceUtility.chkNull(accessCode).toString().trim());
            intent.putExtra(AvenuesParams.MERCHANT_ID, ServiceUtility.chkNull(merchantId).toString().trim());
            intent.putExtra(AvenuesParams.ORDER_ID, ServiceUtility.chkNull(orderId).toString().trim());
            intent.putExtra(AvenuesParams.CURRENCY, ServiceUtility.chkNull("GHS").toString().trim());
            intent.putExtra(AvenuesParams.AMOUNT, ServiceUtility.chkNull(amount).toString().trim());

            intent.putExtra(AvenuesParams.REDIRECT_URL, ServiceUtility.chkNull(redirectUrl).toString().trim());
            intent.putExtra(AvenuesParams.CANCEL_URL, ServiceUtility.chkNull(cancelUrl).toString().trim());
            intent.putExtra(AvenuesParams.RSA_KEY_URL, ServiceUtility.chkNull(rsaKeyUrl).toString().trim());

            startActivity(intent);
        } else {
            Toast.makeText(activity(), "All parameters are mandatory.", Toast.LENGTH_SHORT).show();
        }
    }

    private void makePayment(String amount) {
        String txRef = "SSADEO1";
        String order = "SSADEO1 Order";

        map.put("amount", amount);

        new RaveUiManager(this).setAmount(Double.parseDouble(amount))
                .setCurrency("GHS")
                .setEmail(SharedHelper.getKey(requireActivity(), SharedHelper.EMAIL, ""))
                .setfName(SharedHelper.getKey(requireActivity(), SharedHelper.NAME, ""))
                .setPublicKey(FLW_PUB_KEY)
                .setEncryptionKey(FLW_ENC_KEY)
                .setTxRef(txRef)
                .setNarration(order)
                .setPhoneNumber(SharedHelper.getKey(requireActivity(), SharedHelper.MOBILE, ""), true)
                .acceptAccountPayments(false)
                .acceptCardPayments(true)
                .acceptMpesaPayments(false)
                .acceptAchPayments(false)
                .acceptGHMobileMoneyPayments(false)
                .acceptUgMobileMoneyPayments(false)
                .acceptZmMobileMoneyPayments(false)
                .acceptRwfMobileMoneyPayments(false)
                .acceptSaBankPayments(false)
                .acceptUkPayments(false)
                .acceptBankTransferPayments(true, true)
                .acceptUssdPayments(false)
                .acceptBarterPayments(false)
                .acceptFrancMobileMoneyPayments(false)
                .allowSaveCardFeature(true)
                .onStagingEnv(false)
                .isPreAuth(true)
                .shouldDisplayFee(true)
                .showStagingLabel(true)
                .initialize();
    }
}
