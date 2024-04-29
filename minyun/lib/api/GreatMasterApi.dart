import 'dart:convert';

import 'package:minyun/models/ResultListModel.dart';
import 'package:minyun/models/great_master_model.dart';
import 'package:minyun/models/user_eight_char_model.dart';
import 'package:minyun/utils/HttpUtil.dart';
import 'package:minyun/utils/SharedPreferencesUtil.dart';

class GreatMasterApi{
  // 类变量（静态变量）
  static String info ="/minyun/greatMaster/info";

  static String list ="/minyun/greatMaster/list";

  static String add ="/minyun/greatMaster/add";

  static String del ="/minyun/greatMaster/del";

  static String edit ="/minyun/greatMaster/edit";

  static Future<Map<String, dynamic>> addModel(Map<String, String> body) async {
    final response = await HttpUtil.post(add, body);
    return response;
  }

  static Future<Map<String, dynamic>> editModel(Map<String, String> body) async {
    final response = await HttpUtil.post(edit, body);
    return response;
  }


  static Future<GreatMasterModel> getInfo(int id) async {
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
      return GreatMasterModel.fromJson(jsonMap['data']);
    } else {
      throw Exception('Failed to load info: ${jsonMap['msg']}');
    }

    // }
  }



  static Future<Map<String, dynamic>> deleteModel(int id) async {
    final jsonMap = await HttpUtil.get("$del?id=$id");
    return jsonMap;
  }

  static Future<ResultListModel<GreatMasterModel>> getList(Map<String, String> queryParams) async {
    final jsonMap = await HttpUtil.get(list, queryParams);

    ResultListModel<GreatMasterModel> result = ResultListModel.fromJson(jsonMap, (json) => GreatMasterModel.fromJson(json));
    if (result.code == 200) {
      return result;
    } else {
      throw Exception('Failed to load list: ${jsonMap['msg']}');
    }

  }


  static Future<List<GreatMasterModel>> _performNetworkRequest(String url, Map<String, String> queryParams, String prefsKey) async {
    final jsonMap = await HttpUtil.get(url, queryParams);

    if (jsonMap['code'] == 200) {
      List<dynamic> jsonData = jsonMap['data'];
      List<GreatMasterModel> values = jsonData.map<GreatMasterModel>((item) => GreatMasterModel.fromJson(item as Map<String, dynamic>)).toList();
      // 存储结果到 SharedPreferences
      await SharedPreferencesUtil.setMapWithExpiry(prefsKey, {'data': json.encode(jsonData)}, Duration(days: 1));
      return values;
    } else {
      throw Exception('Failed to load list: ${jsonMap['msg']}');
    }

  }

  static List<GreatMasterModel> _parseAlbumModelList(String jsonData) {
    List<dynamic> listData = json.decode(jsonData);
    return listData.map((item) => GreatMasterModel.fromJson(item as Map<String, dynamic>)).toList();
  }

}