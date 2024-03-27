//
// import 'package:awesome_dialog/awesome_dialog.dart';
// import 'package:flutter/material.dart';
// import 'package:minyun/locale/AppLocalizations.dart';
// import 'package:minyun/screen/ForgotPasswordScreen.dart';
// import 'package:minyun/api/UserApi.dart';
// import 'package:nb_utils/nb_utils.dart';
// import 'package:minyun/screen/DashboardScreen.dart';
//
// /**
//  * 登录/注册
//  */
// class TabBarSignInScreen extends StatefulWidget {
//   final int selectedPage;
//
//   TabBarSignInScreen(this.selectedPage);
//
//   @override
//   TabBarSignInScreenState createState() => TabBarSignInScreenState();
// }
//
// class TabBarSignInScreenState extends State<TabBarSignInScreen> with SingleTickerProviderStateMixin {
//   TabController? _tabController;
//   bool? checkedValue = false;
//
//   TextEditingController contEmailAddress = TextEditingController();
//   TextEditingController contPassword = TextEditingController();
//
//   TextEditingController controllerName = TextEditingController();
//   TextEditingController controllerEmailAddress = TextEditingController();
//   TextEditingController controllerPassword = TextEditingController();
//
//   FocusNode focusNodePassword = FocusNode();
//   FocusNode addressFocusNode = FocusNode();
//   FocusNode passwordFocusNode = FocusNode();
//
//   bool isUsernameValid = true;
//   bool isEmailValid = true;
//   bool isPassworldValid = true;
//   final regisFormKey = GlobalKey<FormState>();
//
//   @override
//   void initState() {
//     super.initState();
//     init();
//   }
//
//   Future<void> init() async {
//     SharedPreferences prefs = await SharedPreferences.getInstance();
//     checkedValue = true; // 设置默认为选中状态
//     String? email = prefs.getString('email');
//     String? password = prefs.getString('password');
//     if (email != null && password != null) {
//       contEmailAddress.text = email;
//       contPassword.text = password;
//       checkedValue = true;
//     }
//     //
//   }
//
//   @override
//   void dispose() {
//     _tabController?.dispose();
//     super.dispose();
//   }
//
//   @override
//   void setState(fn) {
//     if (mounted) super.setState(fn);
//   }
//
//   // 登录函数
//   Future<void> login(String email, String password) async {
//     if (checkedValue ?? false) {
//       SharedPreferences prefs = await SharedPreferences.getInstance();
//       await prefs.setString('email', email);
//       await prefs.setString('password', password);
//     }
//     var response = await UserApi.login({ 'username': email, 'password': password});
//     if (response.code == 200) {
//       // 登录成功，处理响应
//      // print('登录成功');
//       DashboardScreen(4).launch(context);
//       // 这里可以进一步处理如保存token、跳转到主页等操作
//     }
//   }
//
// // 注册函数
//   Future<void> register(String name, String email, String password) async {
//    bool ok=await  UserApi.regis({'name': name, 'email': email, 'password': password});
//     if (ok) {
//       AwesomeDialog(
//         context: context,
//         animType: AnimType.rightSlide,
//         title: '${AppLocalizations.of(context)!.information}',
//         desc: '${AppLocalizations.of(context)!.registerSuccess}',
//         btnOkOnPress: () {
//           TabBarSignInScreen(0).launch(context);
//         },
//       )..show();
//       // 注册成功，处理响应
//      // print('注册成功');
//       // 这里可以进一步处理如自动登录、跳转到登录页面等操作
//     }else{
//       AwesomeDialog(
//         context: context,
//         animType: AnimType.rightSlide,
//         title: '${AppLocalizations.of(context)!.information}',
//         desc: '${AppLocalizations.of(context)!.registerFail}',
//         // btnCancelOnPress: () {
//         // },
//         btnOkOnPress: () {
//           // MPTabBarSignInScreen(0).launch(context);
//         },
//       )..show();
//     }
//   }
//
//   //
//   @override
//   Widget build(BuildContext context) {
//     return DefaultTabController(
//       initialIndex: widget.selectedPage,
//       length: 2,
//       child: Scaffold(
//         backgroundColor: mpAppBackGroundColor,
//         appBar: AppBar(
//           backgroundColor: mpAppBackGroundColor,
//           automaticallyImplyLeading: false,
//           bottomOpacity: 1,
//           bottom: TabBar(
//             unselectedLabelColor: mpAppTextColor,
//             labelColor: white,
//             tabs: [
//               Tab(child: Text(AppLocalizations.of(context)!.login)),
//               Tab(child: Text(AppLocalizations.of(context)!.register)),
//             ],
//             indicatorColor: mpAppButtonColor,
//             indicatorWeight: 3,
//             indicatorSize: TabBarIndicatorSize.label,
//           ),
//         ),
//         body: TabBarView(
//           children: [
//             SingleChildScrollView(
//               child: Column(children: [
//                 AppTextField(
//                   controller: contEmailAddress,
//                   textStyle: primaryTextStyle(color: white),
//                   // nextFocus: focusNodePassword,
//                   cursorColor: Colors.white,
//                   textFieldType: TextFieldType.NAME,
//                   decoration: buildInputDecoration('${AppLocalizations.of(context)!.username}/Email'),
//                 ),
//                 16.height,
//                 AppTextField(
//                   controller: contPassword,
//                   textStyle: primaryTextStyle(color: white),
//                   focus: focusNodePassword,
//                   cursorColor: Colors.white,
//                   textFieldType: TextFieldType.PASSWORD,
//                   suffixIconColor: mpAppButtonColor,
//                   decoration: buildInputDecoration(AppLocalizations.of(context)!.password),
//                 ),
//                 16.height,
//                 Align(
//                   alignment: Alignment.topRight,
//                   child: Text('${AppLocalizations.of(context)!.forgetPassword}?', style: primaryTextStyle(color: mpAppButtonColor, size: 14)),
//                 ).onTap(() {
//                   // UserForgotPasswordScreen().launch(context);
//                 }),
//                 16.height,
//                 Theme(
//                   data: ThemeData(unselectedWidgetColor: white),
//                   child: CheckboxListTile(
//                     contentPadding: EdgeInsets.all(0),
//                     title: Text("${AppLocalizations.of(context)!.rememberAccount}", style: secondaryTextStyle(color: Colors.white)),
//                     value: checkedValue,
//                     dense: true,
//                     onChanged: (newValue) {
//                       setState(() {
//                         checkedValue = newValue;
//                       });
//                     },
//                     controlAffinity: ListTileControlAffinity.leading,
//                   ),
//                 ),
//                 16.height,
//                 AppButton(
//                   child: Text('${AppLocalizations.of(context)!.login}', style: primaryTextStyle(color: Colors.white)),
//                   color: mpAppButtonColor,
//                   width: context.width(),
//                   onTap: () {
//                     login(contEmailAddress.text, contPassword.text);
//                     // finish(context);
//                     // MPDashboardScreen().launch(context);
//                   },
//                 ).cornerRadiusWithClipRRect(24),
//                 16.height,
//                 Row(
//                   mainAxisAlignment: MainAxisAlignment.center,
//                   children: [
//                     Container(height: 0.5, width: 110, color: Colors.white.withOpacity(0.2)),
//                     8.width,
//                     Text('OR', style: TextStyle(color: Colors.grey)),
//                     8.width,
//                     Container(height: 0.5, width: 110, color: Colors.white.withOpacity(0.2)),
//                   ],
//                 ),
//                 16.height,
//                 Row(
//                   mainAxisAlignment: MainAxisAlignment.center,
//                   children: [
//                     socialButton(btnText: "${AppLocalizations.of(context)!.returnHome}", btnBgColor: mpFacebookBtnBgColor,onTap: ()=>{
//                        DashboardScreen(0).launch(context)
//                     }),
//                     // 24.width,
//                     // socialButton(btnText: "Twitter", btnBgColor: mpTwitterBtnBgColor),
//                   ],
//                 ),
//               ]).paddingOnly(top: 70, right: 16, left: 16, bottom: 16),
//             ),
//             SingleChildScrollView(
//             child: Form(
//             key: regisFormKey,
//               child: Column(
//                 children: [
//                   AppTextField(
//                     controller: controllerName,
//                     textStyle: primaryTextStyle(color: white),
//                     textFieldType: TextFieldType.EMAIL,
//                     nextFocus: addressFocusNode,
//                     cursorColor: Colors.white,
//                     maxLength: 20,
//                     decoration: buildInputDecoration('${AppLocalizations.of(context)!.username}'),
//                     validator: (value) {
//                       if (value == null || value.isEmpty || value.length < 6 || value.length > 30 || !RegExp(r'^[a-zA-Z0-9]+$').hasMatch(value)) {
//                         return '${AppLocalizations.of(context)!.usernameRequirement}';
//                       }
//                       return null;
//                     },
//                   ),
//                   16.height,
//                   AppTextField(
//                     controller: controllerEmailAddress,
//                     textStyle: primaryTextStyle(color: white),
//                     textFieldType: TextFieldType.EMAIL,
//                     focus: addressFocusNode,
//                     cursorColor: Colors.white,
//                     nextFocus: passwordFocusNode,
//                     maxLength: 30,
//                     decoration: buildInputDecoration('E-mail'),
//                     validator: (value) {
//                       if (value == null || value.isEmpty  || value.length < 6 || value.length > 50 ||  !RegExp(r'^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$').hasMatch(value)) {
//                         return '${AppLocalizations.of(context)!.validEmail}';
//                       }
//                       return null;
//                     },
//                   ),
//                   16.height,
//                   AppTextField(
//                     controller: controllerPassword,
//                     textStyle: primaryTextStyle(color: white),
//                     textFieldType: TextFieldType.PASSWORD,
//                     focus: passwordFocusNode,
//                     cursorColor: Colors.white,
//                     maxLength: 20,
//                     suffixIconColor: mpAppButtonColor,
//                     decoration: buildInputDecoration('${AppLocalizations.of(context)!.password}'),
//                     validator: (value) {
//                       if (value == null || value.isEmpty || value.length < 6 || value.length > 30) {
//                         return '${AppLocalizations.of(context)!.passwordLength}';
//                       }
//                       return null;
//                     },
//                   ),
//                   20.height,
//                   AppButton(
//                     child: Text('${AppLocalizations.of(context)!.register}', style: primaryTextStyle(color: Colors.white)),
//                     color: mpAppButtonColor,
//                     width: context.width(),
//                     onTap: () {
//                       if (regisFormKey.currentState!.validate()) {
//                         register(controllerName.text, controllerEmailAddress.text,
//                             controllerPassword.text);
//                       }
//                     },
//                   ).cornerRadiusWithClipRRect(24),
//                   24.height,
//                 ],
//               )
//             ).paddingOnly(top: 70, right: 16, left: 16, bottom: 16),
//             ),
//           ],
//           controller: _tabController,
//         ),
//       ),
//     );
//   }
// }
//
