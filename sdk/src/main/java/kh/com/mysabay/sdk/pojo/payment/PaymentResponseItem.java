package kh.com.mysabay.sdk.pojo.payment;

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
public class PaymentResponseItem implements Parcelable {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("hash")
    @Expose
    public String hash;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("asset_code")
    @Expose
    public String assetCode;
    public final static Creator<PaymentResponseItem> CREATOR = new Creator<PaymentResponseItem>() {


        @NotNull
        @Contract("_ -> new")
        public PaymentResponseItem createFromParcel(Parcel in) {
            return new PaymentResponseItem(in);
        }

        @NotNull
        @Contract(value = "_ -> new", pure = true)
        public PaymentResponseItem[] newArray(int size) {
            return (new PaymentResponseItem[size]);
        }

    };

    protected PaymentResponseItem(@NotNull Parcel in) {
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.hash = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.amount = ((String) in.readValue((String.class.getClassLoader())));
        this.assetCode = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public PaymentResponseItem() {
    }

    /**
     * @param amount
     * @param assetCode
     * @param message
     * @param hash
     * @param status
     */
    public PaymentResponseItem(Integer status, String hash, String message, String amount, String assetCode) {
        super();
        this.status = status;
        this.hash = hash;
        this.message = message;
        this.amount = amount;
        this.assetCode = assetCode;
    }

    public PaymentResponseItem withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public PaymentResponseItem withHash(String hash) {
        this.hash = hash;
        return this;
    }

    public PaymentResponseItem withMessage(String message) {
        this.message = message;
        return this;
    }

    public PaymentResponseItem withAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public PaymentResponseItem withAssetCode(String assetCode) {
        this.assetCode = assetCode;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("status", status).append("hash", hash).append("message", message).append("amount", amount).append("assetCode", assetCode).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(amount).append(assetCode).append(message).append(hash).append(status).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PaymentResponseItem) == false) {
            return false;
        }
        PaymentResponseItem rhs = ((PaymentResponseItem) other);
        return new EqualsBuilder().append(amount, rhs.amount).append(assetCode, rhs.assetCode).append(message, rhs.message).append(hash, rhs.hash).append(status, rhs.status).isEquals();
    }

    public void writeToParcel(@NotNull Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(hash);
        dest.writeValue(message);
        dest.writeValue(amount);
        dest.writeValue(assetCode);
    }

    public int describeContents() {
        return 0;
    }

}