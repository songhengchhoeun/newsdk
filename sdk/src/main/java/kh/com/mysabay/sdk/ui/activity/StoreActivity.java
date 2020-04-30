package kh.com.mysabay.sdk.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import kh.com.mysabay.sdk.Globals;
import kh.com.mysabay.sdk.MySabaySDK;
import kh.com.mysabay.sdk.R;
import kh.com.mysabay.sdk.base.BaseActivity;
import kh.com.mysabay.sdk.di.component.StoreComponent;
import kh.com.mysabay.sdk.ui.fragment.PaymentFm;
import kh.com.mysabay.sdk.ui.fragment.ShopsFragment;

public class StoreActivity extends BaseActivity {

    private static final String TAG = StoreActivity.class.getSimpleName();

    private static final int DELAY = 1000;

    private FragmentManager mManager;

    // Reference to the main graph
    public StoreComponent userComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Creation of the main graph using the application graph
        userComponent = MySabaySDK.getInstance().mComponent.storeComponent().create();
        // Make Dagger instantiate @Inject fields in MaiActivity
        userComponent.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_store;
    }

    @Override
    protected int getToolbarId() {
        return 0;
    }

    @Override
    public void initializeObjects(Bundle args) {
        mManager = getSupportFragmentManager();
    }

    @Override
    public void assignValues() {
        initAddFragment(ShopsFragment.newInstance(), ShopsFragment.TAG);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (mManager.getFragments().size() > 0) {
            PaymentFm f = (PaymentFm) mManager.findFragmentByTag(PaymentFm.TAG);
            if (f != null)
                f.onActivityResult(requestCode, resultCode, data);
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    public void initAddFragment(Fragment f, String tag) {
        initAddFragment(f, tag, false);
    }

    public void initAddFragment(Fragment f, String tag, boolean isBack) {
        Globals.initAddFragment(mManager, f, tag, isBack);
    }
}