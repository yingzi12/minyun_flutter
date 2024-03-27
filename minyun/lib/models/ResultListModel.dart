
import 'package:minyun/models/JsonSerializable.dart';

class ResultListModel<T  extends JsonSerializable> {
  ResultListModel({
    num? code,
    String? msg,
    List<T>? data,
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

  ResultListModel.fromJson(dynamic json, T Function(dynamic) fromJson) {
    _code = json['code'];
    _msg = json['msg'];
    if (json['data'] != null) {
      _data = [];
      json['data'].forEach((v) {
        _data?.add(fromJson(v));
      });
    }
    _total = json['total'];
    _fail = json['fail'];
    _succ = json['succ'];
  }

  num? _code;
  String? _msg;
  List<T>? _data;
  num? _total;
  bool? _fail;
  bool? _succ;

  num? get code => _code;
  String? get msg => _msg;
  List<T>? get data => _data;
  num? get total => _total;
  bool? get fail => _fail;
  bool? get succ => _succ;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['code'] = _code;
    map['msg'] = _msg;
    if (_data != null) {
      map['data'] = _data?.map((v) => v?.toJson()).toList();
    }
    map['total'] = _total;
    map['fail'] = _fail;
    map['succ'] = _succ;
    return map;
  }
}
