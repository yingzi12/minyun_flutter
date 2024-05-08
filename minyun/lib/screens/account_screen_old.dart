// import 'dart:io';
//
// import 'package:flutter/material.dart';
// import 'package:flutter_mobx/flutter_mobx.dart';
// import 'package:minyun/screens/bottom_navigation_bar_screen.dart';
// import 'package:minyun/screens/sign_up_screen.dart';
// import 'package:minyun/theme_mode.dart';
// import 'package:minyun/utils/AppContents.dart';
//
// import '../component/logout_component.dart';
// import '../models/account_screen_model.dart';
// import 'package:minyun/utils/AppColors.dart';
// import '../utils/AppCommon.dart';
// import '../utils/images.dart';
// import '../utils/lists.dart';
//
// class AccountScreenOld extends StatefulWidget {
//   const AccountScreenOld({Key? key}) : super(key: key);
//
//   @override
//   State<AccountScreenOld> createState() => _AccountScreenOldState();
// }
//
// theme mode = theme();
//
// class _AccountScreenOldState extends State<AccountScreenOld> {
//   @override
//   Widget build(BuildContext context) {
//     final height = MediaQuery.of(context).size.height;
//     final width = MediaQuery.of(context).size.width;
//     return Observer(
//       builder: (context) {
//         return Scaffold(
//           appBar: AppBar(
//             title: Text("Account"),
//             titleTextStyle: appMainBoldTextStyle(fontSize: 24),
//             elevation: 0,
//             titleSpacing: 0,
//             leading: Padding(
//               padding: EdgeInsets.all(12.0),
//               child: Image.asset(splash_screen_image, color: primaryColor),
//             ),
//             actions: [
//               IconButton(
//                 onPressed: () {},
//                 splashRadius: 22,
//                 icon: Image.asset(more_image, height: 26, width: 26, color: mode.theme ? Colors.white : Colors.black),
//               ),
//             ],
//           ),
//           body: SingleChildScrollView(
//             padding: EdgeInsets.symmetric(vertical: 16),
//             child: Column(
//               children: [
//                 Container(
//                   padding: EdgeInsets.all(16),
//                   margin: EdgeInsets.symmetric(horizontal: 16),
//                   decoration: BoxDecoration(
//                     color: mode.theme ? darkPrimaryLightColor : Colors.grey.shade200,
//                     borderRadius: BorderRadius.circular(DEFAULT_RADIUS),
//                     border: Border.all(color: mode.theme ? Colors.white24 : Colors.grey.shade300),
//                   ),
//                   child: Row(
//                     children: [
//                       Container(
//                         width: 80,
//                         height: 80,
//                         padding: EdgeInsets.all(16),
//                         decoration: BoxDecoration(
//                           shape: BoxShape.circle,
//                           color: Colors.white,
//                           image: DecorationImage(
//                             image: image == null
//                                 ? Image.asset(sign_up_screen_person_image, color: Colors.grey, fit: BoxFit.contain).image
//                                 : Image.file(File(image!.path), fit: BoxFit.cover).image,
//                             fit: BoxFit.cover,
//                           ),
//                         ),
//                       ),
//                       SizedBox(width: 16),
//                       Column(
//                         crossAxisAlignment: CrossAxisAlignment.start,
//                         children: [
//                           Row(
//                             children: [
//                               nameCont.text.length == 0
//                                   ? Text(
//                                       "User Name",
//                                       style: appMainBoldTextStyle(),
//                                     )
//                                   : Text(
//                                       nameCont.text,
//                                       style: appMainBoldTextStyle(),
//                                     ),
//                               SizedBox(width: 16),
//                               Container(
//                                 padding: EdgeInsets.symmetric(horizontal: 8, vertical: 6),
//                                 decoration: BoxDecoration(
//                                   color: Colors.transparent,
//                                   borderRadius: BorderRadius.circular(10),
//                                   border: Border.all(color: primaryColor),
//                                 ),
//                                 child: Text(
//                                   "Basic",
//                                   style: appMainSecondaryTextStyle(color: primaryColor, fontSize: 10),
//                                   textAlign: TextAlign.center,
//                                 ),
//                               ),
//                             ],
//                           ),
//                           SizedBox(height: 8),
//                           Text("0 MB / 1024 MB", style: appMainSecondaryTextStyle()),
//                           SizedBox(height: 8),
//                           Container(
//                             height: 8,
//                             width: width * 0.5,
//                             decoration: BoxDecoration(color: Colors.grey.shade300, borderRadius: BorderRadius.circular(DEFAULT_RADIUS)),
//                             child: Row(
//                               children: [
//                                 Container(
//                                   width: width * 0.03,
//                                   height: 8,
//                                   decoration: BoxDecoration(color: primaryColor, borderRadius: BorderRadius.circular(DEFAULT_RADIUS)),
//                                 ),
//                               ],
//                             ),
//                           ),
//                         ],
//                       ),
//                     ],
//                   ),
//                 ),
//                 // SizedBox(height: 16),
//                 // Container(
//                 //   padding: EdgeInsets.all(16),
//                 //   margin: EdgeInsets.symmetric(horizontal: 16),
//                 //   decoration: BoxDecoration(
//                 //     borderRadius: BorderRadius.circular(DEFAULT_RADIUS),
//                 //     gradient: LinearGradient(colors: [Color(0xFF667fff), Color(0xFF4c69ff)], stops: [0.1, 0.9]),
//                 //   ),
//                 //   child: Row(
//                 //     children: [
//                 //       Container(
//                 //         padding: EdgeInsets.all(16),
//                 //         decoration: BoxDecoration(color: Color(0xFFfb9f19), shape: BoxShape.circle),
//                 //         child: Image.asset(
//                 //           star_image,
//                 //           height: height * 0.055,
//                 //           color: Colors.white,
//                 //         ),
//                 //       ),
//                 //       SizedBox(width: 16),
//                 //       Column(
//                 //         crossAxisAlignment: CrossAxisAlignment.start,
//                 //         children: [
//                 //           Text("Go to Premium!", style: appBoldTextStyle(fontSize: 18, color: Colors.white)),
//                 //           SizedBox(height: 8),
//                 //           Text("Enjoy all the benefits", style: appSecondaryTextStyle(color: Colors.white)),
//                 //         ],
//                 //       ),
//                 //       Spacer(),
//                 //       GestureDetector(
//                 //         onTap: () {
//                 //           Navigator.push(
//                 //               context,
//                 //               MaterialPageRoute(
//                 //                   builder: (context) => BottomNavigationBarScreen(
//                 //                         itemIndex: 2,
//                 //                       )));
//                 //         },
//                 //         child: Container(
//                 //           padding: EdgeInsets.symmetric(horizontal: 10, vertical: 6),
//                 //           decoration: BoxDecoration(
//                 //             color: Colors.white,
//                 //             borderRadius: BorderRadius.circular(DEFAULT_RADIUS),
//                 //           ),
//                 //           child: Text(
//                 //             "Upgrade",
//                 //             style: appPrimaryTextStyle(color: primaryColor, fontSize: 16),
//                 //             textAlign: TextAlign.center,
//                 //           ),
//                 //         ),
//                 //       ),
//                 //     ],
//                 //   ),
//                 // ),
//                 SizedBox(height: 16),
//                 ListView.builder(
//                   itemCount: accountOptions.length,
//                   shrinkWrap: true,
//                   primary: false,
//                   itemBuilder: (context, int index) {
//                     return InkWell(
//                       onTap: () {
//                         if (index == accountOptions.length - 1) {
//                           return null;
//                         } else {
//                           Navigator.push(context, MaterialPageRoute(builder: (context) => accountScreenInfoRouteList[index]));
//                         }
//                       },
//                       child: Padding(
//                         padding: EdgeInsets.only(left: 16, right: 8),
//                         child: Row(
//                           children: [
//                             Image.asset(accountOptions[index].image ?? "", height: 20, width: 20, color: mode.theme ? Colors.white : Colors.black),
//                             SizedBox(width: 16),
//                             Text(accountOptions[index].title ?? "", style: appMainPrimaryTextStyle()),
//                             Spacer(),
//                             Row(
//                               children: [
//                                 Text(
//                                   accountOptions[index].trailingText ?? "",
//                                   style: appMainPrimaryTextStyle(),
//                                 ),
//                                 index == accountOptions.length - 1
//                                     ? Observer(
//                                         builder: (context) {
//                                           return Switch(
//                                             value: mode.theme,
//                                             activeTrackColor: primaryColor,
//                                             activeColor: Colors.white,
//                                             onChanged: (value) {
//                                               mode.isDarkMode(value);
//                                             },
//                                           );
//                                         },
//                                       )
//                                     : IconButton(
//                                         onPressed: () {
//                                           if (index == 4) {
//                                             return null;
//                                           } else {
//                                             Navigator.push(context, MaterialPageRoute(builder: (context) => accountScreenInfoRouteList[index]));
//                                           }
//                                         },
//                                         splashRadius: 28,
//                                         icon: Icon(Icons.arrow_forward_ios, size: 20),
//                                       ),
//                               ],
//                             ),
//                           ],
//                         ),
//                       ),
//                     );
//                   },
//                 ),
//                 Divider(),
//                 ListView.builder(
//                   itemCount: aboutAppOptions.length,
//                   shrinkWrap: true,
//                   primary: false,
//                   itemBuilder: (context, int index) {
//                     return InkWell(
//                       onTap: () {
//                         if (index == 2) {
//                           LogoutBottomSheet(context);
//                         } else {
//                           Navigator.push(context, MaterialPageRoute(builder: (context) => accountScreenHelpRoutList[index]));
//                         }
//                       },
//                       child: Padding(
//                         padding: EdgeInsets.only(left: 16, right: 8),
//                         child: Row(
//                           children: [
//                             index == 2
//                                 ? Image.asset(aboutAppOptions[index].image ?? "", height: 20, width: 20, color: Colors.redAccent)
//                                 : Image.asset(aboutAppOptions[index].image ?? "",
//                                     height: 20, width: 20, color: mode.theme ? Colors.white : Colors.black),
//                             SizedBox(width: 16),
//                             index == 2
//                                 ? Text(aboutAppOptions[index].title ?? "", style: appMainPrimaryTextStyle(color: Colors.redAccent))
//                                 : Text(aboutAppOptions[index].title ?? "", style: appMainPrimaryTextStyle()),
//                             Spacer(),
//                             Row(
//                               children: [
//                                 Text(
//                                   aboutAppOptions[index].trailingText ?? "",
//                                   style: appMainPrimaryTextStyle(),
//                                 ),
//                                 index == 2
//                                     ? IconButton(
//                                         onPressed: () {},
//                                         splashRadius: 1,
//                                         icon: Icon(Icons.arrow_forward_ios, size: 0),
//                                       )
//                                     : IconButton(
//                                         onPressed: () {
//                                           if (index == 2) {
//                                             LogoutBottomSheet(context);
//                                           } else {
//                                             Navigator.push(context, MaterialPageRoute(builder: (context) => accountScreenHelpRoutList[index]));
//                                           }
//                                         },
//                                         splashRadius: 28,
//                                         icon: Icon(Icons.arrow_forward_ios, size: 20),
//                                       ),
//                               ],
//                             ),
//                           ],
//                         ),
//                       ),
//                     );
//                   },
//                 ),
//               ],
//             ),
//           ),
//         );
//       },
//     );
//   }
// }
