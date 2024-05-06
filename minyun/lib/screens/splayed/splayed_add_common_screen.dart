import 'dart:collection';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';
import 'package:minyun/api/AnalyzeEightCharAnalyzeApi.dart';
import 'package:nb_utils/nb_utils.dart';
import 'package:tdesign_flutter/tdesign_flutter.dart';

class SplayedAddCommonScreen extends StatefulWidget {
  String uuid;

  SplayedAddCommonScreen({required this.uuid});

  @override
  _SplayedAddCommonScreenState createState() => _SplayedAddCommonScreenState();
}

class _SplayedAddCommonScreenState extends State<SplayedAddCommonScreen> {
  int _selectedShareValue = 1;
  TextEditingController _userIdController = TextEditingController();
  TextEditingController _phoneNumberController = TextEditingController();
  TextEditingController _labelController = TextEditingController();
  TextEditingController _introController = TextEditingController();

  String phoneNumberError="";
  String userIdError="";


  Future<void> _refreshSaveData(Map<String, String> addMap) async {
    addMap["uuid"]=widget.uuid;
    Map<String, dynamic> result= await AnalyzeEightCharAnalyzeApi.addModel(addMap);
    if(result["code"].toString().toInt == 200){
      _userIdController.clear();
      _phoneNumberController.clear();
      _labelController.clear();
      _introController.clear();
    }else{
    }
  }


  Widget build(BuildContext context){
    return AlertDialog(
      title: Text('点评'),
      content: SingleChildScrollView( // Wrap content with SingleChildScrollView
        child: Center(
          child: Column(
            children: [
              TextFormField(
                controller: _labelController,
                maxLines: null,
                keyboardType: TextInputType.multiline,
                inputFormatters: [LengthLimitingTextInputFormatter(20)],
                decoration: InputDecoration(
                  labelText: '请输入标签，多个用英文;分割',
                  border: OutlineInputBorder(),
                ),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return '输入不能为空';
                  }
                  if (value.length < 2 || value.length > 20) {
                    return '点评的长度必须在2到100之间';
                  }
                  return null; // 返回null表示输入有效
                },
              ),
              TextFormField(
                controller: _introController,
                maxLines: null,
                keyboardType: TextInputType.multiline,
                inputFormatters: [LengthLimitingTextInputFormatter(500)],
                decoration: InputDecoration(
                  labelText: '请输入点评',
                  border: OutlineInputBorder(),
                ),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return '点评不能为空';
                  }
                  if (value.length < 10 || value.length > 500) {
                    return '点评的长度必须在10到100之间';
                  }
                  return null; // 返回null表示输入有效
                },
              ),
              SizedBox(height: 16.0),
              buildShare(),
              if(_selectedShareValue.toInt()==1) buildUserId(context),
              if(_selectedShareValue.toInt()==1) buildPhone(context),
              Row(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    ElevatedButton(
                      onPressed: () {
                        // 验证输入框内容是否为空
                        if (_labelController.text.isEmpty ||
                            _introController.text.isEmpty ||
                            _labelController.text.length>20 ||
                            _labelController.text.length <2 ||
                            _introController.text.length>500 ||
                            _introController.text.length <10
                        ) {
                          // 如果有任何一个输入框内容为空，不执行提交操作
                          return;
                        }

                        if (_selectedShareValue ==1){
                            if(_phoneNumberController.text.isEmpty || _userIdController.text.isEmpty) {
                              // 如果有任何一个输入框内容为空，不执行提交操作
                              return;
                            }
                            if( _phoneNumberController.text.isNotEmpty &&  _phoneNumberController.text.length != 11 ){
                              return;
                            }
                            if( _userIdController.text.isNotEmpty &&  _userIdController.text.length >50 ){
                              return;
                            }
                        }

                        Map<String, String> addMap=new HashMap();
                        addMap['label']=_labelController.text;
                        addMap['intro']=_introController.text;
                        addMap['isSms']=_phoneNumberController.text;
                        addMap['isMessage']=_userIdController.text;

                        _refreshSaveData(addMap);
                        Navigator.pop(context,true);
                      },
                      child: Text('提交'),
                    ),
                    ElevatedButton(
                      onPressed: () {
                        Navigator.pop(context,false);
                      },
                      child: Text('取消'),
                    ),
                  ]
              )
            ],
          ),
        ),
      ),
    );
  }

  Widget buildShare(){
    return Row(
      children: [
        SizedBox(
          width: 90.0,
          child:
          Padding(
            padding: const EdgeInsets.only(left: 16),
            child: Text("通知用户："),
          ),
        ),
        Expanded(
          child: Row(
            mainAxisAlignment: MainAxisAlignment.start,
            children: [
              Expanded(
                child: Radio(
                  value: 1,
                  groupValue: _selectedShareValue,
                  onChanged: (value) {
                    setState(() {
                      _selectedShareValue = value as int;
                    });
                  },
                ),
              ),
              Text('是'),
              Expanded(
                child: Radio(
                  value: 2,
                  groupValue: _selectedShareValue, // Use a different groupValue
                  onChanged: (value) {
                    setState(() {
                      _selectedShareValue = value as int;
                    });
                  },
                ),
              ),
              Text('否'),
            ],
          ),
        ),
      ],
    );
  }


  Widget buildUserId(BuildContext context) {
    return Column(
      children: [
        TDInput(
          leftLabel: '系统',
          required: true,
          controller: _userIdController,
          backgroundColor: Colors.white,
          hintText: '请输入用户账号',
          additionInfo: userIdError,
          additionInfoColor: TDTheme.of(context).errorColor6,
          onChanged: (text) {
            print("请 onChanged 输入用户账号${text}");
            if (text == null || text.isEmpty) {
              userIdError= '输入不能为空';
            }
            setState(() {});
          },
          onClearTap: () {
            _userIdController.clear();
            setState(() {});
          },
        ),
        const SizedBox(
          height: 16,
        )
      ],
    );
  }


  Widget buildPhone(BuildContext context) {

    return Column(
      children: [
        TDInput(
          leftLabel: '短信',
          required: true,
          controller: _phoneNumberController,
          backgroundColor: Colors.white,
          hintText: '请输入用户电话号码',
          additionInfo: phoneNumberError,
          additionInfoColor: TDTheme.of(context).errorColor6,
          onChanged: (text) {
            print("请onChanged输入用户电话号码${text}");
            setState(() {
              if (text == null || text.isEmpty) {
                phoneNumberError= '输入不能为空';
              }
             // 使用正则表达式检查输入是否为11位数字
             if (!RegExp(r'^[0-9]{11}$').hasMatch(text)) {
                phoneNumberError='输入必须为11位数字';
              }
            });
          },
          onClearTap: () {
            print("请输onClearTap 入用户电话号码");

            _phoneNumberController.clear();
            setState(() {});
          },

        ),
        const SizedBox(
          height: 16,
        )
      ],
    );
  }



}
