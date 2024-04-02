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

class SplayedFigureDetailPaipanScreen extends StatefulWidget {
  final SplayedFigureFindModel search;
  final SplayedFigureModel splayedFigureModel;
  SplayedFigureDetailPaipanScreen({required this.search, required this.splayedFigureModel});

  @override
  State<SplayedFigureDetailPaipanScreen> createState() => _SplayedFigureDetailPaipanScreenState();
}

class _SplayedFigureDetailPaipanScreenState extends State<SplayedFigureDetailPaipanScreen>  with TickerProviderStateMixin {

  // SplayedFigureModel? splayedFigureModel;
  // bool isRefreshing = false; // 用于表示是否正在刷新数据
  // GlobalKey<RefreshIndicatorState> refreshKey = GlobalKey<RefreshIndicatorState>();
  Lunar lunar = Lunar.fromDate(DateTime.now());
  // late final TabController _tabController;
  //获取当前年
  DateTime now = DateTime.now();
  int currentYear =DateTime.now().year ;
  int currentMonth = DateTime.now().month;
  int currentDay= DateTime.now().day;
  int currentHour= DateTime.now().hour;

  EightChar? eightChar;
  int luckDaYunIndex=-1;
  int luckYearIndex=-1;
  List<DaYun> daYunList=[];

  //八字
  Map<String, String>? bzMap;
  //星运
  Map<String, String> xyMap={};
  List<LuckyYearModel> bigLuckyList=[];

  //自座
  Map<String, String> zzMap= {};
  //神煞
  Map<String, List<dynamic>> ssMap= {};

  //日天干
  String? riTianGan;
  //日地支
  String? riDiZhi;

  @override
  void initState() {
    super.initState();
    // _tabController = TabController(length: 3, vsync: this);
    _refreshApiData();
    _refreshSdkData();
  }

  @override
  void dispose() {
    super.dispose();
  }
  Future<void> _refreshSdkData() async {
// 今日八字甲戌 丁卯 戊申 戊午
    var birthSolor = Solar.fromYmdHms(widget.search.year!,widget.search.month!,widget.search.day!,widget.search.hour!,widget.search.minute ?? 0,0);
    var birthLunar = birthSolor.getLunar();
    int sex=1;
    if('0' == widget.search.sex ){
      sex=0;
    }

    //  currentYear = now.year;
    // /int currentMonth = now.month;
    // int currentDay= now.day;
    // int currentHour= now.hour;

    setState(() {
      eightChar = birthLunar.getEightChar();
      Yun yun = eightChar!.getYun(sex);
      daYunList=yun.getDaYun();
      for(var daYun in daYunList){
        LuckyYearModel luckyYear=LuckyYearModel();
        if( daYun.getStartYear() <= currentYear && currentYear <= daYun.getEndYear() ){
          luckDaYunIndex=daYun.getIndex();
          // luckyYearMonth(daYun,currentYear);
        }
        luckyYear.year=daYun.getStartYear().toString();
        luckyYear.age=daYun.getStartAge().toString();
        if(0==daYun.getIndex()) {
          Lunar yartLunar = daYun.getLunar();
          luckyYear.gan = yartLunar.getGan();
          luckyYear.zhi = yartLunar.getZhi();
        }else{
          String ganzhi=daYun.getGanZhi();
          luckyYear.gan = ganzhi[0];
          luckyYear.zhi = ganzhi[1];
        }
        String tianganShishen=ganzhiMap[eightChar!.getDayGan()+lunar.getGan()]??"";
        luckyYear.ganShishenIntro=tenGods[tianganShishen];
        var oneZG=dizhiMap[luckyYear.zhi]![0];
        var dizhiShishen= ganzhiMap[eightChar!.getDayGan()+oneZG]??"";
        luckyYear.zhiShishenIntro=tenGods[dizhiShishen];
        luckyYear.twelveGod=twelveGods[eightChar!.getDayGan()+luckyYear.zhi!];
        luckyYear.number=daYun.getIndex();
        bigLuckyList.add(luckyYear);
      }
    });
  }
  Future<void> _refreshApiData() async {
    // Map<String, String> queryParams=new HashMap();
    // queryParams["act"]="ok";
    // queryParams["name"]="七";
    // queryParams["DateType"]="5";
    // queryParams["inputdate"]="公历1994年3月23日 11时";
    // // queryParams["ng"]="己卯";
    // // queryParams["yg"]="丙寅";
    // // queryParams["rg"]="庚寅";
    // // queryParams["sg"]="丙子";
    //
    // queryParams["sex"]="0";
    // queryParams["ztys"]="0";
    // // queryParams["city1"]="北京";
    // // queryParams["city2"]="北京";
    // // queryParams["city3"]="东城区";
    // queryParams["Sect"]="1";
    // queryParams["Siling"]="0";
    //
    // queryParams["leixinggg"]="on";
    // queryParams["api"]="1";
    // queryParams["bcxx"]="1";
    //

    setState(() {
      // isRefreshing = false;
      // splayedFigureModel=widget.splayedFigureModel;
      // Map<String, String> map = Map<String, String>.cast(resultModel.bz);
      bzMap = Map<String, String>.from(widget.splayedFigureModel.bz.map(
            (key, value) => MapEntry(key, value.toString()),
      ));
      xyMap = Map<String, String>.from(widget.splayedFigureModel.dyws.map(
            (key, value) => MapEntry(key, value.toString()),
      ));
      zzMap = Map<String, String>.from(widget.splayedFigureModel.dyzz.map(
            (key, value) => MapEntry(key, value.toString()),
      ));
      widget.splayedFigureModel.llss.forEach((key, value) {
        print(value.toString());
        List<dynamic> stringList = value.cast<String>().map((e) => e.toString()).toList();
        ssMap[key] = stringList;
      });
      riTianGan=bzMap!['rg'];
      riDiZhi=bzMap!['rz'];

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

    DaYun nowDaYun=daYunList[luckDaYunIndex];
    return  ListView(
      children: [
        nowDate(lunar.getSolar(),lunar),
        getBaziTitle("标注","流时","流日","流月","流年","大运","年柱","月柱","日柱","时柱",10,Colors.black87),
        getBaziShishen(eightChar!,lunar),
        getBaziTitle("天干",lunar.getTimeGan(),lunar.getDayGan(),lunar.getMonthGan(),lunar.getYearGan(),lunar.getYearGan(),
            eightChar!.getYearGan(),eightChar!.getMonthGan(), eightChar!.getDayGan(), eightChar!.getTimeGan(),
            20,Colors.black87
        ),
        getBaziTitle("地支",lunar.getTimeZhi(),lunar.getDayZhi(),lunar.getMonthZhi(),lunar.getYearZhi(),lunar.getYearZhi(),
            eightChar!.getYearZhi(),eightChar!.getMonthZhi(), eightChar!.getDayZhi(), eightChar!.getTimeZhi(),
            20,Colors.black87
        ),
        getZgan(eightChar!,lunar),
        getTable( "星运",xyMap[lunar.getTimeZhi()]??"",xyMap[lunar.getDayZhi()]??"",xyMap[lunar.getMonthZhi()]??"",xyMap[lunar.getYearZhi()]??"",xyMap[lunar.getYearZhi()]??"",
            xyMap[eightChar!.getYearZhi()]??"",xyMap[eightChar!.getMonthZhi()]??"",xyMap[eightChar!.getDayZhi()]??"",xyMap[eightChar!.getTimeZhi()]??"",
            10,Colors.black87
        ),
        getTable( "自坐",zzMap[lunar.getTimeGan()+lunar.getTimeZhi()]??"",zzMap[lunar.getDayGan()+lunar.getDayZhi()]??"",zzMap[lunar.getMonthGan()+lunar.getMonthZhi()]??"",zzMap[lunar.getYearGan()+lunar.getYearZhi()]??"",zzMap[lunar.getYearGan()+lunar.getYearZhi()]??"",
            zzMap[eightChar!.getYearGan()+eightChar!.getYearZhi()]??"",zzMap[eightChar!.getMonthGan()+eightChar!.getMonthZhi()]??"",zzMap[eightChar!.getDayGan()+eightChar!.getDayZhi()]??"",zzMap[eightChar!.getTimeGan()+eightChar!.getTimeZhi()]??"",
            10,Colors.black87
        ),
        getTable( "空亡",lunar.getTimeXunKong(),lunar.getDayXunKong(),lunar.getMonthXunKong(),lunar.getYearXunKong(),lunar.getYearXunKong(),
            eightChar!.getYearXunKong(),eightChar!.getMonthXunKong(),eightChar!.getDayXunKong(),eightChar!.getTimeXunKong(),
            10,Colors.black87
        ),
        getTableNode( "纳音",lunar.getTimeNaYin(),lunar.getDayNaYin(),lunar.getMonthNaYin(),lunar.getYearNaYin(),lunar.getYearNaYin(),
            eightChar!.getYearNaYin(),eightChar!.getMonthNaYin(),eightChar!.getDayNaYin(),eightChar!.getTimeNaYin(),
            10,Colors.black87
        ),
        Divider(),
        getShensa(eightChar!,lunar,10,Colors.black87,ssMap),
        Divider(),
        getBigLucky(bigLuckyList,10,Colors.black87),
        Divider(),
        getNowYear(nowDaYun,10,Colors.black87),
        Divider(),
        getAllYear(daYunList,10,Colors.black87),
        Divider(),
        getNowMonth(currentYear,currentMonth,8,Colors.black87),
        Divider(),
        getNowDay(currentYear,currentMonth,currentDay,8,Colors.black87),
        Divider(),
        getNowHour(currentYear,currentMonth,currentDay,currentHour,8,Colors.black87),
        // Divider(),
      ],
    );
  }

  Widget _weakSelectTextTabBar(BuildContext context) {
    return TDBottomTabBar(
      TDBottomTabBarBasicType.text,
      componentType: TDBottomTabBarComponentType.normal,
      useVerticalDivider: true,
      navigationTabs: [
        TDBottomTabBarTabConfig(
          badgeConfig: BadgeConfig(
            showBage: true,
            tdBadge: const TDBadge(TDBadgeType.redPoint),
            badgeTopOffset: -2,
            badgeRightOffset: -10,
          ),
          tabText: '标签',
          onTap: () {
            // onTapTab(context, '标签1');
          },
        ),
        TDBottomTabBarTabConfig(
          tabText: '标签',
          onTap: () {
            // onTapTab(context, '标签2');
          },
        ),
        TDBottomTabBarTabConfig(
          tabText: '标签',
          onTap: () {
            // onTapTab(context, '标签2');
          },
        ),
        TDBottomTabBarTabConfig(
          tabText: '标签',
          onTap: () {
            // onTapTab(context, '标签2');
          },
        ),
        TDBottomTabBarTabConfig(
          tabText: '标签',
          onTap: () {
            // onTapTab(context, '标签2');
          },
        ),
        TDBottomTabBarTabConfig(
          tabText: '标签',
          onTap: () {
            // onTapTab(context, '标签2');
          },
        ),
        TDBottomTabBarTabConfig(
          tabText: '标签',
          onTap: () {
            // onTapTab(context, '标签2');
          },
        ),
        TDBottomTabBarTabConfig(
          tabText: '标签',
          onTap: () {
            // onTapTab(context, '标签3');
          },
        ),
      ],
    );
  }

  Widget nowDate(Solar solar,Lunar lunar){
    return Column(
        children:[
          Text('农历：'+solar.toString()),
          Text('阳历：'+lunar.toString()),
        ]
    );
  }

  Widget getBaziShishen(EightChar eightChar,Lunar luna
      ){
    // "十神",ganzhiMap[eightChar!.getDayGan()+lunar.getTimeGan()]??"",
    String title="十神";
    String v1=ganzhiMap[eightChar!.getDayGan()+lunar.getTimeGan()]??"";
    String v2=ganzhiMap[eightChar!.getDayGan()+lunar.getDayGan()]??"";
    String v3=ganzhiMap[eightChar!.getDayGan()+lunar.getMonthGan()]??"";
    String v4=ganzhiMap[eightChar!.getDayGan()+lunar.getYearGan()]??"";
    String v5=ganzhiMap[eightChar!.getDayGan()+lunar.getYearGan()]??"";
    String bz1=eightChar.getYearShiShenGan();
    String bz2=eightChar.getMonthShiShenGan();
    String bz3=eightChar.getDayShiShenGan();
    String bz4=eightChar.getTimeShiShenGan();
    return   Row(
        children: <Widget>[
          Expanded(child: getSmallTitle(title)),
          Expanded(child: getSSText(v1)),
          Expanded(child: getSSText(v2)),
          Expanded(child: getSSText(v3)),
          Expanded(child: getSSText(v4)),
          Expanded(child: getSSText(v5)),
          SizedBox(
            height: 20,
            child: VerticalDivider(),
          ),
          Expanded(child: getSSText(bz1)),
          Expanded(child: getSSText(bz2)),
          Expanded(child: getSSText(bz3)),
          Expanded(child: getSSText(bz4)),
        ]
    );
  }

  Widget getBaziTitle(String title,String v1,String v2,String v3,String v4,String v5,
      String bz1, String bz2, String bz3, String bz4,double size,Color color
      ){
    return   Row(
        children: <Widget>[
          Expanded(child: getSmallTitle(title)),
          Expanded(child: getBzText(v1,size,color)),
          Expanded(child: getBzText(v2,size,color)),
          Expanded(child: getBzText(v3,size,color)),
          Expanded(child: getBzText(v4,size,color)),
          Expanded(child: getBzText(v5,size,color)),
          SizedBox(
            height: 30,
            child: VerticalDivider(),
          ),
          Expanded(child: getBzText(bz1,size,color)),
          Expanded(child: getBzText(bz2,size,color)),
          Expanded(child: getBzText(bz3,size,color)),
          Expanded(child: getBzText(bz4,size,color)),
        ]
    );
  }
  //星运
  Widget getTable(String title,String v1,String v2,String v3,String v4,String v5,
      String bz1, String bz2, String bz3, String bz4,double size,Color color
      ){
    return   Row(
        children: <Widget>[
          Expanded(child: getSmallTitle(title)),
          SizedBox(
            height: size+5,
            child: VerticalDivider(),
          ),
          Expanded(child: getBzText(v1,size,color)),
          SizedBox(
            height: size+5,
            child: VerticalDivider(),
          ),

          Expanded(child: getBzText(v2,size,color)),
          SizedBox(
            height: size+5,
            child: VerticalDivider(),
          ),
          Expanded(child: getBzText(v3,size,color)),
          SizedBox(
            height: size+5,
            child: VerticalDivider(),
          ),
          Expanded(child: getBzText(v4,size,color)),
          SizedBox(
            height: size+5,
            child: VerticalDivider(),
          ),
          Expanded(child: getBzText(v5,size,color)),
          SizedBox(
            height: size+5,
            child: VerticalDivider(),
          ),
          Expanded(child: getBzText(bz1,size,color)),
          SizedBox(
            height: size+5,
            child: VerticalDivider(),
          ),
          Expanded(child: getBzText(bz2,size,color)),
          SizedBox(
            height: size+5,
            child: VerticalDivider(),
          ),
          Expanded(child: getBzText(bz3,size,color)),
          SizedBox(
            height: size+5,
            child: VerticalDivider(),
          ),
          Expanded(child: getBzText(bz4,size,color)),
        ]
    );
  }

  Widget getTableNode(String title,String v1,String v2,String v3,String v4,String v5,
      String bz1, String bz2, String bz3, String bz4,double size,Color color
      ){
    return   Row(
        children: <Widget>[
          Expanded(child: getSmallTitle(title)),

          Expanded(child: getBzText(v1,size,color)),

          Expanded(child: getBzText(v2,size,color)),

          Expanded(child: getBzText(v3,size,color)),

          Expanded(child: getBzText(v4,size,color)),

          Expanded(child: getBzText(v5,size,color)),

          Expanded(child: getBzText(bz1,size,color)),

          Expanded(child: getBzText(bz2,size,color)),

          Expanded(child: getBzText(bz3,size,color)),

          Expanded(child: getBzText(bz4,size,color)),
        ]
    );
  }
  //神煞
  Widget getShensa(EightChar eightChar,Lunar lunar,double size,Color color,Map<String,List<dynamic>> ssMap){
    int max=1;
    List<dynamic> timeSSList = ssMap[lunar.getTimeGan()+lunar.getTimeZhi()]??[];
    if(timeSSList.length>max){
      max=timeSSList.length;
    }
    List<dynamic> daySSList = ssMap[lunar.getDayGan()+lunar.getDayZhi()]??[];
    if(daySSList.length>max){
      max=daySSList.length;
    }
    List<dynamic> monthSSList = ssMap[lunar.getMonthGan()+lunar.getMonthZhi()]??[];
    if(monthSSList.length>max){
      max=monthSSList.length;
    }
    List<dynamic> yearSSList = ssMap[lunar.getYearGan()+lunar.getYearZhi()]??[];
    if(yearSSList.length>max){
      max=yearSSList.length;
    }
    List<dynamic> luckySSList = ssMap[lunar.getYearGan()+lunar.getYearZhi()]??[];
    if(luckySSList.length>max){
      max=luckySSList.length;
    }
    List<dynamic> bztimeSSList = ssMap[eightChar.getTimeGan()+eightChar.getTimeZhi()]??[];
    if(bztimeSSList.length>max){
      max=bztimeSSList.length;
    }
    List<dynamic> bzdaySSList = ssMap[eightChar.getDayGan()+eightChar.getDayZhi()]??[];
    if(bzdaySSList.length>max){
      max=bzdaySSList.length;
    }
    List<dynamic> bzmonthSSList = ssMap[eightChar.getMonthGan()+eightChar.getMonthZhi()]??[];
    if(bzmonthSSList.length>max){
      max=bzmonthSSList.length;
    }
    List<dynamic> bzyearSSList = ssMap[eightChar.getYearGan()+eightChar.getYearZhi()]??[];
    if(bzyearSSList.length>max){
      max=bzyearSSList.length;
    }
    double hight=max*20+30;
    return   Row(
      children: <Widget>[
        Expanded(
          child: Column(
            children: <Widget>[
              getSmallTitle("神煞"),
            ],
          ),
        ),
        SizedBox(
          height: hight, // 使用 double.infinity
          child: VerticalDivider(),
        ),
        Expanded(
          child: Column(
            children: <Widget>[
              for(var shensa in timeSSList)
                  getBzText(shensa.toString(),size,color),
            ],
          ),
        ),
        SizedBox(
          height: hight,
          child: VerticalDivider(),
        ),
        Expanded(
          child: Column(
            children: <Widget>[
              for(var shensa in daySSList)
                getBzText(shensa.toString(),size,color),
            ],
          ),
        ),
        SizedBox(
          height: hight,
          child: VerticalDivider(),
        ),
        Expanded(
          child: Column(
            children: <Widget>[
              for(var shensa in monthSSList)
                getBzText(shensa.toString(),size,color),
            ],
          ),
        ),
        SizedBox(
          height: hight,
          child: VerticalDivider(),
        ),
        Expanded(
          child: Column(
            children: <Widget>[
              for(var shensa in yearSSList)
                getBzText(shensa.toString(),size,color),

            ],
          ),
        ),
        SizedBox(
          height: hight,
          child: VerticalDivider(),
        ),
        Expanded(
          child: Column(
            children: <Widget>[
              for(var shensa in luckySSList)
                getBzText(shensa.toString(),size,color),
            ],
          ),
        ),
        SizedBox(
          height: hight,
          child: VerticalDivider(),
        ),
        Expanded(
          child: Column(
            children: <Widget>[
              for(var shensa in bzyearSSList)
                getBzText(shensa.toString(),size,color),

            ],
          ),
        ),
        SizedBox(
          height: hight,
          child: VerticalDivider(),
        ),
        Expanded(
          child: Column(
            children: <Widget>[
              for(var shensa in bzmonthSSList)
                getBzText(shensa.toString(),size,color),
            ],
          ),
        ),
        SizedBox(
          height: hight,
          child: VerticalDivider(),
        ),
        Expanded(
          child: Column(
            children: <Widget>[
              for(var shensa in bzdaySSList)
                getBzText(shensa.toString(),size,color),
            ],
          ),
        ),
        SizedBox(
          height: hight,
          child: VerticalDivider(),
        ),
        Expanded(
          child: Column(
            children: <Widget>[
              for(var shensa in bztimeSSList)
                getBzText(shensa.toString(),size,color),
            ],
          ),
        ),
      ],
    );

  }

  //大运
  Widget getBigLucky(List<LuckyYearModel> bigLuckyList,double size,Color color){
    int max=5;
    double hight=max*10+50;
    return   Row(
      children: <Widget>[
        Expanded(
          child: Column(
            children: <Widget>[
              getSmallTitle("鸿运"),
            ],
          ),
        ),
        SizedBox(
          height: hight, // 使用 double.infinity
          child: VerticalDivider(),
        ),
        getBigLuckyCell(bigLuckyList[0]),
        SizedBox(
          height: hight, // 使用 double.infinity
          child: VerticalDivider(),
        ),
        getBigLuckyCell(bigLuckyList[1]),
        SizedBox(
          height: hight, // 使用 double.infinity
          child: VerticalDivider(),
        ),
        getBigLuckyCell(bigLuckyList[2]),
        SizedBox(
          height: hight, // 使用 double.infinity
          child: VerticalDivider(),
        ),
        getBigLuckyCell(bigLuckyList[4]),
        SizedBox(
          height: hight, // 使用 double.infinity
          child: VerticalDivider(),
        ),
        getBigLuckyCell(bigLuckyList[5]),
        SizedBox(
          height: hight, // 使用 double.infinity
          child: VerticalDivider(),
        ),
        getBigLuckyCell(bigLuckyList[6]),
        SizedBox(
          height: hight, // 使用 double.infinity
          child: VerticalDivider(),
        ),
        getBigLuckyCell(bigLuckyList[7]),
        SizedBox(
          height: hight, // 使用 double.infinity
          child: VerticalDivider(),
        ),
        getBigLuckyCell(bigLuckyList[8]),
        SizedBox(
          height: hight, // 使用 double.infinity
          child: VerticalDivider(),
        ),
        getBigLuckyCell(bigLuckyList[9]),
      ],
    );

  }

  Widget getBigLuckyCell(LuckyYearModel lcky) {
    return Expanded(
      child: GestureDetector(
        onTap: () {
          // 处理点击事件
        },
        child: Container(
          color: Colors.grey.withOpacity(0.1), // 设置背景颜色
          child: Column(
            children: <Widget>[
              getBzText(lcky.year ?? "", 9, Colors.black),
              getBzText(lcky.age ?? "", 10, Colors.black),
              Row(
                children: [
                  getBzText(lcky.gan ?? "", 12, Colors.black),
                  getBzText(lcky.ganShishenIntro ?? "", 8, Colors.orange),
                ],
              ),
              Row(
                children: [
                  getBzText(lcky.zhi ?? "", 12, Colors.black),
                  getBzText(lcky.zhiShishenIntro ?? "", 8, Colors.orange),
                ],
              ),
              getBzText(lcky.twelveGod ?? "", 10, Colors.black),
            ],
          ),
        ),
      ),
    );
  }

  //所选大运的流年
  Widget getNowYear(DaYun daYun,double size,Color color){
    int max=4;
    double hight=max*10+30;
   List<LuckyYearModel> nowBigLuckyList=[];
    for(var year in daYun.getLiuNian()){
      LuckyYearModel luckyYear=LuckyYearModel();
      luckyYear.year=year.getYear().toString();
      String ganzhi=year.getGanZhi();
      luckyYear.gan = ganzhi[0];
      luckyYear.zhi = ganzhi[1];
      String tianganShishen=ganzhiMap[eightChar!.getDayGan()+luckyYear.gan!]??"";
      luckyYear.ganShishenIntro=tenGods[tianganShishen];
      var oneZG=dizhiMap[luckyYear.zhi]![0];
      var dizhiShishen= ganzhiMap[eightChar!.getDayGan()+oneZG]??"";
      luckyYear.zhiShishenIntro=tenGods[dizhiShishen];
      luckyYear.twelveGod=twelveGods[eightChar!.getDayGan()+luckyYear.zhi!];
      luckyYear.number=year.getIndex();
      nowBigLuckyList.add(luckyYear);
    }
    return   Row(
      children: <Widget>[
        Expanded(
          flex: 1,
          child: Column(
            children: <Widget>[
              MongolText("流年"),
            ],
          ),
        ),
        Expanded(
          flex: 21,
          child: Row(
            children: <Widget>[
              for(var nowBigLucky in nowBigLuckyList)
              getNowYearCell(nowBigLucky),
            ],
          ),
        ),
      ],
    );

  }

  Widget getNowYearCell(LuckyYearModel lcky) {
    Border border = Border(
      left: BorderSide(
          width: 1.0,
          color: Colors.black
      ), // 左边框宽度和颜色
    );
    return Expanded(
      child: GestureDetector(
        onTap: () {
          // 处理点击事件
        },
        child: Container(
          decoration: BoxDecoration(
            border: border, // 应用左边框样式
          ),
          // color: Colors.grey.withOpacity(0.1), // 设置背景颜色
          child: Column(
            children: <Widget>[
              getBzText(lcky.year ?? "", 9, Colors.black),
              Row(
                children: [
                  getBzText(lcky.gan ?? "", 12, Colors.black),
                  getBzText(lcky.ganShishenIntro ?? "", 8, Colors.orange),
                ],
              ),
              Row(
                children: [
                  getBzText(lcky.zhi ?? "", 12, Colors.black),
                  getBzText(lcky.zhiShishenIntro ?? "", 8, Colors.orange),
                ],
              ),
              getBzText(lcky.twelveGod ?? "", 10, Colors.black),
            ],
          ),
        ),
      ),
    );
  }

  //流月
  Widget getNowMonth(int year,int month,double size,Color color){
    int max=5;
    double hight=max*10+50;
    Solar jqS = Solar.fromYmd(year,month,1);
    Lunar jqL =jqS.getLunar();
    Map<String, Solar> jq =jqL.getJieQiTable();
    List<LuckyDateModel> LuckyDateList=[];
    for(var i=1;i<13;i++){
      Solar d = Solar.fromYmd(year,i,1);
      Lunar l = d.getLunar();
      LuckyDateModel luckyDate=new LuckyDateModel();
      luckyDate.date=i.toString();
      // luckyDate.solarTermsDay=
      luckyDate.gan=l.getMonthGan();
      luckyDate.zhi=l.getMonthZhi();
      String tianganShishen=ganzhiMap[eightChar!.getDayGan()+luckyDate.gan!]??"";
      luckyDate.ganShishenIntro=tenGods[tianganShishen];
      var oneZG=dizhiMap[luckyDate.zhi]![0];
      var dizhiShishen= ganzhiMap[eightChar!.getDayGan()+oneZG]??"";
      luckyDate.zhiShishenIntro=tenGods[dizhiShishen];
      LuckyDateList.add(luckyDate);
    }
    LuckyDateList[0].solarTerms='立春';
    LuckyDateList[0].solarTermsDay=jq['立春']!.getMonth().toString()+"/"+jq['立春']!.getDay().toString();
    LuckyDateList[1].solarTerms='惊蛰';
    LuckyDateList[1].solarTermsDay=jq['惊蛰']!.getMonth().toString()+"/"+jq['惊蛰']!.getDay().toString();
    LuckyDateList[2].solarTerms='清明';
    LuckyDateList[2].solarTermsDay=jq['清明']!.getMonth().toString()+"/"+jq['清明']!.getDay().toString();
    LuckyDateList[3].solarTerms='立夏';
    LuckyDateList[3].solarTermsDay=jq['立夏']!.getMonth().toString()+"/"+jq['立夏']!.getDay().toString();
    LuckyDateList[4].solarTerms='芒种';
    LuckyDateList[4].solarTermsDay=jq['芒种']!.getMonth().toString()+"/"+jq['芒种']!.getDay().toString();
    LuckyDateList[5].solarTerms='小暑';
    LuckyDateList[5].solarTermsDay=jq['小暑']!.getMonth().toString()+"/"+jq['小暑']!.getDay().toString();
    LuckyDateList[6].solarTerms='立秋';
    LuckyDateList[6].solarTermsDay=jq['立秋']!.getMonth().toString()+"/"+jq['立秋']!.getDay().toString();
    LuckyDateList[7].solarTerms='白露';
    LuckyDateList[7].solarTermsDay=jq['白露']!.getMonth().toString()+"/"+jq['白露']!.getDay().toString();
    LuckyDateList[8].solarTerms='寒露';
    LuckyDateList[8].solarTermsDay=jq['寒露']!.getMonth().toString()+"/"+jq['寒露']!.getDay().toString();
    LuckyDateList[9].solarTerms='立冬';
    LuckyDateList[9].solarTermsDay=jq['立冬']!.getMonth().toString()+"/"+jq['立冬']!.getDay().toString();
    LuckyDateList[10].solarTerms='大雪';
    LuckyDateList[10].solarTermsDay=jq['大雪']!.getMonth().toString()+"/"+jq['大雪']!.getDay().toString();
    LuckyDateList[11].solarTerms='小寒';
    LuckyDateList[11].solarTermsDay=jq['小寒']!.getMonth().toString()+"/"+jq['小寒']!.getDay().toString();
    return   Row(
      children: <Widget>[
        Expanded(
          flex: 1,
          child: Column(
            children: <Widget>[
              MongolText("流月"),
            ],
          ),
        ),
        Expanded(
          flex: 21,
          child: Row(
            children: <Widget>[
              for(var luckyDate in LuckyDateList)
                getNowMonthCell(luckyDate,size,color),
            ],
          ),
        ),
      ],
    );

  }
  //流月
  Widget getNowMonthCell(LuckyDateModel luckyModel,double size,Color color) {
    Border border = Border(
      left: BorderSide(
          width: 1.0,
          color: Colors.black
      ), // 左边框宽度和颜色
    );
    return Expanded(
      child: GestureDetector(
        onTap: () {
          // 处理点击事件
        },
        child: Container(
          decoration: BoxDecoration(
            border: border, // 应用左边框样式
          ),
          // color: Colors.grey.withOpacity(0.1), // 设置背景颜色
          child: Column(
            children: <Widget>[
              getBzText(luckyModel.solarTerms??"", size, color),
              getBzText(luckyModel.solarTermsDay??"", size, color),
              getBzText(luckyModel.gan??"", size, color),
              getBzText(luckyModel.zhi??"", size, color),
              getBzText((luckyModel.ganShishenIntro??"")+(luckyModel.zhiShishenIntro??""), size, color),
            ],
          ),
        ),
      ),
    );
  }
  //流月
  Widget getNowDay(int year,int month,int day,double size,Color color){
    int max=5;
    double hight=max*15+50;
    int days=getDaysInMonth(year,month);
    List<LuckyDateModel> LuckyDateList=[];
    for(var i=0;i<days;i++){
      Solar d = Solar.fromYmd(year,month,i+1);
      Lunar l = d.getLunar();
      LuckyDateModel luckyDate=new LuckyDateModel();
      luckyDate.date=(i+1).toString();
      luckyDate.chinaDay=l.getDayInChinese();
      luckyDate.gan=l.getMonthGan();
      luckyDate.zhi=l.getMonthZhi();
      String tianganShishen=ganzhiMap[eightChar!.getDayGan()+luckyDate.gan!]??"";
      luckyDate.ganShishenIntro=tenGods[tianganShishen];
      var oneZG=dizhiMap[luckyDate.zhi]![0];
      var dizhiShishen= ganzhiMap[eightChar!.getDayGan()+oneZG]??"";
      luckyDate.zhiShishenIntro=tenGods[dizhiShishen];
      LuckyDateList.add(luckyDate);
    }
    return   Row(
      children: <Widget>[
        Expanded(
          flex: 1,
          child: Column(
            children: <Widget>[
              MongolText("流日"),
            ],
          ),
        ),
        // SizedBox(
        //   height: hight, // 使用 double.infinity
        //   child: VerticalDivider(),
        // ),
        Expanded(
          flex: 21,
          child: Column(children: <Widget>[
           Row(children: <Widget>[
             for(var i=0;i<11;i++)
               getNowDayCell(LuckyDateList[i],size,color),
           ],),
            Divider(),
            Row(children: <Widget>[
              for(var i=11;i<22;i++)
                getNowDayCell(LuckyDateList[i],size,color),
            ],),
            Divider(),
            Row(children: <Widget>[
              for(var i=22;i<LuckyDateList.length;i++)
                getNowDayCell(LuckyDateList[i],size,color),
              Expanded(
                flex: (11-(LuckyDateList.length-22)),
                  child: SizedBox()),
            ],),
          ],),
        ),
      ],
    );

  }
  //流月
  Widget getNowDayCell(LuckyDateModel luckyModel,double size,Color color) {
    // 定义左边框样式
    Border border = Border(
      left: BorderSide(
          width: 1.0,
          color: Colors.black
      ), // 左边框宽度和颜色
    );
    return Expanded(
      flex: 1,
      child: GestureDetector(
        onTap: () {
          // 处理点击事件
        },
        child: Container(
          decoration: BoxDecoration(
            border: border, // 应用左边框样式
          ),
          padding: EdgeInsets.all(3.0),
          // color: Colors.grey.withOpacity(0.1), // 设置背景颜色
          child:
          // Row(
          //     children: <Widget>[
                // SizedBox(
                //   height: 10, // 使用 double.infinity
                //   child: VerticalDivider(),
                // ),
            Column(
            children: <Widget>[
              getBzText(luckyModel.date??"", size, color),
              getBzText(luckyModel.chinaDay??"", size, color),
              getBzText(luckyModel.gan??"", size, color),
              getBzText(luckyModel.zhi??"", size, color),
              getBzText((luckyModel.ganShishenIntro??"")+(luckyModel.zhiShishenIntro??""), size, color),
            ],
          ),
          //     ],
          // ),
        ),
      ),
    );
  }

  //流月
  Widget getNowHour(int year,int month,int day,int hour,double size,Color color){
    int max=4;
    double hight=max*10+50;
    List<LuckyDateModel> LuckyDateList=[];
    Solar d = Solar.fromYmd(year,month,day);
    Lunar l = d.getLunar();
    List<LunarTime> timeList= l.getTimes();
    for(var time in timeList){

      LuckyDateModel luckyDate=new LuckyDateModel();
      luckyDate.date=time.getMinHm();
      luckyDate.gan=time.getGan();
      luckyDate.zhi=time.getZhi();
      String tianganShishen=ganzhiMap[eightChar!.getDayGan()+luckyDate.gan!]??"";
      luckyDate.ganShishenIntro=tenGods[tianganShishen];
      var oneZG=dizhiMap[luckyDate.zhi]![0];
      var dizhiShishen= ganzhiMap[eightChar!.getDayGan()+oneZG]??"";
      luckyDate.zhiShishenIntro=tenGods[dizhiShishen];
      LuckyDateList.add(luckyDate);
    }
    return   Row(
      children: <Widget>[
        Expanded(
          flex: 1,
          child: Column(
            children: <Widget>[
              MongolText("流时"),
            ],
          ),
        ),
        Expanded(
          flex: 21,
          child: Row(
            children: <Widget>[
              for(var LuckyDate in LuckyDateList)
                getNowHourCell(LuckyDate,size,color),
            ],
          ),
        ),
      ],
    );

  }
  //流月
  Widget getNowHourCell(LuckyDateModel luckyModel,double size,Color color) {
    return Expanded(
      child: GestureDetector(
        onTap: () {
          // 处理点击事件
        },
        child: Container(
          color: Colors.grey.withOpacity(0.1), // 设置背景颜色
          child: Column(
            children: <Widget>[
              getBzText(luckyModel.date??"", size, color),
              getBzText(luckyModel.gan??"", size, color),
              getBzText(luckyModel.zhi??"", size, color),
              getBzText((luckyModel.ganShishenIntro??"")+(luckyModel.zhiShishenIntro??""), size, color),
            ],
          ),
        ),
      ),
    );
  }

  //是有的流年
  Widget getAllYear(List<DaYun> daYun,double size,Color color){
    int max=10;
    double hight=max*15+50;

    return   Row(
      children: <Widget>[
        Expanded(
          flex: 1,
          child: Container(
            height: 210, // 设置固定高度为150
            color: Colors.black12, // 设置背景颜色
            child:  Column(
              mainAxisAlignment: MainAxisAlignment.center, // 将子部件垂直居中
              children: <Widget>[
                MongolText("流年"),
                getSmallTitle("小运"),
              ],
            ),),
        ),
        // Expanded(
        //   flex: 1,
        //   child: Column(
        //     children: <Widget>[
        //       MongolText("流年"),
        //       getSmallTitle("小运"),
        //     ],
        //   ),
        // ),
        Expanded(
          flex: 21,
          child: Row(
            children: <Widget>[
              for(var dayun in daYun)
                getAllYearCell(dayun,12,Colors.black87),

            ],
          ),
        ),
      ],
    );

  }

  Widget getAllYearCell(DaYun daYun,double size,Color color) {
    Border border = Border(
      left: BorderSide(
          width: 1.0,
          color: Colors.black
      ), // 左边框宽度和颜色
    );
    return Expanded(
      child: GestureDetector(
        onTap: () {
          // 处理点击事件
        },
        child: Container(
          height: 210, // 设置固定高度为150
          decoration: BoxDecoration(
            border: border, // 应用左边框样式
          ),
          // color: Colors.grey.withOpacity(0.1), // 设置背景颜色
          child: Column(
            children: <Widget>[
              getBzText(daYun.getStartYear().toString(), size, color),
              for(var yunYear in daYun.getLiuNian())
                getBzText(yunYear.getGanZhi(), size, color),
              getBzText(daYun.getEndYear().toString(), size, color),
            ],
          ),
        ),
      ),
    );
  }

  //藏干
  Widget getZgan(EightChar eightChar,Lunar lunar){
    //流时
    String lsTg="";
    String lsDz=lunar.getTimeZhi();
    //流日
    String lrTg="";
    String lrDz=lunar.getDayZhi();
    //流月
    String lyTg="";
    String lyDz=lunar.getMonthZhi();
    //流年
    String lnTg="";
    String lnDz=lunar.getYearZhi();
    //大运
    String dyTg="";
    String dyDz=lunar.getYearZhi();

    return   Row(
      children: <Widget>[
        Expanded(
          flex: 1,
          child: Container(
            height: 70, // 设置固定高度为150
            color: Colors.black12, // 设置背景颜色
            child:  Column(
              mainAxisAlignment: MainAxisAlignment.center, // 将子部件垂直居中
              children: <Widget>[
                getSmallTitle("藏干"),
              ],
            ),),
        ),
        Expanded(
          flex: 1,

          child: Column(
            children: <Widget>[
              for(var gan in dizhiMap[lsDz]!)
                Text(ganzhiMap[eightChar.getDayGan()+gan]??"", style: TextStyle(fontSize: 11, color: Colors.black),),
              Text.rich(
                  TextSpan(
                    style: TextStyle(fontSize: 11, color: Colors.black),
                    children: [
                      for(var gan in dizhiMap[lsDz]!)
                        TextSpan(text: gan, style: TextStyle(fontSize: 11,color:hourColorMap[gan])),
                    ],
                  )
              ),
            ],
          ),
        ),
        Expanded(
          flex: 1,
          child: Container(
            height: 70, // 设置固定高度为150
            color: Colors.black12, // 设置背景颜色
            child:  Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                for(var gan in dizhiMap[lrDz]!)
                  Text(ganzhiMap[eightChar.getDayGan()+gan]??"", style: TextStyle(fontSize: 11, color: Colors.black),),
                Text.rich(
                    TextSpan(
                      style: TextStyle(fontSize: 11, color: Colors.black),
                      children: [
                        for(var gan in dizhiMap[lrDz]!)
                          TextSpan(text: gan, style: TextStyle(fontSize: 11,color:hourColorMap[gan])),
                      ],
                    )
                ),
              ],
            ),),
        ),
        Expanded(
          child: Column(
            children: <Widget>[
              for(var gan in dizhiMap[lyDz]!)
                Text(ganzhiMap[eightChar.getDayGan()+gan]??"", style: TextStyle(fontSize: 11, color: Colors.black),),
              Text.rich(
                  TextSpan(
                    style: TextStyle(fontSize: 11, color: Colors.black),
                    children: [
                      for(var gan in dizhiMap[lyDz]!)
                        TextSpan(text: gan, style: TextStyle(fontSize: 11,color:hourColorMap[gan])),
                    ],
                  )
              ),
            ],
          ),
        ),
        Expanded(
          child: Column(
            children: <Widget>[
              for(var gan in dizhiMap[lnDz]!)
                Text(ganzhiMap[eightChar.getDayGan()+gan]??"", style: TextStyle(fontSize: 11, color: Colors.black),),
               Text.rich(
                  TextSpan(
                    style: TextStyle(fontSize: 11, color: Colors.black),
                    children: [
                      for(var gan in dizhiMap[lnDz]!)
                        TextSpan(text: gan, style: TextStyle(fontSize: 11,color:hourColorMap[gan])),
                    ],
                  )
              ),
            ],
          ),
        ),
        Expanded(
          child: Column(
            children: <Widget>[
              for(var gan in dizhiMap[dyDz]!)
                Text(ganzhiMap[eightChar.getDayGan()+gan]??"", style: TextStyle(fontSize: 11, color: Colors.black),),
              Text.rich(
                  TextSpan(
                    style: TextStyle(fontSize: 11, color: Colors.black),
                    children: [
                      for(var gan in dizhiMap[dyDz]!)
                        TextSpan(text: gan, style: TextStyle(fontSize: 11,color:hourColorMap[gan])),
                    ],
                  )
              ),
            ],
          ),
        ),
        SizedBox(
          height: 0,
          child: VerticalDivider(),
        ),
        Expanded(
          child: Column(
            children: <Widget>[
              for(var gan in dizhiMap[eightChar.getYearZhi()]!)
                Text(ganzhiMap[eightChar.getDayGan()+gan]??"", style: TextStyle(fontSize: 11, color: Colors.black),),
              Text.rich(
                  TextSpan(
                    style: TextStyle(fontSize: 11, color: Colors.black),
                    children: [
                      for(var gan in dizhiMap[eightChar.getYearZhi()]!)
                        TextSpan(text: gan, style: TextStyle(fontSize: 11,color:hourColorMap[gan])),
                    ],
                  )
              ),
            ],
          ),
        ),
        Expanded(
          child: Column(
            children: <Widget>[
              for(var gan in dizhiMap[eightChar.getMonthZhi()]!)
                Text(ganzhiMap[eightChar.getDayGan()+gan]??"", style: TextStyle(fontSize: 11, color: Colors.black),),
              Text.rich(
                  TextSpan(
                    style: TextStyle(fontSize: 11, color: Colors.black),
                    children: [
                      for(var gan in dizhiMap[eightChar.getMonthZhi()]!)
                        TextSpan(text: gan, style: TextStyle(fontSize: 11,color:hourColorMap[gan])),
                    ],
                  )
              ),
            ],
          ),
        ),
        Expanded(
          child: Column(
            children: <Widget>[
              for(var gan in dizhiMap[eightChar.getDayZhi()]!)
                Text(ganzhiMap[eightChar.getDayGan()+gan]??"", style: TextStyle(fontSize: 11, color: Colors.black),),
              Text.rich(
                  TextSpan(
                    style: TextStyle(fontSize: 11, color: Colors.black),
                    children: [
                      for(var gan in dizhiMap[eightChar.getDayZhi()]!)
                        TextSpan(text: gan, style: TextStyle(fontSize: 11,color:hourColorMap[gan])),
                    ],
                  )
              ),
            ],

          ),),
        Expanded(
          child: Column(
            children: <Widget>[
              for(var gan in dizhiMap[eightChar.getTimeZhi()]!)
                Text(ganzhiMap[eightChar.getDayGan()+gan]??"", style: TextStyle(fontSize: 11, color: Colors.black),),
              Text.rich(
                  TextSpan(
                    style: TextStyle(fontSize: 11, color: Colors.black),
                    children: [
                      for(var gan in dizhiMap[eightChar.getTimeZhi()]!)
                        TextSpan(text: gan, style: TextStyle(fontSize: 11,color:hourColorMap[gan])),
                    ],
                  )
              ),
            ],
          ),
        ),
      ],
    );

  }

  Widget getTb(){
    return Expanded(child: ListView(
      children: [
        // 数据行
        Row(
          children: [
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('John Doe'),
                ),
              ),
            ),
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('John Doe'),
                ),
              ),
            ),
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('John Doe'),
                ),
              ),
            ),
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('John Doe'),
                ),
              ),
            ),
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('John Doe'),
                ),
              ),
            ),
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('30'),
                ),
              ),
            ),
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('John Doe'),
                ),
              ),
            ),
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('30'),
                ),
              ),
            ),
          ],
        ),
        Row(
          children: [
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('John Doe'),
                ),
              ),
            ),
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('John Doe'),
                ),
              ),
            ),
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('John Doe'),
                ),
              ),
            ),
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('John Doe'),
                ),
              ),
            ),
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('John Doe'),
                ),
              ),
            ),
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('30'),
                ),
              ),
            ),
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('John Doe'),
                ),
              ),
            ),
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('30'),
                ),
              ),
            ),
          ],
        ),
        Row(
          children: [
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('John Doe'),
                ),
              ),
            ),
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('John Doe'),
                ),
              ),
            ),
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('John Doe'),
                ),
              ),
            ),
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('John Doe'),
                ),
              ),
            ),
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('John Doe'),
                ),
              ),
            ),
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('30'),
                ),
              ),
            ),
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('John Doe'),
                ),
              ),
            ),
            Expanded(
              child: Container(
                decoration: BoxDecoration(
                  border: Border(
                    right: BorderSide(color: Colors.black, width: 1),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text('30'),
                ),
              ),
            ),
          ],
        ),

        // 可以继续添加更多数据行...
      ],
    ),);
  }

  Widget getSmallTitle(String title){
    return Text(title,style: TextStyle(fontSize: 10,color: Colors.grey),);
  }

  Widget getBzText(String title,double fontSize,Color color ){
    return Text( title,style: TextStyle(fontSize: fontSize,color:hourColorMap[title] ?? color),);
  }

  Widget getSSText(String title){
    return Text( title,style: TextStyle(fontSize: 10,color:hourColorMap[title] ?? Colors.black87),);
  }

  Widget getZGText(String dizhi,String shishen){
    return Text.rich(
        TextSpan(
          style: TextStyle(fontSize: 11, color: Colors.black),
          children: [
            TextSpan(text: dizhi, style: TextStyle(fontSize: 11,color:hourColorMap[dizhi])),
            TextSpan(text: " "),
            TextSpan(text: shishen, style: TextStyle(fontSize: 11,color:Colors.black87)),
          ],
        )
    );
  }


}
