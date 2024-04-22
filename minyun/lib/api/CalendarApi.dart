import 'dart:convert';

import 'package:minyun/models/CalendarModel.dart';
import 'package:minyun/models/ResultListModel.dart';
import 'package:minyun/models/SystemInfoModel.dart';
import 'package:minyun/models/UserModel.dart';
import 'package:minyun/models/LoginModel.dart';
import 'package:minyun/utils/HttpUtil.dart';
import 'package:minyun/utils/SecureStorage.dart';
import 'package:minyun/utils/SharedPreferencesUtil.dart';

class CalendarApi{
  // 类变量（静态变量）
  static String list ="/minyun/calendar/list";

  static Future<ResultListModel<CalendarModel>> getList(Map<String, String> queryParams) async {
    final jsonMap = await HttpUtil.get(list, queryParams);

    ResultListModel<CalendarModel> result = ResultListModel.fromJson(jsonMap, (json) => CalendarModel.fromJson(json));
    if (result.code == 200) {
      return result;
    } else {
      throw Exception('Failed to load list: ${jsonMap['msg']}');
    }

  }

}