import 'package:flutter/material.dart';
import 'package:minyun/models/case_analyze_model.dart';
import 'package:nb_utils/nb_utils.dart';

class CaseComponent extends StatelessWidget {
  final CaseAnalyzeModel element;

  CaseComponent(this.element);

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        // AlbumDetailScreen( element.id!.toInt()).launch(context);
      },
      child: Container(
        padding: EdgeInsets.all(15),
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            // ImageCoverWidget(sourceWeb+(element.imgUrl ?? ""), width: 70, height: 93),
            SizedBox(width: 15),
            Expanded(
              child: buildRight(),
            ),
          ],
        ),
      ),
    );
  }

  Widget buildRight() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        Text(
          element.intro??"",
          style: TextStyle(color: Colors.white, fontSize: 17, fontWeight: FontWeight.bold),
          overflow: TextOverflow.ellipsis,
          maxLines: 2,
        ),
        SizedBox(height: 5),
        Text(
          element.caseDescription ?? "无",
          maxLines: 2,
          overflow: TextOverflow.ellipsis,
          style: TextStyle(
            fontSize: 14,
            color: Colors.grey,
          ),
        ),
        SizedBox(height: 5),
        Row(
          children: <Widget>[
            Expanded(
              // 使用 Expanded 包裹 Text 组件以适应宽度
              child: Text(
                element.label ?? "未知",
                style: TextStyle(fontSize: 14, color: Colors.grey),
                overflow: TextOverflow.ellipsis,
              ),
            ),
            buildTag((element.createTime ?? "").toString(), Colors.red),
            SizedBox(width: 5),
            buildTag("分类，待完成", Colors.grey),
          ],
        )
      ],
    );
  }


  Widget buildTag(String title, Color color) {
    return Container(
      padding: EdgeInsets.fromLTRB(5, 2, 5, 3),
      decoration: BoxDecoration(
        border: Border.all(color: Color.fromARGB(99, color.red, color.green, color.blue), width: 0.5),
        borderRadius: BorderRadius.circular(3),
      ),
      child: Text(
          title,
          style: TextStyle(fontSize: 11, color: color),
          overflow: TextOverflow.ellipsis, // 当文本超出时显示省略号
          maxLines: 1
      ),
    );
  }
}
