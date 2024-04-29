import 'dart:core';

import 'package:flutter/material.dart';
import 'package:minyun/api/CaseAnalyzeApi.dart';
import 'package:minyun/component/CaseComponent.dart';
import 'package:minyun/models/ResultListModel.dart';
import 'package:minyun/models/case_analyze_model.dart';
import 'package:minyun/utils/AppColors.dart';
import 'package:minyun/utils/AppWidget.dart';
import 'package:nb_utils/nb_utils.dart';

class CaseScreen extends StatefulWidget {
  CaseScreen();
  @override
  _CaseScreenState createState() => _CaseScreenState();
}

class _CaseScreenState extends State<CaseScreen> {
  List<CaseAnalyzeModel> stories = [];
  int currentPage = 1;
  bool isLoading = false;
  ScrollController _scrollController = ScrollController();
  GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey();

  String title="我的购买";
  String searchQuery="";

  void reloadData() async {
    setState(() {
      isLoading = true;
      stories.clear();
      currentPage = 1;
    });

    // 你的加载逻辑，可能需要根据搜索查询和下拉选项来过滤数据
    // 例如：
    ResultListModel<CaseAnalyzeModel> resultListModel = await CaseAnalyzeApi.getList({
      "pageNum": currentPage.toString(),
      "title": searchQuery,
    });

    Future.microtask(() {
      if (mounted) {
        setState(() {
          if (resultListModel.data != null) {
            stories.addAll(resultListModel.data!);
            currentPage++;
          }
          isLoading = false;
        });
      }
    });
  }

  //接受另外一个页面的搜索结构
  // void _navigateAndDisplaySearch(BuildContext context) async {
  //   final result = await Navigator.push(
  //     context,
  //     MaterialPageRoute(builder: (context) => MPSearchScreen()),
  //   );
  //
  //   // 使用 result（如果非空）来重新加载数据
  //   if (result != null && result is String) {
  //     searchQuery=result;
  //     reloadData();
  //   }
  // }

  @override
  void initState() {
    super.initState();
    _scrollController.addListener(_onScroll);
    // if(widget.serchValue!=null && widget.serchValue.length>0) {
    //   searchQuery = widget.serchValue;
    // }
    _loadMore();
  }

  void _onScroll() {
    if (_scrollController.position.pixels == _scrollController.position.maxScrollExtent && !isLoading) {
      _loadMore();
    }
  }

  @override
  void dispose() {
    _scrollController.removeListener(_onScroll);
    _scrollController.dispose();
    super.dispose();
  }

  void _loadMore() async {
    // late int wid=this.widget.wId;
    if (isLoading) return;
    setState(() => isLoading = true);
    ResultListModel<CaseAnalyzeModel> resultListModel = await CaseAnalyzeApi.getList({
      "pageNum": currentPage.toString(),
      "title": searchQuery,
    });
    // 使用 Future.microtask 延迟调用 setState
    Future.microtask(() {
      if (mounted) {
        setState(() {
          // 如果 elist 为空或长度为 0，则不再更新数据和增加页码
          if (resultListModel.data != null ) {
            stories.addAll(resultListModel.data!);
            currentPage++;
          }
          // 更新加载状态
          isLoading = false;
        });
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: mpAppBackGroundColor,
      key: _scaffoldKey,
      appBar: AppBar(
        centerTitle: true,
        leading: IconButton(
          icon: Icon(Icons.menu, color: white.withOpacity(0.9)),
          onPressed: () {
            _scaffoldKey.currentState!.openDrawer();
          },
        ),
        backgroundColor: mpAppBackGroundColor,
        elevation: 0.0,
        title: Text(title, style: boldTextStyle(color: Colors.white.withOpacity(0.9))),
        // actions: [
        //   IconButton(
        //     icon: Icon(Icons.search,color: Colors.white),
        //     onPressed: () => _navigateAndDisplaySearch(context),
        //   ),
        // ],
      ),
      // drawer: DrawerComponent() ,//()  //暂时去掉,
      body: Column(
        children: [
          // buildSearchBox(),
          // buildFilterBox(),
          Expanded(
            child: stories.isEmpty
                ? noDataWidget("没有更多数据了")
                : ListView.builder(
              controller: _scrollController,
              itemCount: stories.length + 1, // 增加一个额外的索引用于进度指示器
              itemBuilder: (context, index) {
                if (index >= stories.length) {
                  return Center(child: CircularProgressIndicator());
                }
                return CaseComponent(stories[index]);
              },
            ),
          ),
        ],
      ),
    );
  }

}


