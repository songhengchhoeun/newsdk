package kh.com.mysabay.sdk.webservice;

import android.content.Context;

import androidx.lifecycle.MediatorLiveData;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import io.reactivex.observers.DisposableObserver;
import kh.com.mysabay.sdk.pojo.NetworkState;
import kh.com.mysabay.sdk.utils.IdlingResourceHelper;
import okhttp3.ResponseBody;

/**
 * Created by Tan Phirum on 3/2/18.
 */
public abstract class AbstractDisposableObs<T> extends DisposableObserver<T> {

    private WeakReference<MediatorLiveData<NetworkState>> mWeakRefBaseView;
    private WeakReference<IdlingResourceHelper> mWeakRefIdlingState;
    private WeakReference<Context> mWeakRefContext;

    public AbstractDisposableObs(Context context) {
        this(context, null, null);
    }

    public AbstractDisposableObs(Context context, IdlingResourceHelper idlingResource) {
        this(context, null, idlingResource);
    }

    public AbstractDisposableObs(Context context, MediatorLiveData<NetworkState> baseView) {
        this(context, baseView, null);
    }

    /**
     * @param context  if has context you don't need to handle error on baseview
     * @param baseView nullable
     * @param mHelper  can't null, convenient on unit test
     */
    public AbstractDisposableObs(Context context, MediatorLiveData<NetworkState> baseView, IdlingResourceHelper mHelper) {
        this.mWeakRefBaseView = new WeakReference<>(baseView);
        this.mWeakRefIdlingState = new WeakReference<>(mHelper);
        this.mWeakRefContext = new WeakReference<>(context);
        if (baseView != null)
            baseView.setValue(new NetworkState(NetworkState.Status.LOADING));

        if (mHelper != null)
            mHelper.setIdleState(false);
    }

    protected abstract void onSuccess(T t);

    protected abstract void onErrors(Throwable error);

    @Override
    public void onNext(T object) {
        releaseIdlingState();
        if (mWeakRefBaseView.get() != null) {
            MediatorLiveData<NetworkState> view = mWeakRefBaseView.get();
            view.setValue(new NetworkState(NetworkState.Status.SUCCESS));
        }

        onSuccess(object);
    }

    @Override
    public void onError(Throwable e) {
        onErrors(e);

        releaseIdlingState();

        Context context = null;
        if (mWeakRefContext != null)
            context = mWeakRefContext.get();
        if (context == null)
            return;


    }

    @Override
    public void onComplete() {

    }

    private String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private void releaseIdlingState() {
        IdlingResourceHelper idlingResourceHelper = mWeakRefIdlingState.get();
        if (idlingResourceHelper != null) idlingResourceHelper.setIdleState(true);
    }
}
