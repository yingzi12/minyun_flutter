import 'dart:collection';

import 'package:flutter/material.dart';

import 'package:minyun/api/MessageApi.dart';

import 'package:minyun/component/user/message_cell_component.dart';
import 'package:minyun/models/ResultListModel.dart';

import 'package:minyun/models/message_model.dart';
import 'package:minyun/utils/AppWidget.dart';
import 'package:tdesign_flutter/tdesign_flutter.dart';


/**
 * 大师点评
 */
class MessageListScreen extends StatefulWidget {

  @override
  State<MessageListScreen> createState() => _MessageListScreenState();
}

class _MessageListScreenState extends State<MessageListScreen>  with TickerProviderStateMixin {
  List<MessageModel> values = [];
  int currentPage = 1;
  int total = 0;

  @override
  void initState() {
    super.initState();
    loadData(); // 首次加载数据
  }

  Future<void> reloadData() async {
    // 重置当前页面和数据
    currentPage = 1;
    values.clear();
    // 加载数据
    await loadData();
  }

  Future<void> loadData() async {
    ResultListModel<MessageModel> resultListModel = await MessageApi.getList({"page": currentPage.toString()});
    setState(() {
      if (resultListModel.data != null) {
        values.addAll(resultListModel.data!);
      }
      total = resultListModel.total!.toInt();
    });
  }

  Future<void> loadMoreData() async {
    // 增加页码以加载更多数据
    currentPage++;
    await loadData();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: customAppBar(
        isLeading: true,
        title: "我的通知",
        // onTap: () {
        //   DashboardScreen(4).launch(context);
        //   // finish(context);
        // },
      ),
      body: RefreshIndicator(
        onRefresh: reloadData,
        child: ListView(
          padding: EdgeInsets.symmetric(vertical: 16),
          children: [
            titleRowItem(
              isSeeAll: false,
              title: '通知列表（${total}）',
            ),
            Wrap(
              runSpacing: 16,
              children: List.generate(
                values.length,
                    (index) =>
                        GestureDetector(
                          onTap: () {
                            showGeneralDialog(
                              context: context,
                              pageBuilder: (BuildContext buildContext, Animation<double> animation,
                                  Animation<double> secondaryAnimation) {
                                return TDConfirmDialog(
                                  title: "通知",
                                  content: values[index].centent,
                                  contentMaxHeight: 300,
                                );
                              },
                            );
                          },
                          child: MessageCellComponent(messageModel: values[index],),
                        ),
              ),
            ),
            if (values.length < total) // 只有在还有更多数据时显示加载更多
              OutlinedButton(
                onPressed: loadMoreData,
                child: Text('加载更多'),
              ),
          ],
        ),
      ),
    );
  }
}


