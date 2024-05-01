import 'package:flutter/material.dart';
import 'package:minyun/screens/TabBarSignInScreen.dart';
import 'package:minyun/screens/user/account_screen.dart';

import '../screens/sign_in_screen.dart';
import 'package:minyun/utils/AppColors.dart';
import '../utils/AppCommon.dart';
import 'package:minyun/utils/AppContents.dart';
import 'AppButton.dart';

Future<dynamic> LogoutBottomSheet(BuildContext context) {
  return showModalBottomSheet(
    backgroundColor: mode.theme ? darkPrimaryLightColor : Colors.white,
    isScrollControlled: true,
    shape: RoundedRectangleBorder(
      borderRadius: BorderRadius.vertical(top: Radius.circular(DEFAULT_RADIUS)),
    ),
    elevation: 0,
    context: context,
    builder: (context) {
      return SingleChildScrollView(
        padding: EdgeInsets.only(left: 16, right: 16, top: 12, bottom: 16),
        child: Container(
          padding: EdgeInsets.only(bottom: MediaQuery.of(context).viewInsets.bottom),
          child: Column(
            children: [
              Container(
                height: 2,
                width: 40,
                decoration: BoxDecoration(color: Colors.grey, borderRadius: BorderRadius.circular(DEFAULT_RADIUS)),
              ),
              SizedBox(height: 16),
              Text("Logout", style: appBoldTextStyle(fontSize: 24, color: Colors.redAccent)),
              SizedBox(height: 16),
              Divider(),
              SizedBox(height: 16),
              Text(
                "Are you sure you want to log out?",
                style: appPrimaryTextStyle(),
              ),
              SizedBox(height: 24),
              Row(
                children: [
                  Expanded(
                    child: AppButton(
                      text: "Cancel",
                      color: mode.theme ? darkPrimaryColor : primaryLightColor,
                      textColor: mode.theme ? Colors.white : primaryColor,
                      onTap: () {
                        Navigator.pop(context);
                      },
                    ),
                  ),
                  SizedBox(width: 16),
                  Expanded(
                    child: AppButton(
                      text: "Yes, Logout",
                      onTap: () {
                        Navigator.push(context, MaterialPageRoute(builder: (context) => TabBarSignInScreen(0)));
                        ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text("Logout")));
                      },
                    ),
                  ),
                ],
              ),
            ],
          ),
        ),
      );
    },
  );
}
