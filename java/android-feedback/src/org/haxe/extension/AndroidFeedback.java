package org.haxe.extension;

import android.content.Intent;
import android.os.Bundle;
import android.app.ApplicationErrorReport;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.lang.StackTraceElement;
import org.haxe.extension.Extension;
import android.app.Activity;

public class AndroidFeedback extends Extension {
	
	/**
	 * Called when an activity you launched exits, giving you the requestCode 
	 * you started it with, the resultCode it returned, and any additional data 
	 * from it.
	 */
	@Override public boolean onActivityResult (int requestCode, int resultCode, Intent data) {
		return true;
	}

	/**
	 * Called when the activity is starting.
	 */
	@Override public void onCreate (Bundle savedInstanceState) { }
	
	/**
	 * Perform any final cleanup before an activity is destroyed.
	 */
	@Override public void onDestroy () { }
	
	
	/**
	 * Called as part of the activity lifecycle when an activity is going into
	 * the background, but has not (yet) been killed.
	 */
	@Override public void onPause () { }
	
	/**
	 * Called after {@link #onStop} when the current activity is being 
	 * re-displayed to the user (the user has navigated back to it).
	 */
	@Override public void onRestart () { }
	
	
	/**
	 * Called after {@link #onRestart}, or {@link #onPause}, for your activity 
	 * to start interacting with the user.
	 */
	@Override public void onResume () { }
	
	
	/**
	 * Called after {@link #onCreate} &mdash; or after {@link #onRestart} when  
	 * the activity had been stopped, but is now again being displayed to the 
	 * user.
	 */
	@Override public void onStart () { }
		
	/**
	 * Called when the activity is no longer visible to the user, because 
	 * another activity has been resumed and is covering this one. 
	 */
	@Override public void onStop () { }

	///////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////
	// PUBLIC INTERFACE
	///////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////

	public static void sendFeedback(){
		try {
		    throw new FeedbackException("Feedback from User");
		} catch (FeedbackException e) {
		    ApplicationErrorReport report = new ApplicationErrorReport();
		    report.packageName = report.processName = Extension.mainContext.getApplicationContext()
		        .getPackageName();
		    report.time = System.currentTimeMillis();
		    report.type = ApplicationErrorReport.TYPE_CRASH;
		    report.systemApp = false;

		    ApplicationErrorReport.CrashInfo crash = new ApplicationErrorReport.CrashInfo();
		    crash.exceptionClassName = e.getClass().getSimpleName();
		    crash.exceptionMessage = e.getMessage();

		    StringWriter writer = new StringWriter();
		    PrintWriter printer = new PrintWriter(writer);
		    e.printStackTrace(printer);

		    crash.stackTrace = writer.toString();

		    StackTraceElement stack = e.getStackTrace()[0];
		    crash.throwClassName = stack.getClassName();
		    crash.throwFileName = stack.getFileName();
		    crash.throwLineNumber = stack.getLineNumber();
		    crash.throwMethodName = stack.getMethodName();

		    report.crashInfo = crash;

		    Intent intent = new Intent(Intent.ACTION_APP_ERROR);
		    intent.setClassName("com.google.android.feedback", "com.google.android.feedback.FeedbackActivity");
		    intent.putExtra(Intent.EXTRA_BUG_REPORT, report);
		    Extension.mainContext.startActivity(intent);
		}
	}

}
