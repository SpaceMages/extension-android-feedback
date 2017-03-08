## OpenFL Android Feedback Extension

Lime extension for sending feedback to Google Play on most android devices. Triggers a Java exception that allows you to start the beta feedback process.

## How to Install

Install through haxelib

`haxelib install extension-android-feedback`

Add the following line to your `Project.xml` :

`<haxelib name="extension-android-feedback" if="android" />`

And finally in your project use the following code :

```
#if android
	extension.androidFeedback.AndroidFeedback.sendFeedback();
#end
```