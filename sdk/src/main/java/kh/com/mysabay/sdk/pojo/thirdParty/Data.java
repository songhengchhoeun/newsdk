package kh.com.mysabay.sdk.pojo.thirdParty;

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
public class Data implements Parcelable {

    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("asset_code")
    @Expose
    public String assetCode;
    @SerializedName("service_name")
    @Expose
    public String serviceName;
    @SerializedName("logo")
    @Expose
    public String logo;

    public boolean isPaidWith;

    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @NotNull
        @Contract("_ -> new")
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @NotNull
        @Contract(value = "_ -> new", pure = true)
        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    };

    protected Data(@NotNull Parcel in) {
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.assetCode = ((String) in.readValue((String.class.getClassLoader())));
        this.serviceName = ((String) in.readValue((String.class.getClassLoader())));
        this.logo = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public Data() {
    }

    /**
     * @param code
     * @param assetCode
     * @param logo
     * @param serviceName
     */
    public Data(String code, String assetCode, String serviceName, String logo) {
        super();
        this.code = code;
        this.assetCode = assetCode;
        this.serviceName = serviceName;
        this.logo = logo;
    }

    public Data withCode(String code) {
        this.code = code;
        return this;
    }

    public Data withAssetCode(String assetCode) {
        this.assetCode = assetCode;
        return this;
    }

    public Data withServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public Data withLogo(String logo) {
        this.logo = logo;
        return this;
    }

    public Data withIsPaidWith(boolean isPaidWith) {
        this.isPaidWith = isPaidWith;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("code", code).append("assetCode", assetCode).append("serviceName", serviceName).append("logo", logo).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(logo).append(code).append(assetCode).append(serviceName).toHashCode();
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Data)) {
            return false;
        }
        Data rhs = ((Data) other);
        return new EqualsBuilder().append(logo, rhs.logo).append(code, rhs.code).append(assetCode, rhs.assetCode).append(serviceName, rhs.serviceName).isEquals();
    }

    public void writeToParcel(@NotNull Parcel dest, int flags) {
        dest.writeValue(code);
        dest.writeValue(assetCode);
        dest.writeValue(serviceName);
        dest.writeValue(logo);
    }

    public int describeContents() {
        return 0;
    }

}