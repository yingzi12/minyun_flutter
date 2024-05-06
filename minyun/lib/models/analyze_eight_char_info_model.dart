import 'dart:convert';

import 'package:minyun/models/JsonSerializable.dart';
/// id : 0
/// userId : 0
/// createTime : ""
/// label : ""
/// intro : ""
/// info : ""
/// analyzeTime : ""
/// inputdate : ""
/// upateTime : ""
/// aecid : 0
/// uuid : ""

AnalyzeEightCharInfoModel analyzeEightCharInfoModelFromJson(String str) => AnalyzeEightCharInfoModel.fromJson(json.decode(str));
String analyzeEightCharInfoModelToJson(AnalyzeEightCharInfoModel data) => json.encode(data.toJson());
class AnalyzeEightCharInfoModel  implements JsonSerializable{
  AnalyzeEightCharInfoModel({
    String? id,
    String? userId,
      String? createTime, 
      String? label, 
      String? intro, 
      String? info, 
      String? analyzeTime, 
      String? inputdate, 
      String? upateTime, 
      String? uuid,}){
    _id = id;
    _userId = userId;
    _createTime = createTime;
    _label = label;
    _intro = intro;
    _info = info;
    _analyzeTime = analyzeTime;
    _inputdate = inputdate;
    _upateTime = upateTime;
    _uuid = uuid;
}

  AnalyzeEightCharInfoModel.fromJson(dynamic json) {
    _id = json['id'];
    _userId = json['userId'];
    _createTime = json['createTime'];
    _label = json['label'];
    _intro = json['intro'];
    _info = json['info'];
    _analyzeTime = json['analyzeTime'];
    _inputdate = json['inputdate'];
    _upateTime = json['upateTime'];
    _uuid = json['uuid'];
  }
  String? _id;
  String? _userId;
  String? _createTime;
  String? _label;
  String? _intro;
  String? _info;
  String? _analyzeTime;
  String? _inputdate;
  String? _upateTime;
  String? _uuid;
AnalyzeEightCharInfoModel copyWith({  String? id,
  String? userId,
  String? createTime,
  String? label,
  String? intro,
  String? info,
  String? analyzeTime,
  String? inputdate,
  String? upateTime,
  String? uuid,
}) => AnalyzeEightCharInfoModel(  id: id ?? _id,
  userId: userId ?? _userId,
  createTime: createTime ?? _createTime,
  label: label ?? _label,
  intro: intro ?? _intro,
  info: info ?? _info,
  analyzeTime: analyzeTime ?? _analyzeTime,
  inputdate: inputdate ?? _inputdate,
  upateTime: upateTime ?? _upateTime,
  uuid: uuid ?? _uuid,
);
  String? get id => _id;
  String? get userId => _userId;
  String? get createTime => _createTime;
  String? get label => _label;
  String? get intro => _intro;
  String? get info => _info;
  String? get analyzeTime => _analyzeTime;
  String? get inputdate => _inputdate;
  String? get upateTime => _upateTime;
  String? get uuid => _uuid;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['id'] = _id;
    map['userId'] = _userId;
    map['createTime'] = _createTime;
    map['label'] = _label;
    map['intro'] = _intro;
    map['info'] = _info;
    map['analyzeTime'] = _analyzeTime;
    map['inputdate'] = _inputdate;
    map['upateTime'] = _upateTime;
    map['uuid'] = _uuid;
    return map;
  }

}