import 'dart:io';

import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:minyun/utils/AppCommon.dart';
import 'package:minyun/utils/AppContents.dart';
import 'package:image_picker/image_picker.dart';
import 'package:nb_utils/nb_utils.dart';
import 'package:minyun/utils/AppColors.dart';

bool value = true;

InputDecoration buildInputDecoration(String name) {
  return InputDecoration(
    hintText: name,
    hintStyle: appPrimaryTextStyle(color: white),
    focusedBorder: OutlineInputBorder(borderRadius: BorderRadius.circular(8.0), borderSide: BorderSide(color: viewLineColor, width: 0.5)),
    enabledBorder: OutlineInputBorder(borderRadius: BorderRadius.circular(8.0), borderSide: BorderSide(color: viewLineColor, width: 0.5)),
  );
}

// Widget playMusicWidget(BuildContext context) {
//   return Container(
//     color: cardBackgroundBlackDark,
//     width: context.width(),
//     child: Column(
//       mainAxisAlignment: MainAxisAlignment.end,
//       children: [
//         // LinearPercentIndicator(width: context.width(), lineHeight: 2.0, percent: 0.6, progressColor: mpAppButtonColor),
//         // 4.height,
//         Row(
//           crossAxisAlignment: CrossAxisAlignment.center,
//           children: [
//             commonCacheImageWidget(mpImages_1, 50, width: 50, fit: BoxFit.cover).cornerRadiusWithClipRRect(25),
//             16.width,
//             Column(
//               crossAxisAlignment: CrossAxisAlignment.start,
//               children: [
//                 Text("You Are Free", style: appBoldTextStyle(color: white.withOpacity(0.8))),
//                 4.height,
//                 Text(" Bally Wonger", maxLines: 2, overflow: TextOverflow.ellipsis, style: appSecondaryTextStyle(color: mpAppTextColor1)),
//               ],
//             ).expand(),
//             Row(
//               children: [
//                 Icon(Icons.chevron_left_outlined, color: mpAppButtonColor, size: 25).onTap(() {
//                   toasty(context, 'Previous', bgColor: Colors.white, textColor: Colors.black);
//                 }),
//                 16.width,
//                 Icon(Icons.play_circle_outline, color: mpAppButtonColor, size: 30).onTap(() {
//                   toasty(context, 'Play', bgColor: Colors.white, textColor: Colors.black);
//                 }),
//                 16.width,
//                 Icon(Icons.keyboard_arrow_right, color: mpAppButtonColor, size: 25).onTap(() {
//                   toasty(context, 'Next', bgColor: Colors.white, textColor: Colors.black);
//                 }),
//               ],
//             ),
//           ],
//         ).paddingOnly(left: 16, right: 16, bottom: 8, top: 4),
//       ],
//     ),
//   );
// }

Widget editProfileWidget({required String title, required String uName}) {
  return Row(
    mainAxisAlignment: MainAxisAlignment.spaceBetween,
    children: [
      Text(title, style: appPrimaryTextStyle(color: Colors.white)),
      Text(uName, style: secondaryTextStyle(color: Colors.white.withOpacity(0.7), size: 14)),
    ],
  );
}

Widget editProfileIconWidget({required String title, IconData? icon, VoidCallback? onTap}) {
  return InkWell(
    onTap: onTap,  // 添加点击事件处理函数
    child: Container(
      padding: EdgeInsets.all(8),  // 添加一些内边距以便触摸
      // height: 25px,  // 设置容器高度
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(title, style: appPrimaryTextStyle(color: Colors.white)),
          Icon(icon, color: white, size: 20),
        ],
      ),
    ),
  );
}
// ignore: must_be_immutable
class EditProfileSwitchWidget extends StatefulWidget {
  final String? name;
  bool isSelected;

  EditProfileSwitchWidget({this.name, this.isSelected = true});

  @override
  EditProfileSwitchWidgetState createState() => EditProfileSwitchWidgetState();
}

class EditProfileSwitchWidgetState extends State<EditProfileSwitchWidget> {
  @override
  void initState() {
    super.initState();
    init();
  }

  Future<void> init() async {
    //
  }

  @override
  void setState(fn) {
    if (mounted) super.setState(fn);
  }

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Text(widget.name!, style: appPrimaryTextStyle(color: Colors.white)),
        Switch(
          activeColor: white,
          activeTrackColor: mpAppButtonColor,
          inactiveTrackColor: gray,
          value: widget.isSelected,
          onChanged: (val) {
            setState(() {
              widget.isSelected = val;
            });
          },
        )
      ],
    );
  }
}

Widget searchIconWidget({Function? onPressed}) {
  return IconButton(
    icon: Icon(Icons.search, color: white.withOpacity(0.9)),
    onPressed: () {
      onPressed?.call();
    },
  );
}

Widget socialButton({String? btnText, Function? onTap, Color? btnBgColor}) {
  return Container(
    child: Text(btnText!, style: appPrimaryTextStyle(color: Colors.white)),
    width: 120,
    height: 50,
    alignment: Alignment.center,
    decoration: boxDecorationWithRoundedCorners(
      backgroundColor: btnBgColor!,
      borderRadius: BorderRadius.circular(24),
    ),
  ).onTap(() {
    onTap!();
  });
}



// Widget searchAlbumTextFiled(BuildContext context,String kind) {
//   return Container(
//     height: 40,
//     width: context.width(),
//     alignment: Alignment.topLeft,
//     child: AppTextField(
//       controller: TextEditingController(),
//       textStyle: appPrimaryTextStyle(color: Colors.white.withOpacity(0.4)),
//       textFieldType: TextFieldType.EMAIL,
//       textAlign: TextAlign.start,
//       onFieldSubmitted: (value) {
//         // 在这里实现您的搜索逻辑
//         AlbumListScreen(kind:kind ,serchValue:value).launch(context);
//       },
//       decoration: InputDecoration(
//         filled: true,
//         contentPadding: EdgeInsets.only(top: 4),
//         prefixIcon: Icon(Icons.search, color: Colors.white.withOpacity(0.2)),
//         fillColor: mpSearchBarBackGroundColor,
//         hintText: '搜索 图集, 现在...',
//         hintStyle: appPrimaryTextStyle(color: Colors.white.withOpacity(0.4)),
//         focusedBorder: OutlineInputBorder(borderRadius: BorderRadius.circular(25.7)),
//         enabledBorder: UnderlineInputBorder(borderRadius: BorderRadius.circular(25.7)),
//       ),
//     ),
//   );
// }

Widget commonCacheImageWidget(String? url, double? height, {double? width, BoxFit? fit,String? imgUrl}) {
  if(imgUrl==null||imgUrl==""){
    imgUrl="assets/images/empty.png";
  }
  if(url==null||url==""){
    url=imgUrl;
  }
  if (url.validate().startsWith('http')) {
    if (isMobile) {
      return  CachedNetworkImage(
          // placeholder: placeholderWidgetFn() as Widget Function(BuildContext, String)?,
          imageUrl: '$url',
          height: height,
          width: width,
          fit: fit ?? BoxFit.cover,
        placeholder: (context, url) => Image.asset(imgUrl!, fit: BoxFit.cover),
        errorWidget: (context, url, error) => Image.asset(imgUrl!, fit: BoxFit.cover),
          // errorWidget: (_, __, ___) {
          //   return SizedBox(height: height, width: width);
      );
    } else {
      return Image.network(url!, height: height, width: width, fit: fit ?? BoxFit.cover,);
    }
  } else {
    return Image.asset(url!, height: height, width: width, fit: fit ?? BoxFit.cover);
  }
}
Widget commonCacheImageContextWidget(BuildContext context,String? url,  {double? height,double? width, BoxFit? fit,String? imgUrl}) {
  if(imgUrl==null||imgUrl==""){
    imgUrl="assets/images/empty.png";
  }
  if(url==null||url==""){
    url=imgUrl;
  }
  if (url.validate().startsWith('http')) {
    if (isMobile) {
      return  CachedNetworkImage(
        imageUrl: '$url',
        height: height ?? MediaQuery.of(context).size.height,
        width: width ?? MediaQuery.of(context).size.width,
        fit: fit ?? BoxFit.cover,
        placeholder: (context, url) => Image.asset(imgUrl!, fit: BoxFit.cover),
        errorWidget: (context, url, error) => Image.asset(imgUrl!, fit: BoxFit.cover),
      );
    } else {
      return Image.network(url!, height: height, width: width, fit: fit ?? BoxFit.cover,);
    }
  } else {
    return Image.asset(url!, height: height, width: width, fit: fit ?? BoxFit.cover);
  }
}

// Widget? Function(BuildContext, String) placeholderWidgetFn() => (_, s) => placeholderWidget();

// Widget placeholderWidget() => Image.asset('assets/images/placeholder.jpg', fit: BoxFit.cover);

Widget redirectionButtonContainer({
  String? title,
  double? borderRadius,
  Color? color,
  double? width,
  double? height,
}) {
  return Container(
    height: height ?? 40,
    width: width ?? 100,
    alignment: Alignment.center,
    decoration: BoxDecoration(color: color ?? Colors.red, borderRadius: BorderRadius.circular(borderRadius ?? 4)),
    child: Text(title!, style: appBoldTextStyle(color: Colors.white)),
  );
}

BoxConstraints textFormFieldBoxConstraints({double? minWidth, double? maxWidth, double? minHeight, double? maxHeight}) {
  return BoxConstraints(
    minWidth: minWidth ?? 50,
    maxWidth: maxWidth ?? 100,
    minHeight: minHeight ?? 50,
    maxHeight: maxHeight ?? 60,
  );
}

PreferredSizeWidget customAppBar({required bool? isLeading, Widget? leading, String? title, Function()? onTap}) {
  return AppBar(
    automaticallyImplyLeading: isLeading ?? false,
    elevation: 0,
    leading: leading ?? (isLeading! ? GestureDetector(onTap: onTap, child: backButton()) : Offstage()),
    title: Text(
      title ?? '',
      style: boldTextStyle(size: 24),
    ),
    centerTitle: true,
  );
}

Widget backButton({Color? backgroundColor, double? paddingValue}) {
  return Container(
    padding: EdgeInsets.all(paddingValue ?? 10),
    alignment: Alignment.center,
    decoration: BoxDecoration(
      color: backgroundColor ?? Colors.transparent,
      borderRadius: BorderRadius.circular(28),
    ),
    child: Icon(
      Icons.arrow_back_ios_rounded,
      size: 22,
      color: backgroundColor != null ? Colors.white70 : Colors.grey.shade500,
    ),
  );
}

Widget customFloatingButton({double? paddingValue, double? size, IconData? icon}) {
  return Container(
    padding: EdgeInsets.all(paddingValue ?? 8),
    decoration: BoxDecoration(shape: BoxShape.circle, color: Colors.red),
    child: Icon(icon ?? Icons.play_arrow_rounded, size: size ?? 20, color: Colors.white),
  );
}
Widget titleRowItem({String? title, Function()? onTap, required bool isSeeAll,
  Color textColor = Colors.black // 添加颜色参数，默认为黑色

}) {
  return Row(
    children: [
      SizedBox(width: 16),
      Expanded(
        child: Text(
          title ?? 'Title',
          textAlign: TextAlign.left,
          style: boldTextStyle(size: 20, color: textColor),
        ),
      ),
      if (isSeeAll)
        TextButton(
          style: ButtonStyle(
            padding: MaterialStatePropertyAll(EdgeInsets.zero),
            visualDensity: VisualDensity.compact,
          ),
          onPressed: onTap,
          child: Text('See all>', style: secondaryTextStyle(size: 12)),
        ),
      SizedBox(width: 16)
    ],
  );
}

Widget titleAddRowItem({
  String? title,
  Function()? onTap,
  required bool isSeeAll,
  Color textColor = Colors.black // 添加颜色参数，默认为黑色
}) {
  return Row(
    children: [
      SizedBox(width: 16),
      Expanded(
        child: Text(
          title ?? 'Title',
          textAlign: TextAlign.left,
          style: boldTextStyle(size: 20, color: textColor), // 使用颜色参数
        ),
      ),
      if (isSeeAll)
        TextButton(
          style: ButtonStyle(
            padding: MaterialStatePropertyAll(EdgeInsets.zero),
            visualDensity: VisualDensity.compact,
          ),
          onPressed: onTap,
          child: Text(
            '添加',
            style: secondaryTextStyle(size: 16, color: textColor), // 使用颜色参数
          ),
        ),
      SizedBox(width: 16)
    ],
  );
}

Widget actorImageItem({String? imageName, String? name}) {
  return Container(
    width: 88,
    child: Column(
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        ClipRRect(
          child: Image.asset(imageName ?? "assets/images/default-avatar.jpg", height: 90, width: 88, fit: BoxFit.cover),
          borderRadius: BorderRadius.circular(14),
        ),
        SizedBox(height: 8),
        Text(name!, textAlign: TextAlign.center, style: primaryTextStyle(size: 14)),
      ],
    ),
  );
}

Widget? Function(BuildContext, String) placeholderWidgetFn() => (_, s) => placeholderWidget();

Widget placeholderWidget() => Image.asset('images/app/placeholder.jpg', fit: BoxFit.cover);
//
// class EditTextField extends StatefulWidget {
//   var isPassword;
//   var isSecure;
//   var fontSize;
//   var textColor;
//   var fontFamily;
//   var text;
//   var maxLine;
//   TextEditingController? mController;
//
//   VoidCallback? onPressed;
//
//   EditTextField(
//       {var this.fontSize = 20.0, var this.textColor = textColorSecondary, var this.isPassword = true, var this.isSecure = false, var this.text = "", var this.mController, var this.maxLine = 1});
//
//   @override
//   State<StatefulWidget> createState() {
//     return EditTextFieldState();
//   }
// }

// class EditTextFieldState extends State<EditTextField> {
//   @override
//   Widget build(BuildContext context) {
//     if (!widget.isSecure) {
//       return TextField(
//         controller: widget.mController,
//         obscureText: widget.isPassword,
//         cursorColor: colorPrimary,
//         maxLines: widget.maxLine,
//         style: TextStyle(fontSize: widget.fontSize, color: appStore.textPrimaryColor, fontFamily: widget.fontFamily),
//         decoration: InputDecoration(
//           enabledBorder: UnderlineInputBorder(
//             borderSide: BorderSide(color: appStore.iconColor!),
//           ),
//           focusedBorder: UnderlineInputBorder(
//             borderSide: BorderSide(color: colorPrimary),
//           ),
//         ),
//       );
//     } else {
//       return TextField(
//           controller: widget.mController,
//           obscureText: widget.isPassword,
//           cursorColor: colorPrimary,
//           style: TextStyle(fontSize: widget.fontSize, color: textColorPrimary, fontFamily: widget.fontFamily),
//           decoration: InputDecoration(
//             suffixIcon: GestureDetector(
//               onTap: () {
//                 setState(() {
//                   widget.isPassword = !widget.isPassword;
//                 });
//               },
//               child: Icon(widget.isPassword ? Icons.visibility : Icons.visibility_off, color: appStore.iconColor),
//             ),
//             enabledBorder: UnderlineInputBorder(
//               borderSide: BorderSide(color: appStore.iconColor!),
//             ),
//             focusedBorder: UnderlineInputBorder(
//               borderSide: BorderSide(color: colorPrimary),
//             ),
//           ));
//     }
//   }
// }
//
// class CustomTheme extends StatelessWidget {
//   final Widget? child;
//
//   CustomTheme({required this.child});
//
//   @override
//   Widget build(BuildContext context) {
//     return Theme(
//       data: appStore.isDarkModeOn
//           ? ThemeData.dark().copyWith(
//         scaffoldBackgroundColor: context.scaffoldBackgroundColor,
//       )
//           : ThemeData.light(),
//       child: child!,
//     );
//   }
// }
//
//
// Widget text(
//     String? text, {
//       var fontSize = textSizeLargeMedium,
//       Color? textColor,
//       var fontFamily,
//       var isCentered = false,
//       var maxLine = 1,
//       var latterSpacing = 0.5,
//       bool textAllCaps = false,
//       var isLongText = false,
//       bool lineThrough = false,
//     }) {
//   return Text(
//     textAllCaps ? text!.toUpperCase() : text!,
//     textAlign: isCentered ? TextAlign.center : TextAlign.start,
//     maxLines: isLongText ? null : maxLine,
//     overflow: TextOverflow.ellipsis,
//     style: TextStyle(
//       fontFamily: fontFamily ?? null,
//       fontSize: fontSize,
//       color: textColor ?? appStore.textSecondaryColor,
//       height: 1.5,
//       letterSpacing: latterSpacing,
//       decoration: lineThrough ? TextDecoration.lineThrough : TextDecoration.none,
//     ),
//   );
// }
//
// // BoxDecoration boxDecoration({double radius = 2, Color color = Colors.transparent, Color? bgColor, var showShadow = false}) {
// //   return BoxDecoration(
// //     color: bgColor ?? appStore.scaffoldBackground,
// //     boxShadow: showShadow ? defaultBoxShadow(shadowColor: shadowColorGlobal) : [BoxShadow(color: Colors.transparent)],
// //     border: Border.all(color: color),
// //     borderRadius: BorderRadius.all(Radius.circular(radius)),
// //   );
// // }
// BoxDecoration boxDecoration({double radius = 2, Color color = Colors.transparent, Color? bgColor, var showShadow = false}) {
//   return BoxDecoration(
//     color: bgColor ?? appStore.scaffoldBackground,
//     boxShadow: showShadow ? defaultBoxShadow(shadowColor: shadowColorGlobal) : [BoxShadow(color: Colors.transparent)],
//     border: Border.all(color: color),
//     borderRadius: BorderRadius.all(Radius.circular(radius)),
//   );
// }
//
// Widget transactionWidget(Transactions transaction, var categoryWidth) {
//   return Container(
//     decoration: boxDecoration(bgColor: appStore.scaffoldBackground, showShadow: true, radius: spacing_standard),
//     padding: EdgeInsets.all(spacing_standard),
//     margin: EdgeInsets.only(bottom: spacing_standard),
//     child: Row(
//       children: <Widget>[
//         Image.asset(
//           transaction.img,
//           width: categoryWidth * 0.75,
//           height: categoryWidth * 0.75,
//         ).cornerRadiusWithClipRRect(spacing_standard).paddingRight(spacing_standard),
//         Expanded(
//           child: Column(
//             mainAxisSize: MainAxisSize.max,
//             crossAxisAlignment: CrossAxisAlignment.start,
//             children: <Widget>[
//               text(transaction.type, fontSize: textSizeMedium, textColor: appStore.textPrimaryColor, fontFamily: fontMedium),
//               text(transaction.subType, fontSize: textSizeMedium, textColor: appStore.textSecondaryColor).paddingTop(spacing_control_half),
//             ],
//           ),
//         ),
//         Column(
//           children: <Widget>[
//             text(transaction.transactionType == "credited" ? "+ \$" + transaction.amount.toString() : "- \$" + transaction.amount.toString(),
//                 fontSize: textSizeMedium, textColor: transaction.transactionType == "credited" ? success : error, fontFamily: fontBold),
//             text(transaction.time, fontSize: textSizeMedium, textColor: appStore.textSecondaryColor).paddingTop(spacing_control)
//           ],
//         )
//       ],
//     ),
//   );
// }
//
//
// Widget appBarTitleWidget(context, String title, {Color? color, Color? textColor}) {
//   return Container(
//     width: MediaQuery.of(context).size.width,
//     height: 60,
//     color: color ?? appStore.appBarColor,
//     child: Row(
//       children: <Widget>[
//         Text(
//           title,
//           style: appBoldTextStyle(color: color ?? appStore.textPrimaryColor, size: 20),
//           maxLines: 1,
//         ).expand(),
//       ],
//     ),
//   );
// }

// AppBar appBar(BuildContext context, String title, {final bool isDashboard = false, List<Widget>? actions, bool showBack = true, Color? color, Color? iconColor, Color? textColor, double? elevation}) {
//   return AppBar(
//     automaticallyImplyLeading: false,
//     backgroundColor: color ?? appStore.appBarColor,
//     leading: showBack
//         ? IconButton(
//       onPressed: () {
//         if (isDashboard) {
//           // ProKitLauncher().launch(context, isNewTask: true, pageRouteAnimation: PageRouteAnimation.Fade);
//         } else {
//           finish(context);
//         }
//       },
//       icon: Icon(Icons.arrow_back, color: appStore.isDarkModeOn ? white : black),
//     )
//         : null,
//     title: appBarTitleWidget(context, title, textColor: textColor, color: color),
//     actions: actions,
//     elevation: elevation ?? 0.5,
//   );
// }
//
// class BankingButton extends StatefulWidget {
//   static String tag = '/BankingButton';
//   var textContent;
//   VoidCallback onPressed;
//   var isStroked = false;
//   var height = 50.0;
//   var radius = 5.0;
//
//   BankingButton({required this.textContent, required this.onPressed, this.isStroked = false, this.height = 45.0, this.radius = 5.0});
//
//   @override
//   BankingButtonState createState() => BankingButtonState();
// }

// class BankingButtonState extends State<BankingButton> {
//   @override
//   Widget build(BuildContext context) {
//     return GestureDetector(
//       onTap: widget.onPressed,
//       child: Container(
//         height: widget.height,
//         padding: EdgeInsets.fromLTRB(16, 4, 16, 4),
//         alignment: Alignment.center,
//         child: Text(
//           widget.textContent.toUpperCase(),
//           style: appBoldTextStyle(color: widget.isStroked ? appColorPrimary : Colors.white),
//         ).center(),
//         decoration: widget.isStroked
//             ? boxDecoration(
//           bgColor: Colors.transparent,
//           color: appColorPrimary,
//         )
//             : boxDecoration(bgColor: iconColorSecondary, radius: widget.radius),
//       ),
//     );
//   }
// }

Widget bankingOption(var icon, var heading, Color color) {
  return Container(
    padding: EdgeInsets.fromLTRB(8, 10, 8, 10),
    child: Row(
      children: <Widget>[
        Row(
          children: <Widget>[
            Image.asset(icon, color: color, height: 20, width: 20),
            16.width,
            Text(heading, style: appPrimaryTextStyle()),
          ],
        ).expand(),
        Icon(Icons.keyboard_arrow_right, color: textColorSecondary),
      ],
    ),
  );
}

class TopCard extends StatelessWidget {
  final String name;
  final String title1;
  final String title2;

  final String acno;
  final String bal;

  TopCard({Key? key, required this.name, required this.title1, required this.title2, required this.acno, required this.bal}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      width: context.width(),
      height: context.height(),
      color: context.cardColor,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Expanded(
            child: Row(
              children: [
                Icon(Icons.account_balance_wallet, color: textPrimaryColor, size: 30).paddingOnly(top: 8, left: 8),
                Text(name, style: primaryTextStyle(size: 18)).paddingOnly(left: 8, top: 8).expand(),
                Icon(Icons.info, color: Colors.grey, size: 20).paddingOnly(right: 8)
              ],
            ),
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text(title1, style: secondaryTextStyle(size: 16)).paddingOnly(left: 8, top: 8, bottom: 8),
              Text(acno, style: appPrimaryTextStyle(color: Colors.yellow)).paddingOnly(right: 8, top: 8, bottom: 8),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text(title2, style: secondaryTextStyle(size: 16)).paddingOnly(left: 8, top: 8, bottom: 8),
              Text(bal, style: appPrimaryTextStyle(color: Colors.green)).paddingOnly(right: 8, top: 8, bottom: 8),
            ],
          )
        ],
      ),
    );
  }
}

// ignore: must_be_immutable
class EditText extends StatefulWidget {
  var isPassword;
  var isSecure;
  var fontSize;
  var textColor;
  var fontFamily;
  var text;
  var maxLine;
  TextEditingController? mController;

  VoidCallback? onPressed;

  EditText({
    var this.fontSize = textSizeNormal,
    var this.textColor = appColorPrimary,
    var this.fontFamily = fontRegular,
    var this.isPassword = true,
    var this.isSecure = false,
    var this.text = "",
    var this.mController,
    var this.maxLine = 1,
  });

  @override
  State<StatefulWidget> createState() {
    return EditTextState();
  }
}

class EditTextState extends State<EditText> {
  @override
  Widget build(BuildContext context) {
    if (!widget.isSecure) {
      return TextField(
        controller: widget.mController,
        obscureText: widget.isPassword,
        cursorColor: appColorPrimary,
        maxLines: widget.maxLine,
        style: appPrimaryTextStyle(),
        decoration: InputDecoration(
          hintText: widget.text,
          hintStyle: appPrimaryTextStyle(),
          focusedBorder: UnderlineInputBorder(borderSide: BorderSide(color: appColorPrimary, width: 0.5)),
          enabledBorder: UnderlineInputBorder(borderSide: BorderSide(color: textColorSecondary, width: 0.5)),
        ),
      );
    } else {
      return TextField(
        controller: widget.mController,
        obscureText: widget.isPassword,
        cursorColor: appColorPrimary,
        style: appPrimaryTextStyle(),
        decoration: InputDecoration(
            hintText: widget.text,
            hintStyle: appPrimaryTextStyle(),
            suffixIcon: GestureDetector(
              onTap: () {
                setState(() {
                  widget.isPassword = !widget.isPassword;
                });
              },
              child: Icon(
                widget.isPassword ? Icons.visibility : Icons.visibility_off,
                color: textColorSecondary,
              ),
            ),
            enabledBorder: UnderlineInputBorder(
              borderSide: BorderSide(color: textColorSecondary, width: 0.5),
            ),
            focusedBorder: UnderlineInputBorder(
              borderSide: BorderSide(color: appColorPrimary, width: 0.5),
            )),
      );
    }
  }
}

Widget headerView(var title, double height, BuildContext context) {
  return Container(
    child: Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Icon(
              Icons.chevron_left,
              size: 25,
              color:  white ,
            ).paddingAll(6).paddingOnly(top: spacing_standard).onTap(() {
              finish(context);
            }).paddingOnly(left: spacing_standard, right: spacing_standard_new, top: spacing_standard_new, bottom: spacing_standard),
          ],
        ),
        Text(
          title,
          style: boldTextStyle(size: 30),
        ).paddingOnly(left: spacing_standard_new, right: spacing_standard),
      ],
    ),
  );
}
void changeStatusColor(Color color) async {
  setStatusBarColor(color);
}
// Widget toolBarTitle(var title, {textColor = textColorPrimary}) {
//   return text(title, fontSize: textSizeNormal, fontFamily: fontBold, textColor: textColor);
// }


Widget formField(context, hint,
    {isEnabled = true,
      isDummy = false,
      controller,
      isPasswordVisible = false,
      isPassword = false,
      keyboardType = TextInputType.text,
      FormFieldValidator<String>? validator,
      onSaved,
      textInputAction = TextInputAction.next,
      FocusNode? focusNode,
      FocusNode? nextFocus,
      IconData? suffixIcon,
      IconData? prefixIcon,
      maxLine = 1,
      suffixIconSelector}) {
  return TextFormField(
    controller: controller,
    obscureText: isPassword ? isPasswordVisible : false,
    cursorColor: primaryColor,
    maxLines: maxLine,
    keyboardType: keyboardType,
    validator: validator,
    onSaved: onSaved,
    textInputAction: textInputAction,
    focusNode: focusNode,
    onFieldSubmitted: (arg) {
      if (nextFocus != null) {
        FocusScope.of(context).requestFocus(nextFocus);
      }
    },
    decoration: InputDecoration(
      focusedBorder: UnderlineInputBorder(borderRadius: BorderRadius.circular(spacing_standard), borderSide: BorderSide(color: Colors.transparent)),
      enabledBorder: UnderlineInputBorder(borderRadius: BorderRadius.circular(spacing_standard), borderSide: BorderSide(color: Colors.transparent)),
      filled: true,
      fillColor: edittextBackground,
      hintText: hint,
      hintStyle: TextStyle(fontSize: textSizeMedium, color: textSecondary),
      prefixIcon: Icon(
        prefixIcon,
        color: textSecondary,
        size: 20,
      ),
      suffixIcon: isPassword
          ? GestureDetector(
        onTap: suffixIconSelector,
        child: new Icon(
          suffixIcon,
          color: textSecondary,
          size: 20,
        ),
      )
          : Icon(
        suffixIcon,
        color: textSecondary,
        size: 20,
      ),
    ),
    style: TextStyle(fontSize: textSizeNormal, color: isDummy ? Colors.transparent : textColorPrimary, fontFamily: fontRegular),
  );
}

Widget formNumberField(
    BuildContext context,
    String hint, {
      bool isEnabled = true,
      TextEditingController? controller,
      TextInputType? keyboardType,
      FormFieldValidator<String>? validator,
      void Function(String?)? onSaved,
      TextInputAction textInputAction = TextInputAction.next,
      FocusNode? focusNode,
      FocusNode? nextFocus,
      IconData? suffixIcon,
      IconData? prefixIcon,
      int maxLine = 1,
      VoidCallback? suffixIconSelector,
    }) {
  keyboardType ??= TextInputType.numberWithOptions(decimal: true);

  final inputFormatters = [
    FilteringTextInputFormatter.allow(RegExp(r'^\d*\.?\d{0,2}')),
  ];

  return TextFormField(
    controller: controller,
    enabled: isEnabled,
    cursorColor: Colors.blue,
    maxLines: maxLine,
    keyboardType: keyboardType,
    validator: validator,
    onSaved: onSaved,
    onChanged: (String value) {
      if (!RegExp(r'^\d*\.?\d{0,2}$').hasMatch(value)) {
        controller?.value = TextEditingValue(
          text: value.substring(0, value.length - 1),
          selection: TextSelection.fromPosition(
            TextPosition(offset: value.length - 1),
          ),
        );
      }
    },
    textInputAction: textInputAction,
    focusNode: focusNode,
    onFieldSubmitted: (arg) {
      if (nextFocus != null) {
        FocusScope.of(context).requestFocus(nextFocus);
      }
    },
    decoration: InputDecoration(
      focusedBorder: UnderlineInputBorder(
        borderRadius: BorderRadius.circular(8),
        borderSide: BorderSide(color: Colors.transparent),
      ),
      enabledBorder: UnderlineInputBorder(
        borderRadius: BorderRadius.circular(8),
        borderSide: BorderSide(color: Colors.transparent),
      ),
      filled: true,
      fillColor: Colors.grey[200],
      hintText: hint,
      hintStyle: TextStyle(fontSize: 16, color: Colors.grey),
      prefixIcon: prefixIcon != null ? Icon(prefixIcon, color: Colors.grey) : null,
      suffixIcon: suffixIcon != null ? Icon(suffixIcon, color: Colors.grey) : null,
    ),
    style: TextStyle(fontSize: 16, color: Colors.black),
    inputFormatters: inputFormatters,
  );
}

//没有更多数据显示用
Widget noDataWidget(String message) {
  return Center(
    child: Padding(
      padding: EdgeInsets.all(16.0),
      child: Text(
        message,
        style: TextStyle(color: Colors.grey),
      ),
    ),
  );
}
//标题
Padding settingText(
    var text,
    ) {
  return Padding(
    padding: EdgeInsets.only(left: 8, right: 8, top: 8, bottom: 8),
    child: Text(
      text,
      style: TextStyle(
        fontFamily: fontRegular,
        // color: appStore.textPrimaryColor,
        fontSize: textSizeMedium,
      ),
    ),
  );
}

Widget buildTag(String title, Color color) {
  return Container(
    padding: EdgeInsets.fromLTRB(5, 2, 5, 3),
    decoration: BoxDecoration(
      border: Border.all(color: Color.fromARGB(99, color.red, color.green, color.blue), width: 0.5),
      borderRadius: BorderRadius.circular(3),
    ),
    child: Text(
        title,
        style: TextStyle(fontSize: 11, color: color),
        overflow: TextOverflow.ellipsis, // 当文本超出时显示省略号
        maxLines: 1
    ),
  );
}
/**
 * 打开选择文件
 */
Future<File> svGetImageSource() async {
  final picker = ImagePicker();
  final pickedImage = await picker.pickImage(source: ImageSource.camera);
  return File(pickedImage!.path);
}
