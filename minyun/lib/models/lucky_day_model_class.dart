import 'dart:ffi';

import 'package:flutter/material.dart';

class LuckyDayModel {
  int? id;
  String? title;
  Color? color;
  String? search;

  LuckyDayModel({this.id, this.title,this.color,this.search });
}

List<LuckyDayModel> luckyDayList = [
  LuckyDayModel(
      id: 1,
      color: Color(0xFFfff7eb),
      title: "搬家吉日",
      search:"移徙"
  ),
  LuckyDayModel(
      id: 2,
      color: Color(0xFFfff7eb),
      title: "装修吉日",
      search:"塑绘"

  ),
  LuckyDayModel(
      id: 3,
      color: Color(0xFFfff7eb),
      title: "入宅吉日",
      search:"入宅"),
  LuckyDayModel(
      id: 4,
      color: Color(0xFFfff7eb),
      title: "嫁娶吉日",
      search:"嫁娶"),
  LuckyDayModel(
      id: 5,
      color: Color(0xFFfff7eb),
      title: "订婚吉日",
      search:"订婚"),
  LuckyDayModel(
      id: 6,
      color: Color(0xFFfff7eb),
      title: "纳采吉日",
      search:"纳采"),
  LuckyDayModel(
      id: 7,
      color: Color(0xFFfff7eb),
      title: "置产产日",
      search:"置产"),
  LuckyDayModel(
      id: 8,
      color: Color(0xFFfff7eb),
      title: "开市吉日",
      search:"开市"),
  LuckyDayModel(
      id: 8,
      color: Color(0xFFfff7eb),
      title: "动土吉日",
      search:"动土"),
  LuckyDayModel(
      id: 8,
      color: Color(0xFFfff7eb),
      title: "出行吉日",
      search:"出行"),
  LuckyDayModel(
      id: 8,
      color: Color(0xFFfff7eb),
      title: "提车吉日",
      search:"提车"),
  LuckyDayModel(
      id: 8,
      color: Color(0xFFfff7eb),
      title: "安葬吉日",
      search:"安葬"),
];

