import type {TurboModule, CodegenTypes} from 'react-native';
import {TurboModuleRegistry} from 'react-native';

export type UserPresentEvent = {
	userPresent: boolean;
};

export interface Spec extends TurboModule {
	isLocked(): Promise<boolean>;
	registerUserPresentReceiver(): Promise<boolean>;
	unregisterUserPresentReceiver(): Promise<boolean>;
	readonly onUserPresent: CodegenTypes.EventEmitter<UserPresentEvent>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('RNKeyguard');
