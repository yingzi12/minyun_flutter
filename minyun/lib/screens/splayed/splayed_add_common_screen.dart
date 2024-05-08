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
  final _formKey = GlobalKey<FormState>(); // 添加全局Key来跟踪Form的状态

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
    if("200" == result["code"].toString()){
      _userIdController.clear();
      _phoneNumberController.clear();
      _labelController.clear();
      _introController.clear();
       Navigator.pop(context,true);

    }else{
      Navigator.pop(context,false);

    }
  }


  Widget build(BuildContext context){
    return AlertDialog(
      title: Text('点评'),
      content: SingleChildScrollView(
        // Wrap content with SingleChildScrollView
        child: Form( // 将AlertDialog包装在Form组件中
          key: _formKey, // 将全局Key分配给Form
          child:
          Center(
            child: Column(
              children: [
                TextFormField(
                  controller: _labelController,
                  maxLines: null,
                  keyboardType: TextInputType.multiline,
                  inputFormatters: [LengthLimitingTextInputFormatter(100)], // 调整了限制
                  decoration: InputDecoration(
                    labelText: '请输入标签，多个用英文;分割',
                    border: OutlineInputBorder(),
                  ),
                  validator: (value) {
                    print("validator ${value}");
                    if (value == null || value.isEmpty) {
                      return '输入不能为空';
                    }
                    List<String> labels = value.split(';'); // 通过分号拆分标签
                    for (String label in labels) {
                      if (label.trim().isEmpty) {
                        return '标签不能为空';
                      }
                      if (label.trim().length < 2 || label.trim().length > 20) {
                        return '标签长度必须在2到20之间';
                      }
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
                          // 调用FormState的validate()方法来触发验证器
                          if (_formKey.currentState!.validate()) {
                            // 如果验证通过，执行提交操作
                            _formKey.currentState!.save();
                            Map<String, String> addMap=new HashMap();
                            addMap['label']=_labelController.text;
                            addMap['intro']=_introController.text;
                            addMap['isSms']="0";
                            addMap['isMessage']="0";
                            if (_selectedShareValue ==1){
                              if(_phoneNumberController.text.isEmpty || _userIdController.text.isEmpty) {
                                // 如果有任何一个输入框内容为空，不执行提交操作
                                return;
                              }
                              if( _phoneNumberController.text.isNotEmpty &&  _phoneNumberController.text.length != 11 ){
                                return;
                              }else{
                                addMap['isSms']="1";
                                addMap['sendPhone']=_phoneNumberController.text;
                              }
                              if( _userIdController.text.isNotEmpty &&  _userIdController.text.length >50 ){
                                return;
                              }else{
                                addMap['isMessage']="1";
                                addMap['sendUserId']=_userIdController.text;
                              }
                            }
                            _refreshSaveData(addMap);
                          }

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
            setState(() {
              if (text == null || text.isEmpty) {
                userIdError= '输入不能为空';
                return;
              }});
            return;
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
                return;
              }
              // 使用正则表达式检查输入是否为11位数字
              if (!RegExp(r'^[0-9]{11}$').hasMatch(text)) {
                phoneNumberError='输入必须为11位数字';
                return;
              }
              phoneNumberError="";
            });
            return;
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
