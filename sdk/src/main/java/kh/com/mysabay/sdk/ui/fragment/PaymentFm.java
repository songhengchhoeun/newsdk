package kh.com.mysabay.sdk.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.anjlab.android.iab.v3.BillingCommunicationException;
import com.anjlab.android.iab.v3.BillingHistoryRecord;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.Constants;
import com.anjlab.android.iab.v3.PurchaseData;
import com.anjlab.android.iab.v3.PurchaseInfo;
import com.anjlab.android.iab.v3.SkuDetails;
import com.anjlab.android.iab.v3.TransactionDetails;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import kh.com.mysabay.sdk.BuildConfig;
import kh.com.mysabay.sdk.MySabaySDK;
import kh.com.mysabay.sdk.R;
import kh.com.mysabay.sdk.adapter.BankProviderAdapter;
import kh.com.mysabay.sdk.base.BaseFragment;
import kh.com.mysabay.sdk.databinding.FmPaymentBinding;
import kh.com.mysabay.sdk.databinding.PartialBankProviderBinding;
import kh.com.mysabay.sdk.pojo.googleVerify.DataBody;
import kh.com.mysabay.sdk.pojo.googleVerify.GoogleVerifyBody;
import kh.com.mysabay.sdk.pojo.googleVerify.ReceiptBody;
import kh.com.mysabay.sdk.pojo.shop.Data;
import kh.com.mysabay.sdk.ui.activity.StoreActivity;
import kh.com.mysabay.sdk.utils.FontUtils;
import kh.com.mysabay.sdk.utils.LogUtil;
import kh.com.mysabay.sdk.utils.MessageUtil;
import kh.com.mysabay.sdk.viewmodel.StoreApiVM;

/**
 * Created by Tan Phirum on 3/15/20
 * Gmail phirumtan@gmail.com
 */
public class PaymentFm extends BaseFragment<FmPaymentBinding, StoreApiVM> implements BillingProcessor.IBillingHandler {

    public static final String TAG = PaymentFm.class.getSimpleName();
    public static final String EXT_KEY_DATA = "EXT_KEY_DATA";

    private BillingProcessor bp;

    private Data mData;
    private static String PURCHASE_ID = "android.test.purchased";
    private MaterialDialog dialogBank;

    @NotNull
    @Contract("_ -> new")
    public static PaymentFm newInstance(Data item) {
        Bundle args = new Bundle();
        args.putParcelable(EXT_KEY_DATA, item);
        PaymentFm f = new PaymentFm();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null)
            mData = getArguments().getParcelable(EXT_KEY_DATA);

        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fm_payment;
    }

    @Override
    public void initializeObjects(@NotNull View v, Bundle args) {
        mViewBinding.viewMainPayment.setBackgroundResource(colorCodeBackground());
        mViewBinding.materialCardView.setBackgroundResource(colorCodeBackground());

        viewModel.setShopItemSelected(mData);
        viewModel.getMySabayCheckout(v.getContext());
        if (!BillingProcessor.isIabServiceAvailable(v.getContext()))
            MessageUtil.displayDialog(v.getContext(), getString(R.string.upgrade_google_play));

        bp = new BillingProcessor(v.getContext(), MySabaySDK.getInstance().getSdkConfiguration().licenseKey, MySabaySDK.getInstance().getSdkConfiguration().merchantId, this);
        bp.initialize();
        // or bp = BillingProcessor.newBillingProcessor(this, "YOUR LICENSE KEY FROM GOOGLE PLAY CONSOLE HERE", this);
        // See below on why this is a useful alternative
    }

    @Override
    public void assignValues() {
        viewModel.getNetworkState().observe(this, this::showProgressState);

        viewModel.getItemSelected().observe(this, data -> {
            if (data != null) {
                mViewBinding.tvPoint.setText(data.name);
                mViewBinding.tvPrice.setText(data.toUSDPrice());
                mViewBinding.tvTotal.setText(String.format(getString(R.string.total_s), data.toUSDPrice()));
            }
        });

        viewModel.getMySabayProvider().observe(this, mySabayItem -> {
            if (mySabayItem.status == 200) {
                if (mySabayItem.data.size() > 0)
                    mViewBinding.rdbMySabay.setVisibility(View.VISIBLE);
                else
                    mViewBinding.rdbMySabay.setVisibility(View.GONE);
            } else
                mViewBinding.rdbMySabay.setVisibility(View.GONE);
        });

        viewModel.getThirdPartyProviders().observe(this, thirdPartyItem -> {
            if (thirdPartyItem != null && thirdPartyItem.status == 200) {
                if (thirdPartyItem.data.size() > 0)
                    showBankProviders(getContext(), thirdPartyItem.data);
            }
        });

        kh.com.mysabay.sdk.pojo.thirdParty.Data paidMethod = gson.fromJson(MySabaySDK.getInstance().getMethodSelected(), kh.com.mysabay.sdk.pojo.thirdParty.Data.class);
        if (paidMethod != null) {
            mViewBinding.rdbPreAuthPay.setText(paidMethod.serviceName);
            mViewBinding.rdbPreAuthPay.setVisibility(paidMethod.isPaidWith ? View.VISIBLE : View.GONE);
            mViewBinding.rdbPreAuthPay.setChecked(paidMethod.isPaidWith);
        }
    }

    @Override
    public void addListeners() {
        mViewBinding.btnPay.setOnClickListener(v -> {
            int checkedId = mViewBinding.radioGroup.getCheckedRadioButtonId();

            if (checkedId == R.id.rdb_in_app_purchase) {
                if (bp.isOneTimePurchaseSupported() && (viewModel.getItemSelected().getValue() != null)) {
                    if (!BuildConfig.DEBUG)
                        PURCHASE_ID = viewModel.getItemSelected().getValue().packageId;
                    boolean isPurchase = bp.purchase(getActivity(), PURCHASE_ID);
                    boolean isConsumePurchase = bp.consumePurchase(PURCHASE_ID);

                    LogUtil.info(TAG, "purchase =" + isPurchase + ", comsumePurcase = " + isConsumePurchase);
                } else
                    MessageUtil.displayDialog(v.getContext(), "sorry your device not support in app purchase");

            } else if (checkedId == R.id.rdb_my_sabay) {
                Data data = viewModel.getItemSelected().getValue();
                if (data == null) return;

                MessageUtil.displayDialog(v.getContext(), getString(R.string.payment_confirmation),
                        String.format(getString(R.string.are_you_pay_with_my_sabay_provider), data.priceInSc.toString()), getString(R.string.cancel),
                        getString(R.string.confirm), null,
                        (dialog, which) -> viewModel.postToPaidWithMySabayProvider(v.getContext()));

            } else if (checkedId == R.id.rdb_third_bank_provider) {
                viewModel.get3PartyCheckout(v.getContext());
            } else if (checkedId == R.id.rdb_pre_auth_pay) {
                kh.com.mysabay.sdk.pojo.thirdParty.Data paidItem = gson.fromJson(MySabaySDK.getInstance().getMethodSelected(), kh.com.mysabay.sdk.pojo.thirdParty.Data.class);
                if (paidItem != null)
                    viewModel.postToPaidWithBank((StoreActivity) getActivity(), paidItem);
            } else
                MessageUtil.displayToast(v.getContext(), getString(R.string.please_choose_payment_option));
        });

        mViewBinding.btnBack.setOnClickListener(v -> {
            if (getActivity() != null)
                getActivity().onBackPressed();
        });

        mViewBinding.btnClose.setOnClickListener(v -> {
            if (getActivity() != null)
                getActivity().onBackPressed();
        });

        /*mViewBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rdb_in_app_purchase) {

                } else if (checkedId == R.id.rdb_my_sabay) {

                } else if (checkedId == R.id.rdb_third_bank_provider) {

                } else if (checkedId == R.id.rdb_pre_auth_pay) {

                } else {
                    LogUtil.info(TAG, "nothing selected");
                }
            }
        });*/
    }

    @Override
    public View assignProgressView() {
        return mViewBinding.viewEmpty.progressBar;
    }

    @Override
    public View assignEmptyView() {
        return mViewBinding.viewEmpty.viewRetry;
    }

    @Override
    protected Class<StoreApiVM> getViewModel() {
        return StoreApiVM.class;
    }

    @Override
    protected void onOnlineCallback() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((StoreActivity) context).userComponent.inject(this);
        // Now you can access loginViewModel here and onCreateView too
        // (shared instance with the Activity and the other Fragment)
    }

    @Override
    public void onProductPurchased(@NonNull String productId, TransactionDetails details) {
        LogUtil.debug(TAG, "product id " + productId + " TransactionDetails :" + details + " : " + bp.getPurchaseTransactionDetails(productId));
        if (details == null) {
            SkuDetails skuDetails = bp.getPurchaseListingDetails(productId);
            LogUtil.debug(TAG, "skuDetails =" + skuDetails);
        } else {
            PurchaseInfo info = details.purchaseInfo;
            PurchaseData purchaseData = info.purchaseData;
            GoogleVerifyBody googleVerifyBody = new GoogleVerifyBody();
            ReceiptBody receiptBody = new ReceiptBody();
            receiptBody.withSignature(info.signature);
            DataBody dataBody = new DataBody(purchaseData.orderId, purchaseData.packageName, purchaseData.productId,
                    purchaseData.purchaseTime == null ? 0 : purchaseData.purchaseTime.getTime(), purchaseData.purchaseState.ordinal(), purchaseData.purchaseToken);
            receiptBody.withData(dataBody);
            googleVerifyBody.withReceipt(receiptBody);
            if (getActivity() != null)
                viewModel.postToVerifyAppInPurchase(getActivity(), googleVerifyBody);
        }
    }

    @Override
    public void onPurchaseHistoryRestored() {
        /*
         * Called when purchase history was restored and the list of all owned PRODUCT ID's
         * was loaded from Google Play
         */
        LogUtil.debug(TAG, "onPurchaseHistoryRestored");
    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        if (errorCode == Constants.BILLING_RESPONSE_RESULT_USER_CANCELED) {
            LogUtil.info(TAG, "user cancel purchase");
        }
        LogUtil.debug(TAG, "error code " + errorCode + " smg " + (error != null ? error.toString() : ""));
    }

    @Override
    public void onBillingInitialized() {
        LogUtil.debug(TAG, "onBillingInitialized");
        try {
            if (bp.isRequestBillingHistorySupported(Constants.PRODUCT_TYPE_MANAGED)) {
                Bundle extraParams = new Bundle();

                List<BillingHistoryRecord> lsBilling = bp.getPurchaseHistory(Constants.PRODUCT_TYPE_MANAGED, extraParams);
                LogUtil.debug(TAG, "listBilling " + lsBilling.size() + " payload ");

            }

        } catch (BillingCommunicationException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        if (bp != null) bp.release();
        super.onDestroy();
    }

    private void showBankProviders(Context context, List<kh.com.mysabay.sdk.pojo.thirdParty.Data> data) {
        if (dialogBank != null) {
            dialogBank.dismiss();
        }
        PartialBankProviderBinding view = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.partial_bank_provider, null, false);
        RecyclerView rcv = view.bankRcv;
        BankProviderAdapter adapter = new BankProviderAdapter(context, data, item -> {
            viewModel.postToPaidWithBank((StoreActivity) getActivity(), (kh.com.mysabay.sdk.pojo.thirdParty.Data) item);
            if (dialogBank != null)
                dialogBank.dismiss();
            viewModel._thirdPartyItemMediatorLiveData.setValue(null);
            dialogBank = null;
        });
        rcv.setLayoutManager(new LinearLayoutManager(context));
        rcv.setHasFixedSize(true);
        rcv.setAdapter(adapter);
        dialogBank = new MaterialDialog.Builder(context)
                .typeface(FontUtils.getTypefaceKhmerBold(context), FontUtils.getTypefaceKhmer(context))
                .customView(view.getRoot(), true)
                .canceledOnTouchOutside(false)
                .cancelable(false)
                .positiveText(R.string.label_close).onPositive((dialog, which) -> {
                    dialog.dismiss();
                    dialogBank = null;
                    viewModel._thirdPartyItemMediatorLiveData.setValue(null);

                }).build();
        dialogBank.show();
    }
}

