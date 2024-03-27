
import 'JsonSerializable.dart';

/// id : 0
/// name : "string"
/// nickname : "string"
/// email : "string"
/// isEmail : 0
/// createTime : "2024-01-12T05:45:47.823Z"
/// updateTime : "2024-01-12T05:45:47.823Z"
/// intro : "string"
/// directions : "string"
/// imgUrl : "string"
/// countLike : 0
/// countSee : 0
/// countAttention : 0
/// income : 0
/// withdraw : 0
/// isAttention : 0
/// vip : 0
/// credit : 0
/// vipExpirationTime : "2024-01-12T05:45:47.823Z"

class UserModel  implements JsonSerializable {
  UserModel({
      num? id, 
      String? name, 
      String? nickname, 
      String? email, 
      num? isEmail, 
      String? createTime, 
      String? updateTime, 
      String? intro, 
      String? directions, 
      String? imgUrl, 
      num? countLike, 
      num? countSee, 
      num? countAttention, 
      num? income, 
      num? withdraw, 
      num? isAttention, 
      num? vip,
    num? vipTitle,

    num? credit, 
      String? vipExpirationTime,}){
    _id = id;
    _name = name;
    _nickname = nickname;
    _email = email;
    _isEmail = isEmail;
    _createTime = createTime;
    _updateTime = updateTime;
    _intro = intro;
    _directions = directions;
    _imgUrl = imgUrl;
    _countLike = countLike;
    _countSee = countSee;
    _countAttention = countAttention;
    _income = income;
    _withdraw = withdraw;
    _isAttention = isAttention;
    _vip = vip;
    _vipTitle = vipTitle;
    _credit = credit;
    _vipExpirationTime = vipExpirationTime;
}

  UserModel.fromJson(dynamic json) {
    _id = json['id'];
    _name = json['name'];
    _nickname = json['nickname'];
    _email = json['email'];
    _isEmail = json['isEmail'];
    _createTime = json['createTime'];
    _updateTime = json['updateTime'];
    _intro = json['intro'];
    _directions = json['directions'];
    _imgUrl = json['imgUrl'];
    _countLike = json['countLike'];
    _countSee = json['countSee'];
    _countAttention = json['countAttention'];
    _income = json['income'];
    _withdraw = json['withdraw'];
    _isAttention = json['isAttention'];
    _vip = json['vip'];
    _vipTitle = json['vipTitle'];

    _credit = json['credit'];
    _vipExpirationTime = json['vipExpirationTime'];
  }
  num? _id;
  String? _name;
  String? _nickname;
  String? _email;
  num? _isEmail;
  String? _createTime;
  String? _updateTime;
  String? _intro;
  String? _directions;
  String? _imgUrl;
  num? _countLike;
  num? _countSee;
  num? _countAttention;
  num? _income;
  num? _withdraw;
  num? _isAttention;
  num? _vip;
  num? _vipTitle;

  num? _credit;
  String? _vipExpirationTime;
UserModel copyWith({  num? id,
  String? name,
  String? nickname,
  String? email,
  num? isEmail,
  String? createTime,
  String? updateTime,
  String? intro,
  String? directions,
  String? imgUrl,
  num? countLike,
  num? countSee,
  num? countAttention,
  num? income,
  num? withdraw,
  num? isAttention,
  num? vip,
  num? vipTitle,

  num? credit,
  String? vipExpirationTime,
}) => UserModel(  id: id ?? _id,
  name: name ?? _name,
  nickname: nickname ?? _nickname,
  email: email ?? _email,
  isEmail: isEmail ?? _isEmail,
  createTime: createTime ?? _createTime,
  updateTime: updateTime ?? _updateTime,
  intro: intro ?? _intro,
  directions: directions ?? _directions,
  imgUrl: imgUrl ?? _imgUrl,
  countLike: countLike ?? _countLike,
  countSee: countSee ?? _countSee,
  countAttention: countAttention ?? _countAttention,
  income: income ?? _income,
  withdraw: withdraw ?? _withdraw,
  isAttention: isAttention ?? _isAttention,
  vip: vip ?? _vip,
  vipTitle: vipTitle ?? _vipTitle,

  credit: credit ?? _credit,
  vipExpirationTime: vipExpirationTime ?? _vipExpirationTime,
);
  num? get id => _id;
  String? get name => _name;
  String? get nickname => _nickname;
  String? get email => _email;
  num? get isEmail => _isEmail;
  String? get createTime => _createTime;
  String? get updateTime => _updateTime;
  String? get intro => _intro;
  String? get directions => _directions;
  String? get imgUrl => _imgUrl;
  num? get countLike => _countLike;
  num? get countSee => _countSee;
  num? get countAttention => _countAttention;
  num? get income => _income;
  num? get withdraw => _withdraw;
  num? get isAttention => _isAttention;
  num? get vip => _vip;
  num? get vipTitle => _vipTitle;
  num? get credit => _credit;
  String? get vipExpirationTime => _vipExpirationTime;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['id'] = _id;
    map['name'] = _name;
    map['nickname'] = _nickname;
    map['email'] = _email;
    map['isEmail'] = _isEmail;
    map['createTime'] = _createTime;
    map['updateTime'] = _updateTime;
    map['intro'] = _intro;
    map['directions'] = _directions;
    map['imgUrl'] = _imgUrl;
    map['countLike'] = _countLike;
    map['countSee'] = _countSee;
    map['countAttention'] = _countAttention;
    map['income'] = _income;
    map['withdraw'] = _withdraw;
    map['isAttention'] = _isAttention;
    map['vip'] = _vip;
    map['vipTitle'] = _vipTitle;
    map['credit'] = _credit;
    map['vipExpirationTime'] = _vipExpirationTime;
    return map;
  }

}