import 'dart:collection';

import 'package:flutter/material.dart';
import 'package:minyun/api/AnalyzeEightCharAnalyzeApi.dart';
import 'package:minyun/component/analyze/analyze_user_analyze_cell_componet.dart';
import 'package:minyun/models/ResultListModel.dart';

import 'package:minyun/models/analyze_eight_char_analyze_model.dart';
import 'package:minyun/screens/user/account_screen.dart';
import 'package:minyun/utils/AppCommon.dart';
import 'package:minyun/utils/AppWidget.dart';
import 'package:minyun/utils/images.dart';


/**
 * 大师点评
 */
class UserSplayedFigureListScreen extends StatefulWidget {
  final String sendUserId;
  UserSplayedFigureListScreen({required this.sendUserId});

  @override
  State<UserSplayedFigureListScreen> createState() => _UserSplayedFigureListScreenState();
}

class _UserSplayedFigureListScreenState extends State<UserSplayedFigureListScreen>  with TickerProviderStateMixin {
  List<AnalyzeEightCharAnalyzeModel> stories = [];
  int size = 0;

  @override
  void initState() {
    super.initState();
    _refreshSdkData();
  }

  Future<void> _refreshSdkData() async {
    Map<String, String> queryParams=new HashMap();
    queryParams["sendUserId"]=widget.sendUserId;
    ResultListModel<AnalyzeEightCharAnalyzeModel> resultModel = await AnalyzeEightCharAnalyzeApi.getList(queryParams);
    setState(() {
      stories=resultModel.data??[];
      size=stories.length;
    });
  }



  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          "个人信息",
          style: appMainBoldTextStyle(fontSize: 24),
        ),
        elevation: 0,
        iconTheme: IconThemeData(color: mode.theme ? Colors.white : Colors.black),
        // actions: [
        //   IconButton(
        //     onPressed: () {},
        //     splashRadius: 22,
        //     icon: Image.asset(rename_image, height: 26, color: mode.theme ? Colors.white : Colors.black),
        //   ),
        // ],
      ),
      body: SingleChildScrollView(
        padding: EdgeInsets.only(top: 16, right: 16, left: 16, bottom: 32),
        child : RefreshIndicator(
          onRefresh: _refreshSdkData,
          child: Column(
            children: [
              titleRowItem(
                isSeeAll: false,
                title: '已点评（${stories.length}）',
              ),
              size == 0 ? Center(
                child: Text("暂无点评",style: appMainBoldTextStyle(),),
              ) : Expanded(
                child: ListView.builder(
                  itemCount: stories.length,
                  itemBuilder: (context, index) {
                    return AnalyzeUserAnalyzeCellComponet(stories[index]);
                  },
                ),
              ),
            ],
          ),
        )
      ),
    );
  }
}

