package kh.com.mysabay.sdk.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import kh.com.mysabay.sdk.pojo.login.LoginItem;
import kh.com.mysabay.sdk.pojo.profile.UserProfileItem;
import kh.com.mysabay.sdk.pojo.refreshToken.RefreshTokenItem;
import kh.com.mysabay.sdk.pojo.verified.VerifiedItem;
import kh.com.mysabay.sdk.webservice.api.UserApi;

/**
 * Created by Tan Phirum on 3/8/20
 * Gmail phirumtan@gmail.com
 */
@Singleton
public class UserRepo implements UserApi {

    private final UserApi userApi;

    @Inject
    public UserRepo(UserApi userApi) {
        this.userApi = userApi;
    }

    @Override
    public Observable<LoginItem> getUserLogin(String appSecret, String phone) {
        return userApi.getUserLogin(appSecret, phone);
    }

    @Override
    public Observable<VerifiedItem> postVerifyCode(String appSecret, String phone, int code) {
        return userApi.postVerifyCode(appSecret, phone, code);
    }

    @Override
    public Observable<String> postLoginWithMySabay(String appSecret) {
        return userApi.postLoginWithMySabay(appSecret);
    }

    @Override
    public Observable<UserProfileItem> getUserProfile(String appSecret, String token) {
        return this.userApi.getUserProfile(appSecret, "Bearer " + token);
    }

    @Override
    public Observable<RefreshTokenItem> postRefreshToken(String appSecret, String refreshToken) {
        return this.userApi.postRefreshToken(appSecret, refreshToken);
    }


}
