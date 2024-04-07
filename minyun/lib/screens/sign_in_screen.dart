import 'package:flutter/material.dart';
import 'package:minyun/component/AppButton.dart';
import 'package:minyun/screens/account_screen.dart';
import 'package:minyun/screens/sign_up_screen.dart';
import 'package:minyun/screens/signin_with_password_screen.dart';
import 'package:minyun/utils/color.dart';
import 'package:minyun/utils/common.dart';
import 'package:minyun/utils/lists.dart';

import '../utils/constant.dart';
import '../utils/images.dart';
import 'bottom_navigation_bar_screen.dart';

class SignInScreen extends StatelessWidget {
  const SignInScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final height = MediaQuery.of(context).size.height;
    final width = MediaQuery.of(context).size.width;
    return Scaffold(
      body: SingleChildScrollView(
        padding: EdgeInsets.only(top: 32, bottom: 16),
        child: Column(
          children: [
            Image.asset(sign_in_screen_image, height: height * 0.4, width: width * 1),
            SizedBox(height: 16),
            Text(
              "Let's you in",
              style: boldTextStyle(fontSize: 30),
            ),
            SizedBox(height: 32),
            Padding(
              padding: EdgeInsets.symmetric(horizontal: 16),
              child: ListView.builder(
                  padding: EdgeInsets.zero,
                  itemCount: signInOptions.length,
                  shrinkWrap: true,
                  primary: false,
                  itemBuilder: (context, int index) {
                    return Column(
                      children: [
                        Padding(
                          padding: EdgeInsets.symmetric(vertical: 8),
                          child: GestureDetector(
                            onTap: () {
                              Navigator.push(context, MaterialPageRoute(builder: (context) => BottomNavigationBarScreen(itemIndex: 0)));
                            },
                            child: Container(
                              padding: EdgeInsets.all(8),
                              decoration: BoxDecoration(
                                  borderRadius: BorderRadius.circular(DEFAULT_RADIUS),
                                  border: Border.all(color: mode.theme ? Colors.white30 : Colors.grey.shade300)),
                              child: Row(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                                  Image.asset(
                                    signInOptionImage[index],
                                    width: 30,
                                    height: 30,
                                    fit: BoxFit.cover,
                                  ),
                                  SizedBox(width: 8),
                                  Text(signInOptions[index], style: primaryTextStyle()),
                                ],
                              ),
                            ),
                          ),
                        ),
                      ],
                    );
                  }),
            ),
            SizedBox(height: 16),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16),
              child: Row(
                children: [
                  Expanded(child: Divider()),
                  Padding(
                    padding: EdgeInsets.symmetric(horizontal: 8),
                    child: Text("or", style: secondaryTextStyle()),
                  ),
                  Expanded(
                    child: Divider(),
                  ),
                ],
              ),
            ),
            SizedBox(height: 24),
            Padding(
              padding: EdgeInsets.symmetric(horizontal: 16),
              child: AppButton(
                text: "Sign in with password",
                onTap: () {
                  Navigator.push(context, MaterialPageRoute(builder: (context) => SignInWithPasswordScreen()));
                },
              ),
            ),
            SizedBox(height: 24),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text(
                  "Don't have an account?  ",
                  style: secondaryTextStyle(color: mode.theme ? darkTextSecondaryColor : Colors.black),
                ),
                GestureDetector(
                  onTap: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => SignUpScreen()),
                    );
                  },
                  child: Text(
                    "Sign up",
                    style: secondaryTextStyle(color: primaryColor),
                  ),
                )
              ],
            )
          ],
        ),
      ),
    );
  }
}
