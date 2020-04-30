package kh.com.mysabay.sdk.pojo;

import android.support.annotation.IntDef;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Tan Phirum on 3/8/20
 * Gmail phirumtan@gmail.com
 */
public class NetworkState {

    private final int status;

    private final String message;

    public NetworkState(int status) {
        this.status = status;
        this.message = null;
    }

    public NetworkState(
            int status,
            @Nullable String message) {
        this.status = status;
        this.message = message;
    }

    public int status() {
        return status;
    }

    @Nullable
    public String message() {
        return message;
    }

    @NotNull
    @Override
    public String toString() {
        return "NetworkState{"
                + "status=" + status + ", "
                + "message=" + message
                + "}";
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Status.LOADING, Status.SUCCESS, Status.ERROR})
    public @interface Status {
        int LOADING = 0;
        int SUCCESS = 1;
        int ERROR = 2;
    }
}
