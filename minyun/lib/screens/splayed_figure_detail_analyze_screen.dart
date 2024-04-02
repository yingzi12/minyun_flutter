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

class SplayedFigureDetailAnalyzeScreen extends StatefulWidget {
  final SplayedFigureFindModel search;
  final SplayedFigureModel splayedFigureModel;
  SplayedFigureDetailAnalyzeScreen({required this.search, required this.splayedFigureModel});

  @override
  State<SplayedFigureDetailAnalyzeScreen> createState() => _SplayedFigureDetailAnalyzeScreenState();
}

class _SplayedFigureDetailAnalyzeScreenState extends State<SplayedFigureDetailAnalyzeScreen>  with TickerProviderStateMixin {


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
        Center(
          child: Wrap(
            spacing: 8.0, // 子组件之间的间距
            runSpacing: 4.0, // 换行之间的间距
            children: <Widget>[
              // 在这里放置你的组件
              getTitle('日主分析'),
              getTitle('星座分析'),
              getTitle('宫度论命'),
              getTitle('综合分析'),
              getTitle('三才五格'),
              getTitle('喜用神参考'),
              getTitle('月日精参'),
            ],
          ),
        ),
        Center(
          child: Wrap(
            spacing: 8.0, // 子组件之间的间距
            runSpacing: 4.0, // 换行之间的间距
            children: <Widget>[
              // 在这里放置你的组件
              getTitle('穷通宝鉴'),
              getTitle('滴天髓'),
              getTitle('三命通会'),
              getTitle('八字提要'),
              getTitle('神峰通考'),
              getTitle('天元巫咸'),
              getTitle('五行经纪'),
              getTitle('李虚中命书'),
              getTitle('鬼谷子两头钳'),
              getTitle('格物至言'),
            ],
          ),
        ),
      ],
    );
  }

  Widget getTitle(String title){
    return GestureDetector(
      onTap: () {
        // 在这里处理点击事件  
        print(title);
      },
      child: Chip(label: Text(title)),
    );
  }
}
