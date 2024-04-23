import 'package:flutter/material.dart';
import 'package:minyun/screens/account_screen.dart';
import 'package:minyun/screens/dashboard_screen.dart';
import 'package:minyun/screens/premium_screen.dart';
import 'package:minyun/screens/splayed_figure_screen.dart';
import 'package:minyun/utils/AppColors.dart';

import '../utils/images.dart';
import '../utils/lists.dart';

class BottomNavigationBarScreen extends StatefulWidget {
  BottomNavigationBarScreen({Key? key, required this.itemIndex}) : super(key: key);
  final int itemIndex;

  @override
  State<BottomNavigationBarScreen> createState() => _BottomNavigationBarScreenState();
}

class _BottomNavigationBarScreenState extends State<BottomNavigationBarScreen> {
  List navigationPages = [
    DashBoardScreen(),
    SplayedFigureScreen(),
    PremiumScreen(),
    AccountScreen(),
  ];

  int selectedIndex = 0;
  void onTimeTapped(int index) {
    setState(() {
      selectedIndex = index;
    });
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    selectedIndex = widget.itemIndex;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: navigationPages.elementAt(selectedIndex),
      ),
      bottomNavigationBar: BottomNavigationBar(
        type: BottomNavigationBarType.fixed,
        selectedItemColor: primaryColor,
        currentIndex: selectedIndex,
        iconSize: 26,
        showUnselectedLabels: true,
        showSelectedLabels: true,
        unselectedItemColor: Colors.grey,
        elevation: 0,
        onTap: onTimeTapped,
        items: [
          BottomNavigationBarItem(
            icon: Image.asset(wanlli_outlined, height: 24),
            activeIcon: Image.asset(wanlli_filled, height: 28),
            label: "日历",
          ),
          BottomNavigationBarItem(
            icon: Image.asset(paipan_outlined, height: 24),
            activeIcon: Image.asset(paipan_filled,height: 28),
            label: "排盘",
          ),
          BottomNavigationBarItem(
            icon: Image.asset(star_outlined, height: 24, color: Colors.grey),
            activeIcon: Image.asset(star_filled, color: primaryColor, height: 28),
            label: "档案",
          ),
          // BottomNavigationBarItem(
          //   icon: Image.asset(star_outlined, height: 24, color: Colors.grey),
          //   activeIcon: Image.asset(star_filled, color: primaryColor, height: 28),
          //   label: "会员",
          // ),
          BottomNavigationBarItem(
            icon: Image.asset(user_outlined, height: 24, color: Colors.grey),
            activeIcon: Image.asset(user_filled, color: primaryColor, height: 28),
            label: "用户",
          ),
        ],
      ),
    );
  }
}
