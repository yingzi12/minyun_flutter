import 'dart:convert';

import 'package:minyun/models/ResultListModel.dart';
import 'package:minyun/models/case_analyze_model.dart';
import 'package:minyun/utils/HttpUtil.dart';
import 'package:minyun/utils/SharedPreferencesUtil.dart';

class CaseAnalyzeApi{
  // 类变量（静态变量）
  static String info ="/minyun/caseAnalyze/info";

  static String list ="/minyun/caseAnalyze/list";

  static String add ="/minyun/caseAnalyze/add";

  static String del ="/minyun/caseAnalyze/del";

  static String edit ="/minyun/caseAnalyze/edit";

  static Future<Map<String, dynamic>> addModel(Map<String, String> body) async {
    final response = await HttpUtil.post(add, body);
    return response;
  }

  static Future<Map<String, dynamic>> editModel(Map<String, String> body) async {
    final response = await HttpUtil.post(edit, body);
    return response;
  }


  static Future<CaseAnalyzeModel> getInfo(int id) async {
    final key = 'album_info_$id';
    // final jsonStr = SharedPreferencesUtil.getStringWithExpiry(key);
    //
    // if (jsonStr != null) {
    //   return AlbumModel.fromJson(json.decode(jsonStr));
    // } else {
    final url = "$info?id=$id";
    final jsonMap = await HttpUtil.get(url);


    if (jsonMap['code'] == 200) {
      // SharedPreferencesUtil.setStringWithExpiry(key, json.encode(jsonMap['data']), Duration(days: 1));
      return CaseAnalyzeModel.fromJson(jsonMap['data']);
    } else {
      throw Exception('Failed to load info: ${jsonMap['msg']}');
    }

    // }
  }



  static Future<Map<String, dynamic>> deleteModel(int id) async {
    final jsonMap = await HttpUtil.get("$del?id=$id");
    return jsonMap;
  }

  static Future<ResultListModel<CaseAnalyzeModel>> getList(Map<String, String> queryParams) async {
    final jsonMap = await HttpUtil.get(list, queryParams);

    ResultListModel<CaseAnalyzeModel> result = ResultListModel.fromJson(jsonMap, (json) => CaseAnalyzeModel.fromJson(json));
    if (result.code == 200) {
      return result;
    } else {
      throw Exception('Failed to load list: ${jsonMap['msg']}');
    }

  }


  static Future<List<CaseAnalyzeModel>> _performNetworkRequest(String url, Map<String, String> queryParams, String prefsKey) async {
    final jsonMap = await HttpUtil.get(url, queryParams);

    if (jsonMap['code'] == 200) {
      List<dynamic> jsonData = jsonMap['data'];
      List<CaseAnalyzeModel> values = jsonData.map<CaseAnalyzeModel>((item) => CaseAnalyzeModel.fromJson(item as Map<String, dynamic>)).toList();
      // 存储结果到 SharedPreferences
      await SharedPreferencesUtil.setMapWithExpiry(prefsKey, {'data': json.encode(jsonData)}, Duration(days: 1));
      return values;
    } else {
      throw Exception('Failed to load list: ${jsonMap['msg']}');
    }

  }

  static List<CaseAnalyzeModel> _parseAlbumModelList(String jsonData) {
    List<dynamic> listData = json.decode(jsonData);
    return listData.map((item) => CaseAnalyzeModel.fromJson(item as Map<String, dynamic>)).toList();
  }

}