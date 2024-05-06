import 'dart:collection';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:lunar/lunar.dart';
import 'package:minyun/api/AnalyzeEightCharAnalyzeApi.dart';
import 'package:minyun/component/analyze/analyze_user_analyze_cell_componet.dart';
import 'package:minyun/models/ResultListModel.dart';

import 'package:minyun/models/SplayedFigureFindModel.dart';
import 'package:minyun/models/analyze_eight_char_analyze_model.dart';
import 'package:tdesign_flutter/tdesign_flutter.dart';


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
  int _selectedShareValue=1;
  TextEditingController _userIdController = TextEditingController();
  TextEditingController _phoneNumberController = TextEditingController();
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
    ResultListModel<AnalyzeEightCharAnalyzeModel> resultModel = await AnalyzeEightCharAnalyzeApi.getList(queryParams);
    setState(() {
      stories=resultModel.data??[];
    });
  }

  Future<void> _refreshSaveData(Map<String, String> addMap) async {
    addMap["uuid"]=widget.search.uuid.toString();
    AnalyzeEightCharAnalyzeApi.addModel(addMap);
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
    return  ListView(
      children: [
        Text("大师点评"),
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
            labelText: '请输入点评信息',
            border: OutlineInputBorder(),
          ),
        ),
        SizedBox(height: 16.0), // 添加一些间距
        buildShare(),
        if(_selectedShareValue.toInt()==1) buildUserId(context),
        if(_selectedShareValue.toInt()==1) buildPhone(context),
        ElevatedButton(
          onPressed: () {
            // 这里处理提交逻辑
            Map<String, String> addMap=new HashMap();
            addMap['label']=_labelController.text;
            addMap['intro']=_introController.text;
            addMap['isSms']=_phoneNumberController.text;
            addMap['isMessage']=_userIdController.text;

            _refreshSaveData(addMap);

          },
          child: Text('提交'),
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

  Widget buildUserId(BuildContext context) {
    return Column(
      children: [
        TDInput(
          leftLabel: '信息通知',
          required: true,
          controller: _userIdController,
          backgroundColor: Colors.white,
          hintText: '请输入用户账号',
          onChanged: (text) {
            print("请 onChanged 输入用户账号${text}");

            setState(() {});
          },
          onClearTap: () {
            _userIdController.clear();
            setState(() {});
          },
        ),
        const SizedBox(
          height: 16,
        )
      ],
    );
  }


  Widget buildPhone(BuildContext context) {
    return Column(
      children: [
        TDInput(
          leftLabel: '短信通知',
          required: true,
          controller: _phoneNumberController,
          backgroundColor: Colors.white,
          hintText: '请输入用户电话号码',
          onChanged: (text) {
            print("请onChanged输入用户电话号码${text}");
            setState(() {

            });
          },
          onClearTap: () {
            print("请输onClearTap 入用户电话号码");

            _phoneNumberController.clear();
            setState(() {});
          },
        ),
        const SizedBox(
          height: 16,
        )
      ],
    );
  }


  Widget buildShare(){
    return Row(
      children: [
        SizedBox(
          width: 90.0,
          child:
          Padding(
            padding: const EdgeInsets.only(left: 16),
            child: Text("是否发送给用户：",
              // style: appBoldTextStyle(fontSize: 18)
            ),
          ),
        ),
        Expanded(
            child:
            Row(
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                Expanded(
                  child: RadioListTile(
                    value: 1,
                    groupValue: _selectedShareValue,
                    contentPadding: EdgeInsets.zero,
                    title: Text('是'),
                    onChanged: (value) {
                      setState(() {
                        _selectedShareValue = value as int;
                      });
                    },
                  ),
                ),
                Expanded(
                  child: RadioListTile(
                    value: 2,
                    groupValue: _selectedShareValue,
                    contentPadding: EdgeInsets.zero,
                    title: Text('否'),
                    onChanged: (value) {
                      setState(() {
                        _selectedShareValue = value as int;
                      });
                    },
                  ),
                ),
              ],
            )
          // ),
        ),
      ],
    );
  }

}
