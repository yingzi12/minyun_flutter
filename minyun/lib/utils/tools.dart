import 'package:intl/intl.dart';
// import 'package:minyun/constant.dart';

/// 天干地支
const ganzhi = [
  '甲', '乙', '丙', '丁', '戊', '己', '庚', '辛', '壬', '癸',
  '子', '丑', '寅', '卯', '辰', '巳', '午', '未', '申', '酉', '戌', '亥',
];

/// 五行
const wuXing = ['木', '火', '土', '金', '水'];
/// 根据百字反推年月日
Map<String, int> lunarFromBaizi(String baizi) {
  // 解析百字
  final yearStem = baizi[0];
  final yearBranch = baizi[1];
  final monthStem = baizi[2];
  final monthBranch = baizi[3];
  final dayStem = baizi[4];
  final dayBranch = baizi[5];

  // 计算年
  final year = ganzhiToYear(yearStem, yearBranch);

  // 计算月
  final month = ganzhiToMonth(monthStem, monthBranch);

  // 计算日
  final day = ganzhiToDay(dayStem, dayBranch);

  // 计算时辰
  final hour = ganzhiToHour(baizi.substring(6));

  return {
    'year': year,
    'month': month,
    'day': day,
    'hour': hour,
  };
}

/// 天干转年
int ganzhiToYear(String stem, String branch) {
  final year = ganzhiToNumber(stem) * 12 + ganzhiToNumber(branch);
  if (year <= 0) {
    throw ArgumentError('Invalid year');
  }
  return year;
}

/// 天干转月
int ganzhiToMonth(String stem, String branch) {
  final month = ganzhiToNumber(stem) * 2 + ganzhiToNumber(branch) - 1;
  if (month <= 0 || month > 12) {
    throw ArgumentError('Invalid month');
  }
  return month;
}

/// 天干转日
int ganzhiToDay(String stem, String branch) {
  final day = ganzhiToNumber(stem) * 2 + ganzhiToNumber(branch) - 1;
  if (day <= 0 || day > 31) {
    throw ArgumentError('Invalid day');
  }
  return day;
}

/// 天干转时辰
int ganzhiToHour(String baizi) {
  final hour = (ganzhiToNumber(baizi[0]) * 2 + ganzhiToNumber(baizi[1]) - 1) * 2;
  return hour;
}

/// 天干转数字
int ganzhiToNumber(String ganzi) {
  final index = ganzhi.indexOf(ganzi);
  if (index < 0) {
    throw ArgumentError('Invalid ganzi');
  }
  return index + 1;
}


/// 获取天干的五行
String getStemWuXing(String stem) {
  final index = ganzhi.indexOf(stem);
  if (index < 0 || index > 10) {
    throw ArgumentError('Invalid stem');
  }
  return wuXing[index ~/ 2];
}

/// 获取地支的五行
String getBranchWuXing(String branch) {
  final index = ganzhi.indexOf(branch);
  if (index < 11 || index > 22) {
    throw ArgumentError('Invalid branch');
  }
  return wuXing[index ~/ 2 - 6];
}

/// 获取六神
Map<String, String> getSixGods(List<String> bazzi) {
  final dayStem = bazzi[0];
  final sixGods = <String, String>{};
  for (var i = 0; i < bazzi.length; i++) {
    final stem = bazzi[i];
    i++;
    final branch = bazzi[i];
    if (stem == dayStem) {
      sixGods['比肩'] = branch;
    } else if (branch == dayStem) {
      sixGods['劫财'] = stem;
    } else if (getStemWuXing(stem) == '食') {
      sixGods['食神'] = branch;
    } else if (getStemWuXing(stem) == '伤') {
      sixGods['伤官'] = branch;
    } else if (getStemWuXing(dayStem) == getStemWuXing(stem)) {
      if (getBranchWuXing(stem) == '财') {
        sixGods['正财'] = branch;
      } else {
        sixGods['偏财'] = branch;
      }
    } else if (getStemWuXing(branch) == getStemWuXing(dayStem)) {
      if (getStemWuXing(stem) == '官') {
        sixGods['正官'] = branch;
      } else {
        sixGods['七杀'] = branch;
      }
    } else if (getStemWuXing(stem) == '印') {
      sixGods['正印'] = branch;
    } else if (stem.toLowerCase() == dayStem.toLowerCase()) {
      sixGods['偏印'] = branch;
    }
  }
  return sixGods;
}

/// 获取十神
Map<String, String> getTenGods(List<String> bazzi) {
  final dayStem = bazzi[0];
  final tenGods = <String, String>{};
  for (var i = 0; i < bazzi.length; i++) {
    final stem = bazzi[i];
    i++;
    final branch = bazzi[i];
    if (stem == dayStem) {
      tenGods['比肩'] = branch;
      tenGods['劫财'] = branch;
    } else if (getStemWuXing(stem) == '食') {
      tenGods['食神'] = branch;
      tenGods['伤官'] = branch;
    } else if (getStemWuXing(dayStem) == getStemWuXing(stem)) {
      if (getBranchWuXing(stem) == '财') {
        tenGods['正财'] = branch;
        tenGods['偏财'] = branch;
      }
    } else if (getStemWuXing(branch) == getStemWuXing(dayStem)) {
      if (getStemWuXing(stem) == '官') {
        tenGods['正官'] = branch;
        tenGods['七杀'] = branch;
      }
    } else if (getStemWuXing(stem) == '印') {
      tenGods['正印'] = branch;
      tenGods['偏印'] = branch;
    }
  }
  return tenGods;
}

/// 根据八字获取年柱、月柱、日柱、时柱
// List<String> getBaZi(String year, String month, String day, String hour) {
//   final yearStem = ganzhi[year.codeUnitAt(0) - '甲'.codeUnitAt(0)];
//   final yearBranch = ganzhi[year.codeUnitAt(1) - '甲'.codeUnitAt(0)];
//   final monthStem = ganzhi[month.codeUnitAt(0) - '甲'.codeUnitAt(0)];
//   final monthBranch = ganzhi[month.codeUnitAt(1) - '甲'.codeUnitAt(0)];
//   final dayStem = ganzhi[day.codeUnitAt(0) - '甲'.codeUnitAt(0)];
//   final dayBranch = ganzhi[day.codeUnitAt(1) - '甲'.codeUnitAt(0)];
//   final hourStem = ganzhi[hour.codeUnitAt(0) - '甲'.codeUnitAt(0)];
//   final hourBranch = ganzhi[hour.codeUnitAt(1) - '甲'.codeUnitAt(0)];
//
//   return [yearStem, yearBranch, monthStem, monthBranch, dayStem, dayBranch, hourStem, hourBranch];
// }
// List<String> getBaZi(String year, String month, String day, String hour) {
//   // Ensure year length is 4 and other strings are length 2
//   if (year.length != 4 || month.length != 2 || day.length != 2 || hour.length != 2) {
//     throw ArgumentError('Invalid input string lengths. Year must be 4 digits, others must be 2 digits.');
//   }
//
//   // Validate year format (optional, based on your requirements)
//   // if (!RegExp(r'^[0-9]{4}$').hasMatch(year)) {
//   //   throw ArgumentError('Invalid year format. Year must be a 4-digit number.');
//   // }
//
//   final yearIndex = (int.parse(year) - 4) % 10; // Adjust for year cycle
//   final monthIndex = int.parse(month) - 1; // Month indexing starts from 0
//   final dayIndex = int.parse(day) - 1;
//   final hourIndex = int.parse(hour) - 1;
//
//   final ganzhi = [
//     '甲', '乙', '丙', '丁', '戊', '己', '庚', '辛', '壬', '癸',
//     '子', '丑', '寅', '卯', '辰', '巳', '午', '未', '申', '酉', '戌', '亥',
//   ];
//
//   final yearStem = ganzhi[yearIndex];
//   final yearBranch = ganzhi[(yearIndex + 12) % 22]; // Year branch follows pattern
//   final monthStem = ganzhi[monthIndex];
//   final monthBranch = ganzhi[(monthIndex + 11) % 22]; // Month branch follows pattern
//   final dayStem = ganzhi[dayIndex];
//   final dayBranch = ganzhi[(dayIndex + 9) % 22];  // Day branch follows specific pattern
//   final hourStem = ganzhi[hourIndex];
//   final hourBranch = ganzhi[(hourIndex + 11) % 22]; // Hour branch follows year branch pattern
//
//   return [yearStem, yearBranch, monthStem, monthBranch, dayStem, dayBranch, hourStem, hourBranch];
// }

/// 格式化八字
// String formatBaZi(List<String> bazzi) {
//   final sb = StringBuffer();
//   for (var i = 0; i < bazzi.length; i++) {
//     sb.write(bazzi[i]);
//     if ((i + 1) % 2 == 0) {
//       sb.write(' ');
//     }
//   }
//   return sb.toString();
// }

void main() {
  final year = '1994';
  final month = '03';
  final day = '23';
  final hour = '11';

  //{
  //   "Tg": "癸",
  //   "Tz": "巳",
  //   "mg": "壬",
  //   "mz": "申",
  //   "tg": "戊",
  //   "tz": "午",
  //   "Sg": "甲",
  //   "Sz": "戌",
  //   "ng": "甲",
  //   "nz": "戌",
  //   "yg": "丁",
  //   "yz": "卯",
  //   "rg": "戊",
  //   "rz": "申",
  //   "sg": "戊",
  //   "sz": "午",
  //   "Kg": "癸",
  //   "Kz": "亥"
  // }
  // 获取八字
  final bazzi =["甲","戌","丁","卯","戊","申","戊","午"];

  // 格式化八字
  // final formattedBaZi = formatBaZi(bazzi);

  // 获取六神
  final sixGods = getSixGods(bazzi);

  // 获取十神
  final tenGods = getTenGods(bazzi);

  // print('八字：$formattedBaZi');
  print('六神：$sixGods');
  print('十神：$tenGods');

  final baizi = '甲子寅卯辰巳午未申酉戌亥';
  final lunar = lunarFromBaizi(baizi);

  final year2 = lunar['year']!;
  final month2 = lunar['month']!;
  final day2 = lunar['day']!;
  final hour2 = lunar['hour']!;

  final dateFormat = DateFormat('yyyy-MM-dd HH:mm');
  final lunarDate = dateFormat.format(DateTime(year2, month2, day2, hour2));

  print(lunarDate); // 输出：0001-01-01 11:00
}