
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:lunar/calendar/eightchar/DaYun.dart';
import 'package:lunar/lunar.dart';
import 'package:minyun/constant.dart';
import 'package:minyun/models/SplayedFigureFindModel.dart';
import 'package:minyun/models/SplayedFigureModel.dart';
import 'package:minyun/models/lucky_model.dart';
import 'package:minyun/models/lucky_year_model.dart';
import 'package:mongol/mongol.dart';


class SplayedFigureDetailPaipanScreen extends StatefulWidget {
  final SplayedFigureFindModel search;
  final EightChar eightChar;

  final SplayedFigureModel splayedFigureModel;
  SplayedFigureDetailPaipanScreen({required this.search,required this.eightChar, required this.splayedFigureModel});

  @override
  State<SplayedFigureDetailPaipanScreen> createState() => _SplayedFigureDetailPaipanScreenState();
}

class _SplayedFigureDetailPaipanScreenState extends State<SplayedFigureDetailPaipanScreen>  with TickerProviderStateMixin {

  Lunar nowLunar = Solar.fromDate(DateTime.now()).getLunar();
  Lunar luckyLunar = Solar.fromDate(DateTime.now()).getLunar();

  //获取当前年
  DateTime now = DateTime.now();
  int currentYear =DateTime.now().year;
  int currentMonth = DateTime.now().month;
  int currentDay= DateTime.now().day;
  int currentHour= DateTime.now().hour;

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
    _refreshApiData();
    _refreshSdkData();
  }

  @override
  void dispose() {
    super.dispose();
  }
  Future<void> _refreshSdkData() async {
// 今日八字甲戌 丁卯 戊申 戊午
//     var birthSolor = Solar.fromYmdHms(widget.search.year!,widget.search.month!,widget.search.day!,widget.search.hour!,widget.search.minute ?? 0,0);
//     var birthLunar = birthSolor.getLunar();
    int sex=1;
    if('0' == widget.search.sex ){
      sex=0;
    }

    setState(() {
      Yun yun = widget.eightChar.getYun(sex);
      daYunList=yun.getDaYun();
      for(var daYun in daYunList){
        LuckyYearModel luckyYear=LuckyYearModel();
        if( daYun.getStartYear() <= currentYear && currentYear <= daYun.getEndYear() ){
          luckDaYunIndex=daYun.getIndex();
        }
        luckyYear.year=daYun.getStartYear();
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
        String tianganShishen=ganzhiMap[widget.eightChar.getDayGan()+luckyYear.gan!]??"";
        luckyYear.ganShishenIntro=tenGods[tianganShishen];
        var oneZG=dizhiMap[luckyYear.zhi]![0];
        var dizhiShishen= ganzhiMap[widget.eightChar.getDayGan()+oneZG]??"";
        luckyYear.zhiShishenIntro=tenGods[dizhiShishen];
        luckyYear.twelveGod=twelveGods[widget.eightChar.getDayGan()+luckyYear.zhi!];
        luckyYear.number=daYun.getIndex();
        bigLuckyList.add(luckyYear);
      }
    });
  }
  Future<void> _refreshApiData() async {


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
        nowDate(nowLunar.getSolar(),nowLunar),
        getBaziTitle("标注","流时","流日","流月","流年","大运","年柱","月柱","日柱","时柱",10,Colors.black87),
        getBaziShishen(),
        getBaziTitle("天干",nowLunar.getTimeGan(),nowLunar.getDayGan(),nowLunar.getMonthGan(),nowLunar.getYearGan(),luckyLunar.getYearGan(),
            widget.eightChar.getYearGan(),widget.eightChar.getMonthGan(), widget.eightChar.getDayGan(), widget.eightChar.getTimeGan(),
            20,Colors.black87
        ),
        getBaziTitle("地支",nowLunar.getTimeZhi(),nowLunar.getDayZhi(),nowLunar.getMonthZhi(),nowLunar.getYearZhi(),luckyLunar.getYearZhi(),
            widget.eightChar.getYearZhi(),widget.eightChar.getMonthZhi(), widget.eightChar.getDayZhi(), widget.eightChar.getTimeZhi(),
            20,Colors.black87
        ),
        getZgan(),
        getTable( "星运",xyMap[nowLunar.getTimeZhi()]??"",xyMap[nowLunar.getDayZhi()]??"",xyMap[nowLunar.getMonthZhi()]??"",xyMap[nowLunar.getYearZhi()]??"",xyMap[luckyLunar.getYearZhi()]??"",
            xyMap[widget.eightChar.getYearZhi()]??"",xyMap[widget.eightChar.getMonthZhi()]??"",xyMap[widget.eightChar.getDayZhi()]??"",xyMap[widget.eightChar.getTimeZhi()]??"",
            10,Colors.black87
        ),
        getTable( "自坐",zzMap[nowLunar.getTimeGan()+nowLunar.getTimeZhi()]??"",zzMap[nowLunar.getDayGan()+nowLunar.getDayZhi()]??"",zzMap[nowLunar.getMonthGan()+nowLunar.getMonthZhi()]??"",zzMap[nowLunar.getYearGan()+nowLunar.getYearZhi()]??"",zzMap[luckyLunar.getYearGan()+luckyLunar.getYearZhi()]??"",
            zzMap[widget.eightChar.getYearGan()+widget.eightChar.getYearZhi()]??"",zzMap[widget.eightChar.getMonthGan()+widget.eightChar.getMonthZhi()]??"",zzMap[widget.eightChar.getDayGan()+widget.eightChar.getDayZhi()]??"",zzMap[widget.eightChar.getTimeGan()+widget.eightChar.getTimeZhi()]??"",
            10,Colors.black87
        ),
        getTable( "空亡",nowLunar.getTimeXunKong(),nowLunar.getDayXunKong(),nowLunar.getMonthXunKong(),nowLunar.getYearXunKong(),luckyLunar.getYearXunKong(),
            widget.eightChar.getYearXunKong(),widget.eightChar.getMonthXunKong(),widget.eightChar.getDayXunKong(),widget.eightChar.getTimeXunKong(),
            10,Colors.black87
        ),
        getTableNode( "纳音",nowLunar.getTimeNaYin(),nowLunar.getDayNaYin(),nowLunar.getMonthNaYin(),nowLunar.getYearNaYin(),luckyLunar.getYearNaYin(),
            widget.eightChar.getYearNaYin(),widget.eightChar.getMonthNaYin(),widget.eightChar.getDayNaYin(),widget.eightChar.getTimeNaYin(),
            10,Colors.black87
        ),
        Divider(),
        getShensa(widget.eightChar,10,Colors.black87,ssMap),
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

  Widget getBaziShishen(
      ){
    // "十神",ganzhiMap[widget.eightChar.getDayGan()+lunar.getTimeGan()]??"",
    String title="十神";
    String v1=ganzhiMap[widget.eightChar.getDayGan()+nowLunar.getTimeGan()]??"";
    String v2=ganzhiMap[widget.eightChar.getDayGan()+nowLunar.getDayGan()]??"";
    String v3=ganzhiMap[widget.eightChar.getDayGan()+nowLunar.getMonthGan()]??"";
    String v4=ganzhiMap[widget.eightChar.getDayGan()+nowLunar.getYearGan()]??"";
    String v5=ganzhiMap[widget.eightChar.getDayGan()+luckyLunar.getYearGan()]??"";
    String bz1=widget.eightChar.getYearShiShenGan();
    String bz2=widget.eightChar.getMonthShiShenGan();
    String bz3=widget.eightChar.getDayShiShenGan();
    String bz4=widget.eightChar.getTimeShiShenGan();
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
  Widget getShensa(EightChar eightChar,double size,Color color,Map<String,List<dynamic>> ssMap){
    int max=1;
    List<dynamic> timeSSList = ssMap[nowLunar.getTimeGan()+nowLunar.getTimeZhi()]??[];
    if(timeSSList.length>max){
      max=timeSSList.length;
    }
    List<dynamic> daySSList = ssMap[nowLunar.getDayGan()+nowLunar.getDayZhi()]??[];
    if(daySSList.length>max){
      max=daySSList.length;
    }
    List<dynamic> monthSSList = ssMap[nowLunar.getMonthGan()+nowLunar.getMonthZhi()]??[];
    if(monthSSList.length>max){
      max=monthSSList.length;
    }
    List<dynamic> yearSSList = ssMap[nowLunar.getYearGan()+nowLunar.getYearZhi()]??[];
    if(yearSSList.length>max){
      max=yearSSList.length;
    }
    List<dynamic> luckySSList = ssMap[luckyLunar.getYearGan()+luckyLunar.getYearZhi()]??[];
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
          flex: 1,
          child: Column(
            children: <Widget>[
              getSmallTitle("鸿运"),
            ],
          ),
        ),
        Expanded(
          flex: 21,
          child: Row(
            children: <Widget>[
              for(int i=0;i<bigLuckyList.length;i++)
               getBigLuckyCell(bigLuckyList[i],i),

            ],
          ),
        ),
      ],
    );

  }

  Widget getBigLuckyCell(LuckyYearModel lcky,int number) {
    Border border = Border(
      left: BorderSide(
          width: 1.0,
          color: Colors.black
      ), // 左边框宽度和颜色
    );
    return Expanded(
      child: GestureDetector(
        onTap: () {
          setState(() {
            luckDaYunIndex=number;
            currentYear=lcky.year!;
            currentMonth=1;
            currentDay=1;
            currentHour=0;
            nowLunar = Solar.fromYmdHms(currentYear,currentMonth,currentDay,currentHour,11,11).getLunar();
            luckyLunar = Solar.fromYmdHms(currentYear,1,1,1,00,11).getLunar();
          });
          // 处理点击事件
        },
        child: Container(
          decoration: BoxDecoration(
            color: number == luckDaYunIndex ? Colors.black12 : Colors.white, // 设置背景颜色
            border: border, // 应用左边框样式
          ),
          // color: Colors.grey.withOpacity(0.1), // 设置背景颜色
          child: Column(
            children: <Widget>[
              getBzText(lcky.year!.toString(), 9, Colors.black),
              getBzText(lcky.age.toString()+"岁" ?? "", 10, Colors.black),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  getBzText(lcky.gan ?? "", 12, Colors.black),
                  getBzText(lcky.ganShishenIntro ?? "", 8, Colors.orange),
                ],
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
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
      luckyYear.year=year.getYear();
      String ganzhi=year.getGanZhi();
      luckyYear.gan = ganzhi[0];
      luckyYear.zhi = ganzhi[1];
      String tianganShishen=ganzhiMap[widget.eightChar.getDayGan()+luckyYear.gan!]??"";
      luckyYear.ganShishenIntro=tenGods[tianganShishen];
      var oneZG=dizhiMap[luckyYear.zhi]![0];
      var dizhiShishen= ganzhiMap[widget.eightChar.getDayGan()+oneZG]??"";
      luckyYear.zhiShishenIntro=tenGods[dizhiShishen];
      luckyYear.twelveGod=twelveGods[widget.eightChar.getDayGan()+luckyYear.zhi!];
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
              for(var i=0;i<nowBigLuckyList.length;i++)
                getNowYearCell(nowBigLuckyList[i],i),
            ],
          ),
        ),
      ],
    );

  }

  Widget getNowYearCell(LuckyYearModel lcky,int number) {
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
          setState(() {
            currentYear=lcky.year!;
            currentMonth=1;
            currentDay=1;
            currentHour=0;
            nowLunar = Solar.fromYmdHms(currentYear,currentMonth,currentDay,currentHour,11,11).getLunar();
          });
        },
        child: Container(
          decoration: BoxDecoration(
            color: lcky.year! == currentYear ? Colors.black12 : Colors.white, // 设置背景颜色
            border: border, // 应用左边框样式
          ),
          // color: Colors.grey.withOpacity(0.1), // 设置背景颜色
          child: Column(
            children: <Widget>[
              getBzText(lcky.year!.toString(), 9, Colors.black),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  getBzText(lcky.gan ?? "", 12, Colors.black),
                  getBzText(lcky.ganShishenIntro ?? "", 8, Colors.orange),
                ],
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
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
      luckyDate.date=i;
      // luckyDate.solarTermsDay=
      luckyDate.gan=l.getMonthGan();
      luckyDate.zhi=l.getMonthZhi();
      String tianganShishen=ganzhiMap[widget.eightChar.getDayGan()+luckyDate.gan!]??"";
      luckyDate.ganShishenIntro=tenGods[tianganShishen];
      var oneZG=dizhiMap[luckyDate.zhi]![0];
      var dizhiShishen= ganzhiMap[widget.eightChar.getDayGan()+oneZG]??"";
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
          setState(() {
            currentMonth=luckyModel.date!;
            currentDay=1;
            currentHour=0;
            nowLunar = Solar.fromYmdHms(currentYear,currentMonth,currentDay,currentHour,11,11).getLunar();
          });
        },
        child: Container(
          decoration: BoxDecoration(
            color: luckyModel.date! == currentMonth ? Colors.black12 : Colors.white, // 设置背景颜色
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
  //流日
  Widget getNowDay(int year,int month,int day,double size,Color color){
    int max=5;
    double hight=max*15+50;
    int days=getDaysInMonth(year,month);
    List<LuckyDateModel> LuckyDateList=[];
    for(var i=0;i<days;i++){
      Solar d = Solar.fromYmd(year,month,i+1);
      Lunar l = d.getLunar();
      LuckyDateModel luckyDate=new LuckyDateModel();
      luckyDate.date=(i+1);
      luckyDate.chinaDay=l.getDayInChinese();
      luckyDate.gan=l.getDayGan();
      luckyDate.zhi=l.getDayZhi();
      String tianganShishen=ganzhiMap[widget.eightChar.getDayGan()+luckyDate.gan!]??"";
      luckyDate.ganShishenIntro=tenGods[tianganShishen];
      var oneZG=dizhiMap[luckyDate.zhi]![0];
      var dizhiShishen= ganzhiMap[widget.eightChar.getDayGan()+oneZG]??"";
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
          setState(() {
            currentDay=luckyModel.date!;
            currentHour=0;
            nowLunar = Solar.fromYmdHms(currentYear,currentMonth,currentDay,currentHour,11,11).getLunar();
          });
        },
        child: Container(
          decoration: BoxDecoration(
            color: luckyModel.date! == currentDay ? Colors.black12 : Colors.white, // 设置背景颜色
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
              getBzText(luckyModel.date!.toString(), size, color),
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

  //流时
  Widget getNowHour(int year,int month,int day,int hour,double size,Color color){
    int max=4;
    double hight=max*10+50;
    List<LuckyDateModel> LuckyDateList=[];
    Solar d = Solar.fromYmd(year,month,day);
    Lunar l = d.getLunar();
    List<LunarTime> timeList= l.getTimes();
    int hourStart=1;
    for(int i=0;i<timeList.length;i++ ){
      var time = timeList[i];
      LuckyDateModel luckyDate=new LuckyDateModel();
      if(i == 0 || i == 1) {
        luckyDate.date = i;
      }else{
        hourStart=hourStart+2;
        luckyDate.date = hourStart;
      }
      luckyDate.chinaDay=time.getMinHm();
      luckyDate.gan=time.getGan();
      luckyDate.zhi=time.getZhi();
      String tianganShishen=ganzhiMap[widget.eightChar.getDayGan()+luckyDate.gan!]??"";
      luckyDate.ganShishenIntro=tenGods[tianganShishen];
      var oneZG=dizhiMap[luckyDate.zhi]![0];
      var dizhiShishen= ganzhiMap[widget.eightChar.getDayGan()+oneZG]??"";
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
          setState(() {
            currentHour=luckyModel.date!;
            nowLunar = Solar.fromYmdHms(currentYear,currentMonth,currentDay,currentHour,11,11).getLunar();
          });
        },
        child: Container(
          color: luckyModel.date == currentHour ? Colors.black12 : Colors.white, // 设置背景颜色
          // color: Colors.grey.withOpacity(0.1), // 设置背景颜色
          child: Column(
            children: <Widget>[
              getBzText(luckyModel.chinaDay??"", size, color),
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
          // setState(() {
          //
          // });
        },
        child: Container(
          height: 210, // 设置固定高度为150
          decoration: BoxDecoration(
            border: border, // 应用左边框样式
          ),
          // color: Colors.grey.withOpacity(0.1), // 设置背景颜色
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center, // 将内容垂直居中
            children: <Widget>[
              getBzText(daYun.getStartYear().toString(), size, color),
              for(var yunYear in daYun.getLiuNian())
                GestureDetector(
                    onTap: () {
                      // 处理点击事件
                      setState(() {
                        currentYear=yunYear.getYear();
                        currentMonth=1;
                        currentDay=1;
                        currentHour=0;
                        nowLunar = Solar.fromYmdHms(currentYear,currentMonth,currentDay,currentHour,11,11).getLunar();
                      });
                    },
                    child: Container(
                      decoration: BoxDecoration(
                        color: yunYear.getYear() == currentYear ? Colors.black12 : Colors.white, // 设置背景颜色
                      ),
                      child: getBzText(yunYear.getGanZhi(), size, color),
                    )),
              getBzText(daYun.getEndYear().toString(), size, color),
            ],
          ),
        ),
      ),
    );
  }

  //藏干
  Widget getZgan(){
    //流时
    String lsDz=nowLunar.getTimeZhi();
    //流日
    String lrDz=nowLunar.getDayZhi();
    //流月
    String lyDz=nowLunar.getMonthZhi();
    //流年
    String lnDz=nowLunar.getYearZhi();
    //大运
    String dyDz=luckyLunar.getYearZhi();

    return  Row(
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
        getZGCell(dizhiMap[lsDz]!,0),
        getZGCell(dizhiMap[lrDz]!,1),
        getZGCell(dizhiMap[lyDz]!,0),
        getZGCell(dizhiMap[lnDz]!,1),
        getZGCell(dizhiMap[dyDz]!,0),
        SizedBox(
          height: 0,
          child: VerticalDivider(),
        ),
        getZGCell(dizhiMap[widget.eightChar.getYearZhi()]!,1),
        getZGCell(dizhiMap[widget.eightChar.getMonthZhi()]!,0),
        getZGCell(dizhiMap[widget.eightChar.getDayZhi()]!,1),
        getZGCell(dizhiMap[widget.eightChar.getTimeZhi()]!,0),
      ],
    );

  }

  /**
   * ssList 十神
   * bj
   */
  Widget getZGCell(List<String> ssList,int bj){
    return Expanded(
      child:Container(
            height: 70, // 设置固定高度为150
            color:bj==1 ? Colors.black12 : Colors.white, // 设置背景颜色
      child: Column(
        children: <Widget>[
          for(var gan in ssList)
            Text(ganzhiMap[widget.eightChar.getDayGan()+gan]??"", style: TextStyle(fontSize: 11, color: (bj==1 ?Colors.black : Colors.grey )),),
          Text.rich(
              TextSpan(
                style: TextStyle(fontSize: 11, color: Colors.black),
                children: [
                  for(var gan in ssList)
                    TextSpan(text: gan, style: TextStyle(fontSize: 11,color:hourColorMap[gan])),
                ],
              )
          ),
        ],
      ),
    ),
    );
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
