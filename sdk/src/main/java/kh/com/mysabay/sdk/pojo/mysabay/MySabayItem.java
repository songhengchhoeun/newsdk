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

import java.util.List;

/**
 * Created by Tan Phirum on 3/13/20
 * Gmail phirumtan@gmail.com
 */
public class MySabayItem implements Parcelable {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("data")
    @Expose
    public List<Data> data = null;
    public final static Creator<MySabayItem> CREATOR = new Creator<MySabayItem>() {


        @NotNull
        @Contract("_ -> new")
        public MySabayItem createFromParcel(Parcel in) {
            return new MySabayItem(in);
        }

        @NotNull
        @Contract(value = "_ -> new", pure = true)
        public MySabayItem[] newArray(int size) {
            return (new MySabayItem[size]);
        }

    };

    protected MySabayItem(@NotNull Parcel in) {
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.data, (kh.com.mysabay.sdk.pojo.shop.Data.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     */
    public MySabayItem() {
    }

    /**
     * @param data
     * @param status
     */
    public MySabayItem(Integer status, List<Data> data) {
        super();
        this.status = status;
        this.data = data;
    }

    public MySabayItem withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public MySabayItem withData(List<Data> data) {
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
        if ((other instanceof MySabayItem) == false) {
            return false;
        }
        MySabayItem rhs = ((MySabayItem) other);
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