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
import 'package:minyun/screens/splayed_figure_detail_analyze_screen.dart';
import 'package:minyun/screens/splayed_figure_detail_intro_screen.dart';
import 'package:minyun/screens/splayed_figure_detail_paipan_screen.dart';
import 'package:mongol/mongol.dart';

import '../utils/color.dart';
import '../utils/common.dart';
import '../utils/images.dart';
import 'splayed_figure_detail_book_screen.dart';

class SplayedFigureDetailScreen extends StatefulWidget {
  final SplayedFigureFindModel search;
  SplayedFigureDetailScreen({required this.search});

  @override
  State<SplayedFigureDetailScreen> createState() => _SplayedFigureDetailScreenState();
}

class _SplayedFigureDetailScreenState extends State<SplayedFigureDetailScreen>  with TickerProviderStateMixin {

  SplayedFigureModel? splayedFigureModel;
  bool isRefreshing = false; // 用于表示是否正在刷新数据
  GlobalKey<RefreshIndicatorState> refreshKey = GlobalKey<RefreshIndicatorState>();
  Lunar lunar = Lunar.fromDate(DateTime.now());
  late final TabController _tabController;
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
    _tabController = TabController(length: 4, vsync: this);
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
    Map<String, String> queryParams=new HashMap();
    queryParams["act"]="ok";
    queryParams["name"]="七";
    queryParams["DateType"]="5";
    queryParams["inputdate"]="公历1994年3月23日 11时";
    // queryParams["ng"]="己卯";
    // queryParams["yg"]="丙寅";
    // queryParams["rg"]="庚寅";
    // queryParams["sg"]="丙子";

    queryParams["sex"]="0";
    queryParams["ztys"]="0";
    // queryParams["city1"]="北京";
    // queryParams["city2"]="北京";
    // queryParams["city3"]="东城区";
    queryParams["Sect"]="1";
    queryParams["Siling"]="0";

    queryParams["leixinggg"]="on";
    queryParams["api"]="1";
    queryParams["bcxx"]="1";


    SplayedFigureModel resultModel = await SplayedFigureApi.getSplayedFigure(queryParams);
    // 刷新完成后，设置isRefreshing为false，并更新albumDataModel
    // "bazi": {//干支信息
    //    "ng": "癸",//年干
    //    "nz": "卯",//年支
    //    "yg": "癸",//月干
    //    "yz": "亥",//月支
    //    "rg": "壬",//日干
    //    "rz": "午",//日支
    //    "sg": "癸",//时干
    //    "sz": "卯",//时支
    //    "tg": "甲",//胎元干
    //    "tz": "寅",//胎元支
    //    "Tg": "丁",//胎息
    //    "Tz": "未",//胎息
    //    "mg": "乙",//命宫
    //    "mz": "卯",//命宫
    //    "Sg": "乙",//身宫
    //    "Sz": "卯"//身宫
    //   },
    debugPrint("八字 ："+ json.encode(resultModel.bz));

    //根据所选日期的天干地支
    debugPrint("空亡 ："+ json.encode(resultModel.dykw));
    //根据所选日期的天干地支
    debugPrint("纳音 ："+ json.encode(resultModel.dyny));
    //根据所选日期的地支
    debugPrint("星运 ："+ json.encode(resultModel.dyws));
    //根据所选日期的天干地支
    debugPrint("自座 ："+ json.encode(resultModel.dyzz));

    /**
     * "shishen": {//十神总表(不论四柱乃至大运流年的干支都从此处获取)
        "甲": {//天干
        "全": "⁢食神",//全词
        "半": "⁢食"//半词
     */
    debugPrint("十神 ："+ json.encode(resultModel.dzss));

    debugPrint("分析 ："+resultModel.fx.toString());

    //"minggua": {//命卦
    //    "zg": "坤卦(西四命)",//分析
    //    "b": "绝命",//方位宜忌北
    //    "db": "生气",//东北
    //    "d": "祸害",//东
    //    "dn": "五鬼",//东南
    //    "n": "六煞",//南
    //    "xn": "伏位",//西南
    //    "x": "天医",//西
    //    "xb": "延年"//西北
    //   },
    debugPrint("命卦 ："+ resultModel.jsfw.toString());


    debugPrint("神煞 ："+ resultModel.llss.toString());

    //[
    //   2024,  时间
    //   1,     多少岁
    //   "小运",  运势
    //   2025,
    //   2,
    //   "戊辰",  //天干地支
    //   2035,
    //   12,
    //   "己巳",
    //   2045,
    //   22,
    //   "庚午",
    //   2055,
    //   32,
    //   "辛未",
    //   2065,
    //   42,
    //   "壬申",
    //   2075,
    //   52,
    //   "癸酉",
    //   2085,
    //   62,
    //   "甲戌",
    //   2095,
    //   72,
    //   "乙亥",
    //   2105,
    //   82,
    //   "丙子"
    // ]
    debugPrint("鸿运/运年 ："+ resultModel.lndy.toString());

    //所有年：天干/地支
    debugPrint("鸿运/运年 ："+ resultModel.lnxy.toString());
    // shensha": {//神煞
    //   "nz": [//年柱神煞
    //
    //   ],
    //   "yz": [//月

    //   ],
    //   "rz": [//日
    //   "天德贵人",//   "黄煞日"
    //   ],
    //   "sz": [//时

    //   ],
    // },
    debugPrint("八字神撒 ："+ resultModel.ss.toString());

    debugPrint("？？？ ："+ resultModel.zGss.toString());

    /**
     * "zh": [//八字统计
        [
        0,//金个数
        2,//木个数
        5,//水
        1,//火
        0//土
        ],
        [
        2,//阳个数
        6//阴个数
        ],
        [
        75,//阴占比
        25//阳占比
        ],
        [
        0,//藏干金个数
        3,//木
        1,//水
        1,//火
        1//土
        ],
        [
        2,//藏干阳个数
        4//阴
        ],
        [
        67,//藏干阴占比
        33//阳
        ],
        "未落空"//六甲空亡是否落空
        ],
        "ZaoShi": "湿",//八字燥湿

        bazishengyue:"惊蛰之时，飞龙在天，利见大人，利路亨通，作事大与，有机可称，正是出头之时，德望成功，终成大业，竟天才成功之守，地位权位受人敬仰，有冲天之势，威镇山河。"
        wanzishiliupai:"按明天"  //晚子时规则
     */
    debugPrint("八字信息 ："+ resultModel.bazixinxi.toString());

    /**
     * cl1 节气与时间
     * cl2 节气与时间
     * dl1 节气与时间
     * dl2 节气与时间
     * hyws 换运尾数
     * qh  一年的节气
     * qy 起运
     * wxws 五行旺衰:
     * gongli 公历
     * nongli 农历
     */
    debugPrint("出生 ："+ resultModel.chusheng.toString());

    /**
     * "qiangruo": [//传统强弱算法
        "得令",//是否得令
        1,//强气根
        0,//中气根
        0,//余气根
        3,//得势
        "强",//强弱
        4,//助我数
        3//克我数
        ],
     */
    debugPrint("用神参考 ："+ resultModel.deling.toString());

    debugPrint("？？？？ ："+ resultModel.fanhui.toString());

    /**
     * {
        "yhbh": null,
        "beizhu": "",
        "name": "张三",
        "xmwx": "张(火)三(金)",
        "sexx": "男",
        "PPFS": "公历排盘",
        "city": "北京 北京 东城区",
        "ztys": "0",   // 1 为启用真太阳时
        "Z_QRQ": "2024年3月31日19时9分",   //生日
        "JYRQ": "2025-07-11",       //交运日期
        "GNF": "2024",            //
        "SiLing": "乙",       ////司令
        "SiLingFangshi": "子平真诠",  //司令规则
        "nylm": "伏潭之龙",     //纳音论命
        "KLFX": "XmY1NDsIgEIVPM2zcQFuQWcwCOOnOBngAbQogGje2i3l4cm5i4e0onme0oPCstH9KkogGDX4HpyEsQNvweIQSOfCB6vB9RxGsKaRTcc6gu9aGA1gD9a2iuMZnD4pPLI3yL360IiSvjMVK6YDuEm8PpQ3kV031T3xPFwH6H500oXcHQCnWuJOcI6k5kxS3mPdcUkp0LOOKyVLKELZIIj0zqDQo0o0",
        "XingZuo": "白羊座",   //星座
        "WuHou": "始电",    //物候
        "Hou": "春分 三候",    //物候
        "getXiu": "星",     // 宿
        "getAnimal": "马",  //生肖
        "getXiuLuck": "凶",  //宿
        "getXiuSong": "星宿日好造新房，进职加官近帝王，不可埋葬并放水，凶星临位女人亡，生离死别无心恋，要自归休别嫁郎，孔子九曲殊难度，放水开门天命伤。",
        "getZheng": "日",     //七政
        "getYueXiang": "下弦",  //月相
        "SCWG": "天格:金(8) 人格:水(10) 地格:火(4) 总格:水(10) 外格:木(1)",  //三才五格
        "shengxiao": "龙"   //生肖
        }
     */
    debugPrint("个人信息:"+ resultModel.system.toString());

    setState(() {
      isRefreshing = false;
      splayedFigureModel=resultModel;
      print("-----------加载数据-----start-------------");
      // Map<String, String> map = Map<String, String>.cast(resultModel.bz);
      bzMap = Map<String, String>.from(resultModel.bz.map(
            (key, value) => MapEntry(key, value.toString()),
      ));
      xyMap = Map<String, String>.from(resultModel.dyws.map(
            (key, value) => MapEntry(key, value.toString()),
      ));
      zzMap = Map<String, String>.from(resultModel.dyzz.map(
            (key, value) => MapEntry(key, value.toString()),
      ));
      resultModel.llss.forEach((key, value) {
        print(value.toString());
        List<dynamic> stringList = value.cast<String>().map((e) => e.toString()).toList();
        ssMap[key] = stringList;
      });
      riTianGan=bzMap!['rg'];
      riDiZhi=bzMap!['rz'];
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

    DaYun nowDaYun=daYunList[luckDaYunIndex];
    return Scaffold(
      appBar: AppBar(
        bottom: TabBar(
          controller: _tabController,
          tabs: const <Widget>[
            Tab(
              text: '基本信息',
              // icon: Icon(Icons.cloud_outlined),
            ),
            Tab(
              text: '八字排盘',
            ),
            Tab(
              text: '分析',
            ),
            Tab(
              text: '古籍',
            ),
          ],
        ),
        title: Text("排盘详细"),
        titleTextStyle: boldTextStyle(fontSize: 24),
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
                    ) :
                    SplayedFigureDetailIntroScreen(
                      search: widget.search,
                      splayedFigureModel: splayedFigureModel!, lunar: lunar, eightChar: eightChar!,
                    ),
                    // 这里添加条件判断
                    splayedFigureModel == null ?
                    Center(
                      child: CircularProgressIndicator(), // 加载中的指示器
                    ) :
                    SplayedFigureDetailPaipanScreen(
                      search: widget.search,
                      splayedFigureModel: splayedFigureModel!,
                    ),
                    splayedFigureModel == null ?
                    Center(
                      child: Center(child: Text("加载中"),), // 加载中的指示器
                    ) :
                    SplayedFigureDetailAnalyzeScreen(
                      search: widget.search,
                      fx: splayedFigureModel!.fx!,
                    ),
                    splayedFigureModel == null ?
                    Center(
                      child: Center(child: Text("加载中"),), // 加载中的指示器
                    ) :
                    SplayedFigureDetailBookScreen(
                      search: widget.search,
                      fx: splayedFigureModel!.fx!,
                    ),
                  ],
                ),
              ))
        ],
      ),
    );
  }

}
