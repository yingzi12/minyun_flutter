
import 'package:minyun/models/sms_model.dart';
import 'package:minyun/utils/HttpUtil.dart';

class SmsApi{
  // 类变量（静态变量）
  static String send ="/sendSms";

  static Future<SmsModel> sendSms(String phone) async {
    final jsonMap = await HttpUtil.get("$send?phone=$phone");


    if (jsonMap['code'] == 200) {
      // SharedPreferencesUtil.setStringWithExpiry(key, json.encode(jsonMap['data']), Duration(days: 1));
      return SmsModel.fromJson(jsonMap['data']);
    } else {
      throw Exception('Failed to load info: ${jsonMap['msg']}');
    }
  }

}