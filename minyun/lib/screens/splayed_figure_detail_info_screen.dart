import 'dart:collection';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:lunar/lunar.dart';
import 'package:minyun/api/AnalyzeEightCharInfoApi.dart';
import 'package:minyun/component/analyze/analyze_user_info_cell_componet.dart';
import 'package:minyun/models/ResultListModel.dart';

import 'package:minyun/models/SplayedFigureFindModel.dart';
import 'package:minyun/models/analyze_eight_char_info_model.dart';

/**
 * 大师点评
 */
class SplayedFigureDetailInfoScreen extends StatefulWidget {
  final SplayedFigureFindModel search;
  SplayedFigureDetailInfoScreen({required this.search});

  @override
  State<SplayedFigureDetailInfoScreen> createState() => _SplayedFigureDetailInfoScreenState();
}

class _SplayedFigureDetailInfoScreenState extends State<SplayedFigureDetailInfoScreen>  with TickerProviderStateMixin {

  List<AnalyzeEightCharInfoModel> stories = [];
  int size = 0;



  @override
  void initState() {
    super.initState();
    _refreshSdkData();
  }

  @override
  void dispose() {
    super.dispose();
  }
  Future<void> _refreshSdkData() async {
    Map<String, String> queryParams=new HashMap();
    queryParams["aecid"]=widget.search.aecid.toString();
    ResultListModel<AnalyzeEightCharInfoModel> resultModel = await AnalyzeEightCharInfoApi.getList(queryParams);
    setState(() {
      stories=resultModel.data??[];
    });
  }


  //获取指定月的天数
  int getDaysInMonth(int year, int month) {
    var a = Solar.fromYmd(year, month, 1);
    var b = a.nextMonth(1);
    // 返回差异的天数
    return b.subtract(a);
  }
  @override
  Widget build(BuildContext context) {
    return  ListView(
      children: [
        Text("用户资料"),
        TextField(
          maxLines: null, // 允许无限行
          keyboardType: TextInputType.multiline, // 多行键盘
          decoration: InputDecoration(
            labelText: '请输入用户资料信息',
            border: OutlineInputBorder(),
          ),
        ),
        SizedBox(height: 16.0), // 添加一些间距
        ElevatedButton(
          onPressed: () {
            // 这里处理提交逻辑
            // 例如，你可以获取TextField的值并处理它
          },
          child: Text('提交'),
        ),
        size==0 ? Center(
          child: Text("暂无个人资料"),
        ):Expanded(
          child: ListView.builder(
            itemCount: stories.length,
            itemBuilder: (context, index) {
              return AnalyzeUserInfoCellComponet(stories[index]);
            },
          ),
        ),
      ],
    );
  }
}
