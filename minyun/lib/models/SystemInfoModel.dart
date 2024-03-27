/// updateTime : "string"
/// newVersion : "string"
/// version : 0
/// upVersion : "string"
/// intro : "string"
/// downUrl : "string"

class SystemInfoModel {
  SystemInfoModel({
      String? updateTime, 
      String? newVersion, 
      num? version, 
      String? upVersion, 
      String? intro, 
      String? downUrl,}){
    _updateTime = updateTime;
    _newVersion = newVersion;
    _version = version;
    _upVersion = upVersion;
    _intro = intro;
    _downUrl = downUrl;
}

  SystemInfoModel.fromJson(dynamic json) {
    _updateTime = json['updateTime'];
    _newVersion = json['newVersion'];
    _version = json['version'];
    _upVersion = json['upVersion'];
    _intro = json['intro'];
    _downUrl = json['downUrl'];
  }
  String? _updateTime;
  String? _newVersion;
  num? _version;
  String? _upVersion;
  String? _intro;
  String? _downUrl;
SystemInfoModel copyWith({  String? updateTime,
  String? newVersion,
  num? version,
  String? upVersion,
  String? intro,
  String? downUrl,
}) => SystemInfoModel(  updateTime: updateTime ?? _updateTime,
  newVersion: newVersion ?? _newVersion,
  version: version ?? _version,
  upVersion: upVersion ?? _upVersion,
  intro: intro ?? _intro,
  downUrl: downUrl ?? _downUrl,
);
  String? get updateTime => _updateTime;
  String? get newVersion => _newVersion;
  num? get version => _version;
  String? get upVersion => _upVersion;
  String? get intro => _intro;
  String? get downUrl => _downUrl;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['updateTime'] = _updateTime;
    map['newVersion'] = _newVersion;
    map['version'] = _version;
    map['upVersion'] = _upVersion;
    map['intro'] = _intro;
    map['downUrl'] = _downUrl;
    return map;
  }

}