import 'dart:convert';

import 'package:minyun/models/JsonSerializable.dart';
/// id : 0
/// createId : 0
/// createTime : ""
/// receiveId : 0
/// receiveName : ""
/// receiveTime : ""
/// receiveStatus : ""
/// centent : ""
/// createName : ""
/// types : 0
/// isDelete : 0
/// isRead : 0
/// floor : 0
/// mid : 0
/// page : 0
/// size : 0

MessageModel messageModelFromJson(String str) => MessageModel.fromJson(json.decode(str));
String messageModelToJson(MessageModel data) => json.encode(data.toJson());
class MessageModel implements JsonSerializable {
  MessageModel({
      num? id, 
      num? createId, 
      String? createTime, 
      num? receiveId, 
      String? receiveName, 
      String? receiveTime, 
      String? receiveStatus, 
      String? centent, 
      String? createName, 
      num? types, 
      num? isDelete, 
      num? isRead, 
      num? floor, 
      num? mid, 
      num? page, 
      num? size,}){
    _id = id;
    _createId = createId;
    _createTime = createTime;
    _receiveId = receiveId;
    _receiveName = receiveName;
    _receiveTime = receiveTime;
    _receiveStatus = receiveStatus;
    _centent = centent;
    _createName = createName;
    _types = types;
    _isDelete = isDelete;
    _isRead = isRead;
    _floor = floor;
    _mid = mid;
    _page = page;
    _size = size;
}

  MessageModel.fromJson(dynamic json) {
    _id = json['id'];
    _createId = json['createId'];
    _createTime = json['createTime'];
    _receiveId = json['receiveId'];
    _receiveName = json['receiveName'];
    _receiveTime = json['receiveTime'];
    _receiveStatus = json['receiveStatus'];
    _centent = json['centent'];
    _createName = json['createName'];
    _types = json['types'];
    _isDelete = json['isDelete'];
    _isRead = json['isRead'];
    _floor = json['floor'];
    _mid = json['mid'];
    _page = json['page'];
    _size = json['size'];
  }
  num? _id;
  num? _createId;
  String? _createTime;
  num? _receiveId;
  String? _receiveName;
  String? _receiveTime;
  String? _receiveStatus;
  String? _centent;
  String? _createName;
  num? _types;
  num? _isDelete;
  num? _isRead;
  num? _floor;
  num? _mid;
  num? _page;
  num? _size;
MessageModel copyWith({  num? id,
  num? createId,
  String? createTime,
  num? receiveId,
  String? receiveName,
  String? receiveTime,
  String? receiveStatus,
  String? centent,
  String? createName,
  num? types,
  num? isDelete,
  num? isRead,
  num? floor,
  num? mid,
  num? page,
  num? size,
}) => MessageModel(  id: id ?? _id,
  createId: createId ?? _createId,
  createTime: createTime ?? _createTime,
  receiveId: receiveId ?? _receiveId,
  receiveName: receiveName ?? _receiveName,
  receiveTime: receiveTime ?? _receiveTime,
  receiveStatus: receiveStatus ?? _receiveStatus,
  centent: centent ?? _centent,
  createName: createName ?? _createName,
  types: types ?? _types,
  isDelete: isDelete ?? _isDelete,
  isRead: isRead ?? _isRead,
  floor: floor ?? _floor,
  mid: mid ?? _mid,
  page: page ?? _page,
  size: size ?? _size,
);
  num? get id => _id;
  num? get createId => _createId;
  String? get createTime => _createTime;
  num? get receiveId => _receiveId;
  String? get receiveName => _receiveName;
  String? get receiveTime => _receiveTime;
  String? get receiveStatus => _receiveStatus;
  String? get centent => _centent;
  String? get createName => _createName;
  num? get types => _types;
  num? get isDelete => _isDelete;
  num? get isRead => _isRead;
  num? get floor => _floor;
  num? get mid => _mid;
  num? get page => _page;
  num? get size => _size;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['id'] = _id;
    map['createId'] = _createId;
    map['createTime'] = _createTime;
    map['receiveId'] = _receiveId;
    map['receiveName'] = _receiveName;
    map['receiveTime'] = _receiveTime;
    map['receiveStatus'] = _receiveStatus;
    map['centent'] = _centent;
    map['createName'] = _createName;
    map['types'] = _types;
    map['isDelete'] = _isDelete;
    map['isRead'] = _isRead;
    map['floor'] = _floor;
    map['mid'] = _mid;
    map['page'] = _page;
    map['size'] = _size;
    return map;
  }

}