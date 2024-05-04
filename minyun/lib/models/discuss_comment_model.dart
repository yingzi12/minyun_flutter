import 'dart:convert';
/// id : 0
/// wid : 0
/// wname : ""
/// upid : 0
/// comment : ""
/// reply : ""
/// ranks : 0
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
/// did : 0
/// pid : 0
/// circleUrl : ""
/// replyHide : true
/// comHide : true
/// replyComment : ""
/// replyList : [{"id":0,"wid":0,"wname":"","upid":0,"comment":"","reply":"","ranks":0,"countLike":0,"countReply":0,"countDisagree":0,"status":0,"createId":0,"createName":"","createTime":"","updateId":0,"updateName":"","updateTime":"","eid":0,"title":"","types":0,"did":0,"pid":0,"circleUrl":"","replyHide":true,"comHide":true,"replyComment":"","replyList":[{}],"nickname":"","replyNickname":"","sid":0,"sname":"","source":0}]
/// nickname : ""
/// replyNickname : ""
/// sid : 0
/// sname : ""
/// source : 0

DiscussCommentModel discussCommentModelFromJson(String str) => DiscussCommentModel.fromJson(json.decode(str));
String discussCommentModelToJson(DiscussCommentModel data) => json.encode(data.toJson());
class DiscussCommentModel {
  DiscussCommentModel({
      num? id, 
      num? wid, 
      String? wname, 
      num? upid, 
      String? comment, 
      String? reply, 
      num? ranks, 
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
      num? did, 
      num? pid, 
      String? circleUrl, 
      bool? replyHide, 
      bool? comHide, 
      String? replyComment, 
      List<ReplyList>? replyList, 
      String? nickname, 
      String? replyNickname, 
      num? sid, 
      String? sname, 
      num? source,}){
    _id = id;
    _wid = wid;
    _wname = wname;
    _upid = upid;
    _comment = comment;
    _reply = reply;
    _ranks = ranks;
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
    _did = did;
    _pid = pid;
    _circleUrl = circleUrl;
    _replyHide = replyHide;
    _comHide = comHide;
    _replyComment = replyComment;
    _replyList = replyList;
    _nickname = nickname;
    _replyNickname = replyNickname;
    _sid = sid;
    _sname = sname;
    _source = source;
}

  DiscussCommentModel.fromJson(dynamic json) {
    _id = json['id'];
    _wid = json['wid'];
    _wname = json['wname'];
    _upid = json['upid'];
    _comment = json['comment'];
    _reply = json['reply'];
    _ranks = json['ranks'];
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
    _did = json['did'];
    _pid = json['pid'];
    _circleUrl = json['circleUrl'];
    _replyHide = json['replyHide'];
    _comHide = json['comHide'];
    _replyComment = json['replyComment'];
    if (json['replyList'] != null) {
      _replyList = [];
      json['replyList'].forEach((v) {
        _replyList?.add(ReplyList.fromJson(v));
      });
    }
    _nickname = json['nickname'];
    _replyNickname = json['replyNickname'];
    _sid = json['sid'];
    _sname = json['sname'];
    _source = json['source'];
  }
  num? _id;
  num? _wid;
  String? _wname;
  num? _upid;
  String? _comment;
  String? _reply;
  num? _ranks;
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
  num? _did;
  num? _pid;
  String? _circleUrl;
  bool? _replyHide;
  bool? _comHide;
  String? _replyComment;
  List<ReplyList>? _replyList;
  String? _nickname;
  String? _replyNickname;
  num? _sid;
  String? _sname;
  num? _source;
DiscussCommentModel copyWith({  num? id,
  num? wid,
  String? wname,
  num? upid,
  String? comment,
  String? reply,
  num? ranks,
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
  num? did,
  num? pid,
  String? circleUrl,
  bool? replyHide,
  bool? comHide,
  String? replyComment,
  List<ReplyList>? replyList,
  String? nickname,
  String? replyNickname,
  num? sid,
  String? sname,
  num? source,
}) => DiscussCommentModel(  id: id ?? _id,
  wid: wid ?? _wid,
  wname: wname ?? _wname,
  upid: upid ?? _upid,
  comment: comment ?? _comment,
  reply: reply ?? _reply,
  ranks: ranks ?? _ranks,
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
  did: did ?? _did,
  pid: pid ?? _pid,
  circleUrl: circleUrl ?? _circleUrl,
  replyHide: replyHide ?? _replyHide,
  comHide: comHide ?? _comHide,
  replyComment: replyComment ?? _replyComment,
  replyList: replyList ?? _replyList,
  nickname: nickname ?? _nickname,
  replyNickname: replyNickname ?? _replyNickname,
  sid: sid ?? _sid,
  sname: sname ?? _sname,
  source: source ?? _source,
);
  num? get id => _id;
  num? get wid => _wid;
  String? get wname => _wname;
  num? get upid => _upid;
  String? get comment => _comment;
  String? get reply => _reply;
  num? get ranks => _ranks;
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
  num? get did => _did;
  num? get pid => _pid;
  String? get circleUrl => _circleUrl;
  bool? get replyHide => _replyHide;
  bool? get comHide => _comHide;
  String? get replyComment => _replyComment;
  List<ReplyList>? get replyList => _replyList;
  String? get nickname => _nickname;
  String? get replyNickname => _replyNickname;
  num? get sid => _sid;
  String? get sname => _sname;
  num? get source => _source;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['id'] = _id;
    map['wid'] = _wid;
    map['wname'] = _wname;
    map['upid'] = _upid;
    map['comment'] = _comment;
    map['reply'] = _reply;
    map['ranks'] = _ranks;
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
    map['did'] = _did;
    map['pid'] = _pid;
    map['circleUrl'] = _circleUrl;
    map['replyHide'] = _replyHide;
    map['comHide'] = _comHide;
    map['replyComment'] = _replyComment;
    if (_replyList != null) {
      map['replyList'] = _replyList?.map((v) => v.toJson()).toList();
    }
    map['nickname'] = _nickname;
    map['replyNickname'] = _replyNickname;
    map['sid'] = _sid;
    map['sname'] = _sname;
    map['source'] = _source;
    return map;
  }

}

/// id : 0
/// wid : 0
/// wname : ""
/// upid : 0
/// comment : ""
/// reply : ""
/// ranks : 0
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
/// did : 0
/// pid : 0
/// circleUrl : ""
/// replyHide : true
/// comHide : true
/// replyComment : ""
/// replyList : [{}]
/// nickname : ""
/// replyNickname : ""
/// sid : 0
/// sname : ""
/// source : 0

ReplyList replyListFromJson(String str) => ReplyList.fromJson(json.decode(str));
String replyListToJson(ReplyList data) => json.encode(data.toJson());
class ReplyList {
  ReplyList({
      num? id, 
      num? wid, 
      String? wname, 
      num? upid, 
      String? comment, 
      String? reply, 
      num? ranks, 
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
      num? did, 
      num? pid, 
      String? circleUrl, 
      bool? replyHide, 
      bool? comHide, 
      String? replyComment, 
      List<dynamic>? replyList, 
      String? nickname, 
      String? replyNickname, 
      num? sid, 
      String? sname, 
      num? source,}){
    _id = id;
    _wid = wid;
    _wname = wname;
    _upid = upid;
    _comment = comment;
    _reply = reply;
    _ranks = ranks;
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
    _did = did;
    _pid = pid;
    _circleUrl = circleUrl;
    _replyHide = replyHide;
    _comHide = comHide;
    _replyComment = replyComment;
    _replyList = replyList;
    _nickname = nickname;
    _replyNickname = replyNickname;
    _sid = sid;
    _sname = sname;
    _source = source;
}

  ReplyList.fromJson(dynamic json) {
    _id = json['id'];
    _wid = json['wid'];
    _wname = json['wname'];
    _upid = json['upid'];
    _comment = json['comment'];
    _reply = json['reply'];
    _ranks = json['ranks'];
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
    _did = json['did'];
    _pid = json['pid'];
    _circleUrl = json['circleUrl'];
    _replyHide = json['replyHide'];
    _comHide = json['comHide'];
    _replyComment = json['replyComment'];
    if (json['replyList'] != null) {
      _replyList = [];
      json['replyList'].forEach((v) {
        _replyList?.add(Dynamic.fromJson(v));
      });
    }
    _nickname = json['nickname'];
    _replyNickname = json['replyNickname'];
    _sid = json['sid'];
    _sname = json['sname'];
    _source = json['source'];
  }
  num? _id;
  num? _wid;
  String? _wname;
  num? _upid;
  String? _comment;
  String? _reply;
  num? _ranks;
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
  num? _did;
  num? _pid;
  String? _circleUrl;
  bool? _replyHide;
  bool? _comHide;
  String? _replyComment;
  List<dynamic>? _replyList;
  String? _nickname;
  String? _replyNickname;
  num? _sid;
  String? _sname;
  num? _source;
ReplyList copyWith({  num? id,
  num? wid,
  String? wname,
  num? upid,
  String? comment,
  String? reply,
  num? ranks,
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
  num? did,
  num? pid,
  String? circleUrl,
  bool? replyHide,
  bool? comHide,
  String? replyComment,
  List<dynamic>? replyList,
  String? nickname,
  String? replyNickname,
  num? sid,
  String? sname,
  num? source,
}) => ReplyList(  id: id ?? _id,
  wid: wid ?? _wid,
  wname: wname ?? _wname,
  upid: upid ?? _upid,
  comment: comment ?? _comment,
  reply: reply ?? _reply,
  ranks: ranks ?? _ranks,
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
  did: did ?? _did,
  pid: pid ?? _pid,
  circleUrl: circleUrl ?? _circleUrl,
  replyHide: replyHide ?? _replyHide,
  comHide: comHide ?? _comHide,
  replyComment: replyComment ?? _replyComment,
  replyList: replyList ?? _replyList,
  nickname: nickname ?? _nickname,
  replyNickname: replyNickname ?? _replyNickname,
  sid: sid ?? _sid,
  sname: sname ?? _sname,
  source: source ?? _source,
);
  num? get id => _id;
  num? get wid => _wid;
  String? get wname => _wname;
  num? get upid => _upid;
  String? get comment => _comment;
  String? get reply => _reply;
  num? get ranks => _ranks;
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
  num? get did => _did;
  num? get pid => _pid;
  String? get circleUrl => _circleUrl;
  bool? get replyHide => _replyHide;
  bool? get comHide => _comHide;
  String? get replyComment => _replyComment;
  List<dynamic>? get replyList => _replyList;
  String? get nickname => _nickname;
  String? get replyNickname => _replyNickname;
  num? get sid => _sid;
  String? get sname => _sname;
  num? get source => _source;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['id'] = _id;
    map['wid'] = _wid;
    map['wname'] = _wname;
    map['upid'] = _upid;
    map['comment'] = _comment;
    map['reply'] = _reply;
    map['ranks'] = _ranks;
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
    map['did'] = _did;
    map['pid'] = _pid;
    map['circleUrl'] = _circleUrl;
    map['replyHide'] = _replyHide;
    map['comHide'] = _comHide;
    map['replyComment'] = _replyComment;
    if (_replyList != null) {
      map['replyList'] = _replyList?.map((v) => v.toJson()).toList();
    }
    map['nickname'] = _nickname;
    map['replyNickname'] = _replyNickname;
    map['sid'] = _sid;
    map['sname'] = _sname;
    map['source'] = _source;
    return map;
  }

}