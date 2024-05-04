import 'dart:collection';
import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:lunar/lunar.dart';

import 'package:minyun/models/SplayedFigureFindModel.dart';
import 'package:tdesign_flutter/tdesign_flutter.dart';


/**
 * 大师点评
 */
class UserSplayedFigureDetailCommonScreen extends StatefulWidget {
  final SplayedFigureFindModel search;
  UserSplayedFigureDetailCommonScreen({required this.search});

  @override
  State<UserSplayedFigureDetailCommonScreen> createState() => _UserSplayedFigureDetailCommonScreenState();
}

class _UserSplayedFigureDetailCommonScreenState extends State<UserSplayedFigureDetailCommonScreen>  with TickerProviderStateMixin {

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
            labelText: '请输入需要大师测算的内容（提交之后将会公开姓名生日与资料信息）',
            border: OutlineInputBorder(),
          ),
        ),
        SizedBox(height: 16.0), // 添加一些间距
        buildShare(),
        buildUserId(context),
        // buildPhone(context),
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
          leftLabel: '大师',
          required: true,
          controller: _userIdController,
          backgroundColor: Colors.white,
          hintText: '请输入大师账号',
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

  Widget buildShare(){
    return Row(
      children: [
        SizedBox(
          width: 90.0,
          child:
          Padding(
            padding: const EdgeInsets.only(left: 16),
            child: Text("是否指定大师解析：",
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
