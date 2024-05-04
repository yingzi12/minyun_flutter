import 'dart:convert';
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

AnalyzeEightCharInfoModel analyzeEightCharInfoModelFromJson(String str) => AnalyzeEightCharInfoModel.fromJson(json.decode(str));
String analyzeEightCharInfoModelToJson(AnalyzeEightCharInfoModel data) => json.encode(data.toJson());
class AnalyzeEightCharInfoModel {
  AnalyzeEightCharInfoModel({
      num? id, 
      num? userId, 
      String? createTime, 
      String? label, 
      String? intro, 
      String? info, 
      String? analyzeTime, 
      String? inputdate, 
      String? upateTime, 
      num? aecid,}){
    _id = id;
    _userId = userId;
    _createTime = createTime;
    _label = label;
    _intro = intro;
    _info = info;
    _analyzeTime = analyzeTime;
    _inputdate = inputdate;
    _upateTime = upateTime;
    _aecid = aecid;
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
    _aecid = json['aecid'];
  }
  num? _id;
  num? _userId;
  String? _createTime;
  String? _label;
  String? _intro;
  String? _info;
  String? _analyzeTime;
  String? _inputdate;
  String? _upateTime;
  num? _aecid;
AnalyzeEightCharInfoModel copyWith({  num? id,
  num? userId,
  String? createTime,
  String? label,
  String? intro,
  String? info,
  String? analyzeTime,
  String? inputdate,
  String? upateTime,
  num? aecid,
}) => AnalyzeEightCharInfoModel(  id: id ?? _id,
  userId: userId ?? _userId,
  createTime: createTime ?? _createTime,
  label: label ?? _label,
  intro: intro ?? _intro,
  info: info ?? _info,
  analyzeTime: analyzeTime ?? _analyzeTime,
  inputdate: inputdate ?? _inputdate,
  upateTime: upateTime ?? _upateTime,
  aecid: aecid ?? _aecid,
);
  num? get id => _id;
  num? get userId => _userId;
  String? get createTime => _createTime;
  String? get label => _label;
  String? get intro => _intro;
  String? get info => _info;
  String? get analyzeTime => _analyzeTime;
  String? get inputdate => _inputdate;
  String? get upateTime => _upateTime;
  num? get aecid => _aecid;

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
    map['aecid'] = _aecid;
    return map;
  }

}