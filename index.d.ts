import type {EventSubscription} from 'react-native';

export type UserPresentEvent = {
	userPresent: boolean;
};

export interface KeyguardModule {
	isLocked(): Promise<boolean>;
	registerUserPresentReceiver(): Promise<boolean>;
	unregisterUserPresentReceiver(): Promise<boolean>;
	onUserPresent(listener: (event: UserPresentEvent) => void): EventSubscription;
}

declare const Keyguard: KeyguardModule;

export default Keyguard;
