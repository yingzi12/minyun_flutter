import 'dart:convert';

import 'JsonSerializable.dart';
/// id : "3"
/// year : 2024
/// month : 3
/// day : 19
/// oldYear : "二〇二四"
/// oldMonth : "二"
/// oldDay : "初十"
/// branchYear : "甲辰"
/// branchMonth : "丁卯"
/// branchDay : "壬午"
/// hundredTaboo : "壬不泱水更难提防 午不苫盖屋主更张"
/// monthFetalGod : null
/// fiveElements : "覆灯火 炉中火 杨柳木"
/// week : "无"
/// starsign : "无"
/// joyGod : "无"
/// godBlessing : "无"
/// godWealth : "无"
/// lucky : "无"
/// worthy : "平"
/// dawge : "无"
/// twelveGods : "无"
/// dayFetalGod : "无"
/// yangguiGod : "无"
/// yinguiGod : "无"
/// ninePalaceFlyingStar : "九星 七赤-咸池星(金)-凶神<br>\n 日五行 杨柳木<br>\n 一龙治水 三人分饼<br>\n 八日得金 十牛耕田<br>"
/// collide : "无"
/// lunarName : "无"
/// phaseMoon : "无"
/// hiroc : "无"
/// phenology : "无"
/// yearFiend : "无"
/// solarTerms24 : "无"
/// branchYearBelong : "无"
/// branchYearIntroduce : "无"
/// branchMonthBelong : "无"
/// branchMonthIntroduce : "无"
/// branchDayBelong : "无"
/// branchDayIntroduce : "无"
/// solarTermsStart : "无"
/// solarTermsEnd : "无"
/// calendar : "2024-03-19"
/// timely : "100"
/// timetaboo : "无"

CalendarModel calendarModelFromJson(String str) => CalendarModel.fromJson(json.decode(str));
String calendarModelToJson(CalendarModel data) => json.encode(data.toJson());
class CalendarModel   implements JsonSerializable{
  CalendarModel({
      String? id, 
      num? year, 
      num? month, 
      num? day, 
      String? oldYear, 
      String? oldMonth, 
      String? oldDay, 
      String? branchYear, 
      String? branchMonth, 
      String? branchDay, 
      String? hundredTaboo, 
      dynamic monthFetalGod, 
      String? fiveElements, 
      String? week, 
      String? starsign, 
      String? joyGod, 
      String? godBlessing, 
      String? godWealth, 
      String? lucky, 
      String? worthy, 
      String? dawge, 
      String? twelveGods, 
      String? dayFetalGod, 
      String? yangguiGod, 
      String? yinguiGod, 
      String? ninePalaceFlyingStar, 
      String? collide, 
      String? lunarName, 
      String? phaseMoon, 
      String? hiroc, 
      String? phenology, 
      String? yearFiend, 
      String? solarTerms24, 
      String? branchYearBelong, 
      String? branchYearIntroduce, 
      String? branchMonthBelong, 
      String? branchMonthIntroduce, 
      String? branchDayBelong, 
      String? branchDayIntroduce, 
      String? solarTermsStart, 
      String? solarTermsEnd, 
      String? calendar, 
      String? timely, 
      String? timetaboo,}){
    _id = id;
    _year = year;
    _month = month;
    _day = day;
    _oldYear = oldYear;
    _oldMonth = oldMonth;
    _oldDay = oldDay;
    _branchYear = branchYear;
    _branchMonth = branchMonth;
    _branchDay = branchDay;
    _hundredTaboo = hundredTaboo;
    _monthFetalGod = monthFetalGod;
    _fiveElements = fiveElements;
    _week = week;
    _starsign = starsign;
    _joyGod = joyGod;
    _godBlessing = godBlessing;
    _godWealth = godWealth;
    _lucky = lucky;
    _worthy = worthy;
    _dawge = dawge;
    _twelveGods = twelveGods;
    _dayFetalGod = dayFetalGod;
    _yangguiGod = yangguiGod;
    _yinguiGod = yinguiGod;
    _ninePalaceFlyingStar = ninePalaceFlyingStar;
    _collide = collide;
    _lunarName = lunarName;
    _phaseMoon = phaseMoon;
    _hiroc = hiroc;
    _phenology = phenology;
    _yearFiend = yearFiend;
    _solarTerms24 = solarTerms24;
    _branchYearBelong = branchYearBelong;
    _branchYearIntroduce = branchYearIntroduce;
    _branchMonthBelong = branchMonthBelong;
    _branchMonthIntroduce = branchMonthIntroduce;
    _branchDayBelong = branchDayBelong;
    _branchDayIntroduce = branchDayIntroduce;
    _solarTermsStart = solarTermsStart;
    _solarTermsEnd = solarTermsEnd;
    _calendar = calendar;
    _timely = timely;
    _timetaboo = timetaboo;
}

  CalendarModel.fromJson(dynamic json) {
    _id = json['id'];
    _year = json['year'];
    _month = json['month'];
    _day = json['day'];
    _oldYear = json['oldYear'];
    _oldMonth = json['oldMonth'];
    _oldDay = json['oldDay'];
    _branchYear = json['branchYear'];
    _branchMonth = json['branchMonth'];
    _branchDay = json['branchDay'];
    _hundredTaboo = json['hundredTaboo'];
    _monthFetalGod = json['monthFetalGod'];
    _fiveElements = json['fiveElements'];
    _week = json['week'];
    _starsign = json['starsign'];
    _joyGod = json['joyGod'];
    _godBlessing = json['godBlessing'];
    _godWealth = json['godWealth'];
    _lucky = json['lucky'];
    _worthy = json['worthy'];
    _dawge = json['dawge'];
    _twelveGods = json['twelveGods'];
    _dayFetalGod = json['dayFetalGod'];
    _yangguiGod = json['yangguiGod'];
    _yinguiGod = json['yinguiGod'];
    _ninePalaceFlyingStar = json['ninePalaceFlyingStar'];
    _collide = json['collide'];
    _lunarName = json['lunarName'];
    _phaseMoon = json['phaseMoon'];
    _hiroc = json['hiroc'];
    _phenology = json['phenology'];
    _yearFiend = json['yearFiend'];
    _solarTerms24 = json['solarTerms24'];
    _branchYearBelong = json['branchYearBelong'];
    _branchYearIntroduce = json['branchYearIntroduce'];
    _branchMonthBelong = json['branchMonthBelong'];
    _branchMonthIntroduce = json['branchMonthIntroduce'];
    _branchDayBelong = json['branchDayBelong'];
    _branchDayIntroduce = json['branchDayIntroduce'];
    _solarTermsStart = json['solarTermsStart'];
    _solarTermsEnd = json['solarTermsEnd'];
    _calendar = json['calendar'];
    _timely = json['timely'];
    _timetaboo = json['timetaboo'];
  }
  String? _id;
  num? _year;
  num? _month;
  num? _day;
  String? _oldYear;
  String? _oldMonth;
  String? _oldDay;
  String? _branchYear;
  String? _branchMonth;
  String? _branchDay;
  String? _hundredTaboo;
  dynamic _monthFetalGod;
  String? _fiveElements;
  String? _week;
  String? _starsign;
  String? _joyGod;
  String? _godBlessing;
  String? _godWealth;
  String? _lucky;
  String? _worthy;
  String? _dawge;
  String? _twelveGods;
  String? _dayFetalGod;
  String? _yangguiGod;
  String? _yinguiGod;
  String? _ninePalaceFlyingStar;
  String? _collide;
  String? _lunarName;
  String? _phaseMoon;
  String? _hiroc;
  String? _phenology;
  String? _yearFiend;
  String? _solarTerms24;
  String? _branchYearBelong;
  String? _branchYearIntroduce;
  String? _branchMonthBelong;
  String? _branchMonthIntroduce;
  String? _branchDayBelong;
  String? _branchDayIntroduce;
  String? _solarTermsStart;
  String? _solarTermsEnd;
  String? _calendar;
  String? _timely;
  String? _timetaboo;
CalendarModel copyWith({  String? id,
  num? year,
  num? month,
  num? day,
  String? oldYear,
  String? oldMonth,
  String? oldDay,
  String? branchYear,
  String? branchMonth,
  String? branchDay,
  String? hundredTaboo,
  dynamic monthFetalGod,
  String? fiveElements,
  String? week,
  String? starsign,
  String? joyGod,
  String? godBlessing,
  String? godWealth,
  String? lucky,
  String? worthy,
  String? dawge,
  String? twelveGods,
  String? dayFetalGod,
  String? yangguiGod,
  String? yinguiGod,
  String? ninePalaceFlyingStar,
  String? collide,
  String? lunarName,
  String? phaseMoon,
  String? hiroc,
  String? phenology,
  String? yearFiend,
  String? solarTerms24,
  String? branchYearBelong,
  String? branchYearIntroduce,
  String? branchMonthBelong,
  String? branchMonthIntroduce,
  String? branchDayBelong,
  String? branchDayIntroduce,
  String? solarTermsStart,
  String? solarTermsEnd,
  String? calendar,
  String? timely,
  String? timetaboo,
}) => CalendarModel(  id: id ?? _id,
  year: year ?? _year,
  month: month ?? _month,
  day: day ?? _day,
  oldYear: oldYear ?? _oldYear,
  oldMonth: oldMonth ?? _oldMonth,
  oldDay: oldDay ?? _oldDay,
  branchYear: branchYear ?? _branchYear,
  branchMonth: branchMonth ?? _branchMonth,
  branchDay: branchDay ?? _branchDay,
  hundredTaboo: hundredTaboo ?? _hundredTaboo,
  monthFetalGod: monthFetalGod ?? _monthFetalGod,
  fiveElements: fiveElements ?? _fiveElements,
  week: week ?? _week,
  starsign: starsign ?? _starsign,
  joyGod: joyGod ?? _joyGod,
  godBlessing: godBlessing ?? _godBlessing,
  godWealth: godWealth ?? _godWealth,
  lucky: lucky ?? _lucky,
  worthy: worthy ?? _worthy,
  dawge: dawge ?? _dawge,
  twelveGods: twelveGods ?? _twelveGods,
  dayFetalGod: dayFetalGod ?? _dayFetalGod,
  yangguiGod: yangguiGod ?? _yangguiGod,
  yinguiGod: yinguiGod ?? _yinguiGod,
  ninePalaceFlyingStar: ninePalaceFlyingStar ?? _ninePalaceFlyingStar,
  collide: collide ?? _collide,
  lunarName: lunarName ?? _lunarName,
  phaseMoon: phaseMoon ?? _phaseMoon,
  hiroc: hiroc ?? _hiroc,
  phenology: phenology ?? _phenology,
  yearFiend: yearFiend ?? _yearFiend,
  solarTerms24: solarTerms24 ?? _solarTerms24,
  branchYearBelong: branchYearBelong ?? _branchYearBelong,
  branchYearIntroduce: branchYearIntroduce ?? _branchYearIntroduce,
  branchMonthBelong: branchMonthBelong ?? _branchMonthBelong,
  branchMonthIntroduce: branchMonthIntroduce ?? _branchMonthIntroduce,
  branchDayBelong: branchDayBelong ?? _branchDayBelong,
  branchDayIntroduce: branchDayIntroduce ?? _branchDayIntroduce,
  solarTermsStart: solarTermsStart ?? _solarTermsStart,
  solarTermsEnd: solarTermsEnd ?? _solarTermsEnd,
  calendar: calendar ?? _calendar,
  timely: timely ?? _timely,
  timetaboo: timetaboo ?? _timetaboo,
);
  String? get id => _id;
  num? get year => _year;
  num? get month => _month;
  num? get day => _day;
  String? get oldYear => _oldYear;
  String? get oldMonth => _oldMonth;
  String? get oldDay => _oldDay;
  String? get branchYear => _branchYear;
  String? get branchMonth => _branchMonth;
  String? get branchDay => _branchDay;
  String? get hundredTaboo => _hundredTaboo;
  dynamic get monthFetalGod => _monthFetalGod;
  String? get fiveElements => _fiveElements;
  String? get week => _week;
  String? get starsign => _starsign;
  String? get joyGod => _joyGod;
  String? get godBlessing => _godBlessing;
  String? get godWealth => _godWealth;
  String? get lucky => _lucky;
  String? get worthy => _worthy;
  String? get dawge => _dawge;
  String? get twelveGods => _twelveGods;
  String? get dayFetalGod => _dayFetalGod;
  String? get yangguiGod => _yangguiGod;
  String? get yinguiGod => _yinguiGod;
  String? get ninePalaceFlyingStar => _ninePalaceFlyingStar;
  String? get collide => _collide;
  String? get lunarName => _lunarName;
  String? get phaseMoon => _phaseMoon;
  String? get hiroc => _hiroc;
  String? get phenology => _phenology;
  String? get yearFiend => _yearFiend;
  String? get solarTerms24 => _solarTerms24;
  String? get branchYearBelong => _branchYearBelong;
  String? get branchYearIntroduce => _branchYearIntroduce;
  String? get branchMonthBelong => _branchMonthBelong;
  String? get branchMonthIntroduce => _branchMonthIntroduce;
  String? get branchDayBelong => _branchDayBelong;
  String? get branchDayIntroduce => _branchDayIntroduce;
  String? get solarTermsStart => _solarTermsStart;
  String? get solarTermsEnd => _solarTermsEnd;
  String? get calendar => _calendar;
  String? get timely => _timely;
  String? get timetaboo => _timetaboo;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['id'] = _id;
    map['year'] = _year;
    map['month'] = _month;
    map['day'] = _day;
    map['oldYear'] = _oldYear;
    map['oldMonth'] = _oldMonth;
    map['oldDay'] = _oldDay;
    map['branchYear'] = _branchYear;
    map['branchMonth'] = _branchMonth;
    map['branchDay'] = _branchDay;
    map['hundredTaboo'] = _hundredTaboo;
    map['monthFetalGod'] = _monthFetalGod;
    map['fiveElements'] = _fiveElements;
    map['week'] = _week;
    map['starsign'] = _starsign;
    map['joyGod'] = _joyGod;
    map['godBlessing'] = _godBlessing;
    map['godWealth'] = _godWealth;
    map['lucky'] = _lucky;
    map['worthy'] = _worthy;
    map['dawge'] = _dawge;
    map['twelveGods'] = _twelveGods;
    map['dayFetalGod'] = _dayFetalGod;
    map['yangguiGod'] = _yangguiGod;
    map['yinguiGod'] = _yinguiGod;
    map['ninePalaceFlyingStar'] = _ninePalaceFlyingStar;
    map['collide'] = _collide;
    map['lunarName'] = _lunarName;
    map['phaseMoon'] = _phaseMoon;
    map['hiroc'] = _hiroc;
    map['phenology'] = _phenology;
    map['yearFiend'] = _yearFiend;
    map['solarTerms24'] = _solarTerms24;
    map['branchYearBelong'] = _branchYearBelong;
    map['branchYearIntroduce'] = _branchYearIntroduce;
    map['branchMonthBelong'] = _branchMonthBelong;
    map['branchMonthIntroduce'] = _branchMonthIntroduce;
    map['branchDayBelong'] = _branchDayBelong;
    map['branchDayIntroduce'] = _branchDayIntroduce;
    map['solarTermsStart'] = _solarTermsStart;
    map['solarTermsEnd'] = _solarTermsEnd;
    map['calendar'] = _calendar;
    map['timely'] = _timely;
    map['timetaboo'] = _timetaboo;
    return map;
  }

}