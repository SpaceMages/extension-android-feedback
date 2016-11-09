package extension.androidFeedback;

class AndroidFeedback {
	
	#if (android)
		public static var sendFeedback:Void->Void = openfl.utils.JNI.createStaticMethod("org/haxe/extension/AndroidFeedback", "sendFeedback", "()V");
	#else
		public static var sendFeedback:Void->Void = function(){};
	#end 

}
