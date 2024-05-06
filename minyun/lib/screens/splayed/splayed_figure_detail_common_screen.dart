import 'dart:collection';
import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:lunar/lunar.dart';

import 'package:minyun/models/SplayedFigureFindModel.dart';
import 'package:minyun/models/SplayedFigureModel.dart';
import 'package:tdesign_flutter/tdesign_flutter.dart';

import '../../constant.dart';
import 'package:flutter_html/flutter_html.dart';

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

  String gz="";
  String py="";
  String tyfx="日主分析";
  int _selectedShareValue=1;
  TextEditingController _userIdController = TextEditingController();
  TextEditingController _PhoneNumberController = TextEditingController();


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
          maxLines: null, // 允许无限行
          keyboardType: TextInputType.multiline, // 多行键盘
          decoration: InputDecoration(
            labelText: '请输入点评信息',
            border: OutlineInputBorder(),
          ),
        ),
        SizedBox(height: 16.0), // 添加一些间距
        buildShare(),
        buildUserId(context),
        buildPhone(context),
        ElevatedButton(
          onPressed: () {
            // 这里处理提交逻辑
            // 例如，你可以获取TextField的值并处理它
          },
          child: Text('提交'),
        ),
        Center(
          child: Text("暂无点评"),
        )
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
          controller: _PhoneNumberController,
          backgroundColor: Colors.white,
          hintText: '请输入用户电话号码',
          onChanged: (text) {
            setState(() {});
          },
          onClearTap: () {
            _PhoneNumberController.clear();
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
