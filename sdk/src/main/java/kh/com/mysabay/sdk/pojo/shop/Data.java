package kh.com.mysabay.sdk.pojo.shop;

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
public class Data implements Parcelable {

    @SerializedName("cashier_name")
    @Expose
    public String cashierName;
    @SerializedName("package_id")
    @Expose
    public String packageId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("price_in_usd")
    @Expose
    public Float priceInUsd;
    @SerializedName("price_in_sc")
    @Expose
    public Float priceInSc;
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

    protected Data(Parcel in) {
        this.cashierName = ((String) in.readValue((String.class.getClassLoader())));
        this.packageId = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.priceInUsd = ((Float) in.readValue((Float.class.getClassLoader())));
        this.priceInSc = ((Float) in.readValue((Float.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public Data() {
    }

    /**
     * @param packageId
     * @param name
     * @param priceInUsd
     * @param priceInSc
     * @param cashierName
     */
    public Data(String cashierName, String packageId, String name, Float priceInUsd, Float priceInSc) {
        super();
        this.cashierName = cashierName;
        this.packageId = packageId;
        this.name = name;
        this.priceInUsd = priceInUsd;
        this.priceInSc = priceInSc;
    }

    public Data withCashierName(String cashierName) {
        this.cashierName = cashierName;
        return this;
    }

    public Data withPackageId(String packageId) {
        this.packageId = packageId;
        return this;
    }

    public Data withName(String name) {
        this.name = name;
        return this;
    }

    public Data withPriceInUsd(Float priceInUsd) {
        this.priceInUsd = priceInUsd;
        return this;
    }

    public Data withPriceInSc(Float priceInSc) {
        this.priceInSc = priceInSc;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("cashierName", cashierName).append("packageId", packageId).append("name", name).append("priceInUsd", priceInUsd).append("priceInSc", priceInSc).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(packageId).append(name).append(priceInUsd).append(priceInSc).append(cashierName).toHashCode();
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
        return new EqualsBuilder().append(packageId, rhs.packageId).append(name, rhs.name).append(priceInUsd, rhs.priceInUsd).append(priceInSc, rhs.priceInSc).append(cashierName, rhs.cashierName).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(cashierName);
        dest.writeValue(packageId);
        dest.writeValue(name);
        dest.writeValue(priceInUsd);
        dest.writeValue(priceInSc);
    }

    public int describeContents() {
        return 0;
    }

    public String toUSDPrice() {
        return "$" + this.priceInUsd;
    }

    public static final String PLAY_STORE = "play_store";

}
