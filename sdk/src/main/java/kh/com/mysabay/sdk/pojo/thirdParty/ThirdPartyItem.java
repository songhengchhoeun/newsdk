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

import java.util.List;

/**
 * Created by Tan Phirum on 3/13/20
 * Gmail phirumtan@gmail.com
 */
public class ThirdPartyItem implements Parcelable {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("data")
    @Expose
    public List<Data> data = null;
    public final static Creator<ThirdPartyItem> CREATOR = new Creator<ThirdPartyItem>() {


        @NotNull
        @Contract("_ -> new")
        public ThirdPartyItem createFromParcel(Parcel in) {
            return new ThirdPartyItem(in);
        }

        @NotNull
        @Contract(value = "_ -> new", pure = true)
        public ThirdPartyItem[] newArray(int size) {
            return (new ThirdPartyItem[size]);
        }

    };

    protected ThirdPartyItem(@NotNull Parcel in) {
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.data, (kh.com.mysabay.sdk.pojo.shop.Data.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     */
    public ThirdPartyItem() {
    }

    /**
     * @param data
     * @param status
     */
    public ThirdPartyItem(Integer status, List<Data> data) {
        super();
        this.status = status;
        this.data = data;
    }

    public ThirdPartyItem withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public ThirdPartyItem withData(List<Data> data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("status", status).append("data", data).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(data).append(status).toHashCode();
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ThirdPartyItem)) {
            return false;
        }
        ThirdPartyItem rhs = ((ThirdPartyItem) other);
        return new EqualsBuilder().append(data, rhs.data).append(status, rhs.status).isEquals();
    }

    public void writeToParcel(@NotNull Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeList(data);
    }

    public int describeContents() {
        return 0;
    }

}