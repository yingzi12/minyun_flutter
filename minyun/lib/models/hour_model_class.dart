import 'dart:ffi';

import 'package:flutter/material.dart';

class HourModel {
  int? id;
  String? title;
  Color? color;
  //时间
  int sj;


  HourModel({this.id, this.title,this.color,required this.sj });
}

List<HourModel> hourList = [
  HourModel(
      id: 1,
      color: Color(0xFFfff7eb),
      title: "子时",
      sj : 23
  ),
  HourModel(
      id: 2,
      color: Color(0xFFfff7eb),
      title: "丑时",
      sj : 1
  ),
  HourModel(
      id: 3,
      color: Color(0xFFfff7eb),
      title: "寅时",
      sj : 3
  ),
  HourModel(
      id: 4,
      color: Color(0xFFfff7eb),
      title: "卯时",
      sj : 5
  ),
  HourModel(
      id: 5,
      color: Color(0xFFfff7eb),
      title: "辰时",
      sj : 7
  ),
  HourModel(
      id: 6,
      color: Color(0xFFfff7eb),
      title: "巳时",
      sj : 9
  ),
  HourModel(
      id: 7,
      color: Color(0xFFfff7eb),
      title: "午时",
      sj : 11
  ),
  HourModel(
      id: 8,
      color: Color(0xFFfff7eb),
      title: "未时",
      sj : 13
  ),
  HourModel(
      id: 9,
      color: Color(0xFFfff7eb),
      title: "申时",
      sj : 15
  ),
  HourModel(
      id: 10,
      color: Color(0xFFfff7eb),
      title: "酉时",
      sj : 19
  ),
  HourModel(
      id: 11,
      color: Color(0xFFfff7eb),
      title: "戌时",
      sj : 19
  ),
  HourModel(
      id: 12,
      color: Color(0xFFfff7eb),
      title: "亥时",
      sj : 21
  ),
];

