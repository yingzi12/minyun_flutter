import 'package:flutter/material.dart';
import 'package:minyun/screens/user/account_screen.dart';
import 'package:minyun/utils/AppColors.dart';
import 'package:minyun/utils/AppCommon.dart';

import '../utils/images.dart';
import '../utils/lists.dart';

class AboutScreen extends StatelessWidget {
  const AboutScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final width = MediaQuery.of(context).size.width;
    final height = MediaQuery.of(context).size.height;

    return Scaffold(
      appBar: AppBar(
        title: Text(
          "关于命运",
          style: appMainBoldTextStyle(fontSize: 24),
        ),
        elevation: 0,
        iconTheme: IconThemeData(color: mode.theme ? Colors.white : Colors.black),
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            SizedBox(height: 32),
            Align(
              alignment: Alignment.center,
              child: Image.asset(
                splash_screen_image,
                color: primaryColor,
                height: height * 0.2,
              ),
            ),
            SizedBox(height: 32),
            Text("命运 v1.0.1", style: appMainBoldTextStyle(fontSize: 18)),
            SizedBox(height: 24),
            Divider(indent: 16, endIndent: 16),
            ListView.builder(
              itemCount: aboutScreenList.length,
              shrinkWrap: true,
              primary: false,
              padding: EdgeInsets.only(bottom: 16),
              itemBuilder: (context, int index) {
                return InkWell(
                  onTap: () {
                    //
                  },
                  child: Container(
                    padding: EdgeInsets.symmetric(vertical: 8),
                    margin: EdgeInsets.symmetric(vertical: 8, horizontal: 16),
                    // color: Colors.red,
                    child: Row(
                      children: [
                        Expanded(child: Text(aboutScreenList[index], style: appMainPrimaryTextStyle())),
                        Icon(
                          Icons.arrow_forward_ios,
                          color: mode.theme ? Colors.white : Colors.black54,
                          size: 20,
                        ),
                      ],
                    ),
                  ),
                );
              },
            ),
          ],
        ),
      ),
    );
  }
}
