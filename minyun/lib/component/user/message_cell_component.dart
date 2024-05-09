import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:minyun/models/message_model.dart';
import 'package:minyun/screens/user/account_screen.dart';
import 'package:minyun/utils/AppColors.dart';
import 'package:minyun/utils/AppCommon.dart';
import 'package:minyun/utils/AppContents.dart';

class MessageCellComponent extends StatelessWidget {
  final int index;
  final MessageModel messageModel;
  const MessageCellComponent({super.key, required this.index, required this.messageModel});

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.symmetric(vertical: 8),
      padding: EdgeInsets.all(16),
      decoration: BoxDecoration(
          color: mode.theme ? darkPrimaryLightColor : Colors.grey.shade200,
          borderRadius: BorderRadius.circular(DEFAULT_RADIUS)),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            messageModel.centent ?? "",
            style: appMainBoldTextStyle(),
            maxLines: 1, // 设置最大行数为1
            overflow: TextOverflow.ellipsis, // 文本超出一行时显示省略号
          ),
          SizedBox(width: 16),
          Text(
              // dashboardFilesList[index].titleText.toString(),
              messageModel.createTime??"",
              style: appMainPrimaryTextStyle(),
              overflow: TextOverflow.fade,
          ),

        ],
      ),
    );
  }
}
