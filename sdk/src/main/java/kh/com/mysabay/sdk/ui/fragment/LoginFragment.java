package kh.com.mysabay.sdk.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import kh.com.mysabay.sdk.BuildConfig;
import kh.com.mysabay.sdk.MySabaySDK;
import kh.com.mysabay.sdk.R;
import kh.com.mysabay.sdk.base.BaseFragment;
import kh.com.mysabay.sdk.databinding.FragmentLoginBinding;
import kh.com.mysabay.sdk.pojo.NetworkState;
import kh.com.mysabay.sdk.ui.activity.LoginActivity;
import kh.com.mysabay.sdk.utils.MessageUtil;
import kh.com.mysabay.sdk.viewmodel.UserApiVM;

/**
 * Created by Tan Phirum on 3/7/20
 * Gmail phirumtan@gmail.com
 */
public class LoginFragment extends BaseFragment<FragmentLoginBinding, UserApiVM> {

    public static final String TAG = LoginFragment.class.getSimpleName();

    @NotNull
    @Contract(" -> new")
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initializeObjects(View v, Bundle args) {
        mViewBinding.viewMainLogin.setBackgroundResource(colorCodeBackground());
        mViewBinding.tvMySabayAppName.setText(MySabaySDK.getInstance().getSdkConfiguration().mySabayAppName);
        this.viewModel = LoginActivity.loginActivity.viewModel;
    }

    @Override
    public void assignValues() {
        if (BuildConfig.DEBUG) {
            mViewBinding.edtPhone.setText("098637352");
        }
        mViewBinding.edtPhone.requestFocus();

        new Handler().postDelayed(() -> showProgressState(new NetworkState(NetworkState.Status.SUCCESS)), 500);
    }

    @Override
    public void addListeners() {
        viewModel.liveNetworkState.observe(this, this::showProgressState);

        viewModel.login.observe(this, phone -> mViewBinding.edtPhone.setText(phone));

        mViewBinding.btnLogin.setOnClickListener(v -> {
            if (mViewBinding.edtPhone.getText() == null) return;

            String phoneNo = mViewBinding.edtPhone.getText().toString();
            /*Editable phoneNo = mViewBinding.edtPhone.getText();
            if (StringUtils.isAnyBlank(phoneNo)) {
                showCheckFields(mViewBinding.edtPhone, R.string.msg_input_phone);
            } else if (!MyPhoneUtils.isValidatePhone(phoneNo)) {
                showCheckFields(mViewBinding.edtPhone, R.string.msg_phone_incorrect);
            }*/
            if (StringUtils.isAnyBlank(phoneNo)) {
                showCheckFields(mViewBinding.edtPhone, R.string.msg_input_phone);
            } else
                viewModel.postToLogin(v.getContext(), MySabaySDK.getInstance().getSdkConfiguration().appSecret, phoneNo);
        });

        mViewBinding.btnLoginMysabay.setOnClickListener(v ->
                viewModel.postToLoginWithMySabay(v.getContext(), MySabaySDK.getInstance().getSdkConfiguration().appSecret));


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
    protected Class<UserApiVM> getViewModel() {
        return UserApiVM.class;
    }

    @Override
    protected void onOnlineCallback() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((LoginActivity) context).userComponent.inject(this);
        // Now you can access loginViewModel here and onCreateView too
        // (shared instance with the Activity and the other Fragment)
    }

    private void showCheckFields(AppCompatEditText view, int msg) {
        if (view != null) {
            YoYo.with(Techniques.Shake).duration(600).playOn(view);
            view.requestFocus();
        }
        MessageUtil.displayToast(getContext(), getString(msg));
    }
}