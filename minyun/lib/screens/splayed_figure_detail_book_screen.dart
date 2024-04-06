import 'dart:collection';
import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:lunar/lunar.dart';

import 'package:minyun/models/SplayedFigureFindModel.dart';
import 'package:minyun/models/SplayedFigureModel.dart';
import 'package:flutter_html/flutter_html.dart';

import '../constant.dart';

class SplayedFigureDetailBookScreen extends StatefulWidget {
  final SplayedFigureFindModel search;
  final Fx fx;
  SplayedFigureDetailBookScreen({required this.search, required this.fx});

  @override
  State<SplayedFigureDetailBookScreen> createState() => _SplayedFigureDetailBookScreenState();
}

class _SplayedFigureDetailBookScreenState extends State<SplayedFigureDetailBookScreen>  with TickerProviderStateMixin {

  String gz="";
  String py="";
  String gjck="穷通宝鉴";


  @override
  void initState() {
    super.initState();
    // _refreshApiData();
    _refreshSdkData();
  }

  @override
  void dispose() {
    super.dispose();
  }
  Future<void> _refreshSdkData() async {

  }


  // Future<void> _refreshApiData() async {
  //   Fx fx=widget.fx!;
  //
  //
  // }

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
        Text("古籍参考"),
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
        getTAnalyzeText(gjck),
      ],
    );
  }

  Widget getTitle(String title){
    return GestureDetector(
      onTap: () {
        // 在这里处理点击事件  
        setState(() {
          gjck=ancientBooksAnalyzeCode[title]!;
        });
      },
      child: Chip(
          label: Text(title),
            backgroundColor: gjck == ancientBooksAnalyzeCode[title] ? Colors.grey : Colors.transparent,
           // selected: selectedChips[title],
      ),
    );
  }

  Widget getTAnalyzeText(String key){
    switch (key){
      case "qiongtongbaojian":
        return getFxText(widget.fx.qiongtongbaojian??"");
      case "ditiansui":
        return getFxText(widget.fx.ditiansui??"");
      case "sanmingtonghui":
        List<String> list=[];
        list.add(widget.fx.sanmingtonghui1??"");
        list.add(widget.fx.sanmingtonghui2??"");
        list.add(widget.fx.sanmingtonghui3??"");
        list.add(widget.fx.sanmingtonghui4??"");
        return getList(list);
      case "bazitiyao":
        List<String> list=[];
        list.add(widget.fx.bazitiyao1??"");
        list.add(widget.fx.bazitiyao2??"");
        list.add(widget.fx.bazitiyao3??"");
        return getList(list);
      case "shenfengtongkao":
        List<String> list=[];
        list.add(widget.fx.shenfengtongkao1??"");
        list.add(widget.fx.shenfengtongkao2??"");
        list.add(widget.fx.shenfengtongkao3??"");
        return getList(list);

      case "tianwuwuxian":
        return getFxText(widget.fx.tianwuwuxian ?? "");
      case "wuxingjingji":
        List<String> list=[];
        list.add(widget.fx.wuxingjingji1??"");
        list.add(widget.fx.wuxingjingji2??"");
        list.add(widget.fx.wuxingjingji3??"");
        list.add(widget.fx.wuxingjingji4??"");
        list.add(widget.fx.wuxingjingji5??"");
        list.add(widget.fx.wuxingjingji6??"");
        return getList(list);
      case "lixuzhongmingshu":
        List<String> list=[];
        list.add(widget.fx.lixuzhongmingshu1??"");
        list.add(widget.fx.lixuzhongmingshu2??"");
        return getList(list);
      case "liangtoqian":
        return getFxText(widget.fx.liangtoqian??"");
      case "gewuzhiyan":
        return getFxText(widget.fx.gewuzhiyan??"");
      default:
        return getFxText(widget.fx.qiongtongbaojian??"");
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
     // return document.outerHtml;
    }
  Widget getList(List<String> lists){
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

  Widget getRizhufenxi(dynamic keyValue){
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
