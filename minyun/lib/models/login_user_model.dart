import 'dart:convert';

import 'package:minyun/models/JsonSerializable.dart';
/// searchValue : ""
/// createBy : ""
/// createTime : ""
/// createId : 0
/// createName : ""
/// updateBy : ""
/// updateTime : ""
/// updateName : ""
/// updateId : 0
/// remark : ""
/// userId : 0
/// deptId : 0
/// userName : ""
/// nickName : ""
/// email : ""
/// phonenumber : ""
/// sex : ""
/// avatar : ""
/// status : ""
/// delFlag : ""
/// loginIp : ""
/// loginDate : ""
/// roleId : 0
/// ranks : 0
/// sign : ""
/// starSign : ""
/// admin : true

LoginUserModel loginUserModelFromJson(String str) => LoginUserModel.fromJson(json.decode(str));
String loginUserModelToJson(LoginUserModel data) => json.encode(data.toJson());
class LoginUserModel  implements JsonSerializable  {
  LoginUserModel({
      String? searchValue, 
      String? createBy, 
      String? createTime, 
      num? createId, 
      String? createName, 
      String? updateBy, 
      String? updateTime, 
      String? updateName, 
      num? updateId, 
      String? remark, 
      num? userId, 
      num? deptId, 
      String? userName, 
      String? nickName, 
      String? email, 
      String? phonenumber, 
      String? sex, 
      String? avatar, 
      String? status, 
      String? delFlag, 
      String? loginIp, 
      String? loginDate, 
      num? roleId, 
      num? ranks, 
      String? sign, 
      String? starSign, 
      bool? admin,}){
    _searchValue = searchValue;
    _createBy = createBy;
    _createTime = createTime;
    _createId = createId;
    _createName = createName;
    _updateBy = updateBy;
    _updateTime = updateTime;
    _updateName = updateName;
    _updateId = updateId;
    _remark = remark;
    _userId = userId;
    _deptId = deptId;
    _userName = userName;
    _nickName = nickName;
    _email = email;
    _phonenumber = phonenumber;
    _sex = sex;
    _avatar = avatar;
    _status = status;
    _delFlag = delFlag;
    _loginIp = loginIp;
    _loginDate = loginDate;
    _roleId = roleId;
    _ranks = ranks;
    _sign = sign;
    _starSign = starSign;
    _admin = admin;
}

  LoginUserModel.fromJson(dynamic json) {
    _searchValue = json['searchValue'];
    _createBy = json['createBy'];
    _createTime = json['createTime'];
    _createId = json['createId'];
    _createName = json['createName'];
    _updateBy = json['updateBy'];
    _updateTime = json['updateTime'];
    _updateName = json['updateName'];
    _updateId = json['updateId'];
    _remark = json['remark'];
    _userId = json['userId'];
    _deptId = json['deptId'];
    _userName = json['userName'];
    _nickName = json['nickName'];
    _email = json['email'];
    _phonenumber = json['phonenumber'];
    _sex = json['sex'];
    _avatar = json['avatar'];
    _status = json['status'];
    _delFlag = json['delFlag'];
    _loginIp = json['loginIp'];
    _loginDate = json['loginDate'];
    _roleId = json['roleId'];
    _ranks = json['ranks'];
    _sign = json['sign'];
    _starSign = json['starSign'];
    _admin = json['admin'];
  }
  String? _searchValue;
  String? _createBy;
  String? _createTime;
  num? _createId;
  String? _createName;
  String? _updateBy;
  String? _updateTime;
  String? _updateName;
  num? _updateId;
  String? _remark;
  num? _userId;
  num? _deptId;
  String? _userName;
  String? _nickName;
  String? _email;
  String? _phonenumber;
  String? _sex;
  String? _avatar;
  String? _status;
  String? _delFlag;
  String? _loginIp;
  String? _loginDate;
  num? _roleId;
  num? _ranks;
  String? _sign;
  String? _starSign;
  bool? _admin;
LoginUserModel copyWith({  String? searchValue,
  String? createBy,
  String? createTime,
  num? createId,
  String? createName,
  String? updateBy,
  String? updateTime,
  String? updateName,
  num? updateId,
  String? remark,
  num? userId,
  num? deptId,
  String? userName,
  String? nickName,
  String? email,
  String? phonenumber,
  String? sex,
  String? avatar,
  String? status,
  String? delFlag,
  String? loginIp,
  String? loginDate,
  num? roleId,
  num? ranks,
  String? sign,
  String? starSign,
  bool? admin,
}) => LoginUserModel(  searchValue: searchValue ?? _searchValue,
  createBy: createBy ?? _createBy,
  createTime: createTime ?? _createTime,
  createId: createId ?? _createId,
  createName: createName ?? _createName,
  updateBy: updateBy ?? _updateBy,
  updateTime: updateTime ?? _updateTime,
  updateName: updateName ?? _updateName,
  updateId: updateId ?? _updateId,
  remark: remark ?? _remark,
  userId: userId ?? _userId,
  deptId: deptId ?? _deptId,
  userName: userName ?? _userName,
  nickName: nickName ?? _nickName,
  email: email ?? _email,
  phonenumber: phonenumber ?? _phonenumber,
  sex: sex ?? _sex,
  avatar: avatar ?? _avatar,
  status: status ?? _status,
  delFlag: delFlag ?? _delFlag,
  loginIp: loginIp ?? _loginIp,
  loginDate: loginDate ?? _loginDate,
  roleId: roleId ?? _roleId,
  ranks: ranks ?? _ranks,
  sign: sign ?? _sign,
  starSign: starSign ?? _starSign,
  admin: admin ?? _admin,
);
  String? get searchValue => _searchValue;
  String? get createBy => _createBy;
  String? get createTime => _createTime;
  num? get createId => _createId;
  String? get createName => _createName;
  String? get updateBy => _updateBy;
  String? get updateTime => _updateTime;
  String? get updateName => _updateName;
  num? get updateId => _updateId;
  String? get remark => _remark;
  num? get userId => _userId;
  num? get deptId => _deptId;
  String? get userName => _userName;
  String? get nickName => _nickName;
  String? get email => _email;
  String? get phonenumber => _phonenumber;
  String? get sex => _sex;
  String? get avatar => _avatar;
  String? get status => _status;
  String? get delFlag => _delFlag;
  String? get loginIp => _loginIp;
  String? get loginDate => _loginDate;
  num? get roleId => _roleId;
  num? get ranks => _ranks;
  String? get sign => _sign;
  String? get starSign => _starSign;
  bool? get admin => _admin;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['searchValue'] = _searchValue;
    map['createBy'] = _createBy;
    map['createTime'] = _createTime;
    map['createId'] = _createId;
    map['createName'] = _createName;
    map['updateBy'] = _updateBy;
    map['updateTime'] = _updateTime;
    map['updateName'] = _updateName;
    map['updateId'] = _updateId;
    map['remark'] = _remark;
    map['userId'] = _userId;
    map['deptId'] = _deptId;
    map['userName'] = _userName;
    map['nickName'] = _nickName;
    map['email'] = _email;
    map['phonenumber'] = _phonenumber;
    map['sex'] = _sex;
    map['avatar'] = _avatar;
    map['status'] = _status;
    map['delFlag'] = _delFlag;
    map['loginIp'] = _loginIp;
    map['loginDate'] = _loginDate;
    map['roleId'] = _roleId;
    map['ranks'] = _ranks;
    map['sign'] = _sign;
    map['starSign'] = _starSign;
    map['admin'] = _admin;
    return map;
  }

}