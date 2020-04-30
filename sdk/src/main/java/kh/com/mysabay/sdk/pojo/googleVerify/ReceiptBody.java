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
public class ReceiptBody implements Parcelable {

    @SerializedName("data")
    @Expose
    public DataBody data;
    @SerializedName("signature")
    @Expose
    public String signature;
    public final static Creator<ReceiptBody> CREATOR = new Creator<ReceiptBody>() {


        @NotNull
        @Contract("_ -> new")
        public ReceiptBody createFromParcel(Parcel in) {
            return new ReceiptBody(in);
        }

        @NotNull
        @Contract(value = "_ -> new", pure = true)
        public ReceiptBody[] newArray(int size) {
            return (new ReceiptBody[size]);
        }

    };

    protected ReceiptBody(@NotNull Parcel in) {
        this.data = ((DataBody) in.readValue((DataBody.class.getClassLoader())));
        this.signature = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public ReceiptBody() {
    }

    /**
     * @param data
     * @param signature
     */
    public ReceiptBody(DataBody data, String signature) {
        super();
        this.data = data;
        this.signature = signature;
    }

    public ReceiptBody withData(DataBody data) {
        this.data = data;
        return this;
    }

    public ReceiptBody withSignature(String signature) {
        this.signature = signature;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("data", data).append("signature", signature).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(data).append(signature).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ReceiptBody)) {
            return false;
        }
        ReceiptBody rhs = ((ReceiptBody) other);
        return new EqualsBuilder().append(data, rhs.data).append(signature, rhs.signature).isEquals();
    }

    public void writeToParcel(@NotNull Parcel dest, int flags) {
        dest.writeValue(data);
        dest.writeValue(signature);
    }

    public int describeContents() {
        return 0;
    }

}