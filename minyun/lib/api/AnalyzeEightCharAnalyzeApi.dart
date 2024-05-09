import 'dart:convert';

import 'package:minyun/models/ResultListModel.dart';
import 'package:minyun/models/analyze_eight_char_analyze_model.dart';
import 'package:minyun/utils/HttpUtil.dart';
import 'package:minyun/utils/SharedPreferencesUtil.dart';

class AnalyzeEightCharAnalyzeApi{
  // 类变量（静态变量）
  static String info ="/admin/minyun/analyzeEightCharAnalyze/info";

  static String list ="/admin/minyun/analyzeEightCharAnalyze/list";

  static String add ="/admin/minyun/analyzeEightCharAnalyze/add";

  static String del ="/admin/minyun/analyzeEightCharAnalyze/del";

  static String edit ="/admin/minyun/analyzeEightCharAnalyze/edit";

  static Future<Map<String, dynamic>> addModel(Map<String, String> body) async {
    final response = await HttpUtil.post(add, body);
    return response;
  }

  static Future<Map<String, dynamic>> editModel(Map<String, String> body) async {
    final response = await HttpUtil.post(edit, body);
    return response;
  }


  static Future<AnalyzeEightCharAnalyzeModel> getInfo(int id,String uuid) async {
    final key = 'album_info_$id';
    final url = "$info?id=$id&uuid=$uuid";
    final jsonMap = await HttpUtil.get(url);
    if (jsonMap['code'] == 200) {
      // SharedPreferencesUtil.setStringWithExpiry(key, json.encode(jsonMap['data']), Duration(days: 1));
      return AnalyzeEightCharAnalyzeModel.fromJson(jsonMap['data']);
    } else {
      throw Exception('Failed to load info: ${jsonMap['msg']}');
    }

    // }
  }



  static Future<Map<String, dynamic>> deleteModel(int id,String uuid) async {
    final jsonMap = await HttpUtil.get("$del?id=$id&uuid=$uuid");
    return jsonMap;
  }

  static Future<ResultListModel<AnalyzeEightCharAnalyzeModel>> getList(Map<String, String> queryParams) async {
    final jsonMap = await HttpUtil.get(list, queryParams);

    print(jsonMap);
    ResultListModel<AnalyzeEightCharAnalyzeModel> result = ResultListModel.fromJson(jsonMap, (json) => AnalyzeEightCharAnalyzeModel.fromJson(json));
    if (result.code == 200) {
      return result;
    } else {
      throw Exception('Failed to load list: ${jsonMap['msg']}');
    }

  }


  static Future<List<AnalyzeEightCharAnalyzeModel>> _performNetworkRequest(String url, Map<String, String> queryParams, String prefsKey) async {
    final jsonMap = await HttpUtil.get(url, queryParams);

    if (jsonMap['code'] == 200) {
      List<dynamic> jsonData = jsonMap['data'];
      List<AnalyzeEightCharAnalyzeModel> values = jsonData.map<AnalyzeEightCharAnalyzeModel>((item) => AnalyzeEightCharAnalyzeModel.fromJson(item as Map<String, dynamic>)).toList();
      // 存储结果到 SharedPreferences
      await SharedPreferencesUtil.setMapWithExpiry(prefsKey, {'data': json.encode(jsonData)}, Duration(days: 1));
      return values;
    } else {
      throw Exception('Failed to load list: ${jsonMap['msg']}');
    }

  }

  static List<AnalyzeEightCharAnalyzeModel> _parseAlbumModelList(String jsonData) {
    List<dynamic> listData = json.decode(jsonData);
    return listData.map((item) => AnalyzeEightCharAnalyzeModel.fromJson(item as Map<String, dynamic>)).toList();
  }

}