package kh.com.mysabay.sdk.pojo.googleVerify;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Tan Phirum on 3/13/20
 * Gmail phirumtan@gmail.com
 */
public class DataBody implements Parcelable {

    @SerializedName("orderId")
    @Expose
    public String orderId;
    @SerializedName("packageName")
    @Expose
    public String packageName;
    @SerializedName("productId")
    @Expose
    public String productId;
    @SerializedName("purchaseTime")
    @Expose
    public long purchaseTime;
    @SerializedName("purchaseState")
    @Expose
    public Integer purchaseState;
    @SerializedName("purchaseToken")
    @Expose
    public String purchaseToken;
    public final static Creator<DataBody> CREATOR = new Creator<DataBody>() {


        @NotNull
        @Contract("_ -> new")
        public DataBody createFromParcel(Parcel in) {
            return new DataBody(in);
        }

        @NotNull
        @Contract(value = "_ -> new", pure = true)
        public DataBody[] newArray(int size) {
            return (new DataBody[size]);
        }

    };

    protected DataBody(@NotNull Parcel in) {
        this.orderId = ((String) in.readValue((String.class.getClassLoader())));
        this.packageName = ((String) in.readValue((String.class.getClassLoader())));
        this.productId = ((String) in.readValue((String.class.getClassLoader())));
        this.purchaseTime = ((Long) in.readValue((Long.class.getClassLoader())));
        this.purchaseState = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.purchaseToken = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public DataBody() {
    }

    /**
     * @param purchaseToken
     * @param productId
     * @param orderId
     * @param purchaseTime
     * @param packageName
     * @param purchaseState
     */
    public DataBody(String orderId, String packageName, String productId, long purchaseTime, Integer purchaseState, String purchaseToken) {
        super();
        this.orderId = orderId;
        this.packageName = packageName;
        this.productId = productId;
        this.purchaseTime = purchaseTime;
        this.purchaseState = purchaseState;
        this.purchaseToken = purchaseToken;
    }

    public DataBody withOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public DataBody withPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public DataBody withProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public DataBody withPurchaseTime(long purchaseTime) {
        this.purchaseTime = purchaseTime;
        return this;
    }

    public DataBody withPurchaseState(Integer purchaseState) {
        this.purchaseState = purchaseState;
        return this;
    }

    public DataBody withPurchaseToken(String purchaseToken) {
        this.purchaseToken = purchaseToken;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("orderId", orderId).append("packageName", packageName).append("productId", productId).append("purchaseTime", purchaseTime).append("purchaseState", purchaseState).append("purchaseToken", purchaseToken).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(purchaseToken).append(productId).append(orderId).append(purchaseTime).append(packageName).append(purchaseState).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DataBody)) {
            return false;
        }
        DataBody rhs = ((DataBody) other);
        return new EqualsBuilder().append(purchaseToken, rhs.purchaseToken).append(productId, rhs.productId).append(orderId, rhs.orderId).append(purchaseTime, rhs.purchaseTime).append(packageName, rhs.packageName).append(purchaseState, rhs.purchaseState).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(orderId);
        dest.writeValue(packageName);
        dest.writeValue(productId);
        dest.writeValue(purchaseTime);
        dest.writeValue(purchaseState);
        dest.writeValue(purchaseToken);
    }

    public int describeContents() {
        return 0;
    }

}