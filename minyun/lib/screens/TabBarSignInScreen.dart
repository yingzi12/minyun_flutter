
import 'dart:async';

import 'package:awesome_dialog/awesome_dialog.dart';
import 'package:flutter/material.dart';
import 'package:minyun/api/SmsApi.dart';
import 'package:minyun/api/UserApi.dart';
import 'package:minyun/models/login_user_model.dart';
import 'package:minyun/models/sms_model.dart';
import 'package:minyun/screens/UserForgotPasswordScreen.dart';
import 'package:minyun/screens/bottom_navigation_bar_screen.dart';
import 'package:minyun/utils/AppColors.dart';
import 'package:minyun/utils/AppCommon.dart';
import 'package:minyun/utils/AppWidget.dart';
import 'package:minyun/utils/SecureStorage.dart';
import 'package:nb_utils/nb_utils.dart';
import 'package:tdesign_flutter/tdesign_flutter.dart';

/**
 * 登录/注册
 */
class TabBarSignInScreen extends StatefulWidget {
  final int selectedPage;

  TabBarSignInScreen(this.selectedPage);

  @override
  TabBarSignInScreenState createState() => TabBarSignInScreenState();
}

class TabBarSignInScreenState extends State<TabBarSignInScreen> with SingleTickerProviderStateMixin {
  TabController? _tabController;
  bool? checkedValue = false;

  TextEditingController contPhone = TextEditingController();

  TextEditingController contEmailAddress = TextEditingController();
  TextEditingController contPassword = TextEditingController();

  TextEditingController controllerName = TextEditingController();
  TextEditingController controllerEmailAddress = TextEditingController();
  TextEditingController controllerPassword = TextEditingController();

  FocusNode focusNodePassword = FocusNode();
  FocusNode addressFocusNode = FocusNode();
  FocusNode passwordFocusNode = FocusNode();

  bool isUsernameValid = true;
  bool isEmailValid = true;
  bool isPassworldValid = true;
  final regisFormKey = GlobalKey<FormState>();

  String inputText = '请输入...';
  var controller = [];
  var browseOn = false;
  var confirmText = '发送验证码';
  var countDownText = '重发';
  Timer? _timer;
  int _countdownTime = 0;

  String uuid="";
  String code="";

  @override
  void initState() {
    for (var i = 0; i < 28; i++) {
      controller.add(TextEditingController());
    }
    super.initState();
    init();
  }

  void startCountdownTimer() {
    const oneSec = Duration(seconds: 1);
    var callback = (timer) => {
      setState(() {
        if (_countdownTime < 1) {
          _timer?.cancel();
        } else {
          _countdownTime = _countdownTime - 1;
        }
      })
    };
    _timer = Timer.periodic(oneSec, callback);
  }


  Future<void> init() async {
    LoginUserModel? user = await SecureStorage().getLoginUser();
    if(user != null){
      BottomNavigationBarScreen(itemIndex: 0).launch(context);
    }else {
      SharedPreferences prefs = await SharedPreferences.getInstance();
      checkedValue = true; // 设置默认为选中状态
      String? email = prefs.getString('email');
      String? password = prefs.getString('password');
      if (email != null && password != null) {
        contEmailAddress.text = email;
        contPassword.text = password;
        checkedValue = true;
      }
    }
    //
  }

  @override
  void dispose() {
    _tabController?.dispose();

    super.dispose();
    if (_timer != null) {
      _timer!.cancel();
    }
  }


  @override
  void setState(fn) {
    if (mounted) super.setState(fn);
  }

  // 登录函数
  Future<void> login(String email, String password) async {
    if (checkedValue ?? false) {
      SharedPreferences prefs = await SharedPreferences.getInstance();
      await prefs.setString('email', email);
      await prefs.setString('password', password);
    }
    var response = await UserApi.login({ 'username': email, 'password': password});
    if (response.code == 200) {
      // 登录成功，处理响应
     // print('登录成功');
      BottomNavigationBarScreen(itemIndex: 0).launch(context);
      // 这里可以进一步处理如保存token、跳转到主页等操作
    }
  }

  // 注册函数
  Future<void> register(String name, String email, String password) async {
   bool ok=await  UserApi.regis({'username': name, 'email': email, 'password': password});
    if (ok) {
      AwesomeDialog(
        context: context,
        animType: AnimType.rightSlide,
        title: '信息',
        desc: '注册成功',
        btnOkOnPress: () {
          TabBarSignInScreen(0).launch(context);
        },
      )..show();
      // 注册成功，处理响应
     // print('注册成功');
      // 这里可以进一步处理如自动登录、跳转到登录页面等操作
    }else{
      AwesomeDialog(
        context: context,
        animType: AnimType.rightSlide,
        title: '信息',
        desc: '注册失败',
        // btnCancelOnPress: () {
        // },
        btnOkOnPress: () {
          // MPTabBarSignInScreen(0).launch(context);
        },
      )..show();
    }
  }

  Future<void> sendSms(String phone) async {
    SmsModel smsModel = await SmsApi.sendSms(phone);
    uuid = smsModel.uuid ?? "";
    code = smsModel.code ?? "";
  }

    //
  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      initialIndex: widget.selectedPage,
      length: 2,
      child: Scaffold(
        backgroundColor: mpAppBackGroundColor,
        appBar: AppBar(
          backgroundColor: mpAppBackGroundColor,
          automaticallyImplyLeading: false,
          bottomOpacity: 1,
          bottom: TabBar(
            unselectedLabelColor: mpAppTextColor,
            labelColor: white,
            tabs: [
              Tab(child: Text("登陆")),
              Tab(child: Text("注册")),
            ],
            indicatorColor: mpAppButtonColor,
            indicatorWeight: 3,
            indicatorSize: TabBarIndicatorSize.label,
          ),
        ),
        body: TabBarView(
          children: [
            SingleChildScrollView(
              child: Column(children: [
                AppTextField(
                  controller: contEmailAddress,
                  textStyle: appMainPrimaryTextStyle(color: white),
                  // nextFocus: focusNodePassword,
                  cursorColor: Colors.white,
                  textFieldType: TextFieldType.NAME,
                  decoration: buildInputDecoration('用户名/Email'),
                ),
                16.height,
                AppTextField(
                  controller: contPassword,
                  textStyle: appMainPrimaryTextStyle(color: white),
                  focus: focusNodePassword,
                  cursorColor: Colors.white,
                  textFieldType: TextFieldType.PASSWORD,
                  suffixIconColor: mpAppButtonColor,
                  decoration: buildInputDecoration("密码"),
                ),
                16.height,
                Align(
                  alignment: Alignment.topRight,
                  child: Text('忘记密码?', style: appMainPrimaryTextStyle(color: mpAppButtonColor, fontSize: 14)),
                ).onTap(() {
                   UserForgotPasswordScreen().launch(context);
                }),
                16.height,
                Theme(
                  data: ThemeData(unselectedWidgetColor: white),
                  child: CheckboxListTile(
                    contentPadding: EdgeInsets.all(0),
                    title: Text("记住账号", style: appMainSecondaryTextStyle(color: Colors.white)),
                    value: checkedValue,
                    dense: true,
                    onChanged: (newValue) {
                      setState(() {
                        checkedValue = newValue;
                      });
                    },
                    controlAffinity: ListTileControlAffinity.leading,
                  ),
                ),
                16.height,
                AppButton(
                  child: Text('登陆', style: appMainPrimaryTextStyle(color: Colors.white)),
                  color: mpAppButtonColor,
                  width: context.width(),
                  onTap: () {
                    login(contEmailAddress.text, contPassword.text);
                    // finish(context);
                    // MPDashboardScreen().launch(context);
                  },
                ).cornerRadiusWithClipRRect(24),
                16.height,
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Container(height: 0.5, width: 110, color: Colors.white.withOpacity(0.2)),
                    8.width,
                    Text('OR', style: TextStyle(color: Colors.grey)),
                    8.width,
                    Container(height: 0.5, width: 110, color: Colors.white.withOpacity(0.2)),
                  ],
                ),
                16.height,
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    socialButton(btnText: "返回首页", btnBgColor: mpFacebookBtnBgColor,onTap: ()=>{
                BottomNavigationBarScreen(itemIndex: 0).launch(context)
                    }),
                    // 24.width,
                    // socialButton(btnText: "Twitter", btnBgColor: mpTwitterBtnBgColor),
                  ],
                ),
              ]).paddingOnly(top: 70, right: 16, left: 16, bottom: 16),
            ),
            SingleChildScrollView(
            child: Form(
            key: regisFormKey,
              child: Column(
                children: [
                  _basicPhoneCodeBasic(context),
                  _specialTypePhoneNumber(context),
                  _specialTypeVerifyCode(context),
                  AppTextField(
                    controller: controllerName,
                    textStyle: appMainPrimaryTextStyle(color: white),
                    textFieldType: TextFieldType.EMAIL,
                    nextFocus: addressFocusNode,
                    cursorColor: Colors.white,
                    maxLength: 20,
                    decoration: buildInputDecoration('用户名'),
                    validator: (value) {
                      if (value == null || value.isEmpty || value.length < 6 || value.length > 30 || !RegExp(r'^[a-zA-Z0-9]+$').hasMatch(value)) {
                        return '用户名必须是6-30个字母或数字';
                      }
                      return null;
                    },
                  ),
                  16.height,
                  AppTextField(
                    controller: controllerEmailAddress,
                    textStyle: appMainPrimaryTextStyle(color: white),
                    textFieldType: TextFieldType.EMAIL,
                    focus: addressFocusNode,
                    cursorColor: Colors.white,
                    nextFocus: passwordFocusNode,
                    maxLength: 30,
                    decoration: buildInputDecoration('E-mail'),
                    validator: (value) {
                      if (value == null || value.isEmpty  || value.length < 6 || value.length > 50 ||  !RegExp(r'^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$').hasMatch(value)) {
                        return '请输入有效的电子邮件地址';
                      }
                      return null;
                    },
                  ),
                  16.height,
                  AppTextField(
                    controller: controllerPassword,
                    textStyle: appMainPrimaryTextStyle(color: white),
                    textFieldType: TextFieldType.PASSWORD,
                    focus: passwordFocusNode,
                    cursorColor: Colors.white,
                    maxLength: 20,
                    suffixIconColor: mpAppButtonColor,
                    decoration: buildInputDecoration('密码'),
                    validator: (value) {
                      if (value == null || value.isEmpty || value.length < 6 || value.length > 30) {
                        return '密码长度必须是6-30个字符';
                      }
                      return null;
                    },
                  ),
                  20.height,
                  AppButton(
                    child: Text('注册', style: appMainPrimaryTextStyle(color: Colors.white)),
                    color: mpAppButtonColor,
                    width: context.width(),
                    onTap: () {
                      if (regisFormKey.currentState!.validate()) {
                        register(controllerName.text, controllerEmailAddress.text,
                            controllerPassword.text);
                      }
                    },
                  ).cornerRadiusWithClipRRect(24),
                  24.height,
                ],
              )
            ).paddingOnly(top: 70, right: 16, left: 16, bottom: 16),
            ),
          ],
          controller: _tabController,
        ),
      ),
    );
  }

  Widget _specialTypeVerifyCode(BuildContext context) {
    return Column(
      children: [
        TDInput(
          type: TDInputType.normal,
          size: TDInputSize.small,
          controller: controller[13],
          leftLabel: '验证码',
          hintText: '输入验证码',
          backgroundColor: Colors.white,
          rightBtn: Row(
            mainAxisSize: MainAxisSize.min,
            children: [
              Container(
                width: 0.5,
                height: 24,
                color: TDTheme.of(context).grayColor3,
              ),
              const SizedBox(
                width: 16,
              ),
              Image.network(
                'https://img2018.cnblogs.com/blog/736399/202001/736399-20200108170302307-1377487770.jpg',
                width: 72,
                height: 36,
              )
            ],
          ),
          needClear: false,
          onBtnTap: () {
            TDToast.showText('点击更换验证码', context: context);
          },
        ),
        const SizedBox(
          height: 16,
        )
      ],
    );
  }

  Widget _basicPhoneCodeBasic(BuildContext context) {
    return Column(
      children: [
        TDInput(
          leftLabel: '手机验证码',
          controller: controller[0],
          backgroundColor: Colors.white,
          hintText: '请输入手机验证码',
          onChanged: (text) {
            setState(() {});
          },
          onClearTap: () {
            controller[0].clear();
            setState(() {});
          },
        ),
        const SizedBox(
          height: 16,
        )
      ],
    );
  }

  Widget _specialTypePhoneNumber(BuildContext context) {
    return Column(
      children: [
        TDInput(
          type: TDInputType.normal,
          controller: contPhone,
          leftLabel: '手机号',
          hintText: '输入手机号',
          backgroundColor: Colors.white,
          rightBtn: SizedBox(
            width: 98,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Padding(
                  padding: const EdgeInsets.only(right: 16),
                  child: Container(
                    width: 0.5,
                    height: 24,
                    color: TDTheme.of(context).grayColor3,
                  ),
                ),
                _countdownTime > 0
                    ? TDText(
                  '${countDownText}(${_countdownTime}秒)',
                  textColor: TDTheme.of(context).fontGyColor4,
                )
                    : TDText(confirmText, textColor: TDTheme.of(context).brandNormalColor),
              ],
            ),
          ),
          needClear: false,
          onBtnTap: () {
            if (_countdownTime == 0) {
              sendSms(contPhone.text);
              TDToast.showText('点击了发送验证码', context: context);
              setState(() {
                _countdownTime = 60;
              });
              startCountdownTimer();
            }
          },
        ),
        const SizedBox(
          height: 16,
        )
      ],
    );
  }
}

