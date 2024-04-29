import 'package:flutter/material.dart';
import 'package:minyun/component/AppButton.dart';
import 'package:minyun/screens/TabBarSignInScreen.dart';
import 'package:minyun/screens/user/account_screen.dart';
import 'package:minyun/screens/sign_up_screen.dart';
import 'package:minyun/utils/AppColors.dart';
import 'package:minyun/utils/AppCommon.dart';
import 'package:minyun/utils/AppContents.dart';
import 'package:minyun/utils/lists.dart';

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
                text: "使用密码登录",
                onTap: () {
                  Navigator.push(context, MaterialPageRoute(builder: (context) => TabBarSignInScreen(0)));
                },
              ),
            ),
            SizedBox(height: 24),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text(
                  "还没有账号?  ",
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
                    "注册",
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
