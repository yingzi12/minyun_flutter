import 'package:flutter/material.dart';
import 'package:minyun/screens/account_screen.dart';
import 'package:minyun/utils/AppColors.dart';
import 'package:minyun/utils/AppContents.dart';

class AppButton extends StatelessWidget {
  final Color? color;
  final Color? textColor;
  final String text;
  final double? height;
  final double? width;
  final double? fontSize;
  final Function() onTap;

  AppButton({this.color, this.textColor, this.fontSize, required this.text, this.height, this.width, required this.onTap});

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onTap,
      child: Container(
        height: height,
        width: width ?? MediaQuery.of(context).size.width,
        padding: EdgeInsets.symmetric(vertical: 12, horizontal: 16),
        decoration: BoxDecoration(color: color ?? primaryColor, borderRadius: BorderRadius.circular(DEFAULT_RADIUS)),
        child: Text(text,
            style: TextStyle(color: mode.theme ? textColor ?? Colors.white : textColor ?? Colors.white, fontSize: fontSize ?? 18),
            textAlign: TextAlign.center),
      ),
    );
  }
}
