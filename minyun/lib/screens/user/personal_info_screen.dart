import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_form_builder/flutter_form_builder.dart';
import 'package:flutter_lunar_datetime_picker/date_init.dart';
import 'package:flutter_lunar_datetime_picker/flutter_lunar_datetime_picker.dart';
import 'package:form_builder_validators/form_builder_validators.dart';
import 'package:image_picker/image_picker.dart';
import 'package:intl/intl.dart';
import 'package:lunar/calendar/Lunar.dart';
import 'package:lunar/calendar/Solar.dart';
import 'package:minyun/component/AppButton.dart';
import 'package:minyun/constant.dart';
import 'package:minyun/screens/sign_up_screen.dart';
import 'package:minyun/utils/AppCommon.dart';
import 'package:minyun/utils/AppWidget.dart';
import 'package:tdesign_flutter/tdesign_flutter.dart';

import '../../component/text_form_field_label_text.dart';
import 'package:minyun/utils/AppColors.dart';
import 'package:minyun/utils/AppContents.dart';
import '../../utils/images.dart';
import 'account_screen.dart';

class PersonalInfoScreen extends StatefulWidget {
  const PersonalInfoScreen({Key? key}) : super(key: key);

  @override
  State<PersonalInfoScreen> createState() => _PersonalInfoScreenState();
}

class _PersonalInfoScreenState extends State<PersonalInfoScreen> {
  final _formKey = GlobalKey<FormBuilderState>();
  List<String> genderOptions = ['男', '女'];

  final _nicknameFieldKey = GlobalKey<FormBuilderFieldState>();
  final _introFieldKey = GlobalKey<FormBuilderFieldState>();
  final _nameFieldKey = GlobalKey<FormBuilderFieldState>();
  final _emailFieldKey = GlobalKey<FormBuilderFieldState>();
  final _phoneNumberFieldKey = GlobalKey<FormBuilderFieldState>();
  final _birthdateFieldKey = GlobalKey<FormBuilderFieldState>();
  final _birthplaceFieldKey = GlobalKey<FormBuilderFieldState>();

  TextEditingController _nicknameController = TextEditingController();
  TextEditingController _introController = TextEditingController();
  TextEditingController _nameController = TextEditingController();
  TextEditingController _emailController = TextEditingController();
  TextEditingController _phoneNumberController = TextEditingController();
  TextEditingController _birthdateController = TextEditingController();
  TextEditingController _birthplaceController = TextEditingController();

  String selected_3 = '';
  String city1 = '';
  String city2 = '';
  String city3 = '';

  int _selectedYearDate = 1990;
  int _selectedMonthDate = 1;
  int _selectedDayDate = 1;
  int _selectedHourDate = 1;
  int _selectedMinuteDate=0;

  // 日期
  String? time = '1995-11-8 12:12';
  DateFormat formatter = DateFormat("yyyy-MM-dd Hh:mm");
  /// 是否是农历
  bool isLunar = true;

  Solar solar = Solar.fromDate(DateTime.now());
  Lunar lunar = Solar.fromDate(DateTime.now()).getLunar();

  final _picker = ImagePicker();

  String? dropdownValue;

  Future getImage() async {
    final pickedFile = await _picker.pickImage(source: ImageSource.gallery, imageQuality: 100);
    if (pickedFile != null) {
      image = File(pickedFile.path);
      setState(() {});
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          "个人信息",
          style: appMainBoldTextStyle(fontSize: 24),
        ),
        elevation: 0,
        iconTheme: IconThemeData(color: mode.theme ? Colors.white : Colors.black),
        actions: [
          IconButton(
            onPressed: () {},
            splashRadius: 22,
            icon: Image.asset(rename_image, height: 26, color: mode.theme ? Colors.white : Colors.black),
          ),
        ],
      ),
      body: SingleChildScrollView(
        padding: EdgeInsets.only(top: 16, right: 16, left: 16, bottom: 32),
        child:FormBuilder(
          key: _formKey,
          child:  Column(
            children: [
              Stack(
                children: [
                  Align(
                    alignment: Alignment.center,
                    child: Container(
                      width: 100,
                      height: 100,
                      decoration: BoxDecoration(
                        shape: BoxShape.circle,
                        color: mode.theme ? Colors.grey : Colors.grey.shade200,
                        image: DecorationImage(
                          image: image == null
                              ? Image.asset(sign_up_screen_person_image, color: Colors.grey, fit: BoxFit.contain).image
                              : Image.file(File(image!.path), fit: BoxFit.cover).image,
                          fit: BoxFit.cover,
                        ),
                      ),
                    ),
                  ),
                  Positioned(
                    bottom: 8,
                    right: 110,
                    child: GestureDetector(
                      onTap: () {
                        getImage();
                      },
                      child: Container(
                        alignment: Alignment.center,
                        padding: EdgeInsets.all(4),
                        decoration: BoxDecoration(color: primaryColor, borderRadius: BorderRadius.circular(DEFAULT_RADIUS)),
                        child: Icon(Icons.edit, color: Colors.white, size: 16),
                      ),
                    ),
                  ),
                ],
              ),
              SizedBox(height: 16),
              Column(
                children: [

                  // TextFormFieldLabelText(text: "昵称"),
                  // TextFormField(
                  //   decoration: inputDecoration(hintText: "昵称"),
                  // ),
                  FormBuilderTextField(
                    key: _nicknameFieldKey,
                    controller: _nicknameController,
                    name: '昵称',
                    decoration: const InputDecoration(labelText: '昵称'),
                    validator: FormBuilderValidators.compose([
                      FormBuilderValidators.required(),
                    ]),
                  ),
                  SizedBox(height: 16),
                  FormBuilderTextField(
                    key: _nameFieldKey,
                    controller: _nameController,
                    name: '姓名',
                    decoration: const InputDecoration(labelText: '姓名'),
                    validator: FormBuilderValidators.compose([
                      FormBuilderValidators.required(),
                    ]),
                  ),
                  SizedBox(height: 16),

                  FormBuilderTextField(
                    key: _introFieldKey,
                    controller: _introController,
                    name: '简介',
                    decoration: const InputDecoration(labelText: '简介'),
                    validator: FormBuilderValidators.compose([
                      FormBuilderValidators.required(),
                    ]),
                  ),
                  SizedBox(height: 16),
                  // TextFormFieldLabelText(text: "Email"),
                  FormBuilderTextField(
                    key: _emailFieldKey,
                    controller: _emailController,
                    name: 'email',
                    decoration: const InputDecoration(labelText: 'Email'),
                    validator: FormBuilderValidators.compose([
                      FormBuilderValidators.required(),
                      FormBuilderValidators.email(),
                    ]),
                  ),
                  SizedBox(height: 16),
                  // TextFormFieldLabelText(text: "手机号码"),
                  FormBuilderTextField(
                    key: _phoneNumberFieldKey,
                    controller: _phoneNumberController,
                    name: '手机号码',
                    decoration: const InputDecoration(labelText: '手机号码'),
                    validator: FormBuilderValidators.compose([
                      FormBuilderValidators.required(),
                      FormBuilderValidators.numeric(),
                    ]),
                  ),
                  // TextFormField(
                  //   decoration: inputDecoration(hintText: "手机号码"),
                  // ),
                  SizedBox(height: 16),
                  FormBuilderDropdown<String>(
                    name: '性别',
                    initialValue: '男',
                    decoration: InputDecoration(
                      labelText: '性别',
                      // suffix: IconButton(
                      //   // icon: const Icon(Icons.close),
                      //   onPressed: () {
                      //     _formKey.currentState!.fields['性别']
                      //         ?.reset();
                      //   },
                      // ),
                      hintText: '请选择性别',
                    ),
                    items: genderOptions
                        .map((gender) => DropdownMenuItem(
                      alignment: AlignmentDirectional.center,
                      value: gender,
                      child: Text(gender),
                    ))
                        .toList(),
                  ),
                  // TextFormFieldLabelText(text: "性别"),
                  // DropdownButtonFormField(
                  //   decoration: inputDecoration(hintText: '请选择性别'),
                  //   borderRadius: BorderRadius.circular(DEFAULT_RADIUS),
                  //   dropdownColor: mode.theme ? darkPrimaryLightColor : Colors.white,
                  //   value: dropdownValue,
                  //   icon: Icon(Icons.keyboard_arrow_down, size: 30, color: primaryColor),
                  //   style: TextStyle(fontSize: 18, color: mode.theme ? Colors.white : Colors.black),
                  //   items: genders.map((String items) {
                  //     return DropdownMenuItem(
                  //       value: items,
                  //       child: Text(
                  //         items,
                  //       ),
                  //     );
                  //   }).toList(),
                  //   onChanged: (String? newValue) {
                  //     setState(() {
                  //       dropdownValue = newValue!;
                  //     });
                  //   },
                  // ),
                  SizedBox(height: 16),
                  TextFormFieldLabelText(text: "生日（阳历）"),
                  TextFormField(
                    readOnly: true,
                    controller: dateInput,
                    decoration: inputDecoration(
                      hintText: "YYYY-MM-dd",
                      suffixIcon: GestureDetector(
                        onTap: () async {
                          DateTime? pickedDate = await showDatePicker(
                            context: context,
                            initialDate: DateTime.now(),
                            firstDate: DateTime(1900),
                            lastDate: DateTime.now(),
                          );
                          if (pickedDate != null) {
                            String formattedDate = DateFormat('yyyy-MM-dd HH').format(pickedDate);
                            setState(() {
                              dateInput.text = formattedDate;
                            });
                          } else {
                            print("Date is not selected");
                          }
                        },
                        child: Padding(
                          padding: const EdgeInsets.only(left: 16),
                          child: Icon(Icons.calendar_month_rounded, color: primaryColor),
                        ),
                      ),
                    ),
                  ),
                  SizedBox(height: 16),
                  TextFormFieldLabelText(text: "出生地址"),
                  TextFormField(
                    decoration: inputDecoration(hintText: "出生地址"),
                  ),
                  SizedBox(height: 16),
                  AppButton(
                      text: "保存",
                      onTap: () {
                        Navigator.pop(context);
                      }),
                ],
              )
            ],
          ),
        ),
      ),
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

          _birthdateController.text="${time.year}-${time.month}-${time.day} ${time.hour}:${time.minute}";
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

}
