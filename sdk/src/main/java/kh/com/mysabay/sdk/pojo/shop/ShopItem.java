package kh.com.mysabay.sdk.pojo.shop;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Tan Phirum on 3/13/20
 * Gmail phirumtan@gmail.com
 */
public class ShopItem implements Parcelable {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("data")
    @Expose
    public List<Data> data = null;
    public final static Creator<ShopItem> CREATOR = new Creator<ShopItem>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ShopItem createFromParcel(Parcel in) {
            return new ShopItem(in);
        }

        public ShopItem[] newArray(int size) {
            return (new ShopItem[size]);
        }

    };

    protected ShopItem(Parcel in) {
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.data, (kh.com.mysabay.sdk.pojo.shop.Data.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     */
    public ShopItem() {
    }

    /**
     * @param data
     * @param status
     */
    public ShopItem(Integer status, List<Data> data) {
        super();
        this.status = status;
        this.data = data;
    }

    public ShopItem withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public ShopItem withData(List<Data> data) {
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ShopItem)) {
            return false;
        }
        ShopItem rhs = ((ShopItem) other);
        return new EqualsBuilder().append(data, rhs.data).append(status, rhs.status).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeList(data);
    }

    public int describeContents() {
        return 0;
    }

}