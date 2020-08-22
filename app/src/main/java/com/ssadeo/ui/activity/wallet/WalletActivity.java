package com.ssadeo.ui.activity.wallet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RaveUiManager;
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants;
import com.ssadeo.base.BaseActivity;
import com.ssadeo.common.cc_avenue.AvenuesParams;
import com.ssadeo.common.cc_avenue.CCAvenueWebViewActivity;
import com.ssadeo.common.cc_avenue.ServiceUtility;
import com.ssadeo.data.SharedHelper;
import com.ssadeo.data.network.model.User;
import com.ssadeo.data.network.model.WalletResponse;
import com.ssadeo.user.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ssadeo.user.BuildConfig.FLW_ENC_KEY;
import static com.ssadeo.user.BuildConfig.FLW_PUB_KEY;


public class WalletActivity extends BaseActivity implements WalletIView {

    private static final int CC_AVENUE_METHOD = 43;
    @BindView(R.id.wallet_balance)
    TextView walletBalance;
    @BindView(R.id.amount)
    EditText amount;
    @BindView(R.id._199)
    Button _199;
    @BindView(R.id._599)
    Button _599;
    @BindView(R.id._1099)
    Button _1099;
    @BindView(R.id.add_amount)
    Button addAmount;
    @BindView(R.id.cvAddMoneyContainer)
    CardView cvAddMoneyContainer;

    String numberFormat;
    String regexNumber = "^(\\d{0,9}\\.\\d{1,4}|\\d{1,9})$";
    private WalletPresenter<WalletActivity> presenter = new WalletPresenter<>();

    private HashMap<String, Object> map = new HashMap<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        numberFormat = getNumberFormat();
        presenter.profile();
       /* if (isCCAvenueEnabled) {
            cvAddMoneyContainer.setVisibility(View.VISIBLE);
            addAmount.setVisibility(View.VISIBLE);
        } else {
            cvAddMoneyContainer.setVisibility(View.GONE);
            addAmount.setVisibility(View.GONE);
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id._199, R.id._599, R.id._1099, R.id.add_amount})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id._199:
                amount.setText(String.valueOf("199"));
                break;
            case R.id._599:
                amount.setText(String.valueOf("599"));
                break;
            case R.id._1099:
                amount.setText(String.valueOf("1099"));
                break;
            case R.id.add_amount:
                if (!amount.getText().toString().trim().matches(regexNumber)) {
                    Toast.makeText(activity(), getString(R.string.invalid_amount), Toast.LENGTH_SHORT).show();
                    return;
                }

//                initCCAvenuePayment(amount.getText().toString());

                makePayment(amount.getText().toString());

                break;
        }
    }

    void initCCAvenuePayment(String amount) {

        String accessCode = "AVVH81FJ20BW35HVWB";
        String merchantId = "194437";
        String orderId = "wallet-" + SharedHelper.getIntKey(this, "user_id");
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

            startActivityForResult(intent, CC_AVENUE_METHOD);
        } else {
            Toast.makeText(activity(), "All parameters are mandatory.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CC_AVENUE_METHOD && resultCode == Activity.RESULT_OK && data != null) {
            Toast.makeText(this, getString(R.string._added_to_your_wallet, numberFormat + amount.getText().toString()), Toast.LENGTH_SHORT).show();
            amount.setText("");
            presenter.profile();
        } else if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                try {
                    JSONObject jObj = new JSONObject(message);
                    JSONObject jsonData = new JSONObject(jObj.getString("data"));
                    map.put("tx_id",jsonData.getString("id"));
                    map.put("card_id","FLUTTERWAVE");
                    presenter.addMoney(map);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                Toast.makeText(this, "SUCCESS " + message, Toast.LENGTH_SHORT).show();

//                presenter.addMoney();
            } else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(this, "ERROR " + message, Toast.LENGTH_SHORT).show();
            } else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(this, "CANCELLED " + message, Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onSuccess(User user) {
        walletBalance.setText(numberFormat + user.getWalletBalance());
        SharedHelper.putKey(this, "user_id",user.getId());
        SharedHelper.putKey(this, "wallet_amount", user.getWalletBalance());
    }

    @Override
    public void onSuccess(WalletResponse walletResponse) {
        walletBalance.setText(numberFormat + walletResponse.getUser().getWalletBalance());
        SharedHelper.putKey(this, "user_id", walletResponse.getUser().getId());
        SharedHelper.putKey(this, "wallet_amount", walletResponse.getUser().getWalletBalance());
        Toast.makeText(this, walletResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable e) {
        hideLoading();
        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
    }

    private void makePayment(String amount) {
        String txRef = "SSADEO1";
        String order = "SSADEO1 Order";

        map.put("amount",amount);

        new RaveUiManager(this).setAmount(Double.parseDouble(amount))
                .setCurrency("GHS")
                .setEmail(SharedHelper.getKey(this, SharedHelper.EMAIL, ""))
                .setfName(SharedHelper.getKey(this, SharedHelper.NAME, ""))
                .setPublicKey(FLW_PUB_KEY)
                .setEncryptionKey(FLW_ENC_KEY)
                .setTxRef(txRef)
                .setNarration(order)
                .setPhoneNumber(SharedHelper.getKey(this, SharedHelper.MOBILE, ""), true)
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
                .isPreAuth(false)
                .shouldDisplayFee(true)
                .showStagingLabel(true)
                .withTheme(R.style.MyCustomTheme)
                .initialize();
    }

}
