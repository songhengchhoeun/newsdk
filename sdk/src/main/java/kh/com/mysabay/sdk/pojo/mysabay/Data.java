package kh.com.mysabay.sdk.pojo.mysabay;

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

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("asset_code")
    @Expose
    public String assetCode;
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
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.assetCode = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public Data() {
    }

    /**
     * @param code
     * @param assetCode
     * @param name
     */
    public Data(String name, String code, String assetCode) {
        super();
        this.name = name;
        this.code = code;
        this.assetCode = assetCode;
    }

    public Data withName(String name) {
        this.name = name;
        return this;
    }

    public Data withCode(String code) {
        this.code = code;
        return this;
    }

    public Data withAssetCode(String assetCode) {
        this.assetCode = assetCode;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("code", code).append("assetCode", assetCode).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(code).append(assetCode).toHashCode();
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
        return new EqualsBuilder().append(name, rhs.name).append(code, rhs.code).append(assetCode, rhs.assetCode).isEquals();
    }

    public void writeToParcel(@NotNull Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(code);
        dest.writeValue(assetCode);
    }

    public int describeContents() {
        return 0;
    }

}