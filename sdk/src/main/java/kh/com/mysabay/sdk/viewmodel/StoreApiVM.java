package kh.com.mysabay.sdk.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import kh.com.mysabay.sdk.MySabaySDK;
import kh.com.mysabay.sdk.SdkConfiguration;
import kh.com.mysabay.sdk.pojo.AppItem;
import kh.com.mysabay.sdk.pojo.NetworkState;
import kh.com.mysabay.sdk.pojo.googleVerify.GoogleVerifyBody;
import kh.com.mysabay.sdk.pojo.googleVerify.GoogleVerifyResponse;
import kh.com.mysabay.sdk.pojo.mysabay.MySabayItem;
import kh.com.mysabay.sdk.pojo.payment.PaymentBody;
import kh.com.mysabay.sdk.pojo.payment.PaymentResponseItem;
import kh.com.mysabay.sdk.pojo.payment.SubscribePayment;
import kh.com.mysabay.sdk.pojo.shop.Data;
import kh.com.mysabay.sdk.pojo.shop.ShopItem;
import kh.com.mysabay.sdk.pojo.thirdParty.ThirdPartyItem;
import kh.com.mysabay.sdk.pojo.thirdParty.payment.ResponseItem;
import kh.com.mysabay.sdk.repository.StoreRepo;
import kh.com.mysabay.sdk.ui.activity.StoreActivity;
import kh.com.mysabay.sdk.ui.fragment.BankVerifiedFm;
import kh.com.mysabay.sdk.ui.fragment.PaymentFm;
import kh.com.mysabay.sdk.utils.AppRxSchedulers;
import kh.com.mysabay.sdk.utils.LogUtil;
import kh.com.mysabay.sdk.utils.MessageUtil;
import kh.com.mysabay.sdk.webservice.AbstractDisposableObs;

/**
 * Created by Tan Phirum on 3/8/20
 * Gmail phirumtan@gmail.com
 */
public class StoreApiVM extends ViewModel {

    private static final String TAG = StoreApiVM.class.getSimpleName();

    private final StoreRepo storeRepo;
    private final SdkConfiguration sdkConfiguration;

    @Inject
    AppRxSchedulers appRxSchedulers;
    @Inject
    Gson gson;

    private final MediatorLiveData<String> _msgError = new MediatorLiveData<>();
    private final MediatorLiveData<NetworkState> _networkState;
    private final MediatorLiveData<ShopItem> _shopItem;
    private final CompositeDisposable mCompos;
    private final MediatorLiveData<Data> mDataSelected;
    private final MediatorLiveData<MySabayItem> mySabayItemMediatorLiveData;
    public final MediatorLiveData<ThirdPartyItem> _thirdPartyItemMediatorLiveData;


    @Inject
    public StoreApiVM(StoreRepo storeRepo) {
        this.storeRepo = storeRepo;
        this._networkState = new MediatorLiveData<>();
        this._shopItem = new MediatorLiveData<>();
        this.mCompos = new CompositeDisposable();
        this.mDataSelected = new MediatorLiveData<>();
        this.mySabayItemMediatorLiveData = new MediatorLiveData<>();
        this._thirdPartyItemMediatorLiveData = new MediatorLiveData<>();
        this.sdkConfiguration = MySabaySDK.getInstance().getSdkConfiguration();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mCompos != null) {
            mCompos.dispose();
            mCompos.clear();
        }
    }

    /**
     * List all item from server
     *
     * @param context
     */
    public void getShopFromServer(@NotNull Context context) {
        AppItem appItem = gson.fromJson(MySabaySDK.getInstance().getAppItem(), AppItem.class);
        storeRepo.getShopItem(sdkConfiguration.appSecret, appItem.token).subscribeOn(appRxSchedulers.io())
                .observeOn(appRxSchedulers.mainThread()).subscribe(new AbstractDisposableObs<ShopItem>(context, _networkState) {
            @Override
            protected void onSuccess(ShopItem item) {
                if (item.status == 200)
                    _shopItem.setValue(item);
                else MessageUtil.displayDialog(context, "something went wrong.");
            }

            @Override
            protected void onErrors(Throwable error) {
                LogUtil.error(TAG, error.getLocalizedMessage());
            }
        });
    }

    public LiveData<ShopItem> getShopItem() {
        return _shopItem;
    }

    public LiveData<NetworkState> getNetworkState() {
        return _networkState;
    }

    public LiveData<ThirdPartyItem> getThirdPartyProviders() {
        return _thirdPartyItemMediatorLiveData;
    }

    public LiveData<MySabayItem> getMySabayProvider() {
        return mySabayItemMediatorLiveData;
    }

    public void setShopItemSelected(Data data) {
        _networkState.setValue(new NetworkState(NetworkState.Status.SUCCESS));
        this.mDataSelected.setValue(data);
    }

    public LiveData<Data> getItemSelected() {
        return this.mDataSelected;
    }

    /**
     * Check user has authorize to use with mysabay payment or not
     *
     * @param context
     */
    public void getMySabayCheckout(@NotNull Context context) {
        AppItem appItem = gson.fromJson(MySabaySDK.getInstance().getAppItem(), AppItem.class);
        storeRepo.getMySabayCheckout(sdkConfiguration.appSecret, appItem.token, appItem.uuid).subscribeOn(appRxSchedulers.io())
                .observeOn(appRxSchedulers.mainThread()).subscribe(new Observer<MySabayItem>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompos.add(d);
            }

            @Override
            public void onNext(MySabayItem mySabayItem) {
                mySabayItemMediatorLiveData.setValue(mySabayItem);
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.debug(TAG, "error " + e.getLocalizedMessage());
                //        get3PartyCheckout(context);
            }

            @Override
            public void onComplete() {
                //      get3PartyCheckout(context);
            }
        });

    }

    /**
     * show list all bank provider
     *
     * @param context
     */
    public void get3PartyCheckout(@NotNull Context context) {
        AppItem appItem = gson.fromJson(MySabaySDK.getInstance().getAppItem(), AppItem.class);
        storeRepo.get3PartyCheckout(sdkConfiguration.appSecret, appItem.token, appItem.uuid).subscribeOn(appRxSchedulers.io())
                .observeOn(appRxSchedulers.mainThread()).subscribe(new AbstractDisposableObs<ThirdPartyItem>(context, _networkState) {
            @Override
            protected void onSuccess(ThirdPartyItem thirdPartyItem) {
                LogUtil.debug(TAG, "ThirdPartyItem " + thirdPartyItem);
                _thirdPartyItemMediatorLiveData.setValue(thirdPartyItem);
            }

            @Override
            protected void onErrors(Throwable error) {
                LogUtil.error(TAG, "error " + error.getLocalizedMessage());
            }
        });

    }

    public void postToVerifyAppInPurchase(@NotNull Context context, @NotNull GoogleVerifyBody body) {
        EventBus.getDefault().post(new SubscribePayment(null, body.receipt, null));
        AppItem appItem = gson.fromJson(MySabaySDK.getInstance().getAppItem(), AppItem.class);
        mCompos.add(storeRepo.postToVerifyGoogle(sdkConfiguration.appSecret, appItem.token, body).subscribeOn(appRxSchedulers.io())
                .observeOn(appRxSchedulers.mainThread()).subscribe(new Consumer<GoogleVerifyResponse>() {
                    @Override
                    public void accept(GoogleVerifyResponse googleVerifyResponse) throws Exception {
                        // EventBus.getDefault().post(new SubscribePayment(null, googleVerifyResponse.data, null));
                        MessageUtil.displayDialog(context, googleVerifyResponse.message);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //      EventBus.getDefault().post(new SubscribePayment(null, null, throwable));
                        LogUtil.error(TAG, "error " + throwable.getLocalizedMessage());
                    }
                }));
    }

    /**
     * This method is use to buy item with mysabay payment
     *
     * @param context
     */
    public void postToPaidWithMySabayProvider(Context context) {
        AppItem appItem = gson.fromJson(MySabaySDK.getInstance().getAppItem(), AppItem.class);
        Data shopItem = getItemSelected().getValue();
        if (getMySabayProvider().getValue() == null) return;

        List<kh.com.mysabay.sdk.pojo.mysabay.Data> listMySabayProvider = getMySabayProvider().getValue().data;
        if (listMySabayProvider.size() > 0 && shopItem != null) {
            PaymentBody body = new PaymentBody(appItem.uuid, shopItem.priceInUsd.toString(), listMySabayProvider.get(0).code.toLowerCase(), listMySabayProvider.get(0).assetCode.toLowerCase());
            storeRepo.postToPaid(sdkConfiguration.appSecret, appItem.token, body).subscribeOn(appRxSchedulers.io())
                    .observeOn(appRxSchedulers.mainThread())
                    .subscribe(new AbstractDisposableObs<PaymentResponseItem>(context, _networkState) {
                        @Override
                        protected void onSuccess(PaymentResponseItem item) {
                            EventBus.getDefault().post(new SubscribePayment(item, null, null));
                            MessageUtil.displayDialog(context, item.message);
                        }

                        @Override
                        protected void onErrors(Throwable error) {
                            EventBus.getDefault().post(new SubscribePayment(null, null, error));
                        }
                    });
        }
    }


    public void postToPaidWithBank(StoreActivity context, kh.com.mysabay.sdk.pojo.thirdParty.Data data) {
        AppItem appItem = gson.fromJson(MySabaySDK.getInstance().getAppItem(), AppItem.class);
        Data shopItem = getItemSelected().getValue();

        if (data != null && shopItem != null) {
            PaymentBody body = new PaymentBody(appItem.uuid, shopItem.priceInUsd.toString(), data.code.toLowerCase(), data.assetCode.toLowerCase());
            storeRepo.postToChargeOneTime(sdkConfiguration.appSecret, appItem.token, body).subscribeOn(appRxSchedulers.io())
                    .observeOn(appRxSchedulers.mainThread())
                    .subscribe(new AbstractDisposableObs<ResponseItem>(context, _networkState) {
                        @Override
                        protected void onSuccess(ResponseItem response) {
                            if (response.status == 200) {
                                MySabaySDK.getInstance().saveMethodSelected(gson.toJson(data.withIsPaidWith(false)));
                                context.initAddFragment(BankVerifiedFm.newInstance(response.data), PaymentFm.TAG, true);
                            } else
                                MessageUtil.displayDialog(context, gson.toJson(response));
                        }

                        @Override
                        protected void onErrors(Throwable error) {
                            MessageUtil.displayDialog(context, gson.toJson(error));
                            LogUtil.info(TAG, "error " + error.getLocalizedMessage());
                        }
                    });
        }
    }
}

