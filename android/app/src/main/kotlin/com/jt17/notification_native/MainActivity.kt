package com.jt17.notification_native

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {
    private lateinit var flutterChannel: MethodChannel

    companion object {
        const val CHANNEL_NAME = "com.jt17.notification_test"
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        flutterChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL_NAME)

        flutterChannel.setMethodCallHandler { call, result ->
            if (call.method == "show_notification_on_foreground") {
                val textFromFlutter: String? = call.argument("text")
                NotificationManager.testNotification(this, textFromFlutter ?: "null")
            }
        }
    }

}
