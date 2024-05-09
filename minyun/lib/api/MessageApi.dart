import 'dart:convert';

import 'package:minyun/models/ResultListModel.dart';
import 'package:minyun/models/analyze_eight_char_model.dart';
import 'package:minyun/models/message_model.dart';
import 'package:minyun/utils/HttpUtil.dart';
import 'package:minyun/utils/SharedPreferencesUtil.dart';

class MessageApi{
  // 类变量（静态变量）
  static String info ="/admin/message/getInfo";

  static String list ="/admin/message/list";

  static String add ="/admin/message/add";

  static String del ="/admin/message/del";


  static Future<Map<String, dynamic>> addModel(Map<String, String> body) async {
    final response = await HttpUtil.post(add, body);
    return response;
  }
  

  static Future<MessageModel> getInfo(String id,String uuid) async {
    final key = 'album_info_$id';
    // final jsonStr = SharedPreferencesUtil.getStringWithExpiry(key);
    //
    // if (jsonStr != null) {
    //   return AlbumModel.fromJson(json.decode(jsonStr));
    // } else {
    final url = "$info?id=$id&uuid=$uuid";
    final jsonMap = await HttpUtil.get(url);


    if (jsonMap['code'] == 200) {
      // SharedPreferencesUtil.setStringWithExpiry(key, json.encode(jsonMap['data']), Duration(days: 1));
      return MessageModel.fromJson(jsonMap['data']);
    } else {
      throw Exception('Failed to load info: ${jsonMap['msg']}');
    }

    // }
  }



  static Future<Map<String, dynamic>> deleteModel(String id,String uuid) async {
    final jsonMap = await HttpUtil.get("$del?id=$id&uuid=$uuid");
    return jsonMap;
  }

  static Future<ResultListModel<MessageModel>> getList(Map<String, String> queryParams) async {
    final jsonMap = await HttpUtil.get(list, queryParams);
    print(jsonMap);
    ResultListModel<MessageModel> result = ResultListModel.fromJson(jsonMap, (json) => MessageModel.fromJson(json));
    if (result.code == 200) {
      return result;
    } else {
      throw Exception('Failed to load list: ${jsonMap['msg']}');
    }

  }


  static Future<List<MessageModel>> _performNetworkRequest(String url, Map<String, String> queryParams, String prefsKey) async {
    final jsonMap = await HttpUtil.get(url, queryParams);

    if (jsonMap['code'] == 200) {
      List<dynamic> jsonData = jsonMap['data'];
      List<MessageModel> values = jsonData.map<MessageModel>((item) => MessageModel.fromJson(item as Map<String, dynamic>)).toList();
      // 存储结果到 SharedPreferences
      await SharedPreferencesUtil.setMapWithExpiry(prefsKey, {'data': json.encode(jsonData)}, Duration(days: 1));
      return values;
    } else {
      throw Exception('Failed to load list: ${jsonMap['msg']}');
    }

  }

  static List<MessageModel> _parseAlbumModelList(String jsonData) {
    List<dynamic> listData = json.decode(jsonData);
    return listData.map((item) => MessageModel.fromJson(item as Map<String, dynamic>)).toList();
  }

}