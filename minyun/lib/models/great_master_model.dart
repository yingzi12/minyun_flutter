import 'dart:convert';

import 'package:minyun/models/JsonSerializable.dart';
/// id : 0
/// userName : ""
/// sex : ""
/// avatar : ""
/// password : ""
/// status : ""
/// delFlag : ""
/// loginIp : ""
/// loginDate : ""
/// remark : ""
/// exp : 0
/// renks : 0
/// tags : ""
/// money : 0
/// sign : ""
/// userId : 0
/// createTime : ""
/// introduce : ""

GreatMasterModel greatMasterModelFromJson(String str) => GreatMasterModel.fromJson(json.decode(str));
String greatMasterModelToJson(GreatMasterModel data) => json.encode(data.toJson());
class GreatMasterModel implements JsonSerializable {
  GreatMasterModel({
      num? id, 
      String? userName, 
      String? sex, 
      String? avatar, 
      String? password, 
      String? status, 
      String? delFlag, 
      String? loginIp, 
      String? loginDate, 
      String? remark, 
      num? exp, 
      num? renks, 
      String? tags, 
      num? money, 
      String? sign, 
      num? userId, 
      String? createTime, 
      String? introduce,}){
    _id = id;
    _userName = userName;
    _sex = sex;
    _avatar = avatar;
    _password = password;
    _status = status;
    _delFlag = delFlag;
    _loginIp = loginIp;
    _loginDate = loginDate;
    _remark = remark;
    _exp = exp;
    _renks = renks;
    _tags = tags;
    _money = money;
    _sign = sign;
    _userId = userId;
    _createTime = createTime;
    _introduce = introduce;
}

  GreatMasterModel.fromJson(dynamic json) {
    _id = json['id'];
    _userName = json['userName'];
    _sex = json['sex'];
    _avatar = json['avatar'];
    _password = json['password'];
    _status = json['status'];
    _delFlag = json['delFlag'];
    _loginIp = json['loginIp'];
    _loginDate = json['loginDate'];
    _remark = json['remark'];
    _exp = json['exp'];
    _renks = json['renks'];
    _tags = json['tags'];
    _money = json['money'];
    _sign = json['sign'];
    _userId = json['userId'];
    _createTime = json['createTime'];
    _introduce = json['introduce'];
  }
  num? _id;
  String? _userName;
  String? _sex;
  String? _avatar;
  String? _password;
  String? _status;
  String? _delFlag;
  String? _loginIp;
  String? _loginDate;
  String? _remark;
  num? _exp;
  num? _renks;
  String? _tags;
  num? _money;
  String? _sign;
  num? _userId;
  String? _createTime;
  String? _introduce;
GreatMasterModel copyWith({  num? id,
  String? userName,
  String? sex,
  String? avatar,
  String? password,
  String? status,
  String? delFlag,
  String? loginIp,
  String? loginDate,
  String? remark,
  num? exp,
  num? renks,
  String? tags,
  num? money,
  String? sign,
  num? userId,
  String? createTime,
  String? introduce,
}) => GreatMasterModel(  id: id ?? _id,
  userName: userName ?? _userName,
  sex: sex ?? _sex,
  avatar: avatar ?? _avatar,
  password: password ?? _password,
  status: status ?? _status,
  delFlag: delFlag ?? _delFlag,
  loginIp: loginIp ?? _loginIp,
  loginDate: loginDate ?? _loginDate,
  remark: remark ?? _remark,
  exp: exp ?? _exp,
  renks: renks ?? _renks,
  tags: tags ?? _tags,
  money: money ?? _money,
  sign: sign ?? _sign,
  userId: userId ?? _userId,
  createTime: createTime ?? _createTime,
  introduce: introduce ?? _introduce,
);
  num? get id => _id;
  String? get userName => _userName;
  String? get sex => _sex;
  String? get avatar => _avatar;
  String? get password => _password;
  String? get status => _status;
  String? get delFlag => _delFlag;
  String? get loginIp => _loginIp;
  String? get loginDate => _loginDate;
  String? get remark => _remark;
  num? get exp => _exp;
  num? get renks => _renks;
  String? get tags => _tags;
  num? get money => _money;
  String? get sign => _sign;
  num? get userId => _userId;
  String? get createTime => _createTime;
  String? get introduce => _introduce;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['id'] = _id;
    map['userName'] = _userName;
    map['sex'] = _sex;
    map['avatar'] = _avatar;
    map['password'] = _password;
    map['status'] = _status;
    map['delFlag'] = _delFlag;
    map['loginIp'] = _loginIp;
    map['loginDate'] = _loginDate;
    map['remark'] = _remark;
    map['exp'] = _exp;
    map['renks'] = _renks;
    map['tags'] = _tags;
    map['money'] = _money;
    map['sign'] = _sign;
    map['userId'] = _userId;
    map['createTime'] = _createTime;
    map['introduce'] = _introduce;
    return map;
  }

}