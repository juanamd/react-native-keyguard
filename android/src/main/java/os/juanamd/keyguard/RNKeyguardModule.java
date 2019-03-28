package os.juanamd.keyguard;

import android.app.KeyguardManager;
import android.content.Context;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RNKeyguardModule extends ReactContextBaseJavaModule {
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
}
