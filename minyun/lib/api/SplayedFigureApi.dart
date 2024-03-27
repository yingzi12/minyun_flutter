import 'package:minyun/models/SplayedFigureModel.dart';
import 'package:minyun/utils/HttpUtil.dart';

class SplayedFigureApi{

  static String splayedFigure ="https://zydx.win/@2.0/api.php";


  static Future<SplayedFigureModel> getSplayedFigure(Map<String, String> queryParams) async {
      final jsonMap = await HttpUtil.getSource(splayedFigure,queryParams);
      SplayedFigureModel values = SplayedFigureModel.fromJson(jsonMap);
      return values;
  }
}