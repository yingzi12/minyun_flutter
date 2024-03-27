import 'package:bruno/bruno.dart';
import 'package:china_city_selector/area_analyzer.dart';
import 'package:china_city_selector/area_result.dart';
import 'package:china_city_selector/selector_area.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_lunar_datetime_picker/date_init.dart';
import 'package:flutter_lunar_datetime_picker/flutter_lunar_datetime_picker.dart';
import 'package:intl/intl.dart';
import 'package:lunar/lunar.dart';
import 'package:minyun/models/SplayedFigureFindModel.dart';
import 'package:minyun/models/SplayedFigureModel.dart';
import 'package:minyun/screens/splayed_figure_detail_screen.dart';
import 'package:minyun/utils/constant.dart';
import 'package:nb_utils/nb_utils.dart';
import 'package:tdesign_flutter/tdesign_flutter.dart';
import '../utils/color.dart';
import '../utils/common.dart';
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

  int _singleSelectedIndex=0;
  int? _sexRadio = 0; // 初始化选中第一项

  /// 日期
  String? time = '1995-11-8 12:12';
  DateFormat formatter = DateFormat("yyyy-MM-dd Hh:mm");
  /// 是否是农历
  bool isLunar = true;

  Lunar lunar = Lunar.fromDate(DateTime.now());
  Solar solar = Solar.fromDate(DateTime.now());

   List<String> list = <String>['One', 'Two', 'Three', 'Four'];


  @override
  void initState() {
    var list = <String>[];
    for(var i = 2022; i >= 2000; i--) {
      list.add('${i}年');
    }
    data_2.add(list);
    data_2.add(['春', '夏', '秋', '冬']);
    data_2.add(['春', '夏', '秋', '冬']);
    data_2.add(['春', '夏', '秋', '冬']);

    super.initState();
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
          Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              _basicTypeRequire(context),
              Row(
                children: [
                  Padding(
                    padding: const EdgeInsets.only(left: 16),
                    child: Text("起盘方式：",
                        // style: boldTextStyle(fontSize: 18)
                    ),
                  ),
                  Expanded(
                    child:TDRadioGroup(
                      selectId: 'index:1',
                      direction: Axis.horizontal,
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
              Row(
                children: [
                  Padding(
                    padding: const EdgeInsets.only(left: 16),
                    child: Text("命主性别：",
                        // style: boldTextStyle(fontSize: 18)
                    ),
                  ),
                  Expanded(
                      child:TDRadioGroup(
                        selectId: 'index:1',
                        direction: Axis.horizontal,
                        directionalTdRadios: const [
                          TDRadio(
                            id: '0',
                            title: '男',
                            radioStyle: TDRadioStyle.circle,
                            showDivider: false,
                          ),
                          TDRadio(
                            id: '1',
                            title: '女',
                            radioStyle: TDRadioStyle.circle,
                            showDivider: false,
                          ),
                        ],
                      )
                  ),
                ],
              ),

              buildTime(context),
              // _horizontalRadios(context),
              Center(
                child: OutlinedButton(
                  onPressed: () async {
                    final nation = AreaAnalyzer.analyzeNation();
                    final provinces = nation.allProvinces.values.toList();
                    provinces.sort((a, b) => a.value.compareTo(b.value));
                    print(provinces);
                  },
                  child: const Text('print all provinces'),
                ),
              ),
              Padding(
                padding: const EdgeInsets.fromLTRB(10, 0, 10, 0),
                child: SelectorArea(
                  onNewArea: (AreaResult newArea) async {
                    print(newArea);
                  },
                ),
              ),
              Row(
                children: [
                  Padding(
                    padding: const EdgeInsets.only(left: 16),
                    child: Text("出生时间：",
                        // style: boldTextStyle(fontSize: 18)
                    ),
                  ),
                  Expanded(
                      child: TextField(
                        controller: _birthController,
                        decoration: InputDecoration(
                          labelText: 'Select a date',
                          suffixIcon: IconButton(
                            icon: Icon(Icons.calendar_today),
                            iconSize: 24.0,
                            onPressed: () => _showDatePicker(context),
                          ),
                        ),
                        onTap: () => _showDatePicker(context), // 当TextField被点击时弹出日期选择器
                        readOnly: true, // 设置为只读，因为日期将通过日期选择器设置
                      )
                  ),
                ],
              ),
              getPp(),
              TDButton(
                text: '填充按钮',
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

  Widget buildTime(BuildContext context) {
    return GestureDetector(
      onTap: (){
        TDPicker.showMultiPicker(context, title: '选择时间',
            onConfirm: (selected) {
              setState(() {
                selected_2 = '${data_2[0][selected[0]]} ${data_2[1][selected[1]]}';
              });
            }, data: data_2);
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

  //横向
  Widget _horizontalRadios(BuildContext context) {
    return TDRadioGroup(
      selectId: 'index:1',
      direction: Axis.horizontal,
      directionalTdRadios: const [
        TDRadio(
          id: '0',
          title: '单选标题',
          radioStyle: TDRadioStyle.circle,
          showDivider: false,
        ),
        TDRadio(
          id: '1',
          title: '单选标题',
          radioStyle: TDRadioStyle.circle,
          showDivider: false,
        ),
        TDRadio(
          id: '2',
          title: '上限四字',
          radioStyle: TDRadioStyle.circle,
          showDivider: false,
        ),
      ],
    );
  }

  Widget getPp(){
    return Container(
      margin: EdgeInsets.symmetric(vertical: 8),
      padding: EdgeInsets.all(16),
      decoration: BoxDecoration(
          color:  Colors.grey.shade200, borderRadius: BorderRadius.circular(DEFAULT_RADIUS)),
      child: Row(
        children: [
          Expanded(
            flex: 1,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  // dashboardFilesList[index].titleText.toString(),
                  "专业排盘",
                  // style: primaryTextStyle(),
                  overflow: TextOverflow.fade,
                ),
                SizedBox(height: 16),
                // CircleBackgroundText(lunarTime.getTianShenLuck(),lunarTime.getTianShenLuck() == '吉'?Colors.yellow :Colors.red,30)
              ],
            ),
          ),

          SizedBox(width: 16),
          Expanded(
            flex: 7,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                _horizontalCardStyle(context)
              ],
            ),
          ),

        ],
      ),
    );
  }

  Widget _horizontalCardStyle(BuildContext context) {
    return TDRadioGroup(
      selectId: 'index:1',
      cardMode: true,
      direction: Axis.horizontal,
      directionalTdRadios: const [
        TDRadio(
          id: 'index:0',
          title: '单选',
          cardMode: true,
        ),
        TDRadio(
          id: 'index:1',
          title: '单选',
          cardMode: true,
        ),
        TDRadio(
          id: 'index:2',
          title: '单选',
          cardMode: true,
        ),
        TDRadio(
          id: 'index:3',
          title: '单选',
          cardMode: true,
        ),
      ],
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
