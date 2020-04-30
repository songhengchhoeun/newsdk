package kh.com.mysabay.sample;

import android.app.Application;

import kh.com.mysabay.sdk.MySabaySDK;
import kh.com.mysabay.sdk.SdkConfiguration;
import kh.com.mysabay.sdk.utils.SdkTheme;

/** d
 * Created by Tan Phirum on 4/12/20
 * Gmail phirumtan@gmail.com
 */
public class SampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        final SdkConfiguration configuration = new SdkConfiguration.Builder(
                "55", // mysabay app Id
                "SDK sample", //mysabay  app name
                "9c85c50a4362f687cd4507771ba81db5cf50eaa0b3008f4f943f77ba3ac6386b", //MySabay App Secret
                "", // license key
                "") // merchant id
                .setSdkTheme(SdkTheme.Light)
                .setToUseSandBox(true).build();
        MySabaySDK.Impl.setDefaultInstanceConfiguration(this, configuration);
    }
}
