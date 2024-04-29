import 'package:flutter/material.dart';
import 'package:minyun/screens/user/account_screen.dart';
import 'package:minyun/screens/sign_up_screen.dart';
import 'package:minyun/utils/AppContents.dart';

import '../component/AppButton.dart';
import '../component/text_form_field_label_text.dart';
import 'package:minyun/utils/AppColors.dart';
import '../utils/AppCommon.dart';
import '../utils/images.dart';
import 'bottom_navigation_bar_screen.dart';

class CreateAccountScreen extends StatefulWidget {
  CreateAccountScreen({Key? key}) : super(key: key);

  @override
  State<CreateAccountScreen> createState() => _CreateAccountScreenState();
}

class _CreateAccountScreenState extends State<CreateAccountScreen> {
  bool obText = true;
  bool confirmObText = true;
  bool isChecked = false;
  late FocusNode f1;
  late FocusNode f2;
  late FocusNode f3;
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    f1 = FocusNode();
    f2 = FocusNode();
    f3 = FocusNode();
  }

  @override
  void dispose() {
    // TODO: implement dispose
    super.dispose();
    f1.dispose();
    f2.dispose();
    f3.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final width = MediaQuery.of(context).size.width;
    return Scaffold(
      appBar: AppBar(
        elevation: 0,
        centerTitle: true,
        iconTheme: IconThemeData(color: mode.theme ? Colors.white : Colors.black),
        title: Container(
          height: 14,
          width: width * 0.5,
          decoration: BoxDecoration(color: mode.theme ? darkPrimaryColor : Colors.grey.shade300, borderRadius: BorderRadius.circular(DEFAULT_RADIUS)),
          child: Row(
            children: [
              Container(
                width: width * progress,
                height: 16,
                decoration: BoxDecoration(color: primaryColor, borderRadius: BorderRadius.circular(DEFAULT_RADIUS)),
              ),
            ],
          ),
        ),
      ),
      body: Stack(
        fit: StackFit.expand,
        children: [
          SingleChildScrollView(
            padding: EdgeInsets.only(left: 16, right: 16, top: 16, bottom: 80),
            child: Column(
              children: [
                Row(
                  children: [
                    Text("Create an account", style: boldTextStyle(fontSize: 24)),
                    Image.asset(create_account_screen_lock_image, height: 30, width: 30),
                  ],
                ),
                SizedBox(height: 16),
                Text(
                  "Enter your email & password, if you forgot it, then you have to do forgot password.",
                  style: secondaryTextStyle(color: mode.theme ? darkTextSecondaryColor : Colors.black),
                ),
                SizedBox(height: 24),
                Column(
                  children: [
                    TextFormFieldLabelText(text: "Email"),
                    TextFormField(
                      focusNode: f1,
                      decoration: inputDecoration(hintText: "Email"),
                      onFieldSubmitted: (value) {
                        progress = 0.34;
                        f1.unfocus();
                        FocusScope.of(context).requestFocus(f2);
                        setState(() {});
                      },
                    ),
                    SizedBox(height: 16),
                    TextFormFieldLabelText(text: "Password"),
                    TextFormField(
                      obscureText: obText,
                      focusNode: f2,
                      decoration: inputDecoration(
                        hintText: "Password",
                        suffixIcon: GestureDetector(
                            onTap: () {
                              obText = !obText;
                              setState(() {});
                            },
                            child: Icon(obText ? Icons.visibility_off : Icons.visibility, color: primaryColor)),
                      ),
                      onFieldSubmitted: (value) {
                        progress = 0.45;
                        f2.unfocus();
                        FocusScope.of(context).requestFocus(f3);
                        setState(() {});
                      },
                    ),
                    SizedBox(height: 16),
                    TextFormFieldLabelText(text: "Confirm Password"),
                    TextFormField(
                      obscureText: confirmObText,
                      focusNode: f3,
                      decoration: inputDecoration(
                        hintText: "Confirm Password",
                        suffixIcon: GestureDetector(
                            onTap: () {
                              confirmObText = !confirmObText;
                              setState(() {});
                            },
                            child: Icon(confirmObText ? Icons.visibility_off : Icons.visibility, color: primaryColor)),
                      ),
                      onFieldSubmitted: (value) {
                        progress = 0.5;
                        f3.unfocus();
                        setState(() {});
                      },
                    ),
                  ],
                ),
                SizedBox(height: 16),
                Row(
                  children: [
                    SizedBox(
                      height: 16,
                      width: 16,
                      child: Theme(
                        data: ThemeData(unselectedWidgetColor: Colors.red),
                        child: Checkbox(
                          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(DEFAULT_RADIUS)),
                          activeColor: primaryColor,
                          fillColor: MaterialStateProperty.all(
                            primaryColor,
                          ),
                          side: BorderSide(color: primaryColor),
                          value: isChecked,
                          onChanged: (bool? value) {
                            setState(
                              () {
                                isChecked = value!;
                              },
                            );
                          },
                        ),
                      ),
                    ),
                    SizedBox(width: 16),
                    Text("Remember me", style: boldTextStyle())
                  ],
                ),
              ],
            ),
          ),
          Positioned(
            bottom: 16,
            left: 16,
            right: 16,
            child: AppButton(
              text: "Finish",
              onTap: () {
                showDialog(
                  context: context,
                  builder: (context) {
                    return AlertDialog(
                      backgroundColor: mode.theme ? darkPrimaryLightColor : Colors.white,
                      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(DEFAULT_RADIUS)),
                      content: Column(
                        mainAxisSize: MainAxisSize.min,
                        children: [
                          SizedBox(height: 16),
                          CircleAvatar(
                            backgroundColor: primaryColor,
                            minRadius: 30,
                            maxRadius: 55,
                            child: Padding(
                              padding: EdgeInsets.all(16.0),
                              child: Image.asset(create_account_screen_alert_dialog_image, color: Colors.white, fit: BoxFit.contain),
                            ),
                          ),
                          SizedBox(height: 24),
                          Text("Sign Up Successful!", style: boldTextStyle(color: primaryColor, fontSize: 20)),
                          SizedBox(height: 16),
                          Text("Your account has been created.", style: secondaryTextStyle(), textAlign: TextAlign.center),
                          SizedBox(height: 24),
                          AppButton(
                            text: "Go to Home",
                            onTap: () {
                              Navigator.push(context, MaterialPageRoute(builder: (context) => BottomNavigationBarScreen(itemIndex: 0)));
                            },
                          )
                        ],
                      ),
                    );
                  },
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}
