import 'package:flutter/material.dart';
import 'package:minyun/main.dart';
import 'package:minyun/screens/TabBarSignInScreen.dart';
import 'package:minyun/utils/AppColors.dart';
import 'package:minyun/utils/AppWidget.dart';
import 'package:nb_utils/nb_utils.dart';


class SignInAndSignUpScreen extends StatefulWidget {
  @override
  SignInAndSignUpScreenState createState() => SignInAndSignUpScreenState();
}

class SignInAndSignUpScreenState extends State<SignInAndSignUpScreen> {
  @override
  void initState() {
    super.initState();
    init();
  }

  Future<void> init() async {
    //
  }

  @override
  void setState(fn) {
    if (mounted) super.setState(fn);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: mpAppBackGroundColor,
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          commonCacheImageWidget("assets/icons/bottom_navigation_icons/paipan_outlined.webp", 240, width: 240, fit: BoxFit.cover).cornerRadiusWithClipRRect(120),
          70.height,
          AppButton(
            child: Text('Sign in', style: boldTextStyle(color: mpAppButtonColor)),
            color: appStore.isDarkModeOn ? scaffoldDarkColor : white,
            width: context.width(),
            onTap: () {
              finish(context);
              TabBarSignInScreen(0).launch(context);
            },
          ).cornerRadiusWithClipRRect(24),
          16.height,
          AppButton(
            child: Text('Sign up', style: primaryTextStyle(color: Colors.white)),
            color: mpAppButtonColor,
            width: context.width(),
            onTap: () {
              finish(context);
              TabBarSignInScreen(1).launch(context);
            },
          ).cornerRadiusWithClipRRect(24),
          16.height,
          Text('Terms of Service', style: primaryTextStyle(color: mpAppButtonColor, size: 14))
        ],
      ).paddingSymmetric(horizontal: 16),
    );
  }
}
