
import 'dart:collection';

import 'package:bruno/bruno.dart';
import 'package:flutter/material.dart';
import 'package:flutter_lunar_datetime_picker/date_init.dart';
import 'package:flutter_lunar_datetime_picker/flutter_lunar_datetime_picker.dart';
import 'package:intl/intl.dart';
import 'package:lunar/lunar.dart';
import 'package:minyun/api/AnalyzeEightCharApi.dart';
import 'package:minyun/constant.dart';
import 'package:minyun/models/SplayedFigureFindModel.dart';
import 'package:minyun/models/SplayedFigureModel.dart';
import 'package:minyun/screens/TabBarSignInScreen.dart';
import 'package:minyun/screens/splayed_figure_detail_screen.dart';
import 'package:minyun/utils/AppColors.dart';
import 'package:minyun/utils/AppContents.dart';
import 'package:minyun/utils/SecureStorage.dart';
import 'package:minyun/utils/images.dart';
import 'package:nb_utils/nb_utils.dart';
import 'package:tdesign_flutter/tdesign_flutter.dart';

class UserSplayedFigureScreen extends StatefulWidget {
  const UserSplayedFigureScreen({Key? key}) : super(key: key);

  @override
  State<UserSplayedFigureScreen> createState() => _UserSplayedFigureScreenState();
}

class _UserSplayedFigureScreenState extends State<UserSplayedFigureScreen> {

  SplayedFigureModel? splayedFigureModel;
  bool isRefreshing = false; // 用于表示是否正在刷新数据
  GlobalKey<RefreshIndicatorState> refreshKey = GlobalKey<RefreshIndicatorState>();
  // 用于保存用户输入的文本
  TextEditingController _nameController = TextEditingController();
  TextEditingController _birthController = TextEditingController();
  final TextEditingController colorController = TextEditingController();
  final TextEditingController iconController = TextEditingController();
  String selected_2 = '';
  List<List<String>> data_2 = [];
  //
  int _selectedRenyuanValue = 0;
  //起盘方式
  int _selectedQipanValue=5;
  //排盘 是否专业
  int _selectedPaipanValue=1;
  //是否保存
  int _selectedSaveValue=2;

  //晚子时
  int _selectedLateValue=1;
  //真太阳时
  int _selectedSunValue=1;
  //性别
  int _selectedSexValue=0;

  String birthValue="";

  //下拉
  String _selectedYearValue = '甲子';
  String _selectedMonthValue = '甲子';
  String _selectedDayValue = '甲子';
  String _selectedHourValue = '甲子';

  int _selectedYearDate = 1990;
  int _selectedMonthDate = 1;
  int _selectedDayDate = 1;
  int _selectedHourDate = 1;
  int _selectedMinuteDate=0;

  String city1 = '';
  String city2 = '';
  String city3 = '';

  /// 日期
  String? time = '1995-11-8 12:12';
  DateFormat formatter = DateFormat("yyyy-MM-dd Hh:mm");
  /// 是否是农历
  bool isLunar = true;

  Solar solar = Solar.fromDate(DateTime.now());
  Lunar lunar = Solar.fromDate(DateTime.now()).getLunar();

  String selected_3 = '';

  @override
  void initState() {
    data_2.add(yearTianGanList);
    data_2.add(yearTianGanList);
    data_2.add(yearTianGanList);
    data_2.add(yearTianGanList);
    super.initState();
  }

  //获取指定月的天数
  int getDaysInMonth(int year, int month) {
    var a = Solar.fromYmd(year, month, 1);
    var b = a.nextMonth(1);
    // 返回差异的天数
    return b.subtract(a);
  }

  void _clearText() {
    setState(() {
      _nameController.clear();
    });
  }

  Future<void> _refreshSaveData( Map<String, String> addMap) async {
    AnalyzeEightCharApi.addModel(addMap);
  }

  // DateTime _selectedDate = DateTime.now();

  Future<void> _showDatePicker(BuildContext context) async {
    DatePicker.showDatePicker(
      context,
      lunarPicker: isLunar,
      dateInitTime: time == null
          ? DateInitTime(
          currentTime: DateTime.now(),
          maxTime: DateTime(2100, 12, 12),
          minTime: DateTime(1800, 2, 4))
          : DateInitTime(
          currentTime:
          DateFormat("yyyy-MM-dd h:m").parse(time ?? ""),
          maxTime: DateTime(2100, 12, 12),
          minTime: DateTime(1800, 1, 1)),
      onConfirm: (time, lunar) {
        debugPrint("onConfirm:${time.toString()} ${lunar.toString()}");
        setState(() {
          this.time =
          "${time.year}-${time.month}-${time.day} ${time.hour}:${time.minute}";
          _selectedYearDate = time.year;
          _selectedMonthDate = time.month;
          _selectedDayDate = time.day;
          _selectedHourDate = time.hour;
          _selectedMinuteDate = time.minute;

          _birthController.text="${time.year}-${time.month}-${time.day} ${time.hour}:${time.minute}";
          // DateTime dateTime = formatter.parse(time.toString());
          //isLunar=
          if(isLunar){
            Lunar lunar = Lunar.fromDate(time);
            this.lunar=lunar;
            this.solar = lunar.getSolar();
          }else{
            Solar solar = Solar.fromDate(time);
            this.solar=solar;
            this.lunar = solar.getLunar();
          }
          this.isLunar = isLunar;
        });
      },
      onChanged: (time, lunar) {
        debugPrint("onChanged:${time.toString()} ${lunar.toString()}");
      },
    );
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final height = MediaQuery.of(context).size.height;
    final width = MediaQuery.of(context).size.width;
    return Scaffold(
      appBar: AppBar(
        title: Text("排盘"),
        // titleTextStyle: appBoldTextStyle(fontSize: 24),
        elevation: 0,
        titleSpacing: 0,
        leading: Padding(
          padding: EdgeInsets.all(12.0),
          child: Image.asset(splash_screen_image, color: primaryColor),
        ),
      ),
      body: Stack(
        children: [
          ListView(
            children: [
              _basicTypeRequire(context),
              //排盘方式
              buildQipan(),
              _selectedQipanValue ==5 ? buildBirth(context) : buildXiala(),
              //buildTime(context),
              buildSex(),
              //真太阳时
              if (_selectedPaipanValue ==2)
                buildYesSun(),
              if (_selectedPaipanValue ==2 && _selectedSunValue ==1)
                buildMultiArea(context),
              if (_selectedPaipanValue ==2)
                buildLateSun(),
              //人元司令
              if (_selectedPaipanValue ==2)
                buildRenyuan(),
              buildPaipan(),
              buildSave(),

              TDButton(
                text: '开始排盘',
                size: TDButtonSize.large,
                type: TDButtonType.fill,
                shape: TDButtonShape.rectangle,
                theme: TDButtonTheme.defaultTheme,
                onTap: (){
                  SplayedFigureFindModel sera=new SplayedFigureFindModel();
                  Map<String, String> addMap=new HashMap();
                  sera.name=_nameController.text;
                  addMap["name"]=sera.name ??"";

                  if (sera.name == null || sera.name!.isEmpty) {
                    BrnDialogManager.showSingleButtonDialog(context,
                        label: "确定",
                        title: '信息',
                        warning: '请输入命主姓名', onTap: () {
                          // BrnToast.show('知道了', context);
                        });
                    return;
                  }

                  sera.dateType=_selectedQipanValue;
                  addMap["dateType"]=sera.dateType!.toString() ?? "5";

                  if(_selectedQipanValue == 4){
                    sera.ng = _selectedYearValue;
                    addMap["ng"]=sera.ng ?? "";
                    sera.yg = _selectedMonthValue;
                    addMap["yg"]=sera.yg ?? "";
                    sera.rg = _selectedDayValue;
                    addMap["rg"]=sera.rg ?? "";
                    sera.sg = _selectedHourValue;
                    addMap["sg"]=sera.sg ?? "";
                  }
                  //日期排盘
                  if(_selectedQipanValue == 5){
                    sera.year=_selectedYearDate;
                    addMap["year"]=sera.year!.toString() ?? "";

                    sera.month=_selectedMonthDate;
                    addMap["month"]=sera.month!.toString() ?? "";
                    sera.day=_selectedDayDate;
                    addMap["day"]=sera.day!.toString() ?? "";
                    sera.hour=_selectedHourDate;
                    addMap["hour"]=sera.hour!.toString() ?? "";
                    sera.minute=_selectedMinuteDate;
                    addMap["minute"]=sera.minute!.toString() ?? "";
                    if(10 > _selectedMinuteDate) {
                      sera.inputdate =
                      "公历${sera.year}年${sera.month}月${sera.day}日 0${sera
                          .hour}时${sera.minute}分";
                      addMap["inputdate"]=sera.inputdate ?? "";
                    }else{
                      sera.inputdate =
                      "公历${sera.year}年${sera.month}月${sera.day}日 ${sera
                          .hour}时${sera.minute}分";
                      addMap["inputdate"]=sera.inputdate ?? "";

                    }
                  }
                  sera.sex = _selectedSexValue;
                  addMap["sex"]=sera.dateType!.toString() ?? "0";
                  sera.paipanFs = _selectedPaipanValue;
                  addMap["isMajor"]=sera.dateType!.toString() ?? "1";

                  //是否专业排盘
                  if(_selectedPaipanValue==2) {
                    sera.ztys=_selectedSunValue;
                    addMap["ztys"]=sera.ztys!.toString() ?? "1";
                    //如果时真太阳时
                    if (_selectedSunValue == 1) {
                      sera.city1 = city1;
                      addMap["city1"]=sera.city1!.toString() ?? "1";
                      sera.city2 = city2;
                      addMap["city2"]=sera.city2!.toString() ?? "1";
                      sera.city3 = city3;
                      addMap["city3"]=sera.city3!.toString() ?? "1";
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
                    sera.sect = _selectedLateValue;
                    addMap["sect"]=sera.city1!.toString() ?? "1";
                    sera.siling = _selectedRenyuanValue;
                    addMap["siling"]=sera.city1!.toString() ?? "1";
                  }
                  sera.isSave=_selectedSaveValue;
                  if(sera.isSave ==1){
                    _refreshSaveData(addMap);
                  }
                  SplayedFigureDetailScreen(search: sera,).launch(context);
                }
              ),

            ],
          ),
        ],
      ),
    );
  }


  Widget buildQipan(){
    return Row(
      children: [
        SizedBox(
          width: 90.0,
          child:
          Padding(
            padding: const EdgeInsets.only(left: 16),
            child: Text("起盘方式：",
              // style: appBoldTextStyle(fontSize: 18)
            ),
          ),
        ),
        Expanded(
          child:
              Row(
                mainAxisAlignment: MainAxisAlignment.start,
                children: [
                  Expanded(
                    child: RadioListTile(
                      value: 5,
                      groupValue: _selectedQipanValue,
                      contentPadding: EdgeInsets.zero,
                      title: Text('日期排盘'),
                      onChanged: (value) {
                        setState(() {
                          _selectedQipanValue = value as int;
                        });
                      },
                    ),
                  ),
                  Expanded(
                    child: RadioListTile(
                      value: 4,
                      groupValue: _selectedQipanValue,
                      contentPadding: EdgeInsets.zero,
                      title: Text('八字反推'),
                      onChanged: (value) {
                        setState(() {
                          _selectedQipanValue = value as int;
                        });
                      },
                    ),
                  ),
                ],
              )
          // ),
        ),
      ],
    );
  }

  Widget buildLateSun(){
    return Row(
      children: [
        SizedBox(
          width: 90.0,
          child:
          Padding(
            padding: const EdgeInsets.only(left: 16),
            child: Text("晚子时：",
              // style: appBoldTextStyle(fontSize: 18)
            ),
          ),
        ),
        Expanded(
            child:
            Row(
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                Expanded(
                  child: RadioListTile(
                    value: 1,
                    groupValue: _selectedLateValue,
                    contentPadding: EdgeInsets.zero,
                    title: Text('按明天'),
                    onChanged: (value) {
                      setState(() {
                        _selectedLateValue = value as int;
                      });
                    },
                  ),
                ),
                Expanded(
                  child: RadioListTile(
                    value: 2,
                    groupValue: _selectedLateValue,
                    contentPadding: EdgeInsets.zero,
                    title: Text('按当天'),
                    onChanged: (value) {
                      setState(() {
                        _selectedLateValue = value as int;
                      });
                    },
                  ),
                ),
              ],
            )
          // ),
        ),
      ],
    );
  }

  //真太阳时
  Widget buildYesSun(){
    return Row(
      children: [
        SizedBox(
          width: 90.0,
          child:
          Padding(
            padding: const EdgeInsets.only(left: 16),
            child: Text("真太阳时：",
              // style: appBoldTextStyle(fontSize: 18)
            ),
          ),
        ),
        Expanded(
            child:
            Row(
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                Expanded(
                  child: RadioListTile(
                    value: 1,
                    groupValue: _selectedSunValue,
                    contentPadding: EdgeInsets.zero,
                    title: Text('是'),
                    onChanged: (value) {
                      setState(() {
                        _selectedSunValue = value as int;
                      });
                    },
                  ),
                ),
                Expanded(
                  child: RadioListTile(
                    value: 2,
                    groupValue: _selectedSunValue,
                    contentPadding: EdgeInsets.zero,
                    title: Text('否'),
                    onChanged: (value) {
                      setState(() {
                        _selectedSunValue = value as int;
                      });
                    },
                  ),
                ),
              ],
            )
          // ),
        ),
      ],
    );
  }

  Widget buildSex(){
    return Row(
      children: [
        SizedBox(
          width: 90.0,
          child:
          Padding(
            padding: const EdgeInsets.only(left: 16),
            child: Text("性别：",
              // style: appBoldTextStyle(fontSize: 18)
            ),
          ),
        ),
        Expanded(
            child:
            Row(
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                Expanded(
                  child: RadioListTile(
                    value: 0,
                    groupValue: _selectedSexValue,
                    contentPadding: EdgeInsets.zero,
                    title: Text('男'),
                    onChanged: (value) {
                      setState(() {
                        _selectedSexValue = value as int;
                      });
                    },
                  ),
                ),
                Expanded(
                  child: RadioListTile(
                    value: 1,
                    groupValue: _selectedSexValue,
                    contentPadding: EdgeInsets.zero,
                    title: Text('女'),
                    onChanged: (value) {
                      setState(() {
                        _selectedSexValue = value as int;
                      });
                    },
                  ),
                ),
              ],
            )
          // ),
        ),
      ],
    );
  }

  Widget buildPaipan(){
    return Row(
      children: [
        SizedBox(
          width: 90.0,
          child:
          Padding(
            padding: const EdgeInsets.only(left: 16),
            child: Text("排盘方式：",
              // style: appBoldTextStyle(fontSize: 18)
            ),
          ),
        ),
        Expanded(
            child:
            Row(
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                Expanded(
                  child: RadioListTile(
                    value: 1,
                    groupValue: _selectedPaipanValue,
                    contentPadding: EdgeInsets.zero,
                    title: Text('普通排盘'),
                    onChanged: (value) {
                      setState(() {
                        _selectedPaipanValue = value as int;
                      });
                    },
                  ),
                ),
                Expanded(
                  child: RadioListTile(
                    value: 2,
                    groupValue: _selectedPaipanValue,
                    contentPadding: EdgeInsets.zero,
                    title: Text('专业排盘'),
                    onChanged: (value) {
                      setState(() {
                        _selectedPaipanValue = value as int;
                      });
                    },
                  ),
                ),
              ],
            )
          // ),
        ),
      ],
    );
  }


  Widget buildSave(){
    return Row(
      children: [
        SizedBox(
          width: 90.0,
          child:
          Padding(
            padding: const EdgeInsets.only(left: 16),
            child: Text("是否保存：",
              // style: appBoldTextStyle(fontSize: 18)
            ),
          ),
        ),
        Expanded(
            child:
            Row(
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                Expanded(
                  child: RadioListTile(
                    value: 1,
                    groupValue: _selectedSaveValue,
                    contentPadding: EdgeInsets.zero,
                    title: Text('保存'),
                    onChanged: (value) {
                      var token =  SecureStorage().getLoginToken();

                      if(token ==null){
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            // 返回一个对话框
                            return AlertDialog(
                              title: new Text("警告"),
                              content: new Text("只有登陆之后才能保存，点击取消继续，点击确定跳转登陆页面"),
                              // preferredSize: Size.fromHeight(200.0), // 可选，设置对话框的大小
                              actions: <Widget>[
                                // 通常是按钮
                                new TextButton(
                                  child: new Text("取消"),
                                  onPressed: () {
                                    Navigator.of(context).pop();
                                  },
                                ),
                                new TextButton(
                                  child: new Text("登陆"),
                                  onPressed: () {
                                    TabBarSignInScreen(0).launch(context);
                                  },
                                ),
                              ],
                            );
                          },
                        );
                      }else {
                        setState(() {
                          _selectedSaveValue = value as int;
                        });
                      }
                    },
                  ),
                ),
                Expanded(
                  child: RadioListTile(
                    value: 2,
                    groupValue: _selectedSaveValue,
                    contentPadding: EdgeInsets.zero,
                    title: Text('不保存'),
                    onChanged: (value) {
                      setState(() {
                        _selectedSaveValue = value as int;
                      });
                    },
                  ),
                ),
              ],
            )
          // ),
        ),
      ],
    );
  }

  //人元司令
  Widget buildRenyuan(){
    return Row(
      children: [
        SizedBox(
          width: 90.0,
          child:
          Padding(
            padding: const EdgeInsets.only(left: 16),
            child: Text("人元司令：",
              // style: appBoldTextStyle(fontSize: 18)
            ),
          ),),
        Expanded(
          child:
            Wrap(
              spacing: 8.0, // 子组件之间的间距
              runSpacing: 4.0, // 换行之间的间距
              children: <Widget>[
                RadioListTile<int>(
                  title: const Text('子平真诠'),
                  value: 0,
                  groupValue: _selectedRenyuanValue,
                  onChanged: ( value) {
                    setState(() {
                      _selectedRenyuanValue = value as int;
                    });
                  },
                ),
                RadioListTile<int>(
                  title: const Text('三命通会'),
                  value: 1,
                  groupValue: _selectedRenyuanValue,
                  onChanged: ( value) {
                    setState(() {
                      _selectedRenyuanValue = value  as int;
                    });
                  },
                ),
                RadioListTile<int>(
                  title: const Text('渊海子平'),
                  value: 2,
                  groupValue: _selectedRenyuanValue,
                  onChanged: (value) {
                    setState(() {
                      _selectedRenyuanValue = value  as int;
                    });
                  },
                ),
                RadioListTile<int>(
                  title: const Text('神峰通考'),
                  value: 3,
                  groupValue: _selectedRenyuanValue,
                  onChanged: (value) {
                    setState(() {
                      _selectedRenyuanValue = value  as int;
                    });
                  },
                ),
                RadioListTile<int>(
                  title: const Text('星平会海'),
                  value: 4,
                  groupValue: _selectedRenyuanValue,
                  onChanged: (value) {
                    setState(() {
                      _selectedRenyuanValue = value  as int;
                    });
                  },
                ),
                RadioListTile<int>(
                  title: const Text('万育吾之法诀'),
                  value: 5,
                  groupValue: _selectedRenyuanValue,
                  onChanged: (value) {
                    setState(() {
                      _selectedRenyuanValue = value as int;
                    });
                  },
                ),
              ],
            ),
        ),
      ],
    );
  }

  Widget buildXiala(){
    return Row(
      children: [
        SizedBox(
          width: 90.0,
          child:
          Padding(
            padding: const EdgeInsets.only(left: 16),
            child: Text("出生时间：",
              // style: appBoldTextStyle(fontSize: 18)
            ),
          ),),
        Expanded(
          child:
          Wrap(
            spacing: 8.0, // 子组件之间的间距
            runSpacing: 4.0, // 换行之间的间距
            children: <Widget>[
              DropdownButton<String>(
                value: _selectedYearValue,
                items: <DropdownMenuItem<String>>[
                  for(var tiangan in yearTianGanList)
                    DropdownMenuItem(
                      value: tiangan,
                      child: Text(tiangan),
                    ),
                ],
                onChanged: (value) {
                  setState(() {
                    _selectedYearValue = value!;
                  });
                },
              ),
              DropdownButton<String>(
                value: _selectedMonthValue,
                items: <DropdownMenuItem<String>>[
                  for(var tiangan in yearTianGanList)
                    DropdownMenuItem(
                      value: tiangan,
                      child: Text(tiangan),
                    ),
                ],
                onChanged: (value) {
                  setState(() {
                    _selectedMonthValue = value!;
                  });
                },
              ),
              DropdownButton<String>(
                value: _selectedDayValue,
                items: <DropdownMenuItem<String>>[
                  for(var tiangan in yearTianGanList)
                    DropdownMenuItem(
                      value: tiangan,
                      child: Text(tiangan),
                    ),
                ],
                onChanged: (value) {
                  setState(() {
                    _selectedDayValue = value!;
                  });
                },
              ),
              DropdownButton<String>(
                value: _selectedHourValue,
                items: <DropdownMenuItem<String>>[
                  for(var tiangan in yearTianGanList)
                    DropdownMenuItem(
                      value: tiangan,
                      child: Text(tiangan),
                    ),
                ],
                onChanged: (value) {
                  setState(() {
                    _selectedHourValue = value!;
                  });
                },
              ),
            ],
          ),
        ),
      ],
    );
  }


  Widget getTitle(String title){
    return GestureDetector(
      onTap: () {
        // 在这里处理点击事件
        setState(() {
          // tyfx=generalAnalyzeCode[title]!;
        });
      },
      child: Chip(
        label: Text(title),
        // backgroundColor: tyfx == generalAnalyzeCode[title] ? Colors.grey : Colors.transparent,
        // selected: selectedChips[title],
      ),
    );
  }
  Widget buildTime(BuildContext context) {
    return GestureDetector(
      onTap: (){
        TDPicker.showMultiPicker(context, title: '选择时间',
            onConfirm: (selected) {
              setState(() {
                selected_2 = '${data_2[0][selected[0]]} ${data_2[1][selected[1]]}  ${data_2[2][selected[2]]}  ${data_2[3][selected[3]]}';
                print("-------${selected_2 }----------");
              });
            }, data: data_2
        );
      },
      child: buildSelectRow(context, selected_2, '选择时间'),
    );
  }
  Widget buildSelectRow(BuildContext context, String output, String title) {
    return Container(
      color: TDTheme.of(context).whiteColor1,
      height: 56,
      child: Stack(
        alignment: Alignment.bottomCenter,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Padding(
                padding: const EdgeInsets.only(left: 16, top: 16, bottom: 16),
                child: TDText(title, font: TDTheme.of(context).fontBodyLarge,),
              ),
              Expanded(child: Padding(
                padding: const EdgeInsets.only(right: 16, left: 16),
                child: Row(
                  children: [
                    Expanded(child: TDText(
                      output,
                      font: TDTheme.of(context).fontBodyLarge,
                      textColor: TDTheme.of(context).fontGyColor3.withOpacity(0.4),
                      maxLines: 1,
                      overflow: TextOverflow.ellipsis,
                    )),
                    Padding(
                      padding: const EdgeInsets.only(left: 2),
                      child: Icon(
                        TDIcons.chevron_right,
                        color: TDTheme.of(context).fontGyColor3.withOpacity(0.4),),
                    ),
                  ],
                ),
              )),
            ],
          ),
          const TDDivider(margin: EdgeInsets.only(left: 16, ),)
        ],
      ),
    );
  }
 //输入框
  Widget _basicTypeRequire(BuildContext context) {
    return Column(
      children: [
        TDInput(
          leftLabel: '姓名',
          required: true,
          controller: _nameController,
          backgroundColor: Colors.white,
          hintText: '请输入姓名',
          onChanged: (text) {
            setState(() {});
          },
          onClearTap: () {
            _nameController.clear();
            setState(() {});
          },
        ),
        const SizedBox(
          height: 16,
        )
      ],
    );
  }

  Widget buildBirth(BuildContext context) {
    return GestureDetector(
      onTap: (){
        _showDatePicker(context);
      },
      child: Container(
        color: TDTheme.of(context).whiteColor1,
        height: 56,
        child: Stack(
          alignment: Alignment.bottomCenter,
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Padding(
                  padding: const EdgeInsets.only(left: 16, top: 16, bottom: 16),
                  child: TDText("选择时间", font: TDTheme.of(context).fontBodyLarge,),
                ),
                Expanded(child: Padding(
                  padding: const EdgeInsets.only(right: 16, left: 16),
                  child: Row(
                    children: [
                      Expanded(child: Text(
                        this.time.toString(),
                        maxLines: 1,
                        overflow: TextOverflow.ellipsis,
                      )
                      ),
                      Padding(
                        padding: const EdgeInsets.only(left: 2),
                        child: Icon(
                          TDIcons.calendar,
                          color: TDTheme.of(context).fontGyColor3.withOpacity(0.4),),
                      ),
                    ],
                  ),
                )),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget getRadio(String title,String key,String value1,String value2){
    return Container(
      margin: EdgeInsets.symmetric(vertical: 8),
      padding: EdgeInsets.all(16),
      decoration: BoxDecoration(
          color:  Colors.grey.shade200, borderRadius: BorderRadius.circular(DEFAULT_RADIUS)),
      child: Row(
        children: [
          Padding(
            padding: const EdgeInsets.only(left: 16),
            child: Text("起盘方式：",
            ),
          ),
          Expanded(
              child:TDRadioGroup(
                selectId: 'index:1',
                direction: Axis.horizontal,
                onRadioGroupChange: (String? selectedId)=>{
                  print("----${selectedId}------")
                },
                directionalTdRadios: const [
                  TDRadio(
                    id: '0',
                    title: '日期排盘',
                    radioStyle: TDRadioStyle.circle,
                    showDivider: false,
                  ),
                  TDRadio(
                    id: '1',
                    title: '八字反推',
                    radioStyle: TDRadioStyle.circle,
                    showDivider: false,
                  ),
                ],
              )
          ),
        ],
      ),
    );
  }


  Widget buildMultiArea(BuildContext context) {
    return GestureDetector(
      onTap: (){
        TDPicker.showMultiLinkedPicker(context, title: '选择地区',
            onConfirm: (selected) {
              setState(() {
                selected_3 = '${selected[0]} ${selected[1]} ${selected[2]}';
                city1=selected[0];
                city2=selected[1];
                city3=selected[2];

              });
              Navigator.of(context).pop();
            },
            data: cityData,
            columnNum: 3,
            initialData: ['浙江省', '杭州市', '西湖区']);
      },
      child: buildSelectRow(context, selected_3, '选择地区'),
    );
  }
}
