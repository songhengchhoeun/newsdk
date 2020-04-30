package kh.com.mysabay.sdk.pojo.googleVerify;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Tan Phirum on 3/18/20
 * Gmail phirumtan@gmail.com
 */
public class GoogleVerifyResponse implements Parcelable {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public Object data = null;
    public final static Creator<GoogleVerifyResponse> CREATOR = new Creator<GoogleVerifyResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public GoogleVerifyResponse createFromParcel(Parcel in) {
            return new GoogleVerifyResponse(in);
        }

        public GoogleVerifyResponse[] newArray(int size) {
            return (new GoogleVerifyResponse[size]);
        }

    };

    protected GoogleVerifyResponse(Parcel in) {
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.data = in.readValue(Object.class.getClassLoader());
    }

    /**
     * No args constructor for use in serialization
     */
    public GoogleVerifyResponse() {
    }

    /**
     * @param data
     * @param message
     * @param status
     */
    public GoogleVerifyResponse(Integer status, String message, Object data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public GoogleVerifyResponse withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public GoogleVerifyResponse withMessage(String message) {
        this.message = message;
        return this;
    }

    public GoogleVerifyResponse withData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("status", status).append("message", message).append("data", data).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(message).append(data).append(status).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GoogleVerifyResponse)) {
            return false;
        }
        GoogleVerifyResponse rhs = ((GoogleVerifyResponse) other);
        return new EqualsBuilder().append(message, rhs.message).append(data, rhs.data).append(status, rhs.status).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(message);
        dest.writeValue(data);
    }

    public int describeContents() {
        return 0;
    }

}