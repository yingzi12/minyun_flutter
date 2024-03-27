import 'package:bruno/bruno.dart';
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

  Lunar lunar = Lunar.fromDate(DateTime.now());
  Solar solar = Solar.fromDate(DateTime.now());
  /// 日期
  String? time = '1995-11-8 12:12';
  DateFormat formatter = DateFormat("yyyy-MM-dd Hh:mm");
  /// 是否是农历
  bool isLunar = true;
  String _selectedValue = '选项1';

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
              lunarDate(lunar,solar),
              Row(
                children: [
                  Padding(
                    padding: const EdgeInsets.only(left: 16),
                    child: Text("Total ${recentFilesList.length} files", style: boldTextStyle(fontSize: 18)),
                  ),
                  Spacer(),
                  PopupMenuButton(
                    icon: Image.asset(page_up_down_image, height: 18, color: mode.theme ? Colors.white : Colors.black),
                    shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(DEFAULT_RADIUS)),
                    onSelected: (value) {},
                    color: mode.theme ? darkPrimaryLightColor : Colors.white,
                    splashRadius: 24,
                    itemBuilder: (context) => [
                      popupItem(title: "Title (A to Z)", value: 1),
                      popupItem(title: "Title (Z to A)", value: 2),
                      popupItem(title: "Date Created", value: 3),
                      popupItem(title: "Date Modified", value: 4),
                      popupItem(title: "Date Last Opened", value: 5),
                      popupItem(title: "Date Added", value: 6),
                      popupItem(title: "Size", value: 7),
                    ],
                  ),
                  IconButton(
                      onPressed: () {
                        NewFolderBottomSheetComponent(context).then((_) => setState(() {}));
                      },
                      splashRadius: 24,
                      icon: Image.asset(folder_image, height: 22, color: mode.theme ? Colors.white : Colors.black)),
                ],
              ),
              Expanded(
                child:                     ListView.builder(
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
                                getYJ(lunar),
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
          // Positioned(
          //   bottom: 16,
          //   right: 86,
          //   child: GestureDetector(
          //     onTap: () {
          //       //
          //     },
          //     child: Container(
          //       padding: EdgeInsets.all(16),
          //       decoration: BoxDecoration(shape: BoxShape.circle, color: primaryColor),
          //       child: Image.asset(camera_image, color: Colors.white, height: 20, fit: BoxFit.fill),
          //     ),
          //   ),
          // ),
          // Positioned(
          //   bottom: 16,
          //   right: 16,
          //   child: GestureDetector(
          //     onTap: () {
          //       //
          //     },
          //     child: Container(
          //       padding: EdgeInsets.all(16),
          //       decoration: BoxDecoration(shape: BoxShape.circle, color: primaryColor),
          //       child: Image.asset(picture_image, color: Colors.white, height: 20, fit: BoxFit.fill),
          //     ),
          //   ),
          // )
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
  Widget getGl(){
    return  Center(
      child: Row(
        children: [
          DropdownButton<String>(
            value: _selectedValue,
            icon: Icon(Icons.arrow_downward),
            iconSize: 24,
            elevation: 16,
            style: TextStyle(color: Colors.deepPurple),
            underline: Container(
              height: 2,
              color: Colors.deepPurpleAccent,
            ),
            // onChanged: (String newValue) {
            //   setState(() {
            //     _selectedValue = newValue;
            //   });
            // },
            items: <String>['选项1', '选项2', '选项3']
                .map((String value) {
              return DropdownMenuItem<String>(
                value: value,
                child: Text(value),
              );
            })
                .toList(), onChanged: (String? value) {  },
          ),

        ],
      )

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


}
