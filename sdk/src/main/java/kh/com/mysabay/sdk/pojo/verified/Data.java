package kh.com.mysabay.sdk.pojo.verified;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Tan Phirum on 3/10/20
 * Gmail phirumtan@gmail.com
 */
public class Data implements Parcelable {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("access_token")
    @Expose
    public String accessToken;
    @SerializedName("refresh_token")
    @Expose
    public String refreshToken;
    @SerializedName("uuid")
    @Expose
    public String uuid;
    @SerializedName("service_id")
    @Expose
    public Integer serviceId;
    @SerializedName("mysabay_user_id")
    @Expose
    public Integer mysabayUserId;
    @SerializedName("service_user_id")
    @Expose
    public String serviceUserId;
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("last_login")
    @Expose
    public String lastLogin;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("expire")
    @Expose
    public int expire;

    public final static Parcelable.Creator<Data> CREATOR = new Creator<Data>() {


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

    protected Data(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.uuid = ((String) in.readValue((String.class.getClassLoader())));
        this.serviceId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.mysabayUserId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.serviceUserId = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.lastLogin = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.accessToken = ((String) in.readValue((String.class.getClassLoader())));
        this.refreshToken = ((String) in.readValue((String.class.getClassLoader())));
        this.expire = ((int) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public Data() {
    }

    /**
     * @param mysabayUserId
     * @param lastLogin
     * @param createdAt
     * @param serviceUserId
     * @param id
     * @param serviceId
     * @param uuid
     * @param status
     * @param updatedAt
     */
    public Data(Integer id, String accessToken, String refreshToken, String uuid, Integer serviceId, Integer mysabayUserId, String serviceUserId, Integer status, String lastLogin, String createdAt, String updatedAt, int expire) {
        super();
        this.id = id;
        this.uuid = uuid;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.serviceId = serviceId;
        this.mysabayUserId = mysabayUserId;
        this.serviceUserId = serviceUserId;
        this.status = status;
        this.lastLogin = lastLogin;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.expire = expire;
    }

    public Data withId(Integer id) {
        this.id = id;
        return this;
    }

    public Data withUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public Data withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public Data withRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public Data withServiceId(Integer serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public Data withMysabayUserId(Integer mysabayUserId) {
        this.mysabayUserId = mysabayUserId;
        return this;
    }

    public Data withServiceUserId(String serviceUserId) {
        this.serviceUserId = serviceUserId;
        return this;
    }

    public Data withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Data withLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
        return this;
    }

    public Data withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Data withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Data withExpire(int expire) {
        this.expire = expire;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("accessToken", accessToken).append("refreshToken", refreshToken).append("uuid", uuid).append("serviceId", serviceId).append("mysabayUserId", mysabayUserId).append("serviceUserId", serviceUserId).append("status", status).append("lastLogin", lastLogin).append("createdAt", createdAt).append("updatedAt", updatedAt).append("expire", expire).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(accessToken).append(refreshToken).append(mysabayUserId).append(lastLogin).append(createdAt).append(serviceUserId).append(id).append(serviceId).append(uuid).append(status).append(updatedAt).append(expire).toHashCode();
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
        return new EqualsBuilder().append(mysabayUserId, rhs.mysabayUserId).append(lastLogin, rhs.lastLogin).append(createdAt, rhs.createdAt).append(serviceUserId, rhs.serviceUserId).append(id, rhs.id).append(serviceId, rhs.serviceId).append(uuid, rhs.uuid).append(status, rhs.status).append(updatedAt, rhs.updatedAt).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(accessToken);
        dest.writeValue(refreshToken);
        dest.writeValue(uuid);
        dest.writeValue(serviceId);
        dest.writeValue(mysabayUserId);
        dest.writeValue(serviceUserId);
        dest.writeValue(status);
        dest.writeValue(lastLogin);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(expire);
    }

    public int describeContents() {
        return 0;
    }

}