package os.juanamd.keyguard;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RNKeyguardModule extends ReactContextBaseJavaModule {
    private IntentFilter intentFilter;

    public RNKeyguardModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RNKeyguard";
    }

    @ReactMethod
    public void isLocked(Promise promise) {
        ReactApplicationContext context = this.getReactApplicationContext();
        KeyguardManager kwManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        try {
			promise.resolve(kwManager.isKeyguardLocked());
		}
		catch (Exception e) {
			promise.reject(e);
		}
    }

    @ReactMethod
    public void registerUserPresentReceiver() {
        if (this.intentFilter == null) {
            this.intentFilter = new IntentFilter(Intent.ACTION_USER_PRESENT);
		    this.getReactApplicationContext().registerReceiver(new UserPresentReceiver(), this.intentFilter);
        }
    }
}
