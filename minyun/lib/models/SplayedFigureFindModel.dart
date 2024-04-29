class SplayedFigureFindModel{
  String? act;
  String? name;

  /**
   *   4/5 4为八字反推，5为日期排盘
   */
  int? dateType;
  /**
   *   DateType为5时必填，日期排盘日期，具体传入规则见附录
   */
  String? inputdate;
  //阳历
  int? year;
  int? month;
  int? day;
  int? hour;
  int? minute;

  /**
   *DateType为4时必填，八字反推年柱
   */
  String? ng;
  /**
   *DateType为4时必填，八字反推月柱
   */
  String? yg;
  /**
   *DateType为4时必填，八字反推日柱
   */
  String? rg;
  /**
   *DateType为4时必填，八字反推时柱
   */
  String? sg;
  /**
   * 0/1 0为男，1为女
   */
  int? sex;

  /**
   * 排盘方式 2是专 1是普通
   */
  int? paipanFs;

  /**
   *1 为启用真太阳时
   */
  int? ztys;
  /**
   * ztys为1时必填，真太阳时省地区，具体传入规则见附录
   */
  String? city1;
  /**
   *
   */
  String? city2;
  /**
   *
   */
  String? city3;
  /**
   * 1/2 晚子时规则，1为按明天，2为按当天，不传入默认为2
   */
  int? sect;
  /**
   * 0/1/2/3/4/5 人元司令分野，0为子平真诠，1为三命通会，2为渊海子平，3为神峰通考，4为星平会海，5为万育吾之法诀，不传入默认为0
   */
  int? siling;
  /**
   *
   */
  String? leixinggg="on";
  /**
   *
   */
  String? api="1";
  /**
   *
   */
  String? bcxx="1";

  //1保存，2不保存
  int isSave=1;

  //1是自己的，user_eight_char uecid
  //2是别人的  analyze_eight_char aecid
  int? commonType=1;

  //1保存，2不保存
  int? uecid;

  int? aecid;


}