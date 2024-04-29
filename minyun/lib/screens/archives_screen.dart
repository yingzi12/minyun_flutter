import 'dart:collection';

import 'package:bruno/bruno.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:lunar/calendar/Lunar.dart';
import 'package:lunar/calendar/Solar.dart';
import 'package:minyun/api/AnalyzeEightCharApi.dart';
import 'package:minyun/api/CalendarApi.dart';
import 'package:minyun/api/UserEightCharApi.dart';
import 'package:minyun/constant.dart';
import 'package:minyun/models/CalendarModel.dart';
import 'package:minyun/models/ResultListModel.dart';
import 'package:minyun/models/analyze_eight_char_modell.dart';
import 'package:minyun/screens/account_screen.dart';
import 'package:minyun/screens/dashboard_screen.dart';

import 'package:minyun/utils/AppColors.dart';
import 'package:minyun/utils/AppContents.dart';
import '../utils/AppCommon.dart';
import '../utils/images.dart';

/**
 *档案
 */
class ArchivesScreen extends StatefulWidget {
  ArchivesScreen({super.key});

  @override
  State<ArchivesScreen> createState() => _ArchivesScreenState();
}

class _ArchivesScreenState extends State<ArchivesScreen> {
  List<AnalyzeEightCharModel> stories = [];


  @override
  void initState() {
    super.initState();
    _refreshApiData("","","");
    // _refreshSdkData();
  }

  @override
  void dispose() {
    super.dispose();
  }

  Future<void> _refreshApiData(String timely,String start,String end) async {
    // timely=嫁娶&startDate=2024-02-03&endDate=2024-03-19
    Map<String, String> queryParams=new HashMap();
    // queryParams["timely"] = timely;
    // queryParams["startDate"] = start;
    // queryParams["endDate"] = end;
    ResultListModel<AnalyzeEightCharModel> resultModel = await AnalyzeEightCharApi.getList(queryParams);
    setState(() {

    });
  }

  @override
  Widget build(BuildContext context) {
    final height = MediaQuery.of(context).size.height;
    final width = MediaQuery.of(context).size.width;
    return Scaffold(
      appBar: AppBar(
        title: Text("档案"),
        titleTextStyle: boldTextStyle(fontSize: 24),
        elevation: 0,
        titleSpacing: 0,
        leading: Padding(
          padding: EdgeInsets.all(12.0),
          child: Image.asset(splash_screen_image, color: primaryColor),
        ),
      ),
      body: Stack(
        children: [
          Column(
            children: [
              Row(
                children: [
                  Padding(
                    padding: const EdgeInsets.only(left: 16),
                    child: Text("共 ${stories.length} 条", style: boldTextStyle(fontSize: 18,color: Colors.black45)),
                  ),
                  Spacer(),
                ],
              ),
              Expanded(
                child:
                ListView.builder(
                  itemCount: stories.length,
                  primary: false,
                  padding: EdgeInsets.symmetric(horizontal: 16),
                  shrinkWrap: true,
                  itemBuilder: (context, int index) {
                    AnalyzeEightCharModel analyze= stories[index];
                    int sex=analyze.sex!.toInt();
                    return Container(
                      margin: EdgeInsets.symmetric(vertical: 8),
                      padding: EdgeInsets.all(16),
                      decoration: BoxDecoration(
                          color: mode.theme ? darkPrimaryLightColor : Colors.grey.shade200, borderRadius: BorderRadius.circular(DEFAULT_RADIUS)),
                      child: Row(
                        children: [
                          Expanded(
                            // flex: 7,
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Row(
                                  children: [
                                    Text(analyze.name??"", style: TextStyle(fontSize:   20,color: Colors.black)),
                                     CircleBackgroundText(sex ==0 ? "男":"女",sex == '男'?Colors.yellow :Colors.red,30),
                                  ],
                                ),
                                Text( "${analyze.createTime ?? ""}", style: TextStyle(fontSize:   14,color: Colors.black45)),
                                Text(analyze.ztys!.toInt() ==5 ? "${analyze.year}-${analyze.month}-${analyze.day} ${analyze.hour}:00": "${analyze.ng} ${analyze.yg} ${analyze.rg} ${analyze.sg}", style: TextStyle(fontSize:   12,color: Colors.black45)),
                              ],
                            ),
                          ),
                        ],
                      ),
                    );
                  },
                ),

              ),
            ],
          ),
        ],
      ),
    );
  }

  PopupMenuItem<int> popupItem({required String title, required int value}) {
    return PopupMenuItem(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [Text(title, style: primaryTextStyle(fontSize: 14)), Divider()],
      ),
      value: value,
    );
  }

}
