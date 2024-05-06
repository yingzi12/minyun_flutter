import 'dart:convert';

import 'package:minyun/models/JsonSerializable.dart';
/// id : 0
/// userId : 0
/// year : 0
/// month : 0
/// day : 0
/// hour : 0
/// ng : ""
/// yg : ""
/// rg : ""
/// sg : ""
/// sex : 0
/// isMajor : 0
/// ztys : 0
/// dateType : 0
/// name : ""
/// sect : 0
/// siling : 0
/// city1 : ""
/// city2 : ""
/// city3 : ""
/// createTime : ""
/// label : ""
/// intro : ""
/// analyze : ""
/// analyzeTime : ""
/// analyzeUserId : ""
/// analyzeUserName : ""
/// analyzeDirection : ""
/// uuid : ""

AnalyzeEightCharModel analyzeEightCharModelFromJson(String str) => AnalyzeEightCharModel.fromJson(json.decode(str));
String analyzeEightCharModelToJson(AnalyzeEightCharModel data) => json.encode(data.toJson());
class AnalyzeEightCharModel  implements JsonSerializable {
  AnalyzeEightCharModel({
      num? id, 
      num? userId, 
      num? year, 
      num? month, 
      num? day, 
      num? hour, 
      String? ng, 
      String? yg, 
      String? rg, 
      String? sg, 
      num? sex, 
      num? isMajor, 
      num? ztys, 
      num? dateType, 
      String? name, 
      num? sect, 
      num? siling, 
      String? city1, 
      String? city2, 
      String? city3, 
      String? createTime, 
      String? label, 
      String? intro, 
      String? analyze, 
      String? analyzeTime, 
      String? analyzeUserId, 
      String? analyzeUserName, 
      String? analyzeDirection, 
      String? uuid,}){
    _id = id;
    _userId = userId;
    _year = year;
    _month = month;
    _day = day;
    _hour = hour;
    _ng = ng;
    _yg = yg;
    _rg = rg;
    _sg = sg;
    _sex = sex;
    _isMajor = isMajor;
    _ztys = ztys;
    _dateType = dateType;
    _name = name;
    _sect = sect;
    _siling = siling;
    _city1 = city1;
    _city2 = city2;
    _city3 = city3;
    _createTime = createTime;
    _label = label;
    _intro = intro;
    _analyze = analyze;
    _analyzeTime = analyzeTime;
    _analyzeUserId = analyzeUserId;
    _analyzeUserName = analyzeUserName;
    _analyzeDirection = analyzeDirection;
    _uuid = uuid;
}

  AnalyzeEightCharModel.fromJson(dynamic json) {
    _id = json['id'];
    _userId = json['userId'];
    _year = json['year'];
    _month = json['month'];
    _day = json['day'];
    _hour = json['hour'];
    _ng = json['ng'];
    _yg = json['yg'];
    _rg = json['rg'];
    _sg = json['sg'];
    _sex = json['sex'];
    _isMajor = json['isMajor'];
    _ztys = json['ztys'];
    _dateType = json['dateType'];
    _name = json['name'];
    _sect = json['sect'];
    _siling = json['siling'];
    _city1 = json['city1'];
    _city2 = json['city2'];
    _city3 = json['city3'];
    _createTime = json['createTime'];
    _label = json['label'];
    _intro = json['intro'];
    _analyze = json['analyze'];
    _analyzeTime = json['analyzeTime'];
    _analyzeUserId = json['analyzeUserId'];
    _analyzeUserName = json['analyzeUserName'];
    _analyzeDirection = json['analyzeDirection'];
    _uuid = json['uuid'];
  }
  num? _id;
  num? _userId;
  num? _year;
  num? _month;
  num? _day;
  num? _hour;
  String? _ng;
  String? _yg;
  String? _rg;
  String? _sg;
  num? _sex;
  num? _isMajor;
  num? _ztys;
  num? _dateType;
  String? _name;
  num? _sect;
  num? _siling;
  String? _city1;
  String? _city2;
  String? _city3;
  String? _createTime;
  String? _label;
  String? _intro;
  String? _analyze;
  String? _analyzeTime;
  String? _analyzeUserId;
  String? _analyzeUserName;
  String? _analyzeDirection;
  String? _uuid;
AnalyzeEightCharModel copyWith({  num? id,
  num? userId,
  num? year,
  num? month,
  num? day,
  num? hour,
  String? ng,
  String? yg,
  String? rg,
  String? sg,
  num? sex,
  num? isMajor,
  num? ztys,
  num? dateType,
  String? name,
  num? sect,
  num? siling,
  String? city1,
  String? city2,
  String? city3,
  String? createTime,
  String? label,
  String? intro,
  String? analyze,
  String? analyzeTime,
  String? analyzeUserId,
  String? analyzeUserName,
  String? analyzeDirection,
  String? uuid,
}) => AnalyzeEightCharModel(  id: id ?? _id,
  userId: userId ?? _userId,
  year: year ?? _year,
  month: month ?? _month,
  day: day ?? _day,
  hour: hour ?? _hour,
  ng: ng ?? _ng,
  yg: yg ?? _yg,
  rg: rg ?? _rg,
  sg: sg ?? _sg,
  sex: sex ?? _sex,
  isMajor: isMajor ?? _isMajor,
  ztys: ztys ?? _ztys,
  dateType: dateType ?? _dateType,
  name: name ?? _name,
  sect: sect ?? _sect,
  siling: siling ?? _siling,
  city1: city1 ?? _city1,
  city2: city2 ?? _city2,
  city3: city3 ?? _city3,
  createTime: createTime ?? _createTime,
  label: label ?? _label,
  intro: intro ?? _intro,
  analyze: analyze ?? _analyze,
  analyzeTime: analyzeTime ?? _analyzeTime,
  analyzeUserId: analyzeUserId ?? _analyzeUserId,
  analyzeUserName: analyzeUserName ?? _analyzeUserName,
  analyzeDirection: analyzeDirection ?? _analyzeDirection,
  uuid: uuid ?? _uuid,
);
  num? get id => _id;
  num? get userId => _userId;
  num? get year => _year;
  num? get month => _month;
  num? get day => _day;
  num? get hour => _hour;
  String? get ng => _ng;
  String? get yg => _yg;
  String? get rg => _rg;
  String? get sg => _sg;
  num? get sex => _sex;
  num? get isMajor => _isMajor;
  num? get ztys => _ztys;
  num? get dateType => _dateType;
  String? get name => _name;
  num? get sect => _sect;
  num? get siling => _siling;
  String? get city1 => _city1;
  String? get city2 => _city2;
  String? get city3 => _city3;
  String? get createTime => _createTime;
  String? get label => _label;
  String? get intro => _intro;
  String? get analyze => _analyze;
  String? get analyzeTime => _analyzeTime;
  String? get analyzeUserId => _analyzeUserId;
  String? get analyzeUserName => _analyzeUserName;
  String? get analyzeDirection => _analyzeDirection;
  String? get uuid => _uuid;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['id'] = _id;
    map['userId'] = _userId;
    map['year'] = _year;
    map['month'] = _month;
    map['day'] = _day;
    map['hour'] = _hour;
    map['ng'] = _ng;
    map['yg'] = _yg;
    map['rg'] = _rg;
    map['sg'] = _sg;
    map['sex'] = _sex;
    map['isMajor'] = _isMajor;
    map['ztys'] = _ztys;
    map['dateType'] = _dateType;
    map['name'] = _name;
    map['sect'] = _sect;
    map['siling'] = _siling;
    map['city1'] = _city1;
    map['city2'] = _city2;
    map['city3'] = _city3;
    map['createTime'] = _createTime;
    map['label'] = _label;
    map['intro'] = _intro;
    map['analyze'] = _analyze;
    map['analyzeTime'] = _analyzeTime;
    map['analyzeUserId'] = _analyzeUserId;
    map['analyzeUserName'] = _analyzeUserName;
    map['analyzeDirection'] = _analyzeDirection;
    map['uuid'] = _uuid;
    return map;
  }

}