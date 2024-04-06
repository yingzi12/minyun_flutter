import 'package:bruno/bruno.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_mobx/flutter_mobx.dart';
import 'package:minyun/screens/account_screen.dart';
import 'package:minyun/screens/splash_sceen.dart';
void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return Observer(
      builder: (BuildContext context) {
        return MaterialApp(
          debugShowCheckedModeBanner: false,
          title: 'ProScan App',
          theme: getThemeData(mode.theme),
          home: SplashScreen(),
          localizationsDelegates: [
            // GlobalMaterialLocalizations.delegate,
            // GlobalCupertinoLocalizations.delegate,
            BrnLocalizationDelegate(), // Add the BrnLocalizationDelegate
          ],
          supportedLocales: [
            const Locale('en', ''), // English (basic example)
            // You can add other supported locales here
          ],
          builder: (context, child) {
            return ScrollConfiguration(behavior: AppBehavior(), child: child!);
          },
        );
      },
    );
  }
}

class AppBehavior extends ScrollBehavior {
  @override
  Widget buildOverscrollIndicator(BuildContext context, Widget child, ScrollableDetails details) {
    return child;
  }
}

getThemeData(bool isDarkMode) {
  if (isDarkMode) {
    return ThemeData(
      brightness: Brightness.dark,
      primarySwatch: Colors.blue,
      appBarTheme: AppBarTheme(
        backgroundColor: Color(0xFF181a20),
        iconTheme: IconThemeData(color: Colors.white),
        systemOverlayStyle: SystemUiOverlayStyle(
          statusBarColor: Color(0xFF181a20),
          statusBarIconBrightness: Brightness.light,
          statusBarBrightness: Brightness.dark,
        ),
      ),
      scaffoldBackgroundColor: Color(0xFF181a20),
      bottomNavigationBarTheme: BottomNavigationBarThemeData(
        backgroundColor: Color(0xFF181a20),
      ),
    );
  } else {
    return ThemeData(
      primarySwatch: Colors.blue,
      appBarTheme: AppBarTheme(
        backgroundColor: Colors.white,
        iconTheme: IconThemeData(color: Colors.black),
        systemOverlayStyle: SystemUiOverlayStyle(
          statusBarColor: Colors.white,
          statusBarIconBrightness: Brightness.dark,
          statusBarBrightness: Brightness.light,
        ),
      ),
      scaffoldBackgroundColor: Colors.white,
      bottomNavigationBarTheme: BottomNavigationBarThemeData(
        backgroundColor: Colors.white,
      ),
    );
  }
}
