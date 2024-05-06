import 'dart:collection';

import 'package:bruno/bruno.dart';
import 'package:flutter/material.dart';
import 'package:minyun/api/AnalyzeEightCharApi.dart';
import 'package:minyun/models/ResultListModel.dart';
import 'package:minyun/models/SplayedFigureFindModel.dart';
import 'package:minyun/models/analyze_eight_char_model.dart';
import 'package:minyun/screens/splayed_figure_detail_screen.dart';
import 'package:minyun/screens/user/account_screen.dart';
import 'package:minyun/screens/dashboard_screen.dart';

import 'package:minyun/utils/AppColors.dart';
import 'package:minyun/utils/AppContents.dart';
import 'package:minyun/utils/AppWidget.dart';
import 'package:minyun/utils/SecureStorage.dart';
import 'package:nb_utils/nb_utils.dart';
import '../../utils/AppCommon.dart';
import '../../utils/images.dart';

/**
 *档案
 */
class ArchivesEditScreen extends StatefulWidget {
  final AnalyzeEightCharModel analyzeEightCharModel;

  ArchivesEditScreen({super.key, required this.analyzeEightCharModel});

  @override
  State<ArchivesEditScreen> createState() => _ArchivesEditScreenState();
}

class _ArchivesEditScreenState extends State<ArchivesEditScreen> {
  List<AnalyzeEightCharModel> stories = [];

  String explanation="请先登录。。。";
  String searchQuery="";
  bool isLogin=false;
  @override
  void initState() {
    super.initState();
    init();
  }

  Future<void> init() async {
    var token = await SecureStorage().getLoginToken();
    setState(() {
      if(token ==null){
        explanation="请先登录。。。";
      }else{
        explanation="数据加载中";
        isLogin=true;
        _refreshApiData("","","");
      }
    });
  }

  @override
  void dispose() {
    super.dispose();
  }

  Future<void> _refreshApiData(String timely,String start,String end) async {
    // timely=嫁娶&startDate=2024-02-03&endDate=2024-03-19
    Map<String, String> queryParams=new HashMap();
    ResultListModel<AnalyzeEightCharModel> resultModel = await AnalyzeEightCharApi.getList(queryParams);
    setState(() {
      stories=resultModel.data??[];
    });
  }

  @override
  Widget build(BuildContext context) {
    final height = MediaQuery.of(context).size.height;
    final width = MediaQuery.of(context).size.width;
    return Scaffold(
      appBar: AppBar(
        title: Text("档案"),
        titleTextStyle: appMainBoldTextStyle(fontSize: 24),
        elevation: 0,
        titleSpacing: 0,
        leading: Padding(
          padding: EdgeInsets.all(12.0),
          child: Image.asset(splash_screen_image, color: primaryColor),
        ),
      ),
      body: Stack(
        children: [
          stories.isEmpty
              ? noLoginWidget(explanation,context)
              : Column(
            children: [
              Row(
                children: [
                  Padding(
                    padding: const EdgeInsets.only(left: 16),
                    child: Text("共 ${stories.length} 条", style: appMainBoldTextStyle(fontSize: 18,color: Colors.black45)),
                  ),
                  Spacer(),
                ],
              ),
              Expanded(
                child:
                ListView.builder(
                  itemCount: stories.length,
                  primary: false,
                  padding: EdgeInsets.symmetric(horizontal: 16),
                  shrinkWrap: true,
                  itemBuilder: (context, int index) {
                    return buildCall( stories[index]);
                    // AnalyzeEightCharModel analyze= stories[index];
                    // int sex=analyze.sex!.toInt();
                    // return Container(
                    //   margin: EdgeInsets.symmetric(vertical: 8),
                    //   padding: EdgeInsets.all(16),
                    //   decoration: BoxDecoration(
                    //       color: mode.theme ? darkPrimaryLightColor : Colors.grey.shade200, borderRadius: BorderRadius.circular(DEFAULT_RADIUS)),
                    //   child: Row(
                    //     children: [
                    //       Expanded(
                    //         // flex: 7,
                    //         child: Column(
                    //           crossAxisAlignment: CrossAxisAlignment.start,
                    //           children: [
                    //             Row(
                    //               children: [
                    //                 Text(analyze.name??"", style: TextStyle(fontSize:   20,color: Colors.black)),
                    //                  CircleBackgroundText(sex ==0 ? "男":"女",sex == '男'?Colors.yellow :Colors.red,30),
                    //               ],
                    //             ),
                    //             Text( "${analyze.createTime ?? ""}", style: TextStyle(fontSize:   14,color: Colors.black45)),
                    //             Text(analyze.dateType!.toInt() ==5 ? "${analyze.year}-${analyze.month}-${analyze.day} ${analyze.hour}:00": "${analyze.ng} ${analyze.yg} ${analyze.rg} ${analyze.sg}", style: TextStyle(fontSize:   12,color: Colors.black45)),
                    //           ],
                    //         ),
                    //       ),
                    //     ],
                    //   ),
                    // );
                  },
                ),

              ),
            ],
          ),
        ],
      ),
    );
  }

  PopupMenuItem<int> popupItem({required String title, required int value}) {
    return PopupMenuItem(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [Text(title, style: appMainPrimaryTextStyle(fontSize: 14)), Divider()],
      ),
      value: value,
    );
  }

  Widget buildCall(AnalyzeEightCharModel analyze){
    // AnalyzeEightCharModel analyze= stories[index];
    int sex=analyze.sex!.toInt();
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
                routerCell(analyze);
              },
              child:
            Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text( "${analyze.createTime ?? ""}", style: TextStyle(fontSize:   14,color: Colors.black45)),
                Row(
                  children: [
                    Text(analyze.name??"", style: TextStyle(fontSize:   20,color: Colors.black)),
                    CircleBackgroundText(sex ==0 ? "女":"男",sex == '女'?Colors.yellow :Colors.orange,30),
                  ],
                ),
                Text(analyze.dateType!.toInt() ==5 ? "${analyze.year}-${analyze.month}-${analyze.day} ${analyze.hour}:00": "${analyze.ng} ${analyze.yg} ${analyze.rg} ${analyze.sg}", style: TextStyle(fontSize:   12,color: Colors.black45)),
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
  
  void routerCell(AnalyzeEightCharModel analyze){
    SplayedFigureFindModel sera=new SplayedFigureFindModel();
    // Map<String, String> addMap=new HashMap();
    sera.name=analyze.name;
    // addMap["name"]=sera.name ??"";

    if (sera.name == null || sera.name!.isEmpty) {
      BrnDialogManager.showSingleButtonDialog(context,
          label: "确定",
          title: '信息',
          warning: '请输入命主姓名', onTap: () {
            // BrnToast.show('知道了', context);
          });
      return;
    }

    sera.dateType=analyze.dateType!.toInt();
    // addMap["dateType"]=sera.dateType!.toString() ?? "5";

    if(sera.dateType == 4){
      sera.ng = analyze.ng;
      sera.yg = analyze.yg;
      sera.rg = analyze.rg;
      sera.sg = analyze.sg;
    }
    //日期排盘
    if(sera.dateType == 5){
      sera.year=analyze.year!.toInt();

      sera.month=analyze.month!.toInt();
      sera.day=analyze.day!.toInt();
      sera.hour=analyze.hour!.toInt();
      sera.minute=00;
      if(10 > sera.minute!) {
        sera.inputdate =
        "公历${sera.year}年${sera.month}月${sera.day}日 0${sera
            .hour}时${sera.minute}分";
      }else{
        sera.inputdate =
        "公历${sera.year}年${sera.month}月${sera.day}日 ${sera
            .hour}时${sera.minute}分";
      }
    }
    sera.sex = analyze.sex!.toInt();
    sera.paipanFs = analyze.isMajor!.toInt();

    //是否专业排盘
    if(sera.paipanFs == 2) {
      sera.ztys=analyze.ztys!.toInt();
      //如果时真太阳时
      if (sera.ztys  == 1) {
        sera.city1 = analyze.city1;
        sera.city2 = analyze.city2;
        sera.city3 = analyze.city3;
        if (sera.city1 == null || sera.city1!.isEmpty) {
          BrnDialogManager.showSingleButtonDialog(context,
              label: "确定",
              title: '信息',
              warning: '请选择地区', onTap: () {
                // BrnToast.show('知道了', context);
              });
          return;
        }
      }
      sera.sect = analyze.sect!.toInt();
      sera.siling = analyze.siling!.toInt();
    }
    
    SplayedFigureDetailScreen(search: sera,).launch(context);
  }
}
