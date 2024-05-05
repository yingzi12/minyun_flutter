import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:minyun/api/DiscussCommentApi.dart';
import 'package:minyun/api/GreatMasterApi.dart';
import 'package:minyun/component/discuss/comment_cell_componet.dart';
import 'package:minyun/models/ResultListModel.dart';
import 'package:minyun/models/discuss_comment_model.dart';
import 'package:minyun/models/great_master_model.dart';
import 'package:minyun/utils/AppColors.dart';
import 'package:minyun/utils/ScreenUtil.dart';
import 'package:nb_utils/nb_utils.dart';

class DiscussDetailScreen extends StatefulWidget {
  final int gaid;

  DiscussDetailScreen(this.gaid);

  @override
  DiscussDetailScreenState createState() => DiscussDetailScreenState();
}

class DiscussDetailScreenState extends State<DiscussDetailScreen> with RouteAware {
  GreatMasterModel? greatMaster;
  List<DiscussCommentModel> comments = [];
  ScrollController scrollController = ScrollController();
  double navAlpha = 0;
  bool isSummaryUnfold = false;
  int commentCount = 0;
  int commentMemberCount = 0;

  @override
  void initState() {
    super.initState();
    fetchData();

    scrollController.addListener(() {
      var offset = scrollController.offset;
      if (offset < 0) {
        if (navAlpha != 0) {
          setState(() {
            navAlpha = 0;
          });
        }
      } else if (offset < 50) {
        setState(() {
          navAlpha = 1 - (50 - offset) / 50;
        });
      } else if (navAlpha != 1) {
        setState(() {
          navAlpha = 1;
        });
      }
    });
  }

  @override
  void dispose() {
    scrollController.dispose();
    super.dispose();
  }

  changeSummaryMaxLines() {
    setState(() {
      isSummaryUnfold = !isSummaryUnfold;
    });
  }

  back() {
    Navigator.pop(context);
  }

  //获取世界详细
  fetchData() async {
    var gaid = this.widget.gaid;
    GreatMasterModel greatMasterModel= await GreatMasterApi.getInfo(gaid);
    //停止
    ResultListModel<DiscussCommentModel>  commentList= await DiscussCommentApi.getList({'gaid': gaid.toString(),'source':2.toString()});

    setState(() {
      // this.story = Story.fromStoryEntity(storyEntity);
      this.comments = commentList.data??[];
      this.greatMaster =  greatMasterModel;
    });
  }

  Widget buildNavigationBar() {
    return Stack(
      children: <Widget>[
        Container(
          width: 44,
          height: ScreenUtil.navigationBarHeight,
          padding: EdgeInsets.fromLTRB(5, ScreenUtil.topSafeHeight, 0, 0),
          child: GestureDetector(onTap: back, child: Image.asset('img/pub_back_white.png')),
        ),
        Opacity(
          opacity: navAlpha,
          child: Container(
            decoration: BoxDecoration(color: white, boxShadow: [BoxShadow(color: Color(0x22000000), blurRadius: 8)]),
            padding: EdgeInsets.fromLTRB(5, ScreenUtil.topSafeHeight, 0, 0),
            height: ScreenUtil.navigationBarHeight,
            child: Row(
              children: <Widget>[
                Container(
                  width: 44,
                  child: GestureDetector(onTap: back, child: Image.asset('img/pub_back_gray.png')),
                ),
                Expanded(
                  child: Text(
                    greatMaster!.userName??"",
                    style: TextStyle(fontSize: 17, fontWeight: FontWeight.bold),
                    textAlign: TextAlign.center,
                  ),
                ),
                Container(width: 44),
              ],
            ),
          ),
        )
      ],
    );
  }

  Widget buildComment() {
    return Container(
      color: Colors.white,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          Container(
            padding: EdgeInsets.symmetric(vertical: 15),
            child: Row(
              children: <Widget>[
                Image.asset('img/home_tip.png'),
                SizedBox(width: 13),
                Text('书友评价', style: TextStyle(fontSize: 16)),
                Expanded(child: Container()),
                Image.asset('img/detail_write_comment.png'),
                Text('  写书评', style: TextStyle(fontSize: 14, color: primary)),
                SizedBox(width: 15),
              ],
            ),
          ),
          Divider(height: 1),
          Column(
            children: comments.map((comment) => CommentCellComponet(comment)).toList(),
          ),
          Container(
            padding: EdgeInsets.symmetric(vertical: 15),
            child: Center(
              child: Text(
                '查看全部评论（0条）',
                style: TextStyle(fontSize: 14, color: gray),
              ),
            ),
          )
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    if (this.greatMaster == null) {
      return Scaffold(appBar: AppBar(elevation: 0));
    }
    var greatMaster = this.greatMaster!;
    return Scaffold(
      body: AnnotatedRegion(
        value: navAlpha > 0.5 ? SystemUiOverlayStyle.dark : SystemUiOverlayStyle.light,
        child: Stack(
          children: <Widget>[
            Column(
              children: <Widget>[
                Expanded(
                  child: ListView(
                    controller: scrollController,
                    padding: EdgeInsets.only(top: 0),
                    children: <Widget>[
                      // StoryDetailHeader(story),
                      // StorySummaryView(story.introduction, isSummaryUnfold, changeSummaryMaxLines),
                      SizedBox(height: 10),
                      buildComment(),
                      SizedBox(height: 10),
                      // StoryDetailRecommendView(recommendStoryS),
                    ],
                  ),
                ),
                // StoryDetailToolbar(story),
              ],
            ),
            buildNavigationBar(),
          ],
        ),
      ),
    );
  }
}
class StoryDetailCell extends StatelessWidget {
  final String iconName;
  final String title;
  final String subtitle;
  final Widget? attachedWidget;
  final VoidCallback onTap;  // 点击事件的回调函数

  StoryDetailCell({
    required this.iconName,
    required this.title,
    required this.subtitle,
    this.attachedWidget,
    required this.onTap,  // 在构造函数中初始化
  });

  @override
  Widget build(BuildContext context) {
    return InkWell(  // 使用 InkWell 来获得点击效果
      onTap: onTap,  // 将回调函数赋值给 onTap 属性
      child: Container(
        color: Colors.white,
        padding: EdgeInsets.symmetric(horizontal: 15),
        child: Column(
          children: <Widget>[
            Container(
              height: 50,
              child: Row(
                children: <Widget>[
                  Image.asset(iconName),
                  SizedBox(width: 5),
                  Text(title, style: TextStyle(fontSize: 16)),
                  SizedBox(width: 10),
                  Expanded(
                    child: Text(
                      subtitle,
                      style: TextStyle(fontSize: 14, color: gray),
                      maxLines: 1,
                      overflow: TextOverflow.ellipsis,
                    ),
                  ),
                  attachedWidget != null ? attachedWidget! : Container(),
                  SizedBox(width: 10),
                  Image.asset('img/arrow_right.png'),
                ],
              ),
            ),
            Divider(height: 1),
          ],
        ),
      ),
    );
  }
}
