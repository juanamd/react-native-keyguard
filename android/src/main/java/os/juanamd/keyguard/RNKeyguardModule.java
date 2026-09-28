package os.juanamd.keyguard;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = RNKeyguardModule.NAME)
public class RNKeyguardModule extends NativeRNKeyguardSpec {
	public static final String NAME = "RNKeyguard";
	private static final String TAG = "RNKeyguard";

	private UserPresentReceiver receiver;

	public RNKeyguardModule(ReactApplicationContext reactContext) {
		super(reactContext);
		registerReceiver();
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void isLocked(Promise promise) {
		try {
			ReactApplicationContext context = getReactApplicationContext();
			KeyguardManager keyguardMgr = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
			if (keyguardMgr == null) {
				promise.reject("E_KEYGUARD_MANAGER", "KeyguardManager unavailable");
				return;
			}

			promise.resolve(keyguardMgr.isKeyguardLocked());
		} catch (Exception e) {
			Log.e(TAG, "Error on isLocked()", e);
			promise.reject(e);
		}
	}

	@Override
	public void registerUserPresentReceiver(Promise promise) {
		try {
			promise.resolve(registerReceiver());
		} catch (Exception e) {
			Log.e(TAG, "Error on registerUserPresentReceiver()", e);
			promise.reject(e);
		}
	}

	@Override
	public void unregisterUserPresentReceiver(Promise promise) {
		try {
			promise.resolve(unregisterReceiver());
		} catch (Exception e) {
			Log.e(TAG, "Error on unregisterUserPresentReceiver()", e);
			promise.reject(e);
		}
	}

	private synchronized boolean registerReceiver() {
		if (receiver != null) {
			return false;
		}

		UserPresentReceiver newReceiver = new UserPresentReceiver(this);
		IntentFilter filter = new IntentFilter(Intent.ACTION_USER_PRESENT);
		ReactApplicationContext context = getReactApplicationContext();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			context.registerReceiver(newReceiver, filter, Context.RECEIVER_EXPORTED);
		} else {
			context.registerReceiver(newReceiver, filter);
		}

		receiver = newReceiver;
		Log.d(TAG, "Registered user present receiver");
		return true;
	}

	private synchronized boolean unregisterReceiver() {
		if (receiver == null) {
			return false;
		}

		ReactApplicationContext context = getReactApplicationContext();
		context.unregisterReceiver(receiver);
		receiver = null;
		Log.d(TAG, "Unregistered user present receiver");
		return true;
	}

	void emitUserPresent() {
		WritableMap event = Arguments.createMap();
		event.putBoolean("userPresent", true);
		emitOnUserPresent(event);
	}

	@Override
	public void invalidate() {
		try {
			unregisterReceiver();
		} catch (Exception e) {
			Log.e(TAG, "Error unregistering user present receiver", e);
		}

		super.invalidate();
	}
}
