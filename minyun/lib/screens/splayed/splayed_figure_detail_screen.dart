import 'dart:collection';

import 'package:flutter/material.dart';
import 'package:lunar/lunar.dart';
import 'package:minyun/api/SplayedFigureApi.dart';
import 'package:minyun/models/SplayedFigureFindModel.dart';
import 'package:minyun/models/SplayedFigureModel.dart';
import 'package:minyun/screens/splayed/splayed_figure_detail_analyze_screen.dart';
import 'package:minyun/screens/splayed/splayed_figure_detail_common_screen.dart';
import 'package:minyun/screens/splayed/splayed_figure_detail_info_screen.dart';
import 'package:minyun/screens/splayed/splayed_figure_detail_intro_screen.dart';
import 'package:minyun/screens/splayed/splayed_figure_detail_paipan_screen.dart';
import 'package:minyun/utils/AppColors.dart';

import '../../utils/AppCommon.dart';
import '../../utils/images.dart';
import 'splayed_figure_detail_book_screen.dart';

class SplayedFigureDetailScreen extends StatefulWidget {
  final SplayedFigureFindModel search;
  SplayedFigureDetailScreen({required this.search});

  @override
  State<SplayedFigureDetailScreen> createState() => _SplayedFigureDetailScreenState();
}

class _SplayedFigureDetailScreenState extends State<SplayedFigureDetailScreen>  with TickerProviderStateMixin {

  List<String> tabsTiles=["基本","排盘","分析","古籍","点评","资料"];
  SplayedFigureModel? splayedFigureModel;
  late final TabController _tabController;

  EightChar? eightChar;
  // 创建日期格式化器
  RegExp regExp = RegExp(r'\d+');

  //日天干
  String? riTianGan;
  //日地支
  String? riDiZhi;

  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: tabsTiles.length, vsync: this);

    _refreshApiData();
    // _refreshSdkData();
  }



  @override
  void dispose() {
    super.dispose();
  }
  Future<void> _refreshSdkData(int year,int month,int day,int hour,int minute) async {
   // 今日八字甲戌 丁卯 戊申 戊午
    var birthSolor = Solar.fromYmdHms(year,month,day,hour,month,0);
    var birthLunar = birthSolor.getLunar();
    int sex=1;
    if('0' == widget.search.sex ){
      sex=0;
    }
    setState(() {
      eightChar = birthLunar.getEightChar();
    });
  }
  Future<void> _refreshApiData() async {
    Map<String, String> queryParams=new HashMap();
    queryParams["act"]="ok";
    queryParams["name"]=widget.search.name??"未知";
    queryParams["DateType"]=(widget.search.dateType ?? 4).toString();
    if(widget.search.dateType ==4){
      queryParams["ng"] = widget.search.ng ?? "";
      queryParams["yg"] = widget.search.yg ?? "";
      queryParams["rg"] = widget.search.rg ?? "";
      queryParams["sg"] = widget.search.sg ?? "";
    }
    if(widget.search.dateType ==5){
      queryParams["inputdate"] = widget.search.inputdate ?? "";
    }
    queryParams["sex"]=(widget.search.sex ?? 0).toString();
    if(widget.search.dateType ==5){
      queryParams["inputdate"] = widget.search.inputdate ?? "";
    }
    if(widget.search.paipanFs ==2) {
      queryParams["ztys"] =  (widget.search.ztys ?? 0).toString();
      if(widget.search.ztys ==1){
        queryParams["city1"] = widget.search.city1 ?? "";
        queryParams["city2"] = widget.search.city2 ?? "";
        queryParams["city3"] = widget.search.city3 ?? "";
      }
      queryParams["Sect"] = (widget.search.sect ?? 2).toString();
      queryParams["Siling"] =(widget.search.siling ?? 0).toString()  ;
    }
    queryParams["leixinggg"]="on";
    queryParams["api"]="1";
    queryParams["bcxx"]="1";
    SplayedFigureModel resultModel = await SplayedFigureApi.getSplayedFigure(queryParams);
    setState(() {
      // isRefreshing = false;
      splayedFigureModel=resultModel;
      // 将字符串解析为日期
      // DateTime dateTime = df.parse(resultModel.system.);
      String birthStr=resultModel.system!.zqrq ?? "";
      List<String> dateList = regExp.allMatches(birthStr).map((match) => match.group(0) ??"").toList();
      List<int> intNumbers = dateList.map((number) => int.parse(number)).toList();
      _refreshSdkData(intNumbers[0],intNumbers[1],intNumbers[2],intNumbers[3],intNumbers[4],);
      print("-----------加载数据-----start-------------");
      // Map<String, String> map = Map<String, String>.cast(resultModel.bz);
      // bzMap = Map<String, String>.from(resultModel.bz.map(
      //       (key, value) => MapEntry(key, value.toString()),
      // ));
      // xyMap = Map<String, String>.from(resultModel.dyws.map(
      //       (key, value) => MapEntry(key, value.toString()),
      // ));
      // zzMap = Map<String, String>.from(resultModel.dyzz.map(
      //       (key, value) => MapEntry(key, value.toString()),
      // ));
      // resultModel.llss.forEach((key, value) {
      //   print(value.toString());
      //   List<dynamic> stringList = value.cast<String>().map((e) => e.toString()).toList();
      //   ssMap[key] = stringList;
      // });
      // riTianGan=bzMap!['rg'];
      // riDiZhi=bzMap!['rz'];
      print("-----------加载数据----end--------------");

      // albumDataModel = resultModel.data ?? albumDataModel; // 如果获取失败，保留原数据
    });
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
    final height = MediaQuery.of(context).size.height;
    final width = MediaQuery.of(context).size.width;

    // DaYun nowDaYun=daYunList[luckDaYunIndex];
    return Scaffold(
      appBar: AppBar(
        bottom: TabBar(
          controller: _tabController,
          tabs: const <Widget>[
            // for(var tit in tabsTiles)
            Tab(
              text: '基本',
              // icon: Icon(Icons.cloud_outlined),
            ),
            Tab(
              text: '排盘',
            ),
            Tab(
              text: '分析',
            ),
            Tab(
              text: '古籍',
            ),
            Tab(
              text: '点评',
            ),
            Tab(
              text: '说明',
            ),
          ],
        ),
        title: Text("排盘详细"),
        titleTextStyle: appMainBoldTextStyle(fontSize: 24),
        elevation: 0,
        titleSpacing: 0,
        leading: Padding(
          padding: EdgeInsets.all(12.0),
          child: Image.asset(splash_screen_image, color: primaryColor),
        ),
      ),
      body:Stack(
        children: [
          Center(
              child: Padding(
                padding: EdgeInsets.all(16.0),
                child:  TabBarView(
                  controller: _tabController,
                  children:  <Widget>[
                    splayedFigureModel == null ?
                    Center(
                      child: Center(child: Text("加载中"),), // 加载中的指示器
                    ) : SplayedFigureDetailIntroScreen(
                      search: widget.search,
                      splayedFigureModel: splayedFigureModel!, eightChar: eightChar!,
                    ),
                    // 这里添加条件判断
                    splayedFigureModel == null ?
                    Center(
                      child: CircularProgressIndicator(), // 加载中的指示器
                    ) : SplayedFigureDetailPaipanScreen(
                      search: widget.search,
                      eightChar: eightChar!,
                      splayedFigureModel: splayedFigureModel!,
                    ),
                    splayedFigureModel == null ?
                    Center(
                      child: Center(child: Text("加载中"),), // 加载中的指示器
                    ) : SplayedFigureDetailAnalyzeScreen(
                      search: widget.search,
                      fx: splayedFigureModel!.fx!,
                    ),
                    splayedFigureModel == null ?
                    Center(
                      child: Center(child: Text("加载中"),), // 加载中的指示器
                    ) : SplayedFigureDetailBookScreen(
                      search: widget.search,
                      fx: splayedFigureModel!.fx!,
                    ),
                    SplayedFigureDetailCommonScreen(
                      search: widget.search,
                    ),
                    SplayedFigureDetailInfoScreen(
                      search: widget.search,
                    ),
                  ],
                ),
              ))
        ],
      ),
    );
  }

}
