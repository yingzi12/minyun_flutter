import 'dart:collection';
import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:lunar/calendar/eightchar/DaYun.dart';
import 'package:lunar/lunar.dart';
import 'package:minyun/api/SplayedFigureApi.dart';
import 'package:minyun/constant.dart';
import 'package:minyun/models/SplayedFigureFindModel.dart';
import 'package:minyun/models/SplayedFigureModel.dart';
import 'package:minyun/models/lucky_model.dart';
import 'package:minyun/models/lucky_year_model.dart';
import 'package:mongol/mongol.dart';
import 'package:tdesign_flutter/tdesign_flutter.dart';

import '../utils/color.dart';
import '../utils/common.dart';
import '../utils/images.dart';

class SplayedFigureDetailIntroScreen extends StatefulWidget {
  final SplayedFigureFindModel search;
  final SplayedFigureModel splayedFigureModel;
  SplayedFigureDetailIntroScreen({required this.search, required this.splayedFigureModel});

  @override
  State<SplayedFigureDetailIntroScreen> createState() => _SplayedFigureDetailIntroScreenState();
}

class _SplayedFigureDetailIntroScreenState extends State<SplayedFigureDetailIntroScreen>  with TickerProviderStateMixin {


  @override
  void initState() {
    super.initState();
    _refreshApiData();
    _refreshSdkData();
  }

  @override
  void dispose() {
    super.dispose();
  }
  Future<void> _refreshSdkData() async {
  }
  Future<void> _refreshApiData() async {
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
        getText("姓名",""),
        Row(
          children: [
            Expanded(child: getText("性别",""),),
            Expanded(child: getText("生肖",""),),
            Expanded(child: getText("性别",""),)
          ],
        ),
        getText("农历",""),
        getText("阴历",""),
        getText("出生地区",""),
        getText("节气",""),
        getText("物候",""),
        getText("人元司令分野",""),
        getText("三才五格",""),
        getText("六甲空亡",""),
        getText("强弱",""),
        getText("起运",""),
        getText("交运",""),
        Row(
          children: [
            Expanded(child: getText("星座",""),),
            Expanded(child: getText("星宿",""),)

          ],
        ),
        Row(
          children: [
            Expanded(child: getText("胎元",""),),
            Expanded(child: getText("空亡",""),)

          ],
        ),
        Row(
          children: [
            Expanded(child: getText("命宫",""),),
            Expanded(child: getText("胎息",""),)

          ],
        ),
        Row(
          children: [
            Expanded(child: getText("身宫",""),),
            Expanded(child: getText("命卦",""),)

          ],
        ),
        Text("袁天罡称骨"),
        getText("骨重",""),
        getText("评语",""),
      ],
    );
  }

  Widget getText(String title,String value){
    return Text.rich(
      TextSpan(
        text: '智能择日（ ',
        style: TextStyle(color: Colors.white, fontWeight: FontWeight.bold),
        children: <TextSpan>[
          TextSpan(text: title, style: TextStyle(color: Colors.black, fontWeight: FontWeight.bold)),
          TextSpan(text: value, style: TextStyle(fontWeight: FontWeight.bold)),
        ],
      ),
    );

  }

}
