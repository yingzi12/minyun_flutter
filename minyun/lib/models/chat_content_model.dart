import 'dart:convert';
/// id : 0
/// sendAccount : 0
/// receiverUserId : 0
/// content : ""
/// isImage : 0
/// imgUrl : ""
/// createTime : ""
/// status : 0

ChatContentModel chatContentModelFromJson(String str) => ChatContentModel.fromJson(json.decode(str));
String chatContentModelToJson(ChatContentModel data) => json.encode(data.toJson());
class ChatContentModel {
  ChatContentModel({
      num? id, 
      num? sendAccount, 
      num? receiverUserId, 
      String? content, 
      num? isImage, 
      String? imgUrl, 
      String? createTime, 
      num? status,}){
    _id = id;
    _sendAccount = sendAccount;
    _receiverUserId = receiverUserId;
    _content = content;
    _isImage = isImage;
    _imgUrl = imgUrl;
    _createTime = createTime;
    _status = status;
}

  ChatContentModel.fromJson(dynamic json) {
    _id = json['id'];
    _sendAccount = json['sendAccount'];
    _receiverUserId = json['receiverUserId'];
    _content = json['content'];
    _isImage = json['isImage'];
    _imgUrl = json['imgUrl'];
    _createTime = json['createTime'];
    _status = json['status'];
  }
  num? _id;
  num? _sendAccount;
  num? _receiverUserId;
  String? _content;
  num? _isImage;
  String? _imgUrl;
  String? _createTime;
  num? _status;
ChatContentModel copyWith({  num? id,
  num? sendAccount,
  num? receiverUserId,
  String? content,
  num? isImage,
  String? imgUrl,
  String? createTime,
  num? status,
}) => ChatContentModel(  id: id ?? _id,
  sendAccount: sendAccount ?? _sendAccount,
  receiverUserId: receiverUserId ?? _receiverUserId,
  content: content ?? _content,
  isImage: isImage ?? _isImage,
  imgUrl: imgUrl ?? _imgUrl,
  createTime: createTime ?? _createTime,
  status: status ?? _status,
);
  num? get id => _id;
  num? get sendAccount => _sendAccount;
  num? get receiverUserId => _receiverUserId;
  String? get content => _content;
  num? get isImage => _isImage;
  String? get imgUrl => _imgUrl;
  String? get createTime => _createTime;
  num? get status => _status;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['id'] = _id;
    map['sendAccount'] = _sendAccount;
    map['receiverUserId'] = _receiverUserId;
    map['content'] = _content;
    map['isImage'] = _isImage;
    map['imgUrl'] = _imgUrl;
    map['createTime'] = _createTime;
    map['status'] = _status;
    return map;
  }

}