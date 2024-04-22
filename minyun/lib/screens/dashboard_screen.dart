import 'dart:io';

import 'package:bruno/bruno.dart';
import 'package:flutter/material.dart' hide primaryColor;
import 'package:flutter_lunar_datetime_picker/date_init.dart';
import 'package:flutter_lunar_datetime_picker/flutter_lunar_datetime_picker.dart';
import 'package:flutter_mobx/flutter_mobx.dart';
import 'package:intl/intl.dart';
import 'package:lunar/lunar.dart';
import 'package:minyun/constant.dart';
import 'package:minyun/models/hour_model_class.dart';
import 'package:minyun/models/lucky_day_model_class.dart';
import 'package:minyun/screens/account_screen.dart';
import 'package:minyun/screens/lucky_day_screen.dart';
import 'package:minyun/utils/AppColors.dart';
import 'package:minyun/utils/AppContents.dart';
import 'package:mongol/mongol.dart';
import 'package:nb_utils/nb_utils.dart';

import '../component/new_file_component.dart';
import '../models/dashboard_model_class.dart';
import '../utils/images.dart';

class DashBoardScreen extends StatefulWidget {
  const DashBoardScreen({Key? key}) : super(key: key);

  @override
  State<DashBoardScreen> createState() => _DashBoardScreenState();
}

class _DashBoardScreenState extends State<DashBoardScreen> {
  bool obText = false;
  List<int> items = List.generate(dashboardIconsList.length, (index) => index);
  /// 日期
  String? time = '1995-11-8 12:12';
  DateFormat formatter = DateFormat("yyyy-MM-dd Hh:mm");
  /// 是否是农历
  bool isLunar = true;

  Lunar lunar = Lunar.fromDate(DateTime.now());
  Solar solar = Solar.fromDate(DateTime.now());


  File? image;
  File? _scannedImage;

  openCameraImageScanner(BuildContext context) async {
    // image = await DocumentScannerFlutter.launch(context,
    //     source: ScannerFileSource.CAMERA,
    //     labelsConfig: {ScannerLabelsConfig.ANDROID_NEXT_BUTTON_LABEL: "Next", ScannerLabelsConfig.ANDROID_SAVE_BUTTON_LABEL: "Save"});
    if (image != null) {
      _scannedImage = image;

      NewFileBottomSheet(context: context, scannedImage: _scannedImage!).then((_) => setState(() {}));
    }
  }

  openImageScanner(BuildContext context) async {
    // image = await DocumentScannerFlutter.launch(context,
    //     source: ScannerFileSource.GALLERY,
    //     labelsConfig: {ScannerLabelsConfig.ANDROID_NEXT_BUTTON_LABEL: "Next", ScannerLabelsConfig.ANDROID_OK_LABEL: "OK"});
    if (image != null) {
      _scannedImage = image;
      NewFileBottomSheet(context: context, scannedImage: _scannedImage!).then((_) => setState(() {}));
    }
  }

  @override
  Widget build(BuildContext context) {
    final height = MediaQuery.of(context).size.height;
    final width = MediaQuery.of(context).size.width;
    bool statusBarColor = false;

    return Observer(
      builder: (context) {
        return Scaffold(
          appBar: AppBar(
            title: Text("万年历"),
            // titleTextStyle: boldTextStyle(fontSize: 24),
            elevation: 0,
            titleSpacing: 0,
            leading: Padding(
              padding: EdgeInsets.all(12.0),
              child: Image.asset(splash_screen_image, color: primaryColor),
            ),
          ),
          body: Stack(
            fit: StackFit.expand,
            children: [
              SingleChildScrollView(
                padding: EdgeInsets.only(bottom: 80),
                child: Column(
                  children: [
                    SizedBox(height: 16),
                    BrnNoticeBar(
                      content: '今日运势：龙年大吉',
                      noticeStyle: NoticeStyles.runningWithArrow,
                      onNoticeTap: () {
                        BrnToast.show('点击通知', context);
                      },
                      onRightIconTap: () {
                        BrnToast.show('点击右侧图标', context);
                      },
                    ),
                    SizedBox(height: 16),
                    Wrap(
                      crossAxisAlignment: WrapCrossAlignment.start,
                      spacing: 8,
                      runSpacing: 8,
                      children: items
                          .map(
                            (index) => Column(
                          children: [
                            GestureDetector(
                                onTap: () {
                                  LuckyDayScreen(search :luckyDayList[index].search!).launch(context);
                                },
                                child:
                                BrnTagCustom(
                                    tagText: luckyDayList[index].title.toString(),
                                    fontSize: 18,
                                    textPadding: EdgeInsets.all(6),
                                    backgroundColor:Colors.amber
                                )
                            )
                          ],
                        ),
                      )
                          .toList(),
                    ),
                    SizedBox(height: 24),
                    lunarDate(lunar,solar),
                    SizedBox(height: 24),
                    nowDate(lunar,solar),
                    SizedBox(height: 24),
                    zhinengzheri(lunar),
                    SizedBox(height: 24),
                    nowYearMonthDay(lunar),
                    SizedBox(height: 24),
                    getYJ(lunar),
                    SizedBox(height: 24),
                    getZRJX(lunar),
                    SizedBox(height: 24),
                    getOhterFive(lunar),
                    SizedBox(height: 24),
                    jxTitle(),
                    getJX(lunar),
                    Divider(
                      thickness: 2.0, // 分割线厚度
                      height: 20.0, // 分割线高度（垂直分割线时使用）
                      // indent: 20.0, // 分割线起始缩进
                      // endIndent: 20.0, // 分割线结束缩进
                      color: Colors.red, // 分割线颜色
                    ),
                    // SizedBox(height: 24),
                    getOther(lunar),
                    Padding(
                      padding: const EdgeInsets.symmetric(horizontal: 16),
                      child: Divider(height: 0),
                    ),
                    hourTitle(),
                    ListView.builder(
                      itemCount: hourList.length,
                      primary: false,
                      padding: EdgeInsets.symmetric(horizontal: 16),
                      shrinkWrap: true,
                      itemBuilder: (context, int index) {
                        HourModel hour= hourList[index];
                        LunarTime lunarTime = LunarTime.fromYmdHms(lunar.getYear(), lunar.getMonth(), lunar.getDay(), hour.sj, 30, 0);
                        return Container(
                          margin: EdgeInsets.symmetric(vertical: 8),
                          padding: EdgeInsets.all(16),
                          decoration: BoxDecoration(
                              color: mode.theme ? darkPrimaryLightColor : Colors.grey.shade200, borderRadius: BorderRadius.circular(DEFAULT_RADIUS)),
                          child: Row(
                            children: [
                              Expanded(
                                flex: 1,
                                child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    Text(
                                      // dashboardFilesList[index].titleText.toString(),
                                      hour.title.toString(),
                                      // style: primaryTextStyle(),
                                      overflow: TextOverflow.fade,
                                    ),
                                    SizedBox(height: 16),
                                    CircleBackgroundText(lunarTime.getTianShenLuck(),lunarTime.getTianShenLuck() == '吉'?Colors.yellow :Colors.red,30)
                                  ],
                                ),
                              ),

                              SizedBox(width: 16),
                              Expanded(
                                flex: 7,
                                child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    // SizedBox(height: 16),
                                    Text("${lunarTime.getMinHm() + '-' + lunarTime.getMaxHm()}  时冲${lunarTime.getChongDesc()} 煞${lunarTime.getSha()}", style: TextStyle(fontSize:   14,color: Colors.black45)),
                                    Text("财神${lunarTime.getPositionCaiDesc()} 喜神${lunarTime.getPositionXiDesc()}  福神${lunarTime.getPositionFuDesc()} 阳神${lunarTime.getPositionYangGuiDesc()} 阴神${lunarTime.getPositionYinGuiDesc()}", style: TextStyle(fontSize:   12,color: Colors.black45)),
                                    getTimeYJ(lunarTime),
                                  ],
                                ),
                              ),
                            ],
                          ),
                        );
                      },
                    ),
                  ],
                ),
              ),
            ],
          ),
        );
      },
    );
  }

  Widget lunarDate(Lunar lunar,Solar solar){
    String time=solar.toYmdHms();
    return Center(
        child: Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            Text(
              "日期:$time",
              style: const TextStyle(fontSize: 20),
            ),
            // const SizedBox(height: 100),
            ElevatedButton(
              onPressed: () {
                DatePicker.showDatePicker(
                  context,
                  lunarPicker: isLunar,
                  dateInitTime: time == null
                      ? DateInitTime(
                      currentTime: DateTime.now(),
                      maxTime: DateTime(2100, 12, 12),
                      minTime: DateTime(1800, 2, 4))
                      : DateInitTime(
                      currentTime:
                      DateFormat("yyyy-MM-dd h:m").parse(time ?? ""),
                      maxTime: DateTime(2100, 12, 12),
                      minTime: DateTime(1800, 1, 1)),
                  onConfirm: (time, lunar) {
                    debugPrint("onConfirm:${time.toString()} ${lunar.toString()}");
                    setState(() {
                      this.time =
                      "${time.year}-${time.month}-${time.day} ${time.hour}:${time.minute}";
                      // DateTime dateTime = formatter.parse(time.toString());
                      //isLunar=
                      if(isLunar){
                        Lunar lunar = Lunar.fromDate(time);
                        this.lunar=lunar;
                        this.solar = lunar.getSolar();
                      }else{
                        Solar solar = Solar.fromDate(time);
                        this.solar=solar;
                        this.lunar = solar.getLunar();
                      }
                      this.isLunar = isLunar;
                    });
                  },
                  onChanged: (time, lunar) {
                    // setState(() {
                    // this.lunar=lunar;
                    // this.solar=
                    // });
                    debugPrint("onChanged:${time.toString()} ${lunar.toString()}");
                  },
                );
              },
              child: const Text("选择"),
            )
          ],
        ));
  }

  Widget nowDate(Lunar lunar, Solar solar) {
    return Row(
      mainAxisSize: MainAxisSize.min,
      children: [
        Expanded(
          flex: 1,
          child: Center( // 使用 Center 组件来居中内容
            child: MongolText("星期" + lunar.getWeekInChinese()),
          ),
        ),
        Expanded(
          flex: 5,
          child: nowDateDay(lunar, solar),
        ),
        // MongolText(solar.getXingZuo() + "座"),
        Expanded(
          flex: 1,
          child: Center( // 使用 Center 组件来居中内容
            child: MongolText(solar.getXingZuo() + "座"),
          ),
        ),
      ],
    );
  }
  Widget nowDateDay(Lunar lunar,Solar solar){
    return  Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        Text("公历"+solar.getYear().toString()+"年"+solar.getMonth().toString()+"月",style: TextStyle(fontSize: 15)),
        Row(
          mainAxisSize: MainAxisSize.min, // 允许Row根据内容大小来收缩
          children: [
            Flexible(
              flex: 1, // 这里分配3份的空间，相对于前一个和后一个元素更大
              child:  IconButton(
                iconSize: 20,
                icon: const Icon(Icons.chevron_left,size: 30),
                onPressed: () {
                  setState(() {
                    this.solar=solar.next(-1);
                    this.lunar =  this.solar.getLunar();
                    this.time =
                    "${solar.getYear()}-${solar.getMonth()}-${solar.getDay()} ${solar.getHour()}:${solar.getMinute()}";
                  });
                },
              ),),
            Expanded(
              flex: 5, // 这里分配3份的空间，相对于前一个和后一个元素更大
              child:   Center( // 使用Center来确保内容垂直居中
                child: Text(
                  solar.getDay().toString(),
                  style: TextStyle(fontSize: 100, color: Colors.red),
                ),
              ),
              // Text(
              //   solar.getDay().toString(),
              //   style: TextStyle(fontSize: 100,color: Colors.red),
              // ),
            ),
            Flexible(
              flex: 1, // 这里分配3份的空间，相对于前一个和后一个元素更大
              child:    IconButton(
                iconSize: 20,
                icon: const Icon(Icons.chevron_right,size: 30,),
                onPressed: () {
                  setState(() {
                    this.solar=solar.next(1);
                    this.lunar =  this.solar.getLunar();
                    this.time =
                    "${solar.getYear()}-${solar.getMonth()}-${solar.getDay()} ${solar.getHour()}:${solar.getMinute()}";
                  });
                },
              ),),
          ],
        ),
        Text("农历 "+lunar.getYearInChinese()+" "+lunar.getMonthInChinese()+"月 "+lunar.getDayInChinese()+"",style: TextStyle(fontSize: 15),),
      ],
    );
  }

  Widget nowYearMonthDay(Lunar lunar){
    return  Row(
      mainAxisAlignment: MainAxisAlignment.center, // 水平居中
      crossAxisAlignment: CrossAxisAlignment.center, // 垂直居中
      children: [
        Expanded(child:SizedBox(),),

        Expanded(child: Center(  child:  MongolText.rich(
          getMongolText("年",lunar.getYearGan(),lunar.getYearZhi()),
          textScaleFactor: 2.5,
        ),),),

        Expanded(child:Center(  child:MongolText.rich(
          getMongolText("月",lunar.getMonthGan(),lunar.getMonthZhi()),
          textScaleFactor: 2.5,
        ),),),
        Expanded(child:Center(  child:MongolText.rich(
          getMongolText("日",lunar.getDayGan(),lunar.getDayZhi()),
          textScaleFactor: 2.5,
        ),),),
        Expanded(child:Center(  child:MongolText.rich(
          getMongolText("时",lunar.getTimeGan(),lunar.getTimeZhi()),
          textScaleFactor: 2.5,
        ),),),
        Expanded(child:SizedBox(),),
      ],
    );
  }

  TextSpan getMongolText(String unit,String v1,String v2){
    return TextSpan(
      style: TextStyle(fontSize: 11, color: Colors.black),
      children: [
        TextSpan(text: unit, style: TextStyle(fontSize: 9)),
        TextSpan(text: v1, style: TextStyle(color:hourColorMap[v1] )),
        TextSpan(text: v2, style: TextStyle(color:hourColorMap[v2])),
      ],
    );
  }


  Widget jxTitle() {
    return Row(
      crossAxisAlignment: CrossAxisAlignment.start, // 垂直居顶
      children: [
        Expanded( // 使用Expanded来填充整行
          child: Container(
            decoration: BoxDecoration(
              image: DecorationImage(
                image: AssetImage('assets/images/bgm_01.png'), // 确保图片路径正确
                fit: BoxFit.cover, // 图片覆盖整个Container
              ),
            ),
            child: Center( // 使用Center来居中内容
              child: Padding(
                  padding: const EdgeInsets.all(16), // 设置内边距
                  child:  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween, // 平均分布子项
                    children: [
                      Expanded( // 扩展以填充可用空间
                        child: Column(
                          mainAxisSize: MainAxisSize.min, // 允许Column高度为其子项高度
                          crossAxisAlignment: CrossAxisAlignment.center, // 内容垂直居中
                          children: [
                            Text("吉神宜趋", style: TextStyle(color: Colors.black)),
                          ],
                        ),
                      ),
                      Expanded(
                        child: Column(
                          mainAxisSize: MainAxisSize.min,
                          crossAxisAlignment: CrossAxisAlignment.center,
                          children: [
                            Text("凶煞宜忌", style: TextStyle(color: Colors.black)),
                          ],
                        ),
                      ),
                    ],
                  )
              ),
            ),
          ),
        ),
      ],
    );
  }

  Widget hourTitle() {
    return Row(
      crossAxisAlignment: CrossAxisAlignment.start, // 垂直居顶
      children: [
        Expanded( // 使用Expanded来填充整行
          child: Container(
            decoration: BoxDecoration(
              image: DecorationImage(
                image: AssetImage('assets/images/bgm_01.png'), // 确保图片路径正确
                fit: BoxFit.cover, // 图片覆盖整个Container
              ),
            ),
            child: Center( // 使用Center来居中内容
              child: Padding(
                padding: const EdgeInsets.all(16), // 设置内边距
                child: Column(
                  mainAxisSize: MainAxisSize.min, // 允许Column根据内容高度调整大小
                  mainAxisAlignment: MainAxisAlignment.center, // 垂直居中
                  crossAxisAlignment: CrossAxisAlignment.start, // 水平居左
                  children: [
                    Text(
                      "时辰宜忌",
                      style: TextStyle(fontSize: 18),
                    ),
                    // 如果有更多内容，可以在这里添加
                  ],
                ),
              ),
            ),
          ),
        ),
      ],
    );
  }

  Widget zhinengzheri(Lunar lunar) {
    String str = "） ${lunar.getDayShengXiao()}日冲${lunar.getDayChongShengXiao()}煞${lunar.getDaySha()}";
    return Container(
      constraints: BoxConstraints(
        maxWidth: double.infinity, // 允许Container占据尽可能多的水平空间
      ),
      decoration: BoxDecoration(
        color: Colors.black,
      ),
      child: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 16),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center, // 将内容在水平方向上居中
          crossAxisAlignment: CrossAxisAlignment.center, // 将内容在垂直方向上居中
          children: [
            Text.rich(
              TextSpan(
                text: '智能择日（ ',
                style: TextStyle(color: Colors.white, fontWeight: FontWeight.bold),
                children: <TextSpan>[
                  TextSpan(text: lunar.getDayTianShenLuck(), style: TextStyle(color: Colors.red, fontWeight: FontWeight.bold)),
                  TextSpan(text: str, style: TextStyle(fontWeight: FontWeight.bold)),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
  Widget getYJ(Lunar lunar){
    List<String> yList = lunar.getDayYi();
    String y=yList.join(",");
    // 忌
    List<String> jList = lunar.getDayJi();
    String j=jList.join(",");
    return Column(
      children: [
        BrnPairInfoTable(
          children: <BrnInfoModal>[
            BrnInfoModal(keyPart: CircleBackgroundText("宜",Colors.yellow,30)
                , valuePart: y),
            BrnInfoModal(keyPart: CircleBackgroundText("忌",Colors.red,30), valuePart: j),
          ],
        ),
      ],
    );
  }

  Widget getTimeYJ(LunarTime lunarTime){
    List<String> yList = lunar.getDayYi();
    String y=yList.join(",");
    // 忌
    List<String> jList = lunar.getDayJi();
    String j=jList.join(",");
    return Column(
      children: [
        BrnPairInfoTable(
          children: <BrnInfoModal>[
            BrnInfoModal(keyPart: CircleBackgroundText("宜",Colors.yellow,20), valuePart: y),
            BrnInfoModal(keyPart: CircleBackgroundText("忌",Colors.red,20), valuePart: j),
          ],
        ),
      ],
    );

  }

  Widget getZRJX(Lunar lunar){
    return Text("值日九星："+lunar.getDayNineStar().toString());
  }
  Widget getOhterFive(Lunar lunar) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween, // 平均分布子项
      children: [
        Expanded( // 扩展以填充可用空间
          child: Column(
            mainAxisSize: MainAxisSize.min, // 允许Column高度为其子项高度
            crossAxisAlignment: CrossAxisAlignment.center, // 内容垂直居中
            children: [
              Text("五行", style: TextStyle(color: Colors.yellow)),
              Text(lunar.getDayNaYin(), textAlign: TextAlign.center), // 文本水平居中
            ],
          ),
        ),
        Expanded(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Text("黄道", style: TextStyle(color: Colors.yellow)),
              Text(lunar.getDayTianShen(), textAlign: TextAlign.center),
            ],
          ),
        ),
        Expanded(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Text("72侯", style: TextStyle(color: Colors.yellow)),
              Text(lunar.getWuHou(), textAlign: TextAlign.center),
            ],
          ),
        ),
        Expanded(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Text("星宿", style: TextStyle(color: Colors.yellow)),
              Text("${lunar.getXiu()}${lunar.getZheng()}${lunar.getAnimal()}", textAlign: TextAlign.center),
            ],
          ),
        ),
        Expanded(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Text("除建12神", style: TextStyle(color: Colors.yellow)),
              Text("${lunar.getZhiXing()}日", textAlign: TextAlign.center),
            ],
          ),
        ),
      ],
    );
  }

  Widget getJX(Lunar lunar) {
    List<String> jList = lunar.getDayJiShen();
    String j=jList.join(" ");
    // 凶煞宜忌
    List<String> xList = lunar.getDayXiongSha();
    String x=xList.join(" ");

    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween, // 平均分布子项
      children: [
        Expanded( // 扩展以填充可用空间
          child: Column(
            mainAxisSize: MainAxisSize.min, // 允许Column高度为其子项高度
            crossAxisAlignment: CrossAxisAlignment.center, // 内容垂直居中
            children: [
              Text(j, textAlign: TextAlign.center), // 文本水平居中
            ],
          ),
        ),
        Expanded(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Text(x, textAlign: TextAlign.center),
            ],
          ),
        ),
      ],
    );
  }

  Widget getOther(Lunar lunar){
    List<String> jr = lunar.getFestivals();
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 16),
      child:Column(
          children: [
            Text( "节日："+ (jr.length ==0 ? "无":jr.join(","))),
            Text( "胎神："+ lunar.getDayPositionTai()),
            Text( "五行："+ lunar.getYearNaYin()+" "+lunar.getMonthNaYin()+" "+lunar.getDayNaYin()),
            Text( "财神："+ lunar.getDayPositionCaiDesc()),
            Text( "喜神："+  lunar.getDayShengXiao()),
            Text( "福神："+  lunar.getDayPositionFuDesc()),
            Text( "阳神："+  lunar.getDayPositionYangGuiDesc()),
            Text( "阴神："+  lunar.getDayPositionYinGuiDesc()),
            Text( "彭祖："+ lunar.getPengZuGan()+" "+lunar.getPengZuZhi()),
          ]
      ),
    );
  }

}
class CircleBackgroundText extends StatelessWidget {
  final String text;
  final Color color;
  final double size;
  CircleBackgroundText(this.text,this.color,this.size);
  @override
  Widget build(BuildContext context) {
    return Container(
      width: size, // 你可以根据需要调整这个值来改变圆形的大小
      height: size, // 你可以根据需要调整这个值来改变圆形的大小
      decoration: BoxDecoration(
        shape: BoxShape.circle, // 设置为圆形
        color: color, // 设置为黄色背景
      ),
      child: Center(
        child: Text(
          text,
          style: TextStyle(
            color: Colors.black, // 设置文本颜色为黑色，以便在黄色背景上可见
          ),
        ),
      ),
    );
  }
}
