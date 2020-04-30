package kh.com.mysabay.sdk.base;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.view.View;

import com.akexorcist.localizationactivity.core.LocalizationActivityDelegate;
import com.akexorcist.localizationactivity.core.OnLocaleChangedListener;

import java.util.Locale;

import kh.com.mysabay.sdk.MySabaySDK;
import kh.com.mysabay.sdk.R;
import kh.com.mysabay.sdk.utils.FontUtils;
import kh.com.mysabay.sdk.utils.IdlingResourceHelper;

public abstract class BaseActivity extends AppCompatActivity implements OnLocaleChangedListener {

    private LocalizationActivityDelegate localizationDelegate = new LocalizationActivityDelegate(this);

    private boolean isNetWorkHasLost = false;

    public abstract int getLayoutId();

    protected abstract int getToolbarId();

    public abstract void initializeObjects(Bundle args);

    public abstract void assignValues();

    public abstract void addListeners();

    public abstract void onActionAfterCreated();

    protected abstract View getCoordinateLayout();

    protected Toolbar mToolbar;

    protected IdlingResourceHelper mIdlingResourceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        localizationDelegate.addOnLocaleChangedListener(this);
        localizationDelegate.onCreate(savedInstanceState);
        setLanguage(MySabaySDK.getInstance().getSdkConfiguration().sdkLanguages.value);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        if (getToolbarId() > 0) {
            mToolbar = findViewById(getToolbarId());
            setSupportActionBar(mToolbar);
            if (getSupportActionBar() != null)
                getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
        initializeObjects(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (savedInstanceState == null) {
            assignValues();
            addListeners();
            onActionAfterCreated();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        localizationDelegate.onResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(localizationDelegate.attachBaseContext(newBase));
    }

    @Override
    public Context getApplicationContext() {
        return localizationDelegate.getApplicationContext(super.getApplicationContext());
    }

    @Override
    public Resources getResources() {
        return localizationDelegate.getResources(super.getResources());
    }

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setTitle(CharSequence title) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SpannableString newTitle = FontUtils.getEmbeddedString(this,
                    title.toString(), R.font.font_battambang_bold, false);
            super.setTitle(newTitle);
        } else
            super.setTitle(title);
    }

    /**
     * call before change string language
     */
    @Override
    public void onBeforeLocaleChanged() {

    }

    /**
     * call after change string language
     */
    @Override
    public void onAfterLocaleChanged() {

    }

    /**
     * Only called from test, creates and returns a new {@link IdlingResourceHelper}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResourceHelper == null)
            mIdlingResourceHelper = new IdlingResourceHelper();
        return mIdlingResourceHelper;
    }

    public final void setLanguage(String language) {
        localizationDelegate.setLanguage(this, language);
    }

    public final void setLanguage(Locale locale) {
        localizationDelegate.setLanguage(this, locale);
    }

    public final Locale getCurrentLanguage() {
        return localizationDelegate.getLanguage(this);
    }

}
