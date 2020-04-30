package kh.com.mysabay.sdk.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import org.apache.commons.lang3.StringUtils;

import kh.com.mysabay.sdk.MySabaySDK;
import kh.com.mysabay.sdk.R;
import kh.com.mysabay.sdk.base.BaseFragment;
import kh.com.mysabay.sdk.databinding.FragmentVerifiedBinding;
import kh.com.mysabay.sdk.pojo.login.LoginItem;
import kh.com.mysabay.sdk.ui.activity.LoginActivity;
import kh.com.mysabay.sdk.utils.KeyboardUtils;
import kh.com.mysabay.sdk.utils.MessageUtil;
import kh.com.mysabay.sdk.utils.SdkTheme;
import kh.com.mysabay.sdk.viewmodel.UserApiVM;

/**
 * Created by Tan Phirum on 3/7/20
 * Gmail phirumtan@gmail.com
 */
public class VerifiedFragment extends BaseFragment<FragmentVerifiedBinding, UserApiVM> {

    public static final String TAG = VerifiedFragment.class.getSimpleName();

    public VerifiedFragment() {
        super();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_verified;
    }

    @Override
    public void initializeObjects(View v, Bundle args) {
        mViewBinding.viewMainVerified.setBackgroundResource(colorCodeBackground());
        mViewBinding.btnBack.setBackgroundResource(colorCodeBackground());
        if (MySabaySDK.getInstance().getSdkConfiguration().sdkTheme == SdkTheme.Light)
            mViewBinding.tvResendOtp.setTextColor(getResources().getColor(R.color.colorWhite700));

        this.viewModel = LoginActivity.loginActivity.viewModel;
    }

    @Override
    public void assignValues() {
        viewModel.getResponseLogin().observe(this, item -> {
            if (item != null && item.data.verifyCode > 0)
                MessageUtil.displayDialog(getContext(), String.valueOf(item.data.verifyCode));
            //mViewBinding.edtVerifyCode.setText(String.valueOf(item.data.verifyCode));
        });
    }

    @Override
    public void addListeners() {
        mViewBinding.edtVerifyCode.setAnimateText(true);
        mViewBinding.edtVerifyCode.setOnPinEnteredListener(str -> {
            LoginItem item = viewModel.getResponseLogin().getValue();
            if (item == null) return;

            if (Integer.parseInt(str.toString()) == item.data.verifyCode) {
                KeyboardUtils.hideKeyboard(getContext(), mViewBinding.edtVerifyCode);
                viewModel.postToVerified(getContext(), Integer.parseInt(str.toString()));
            } else {
                KeyboardUtils.hideKeyboard(getContext(), mViewBinding.edtVerifyCode);
                //MessageUtil.displayToast(getContext(), "verified failed");
                mViewBinding.edtVerifyCode.setError(true);
                mViewBinding.edtVerifyCode.postDelayed(() ->
                        mViewBinding.edtVerifyCode.setText(null), 1000);
            }
        });

        viewModel.liveNetworkState.observe(this, this::showProgressState);

        mViewBinding.tvResendOtp.setOnClickListener(v -> {
            mViewBinding.edtVerifyCode.setText("");
            viewModel.resendOTP(v.getContext());
        });

        mViewBinding.btnVerify.setOnClickListener(v -> {
            String code = mViewBinding.edtVerifyCode.getText() != null ? mViewBinding.edtVerifyCode.getText().toString() : "";
            if (!StringUtils.isEmpty(code)) {
                KeyboardUtils.hideKeyboard(v.getContext(), v);
                viewModel.postToVerified(v.getContext(), Integer.parseInt(code));
            } else
                MessageUtil.displayToast(v.getContext(), getString(R.string.verify_code_required));
        });

        //
        mViewBinding.btnBack.setOnClickListener(v -> {
            if (getActivity() != null)
                getActivity().onBackPressed();
        });
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
}
