import 'package:flutter/material.dart';
import 'package:flutter_webrtc/flutter_webrtc.dart';
import 'package:videocall/signalling.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  // await Firebase.initializeApp();
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Sahayak',
      debugShowCheckedModeBanner: false,
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key? key}) : super(key: key);

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  Signaling signaling = Signaling();
  RTCVideoRenderer _localRenderer = RTCVideoRenderer();
  RTCVideoRenderer _remoteRenderer = RTCVideoRenderer();
  String? roomId;
  TextEditingController textEditingController = TextEditingController(text: '');

  @override
  void initState() {
    _localRenderer.initialize();
    _remoteRenderer.initialize();
    _openCamera();
    signaling.onAddRemoteStream = ((stream) {
      _remoteRenderer.srcObject = stream;
      setState(() {});
    });

    super.initState();
  }

  @override
  void didChangeDependencies() {
    _openCamera();
    super.didChangeDependencies();
  }

  Future<void> _openCamera() async {
    await signaling.openUserMedia(_localRenderer, _remoteRenderer);
  }

  @override
  void dispose() {
    _localRenderer.dispose();
    _remoteRenderer.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      extendBodyBehindAppBar: true,
      extendBody: true,
      body: FutureBuilder(
          future: _openCamera(),
          builder: (context, data) {
            return Stack(
              children: [
                Container(
                  height: double.infinity,
                  color: Colors.black,
                  child: RTCVideoView(
                    _remoteRenderer,
                    mirror: true,
                  ),
                ),
                Positioned(
                  top: 40,
                  left: -40,
                  child: Container(
                    height: 150,
                    width: 200,
                    child: RTCVideoView(
                      _localRenderer,
                      mirror: true,
                    ),
                  ),
                ),
                Positioned(
                  bottom: 80,
                  left: MediaQuery.of(context).size.width / 2 - 100,
                  child: InkWell(
                    onTap: () {},
                    child: CircleAvatar(
                      backgroundColor: Colors.white,
                      radius: 32,
                      child: Icon(
                        Icons.phone_disabled,
                        size: 26,
                      ),
                    ),
                  ),
                ),
                Positioned(
                  bottom: 80,
                  right: MediaQuery.of(context).size.width / 2 - 100,
                  child: InkWell(
                    onTap: () {},
                    child: CircleAvatar(
                      backgroundColor: Colors.white,
                      radius: 32,
                      child: Icon(
                        Icons.flashlight_off,
                        size: 26,
                      ),
                    ),
                  ),
                ),
                // ElevatedButton(
                //   onPressed: () {
                //     signaling.openUserMedia(_localRenderer, _remoteRenderer);
                //   },
                //   child: Text("Open camera & microphone"),
                // ),
                //
                // ElevatedButton(
                //   onPressed: () async {
                //     roomId = await signaling.createRoom(_remoteRenderer);
                //     textEditingController.text = roomId!;
                //     setState(() {});
                //   },
                //   child: Text("Create room"),
                // ),
                //
                // ElevatedButton(
                //   onPressed: () {
                //     // Add roomId
                //     signaling.joinRoom(
                //       textEditingController.text,
                //       _remoteRenderer,
                //     );
                //   },
                //   child: Text("Join room"),
                // ),
                //
                // ElevatedButton(
                //   onPressed: () {
                //     signaling.hangUp(_localRenderer);
                //   },
                //   child: Text("Hangup"),
                // ),
                //
                // Expanded(
                //   child: Padding(
                //     padding: const EdgeInsets.all(8.0),
                //     child: Row(
                //       mainAxisAlignment: MainAxisAlignment.center,
                //       children: [
                //         Expanded(child: RTCVideoView(_localRenderer, mirror: true)),
                //         Expanded(child: RTCVideoView(_remoteRenderer)),
                //       ],
                //     ),
                //   ),
                // ),
                // Padding(
                //   padding: const EdgeInsets.all(8.0),
                //   child: Row(
                //     mainAxisAlignment: MainAxisAlignment.center,
                //     children: [
                //       Text("Join the following Room: "),
                //       Flexible(
                //         child: TextFormField(
                //           controller: textEditingController,
                //         ),
                //       )
                //     ],
                //   ),
                // ),
              ],
            );
          }),
    );
  }
}
