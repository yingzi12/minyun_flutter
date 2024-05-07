import 'dart:collection';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:lunar/lunar.dart';
import 'package:minyun/api/AnalyzeEightCharInfoApi.dart';
import 'package:minyun/component/analyze/analyze_user_info_cell_componet.dart';
import 'package:minyun/models/ResultListModel.dart';

import 'package:minyun/models/SplayedFigureFindModel.dart';
import 'package:minyun/models/analyze_eight_char_info_model.dart';
import 'package:nb_utils/nb_utils.dart';

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
  TextEditingController _labelController = TextEditingController();
  TextEditingController _introController = TextEditingController();

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
    ResultListModel<AnalyzeEightCharInfoModel> resultModel = await AnalyzeEightCharInfoApi.getList(queryParams);
    setState(() {
      stories=resultModel.data??[];
      size=stories.length;
    });
  }
  Future<void> _refreshSaveData(Map<String, String> addMap) async {
    addMap["uuid"]=widget.search.uuid.toString();
    Map<String, dynamic> result= await AnalyzeEightCharInfoApi.addModel(addMap);
    if(result["code"].toString().toInt == 200){
      _labelController.clear();
      _introController.clear();
      _refreshSdkData();
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

    }
  }

  Future<void> _refreshEditData(Map<String, String> editMap) async {
    editMap["uuid"]=widget.search.uuid.toString();

    AnalyzeEightCharInfoApi.editModel(editMap);
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
          controller: _labelController,
          maxLines: null, // 允许无限行
          keyboardType: TextInputType.multiline, // 多行键盘
          inputFormatters: [LengthLimitingTextInputFormatter(100)],
          decoration: InputDecoration(
            labelText: '请输入标签，多个用英文;分割',
            border: OutlineInputBorder(),
          ),
        ),
        TextField(
          controller: _introController,
          maxLines: null, // 允许无限行
          keyboardType: TextInputType.multiline, // 多行键盘
          inputFormatters: [LengthLimitingTextInputFormatter(500)],
          decoration: InputDecoration(
            labelText: '请输入用户资料信息',
            border: OutlineInputBorder(),
          ),
        ),
        SizedBox(height: 16.0), // 添加一些间距
        ElevatedButton(
          onPressed: () {
            Map<String, String> addMap=new HashMap();
            addMap['label']=_labelController.text;
            addMap['intro']=_introController.text;
            _refreshSaveData(addMap);
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
