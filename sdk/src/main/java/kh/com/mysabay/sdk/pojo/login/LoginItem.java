package kh.com.mysabay.sdk.pojo.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Tan Phirum on 3/10/20
 * Gmail phirumtan@gmail.com
 */
public class LoginItem implements Parcelable {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("data")
    @Expose
    public Data data;
    public final static Creator<LoginItem> CREATOR = new Creator<LoginItem>() {


        @SuppressWarnings({
                "unchecked"
        })
        public LoginItem createFromParcel(Parcel in) {
            return new LoginItem(in);
        }

        public LoginItem[] newArray(int size) {
            return (new LoginItem[size]);
        }

    };

    protected LoginItem(Parcel in) {
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public LoginItem() {
    }

    /**
     * @param data
     * @param status
     */
    public LoginItem(Integer status, Data data) {
        super();
        this.status = status;
        this.data = data;
    }

    public LoginItem withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public LoginItem withData(Data data) {
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
        if (!(other instanceof LoginItem)) {
            return false;
        }
        LoginItem rhs = ((LoginItem) other);
        return new EqualsBuilder().append(data, rhs.data).append(status, rhs.status).isEquals();
    }

    public void writeToParcel(@NotNull Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(data);
    }

    public int describeContents() {
        return 0;
    }

}