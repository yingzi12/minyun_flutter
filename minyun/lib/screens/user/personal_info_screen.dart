import 'dart:io';

import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:intl/intl.dart';
import 'package:minyun/component/AppButton.dart';
import 'package:minyun/screens/sign_up_screen.dart';
import 'package:minyun/utils/AppCommon.dart';

import '../../component/text_form_field_label_text.dart';
import 'package:minyun/utils/AppColors.dart';
import 'package:minyun/utils/AppContents.dart';
import '../../utils/images.dart';
import '../../utils/lists.dart';
import 'account_screen.dart';

class PersonalInfoScreen extends StatefulWidget {
  const PersonalInfoScreen({Key? key}) : super(key: key);

  @override
  State<PersonalInfoScreen> createState() => _PersonalInfoScreenState();
}

class _PersonalInfoScreenState extends State<PersonalInfoScreen> {
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
          style: boldTextStyle(fontSize: 24),
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
        child: Column(
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
                TextFormFieldLabelText(text: "昵称"),
                TextFormField(
                  decoration: inputDecoration(hintText: "昵称"),
                ),
                SizedBox(height: 16),
                TextFormFieldLabelText(text: "简介"),
                TextFormField(
                  decoration: inputDecoration(hintText: "简介"),
                ),
                TextFormFieldLabelText(text: "姓名"),
                TextFormField(
                  decoration: inputDecoration(hintText: "姓名"),
                ),

                SizedBox(height: 16),
                TextFormFieldLabelText(text: "Email"),
                TextFormField(
                  decoration: inputDecoration(hintText: "Email"),
                ),
                SizedBox(height: 16),
                TextFormFieldLabelText(text: "手机号码"),
                TextFormField(
                  decoration: inputDecoration(hintText: "手机号码"),
                ),
                SizedBox(height: 16),
                TextFormFieldLabelText(text: "性别"),
                DropdownButtonFormField(
                  decoration: inputDecoration(hintText: '请选择性别'),
                  borderRadius: BorderRadius.circular(DEFAULT_RADIUS),
                  dropdownColor: mode.theme ? darkPrimaryLightColor : Colors.white,
                  value: dropdownValue,
                  icon: Icon(Icons.keyboard_arrow_down, size: 30, color: primaryColor),
                  style: TextStyle(fontSize: 18, color: mode.theme ? Colors.white : Colors.black),
                  items: genders.map((String items) {
                    return DropdownMenuItem(
                      value: items,
                      child: Text(
                        items,
                      ),
                    );
                  }).toList(),
                  onChanged: (String? newValue) {
                    setState(() {
                      dropdownValue = newValue!;
                    });
                  },
                ),
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
                          String formattedDate = DateFormat('yyyy-MM-dd').format(pickedDate);
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
    );
  }
}
