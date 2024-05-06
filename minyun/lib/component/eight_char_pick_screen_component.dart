
import 'package:flutter/cupertino.dart';
import 'package:minyun/utils/AppCommon.dart';
import 'package:nb_utils/nb_utils.dart';

class EightCharPickScreenComponent extends StatelessWidget {
  final String text;
  final TextStyle? style;
  EightCharPickScreenComponent({Key? key, required this.text, this.style}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Align(alignment: Alignment.centerLeft, child: Text(text, style: style ?? appMainBoldTextStyle()));
  }
}