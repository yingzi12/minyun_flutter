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


  // Widget buildDialogCommon(BuildContext context){
  //   return AlertDialog(
  //     title: Text('点评'),
  //     content: SingleChildScrollView( // Wrap content with SingleChildScrollView
  //       child: Center(
  //         child: Column(
  //           children: [
  //             TextField(
  //               controller: _labelController,
  //               maxLines: null,
  //               keyboardType: TextInputType.multiline,
  //               inputFormatters: [LengthLimitingTextInputFormatter(100)],
  //               decoration: InputDecoration(
  //                 labelText: '请输入标签，多个用英文;分割',
  //                 border: OutlineInputBorder(),
  //               ),
  //             ),
  //             TextField(
  //               controller: _introController,
  //               maxLines: null,
  //               keyboardType: TextInputType.multiline,
  //               inputFormatters: [LengthLimitingTextInputFormatter(500)],
  //               decoration: InputDecoration(
  //                 labelText: '请输入点评信息',
  //                 border: OutlineInputBorder(),
  //               ),
  //             ),
  //             SizedBox(height: 16.0),
  //             buildShare(),
  //             if(_selectedShareValue.toInt()==1) buildUserId(context),
  //             if(_selectedShareValue.toInt()==1) buildPhone(context),
  //   Row(
  //     crossAxisAlignment: CrossAxisAlignment.center,
  //   children: [
  //             ElevatedButton(
  //               onPressed: () {
  //                 Map<String, String> addMap=new HashMap();
  //                 addMap['label']=_labelController.text;
  //                 addMap['intro']=_introController.text;
  //                 addMap['isSms']=_phoneNumberController.text;
  //                 addMap['isMessage']=_userIdController.text;
  //
  //                 _refreshSaveData(addMap);
  //                 Navigator.of(context).pop();
  //               },
  //               child: Text('提交'),
  //             ),
  //     ElevatedButton(
  //       onPressed: () {
  //         Navigator.of(context).pop();
  //       },
  //       child: Text('取消'),
  //     ),
  //   ]
  //   )
  //           ],
  //         ),
  //       ),
  //     ),
  //   );
  // }
  //
  // Widget buildShare(){
  //   return Row(
  //     children: [
  //       SizedBox(
  //         width: 90.0,
  //         child:
  //         Padding(
  //           padding: const EdgeInsets.only(left: 16),
  //           child: Text("是否发送给用户："),
  //         ),
  //       ),
  //       Expanded(
  //         child: Row(
  //           mainAxisAlignment: MainAxisAlignment.start,
  //           children: [
  //             Expanded(
  //               child: Radio(
  //                 value: 1,
  //                 groupValue: _selectedShareValue,
  //                 onChanged: (value) {
  //                   print('Radio 1 selected'); // Add print statement for debugging
  //
  //                   setState(() {
  //                     _selectedShareValue = value as int;
  //                   });
  //                 },
  //               ),
  //             ),
  //             Text('是'),
  //             Expanded(
  //               child: Radio(
  //                 value: 2,
  //                 groupValue: _selectedShareValue, // Use a different groupValue
  //                 onChanged: (value) {
  //                   print('Radio 2 selected'); // Add print statement for debugging
  //
  //                   setState(() {
  //                     _selectedShareValue = value as int;
  //                   });
  //                 },
  //               ),
  //             ),
  //             Text('否'),
  //           ],
  //         ),
  //       ),
  //     ],
  //   );
  // }

}
