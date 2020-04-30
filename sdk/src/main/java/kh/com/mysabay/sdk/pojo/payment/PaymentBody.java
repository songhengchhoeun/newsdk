package kh.com.mysabay.sdk.pojo.payment;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Tan Phirum on 3/13/20
 * Gmail phirumtan@gmail.com
 */
public class PaymentBody implements Parcelable {

    @SerializedName("uuid")
    @Expose
    public String uuid;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("cashier_code")
    @Expose
    public String cashierCode;
    @SerializedName("asset_code")
    @Expose
    public String assetCode;
    public final static Creator<PaymentBody> CREATOR = new Creator<PaymentBody>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PaymentBody createFromParcel(Parcel in) {
            return new PaymentBody(in);
        }

        public PaymentBody[] newArray(int size) {
            return (new PaymentBody[size]);
        }

    };

    protected PaymentBody(Parcel in) {
        this.uuid = ((String) in.readValue((String.class.getClassLoader())));
        this.amount = ((String) in.readValue((String.class.getClassLoader())));
        this.cashierCode = ((String) in.readValue((String.class.getClassLoader())));
        this.assetCode = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * @param amount
     * @param assetCode
     * @param cashierCode
     * @param uuid
     */
    public PaymentBody(String uuid, String amount, String cashierCode, String assetCode) {
        super();
        this.uuid = uuid;
        this.amount = amount;
        this.cashierCode = cashierCode;
        this.assetCode = assetCode;
    }

    public PaymentBody withUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public PaymentBody withAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public PaymentBody withCashierCode(String cashierCode) {
        this.cashierCode = cashierCode;
        return this;
    }

    public PaymentBody withAssetCode(String assetCode) {
        this.assetCode = assetCode;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("uuid", uuid).append("amount", amount).append("cashierCode", cashierCode).append("assetCode", assetCode).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(amount).append(cashierCode).append(assetCode).append(uuid).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PaymentBody)) {
            return false;
        }
        PaymentBody rhs = ((PaymentBody) other);
        return new EqualsBuilder().append(amount, rhs.amount).append(cashierCode, rhs.cashierCode).append(assetCode, rhs.assetCode).append(uuid, rhs.uuid).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(uuid);
        dest.writeValue(amount);
        dest.writeValue(cashierCode);
        dest.writeValue(assetCode);
    }

    public int describeContents() {
        return 0;
    }

}