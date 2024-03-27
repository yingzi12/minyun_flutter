import 'dart:convert';

import 'package:shared_preferences/shared_preferences.dart';
//无加密
class SharedPreferencesUtil {
  static SharedPreferences? _preferences;

  // 初始化SharedPreferences
  static Future init() async {
    _preferences = await SharedPreferences.getInstance();
  }
// 判断键是否存在
  static bool containsKey(String key) {
    return _preferences?.containsKey(key) ?? false;
  }


  // 读取字符串
  static String? getString(String key) {
    final int? expiryTime = _preferences?.getInt('${key}_expiry');
    if (expiryTime == null || DateTime.now().millisecondsSinceEpoch > expiryTime) {
      // 数据已过期或未设置过期时间
      return null;
    }
    return _preferences?.getString(key);
  }

  // 存储数据
  static Future<bool> setString(String key, String value) async {
    return _preferences?.setString(key, value) ?? Future.value(false);
  }

  // 存储整型数据
  static Future<bool> setInt(String key, int value) async {
    return _preferences?.setInt(key, value) ?? Future.value(false);
  }

  // 读取整型数据
  static int? getInt(String key, [int? defValue]) {
    return _preferences?.getInt(key) ?? defValue;
  }

// 更多类型的存储和读取方法...

// 清除过期数据
  static void clearExpired() {
    _preferences?.getKeys().forEach((key) {
      if (key.endsWith('_expiry')) {
        final int? expiryTime = _preferences?.getInt(key);
        if (expiryTime != null && DateTime.now().millisecondsSinceEpoch > expiryTime) {
          final String originalKey = key.replaceAll('_expiry', '');
          _preferences?.remove(originalKey);
          _preferences?.remove(key);
        }
      }
    });
  }

  Future<bool> checkUserLoggedIn() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getBool('isLoggedIn') ?? false;
  }

  // 设置带过期时间的字符串
  static Future<bool> setStringWithExpiry(String key, String value, Duration duration) async {
    final bool result = await _preferences?.setString(key, value) ?? false;
    final int expiryTime = DateTime.now().millisecondsSinceEpoch + duration.inMilliseconds;
    await _preferences?.setInt('${key}_expiry', expiryTime);
    return result;
  }

  // 获取带过期时间的字符串
  static String? getStringWithExpiry(String key) {
    final int? expiryTime = _preferences?.getInt('${key}_expiry');
    if (expiryTime == null || DateTime.now().millisecondsSinceEpoch > expiryTime) {
      // 数据已过期或未设置过期时间
      return null;
    }
    return _preferences?.getString(key);
  }

  // 存储带有过期时间的 Map 数据
  static Future<bool> setMapWithExpiry(String key, Map<String, String> value, Duration duration) async {
    final bool result = await _preferences?.setString(key, json.encode(value)) ?? false;
    final int expiryTime = DateTime.now().millisecondsSinceEpoch + duration.inMilliseconds;
    await _preferences?.setInt('${key}_expiry', expiryTime);
    return result;
  }

  // 获取带有过期时间的 Map 数据
  static Map<String, String>? getMapWithExpiry(String key) {
    final int? expiryTime = _preferences?.getInt('${key}_expiry');
    if (expiryTime == null || DateTime.now().millisecondsSinceEpoch > expiryTime) {
      // 数据已过期或未设置过期时间
      return null;
    }
    final String? jsonString = _preferences?.getString(key);
    return jsonString != null ? Map<String, String>.from(json.decode(jsonString)) : null;
  }

}
