import 'dart:collection';
import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:lunar/lunar.dart';

import 'package:minyun/models/SplayedFigureFindModel.dart';
import 'package:minyun/models/SplayedFigureModel.dart';


class SplayedFigureDetailIntroScreen extends StatefulWidget {
  final SplayedFigureFindModel search;
  final Lunar lunar;
  final SplayedFigureModel splayedFigureModel;
  final EightChar eightChar;
  SplayedFigureDetailIntroScreen({required this.search,required this.lunar, required this.splayedFigureModel, required this.eightChar});

  @override
  State<SplayedFigureDetailIntroScreen> createState() => _SplayedFigureDetailIntroScreenState();
}

class _SplayedFigureDetailIntroScreenState extends State<SplayedFigureDetailIntroScreen>  with TickerProviderStateMixin {
  System? system;
  String? mg;
  Chusheng? chusheng;
  String? ljkw;
  String? qr;


  @override
  void initState() {
    super.initState();
    _refreshApiData();
    _refreshSdkData();
  }

  @override
  void dispose() {
    super.dispose();
  }
  Future<void> _refreshSdkData() async {
  }
  Future<void> _refreshApiData() async {
    setState(() {
      system=widget.splayedFigureModel.system;
      mg=widget.splayedFigureModel.jsfw!.zg;
      chusheng = widget.splayedFigureModel.chusheng!;
      ljkw= widget.splayedFigureModel.bazixinxi!.zh![6].toString();
      qr=widget.splayedFigureModel.deling![5].toString();
    });

  }

  //获取指定月的天数
  int getDaysInMonth(int year, int month) {
    var a = Solar.fromYmd(year, month, 1);
    var b = a.nextMonth(1);
    // 返回差异的天数
    return b.subtract(a);
  }
  @override
  Widget build(BuildContext context) {
    return  ListView(
      children: [
        getText("姓名",(system!.name??"")+"("+(system!.nylm??"")+")【"+(mg??"")+"】"),
        Row(
          children: [
            Expanded(child: getText("性别",system!.sexx??""),),
            Expanded(child: getText("生肖",system!.shengxiao??""),),
          ],
        ),
        getText("农历",system!.sexx??""),
        getText("阴历",widget.lunar.toString()),
        getText("出生地区",system!.city??""),
        getText("节气",(chusheng!.cl1 ??"")+" "+(chusheng!.dl1 ??"")+" "+(chusheng!.cl2 ??"")+" "+(chusheng!.dl2 ??"") ),
        getText("物候",(system!.wuHou??"")+" "+(system!.hou??"")),
        getText("人元司令分野",(system!.siLing??"")+"用事 ("+(system!.siLingFangshi??"")+")"),
        getText("三才五格",system!.scwg??""),
        getText("六甲空亡",ljkw ?? ""),
        getText("强弱",qr ?? ""),
        getText("起运","命主于公历"+(system!.jyrq??"")+"交运"),
        getText("交运","以后每逢尾数带"+(chusheng!.hyws??"")+"的年份换运"),
        getText("空亡",widget.eightChar.getYearXunKong()+" "+widget.eightChar.getMonthXunKong()+" "+widget.eightChar.getDayXunKong()+" "+widget.eightChar.getTimeXunKong()),
        Row(
          children: [
            Expanded(child: getText("星座",system!.xingZuo??""),),
            Expanded(child: getText("星宿",(system!.getXiu ?? "")+""+(system!.getAnimal ?? "")+""+(system!.getXiuLuck ?? "")),)

          ],
        ),
        Row(
          children: [
            Expanded(child: getText("胎元",widget.eightChar.getTaiYuan()),),
            Expanded(child: getText("身宫",widget.eightChar.getShenGong()),),
          ],
        ),
        Row(
          children: [
            Expanded(child: getText("命宫",widget.eightChar.getMingGong())),
            Expanded(child: getText("胎息",widget.eightChar.getTaiXi()),)
          ],
        ),

      ],
    );
  }

  Widget getText(String title,String value){
    return Text.rich(
      TextSpan(
        text: title,
        style: TextStyle(color: Colors.black, fontWeight: FontWeight.bold),
        children: <TextSpan>[
          TextSpan(text:":" , style: TextStyle(color: Colors.black, fontWeight: FontWeight.bold)),
          TextSpan(text: value, style: TextStyle(fontWeight: FontWeight.bold,color: Colors.black45,)),
        ],
      ),
    );

  }

}
