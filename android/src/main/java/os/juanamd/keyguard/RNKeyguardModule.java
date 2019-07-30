package os.juanamd.keyguard;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RNKeyguardModule extends ReactContextBaseJavaModule {
	private static final String TAG = "RNKeyguard";

	private UserPresentReceiver receiver;

	public RNKeyguardModule(ReactApplicationContext reactContext) {
		super(reactContext);
	}

	@Override
	public String getName() {
		return TAG;
	}

	@ReactMethod
	public void isLocked(final Promise promise) {
		try {
			ReactApplicationContext context = this.getReactApplicationContext();
			KeyguardManager kwManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
			promise.resolve(kwManager.isKeyguardLocked());
			Log.d(TAG, "Is locked: " + kwManager.isKeyguardLocked());
		} catch (Exception e) {
			promise.reject(e);
			Log.e(TAG, "Error on isLocked()", e);
		}
	}

	@ReactMethod
	public void registerUserPresentReceiver(final Promise promise) {
		if (this.receiver != null) {
			promise.resolve(false);
		} else {
			ReactApplicationContext context = this.getReactApplicationContext();
			if (context == null) {
				promise.reject("Null react context");
			} else {
				try {
					this.receiver = new UserPresentReceiver();
					context.registerReceiver(this.receiver, new IntentFilter(Intent.ACTION_USER_PRESENT));
					promise.resolve(true);
					Log.d(TAG, "Registered user present receiver");
				} catch (Exception e) {
					Log.e(TAG, "Error on registerUserPresentReceiver()", e);
					promise.reject(e);
				}
			}
		}
	}

	@ReactMethod
	public void unregisterUserPresentReceiver(final Promise promise) {
		if (this.receiver == null) {
			promise.resolve(false);
		} else {
			ReactApplicationContext context = this.getReactApplicationContext();
			if (context == null) {
				promise.reject("Null react context");
			} else {
				try {
					context.unregisterReceiver(this.receiver);
					this.receiver = null;
					promise.resolve(true);
					Log.d(TAG, "Unregistered user present receiver");
				} catch (Exception e) {
					Log.e(TAG, "Error on unregisterUserPresentReceiver()", e);
					promise.reject(e);
				}
			}
		}
	}

	@Override
	public void onCatalystInstanceDestroy() {
		super.onCatalystInstanceDestroy();
		ReactApplicationContext context = this.getReactApplicationContext();
		if (context != null && this.receiver != null) {
			context.unregisterReceiver(this.receiver);
			Log.d(TAG, "Unregistered receiver on destroy");
		}
	}
}
