package os.juanamd.keyguard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class UserPresentReceiver extends BroadcastReceiver {
	private static final String TAG = "RNKeyguard";

	private final RNKeyguardModule module;

	public UserPresentReceiver(RNKeyguardModule module) {
		this.module = module;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (!Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {
			return;
		}

		Log.d(TAG, "User present action received");
		try {
			module.emitUserPresent();
		} catch (Exception e) {
			Log.e(TAG, "Error dispatching user present event", e);
		}
	}
}
