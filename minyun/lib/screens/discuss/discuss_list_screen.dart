import 'dart:core';

import 'package:flutter/material.dart';
import 'package:minyun/api/DiscussApi.dart';
import 'package:minyun/component/discuss/discuss_cell_component.dart';
import 'package:minyun/models/ResultListModel.dart';
import 'package:minyun/models/discuss_model.dart';
import 'package:minyun/utils/AppColors.dart';
import 'package:nb_utils/nb_utils.dart';

class DiscussListScreen extends StatefulWidget {
  final String kind;
  final String serchValue;

  DiscussListScreen({required this.kind,required this.serchValue});
  @override
  _DiscussListScreenState createState() => _DiscussListScreenState();
}

class _DiscussListScreenState extends State<DiscussListScreen> {
  List<DiscussModel> stories = [];
  int currentPage = 1;
  bool isLoading = false;
  ScrollController _scrollController = ScrollController();
  GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey();

  String title="最新";
  String searchQuery="";

  void reloadData() async {
    setState(() {
      isLoading = true;
      stories.clear();
      currentPage = 1;
    });

    // 你的加载逻辑，可能需要根据搜索查询和下拉选项来过滤数据
    // 例如：
    ResultListModel<DiscussModel> elist = await DiscussApi.getList({
      "pageNum": currentPage.toString(),
      "title": searchQuery,
    });

    Future.microtask(() {
      if (mounted) {
        setState(() {
          if (elist.data != null ) {
            stories.addAll(elist.data!);
            currentPage++;
          }
          isLoading = false;
        });
      }
    });
  }

  @override
  void initState() {
    super.initState();
    _scrollController.addListener(_onScroll);
    if(widget.serchValue!=null && widget.serchValue.length>0) {
      searchQuery = widget.serchValue;
    }
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
    List<DiscussModel> elist=[];
    ResultListModel<DiscussModel> resultListModel = await DiscussApi.getList(
        {"pageNum": currentPage.toString(),"title": searchQuery});
    if(resultListModel.data!=null){
      elist.addAll(resultListModel.data!);
    }
    // 使用 Future.microtask 延迟调用 setState
    Future.microtask(() {
      if (mounted) {
        setState(() {
          // 如果 elist 为空或长度为 0，则不再更新数据和增加页码
          if (elist != null && elist.isNotEmpty) {
            stories.addAll(elist);
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
            child: ListView.builder(
              controller: _scrollController,
              itemCount: stories.length,
              itemBuilder: (context, index) {
                return DiscussCellComponent(stories[index]);
              },
            ),
          ),
        ],
      ),
    );
  }

}


