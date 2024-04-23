import 'dart:collection';

import 'package:bruno/bruno.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:lunar/calendar/Lunar.dart';
import 'package:lunar/calendar/Solar.dart';
import 'package:minyun/api/CalendarApi.dart';
import 'package:minyun/constant.dart';
import 'package:minyun/models/CalendarModel.dart';
import 'package:minyun/models/ResultListModel.dart';
import 'package:minyun/screens/account_screen.dart';
import 'package:minyun/screens/dashboard_screen.dart';

import 'package:minyun/utils/AppColors.dart';
import 'package:minyun/utils/AppContents.dart';
import '../utils/AppCommon.dart';
import '../utils/images.dart';

class ArchivesScreen extends StatefulWidget {
  final String search;
  ArchivesScreen({super.key, required this.search});

  @override
  State<ArchivesScreen> createState() => _ArchivesScreenState();
}

class _ArchivesScreenState extends State<ArchivesScreen> {
  // List<String>  timelyList = ["祭祀","祈福","求嗣","开光","塑绘","齐醮","斋醮","沐浴","酬神","造庙",
  //   "祀灶","焚香","谢土","出火","雕刻","嫁娶","订婚","纳采","问名","纳婿","归宁","安床","合帐","冠笄",
  //   "订盟","进人口","裁衣","挽面","开容","修坟","启钻","破土","安葬","立碑",
  //   "成服","除服","开生坟","合寿木","入殓","移柩","普渡","入宅","安香","安门","修造","起基","动土","上梁","竖柱","开井开池",
  //   "作陂放水","拆卸","破屋","坏垣","补垣","伐木做梁","作灶","解除","开柱眼","穿屏扇架","盖屋合脊","开厕","造仓","塞穴",
  //   "平治道涂","造桥","作厕","筑堤","开池","伐木","开渠","掘井","扫舍","放水","造屋","合脊","造畜稠","修门","定磉","作梁",
  //   "修饰垣墙","架马","开市","挂匾","纳财","求财","开仓","买车","置产","雇庸","出货财","安机械","造车器","经络","酝酿",
  //   "作染","鼓铸","造船","割蜜","栽种","取渔","结网","牧养","安碓磑","习艺","入学","理发","探病","见贵","乘船","渡水",
  //   "针灸","出行","移徙","分居","剃头","整手足甲","纳畜","捕捉","畋猎","教牛马","会亲友","赴任","求医","治病","词讼","起基动土",
  //   "破屋坏垣","盖屋","造仓库","立券交易","交易","立券","安机","会友","求医疗病","诸事不宜","馀事勿取","行丧","断蚁","归岫"];
  List<String>  timelyList = ["祭祀","祈福","置产","嫁娶","订婚","纳采","动土","安葬","进人口","开市","求医","治病","买车","出行","移徙","分居","交易",];
  String dropdownValue ="";
  DateFormat formatter = new DateFormat('yyyy-MM-dd'); // Define your desired format

  /// 日期
  String format="yyyy-MM-dd";
  /// 是否是农历
  bool isLunar = true;
  List<CalendarModel> calendarList=[];
  //是否有数据
  bool isDate = false;
  String startDate="1990-01-01";
  String endDate="1990-01-01";

  @override
  void initState() {
    super.initState();
    Solar solar = Solar.fromDate(DateTime.now());
    String startDate = solar.toString();
    String endDate  = solar.nextMonth(3).toString();
    _refreshApiData(widget.search, startDate,endDate);
    // _refreshSdkData();
  }

  @override
  void dispose() {
    super.dispose();
  }

  Future<void> _refreshApiData(String timely,String start,String end) async {
    // timely=嫁娶&startDate=2024-02-03&endDate=2024-03-19
    Map<String, String> queryParams=new HashMap();
    queryParams["timely"] = timely;
    queryParams["startDate"] = start;
    queryParams["endDate"] = end;
    ResultListModel<CalendarModel> resultModel = await CalendarApi.getList(queryParams);
    setState(() {
      dropdownValue = timely;
      startDate=start;
      endDate=end;
      if(resultModel.code! == 200) {
        calendarList = resultModel.data!;
        if(calendarList.length >0) {
          isDate = true;
        }else{
          calendarList=[];
          isDate=false;
        }
      }else{
        calendarList=[];
        isDate=false;

      }
    });
  }




  @override
  Widget build(BuildContext context) {
    final height = MediaQuery.of(context).size.height;
    final width = MediaQuery.of(context).size.width;
    return Scaffold(
      appBar: AppBar(
        title: Text("吉日"),
        titleTextStyle: boldTextStyle(fontSize: 24),
        elevation: 0,
        titleSpacing: 0,
        leading: Padding(
          padding: EdgeInsets.all(12.0),
          child: Image.asset(splash_screen_image, color: primaryColor),
        ),
      ),
      body: Stack(
        children: [
          Column(
            children: [
              getGl(),
              Row(
                children: [
                  Padding(
                    padding: const EdgeInsets.only(left: 16),
                    child: Text("${widget.search} 共 ${calendarList.length} 天", style: boldTextStyle(fontSize: 18,color: Colors.black45)),
                  ),
                  Spacer(),
                ],
              ),
              Expanded(
                child:
                ListView.builder(
                  itemCount: calendarList.length,
                  primary: false,
                  padding: EdgeInsets.symmetric(horizontal: 16),
                  shrinkWrap: true,
                  itemBuilder: (context, int index) {
                    CalendarModel calendar= calendarList[index];
                    Solar solar = Solar.fromYmd(calendar.year!.toInt(),calendar.month!.toInt(),calendar.day!.toInt());
                    Lunar lunar = solar.getLunar();
                    return Container(
                      margin: EdgeInsets.symmetric(vertical: 8),
                      padding: EdgeInsets.all(16),
                      decoration: BoxDecoration(
                          color: mode.theme ? darkPrimaryLightColor : Colors.grey.shade200, borderRadius: BorderRadius.circular(DEFAULT_RADIUS)),
                      child: Row(
                        children: [
                          Expanded(
                            // flex: 7,
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Row(
                                  children: [
                                    Text(solar.toYmd(), style: TextStyle(fontSize:   20,color: Colors.black)),
                                    CircleBackgroundText(lunar.getDayTianShenLuck(),lunar.getDayTianShenLuck() == '吉'?Colors.yellow :Colors.red,30),
                                  ],
                                ),
                                Text( "${lunar.getDayShengXiao()}日冲${lunar.getDayChongShengXiao()}煞${lunar.getDaySha()}", style: TextStyle(fontSize:   14,color: Colors.black45)),
                                Text("财神${lunar.getDayPositionCaiDesc()} 喜神${lunar.getDayShengXiao()}  福神${lunar.getDayPositionFuDesc()} 阳神${lunar.getDayPositionYangGuiDesc()} 阴神${lunar.getDayPositionYinGuiDesc()}", style: TextStyle(fontSize:   12,color: Colors.black45)),
                                getYJ(lunar,dropdownValue),
                              ],
                            ),
                          ),
                        ],
                      ),
                    );
                  },
                ),

              ),
            ],
          ),
        ],
      ),
    );
  }

  PopupMenuItem<int> popupItem({required String title, required int value}) {
    return PopupMenuItem(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [Text(title, style: primaryTextStyle(fontSize: 14)), Divider()],
      ),
      value: value,
    );
  }
  Widget getGl() {
    String format = 'yyyy-MM-dd';
    BrnPickerTitleConfig pickerTitleConfig = BrnPickerTitleConfig(titleContent: "选择时间范围");
    return Center(
      child:
          GestureDetector(
            onTap: () {
              // 这里是点击事件发生时调用的代码
              // print('2013-11-11');
              BrnDateRangePicker.showDatePicker(context,
                  isDismissible: false,
                  minDateTime: DateTime(2010, 06, 01, 00, 00, 00),
                  maxDateTime: DateTime(2029, 07, 24, 23, 59, 59),
                  pickerMode: BrnDateTimeRangePickerMode.date,
                  minuteDivider: 10,
                  pickerTitleConfig: pickerTitleConfig,
                  dateFormat: format,
                  initialStartDateTime:  formatter.parse(startDate),
                  initialEndDateTime:  formatter.parse(endDate),
                  onConfirm: (startDateTime, endDateTime, startlist, endlist) {
                     setState(() {
                       startDate = formatter.format(startDateTime); // Convert to String representation
                       endDate = formatter.format(endDateTime); // Convert to String representation
                       _refreshApiData(dropdownValue, startDate, endDate);
                     });
                    // BrnToast.show(
                    //     "onConfirm:  $startDateTime   $endDateTime     $startlist     $endlist", context);
                  }, onClose: () {
                    // print("onClose");
                  }, onCancel: () {
                    // print("onCancel");
                  }, onChange: (startDateTime, endDateTime, startlist, endlist) {
                    // BrnToast.show(
                    //     "onChange:  $startDateTime   $endDateTime     $startlist     $endlist", context);
                  });
              // 你可以在这里导航到新的页面、显示对话框等
            },
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center, // 水平居中
              children: [Text(
                startDate,
              style: TextStyle(
                fontSize: 20,
                color: Colors.blue, // 可以设置文本颜色以指示它是可点击的
                // decoration: TextDecoration.underline, // 可以添加下划线以指示它是可点击的
              ),
            ),
                SizedBox(width: 10,),
                Text("至"),
                SizedBox(width: 10,),
                Text(
                  endDate,
                  style: TextStyle(
                    fontSize: 20,
                    color: Colors.blue, // 可以设置文本颜色以指示它是可点击的
                    // decoration: TextDecoration.underline, // 可以添加下划线以指示它是可点击的
                  ),
                ),
                SizedBox(width: 10,),
                 // Expanded(
                 //   child:
                   DropdownMenu<String>(
                  initialSelection: dropdownValue,
                  width: 100,
                  onSelected: (String? value) {
                    // This is called when the user selects an item.
                    setState(() {
                      dropdownValue = value!;
                    });
                    _refreshApiData(dropdownValue,startDate,endDate);
                  },
                  dropdownMenuEntries: timelyList.map<DropdownMenuEntry<String>>((String value) {
                    return DropdownMenuEntry<String>(
                        value: value,
                        label: value,
                        labelWidget: SizedBox(
                        width: 80, // Set desired width
                        height: 30, // Set desired height
                        child: Text(value),
                      ),
                    );
                  }).toList(),
                ),
          // ),
              ],
            ),
          ),
    );
  }
  Widget getYJ(Lunar lunar,String title){
    List<String> yList = lunar.getDayYi();
    // String y=yList.join(",");
    // 忌
    List<String> jList = lunar.getDayJi();
    // String j=jList.join(",");
    return Column(
      children: [
        Row(
          children: [
            Container(
              width: 30.0, // 设置固定宽度为100
              decoration: BoxDecoration(
                shape: BoxShape.circle,
                color: Colors.yellow,
              ),
              alignment: Alignment.center, // 使内容水平垂直居中
              padding: EdgeInsets.all(8.0), // 可选，根据需要设置内边距
              child: Text(
                "宜",
                style: TextStyle(
                  fontSize: 20.0, // 根据需要设置字体大小
                  color: Colors.black, // 根据需要设置文字颜色
                ),
              ),
            ),

            Expanded(
              child:  Wrap(
                spacing: 8.0, // 子组件之间的间距
                runSpacing: 4.0, // 换行之间的间距
                children: <Widget>[
                  // 在这里放置你的组件
                  for(var y in yList)
                    Container(
                      decoration: BoxDecoration(
                        color: title== y ? Colors.red :Colors.white10,
                      ),
                      child: Text(
                        y,
                        style: TextStyle(
                          fontSize: 15.0, // 根据需要设置字体大小
                          color: Colors.black, // 根据需要设置文字颜色
                        ),
                      ),
                    ),
                ],
              ),
            )
          ],
        ),
        Row(
          children: [
            Container(
              width: 30.0, // 设置固定宽度为100
              decoration: BoxDecoration(
                shape: BoxShape.circle,
                color: Colors.red,
              ),
              alignment: Alignment.center, // 使内容水平垂直居中
              padding: EdgeInsets.all(8.0), // 可选，根据需要设置内边距
              child: Text(
                "忌",
                style: TextStyle(
                  fontSize: 20.0, // 根据需要设置字体大小
                  color: Colors.black, // 根据需要设置文字颜色
                ),
              ),
            ),

            Expanded(
              child:  Wrap(
                spacing: 8.0, // 子组件之间的间距
                runSpacing: 4.0, // 换行之间的间距
                children: <Widget>[
                  // 在这里放置你的组件
                  for(var j in jList)
                    Container(
                      decoration: BoxDecoration(
                        color: title== j ? Colors.red :Colors.white10,
                      ),
                      child: Text(
                        j,
                        style: TextStyle(
                          fontSize: 15.0, // 根据需要设置字体大小
                          color: Colors.black, // 根据需要设置文字颜色
                        ),
                      ),
                    ),
                ],
              ),
            )
          ],
        ),
      ],
    );
  }


}
