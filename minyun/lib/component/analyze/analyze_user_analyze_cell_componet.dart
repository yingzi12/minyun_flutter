import 'package:flutter/material.dart';
import 'package:minyun/models/analyze_eight_char_analyze_model.dart';
import 'package:minyun/models/analyze_eight_char_info_model.dart';
import 'package:minyun/screens/user/account_screen.dart';
import 'package:minyun/utils/AppColors.dart';
import 'package:minyun/utils/AppContents.dart';
import 'package:nb_utils/nb_utils.dart';


class AnalyzeUserAnalyzeCellComponet extends StatelessWidget {
  final AnalyzeEightCharAnalyzeModel infoModel;

  AnalyzeUserAnalyzeCellComponet(this.infoModel);

  like() {}

  Widget buildButton(String image, String title, VoidCallback onPress, bool isSelected) {
    return Row(
      children: <Widget>[
        Image.asset(image),
        SizedBox(width: 5),
        Text(
          title,
          style: TextStyle(fontSize: 14, color: isSelected ? Color(0xfff5a623) : gray),
        )
      ],
    );
  }

  Widget buildContent() {
    return Container(
      margin: EdgeInsets.symmetric(vertical: 8),
      padding: EdgeInsets.all(16),
      decoration: BoxDecoration(
          color: mode.theme ? darkPrimaryLightColor : Colors.grey.shade200, borderRadius: BorderRadius.circular(DEFAULT_RADIUS)),
      child: Row(
        children: [
          Expanded(
            flex: 7,
            child: GestureDetector(
              onTap: () {
                // routerCell(analyze);
              },
              child:
              Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text( "${infoModel.createTime ?? ""}", style: TextStyle(fontSize:   14,color: Colors.black45)),
                  Row(
                    children: [
                      Text(infoModel.intro??"", style: TextStyle(fontSize:   20,color: Colors.black)),
                      // CircleBackgroundText(sex ==0 ? "女":"男",sex == '女'?Colors.yellow :Colors.orange,30),
                    ],
                  ),
                  // Text(analyze.dateType!.toInt() ==5 ? "${analyze.year}-${analyze.month}-${analyze.day} ${analyze.hour}:00": "${analyze.ng} ${analyze.yg} ${analyze.rg} ${analyze.sg}", style: TextStyle(fontSize:   12,color: Colors.black45)),
                ],
              ),
            ),
          ),
          Expanded(
            flex: 1,
            child: Column(
              // crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Container(
                  width: 30.0, // 设置Container的宽度
                  height: 30.0, // 设置Container的高度
                  child: IconButton(
                    icon: Icon(Icons.delete),
                    color: Colors.red,
                    padding: EdgeInsets.all(0.0), // 移除默认的padding
                    onPressed: () {
                      print('Edit button pressed');
                    },
                  ),
                ),
                Container(
                  width: 30.0, // 设置Container的宽度
                  height: 30.0, // 设置Container的高度
                  child: IconButton(
                    icon: Icon(Icons.edit),
                    color: Colors.blue,
                    padding: EdgeInsets.all(0.0), // 移除默认的padding
                    onPressed: () {
                      print('Edit button pressed');
                    },
                  ),
                ),

              ],
            ),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: <Widget>[
        buildContent(),
        Divider(height: 1, indent: 15),
      ],
    );
  }
}
