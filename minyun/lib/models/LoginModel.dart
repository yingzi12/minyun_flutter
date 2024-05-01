//
import 'package:minyun/models/login_user_model.dart';

import 'JsonSerializable.dart';

/// token : ""
/// accessToken : ""
/// refreshToken : ""
/// code : 0
/// LoginUser : {"id":0,"name":"","nickname":"","email":"","isEmail":0,"password":"","salt":"","createTime":"","updateTime":"","intro":"","directions":"","imgUrl":"","countLike":0,"countSee":0,"countAttention":0,"vip":0,"credit":0,"income":0,"withdraw":0,"vipExpirationTime":""}
/// loginTime : 0
/// expireTime : 0

class LoginModel  implements JsonSerializable {
  LoginModel({
      String? token,
      String? accessToken,
      String? refreshToken,
      num? code,
      LoginUserModel? user,
      num? loginTime,
      num? expireTime,}){
    _token = token;
    _accessToken = accessToken;
    _refreshToken = refreshToken;
    _code = code;
    _user = user;
    _loginTime = loginTime;
    _expireTime = expireTime;
}

  LoginModel.fromJson(dynamic json) {
    _token = json['token'];
    _accessToken = json['accessToken'];
    _refreshToken = json['refreshToken'];
    _code = json['code'];
    _user = json['user'] != null ? LoginUserModel.fromJson(json['user']) : null;
    _loginTime = json['loginTime'];
    _expireTime = json['expireTime'];
  }
  String? _token;
  String? _accessToken;
  String? _refreshToken;
  num? _code;
  LoginUserModel? _user;
  num? _loginTime;
  num? _expireTime;
LoginModel copyWith({  String? token,
  String? accessToken,
  String? refreshToken,
  num? code,
  LoginUserModel? user,
  num? loginTime,
  num? expireTime,
}) => LoginModel(  token: token ?? _token,
  accessToken: accessToken ?? _accessToken,
  refreshToken: refreshToken ?? _refreshToken,
  code: code ?? _code,
  user: user ?? _user,
  loginTime: loginTime ?? _loginTime,
  expireTime: expireTime ?? _expireTime,
);
  String? get token => _token;
  String? get accessToken => _accessToken;
  String? get refreshToken => _refreshToken;
  num? get code => _code;
  LoginUserModel? get user => _user;
  num? get loginTime => _loginTime;
  num? get expireTime => _expireTime;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['token'] = _token;
    map['accessToken'] = _accessToken;
    map['refreshToken'] = _refreshToken;
    map['code'] = _code;
    if (_user != null) {
      map['user'] = _user?.toJson();
    }
    map['loginTime'] = _loginTime;
    map['expireTime'] = _expireTime;
    return map;
  }

}
//
// /// id : 0
// /// name : ""
// /// nickname : ""
// /// email : ""
// /// isEmail : 0
// /// password : ""
// /// salt : ""
// /// createTime : ""
// /// updateTime : ""
// /// intro : ""
// /// directions : ""
// /// imgUrl : ""
// /// countLike : 0
// /// countSee : 0
// /// countAttention : 0
// /// vip : 0
// /// credit : 0
// /// income : 0
// /// withdraw : 0
// /// vipExpirationTime : ""
//
// class LoginUser {
//   LoginUser({
//       num? id,
//       String? name,
//       String? nickname,
//       String? email,
//       num? isEmail,
//       String? password,
//       String? salt,
//       String? createTime,
//       String? updateTime,
//       String? intro,
//       String? directions,
//       String? imgUrl,
//       num? countLike,
//       num? countSee,
//       num? countAttention,
//       num? vip,
//       num? credit,
//       num? income,
//       num? withdraw,
//       String? vipExpirationTime,}){
//     _id = id;
//     _name = name;
//     _nickname = nickname;
//     _email = email;
//     _isEmail = isEmail;
//     _password = password;
//     _salt = salt;
//     _createTime = createTime;
//     _updateTime = updateTime;
//     _intro = intro;
//     _directions = directions;
//     _imgUrl = imgUrl;
//     _countLike = countLike;
//     _countSee = countSee;
//     _countAttention = countAttention;
//     _vip = vip;
//     _credit = credit;
//     _income = income;
//     _withdraw = withdraw;
//     _vipExpirationTime = vipExpirationTime;
// }
//
//   LoginUser.fromJson(dynamic json) {
//     _id = json['id'];
//     _name = json['name'];
//     _nickname = json['nickname'];
//     _email = json['email'];
//     _isEmail = json['isEmail'];
//     _password = json['password'];
//     _salt = json['salt'];
//     _createTime = json['createTime'];
//     _updateTime = json['updateTime'];
//     _intro = json['intro'];
//     _directions = json['directions'];
//     _imgUrl = json['imgUrl'];
//     _countLike = json['countLike'];
//     _countSee = json['countSee'];
//     _countAttention = json['countAttention'];
//     _vip = json['vip'];
//     _credit = json['credit'];
//     _income = json['income'];
//     _withdraw = json['withdraw'];
//     _vipExpirationTime = json['vipExpirationTime'];
//   }
//   num? _id;
//   String? _name;
//   String? _nickname;
//   String? _email;
//   num? _isEmail;
//   String? _password;
//   String? _salt;
//   String? _createTime;
//   String? _updateTime;
//   String? _intro;
//   String? _directions;
//   String? _imgUrl;
//   num? _countLike;
//   num? _countSee;
//   num? _countAttention;
//   num? _vip;
//   num? _credit;
//   num? _income;
//   num? _withdraw;
//   String? _vipExpirationTime;
// LoginUser copyWith({  num? id,
//   String? name,
//   String? nickname,
//   String? email,
//   num? isEmail,
//   String? password,
//   String? salt,
//   String? createTime,
//   String? updateTime,
//   String? intro,
//   String? directions,
//   String? imgUrl,
//   num? countLike,
//   num? countSee,
//   num? countAttention,
//   num? vip,
//   num? credit,
//   num? income,
//   num? withdraw,
//   String? vipExpirationTime,
// }) => LoginUser(  id: id ?? _id,
//   name: name ?? _name,
//   nickname: nickname ?? _nickname,
//   email: email ?? _email,
//   isEmail: isEmail ?? _isEmail,
//   password: password ?? _password,
//   salt: salt ?? _salt,
//   createTime: createTime ?? _createTime,
//   updateTime: updateTime ?? _updateTime,
//   intro: intro ?? _intro,
//   directions: directions ?? _directions,
//   imgUrl: imgUrl ?? _imgUrl,
//   countLike: countLike ?? _countLike,
//   countSee: countSee ?? _countSee,
//   countAttention: countAttention ?? _countAttention,
//   vip: vip ?? _vip,
//   credit: credit ?? _credit,
//   income: income ?? _income,
//   withdraw: withdraw ?? _withdraw,
//   vipExpirationTime: vipExpirationTime ?? _vipExpirationTime,
// );
//   num? get id => _id;
//   String? get name => _name;
//   String? get nickname => _nickname;
//   String? get email => _email;
//   num? get isEmail => _isEmail;
//   String? get password => _password;
//   String? get salt => _salt;
//   String? get createTime => _createTime;
//   String? get updateTime => _updateTime;
//   String? get intro => _intro;
//   String? get directions => _directions;
//   String? get imgUrl => _imgUrl;
//   num? get countLike => _countLike;
//   num? get countSee => _countSee;
//   num? get countAttention => _countAttention;
//   num? get vip => _vip;
//   num? get credit => _credit;
//   num? get income => _income;
//   num? get withdraw => _withdraw;
//   String? get vipExpirationTime => _vipExpirationTime;
//
//   Map<String, dynamic> toJson() {
//     final map = <String, dynamic>{};
//     map['id'] = _id;
//     map['name'] = _name;
//     map['nickname'] = _nickname;
//     map['email'] = _email;
//     map['isEmail'] = _isEmail;
//     map['password'] = _password;
//     map['salt'] = _salt;
//     map['createTime'] = _createTime;
//     map['updateTime'] = _updateTime;
//     map['intro'] = _intro;
//     map['directions'] = _directions;
//     map['imgUrl'] = _imgUrl;
//     map['countLike'] = _countLike;
//     map['countSee'] = _countSee;
//     map['countAttention'] = _countAttention;
//     map['vip'] = _vip;
//     map['credit'] = _credit;
//     map['income'] = _income;
//     map['withdraw'] = _withdraw;
//     map['vipExpirationTime'] = _vipExpirationTime;
//     return map;
//   }
//
// }