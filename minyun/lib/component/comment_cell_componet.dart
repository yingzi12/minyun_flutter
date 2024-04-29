import 'package:flutter/material.dart';
import 'package:cached_network_image/cached_network_image.dart';
import 'package:minyun/models/great_master_comment_model.dart';
import 'package:nb_utils/nb_utils.dart';


class CommentCellComponet extends StatelessWidget {
  final GreatMasterCommentModel comment;

  CommentCellComponet(this.comment);

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
      padding: EdgeInsets.all(15),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          Row(
            children: <Widget>[
              CircleAvatar(
                radius: 13,
                backgroundImage: CachedNetworkImageProvider(comment.circleUrl ??""),
              ),
              SizedBox(width: 10),
              Text(comment.nickname??"", style: TextStyle(fontSize: 14, color: gray)),
            ],
          ),
          Container(
            padding: EdgeInsets.fromLTRB(35, 15, 15, 0),
            child: Text(comment.comment??"", style: TextStyle(fontSize: 16)),
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
