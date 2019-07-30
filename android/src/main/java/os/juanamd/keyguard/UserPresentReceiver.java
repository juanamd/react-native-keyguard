package os.juanamd.keyguard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.ReactApplication;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class UserPresentReceiver extends BroadcastReceiver {
	public static String USER_PRESENT_EVENT = "userPresent";

	@Override
	public void onReceive(final Context context, final Intent intent) {
		Log.d("RNKeyguard", "User present action received");
		if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
			try {
				this.getReactContext(context)
					.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
					.emit(USER_PRESENT_EVENT, Arguments.createMap());
			} catch (Exception e) {
				Log.e("RNKeyguard", "Error dispatching user present event", e);
			}
		}
	}

	protected ReactContext getReactContext(final Context context) {
		ReactApplication reactApp = (ReactApplication) context.getApplicationContext();
		return reactApp.getReactNativeHost().getReactInstanceManager().getCurrentReactContext();
	}
}
