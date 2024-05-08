import 'package:minyun/screens/about_screen.dart';
import 'package:minyun/screens/help_center_screen.dart';
import 'package:minyun/screens/language_screen.dart';
import 'package:minyun/screens/prefrances_screen.dart';
import 'package:minyun/screens/security_screen.dart';
import 'package:minyun/screens/user/personal_info_screen.dart';
import 'package:minyun/screens/user/user_splayed_figure_list_screen.dart';

class AccountScreenOptions {
  String? image;
  String? title;
  String? trailingText;

  AccountScreenOptions(this.image, this.title, this.trailingText);
}
List<AccountScreenOptions> accountOptions = [
  AccountScreenOptions("assets/icons/bottom_navigation_icons/user_outlined.png", "个人资料", ""),
  AccountScreenOptions("assets/icons/bagua.png", "我的命盘与解析", ""),
  // AccountScreenOptions("assets/icons/bottom_navigation_icons/user_outlined.png", "我的订单", ""),
  // AccountScreenOptions("assets/icons/bottom_navigation_icons/user_outlined.png", "查询档案", ""),
  // AccountScreenOptions("assets/icons/bottom_navigation_icons/user_outlined.png", "分享", ""),
  AccountScreenOptions("assets/icons/message.png", "通知", ""),

  // AccountScreenOptions("assets/images/setting.png", "Preferences", ""),
  // AccountScreenOptions("assets/images/security.png", "Security", ""),
  // AccountScreenOptions("assets/images/document.png", "Language", "English (US)"),
  AccountScreenOptions("assets/images/view.png", "主题", ""),
];
List accountScreenHelpRoutList = [HelpCenterScreen(), AboutScreen()];
List<AccountScreenOptions> aboutAppOptions = [
  AccountScreenOptions("assets/images/document.png", "帮助", ""),
  AccountScreenOptions("assets/icons/info.png", "关于命运", ""),
  AccountScreenOptions("assets/icons/exit.png", "退出", ""),
];
