import 'dart:convert';

import 'package:minyun/models/JsonSerializable.dart';
/// phone : ""
/// uuid : ""
/// code : ""

SmsModel smsModelFromJson(String str) => SmsModel.fromJson(json.decode(str));
String smsModelToJson(SmsModel data) => json.encode(data.toJson());
class SmsModel implements JsonSerializable {
  SmsModel({
      String? phone, 
      String? uuid, 
      String? code,}){
    _phone = phone;
    _uuid = uuid;
    _code = code;
}

  SmsModel.fromJson(dynamic json) {
    _phone = json['phone'];
    _uuid = json['uuid'];
    _code = json['code'];
  }
  String? _phone;
  String? _uuid;
  String? _code;
SmsModel copyWith({  String? phone,
  String? uuid,
  String? code,
}) => SmsModel(  phone: phone ?? _phone,
  uuid: uuid ?? _uuid,
  code: code ?? _code,
);
  String? get phone => _phone;
  String? get uuid => _uuid;
  String? get code => _code;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['phone'] = _phone;
    map['uuid'] = _uuid;
    map['code'] = _code;
    return map;
  }

}