
import 'package:image_picker/image_picker.dart';
import 'package:minyun/models/UserModel.dart';
import 'package:minyun/utils/HttpUtil.dart';

class AdminUserApi{
  // 类变量（静态变量）
  static String info ="/admin/systemUser/getInfo";

  static String random ="/admin/systemUser/random";

  static String add ="/admin/systemUser/add";

  static String logutUrl ="/admin/systemUser/logout";

  static String edit ="/admin/systemUser/edit";

  static String updatePassworld ="/admin/systemUser/updatePassworld";

  static String upload ="/admin/systemUser/upload";

  static String sendUpdateCheckEmail ="/admin/systemUser/sendUpdateCheckEmail";

  static String sendCheckEmail ="/admin/systemUser/sendCheckEmailCode";
  static String checkCode ="/admin/systemUser/checkEmailCode";


  static Future<Map<String, dynamic>> addModel(Map<String, String> body) async {
    final response = await HttpUtil.post(add, body);
    return response;
  }

  static Future<Map<String, dynamic>> editModel(Map<String, String> body) async {
    final response = await HttpUtil.post(edit, body);
    return response;
  }

  static Future<Map<String, dynamic>> resPassworld(Map<String, String> body) async {
    final response = await HttpUtil.post(updatePassworld, body);
    return response;
  }


  static Future<bool> logout() async {
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
      return values;
    } else {
      throw Exception('Failed to load list: ${jsonMap['msg']}');
    }

  }

  static Future<UserModel> getInfo() async {
    final url = "$info";
    final jsonMap = await HttpUtil.get(url);

    if (jsonMap['code'] == 200) {
      // SharedPreferencesUtil.setStringWithExpiry(key, json.encode(jsonMap['data']), Duration(days: 1));
      return UserModel.fromJson(jsonMap['data']);
    } else {
      throw Exception('Failed to load info: ${jsonMap['msg']}');
    }
  }

  static Future<Map<String, dynamic>> uploadImage( XFile imageFile) async {
    Map<String, dynamic> jsonMap = await HttpUtil.uploadImage(upload,imageFile);
    return jsonMap;
  }

  static Future<Map<String, dynamic>> sendEmailCode(String email) async {
    final url = "$sendCheckEmail?email=$email";
    final jsonMap = await HttpUtil.get(url);


    return jsonMap;

  }

  static Future<Map<String, dynamic>> checkEmailCode(String email,String code) async {
    final url = "$checkCode?email=$email&code=$code";
    final jsonMap = await HttpUtil.get(url);


    return jsonMap;

  }

}