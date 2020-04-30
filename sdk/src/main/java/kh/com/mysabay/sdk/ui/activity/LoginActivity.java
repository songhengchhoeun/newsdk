package kh.com.mysabay.sdk.ui.activity;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import kh.com.mysabay.sdk.Globals;
import kh.com.mysabay.sdk.MySabaySDK;
import kh.com.mysabay.sdk.R;
import kh.com.mysabay.sdk.base.BaseActivity;
import kh.com.mysabay.sdk.di.component.UserComponent;
import kh.com.mysabay.sdk.ui.fragment.LoginFragment;
import kh.com.mysabay.sdk.ui.fragment.MySabayLoginFm;
import kh.com.mysabay.sdk.utils.LogUtil;
import kh.com.mysabay.sdk.viewmodel.UserApiVM;

public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final int DELAY = 1000;

    private FragmentManager mManager;
    private Handler mHandler;

    // Reference to the main graph
    public UserComponent userComponent;
    public static LoginActivity loginActivity;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public UserApiVM viewModel;
    private Uri mDeepLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        String action = intent.getAction();
        mDeepLink = intent.getData();
        LogUtil.debug(TAG, "");
        // Creation of the main graph using the application graph
        userComponent = MySabaySDK.getInstance().mComponent.mainComponent().create();
        // Make Dagger instantiate @Inject fields in MaiActivity
        userComponent.inject(this);
        loginActivity = this;
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserApiVM.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected int getToolbarId() {
        return 0;
    }

    @Override
    public void initializeObjects(Bundle args) {
        mManager = getSupportFragmentManager();
        mHandler = new Handler();
    }

    @Override
    public void assignValues() {
        if (mDeepLink != null && StringUtils.contains(mDeepLink.toString(), "user.master.mysabay.com/api/v1/user/mysabay/login/deeplink"))
            initAddFragment(MySabayLoginFm.newInstance(mDeepLink.toString()), MySabayLoginFm.TAG);
        else initAddFragment(LoginFragment.newInstance(), LoginFragment.TAG);
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void onActionAfterCreated() {

    }

    @Override
    protected View getCoordinateLayout() {
        return null;
    }

    public void initAddFragment(Fragment f, String tag) {
        initAddFragment(f, tag, false);
    }

    public void initAddFragment(Fragment f, String tag, boolean isBack) {
        Globals.initAddFragment(mManager, f, tag, isBack);
    }

}
