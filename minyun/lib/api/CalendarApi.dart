
import 'package:minyun/models/CalendarModel.dart';
import 'package:minyun/models/ResultListModel.dart';
import 'package:minyun/utils/HttpUtil.dart';

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