import 'package:flutter/material.dart';
import 'package:minyun/screens/TabBarSignInScreen.dart';
import 'package:minyun/utils/AppColors.dart';
import 'package:minyun/utils/AppCommon.dart';
import 'package:minyun/utils/AppWidget.dart';
import 'package:nb_utils/nb_utils.dart';


class UserForgotPasswordScreen extends StatefulWidget {
  @override
  UserForgotPasswordScreenState createState() => UserForgotPasswordScreenState();
}

class UserForgotPasswordScreenState extends State<UserForgotPasswordScreen> {
  TextEditingController contEmailAddress = TextEditingController();
  TextEditingController contPassword = TextEditingController();
  TextEditingController contConfirmPassword = TextEditingController();

  FocusNode focusNodePassword = FocusNode();
  FocusNode focusNodeConfirmPassword = FocusNode();

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
      appBar: AppBar(
        elevation: 0.0,
        backgroundColor: mpAppBackGroundColor,
        leading: IconButton(
          icon: Icon(Icons.arrow_back_outlined, color: white),
          onPressed: () {
            finish(context);
          },
        ),
        centerTitle: true,
        title: Text('Forgot password', style: appMainBoldTextStyle(color: white)),
      ),
      body: SingleChildScrollView(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            AppTextField(
              controller: contEmailAddress,
              textStyle: appMainPrimaryTextStyle(color: white),
              nextFocus: focusNodePassword,
              textFieldType: TextFieldType.EMAIL,
              cursorColor: Colors.white,
              decoration: buildInputDecoration('Your Email Address'),
            ),
            20.height,
            AppTextField(
              controller: contPassword,
              textStyle: appMainPrimaryTextStyle(color: white),
              focus: focusNodePassword,
              nextFocus: focusNodeConfirmPassword,
              textFieldType: TextFieldType.PASSWORD,
              cursorColor: Colors.white,
              suffixIconColor: mpAppButtonColor,
              decoration: buildInputDecoration('Your New password'),
            ),
            20.height,
            AppTextField(
              controller: contConfirmPassword,
              textStyle: appMainPrimaryTextStyle(color: white),
              focus: focusNodeConfirmPassword,
              textFieldType: TextFieldType.PASSWORD,
              cursorColor: Colors.white,
              suffixIconColor: mpAppButtonColor,
              decoration: buildInputDecoration('Confirm Password'),
            ),
            50.height,
            AppButton(
              child: Text('Reset My Password', style: appMainPrimaryTextStyle(color: Colors.white)),
              color: mpAppButtonColor,
              width: context.width(),
              onTap: () {
                TabBarSignInScreen(0).launch(context);
              },
            ).cornerRadiusWithClipRRect(24),
          ],
        ).paddingOnly(top: 100, left: 16, right: 16, bottom: 16),
      ),
    );
  }
}
