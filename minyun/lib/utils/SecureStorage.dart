import 'dart:convert';

import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:minyun/models/LoginModel.dart';
import 'package:minyun/models/login_user_model.dart';

//带加密
class SecureStorage {
  final _storage = const FlutterSecureStorage();

  Future<void> storeLoginToken(String token) async {
    await _storage.write(key: 'login_token', value: token);
  }

  Future<String?> getLoginToken() async {
    return await _storage.read(key: 'login_token');
  }

  Future<bool> isLoggedIn() async {
    var token = await getLoginToken();
    return token != null;
  }

  Future<void> setLoginUser(String userInfo) async {
    await _storage.write(key: 'user_info', value: userInfo);
  }

  Future<void> updateUserInfo(LoginUserModel loginUser) async {
    try {
      String updatedUserInfo = jsonEncode(loginUser.toJson());
      await setLoginUser(updatedUserInfo);
    } catch (e) {
      // Handle any errors here
     // print("Error updating user info: $e");
    }
  }
  Future<LoginUserModel?> getLoginUser() async {
    final userInfoStr=await _storage.read(key: "user_info");
    if (userInfoStr != null) {
      final data = jsonDecode(userInfoStr);
      // Map<String, dynamic> jsonMap = json.decode(userInfoStr);
      return  LoginUserModel.fromJson(data);
    }
  }

  Future<void> clearLoginToken() async {
    await _storage.delete(key: 'login_token');
  }

  Future<void> clearUser() async {
    await _storage.delete(key: 'user_info');
  }


  Future<void> saveData(String key, String value, {Duration? expiryDuration}) async {
    final expiryTime = DateTime.now().add(expiryDuration ?? Duration(days: 30)).toIso8601String();
    final data = {'value': value, 'expiry': expiryTime};
    await _storage.write(key: key, value: jsonEncode(data));
  }

  Future<String?> getData(String key) async {
    final dataString = await _storage.read(key: key);
    if (dataString != null) {
      final data = jsonDecode(dataString);
      final expiryTime = DateTime.parse(data['expiry']);

      if (DateTime.now().isBefore(expiryTime)) {
        return data['value'];
      } else {
        await _storage.delete(key: key); // 删除过期数据
        return null;
      }
    }
    return null;
  }
}