import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:minyun/models/message_model.dart';
import 'package:minyun/screens/user/account_screen.dart';
import 'package:minyun/utils/AppColors.dart';
import 'package:minyun/utils/AppCommon.dart';
import 'package:minyun/utils/AppContents.dart';

class MessageCellComponent extends StatelessWidget {

  final MessageModel messageModel;
  const MessageCellComponent({super.key, required this.messageModel});

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.symmetric(vertical: 8),
      padding: EdgeInsets.all(16),
      decoration: BoxDecoration(
          color: mode.theme ? darkPrimaryLightColor : Colors.grey.shade200,
          borderRadius: BorderRadius.circular(DEFAULT_RADIUS)),
      child: Column(
        children: [
          Expanded(
            child: Text(
              // dashboardFilesList[index].titleText.toString(),
              messageModel.centent??"",
              // style: appPrimaryTextStyle(),
              overflow: TextOverflow.fade,
            ),
          ),
          SizedBox(width: 16),
          Expanded(
            child: Text(
              // dashboardFilesList[index].titleText.toString(),
              messageModel.createTime??"",
               style: appMainPrimaryTextStyle(),
              overflow: TextOverflow.fade,
            ),
          ),
        ],
      ),
    );
  }
}
