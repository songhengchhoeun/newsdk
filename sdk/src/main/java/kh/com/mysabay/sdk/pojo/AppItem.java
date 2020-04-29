package kh.com.mysabay.sdk.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Tan Phirum on 3/9/20
 * Gmail phirumtan@gmail.com
 */
public class AppItem implements Parcelable {

    @SerializedName("app_secret")
    @Expose
    public String appSecret;

    @SerializedName("token")
    @Expose
    public String token;

    @SerializedName("refreshToken")
    @Expose
    public String refreshToken;

    @SerializedName("uuid")
    @Expose
    public String uuid;

    @SerializedName("expire")
    @Expose
    public long expire;

    public AppItem(String appSecret, String token, String refreshToken, String uuid, long expire) {
        this.appSecret = appSecret;
        this.token = token;
        this.refreshToken = refreshToken;
        this.uuid = uuid;
        this.expire = expire;
    }

    public AppItem(String appSecret, String token, String refreshToken) {
        this(appSecret, token, refreshToken, null, 0);
    }

    public AppItem(String appSecret) {
        this(appSecret, null, null, null, 0);
    }

    protected AppItem(@NotNull Parcel in) {
        appSecret = in.readString();
        token = in.readString();
        refreshToken = in.readString();
        uuid = in.readString();
        expire = in.readLong();
    }

    public static final Creator<AppItem> CREATOR = new Creator<AppItem>() {
        @Override
        public AppItem createFromParcel(Parcel in) {
            return new AppItem(in);
        }

        @Override
        public AppItem[] newArray(int size) {
            return new AppItem[size];
        }
    };

    public AppItem withAppSecret(String appSecret) {
        this.appSecret = appSecret;
        return this;
    }

    public AppItem withToken(String token) {
        this.token = token;
        return this;
    }

    public AppItem withRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public AppItem withUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public AppItem withExpired(long expire) {
        this.expire = expire;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(appSecret);
        dest.writeString(token);
        dest.writeString(refreshToken);
        dest.writeString(uuid);
        dest.writeLong(expire);
    }
}
