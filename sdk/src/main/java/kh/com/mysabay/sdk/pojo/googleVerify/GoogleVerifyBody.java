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
public class GoogleVerifyBody implements Parcelable {

    @SerializedName("receipt")
    @Expose
    public ReceiptBody receipt;
    public final static Creator<GoogleVerifyBody> CREATOR = new Creator<GoogleVerifyBody>() {


        @NotNull
        @Contract("_ -> new")
        public GoogleVerifyBody createFromParcel(Parcel in) {
            return new GoogleVerifyBody(in);
        }

        @NotNull
        @Contract(value = "_ -> new", pure = true)
        public GoogleVerifyBody[] newArray(int size) {
            return (new GoogleVerifyBody[size]);
        }

    };

    protected GoogleVerifyBody(@NotNull Parcel in) {
        this.receipt = ((ReceiptBody) in.readValue((ReceiptBody.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public GoogleVerifyBody() {
    }

    /**
     * @param receipt
     */
    public GoogleVerifyBody(ReceiptBody receipt) {
        super();
        this.receipt = receipt;
    }

    public GoogleVerifyBody withReceipt(ReceiptBody receipt) {
        this.receipt = receipt;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("receipt", receipt).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(receipt).toHashCode();
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GoogleVerifyBody)) {
            return false;
        }
        GoogleVerifyBody rhs = ((GoogleVerifyBody) other);
        return new EqualsBuilder().append(receipt, rhs.receipt).isEquals();
    }

    public void writeToParcel(@NotNull Parcel dest, int flags) {
        dest.writeValue(receipt);
    }

    public int describeContents() {
        return 0;
    }

}