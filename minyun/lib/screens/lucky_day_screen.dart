import 'package:bruno/bruno.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_lunar_datetime_picker/date_init.dart';
import 'package:flutter_lunar_datetime_picker/flutter_lunar_datetime_picker.dart';
import 'package:intl/intl.dart';
import 'package:lunar/calendar/Lunar.dart';
import 'package:lunar/calendar/LunarTime.dart';
import 'package:lunar/calendar/Solar.dart';
import 'package:minyun/models/hour_model_class.dart';
import 'package:minyun/screens/account_screen.dart';
import 'package:minyun/screens/dashboard_screen.dart';
import '../component/new_folder_bottom_sheet_component.dart';

import '../models/dashboard_model_class.dart';
import '../utils/color.dart';
import '../utils/common.dart';
import '../utils/constant.dart';
import '../utils/images.dart';

class LuckyDayScreen extends StatefulWidget {
  final String search;
  LuckyDayScreen({required this.search});

  @override
  State<LuckyDayScreen> createState() => _LuckyDayScreenState();
}

class _LuckyDayScreenState extends State<LuckyDayScreen> {

  // Lunar lunar = Lunar.fromDate(DateTime.now());
  // Solar solar = Solar.fromDate(DateTime.now());
  /// 日期
  String format="yyyy-MM-dd";
  /// 是否是农历
  bool isLunar = true;

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
                    child: Text("共 ${recentFilesList.length} 天", style: boldTextStyle(fontSize: 18,color: Colors.black45)),
                  ),
                  Spacer(),
                ],
              ),
              Expanded(
                child:
                ListView.builder(
                  itemCount: hourList.length,
                  primary: false,
                  padding: EdgeInsets.symmetric(horizontal: 16),
                  shrinkWrap: true,
                  itemBuilder: (context, int index) {
                    HourModel hour= hourList[index];
                    Lunar lunar = Lunar.fromDate(DateTime.now());
                    LunarTime lunarTime = LunarTime.fromYmdHms(lunar.getYear(), lunar.getMonth(), lunar.getDay(), hour.sj, 30, 0);
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
                                    Text("2012-11-11", style: TextStyle(fontSize:   20,color: Colors.black)),
                                    CircleBackgroundText(lunarTime.getTianShenLuck(),lunarTime.getTianShenLuck() == '吉'?Colors.yellow :Colors.red,30),
                                  ],
                                ),
                                // SizedBox(height: 16),
                                Text("${lunarTime.getMinHm() + '-' + lunarTime.getMaxHm()}  时冲${lunarTime.getChongDesc()} 煞${lunarTime.getSha()}", style: TextStyle(fontSize:   14,color: Colors.black45)),
                                Text("财神${lunarTime.getPositionCaiDesc()} 喜神${lunarTime.getPositionXiDesc()}  福神${lunarTime.getPositionFuDesc()} 阳神${lunarTime.getPositionYangGuiDesc()} 阴神${lunarTime.getPositionYinGuiDesc()}", style: TextStyle(fontSize:   12,color: Colors.black45)),
                                getYJ(lunar,widget.search),
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
              print('2013-11-11');
              BrnDateRangePicker.showDatePicker(context,
                  isDismissible: false,
                  minDateTime: DateTime(2010, 06, 01, 00, 00, 00),
                  maxDateTime: DateTime(2029, 07, 24, 23, 59, 59),
                  pickerMode: BrnDateTimeRangePickerMode.date,
                  minuteDivider: 10,
                  pickerTitleConfig: pickerTitleConfig,
                  dateFormat: format,
                  initialStartDateTime: DateTime(2020, 06, 21, 11, 00, 00),
                  initialEndDateTime: DateTime(2020, 06, 23, 10, 00, 00),
                  onConfirm: (startDateTime, endDateTime, startlist, endlist) {
                    BrnToast.show(
                        "onConfirm:  $startDateTime   $endDateTime     $startlist     $endlist", context);
                  }, onClose: () {
                    print("onClose");
                  }, onCancel: () {
                    print("onCancel");
                  }, onChange: (startDateTime, endDateTime, startlist, endlist) {
                    BrnToast.show(
                        "onChange:  $startDateTime   $endDateTime     $startlist     $endlist", context);
                  });
              // 你可以在这里导航到新的页面、显示对话框等
            },
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center, // 水平居中
              children: [Text(
              '2024-11-11',
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
                  '2024-11-11',
                  style: TextStyle(
                    fontSize: 20,
                    color: Colors.blue, // 可以设置文本颜色以指示它是可点击的
                    // decoration: TextDecoration.underline, // 可以添加下划线以指示它是可点击的
                  ),
                ),
            ],
            ),
          ),
    );
  }
  Widget getYJ(Lunar lunar,String title){
    List<String> yList = lunar.getDayYi();
    String y=yList.join(",");
    // 忌
    List<String> jList = lunar.getDayJi();
    String j=jList.join(",");
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
                        color: title== j ? Colors.red :Colors.white10,
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
