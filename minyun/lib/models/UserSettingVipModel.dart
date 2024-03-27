
import 'JsonSerializable.dart';

/// id : 0
/// userId : 0
/// userName : ""
/// createTime : ""
/// rank : 0
/// updateTime : ""
/// price : 0
/// intro : ""
/// timeLong : 0
/// timeType : 0
/// countBuy : 0
/// introduce : ""
/// title : ""
/// status : 0

class UserSettingVipModel  implements JsonSerializable{
  UserSettingVipModel({
      num? id, 
      num? userId, 
      String? userName, 
      String? createTime, 
      num? rank, 
      String? updateTime, 
      num? price, 
      String? intro, 
      num? timeLong, 
      num? timeType, 
      num? countBuy, 
      String? introduce, 
      String? title, 
      num? status,}){
    _id = id;
    _userId = userId;
    _userName = userName;
    _createTime = createTime;
    _rank = rank;
    _updateTime = updateTime;
    _price = price;
    _intro = intro;
    _timeLong = timeLong;
    _timeType = timeType;
    _countBuy = countBuy;
    _introduce = introduce;
    _title = title;
    _status = status;
}

  UserSettingVipModel.fromJson(dynamic json) {
    _id = json['id'];
    _userId = json['userId'];
    _userName = json['userName'];
    _createTime = json['createTime'];
    _rank = json['rank'];
    _updateTime = json['updateTime'];
    _price = json['price'];
    _intro = json['intro'];
    _timeLong = json['timeLong'];
    _timeType = json['timeType'];
    _countBuy = json['countBuy'];
    _introduce = json['introduce'];
    _title = json['title'];
    _status = json['status'];
  }
  num? _id;
  num? _userId;
  String? _userName;
  String? _createTime;
  num? _rank;
  String? _updateTime;
  num? _price;
  String? _intro;
  num? _timeLong;
  num? _timeType;
  num? _countBuy;
  String? _introduce;
  String? _title;
  num? _status;
UserSettingVipModel copyWith({  num? id,
  num? userId,
  String? userName,
  String? createTime,
  num? rank,
  String? updateTime,
  num? price,
  String? intro,
  num? timeLong,
  num? timeType,
  num? countBuy,
  String? introduce,
  String? title,
  num? status,
}) => UserSettingVipModel(  id: id ?? _id,
  userId: userId ?? _userId,
  userName: userName ?? _userName,
  createTime: createTime ?? _createTime,
  rank: rank ?? _rank,
  updateTime: updateTime ?? _updateTime,
  price: price ?? _price,
  intro: intro ?? _intro,
  timeLong: timeLong ?? _timeLong,
  timeType: timeType ?? _timeType,
  countBuy: countBuy ?? _countBuy,
  introduce: introduce ?? _introduce,
  title: title ?? _title,
  status: status ?? _status,
);
  num? get id => _id;
  num? get userId => _userId;
  String? get userName => _userName;
  String? get createTime => _createTime;
  num? get rank => _rank;
  String? get updateTime => _updateTime;
  num? get price => _price;
  String? get intro => _intro;
  num? get timeLong => _timeLong;
  num? get timeType => _timeType;
  num? get countBuy => _countBuy;
  String? get introduce => _introduce;
  String? get title => _title;
  num? get status => _status;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['id'] = _id;
    map['userId'] = _userId;
    map['userName'] = _userName;
    map['createTime'] = _createTime;
    map['rank'] = _rank;
    map['updateTime'] = _updateTime;
    map['price'] = _price;
    map['intro'] = _intro;
    map['timeLong'] = _timeLong;
    map['timeType'] = _timeType;
    map['countBuy'] = _countBuy;
    map['introduce'] = _introduce;
    map['title'] = _title;
    map['status'] = _status;
    return map;
  }

}
 List<UserSettingVipModel> getValueList() {
  List<UserSettingVipModel> actorsDetails = [];

  actorsDetails.add(UserSettingVipModel(
    id:1,
    userId: 1,
    title: 'Hrithik Roshan',
    price: 11.0,
    timeLong: 1,
    timeType: 2,
    status: 2,
    intro: "这是简介",
    introduce: "这是说明这是说明这是说明这是说明这是说明这是说明这是说明这是说明",

  ));actorsDetails.add(UserSettingVipModel(
    id:2,
    userId: 1,
    title: 'Hrithik Roshan',
    price: 11.0,
    timeLong: 1,
    timeType: 2,
    status: 2,
    intro: "这是简介",
    introduce: "这是说明这是说明这是说明这是说明这是说明这是说明这是说明这是说明",

  ));actorsDetails.add(UserSettingVipModel(
    id:3,
    userId: 1,
    title: 'Hrithik Roshan',
    price: 11.0,
    timeLong: 1,
    timeType: 2,
    status: 2,
    intro: "这是简介",
    introduce: "这是说明这是说明这是说明这是说明这是说明这是说明这是说明这是说明",

  ));

      return actorsDetails;
}
