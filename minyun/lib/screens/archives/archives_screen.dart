import 'dart:collection';

import 'package:bruno/bruno.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:minyun/api/AnalyzeEightCharApi.dart';
import 'package:minyun/models/ResultListModel.dart';
import 'package:minyun/models/SplayedFigureFindModel.dart';
import 'package:minyun/models/analyze_eight_char_model.dart';
import 'package:minyun/models/login_user_model.dart';
import 'package:minyun/screens/TabBarSignInScreen.dart';
import 'package:minyun/screens/archives/archives_eight_char_screen.dart';
import 'package:minyun/screens/splayed/splayed_figure_detail_screen.dart';
import 'package:minyun/screens/user/account_screen.dart';
import 'package:minyun/screens/calendar_screen.dart';

import 'package:minyun/utils/AppColors.dart';
import 'package:minyun/utils/AppContents.dart';
import 'package:minyun/utils/SecureStorage.dart';
import 'package:nb_utils/nb_utils.dart';
import 'package:tdesign_flutter/tdesign_flutter.dart';
import '../../utils/AppCommon.dart';
import '../../utils/images.dart';

/**
 *档案
 */
class ArchivesScreen extends StatefulWidget {
  ArchivesScreen({super.key});

  @override
  State<ArchivesScreen> createState() => _ArchivesScreenState();
}

class _ArchivesScreenState extends State<ArchivesScreen> {
  List<AnalyzeEightCharModel> stories = [];


  bool isLogin=false;
  @override
  void initState() {
    super.initState();
    _refreshApiData();
  }

  @override
  void dispose() {
    super.dispose();
  }


  Future<void> _refreshApiData() async {
    LoginUserModel? loginUser = await SecureStorage().getLoginUser();
    if (loginUser != null) {
      setState(() {
        isLogin=true;
      });
    }
    // timely=嫁娶&startDate=2024-02-03&endDate=2024-03-19
    Map<String, String> queryParams=new HashMap();
    // queryParams["timely"] = timely;
    // queryParams["startDate"] = start;
    // queryParams["endDate"] = end;
    ResultListModel<AnalyzeEightCharModel> resultModel = await AnalyzeEightCharApi.getList(queryParams);
    setState(() {
      stories=resultModel.data??[];
    });
  }

  Future<void> _delApiData(AnalyzeEightCharModel eightCharModel) async {
    // timely=嫁娶&startDate=2024-02-03&endDate=2024-03-19
    Map<String, String> queryParams=new HashMap();
    // queryParams["timely"] = timely;
    // queryParams["startDate"] = start;
    // queryParams["endDate"] = end;
    Map<String,dynamic> result = await AnalyzeEightCharApi.deleteModel(eightCharModel.id.toString(),eightCharModel.uuid.toString());
    if(200 == result["code"].toString()){
      _refreshApiData();
      Get.dialog(
        AlertDialog(
          title: Text("信息"),
          content: Text("删除成功"),
          actions: <Widget>[
            TextButton(
              child: Text("确定"),
              onPressed: () {
                Get.back(); // 关闭对话框
              },
            ),
          ],
        ),
        barrierDismissible: false, // 点击对话框外部不关闭对话框
      );

    }else{
      Get.dialog(
        AlertDialog(
          title: Text("信息"),
          content: Text("删除失败"),
          actions: <Widget>[
            TextButton(
              child: Text("确定"),
              onPressed: () {
                Get.back(); // 关闭对话框
              },
            ),
          ],
        ),
        barrierDismissible: false, // 点击对话框外部不关闭对话框
      );
    }
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
          isLogin==false?
              Center(
                child:Column(children: [
            Text("请先登录",style: appMainBoldTextStyle(),),
            TextButton(onPressed: (){
              TabBarSignInScreen(0).launch(context);
            },
              child: Text("登录",style: appMainBoldTextStyle(),),
            )
        ],)):
          Column(
            children: [
              Row(
                children: [
                  Padding(
                    padding: const EdgeInsets.only(left: 16),
                    child: Text("共 ${stories.length} 条", style: appMainBoldTextStyle(fontSize: 18)),
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
        children: [Text(title, style: appMainPrimaryTextStyle()), Divider()],
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
                Text( "${analyze.createTime ?? ""}", style: appMainSecondaryTextStyle()),
                Row(
                  children: [
                    Text(analyze.name??"", style: appMainSecondaryTextStyle(fontSize:   20)),
                    CircleBackgroundText(sex ==0 ? "女":"男",sex == '女'?Colors.yellow :Colors.orange,30),
                  ],
                ),
                Text(analyze.dateType!.toInt() ==5 ? "${analyze.year}-${analyze.month}-${analyze.day} ${analyze.hour}:00": "${analyze.ng} ${analyze.yg} ${analyze.rg} ${analyze.sg}", style: appMainSecondaryTextStyle(fontSize:   12)),
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
                      showGeneralDialog(
                        context: context,
                        pageBuilder: (BuildContext buildContext, Animation<double> animation,
                            Animation<double> secondaryAnimation) {
                          return TDAlertDialog(
                            title: "信息",
                            content: "是否确定删除",
                            rightBtnAction: (){
                              _delApiData(analyze);
                            },
                          );
                        },
                      );
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
                       ArchivesEightCharScreen(analyze: analyze).launch(context);
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
    sera.uuid=analyze.uuid;

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
