// 天干
List<String> tiangan = ["甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"];
// 地支
List<String> dizhi = ["子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"];
// 时辰表
List<String> hourTable = [
  "子", "丑", "丑", "寅", "寅", "卯", "卯", "辰", "辰", "巳", "巳", "午",
  "午", "未", "未", "申", "申", "酉", "酉", "戌", "戌", "亥", "亥", "子"
];

// 八字的五行属性
Map<String, String> wuxingMap = {
  "甲": "木", "乙": "木", "丙": "火", "丁": "火", "戊": "土",
  "己": "土", "庚": "金", "辛": "金", "壬": "水", "癸": "水",
  "子": "水", "丑": "土", "寅": "木", "卯": "木", "辰": "土",
  "巳": "火", "午": "火", "未": "土", "申": "金", "酉": "金",
  "戌": "土", "亥": "水"
};

// 根据八字反推出可能的出生时辰
List<String> reverseCalculateHour(String bazi) {
  List<String> possibleHours = [];

  // 解析天干和地支
  String yearTiangan = bazi[0];
  String yearDizhi = bazi[1];
  String monthTiangan = bazi[2];
  String monthDizhi = bazi[3];
  String dayTiangan = bazi[4];
  String dayDizhi = bazi[5];

  // 计算年、月、日的天干和地支索引
  int yearTianganIndex = tiangan.indexOf(yearTiangan);
  int yearDizhiIndex = dizhi.indexOf(yearDizhi);
  int monthTianganIndex = tiangan.indexOf(monthTiangan);
  int monthDizhiIndex = dizhi.indexOf(monthDizhi);
  int dayTianganIndex = tiangan.indexOf(dayTiangan);
  int dayDizhiIndex = dizhi.indexOf(dayDizhi);

  // 计算时辰的天干地支索引
  for (int i = 0; i < 12; i++) {
    String hourDizhi = hourTable[i];
    int hourDizhiIndex = dizhi.indexOf(hourDizhi);

    // 计算时辰的天干索引
    int yearIndexDiff = (hourDizhiIndex - yearDizhiIndex + 12) % 12;
    int monthIndexDiff = (hourDizhiIndex - monthDizhiIndex + 12) % 12;
    int dayIndexDiff = (hourDizhiIndex - dayDizhiIndex + 12) % 12;

    int hourTianganIndex = (yearTianganIndex + yearIndexDiff) % 10;
    int monthWuxingIndex = (monthTianganIndex + monthIndexDiff) % 10;
    int dayWuxingIndex = (dayTianganIndex + dayIndexDiff) % 10;

    // 获取年、月、日、时的五行属性
    String yearWuxing = wuxingMap[tiangan[hourTianganIndex]]!;
    String monthWuxing = wuxingMap[tiangan[monthWuxingIndex]]!;
    String dayWuxing = wuxingMap[tiangan[dayWuxingIndex]]!;
    String hourWuxing = wuxingMap[tiangan[i]]!;

    // 判断五行属性是否符合规则
    if (hourWuxing == yearWuxing || hourWuxing == monthWuxing || hourWuxing == dayWuxing) {
      // 如果符合规则，则将该时辰加入可能的时辰列表
      possibleHours.add(tiangan[i] + hourDizhi);
    }
  }

  return possibleHours;
}

void main() {
  String bazi = "甲子乙丑丙寅丁卯戊辰己巳庚午辛未壬申癸酉甲戌";

  List<String> possibleHours = reverseCalculateHour(bazi);
  if (possibleHours.isNotEmpty) {
    print("可能的出生时辰为：${possibleHours.join(", ")}时");
  } else {
    print("未找到可能的出生时辰。");
  }
}
