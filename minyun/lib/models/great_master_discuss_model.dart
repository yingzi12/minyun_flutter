import 'dart:convert';

import 'package:minyun/models/JsonSerializable.dart';
/// id : 0
/// gaid : 0
/// ganame : ""
/// comment : ""
/// reply : ""
/// countLike : 0
/// countReply : 0
/// countDisagree : 0
/// status : 0
/// createId : 0
/// createName : ""
/// createTime : ""
/// updateId : 0
/// updateName : ""
/// updateTime : ""
/// eid : 0
/// title : ""
/// types : 0
/// circleUrl : ""
/// nickname : ""
/// replyNickname : ""

GreatMasterDiscussModel greatMasterDiscussModelFromJson(String str) => GreatMasterDiscussModel.fromJson(json.decode(str));
String greatMasterDiscussModelToJson(GreatMasterDiscussModel data) => json.encode(data.toJson());
class GreatMasterDiscussModel implements JsonSerializable {
  GreatMasterDiscussModel({
      num? id, 
      num? gaid, 
      String? ganame, 
      String? comment, 
      String? reply, 
      num? countLike, 
      num? countReply, 
      num? countDisagree, 
      num? status, 
      num? createId, 
      String? createName, 
      String? createTime, 
      num? updateId, 
      String? updateName, 
      String? updateTime, 
      num? eid, 
      String? title, 
      num? types, 
      String? circleUrl, 
      String? nickname, 
      String? replyNickname,}){
    _id = id;
    _gaid = gaid;
    _ganame = ganame;
    _comment = comment;
    _reply = reply;
    _countLike = countLike;
    _countReply = countReply;
    _countDisagree = countDisagree;
    _status = status;
    _createId = createId;
    _createName = createName;
    _createTime = createTime;
    _updateId = updateId;
    _updateName = updateName;
    _updateTime = updateTime;
    _eid = eid;
    _title = title;
    _types = types;
    _circleUrl = circleUrl;
    _nickname = nickname;
    _replyNickname = replyNickname;
}

  GreatMasterDiscussModel.fromJson(dynamic json) {
    _id = json['id'];
    _gaid = json['gaid'];
    _ganame = json['ganame'];
    _comment = json['comment'];
    _reply = json['reply'];
    _countLike = json['countLike'];
    _countReply = json['countReply'];
    _countDisagree = json['countDisagree'];
    _status = json['status'];
    _createId = json['createId'];
    _createName = json['createName'];
    _createTime = json['createTime'];
    _updateId = json['updateId'];
    _updateName = json['updateName'];
    _updateTime = json['updateTime'];
    _eid = json['eid'];
    _title = json['title'];
    _types = json['types'];
    _circleUrl = json['circleUrl'];
    _nickname = json['nickname'];
    _replyNickname = json['replyNickname'];
  }
  num? _id;
  num? _gaid;
  String? _ganame;
  String? _comment;
  String? _reply;
  num? _countLike;
  num? _countReply;
  num? _countDisagree;
  num? _status;
  num? _createId;
  String? _createName;
  String? _createTime;
  num? _updateId;
  String? _updateName;
  String? _updateTime;
  num? _eid;
  String? _title;
  num? _types;
  String? _circleUrl;
  String? _nickname;
  String? _replyNickname;
GreatMasterDiscussModel copyWith({  num? id,
  num? gaid,
  String? ganame,
  String? comment,
  String? reply,
  num? countLike,
  num? countReply,
  num? countDisagree,
  num? status,
  num? createId,
  String? createName,
  String? createTime,
  num? updateId,
  String? updateName,
  String? updateTime,
  num? eid,
  String? title,
  num? types,
  String? circleUrl,
  String? nickname,
  String? replyNickname,
}) => GreatMasterDiscussModel(  id: id ?? _id,
  gaid: gaid ?? _gaid,
  ganame: ganame ?? _ganame,
  comment: comment ?? _comment,
  reply: reply ?? _reply,
  countLike: countLike ?? _countLike,
  countReply: countReply ?? _countReply,
  countDisagree: countDisagree ?? _countDisagree,
  status: status ?? _status,
  createId: createId ?? _createId,
  createName: createName ?? _createName,
  createTime: createTime ?? _createTime,
  updateId: updateId ?? _updateId,
  updateName: updateName ?? _updateName,
  updateTime: updateTime ?? _updateTime,
  eid: eid ?? _eid,
  title: title ?? _title,
  types: types ?? _types,
  circleUrl: circleUrl ?? _circleUrl,
  nickname: nickname ?? _nickname,
  replyNickname: replyNickname ?? _replyNickname,
);
  num? get id => _id;
  num? get gaid => _gaid;
  String? get ganame => _ganame;
  String? get comment => _comment;
  String? get reply => _reply;
  num? get countLike => _countLike;
  num? get countReply => _countReply;
  num? get countDisagree => _countDisagree;
  num? get status => _status;
  num? get createId => _createId;
  String? get createName => _createName;
  String? get createTime => _createTime;
  num? get updateId => _updateId;
  String? get updateName => _updateName;
  String? get updateTime => _updateTime;
  num? get eid => _eid;
  String? get title => _title;
  num? get types => _types;
  String? get circleUrl => _circleUrl;
  String? get nickname => _nickname;
  String? get replyNickname => _replyNickname;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['id'] = _id;
    map['gaid'] = _gaid;
    map['ganame'] = _ganame;
    map['comment'] = _comment;
    map['reply'] = _reply;
    map['countLike'] = _countLike;
    map['countReply'] = _countReply;
    map['countDisagree'] = _countDisagree;
    map['status'] = _status;
    map['createId'] = _createId;
    map['createName'] = _createName;
    map['createTime'] = _createTime;
    map['updateId'] = _updateId;
    map['updateName'] = _updateName;
    map['updateTime'] = _updateTime;
    map['eid'] = _eid;
    map['title'] = _title;
    map['types'] = _types;
    map['circleUrl'] = _circleUrl;
    map['nickname'] = _nickname;
    map['replyNickname'] = _replyNickname;
    return map;
  }

}