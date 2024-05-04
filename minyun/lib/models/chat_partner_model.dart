import 'dart:convert';
/// id : 0
/// userId : 0
/// userName : ""
/// receiverUserId : 0
/// receiverUserName : ""
/// createTime : ""
/// lastTime : ""

ChatPartnerModel chatPartnerModelFromJson(String str) => ChatPartnerModel.fromJson(json.decode(str));
String chatPartnerModelToJson(ChatPartnerModel data) => json.encode(data.toJson());
class ChatPartnerModel {
  ChatPartnerModel({
      num? id, 
      num? userId, 
      String? userName, 
      num? receiverUserId, 
      String? receiverUserName, 
      String? createTime, 
      String? lastTime,}){
    _id = id;
    _userId = userId;
    _userName = userName;
    _receiverUserId = receiverUserId;
    _receiverUserName = receiverUserName;
    _createTime = createTime;
    _lastTime = lastTime;
}

  ChatPartnerModel.fromJson(dynamic json) {
    _id = json['id'];
    _userId = json['userId'];
    _userName = json['userName'];
    _receiverUserId = json['receiverUserId'];
    _receiverUserName = json['receiverUserName'];
    _createTime = json['createTime'];
    _lastTime = json['lastTime'];
  }
  num? _id;
  num? _userId;
  String? _userName;
  num? _receiverUserId;
  String? _receiverUserName;
  String? _createTime;
  String? _lastTime;
ChatPartnerModel copyWith({  num? id,
  num? userId,
  String? userName,
  num? receiverUserId,
  String? receiverUserName,
  String? createTime,
  String? lastTime,
}) => ChatPartnerModel(  id: id ?? _id,
  userId: userId ?? _userId,
  userName: userName ?? _userName,
  receiverUserId: receiverUserId ?? _receiverUserId,
  receiverUserName: receiverUserName ?? _receiverUserName,
  createTime: createTime ?? _createTime,
  lastTime: lastTime ?? _lastTime,
);
  num? get id => _id;
  num? get userId => _userId;
  String? get userName => _userName;
  num? get receiverUserId => _receiverUserId;
  String? get receiverUserName => _receiverUserName;
  String? get createTime => _createTime;
  String? get lastTime => _lastTime;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['id'] = _id;
    map['userId'] = _userId;
    map['userName'] = _userName;
    map['receiverUserId'] = _receiverUserId;
    map['receiverUserName'] = _receiverUserName;
    map['createTime'] = _createTime;
    map['lastTime'] = _lastTime;
    return map;
  }

}