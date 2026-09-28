# react-native-keyguard

Check if Android device screen is locked.

## Usage

```ts
import Keyguard from 'react-native-keyguard';

const locked = await Keyguard.isLocked();

const subscription = Keyguard.onUserPresent(() => {
	console.log('User unlocked the device');
});

subscription.remove();
```

`registerUserPresentReceiver()` and `unregisterUserPresentReceiver()` remain available for explicit receiver control. The receiver is registered automatically when the native module is created, so they are normally unnecessary.

Android only. This version requires React Native's New Architecture.
