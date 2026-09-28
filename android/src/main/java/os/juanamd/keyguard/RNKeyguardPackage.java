package os.juanamd.keyguard;

import java.util.HashMap;
import java.util.Map;

import com.facebook.react.BaseReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.react.module.model.ReactModuleInfoProvider;

public class RNKeyguardPackage extends BaseReactPackage {
	@Override
	public NativeModule getModule(String name, ReactApplicationContext reactContext) {
		if (name.equals(RNKeyguardModule.NAME)) {
			return new RNKeyguardModule(reactContext);
		}
		return null;
	}

	@Override
	public ReactModuleInfoProvider getReactModuleInfoProvider() {
		return new ReactModuleInfoProvider() {
			@Override
			public Map<String, ReactModuleInfo> getReactModuleInfos() {
				Map<String, ReactModuleInfo> map = new HashMap<>();
				map.put(RNKeyguardModule.NAME, new ReactModuleInfo(
					RNKeyguardModule.NAME,
					RNKeyguardModule.class.getName(),
					false,
					false,
					false,
					true
				));
				return map;
			}
		};
	}
}
