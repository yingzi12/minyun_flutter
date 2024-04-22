import 'package:flutter/material.dart';
import 'package:minyun/screens/account_screen.dart';

import '../screens/tab_bar_watermark_icon_screen.dart';
import '../screens/tab_bar_watermark_text_screen.dart';
import 'package:minyun/utils/AppColors.dart';
import '../utils/AppCommon.dart';
import 'package:minyun/utils/AppContents.dart';

Future<dynamic> AddWatermarkBottomSheetComponent(BuildContext context) {
  return showModalBottomSheet(
      backgroundColor: Colors.transparent,
      context: context,
      isScrollControlled: true,
      builder: (context) {
        return Padding(
          padding: EdgeInsets.only(bottom: MediaQuery.of(context).viewInsets.bottom),
          child: StatefulBuilder(
            builder: (BuildContext context, StateSetter setState) {
              return Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  Container(
                    height: MediaQuery.of(context).size.height * 0.5,
                    decoration: BoxDecoration(
                      color: mode.theme ? darkPrimaryLightColor : Colors.white,
                      borderRadius: BorderRadius.vertical(
                        top: Radius.circular(DEFAULT_RADIUS),
                      ),
                    ),
                    child: Column(
                      children: [
                        SizedBox(height: 12),
                        Container(
                          height: 2,
                          width: 40,
                          decoration: BoxDecoration(color: Colors.grey.shade400, borderRadius: BorderRadius.circular(DEFAULT_RADIUS)),
                        ),
                        SizedBox(height: 16),
                        Text("Add Watermark", style: boldTextStyle(fontSize: 24)),
                        SizedBox(height: 16),
                        Divider(),
                        Expanded(
                          child: DefaultTabController(
                            length: 2,
                            child: Padding(
                              padding: EdgeInsets.symmetric(vertical: 8, horizontal: 16),
                              child: Scaffold(
                                backgroundColor: mode.theme ? darkPrimaryLightColor : Colors.white,
                                appBar: TabBar(
                                  labelColor: primaryColor,
                                  unselectedLabelColor: Colors.grey,
                                  indicatorColor: primaryColor,
                                  labelStyle: primaryTextStyle(),
                                  tabs: [
                                    Tab(
                                      text: "Watermark Text",
                                    ),
                                    Tab(
                                      text: "Watermark Icon",
                                    ),
                                  ],
                                ),
                                body: TabBarView(
                                  children: [
                                    TabBarWatermarkTextScreen(),
                                    TabBarWatermarkIconScreen(),
                                  ],
                                ),
                              ),
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
                ],
              );
            },
          ),
        );
      });
}
