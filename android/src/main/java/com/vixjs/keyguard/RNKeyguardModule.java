package com.vixjs.keyguard;

import android.app.KeyguardManager;
import android.content.Context;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RNKeyguardModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public RNKeyguardModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNKeyguard";
    }

    @ReactMethod
    public void isLocked(Callback cb) {
        KeyguardManager myKM = (KeyguardManager) reactContext.getSystemService(Context.KEYGUARD_SERVICE);
        if (myKM.inKeyguardRestrictedInputMode()) {
            //it is locked
            cb.invoke(true);
        } else {
            //it is not locked
            cb.invoke(false);
        }
    }
}
