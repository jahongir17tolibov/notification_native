import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:permission_handler/permission_handler.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final TextEditingController controller = TextEditingController();

  static const String _channelName = 'com.jt17.notification_test';
  static const String _invokedMethod = 'show_notification_on_foreground';
  static const _platform = MethodChannel(_channelName);

  Future<void> _showNotificationForeground() async {
    try {
      await _platform.invokeMethod(_invokedMethod, {'text': controller.text});
    } on PlatformException catch (_) {
      rethrow;
    }
  }

  @override
  void initState() {
    _checkNotifPermissions();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
      ),
      body: Center(
        child: TextField(controller: controller),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () async {
          if (await Permission.notification.isDenied) {
            _checkNotifPermissions();
          } else {
            await _showNotificationForeground();
          }
        },
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ),
    );
  }

  void _checkNotifPermissions() async {
    await Permission.notification.isDenied.then((value) {
      if (value) {
        Permission.notification.request();
      }
    });
  }
}
