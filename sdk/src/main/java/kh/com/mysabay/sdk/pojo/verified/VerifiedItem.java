package kh.com.mysabay.sdk.pojo.verified;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Tan Phirum on 3/10/20
 * Gmail phirumtan@gmail.com
 */
public class VerifiedItem implements Parcelable {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("data")
    @Expose
    public Data data;
    public final static Creator<VerifiedItem> CREATOR = new Creator<VerifiedItem>() {


        @SuppressWarnings({
                "unchecked"
        })
        public VerifiedItem createFromParcel(Parcel in) {
            return new VerifiedItem(in);
        }

        public VerifiedItem[] newArray(int size) {
            return (new VerifiedItem[size]);
        }

    };

    protected VerifiedItem(Parcel in) {
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public VerifiedItem() {
    }

    /**
     * @param data
     * @param status
     */
    public VerifiedItem(Integer status, Data data) {
        super();
        this.status = status;
        this.data = data;
    }

    public VerifiedItem withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public VerifiedItem withData(Data data) {
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
        if (!(other instanceof VerifiedItem)) {
            return false;
        }
        VerifiedItem rhs = ((VerifiedItem) other);
        return new EqualsBuilder().append(data, rhs.data).append(status, rhs.status).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(data);
    }

    public int describeContents() {
        return 0;
    }

}