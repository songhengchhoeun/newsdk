package kh.com.mysabay.sdk.pojo.refreshToken;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

public class Data implements Parcelable {

    @SerializedName("access_token")
    @Expose
    public String accessToken;
    @SerializedName("refresh_token")
    @Expose
    public String refreshToken;
    @SerializedName("expire")
    @Expose
    public int expire;

    public final static Creator<Data> CREATOR = new Creator<Data>() {

        @SuppressWarnings({
                "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    };

    protected Data(@NotNull Parcel in) {
        this.accessToken = ((String) in.readValue((String.class.getClassLoader())));
        this.refreshToken = ((String) in.readValue((String.class.getClassLoader())));
        this.expire = ((int) in.readValue((Long.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public Data() {
    }

    /**
     *
     * @param accessToken
     * @param refreshToken
     * @param expire
     */
    public Data(String accessToken, String refreshToken, int expire) {
        super();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expire = expire;
    }

    public Data withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public Data withRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public Data withExpire(int expire) {
        this.expire = expire;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("accessToken", accessToken).append("refreshToken", refreshToken).append("expire", expire).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(accessToken).append(refreshToken).append(expire).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Data)) {
            return false;
        }
        Data rhs = ((Data) other);
        return new EqualsBuilder().append(accessToken, rhs.accessToken).append(refreshToken, rhs.refreshToken).append(expire, rhs.expire).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(accessToken);
        dest.writeValue(refreshToken);
        dest.writeValue(expire);
    }

    public int describeContents() {
        return 0;
    }

}
