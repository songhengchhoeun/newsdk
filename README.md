# Official MySabay SDK for Android

This is the official MySabay SDK for native Android application. To use this SDK, you can follow the guides below or download the test with the example project we have in this repository.

## Create your application

Create your MySabay application if you don't have one yet at [MySabay App Dashboard](https://kh.mysabay.com:8443/index.html) and copy your `appId` 
and `appSecret` for the integration. 

## Workflow
The login and payment workflow is described with the following diagram for communication between CP app, server, mySabay SDK and mySabay API.

### Login flow
<img src="https://git.sabay.com/mysabay/sdk/app.android.sdk.mysabay.com.public/-/raw/master/Images/user-login-flow.png">

## Installation

1. Add Jitpack to your project build.gralde file

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

2. Then add this dependency to your app build.gradle file.

```gradle
dependencies {
    implementation 'com.github.sabay-digital:sdk.android-old.mysabay.com:1.0.1-o'
}
```

3. Initialize sdk & Declare Permissions
MysabaySdk needs to be initialized. You should only do this 1 time, so placing the initialization in your Application is a good idea. An example for this would be:

```java
[MyApplication.java]
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //MySabaySDK has default configuration with dark theme and sandbox url.
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
```
> NOTE: MySabaySdk is need configuration

## Integration

> Note that in order to use the store and checkout function, the user must login first.
> Follow the guide below for each functions provided by the SDK:

*  **Login**

Call 

```java
    MySabaySDK.getInstance().showLoginView(new LoginListener() {
        @Override
        public void loginSuccess(String accessToken) {
            MessageUtil.displayToast(v.getContext(), "accessToken = " + accessToken);
        }

        @Override
        public void loginFailed(Object error) {
            MessageUtil.displayToast(v.getContext(), "error = " + error);
        }
    });
``` 
to open login screen

* **Show user profile**

```java

    MySabaySDK.getInstance().getUserProfile(new UserInfoListener() {
        @Override
        public void userInfo(String info) {
            // handle user infomation here
        }
    });
```

* **Store and checkout**

```java
    MySabaySDK.getInstance().showStoreView(new PaymentListener() {
        @Override
        public void purchaseMySabaySuccess(Object dataMySabay) {
            // handle mysabay payment success here
        }

        @Override
        public void purchaseIAPSuccess(Object dataIAP) {
            // handle android in app purchase here
        }

        @Override
        public void purchaseFailed(Object dataError) {
            // hanlde error
        }
    });
```

* **Refresh token** 

```java
    MySabaySDK.getInstance().refreshToken(new RefreshTokenListener() {
        @Override
        public void refreshSuccess(String token) {
            // work with token
        }
    
        @Override
        public void refreshFailed(Throwable error) {
            //handle error here
        }
    });
```

* **Get current token**

```java
    MySabaySDK.getInstance().currentToken();
```

* **Check valid token**

```java
    MySabaySDK.getInstance().isTokenValid();
```

* **Logout**

To logout user session from the app use the following method:

``` java
    MySabaySDK.getInstance().logout();
```

## mySabay API
### Server side validation
In order for the CP server to validate the user access token received from the client as valid, mySabay also hosts pulic user api for fetching user profile and validating token. The API document is available [here](https://api-reference.mysabay.com/).

