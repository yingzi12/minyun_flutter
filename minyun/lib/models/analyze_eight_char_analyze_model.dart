import 'dart:convert';

import 'package:minyun/models/JsonSerializable.dart';
/// id : 0
/// userId : 0
/// createTime : ""
/// label : ""
/// intro : ""
/// analyze : ""
/// analyzeTime : ""
/// inputdate : ""
/// upateTime : ""
/// uecid : 0
/// isSms : 0
/// isMessage : 0
/// isEmail : 0
/// smsStatus : 0
/// messageStatus : 0
/// emailStatus : 0
/// uuid : ""

AnalyzeEightCharAnalyzeModel analyzeEightCharAnalyzeModelFromJson(String str) => AnalyzeEightCharAnalyzeModel.fromJson(json.decode(str));
String analyzeEightCharAnalyzeModelToJson(AnalyzeEightCharAnalyzeModel data) => json.encode(data.toJson());
class AnalyzeEightCharAnalyzeModel  implements JsonSerializable {
  AnalyzeEightCharAnalyzeModel({
      num? id, 
      num? userId, 
      String? createTime, 
      String? label, 
      String? intro, 
      String? analyze, 
      String? analyzeTime, 
      String? inputdate, 
      String? upateTime, 
      num? uecid, 
      num? isSms, 
      num? isMessage, 
      num? isEmail, 
      num? smsStatus, 
      num? messageStatus, 
      num? emailStatus, 
      String? uuid,}){
    _id = id;
    _userId = userId;
    _createTime = createTime;
    _label = label;
    _intro = intro;
    _analyze = analyze;
    _analyzeTime = analyzeTime;
    _inputdate = inputdate;
    _upateTime = upateTime;
    _uecid = uecid;
    _isSms = isSms;
    _isMessage = isMessage;
    _isEmail = isEmail;
    _smsStatus = smsStatus;
    _messageStatus = messageStatus;
    _emailStatus = emailStatus;
    _uuid = uuid;
}

  AnalyzeEightCharAnalyzeModel.fromJson(dynamic json) {
    _id = json['id'];
    _userId = json['userId'];
    _createTime = json['createTime'];
    _label = json['label'];
    _intro = json['intro'];
    _analyze = json['analyze'];
    _analyzeTime = json['analyzeTime'];
    _inputdate = json['inputdate'];
    _upateTime = json['upateTime'];
    _uecid = json['uecid'];
    _isSms = json['isSms'];
    _isMessage = json['isMessage'];
    _isEmail = json['isEmail'];
    _smsStatus = json['smsStatus'];
    _messageStatus = json['messageStatus'];
    _emailStatus = json['emailStatus'];
    _uuid = json['uuid'];
  }
  num? _id;
  num? _userId;
  String? _createTime;
  String? _label;
  String? _intro;
  String? _analyze;
  String? _analyzeTime;
  String? _inputdate;
  String? _upateTime;
  num? _uecid;
  num? _isSms;
  num? _isMessage;
  num? _isEmail;
  num? _smsStatus;
  num? _messageStatus;
  num? _emailStatus;
  String? _uuid;
AnalyzeEightCharAnalyzeModel copyWith({  num? id,
  num? userId,
  String? createTime,
  String? label,
  String? intro,
  String? analyze,
  String? analyzeTime,
  String? inputdate,
  String? upateTime,
  num? uecid,
  num? isSms,
  num? isMessage,
  num? isEmail,
  num? smsStatus,
  num? messageStatus,
  num? emailStatus,
  String? uuid,
}) => AnalyzeEightCharAnalyzeModel(  id: id ?? _id,
  userId: userId ?? _userId,
  createTime: createTime ?? _createTime,
  label: label ?? _label,
  intro: intro ?? _intro,
  analyze: analyze ?? _analyze,
  analyzeTime: analyzeTime ?? _analyzeTime,
  inputdate: inputdate ?? _inputdate,
  upateTime: upateTime ?? _upateTime,
  uecid: uecid ?? _uecid,
  isSms: isSms ?? _isSms,
  isMessage: isMessage ?? _isMessage,
  isEmail: isEmail ?? _isEmail,
  smsStatus: smsStatus ?? _smsStatus,
  messageStatus: messageStatus ?? _messageStatus,
  emailStatus: emailStatus ?? _emailStatus,
  uuid: uuid ?? _uuid,
);
  num? get id => _id;
  num? get userId => _userId;
  String? get createTime => _createTime;
  String? get label => _label;
  String? get intro => _intro;
  String? get analyze => _analyze;
  String? get analyzeTime => _analyzeTime;
  String? get inputdate => _inputdate;
  String? get upateTime => _upateTime;
  num? get uecid => _uecid;
  num? get isSms => _isSms;
  num? get isMessage => _isMessage;
  num? get isEmail => _isEmail;
  num? get smsStatus => _smsStatus;
  num? get messageStatus => _messageStatus;
  num? get emailStatus => _emailStatus;
  String? get uuid => _uuid;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['id'] = _id;
    map['userId'] = _userId;
    map['createTime'] = _createTime;
    map['label'] = _label;
    map['intro'] = _intro;
    map['analyze'] = _analyze;
    map['analyzeTime'] = _analyzeTime;
    map['inputdate'] = _inputdate;
    map['upateTime'] = _upateTime;
    map['uecid'] = _uecid;
    map['isSms'] = _isSms;
    map['isMessage'] = _isMessage;
    map['isEmail'] = _isEmail;
    map['smsStatus'] = _smsStatus;
    map['messageStatus'] = _messageStatus;
    map['emailStatus'] = _emailStatus;
    map['uuid'] = _uuid;
    return map;
  }

}