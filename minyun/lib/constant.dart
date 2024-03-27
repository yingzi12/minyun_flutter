
import 'package:flutter/material.dart';

const baseUrl="https://admin.aiavr.uk";
// const baseUrl="https://test.aiavr.uk";
// const baseUrl="http://192.168.68.100:8098";
const SOURCEWEB="https://image.51x.uk/xinshijie";
const paymentUrl="https://www.aiavr.uk/paypalModile";
// const paymentUrl="http://192.168.68.106:3000/paypalModile";

const Map<String, String> hostnameValues = {
  'video.': 'video',
  'hotfirl': 'hotfirl',
  'https://images.hotgirl.asia': 'https://imageshotgirl.yappgcu.uk',
  'https://mogura.my.id': 'https://mogura.yappgcu.uk',
  'jkforum': 'jkforum.com',
  'x60': 'x60.com'
};

Map<String, String> AlbumChargeMap = {
  '免费': '1',
  'VIP免费': '2',
  'VIP折扣': '3',
  'VIP独享': '4',
  '统一': '5',
};

Map<int, String> AlbumChargeTypeMap = {
  1: '免费',
  2: 'VIP免费',
  3: 'VIP折扣',
  4: 'VIP独享',
  5: '统一',
};

Map<String, String> VipTimeTypeMap = {
  '天': '1',
  '周': '2',
  '月': '3',
  '年': '4',
  // '永久': '5',
};
Map<int, String> VipTimeMap = {
  1: '天',
  2: '周',
  3: '月',
  4: '年',
  // 5: '永久',
};

Map<int, String> SystemVipMap = {
  1: '月度会员',
  2: '季度会员',
  3: '半年会员',
  4: '年度会员',
  5: '永久',
};
//戊己 棕色土色
//庚辛 金色
//辰戌丑未 棕色属土
//壬癸 蓝色。亥子 蓝色    寅卯 绿色  巳午 红色  申酉   金色 辰戌丑未 棕色属土
const Map<String, Color> hourColorMap = {
  "甲": Colors.green,
  "乙": Colors.green,
  "丙": Colors.red,
  "丁": Colors.red,
  "戊": Colors.brown,
  "己": Colors.brown,
  "庚": Color(0xFFFFC107),
  "辛": Color(0xFFFFC107),
  "辰": Colors.brown,
  "戌": Colors.brown,
  "丑": Colors.brown,
  "未": Colors.brown,
  "壬": Colors.blue,
  "癸": Colors.blue,
  "亥": Colors.blue,
  "子": Colors.blue,
  "寅": Colors.green,
  "卯": Colors.green,
  "巳": Colors.red,
  "午": Colors.red,
  "申": Color(0xFFFFC107),
  "酉": Color(0xFFFFC107),
};
const Map<String,List<String>> dizhiMap = {
  "子":["癸"],
  "丑":["己","癸","辛"],
  "寅":["甲","丙","戊"],
  "卯":["乙"],
  "辰":["戊","乙","癸"],
  "巳":["丙","庚","戊"],
  "午":["丁","己"],
  "未":["己","丁","乙"],
  "申":["庚","壬","戊"],
  "酉":["辛"],
  "戌":["戊","辛","丁"],
  "亥":["壬","甲"],
};

/// 天干地支
const ganzhi = [
  '甲', '乙', '丙', '丁', '戊', '己', '庚', '辛', '壬', '癸',
  '子', '丑', '寅', '卯', '辰', '巳', '午', '未', '申', '酉', '戌', '亥',
];
/// 五行
const wuXing = ['木', '火', '土', '金', '水'];
//天干十神对应
const Map<String, String> _ganzhiMap = {
  '甲甲': '比肩',
  '甲乙': '劫财',
  '甲丙': '食神',
  '甲丁': '伤官',
  '甲戊': '偏财',
  '甲己': '正财',
  '甲庚': '七杀',
  '甲辛': '正官',
  '甲壬': '偏印',
  '甲癸': '正印',

  '乙甲': '劫财',
  '乙乙': '比肩',
  '乙丙': '伤官',
  '乙丁': '食神',
  '乙戊': '正财',
  '乙己': '偏财',
  '乙庚': '正官',
  '乙辛': '七杀',
  '乙壬': '正印',
  '乙癸': '偏印',

  '丙丙': '比肩',
  '丙丁': '劫财',
  '丙戊': '食神',
  '丙己': '伤官',
  '丙庚': '偏财',
  '丙辛': '正财',
  '丙壬': '七杀',
  '丙癸': '正官',
  '丙甲': '偏印',
  '丙乙': '正印',

  '丁丁': '比肩',
  '丁丙': '劫财',
  '丁己': '食神',
  '丁戊': '伤官',
  '丁辛': '偏财',
  '丁庚': '正财',
  '丁癸': '七杀',
  '丁壬': '正官',
  '丁乙': '偏印',
  '丁甲': '正印',

  '戊戊': '比肩',
  '戊己': '劫财',
  '戊庚': '食神',
  '戊辛': '伤官',
  '戊壬': '偏财',
  '戊癸': '正财',
  '戊甲': '七杀',
  '戊乙': '正官',
  '戊丙': '偏印',
  '戊丁': '正印',

  '己己': '比肩',
  '己戊': '劫财',
  '己辛': '食神',
  '己庚': '伤官',
  '己癸': '偏财',
  '己壬': '正财',
  '己乙': '七杀',
  '己甲': '正官',
  '己丁': '偏印',
  '己丙': '正印',

  '庚庚': '比肩',
  '庚辛': '劫财',
  '庚壬': '食神',
  '庚癸': '伤官',
  '庚甲': '偏财',
  '庚乙': '正财',
  '庚丁': '七杀',
  '庚丙': '正官',
  '庚戊': '偏印',
  '庚己': '正印',

  '辛辛': '比肩',
  '辛庚': '劫财',
  '辛癸': '食神',
  '辛壬': '伤官',
  '辛乙': '偏财',
  '辛甲': '正财',
  '辛丁': '七杀',
  '辛丙': '正官',
  '辛己': '偏印',
  '辛戊': '正印',

  '壬壬': '比肩',
  '壬癸': '劫财',
  '壬甲': '食神',
  '壬乙': '伤官',
  '壬丙': '偏财',
  '壬丁': '正财',
  '壬戊': '七杀',
  '壬己': '正官',
  '壬庚': '偏印',
  '壬辛': '正印',

  '癸癸': '比肩',
  '癸壬': '劫财',
  '癸乙': '食神',
  '癸甲': '伤官',
  '癸丁': '偏财',
  '癸丙': '正财',
  '癸己': '七杀',
  '癸戊': '正官',
  '癸辛': '偏印',
  '癸庚': '正印',
};
