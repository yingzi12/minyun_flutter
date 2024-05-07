import 'dart:collection';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:lunar/lunar.dart';
import 'package:minyun/api/AnalyzeEightCharAnalyzeApi.dart';
import 'package:minyun/component/analyze/analyze_user_analyze_cell_componet.dart';
import 'package:minyun/models/ResultListModel.dart';

import 'package:minyun/models/SplayedFigureFindModel.dart';
import 'package:minyun/models/analyze_eight_char_analyze_model.dart';
import 'package:minyun/utils/AppWidget.dart';
import 'splayed_add_common_screen.dart';


/**
 * 大师点评
 */
class SplayedFigureDetailCommonScreen extends StatefulWidget {
  final SplayedFigureFindModel search;
  SplayedFigureDetailCommonScreen({required this.search});

  @override
  State<SplayedFigureDetailCommonScreen> createState() => _SplayedFigureDetailCommonScreenState();
}

class _SplayedFigureDetailCommonScreenState extends State<SplayedFigureDetailCommonScreen>  with TickerProviderStateMixin {

  List<AnalyzeEightCharAnalyzeModel> stories = [];
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
    queryParams["uuid"]=widget.search.uuid.toString();
    ResultListModel<AnalyzeEightCharAnalyzeModel> resultModel = await AnalyzeEightCharAnalyzeApi.getList(queryParams);
    setState(() {
      stories=resultModel.data??[];
      size=stories.length;
    });
  }


  Future<void> _refreshEditData(Map<String, String> editMap) async {
    editMap["uuid"]=widget.search.uuid.toString();

    AnalyzeEightCharAnalyzeApi.editModel(editMap);
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
    return  Column(
      children: [
        titleAddRowItem(isSeeAll: true, title: '已点评（${stories.length}）',onTap: ()=>{
          showDialog(
              context: context,
              builder: (BuildContext context) {
                return SplayedAddCommonScreen( uuid: widget.search.uuid.toString(),);
              }
          ).then((value) {
            if (value == true) {
              Get.dialog(
                AlertDialog(
                  title: Text("信息"),
                  content: Text("添加成功"),
                  actions: <Widget>[
                    TextButton(
                      child: Text("确定"),
                      onPressed: () {
                        Get.back(); // 关闭对话框
                      },
                    ),
                  ],
                ),
                barrierDismissible: false, // 点击对话框外部不关闭对话框
              );
              // 执行页面刷新操作
              _refreshSdkData();
            }
          })
        }
        ),
        size==0 ? Center(
          child: Text("暂无点评"),
        ):Expanded(
          child: ListView.builder(
            itemCount: stories.length,
            itemBuilder: (context, index) {
              return AnalyzeUserAnalyzeCellComponet(stories[index]);
            },
          ),
        ),
      ],
    );
  }

}
