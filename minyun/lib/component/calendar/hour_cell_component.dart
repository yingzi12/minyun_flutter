import 'package:bruno/bruno.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:lunar/lunar.dart';
import 'package:minyun/models/hour_model_class.dart';
import 'package:minyun/screens/calendar_screen.dart';
import 'package:minyun/screens/user/account_screen.dart';
import 'package:minyun/utils/AppColors.dart';
import 'package:minyun/utils/AppContents.dart';

class HourCellComponent extends StatelessWidget {
  final Lunar lunar;

  final HourModel hour;

  HourCellComponent(this.hour, this.lunar);

  @override
  Widget build(BuildContext context) {
    LunarTime lunarTime = LunarTime.fromYmdHms(lunar.getYear(), lunar.getMonth(), lunar.getDay(), hour.sj, 30, 0);

    return Container(
      margin: EdgeInsets.symmetric(vertical: 8),
      padding: EdgeInsets.all(16),
      decoration: BoxDecoration(
          color: mode.theme ? darkPrimaryLightColor : Colors.grey.shade200,
          borderRadius: BorderRadius.circular(DEFAULT_RADIUS)),
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
                  // style: appPrimaryTextStyle(),
                  overflow: TextOverflow.fade,
                ),
                SizedBox(height: 16),
                CircleBackgroundText(lunarTime.getTianShenLuck(),
                    lunarTime.getTianShenLuck() == '吉' ? Colors.yellow : Colors
                        .red, 30)
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
                Text("${lunarTime.getMinHm() + '-' +
                    lunarTime.getMaxHm()}  时冲${lunarTime
                    .getChongDesc()} 煞${lunarTime.getSha()}",
                    style: TextStyle(fontSize: 14, color: Colors.black45)),
                Text("财神${lunarTime.getPositionCaiDesc()} 喜神${lunarTime
                    .getPositionXiDesc()}  福神${lunarTime
                    .getPositionFuDesc()} 阳神${lunarTime
                    .getPositionYangGuiDesc()} 阴神${lunarTime
                    .getPositionYinGuiDesc()}",
                    style: TextStyle(fontSize: 12, color: Colors.black45)),
                getTimeYJ(lunarTime),
              ],
            ),
          ),
        ],
      ),
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

}
