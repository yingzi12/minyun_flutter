import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:lunar/lunar.dart';

import 'package:minyun/models/SplayedFigureFindModel.dart';

/**
 * 大师点评
 */
class UserSplayedFigureDetailInfoScreen extends StatefulWidget {
  final SplayedFigureFindModel search;
  UserSplayedFigureDetailInfoScreen({required this.search});

  @override
  State<UserSplayedFigureDetailInfoScreen> createState() => _UserSplayedFigureDetailInfoScreenState();
}

class _UserSplayedFigureDetailInfoScreenState extends State<UserSplayedFigureDetailInfoScreen>  with TickerProviderStateMixin {

  String gz="";
  String py="";
  String tyfx="日主分析";


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
        Text("个人资料"),
        TextField(
          maxLines: null, // 允许无限行
          keyboardType: TextInputType.multiline, // 多行键盘
          decoration: InputDecoration(
            labelText: '请输入个人资料信息',
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
        Center(
          child: Text("暂无个人资料"),
        )
      ],
    );
  }
}
