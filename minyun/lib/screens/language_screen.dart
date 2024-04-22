import 'package:flutter/material.dart';
import 'package:minyun/screens/account_screen.dart';
import 'package:minyun/utils/AppColors.dart';

import '../component/text_form_field_label_text.dart';
import '../utils/AppCommon.dart';
import '../utils/lists.dart';

class LanguageScreen extends StatefulWidget {
  const LanguageScreen({Key? key}) : super(key: key);

  @override
  State<LanguageScreen> createState() => _LanguageScreenState();
}

class _LanguageScreenState extends State<LanguageScreen> {
  int selectedIndex = -1;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        elevation: 0,
        title: Text("Language", style: boldTextStyle(fontSize: 24)),
        iconTheme: IconThemeData(color: mode.theme ? Colors.white : Colors.black),
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            Padding(
              padding: EdgeInsets.only(top: 16, bottom: 8, right: 16, left: 16),
              child: TextFormFieldLabelText(text: "Suggested", style: boldTextStyle(fontSize: 18)),
            ),
            ListView.builder(
              itemCount: suggestedList.length,
              shrinkWrap: true,
              primary: false,
              itemBuilder: (context, int index) {
                return InkWell(
                  onTap: () {
                    selectedIndex = index;
                    setState(() {});
                  },
                  child: Container(
                    padding: EdgeInsets.symmetric(horizontal: 16, vertical: 8),
                    margin: EdgeInsets.symmetric(vertical: 4),
                    child: Row(
                      children: [
                        Expanded(
                          child: Text(suggestedList[index], style: primaryTextStyle()),
                        ),
                        Spacer(),
                        Icon(Icons.check,
                            size: 20,
                            color: mode.theme
                                ? selectedIndex == index
                                    ? primaryColor
                                    : appScaffoldColor
                                : selectedIndex == index
                                    ? primaryColor
                                    : Colors.white),
                      ],
                    ),
                  ),
                );
              },
            ),
            Divider(height: 0, indent: 16, endIndent: 16),
            Padding(
              padding: EdgeInsets.only(top: 16, bottom: 8, left: 16, right: 16),
              child: TextFormFieldLabelText(text: "Language", style: boldTextStyle(fontSize: 18)),
            ),
            ListView.builder(
              itemCount: languageList.length,
              shrinkWrap: true,
              primary: false,
              padding: EdgeInsets.only(bottom: 24),
              itemBuilder: (context, int index) {
                return InkWell(
                  onTap: () {
                    selectedIndex = suggestedList.length + index;
                    setState(() {});
                  },
                  child: Container(
                    padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
                    margin: EdgeInsets.symmetric(vertical: 4),
                    child: Row(
                      children: [
                        Expanded(
                          child: Text(languageList[index], style: primaryTextStyle()),
                        ),
                        Spacer(),
                        Icon(Icons.check,
                            size: 20,
                            color: mode.theme
                                ? selectedIndex == (index + suggestedList.length)
                                    ? primaryColor
                                    : appScaffoldColor
                                : selectedIndex == (index + suggestedList.length)
                                    ? primaryColor
                                    : Colors.white),
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
