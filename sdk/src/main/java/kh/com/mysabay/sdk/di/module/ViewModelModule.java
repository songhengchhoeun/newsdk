package kh.com.mysabay.sdk.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import kh.com.mysabay.sdk.di.scopes.ViewModelKey;
import kh.com.mysabay.sdk.viewmodel.SignatureApiVM;
import kh.com.mysabay.sdk.viewmodel.StoreApiVM;
import kh.com.mysabay.sdk.viewmodel.UserApiVM;
import kh.com.mysabay.sdk.viewmodel.ViewModelFactory;

/**
 * Created by Tan Phirum on 2020-01-08
 * Gmail phirumtan@gmail.com
 */

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserApiVM.class)
    abstract ViewModel bindUserViewModel(UserApiVM userApiVM);

    @Binds
    @IntoMap
    @ViewModelKey(StoreApiVM.class)
    abstract ViewModel bindStoreViewModel(StoreApiVM storeApiVM);

    @Binds
    @IntoMap
    @ViewModelKey(SignatureApiVM.class)
    abstract ViewModel bindSignatureViewModel(SignatureApiVM signatureApiVM);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactoryGlobal(ViewModelFactory viewModelFactory);
}
