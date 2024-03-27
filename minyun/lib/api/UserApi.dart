import 'dart:convert';

// import 'package:minyun/models/AlbumDiscoverModel.dart';
import 'package:minyun/models/ResultListModel.dart';
import 'package:minyun/models/SystemInfoModel.dart';
import 'package:minyun/models/UserModel.dart';
import 'package:minyun/models/LoginModel.dart';
import 'package:minyun/utils/HttpUtil.dart';
import 'package:minyun/utils/SecureStorage.dart';
import 'package:minyun/utils/SharedPreferencesUtil.dart';

class UserApi{
  // 类变量（静态变量）
  static String info ="/systemUser/info";


  static String systemInfo ="/systemUser/systemInfo";

  static String list ="/systemUser/list";

  static String listSee ="/systemUser/listSee";

  static String random ="/systemUser/random";

  static String loginUrl ="/systemUser/loginModile";

  static String regisUrl ="/systemUser/regis";

  static String logutUrl ="/admin/systemUser/logout";

  static Future<LoginModel> login(Map<String, String> body) async {
    final jsonMap = await HttpUtil.post(loginUrl, body);

    LoginModel result = LoginModel.fromJson(jsonMap);
    if (jsonMap['code'] == 200) {
      SecureStorage().storeLoginToken(result.accessToken??"");
      SecureStorage().setLoginUser(jsonEncode(result.user!));
      return result;
    } else {
      throw Exception('Failed to load info: ${jsonMap['msg']}');
    }

  }


  static Future<bool> regis(Map<String, String> body) async {
    final jsonMap = await HttpUtil.post(regisUrl, body);


    if (jsonMap['code'] == 200) {
      // SharedPreferencesUtil.setStringWithExpiry(key, json.encode(jsonMap['data']), Duration(days: 1));
      return true;
    } else {
      throw Exception('Failed to load info: ${jsonMap['msg']}');
    }

  }

  static Future<bool> logout() async {
    await SecureStorage().clearLoginToken();
    await SecureStorage().clearUser();
    final jsonMap = await HttpUtil.get(logutUrl);
    if (jsonMap['code'] == 200) {
      return true;
    } else {
      throw Exception('Failed to load info: ${jsonMap['msg']}');
    }

  }

  static Future<List<UserModel>> getRandom() async {
    final jsonMap = await HttpUtil.get(random);

    if (jsonMap['code'] == 200) {
      List<dynamic> jsonData = jsonMap['data'];
      List<UserModel> values = jsonData.map<UserModel>((item) => UserModel.fromJson(item as Map<String, dynamic>)).toList();
      // List<UserModel> values = parseList<UserModel>(json.encode(jsonData), (item) => UserModel.fromJson(item as Map<String, dynamic>));
      return values;
    } else {
      throw Exception('Failed to load list: ${jsonMap['msg']}');
    }

  }

  static Future<UserModel> getInfo(int id) async {
    final url = "$info?userId=$id";
    final jsonMap = await HttpUtil.get(url);


    if (jsonMap['code'] == 200) {
      return UserModel.fromJson(jsonMap['data']);
    } else {
      throw Exception('Failed to load info: ${jsonMap['msg']}');
    }
  }

  static Future<SystemInfoModel> getSystemInfo() async {
    final url = "$systemInfo";
    final jsonMap = await HttpUtil.get(url);

    if (jsonMap['code'] == 200) {
      return SystemInfoModel.fromJson(jsonMap['data']);
    } else {
      throw Exception('Failed to load info: ${jsonMap['msg']}');
    }
  }

  static Future<ResultListModel<UserModel>> getList(Map<String, String> queryParams) async {
    final jsonMap = await HttpUtil.get(list, queryParams);

    ResultListModel<UserModel> result = ResultListModel.fromJson(jsonMap, (json) => UserModel.fromJson(json));
    if (result.code == 200) {
      return result;
    } else {
      throw Exception('Failed to load list: ${jsonMap['msg']}');
    }

  }

  static Future<ResultListModel<UserModel>> getListSee(Map<String, String> queryParams) async {
    final jsonMap = await HttpUtil.get(listSee, queryParams);

    ResultListModel<UserModel> result = ResultListModel.fromJson(jsonMap, (json) => UserModel.fromJson(json));
    if (result.code == 200) {
      return result;
    } else {
      throw Exception('Failed to load list: ${jsonMap['msg']}');
    }

  }


  static Future<List<UserModel>> _performNetworkRequest(String url, Map<String, String> queryParams, String prefsKey) async {
    final jsonMap = await HttpUtil.get(url, queryParams);

    if (jsonMap['code'] == 200) {
      List<dynamic> jsonData = jsonMap['data'];
      List<UserModel> values = jsonData.map<UserModel>((item) => UserModel.fromJson(item as Map<String, dynamic>)).toList();
      // List<UserModel> values = parseList<UserModel>(json.encode(jsonData), (item) => UserModel.fromJson(item as Map<String, dynamic>));
      // 存储结果到 SharedPreferences
      await SharedPreferencesUtil.setMapWithExpiry(prefsKey, {'data': json.encode(jsonData)}, Duration(days: 1));
      return values;
    } else {
      throw Exception('Failed to load list: ${jsonMap['msg']}');
    }

  }

  static List<UserModel> _parseUserModelList(String jsonData) {
    List<dynamic> listData = json.decode(jsonData);
    return listData.map((item) => UserModel.fromJson(item as Map<String, dynamic>)).toList();
  }

}