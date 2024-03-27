import 'package:minyun/models/JsonSerializable.dart';

/// code : 0
/// msg : ""
/// data : {"id":0,"bankName":"","bankCard":"","bankUser":"","createTime":"","updateTime":""}
/// total : 0
/// fail : true
/// succ : true

class ResultModel<T  extends JsonSerializable> {
  ResultModel({
    num? code,
    String? msg,
    T? data,
    num? total,
    bool? fail,
    bool? succ,
  }) {
    _code = code;
    _msg = msg;
    _data = data;
    _total = total;
    _fail = fail;
    _succ = succ;
  }

  ResultModel.fromJson(dynamic json, T Function(dynamic json) fromJsonT) {
    _code = json['code'];
    _msg = json['msg'];
    _data = json['data'] != null ? fromJsonT(json['data']) : null;
    _total = json['total'];
    _fail = json['fail'];
    _succ = json['succ'];
  }

  num? _code;
  String? _msg;
  T? _data;
  num? _total;
  bool? _fail;
  bool? _succ;

  ResultModel<T> copyWith({
    num? code,
    String? msg,
    T? data,
    num? total,
    bool? fail,
    bool? succ,
  }) =>
      ResultModel<T>(
        code: code ?? _code,
        msg: msg ?? _msg,
        data: data ?? _data,
        total: total ?? _total,
        fail: fail ?? _fail,
        succ: succ ?? _succ,
      );

  num? get code => _code;
  String? get msg => _msg;
  T? get data => _data;
  num? get total => _total;
  bool? get fail => _fail;
  bool? get succ => _succ;

  Map<String, dynamic> toJson(Map<String, dynamic> Function(T data) toJsonT) {
    final map = <String, dynamic>{};
    map['code'] = _code;
    map['msg'] = _msg;
    if (_data != null) {
      map['data'] = toJsonT(_data!);
    }
    map['total'] = _total;
    map['fail'] = _fail;
    map['succ'] = _succ;
    return map;
  }
}
