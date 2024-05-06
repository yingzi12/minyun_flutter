import 'dart:collection';
import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:lunar/lunar.dart';

import 'package:minyun/models/SplayedFigureFindModel.dart';
import 'package:minyun/models/SplayedFigureModel.dart';

import '../../constant.dart';
import 'package:flutter_html/flutter_html.dart';

class SplayedFigureDetailAnalyzeScreen extends StatefulWidget {
  final SplayedFigureFindModel search;
  final Fx fx;
  SplayedFigureDetailAnalyzeScreen({required this.search, required this.fx});

  @override
  State<SplayedFigureDetailAnalyzeScreen> createState() => _SplayedFigureDetailAnalyzeScreenState();
}

class _SplayedFigureDetailAnalyzeScreenState extends State<SplayedFigureDetailAnalyzeScreen>  with TickerProviderStateMixin {

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
        Text("袁天罡称骨"),
        getText("骨重",widget.fx.gz??""),
        getText("评语",widget.fx.cgg??""),
        Text("通用分析"),
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
        getTAnalyzeText(tyfx),
      ],
    );
  }

  Widget getTitle(String title){
    return GestureDetector(
      onTap: () {
        // 在这里处理点击事件  
        setState(() {
          tyfx=generalAnalyzeCode[title]!;
        });
      },
      child: Chip(
          label: Text(title),
            backgroundColor: tyfx == generalAnalyzeCode[title] ? Colors.grey : Colors.transparent,
           // selected: selectedChips[title],
      ),
    );
  }

  Widget getTAnalyzeText(String key){
    switch (key){
      case "rizhufenxi":
        return getMap(widget.fx.rizhufenxi);
      case "xingZuo":
        return getMap(widget.fx.xingZuo);
      case "kwsc":
        return getList(widget.fx.kwsc2 ?? []);
      case "gdlm":
        return getList(widget.fx.gdlm ?? []);
      case "scwg":
        return getList(widget.fx.scwg ?? []);
      case "xiyongshencankao":
        List<String> list=[];
        list.add(widget.fx.xiyongshencankao1??"");
        list.add(widget.fx.xiyongshencankao2??"");
        return getList(list);
      case "yrjpfx":
        return getFxText(widget.fx.yrjpfx ?? "");
      default:
        return getMap(widget.fx.rizhufenxi);
    }
    }

    Widget getFxText(String text){
      return Html(
        data: text,
        style: {
          "p":Style(
            fontSize: FontSize(20),
            color:  Colors.black,
          )
        },
      );
    }
  Widget getList(List<String> lists){
    if(lists.length==0){
      return Text("无数据");
    }
    return RichText(
      text: TextSpan(
        children: [
          for(var str in lists)
            TextSpan(
              text: str+"\n",
              style: TextStyle(color: Colors.black, fontSize: 20),
            ),

        ],
      ),
    );

  }

  Widget getMap(dynamic keyValue){
    Map<String,String> map = Map<String, String>.from(keyValue.map(
          (key, value) => MapEntry(key, value.toString()),
    ));
    return RichText(
      text: TextSpan(
        children: [
          for (var entry in map.entries)
            TextSpan(
              text: entry.key,
              style: TextStyle(color: Colors.black, fontWeight: FontWeight.bold),
              children: <TextSpan>[
                TextSpan(text:":" , style: TextStyle(color: Colors.black, fontWeight: FontWeight.bold)),
                TextSpan(text: entry.value, style: TextStyle(fontWeight: FontWeight.bold,color: Colors.black45,)),
                TextSpan(text:"\n" ),
              ],
            ),
        ],
      ),
    );

  }


  Widget getText(String title,String value){
    return Text.rich(
      TextSpan(
        text: title,
        style: TextStyle(color: Colors.black, fontWeight: FontWeight.bold),
        children: <TextSpan>[
          TextSpan(text:":" , style: TextStyle(color: Colors.black, fontWeight: FontWeight.bold)),
          TextSpan(text: value, style: TextStyle(fontWeight: FontWeight.bold,color: Colors.black45,)),
        ],
      ),
    );

  }
}
