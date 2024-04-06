
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_lunar_datetime_picker/date_init.dart';
import 'package:flutter_lunar_datetime_picker/flutter_lunar_datetime_picker.dart';
import 'package:intl/intl.dart';
import 'package:lunar/lunar.dart';
import 'package:minyun/constant.dart';
import 'package:minyun/models/SplayedFigureFindModel.dart';
import 'package:minyun/models/SplayedFigureModel.dart';
import 'package:minyun/screens/splayed_figure_detail_screen.dart';
import 'package:minyun/utils/constant.dart';
import 'package:nb_utils/nb_utils.dart';
import 'package:tdesign_flutter/tdesign_flutter.dart';
import '../utils/color.dart';
import '../utils/images.dart';

class SplayedFigureScreen extends StatefulWidget {
  const SplayedFigureScreen({Key? key}) : super(key: key);

  @override
  State<SplayedFigureScreen> createState() => _SplayedFigureScreenState();
}

class _SplayedFigureScreenState extends State<SplayedFigureScreen> {

  SplayedFigureModel? splayedFigureModel;
  bool isRefreshing = false; // 用于表示是否正在刷新数据
  GlobalKey<RefreshIndicatorState> refreshKey = GlobalKey<RefreshIndicatorState>();
  // 用于保存用户输入的文本
  TextEditingController _nameController = TextEditingController();
  TextEditingController _birthController = TextEditingController();
  final TextEditingController colorController = TextEditingController();
  final TextEditingController iconController = TextEditingController();
  ColorLabel? selectedColor;
  IconLabel? selectedIcon;
  String selected_2 = '';
  List<List<String>> data_2 = [];
  String xz="1";
  //起盘方式
  int _selectedQipanValue=1;
  //排盘
  int _selectedPaipanValue=1;
  //晚子时
  int _selectedLateValue=1;
  //真太阳时
  int _selectedSunValue=1;
  //性别
  int _selectedSexValue=1;
  //生日
  int _selectedBirthValue=1;

  String birthValue="";

  //下拉
   String _selectedYearValue = '甲子';
  String _selectedMonthValue = '甲子';
  String _selectedDayValue = '甲子';
  String _selectedHourValue = '甲子';

  /// 日期
  String? time = '1995-11-8 12:12';
  DateFormat formatter = DateFormat("yyyy-MM-dd Hh:mm");
  /// 是否是农历
  bool isLunar = true;

  Lunar lunar = Lunar.fromDate(DateTime.now());
  Solar solar = Solar.fromDate(DateTime.now());

   List<String> list = <String>['One', 'Two', 'Three', 'Four'];

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
        // titleTextStyle: boldTextStyle(fontSize: 24),
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
              _selectedQipanValue ==1 ? buildBirth(context) : buildXiala(),
              buildTime(context),
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

              TDButton(
                text: '开始排盘',
                size: TDButtonSize.large,
                type: TDButtonType.fill,
                shape: TDButtonShape.rectangle,
                theme: TDButtonTheme.defaultTheme,
                onTap: (){
                  SplayedFigureFindModel sera=new SplayedFigureFindModel();
                  sera.year=1994;
                  sera.month=3;
                  sera.day=23;
                  sera.hour=11;
                  sera.minute=11;

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
              // style: boldTextStyle(fontSize: 18)
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
                      value: 2,
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
              // style: boldTextStyle(fontSize: 18)
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
              // style: boldTextStyle(fontSize: 18)
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
              // style: boldTextStyle(fontSize: 18)
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
                    value: 2,
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
              // style: boldTextStyle(fontSize: 18)
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

  //横
  Widget buildRadioCross(){
    return Row(
      children: [
        SizedBox(
          // flex: 3,
          child:
          Padding(
            padding: const EdgeInsets.only(left: 16),
            child: Text("出生时间：",
              // style: boldTextStyle(fontSize: 18)
            ),
          ),
        ),
        Expanded(
          // flex: 9,
          child:  Center(
            child:
            Row(
              children: [
                Expanded(
                  child: RadioListTile(
                    value: 1,
                    groupValue: _selectedBirthValue,
                    title: Text('选项一'),
                    onChanged: (value) {
                      setState(() {
                        _selectedBirthValue = value as int;
                      });
                    },
                  ),
                ),
                Expanded(
                  child: RadioListTile(
                    value: 2,
                    groupValue: _selectedBirthValue,
                    title: Text('选项二'),
                    onChanged: (value) {
                      setState(() {
                        _selectedBirthValue = value as int;
                      });
                    },
                  ),
                ),
              ],
            )
          ),
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
              // style: boldTextStyle(fontSize: 18)
            ),
          ),),
        Expanded(
          child:
            Wrap(
              spacing: 8.0, // 子组件之间的间距
              runSpacing: 4.0, // 换行之间的间距
              children: <Widget>[
                RadioListTile<String>(
                  title: const Text('子平真诠'),
                  value: "1",
                  groupValue: xz,
                  onChanged: ( value) {
                    setState(() {
                      xz = value!;
                    });
                  },
                ),
                RadioListTile<String>(
                  title: const Text('三命通会'),
                  value: "2",
                  groupValue: xz,
                  onChanged: ( value) {
                    setState(() {
                      xz = value!;
                    });
                  },
                ),
                RadioListTile<String>(
                  title: const Text('渊海子平'),
                  value: "3",
                  groupValue: xz,
                  onChanged: (value) {
                    setState(() {
                      xz = value!;
                    });
                  },
                ),
                RadioListTile<String>(
                  title: const Text('神峰通考'),
                  value: "4",
                  groupValue: xz,
                  onChanged: (value) {
                    setState(() {
                      xz = value!;
                    });
                  },
                ),
                RadioListTile<String>(
                  title: const Text('星平会海'),
                  value: "5",
                  groupValue: xz,
                  onChanged: (value) {
                    setState(() {
                      xz = value!;
                    });
                  },
                ),
                RadioListTile<String>(
                  title: const Text('万育吾之法诀'),
                  value: "6",
                  groupValue: xz,
                  onChanged: (value) {
                    setState(() {
                      xz = value!;
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
              // style: boldTextStyle(fontSize: 18)
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
          leftLabel: '标签文字',
          required: true,
          controller: _nameController,
          backgroundColor: Colors.white,
          hintText: '请输入文字',
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
enum ColorLabel {
  blue('测试1', Colors.blue),
  pink('测试2', Colors.pink),
  green('测试3', Colors.green),
  yellow('测试4', Colors.orange),
  grey('测试5', Colors.grey);

  const ColorLabel(this.label, this.color);
  final String label;
  final Color color;
}

// DropdownMenuEntry labels and values for the second dropdown menu.
enum IconLabel {
  smile('Smile', Icons.sentiment_satisfied_outlined),
  cloud(
    'Cloud',
    Icons.cloud_outlined,
  ),
  brush('Brush', Icons.brush_outlined),
  heart('Heart', Icons.favorite);

  const IconLabel(this.label, this.icon);
  final String label;
  final IconData icon;
}
