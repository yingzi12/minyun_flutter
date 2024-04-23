class AccountScreenOptions {
  String? image;
  String? title;
  String? trailingText;

  AccountScreenOptions(this.image, this.title, this.trailingText);
}

List<AccountScreenOptions> accountOptions = [
  AccountScreenOptions("assets/icons/bottom_navigation_icons/user_outlined.png", "个人信息", ""),
  AccountScreenOptions("assets/icons/bottom_navigation_icons/user_outlined.png", "我的订单", ""),
  AccountScreenOptions("assets/icons/bottom_navigation_icons/user_outlined.png", "档案", ""),

  AccountScreenOptions("assets/icons/bottom_navigation_icons/user_outlined.png", "我的信息", ""),
  AccountScreenOptions("assets/icons/bottom_navigation_icons/user_outlined.png", "分享", ""),
  AccountScreenOptions("assets/icons/bottom_navigation_icons/user_outlined.png", "通知", ""),

  // AccountScreenOptions("assets/images/setting.png", "Preferences", ""),
  // AccountScreenOptions("assets/images/security.png", "Security", ""),
  // AccountScreenOptions("assets/images/document.png", "Language", "English (US)"),
  AccountScreenOptions("assets/images/view.png", "主题", ""),
];
List<AccountScreenOptions> aboutAppOptions = [
  AccountScreenOptions("assets/images/document.png", "帮助", ""),
  AccountScreenOptions("assets/icons/info.png", "关于命运", ""),
  AccountScreenOptions("assets/icons/exit.png", "退出", ""),
];
