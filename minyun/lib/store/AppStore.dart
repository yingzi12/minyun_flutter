import 'package:flutter/material.dart';
import 'package:minyun/utils/AppColors.dart';
import 'package:minyun/utils/AppContents.dart';

import 'package:mobx/mobx.dart';
import 'package:nb_utils/nb_utils.dart';


part 'AppStore.g.dart';

class AppStore = AppStoreBase with _$AppStore;

abstract class AppStoreBase with Store {
  @observable
  bool isDarkModeOn = false;

  @observable
  bool isHover = false;

  @observable
  Color? iconColor;

  @observable
  Color? backgroundColor;

  @observable
  Color? appBarColor;

  @observable
  Color? scaffoldBackground;

  @observable
  Color? backgroundSecondaryColor;

  @observable
  Color? appColorPrimaryLightColor;

  @observable
  Color? iconSecondaryColor;

  @observable
  Color? textPrimaryColor;

  @observable
  Color? textSecondaryColor;

  @observable
  String selectedLanguageCode = defaultLanguage;

  @action
  Future<void> toggleDarkMode({bool? value}) async {
    isDarkModeOn = value ?? !isDarkModeOn;

    if (isDarkModeOn) {
      scaffoldBackground = appBackgroundColorDark;

      appBarColor = cardBackgroundBlackDark;
      backgroundColor = Colors.white;
      backgroundSecondaryColor = Colors.white;
      appColorPrimaryLightColor = cardBackgroundBlackDark;

      iconColor = iconColorPrimary;
      iconSecondaryColor = iconColorSecondary;

      textPrimaryColor = whiteColor;
      textSecondaryColor = Colors.white54;

      textPrimaryColorGlobal = whiteColor;
      textSecondaryColorGlobal = Colors.white54;
      shadowColorGlobal = appShadowColorDark;
    } else {
      scaffoldBackground = scaffoldLightColor;

      appBarColor = Colors.white;
      backgroundColor = Colors.black;
      backgroundSecondaryColor = appSecondaryBackgroundColor;
      appColorPrimaryLightColor = appColorPrimaryLight;

      iconColor = iconColorPrimaryDark;
      iconSecondaryColor = iconColorSecondaryDark;

      textPrimaryColor = appTextColorPrimary;
      textSecondaryColor = appTextColorSecondary;

      textPrimaryColorGlobal = appTextColorPrimary;
      textSecondaryColorGlobal = appTextColorSecondary;
      shadowColorGlobal = appShadowColor;
    }
    setStatusBarColor(scaffoldBackground!);

    setValue('isDarkModeOnPref', isDarkModeOn);
  }

  // @action
  // Future<void> setLanguage(String val, {BuildContext? context}) async {
  //   selectedLanguageCode = val;
  //
  //   await setValue(SELECTED_LANGUAGE_CODE, selectedLanguageCode);
  //   selectedLanguageDataModel = getSelectedLanguageModel();
  //
  //   if (context != null) language = BaseLanguage.of(context);
  //   language = await AppLocalizations().load(Locale(selectedLanguageCode));
  // }

  @action
  Future<void> setLanguage(String val, {BuildContext? context}) async {
    selectedLanguageCode = val;
    await setValue(SELECTED_LANGUAGE_CODE, selectedLanguageCode);
    // 如果需要，触发 UI 重建以反映新的语言设置
    // 在 StatefulWidget 中，可以调用 setState(() {});
    // 在使用响应式状态管理时，确保状态更改会触发 UI 重建
  }

  @action
  void toggleHover({bool value = false}) => isHover = value;
}
