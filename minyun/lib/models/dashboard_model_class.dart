import 'package:flutter/material.dart';

class DashboardIcons {
  String? image;
  String? title;
  Color? color;
  Color? imageColor;
  Color? darkImageColor;

  DashboardIcons({this.image, this.color, this.title, this.imageColor, required this.darkImageColor});
}

List<DashboardIcons> dashboardIconsList = [
  DashboardIcons(
      color: Color(0xFFfff7eb),
      imageColor: Color(0xFFeda43c),
      darkImageColor: Color(0xFF2a211f),
      image: "assets/icons/dashboard_icons/scan.png",
      title: "Scan Code"),
  DashboardIcons(
      color: Color(0xFFf8f5f5),
      imageColor: Color(0xFFa6745c),
      darkImageColor: Color(0xFF222125),
      image: "assets/icons/dashboard_icons/watermark.png",
      title: "Watermark"),
  DashboardIcons(
      color: Color(0xFFfff2f1),
      imageColor: Color(0xFFe48c6e),
      darkImageColor: Color(0xFF2a211f),
      image: "assets/icons/dashboard_icons/edit.png",
      title: "eSign PDF"),
  DashboardIcons(
      color: Color(0xFFf2f4ff),
      imageColor: Color(0xFF816cff),
      darkImageColor: Color(0xFF1c2131),
      image: "assets/icons/dashboard_icons/cut.png",
      title: "Split PDF"),
  DashboardIcons(
      color: Color(0xFFfff2f1),
      imageColor: Color(0xFFe69073),
      darkImageColor: Color(0xFF262124),
      image: "assets/icons/dashboard_icons/managePDF.png",
      title: "Merge PDF"),
  DashboardIcons(
      color: Color(0xFFe9efea),
      imageColor: Color(0xFF43dba6),
      darkImageColor: Color(0xFF172127),
      image: "assets/icons/dashboard_icons/key.png",
      title: "Protect PDF"),
  DashboardIcons(
      color: Color(0xFFfff7eb),
      imageColor: Color(0xFFeda43c),
      darkImageColor: Color(0xFF2a211f),
      image: "assets/icons/dashboard_icons/star.png",
      title: "Compress PDF"),
  DashboardIcons(
      color: Color(0xFFf3f5ff),
      imageColor: Color(0xFF607aff),
      darkImageColor: Color(0xFF1b2031),
      image: "assets/icons/dashboard_icons/menu.png",
      title: "All Tools"),
];

enum type { file, folder }

class DashboardList {
  String? image;
  String? titleText;
  String? date;
  String? time;
  String? folderText;

  // String? filetype;

  DashboardList(this.image, this.titleText, this.date, this.time, this.folderText);
}

List<DashboardList> dashboardFilesList = [
  DashboardList("assets/images/pdf.png", "Job Application Letter", "12/30/23", "09:41", ""),
  DashboardList("assets/images/pdf.png", "Requirements Documents", "12/20/23", "01:41", ""),
  DashboardList("assets/images/pdf.png", "Recommendation Letter", "12/15/23", "05:41", ""),
  DashboardList("assets/images/pdf.png", "Business Plan Proposal", "12/14/23", "10:41", ""),
];
List<DashboardList> recentFilesList = [
  DashboardList("assets/images/pdf.png", "Job Application Letter", "12/30/23", "09:41", ""),
  DashboardList("assets/images/pdf.png", "Requirements Documents", "12/20/23", "01:41", ""),
  DashboardList("assets/images/pdf.png", "Recommendation Letter", "12/15/23", "05:41", ""),
  DashboardList("assets/images/pdf.png", "Business Plan Proposal", "12/14/23", "10:41", ""),
  DashboardList("assets/images/pdf.png", "Legal & Terms of Reference", "12/12/23", "11:31", ""),
  DashboardList("assets/images/pdf.png", "Software Requirements", "12/10/23", "10:41", ""),
  DashboardList("assets/images/pdf.png", "Delayed Job Application Letter", "12/06/23", "09:41", ""),
  DashboardList("assets/images/pdf.png", "Delayed Requirements Documents", "12/04/23", "01:41", ""),
];

class RecentFiles {
  String? image;
  String? Text;
  String? size;

  RecentFiles(this.image, this.Text, this.size);
}

List<RecentFiles> recentFileShareOptions = [
  RecentFiles("assets/icons/dashboard_icons/link.png", "Share Link", ""),
  RecentFiles("assets/icons/dashboard_icons/share.png", "Share PDF", "(1.2 MB)"),
  RecentFiles("assets/icons/dashboard_icons/share.png", "Share Word", "(406 KB)"),
  RecentFiles("assets/icons/dashboard_icons/image.png", "Share JPG", "(500 KB)"),
  RecentFiles("assets/icons/dashboard_icons/image.png", "Share PNG", "(800 KB)"),
];
List<RecentFiles> recentFilesMenuOptionsList = [
  RecentFiles("assets/icons/dashboard_icons/watermark.png", "Add Watermark", ""),
  RecentFiles("assets/icons/dashboard_icons/signature.png", "Add Digital Signature", ""),
  RecentFiles("assets/icons/dashboard_icons/scissors.png", "Split PDF", ""),
  RecentFiles("assets/icons/dashboard_icons/pdf_file.png", "Merge PDF", ""),
  RecentFiles("assets/icons/dashboard_icons/key.png", "Protect PDF", ""),
  RecentFiles("assets/icons/dashboard_icons/star.png", "Compress PDF", ""),
];
List<RecentFiles> recentFilesMenuOptionsBottomList = [
  RecentFiles("assets/icons/dashboard_icons/edit.png", "Rename", ""),
  RecentFiles("assets/icons/bottom_navigation_icons/folder_outlined.png", "Move to folder", ""),
  RecentFiles("assets/icons/dashboard_icons/printer.png", "Print", ""),
  RecentFiles("assets/icons/dashboard_icons/delete.png", "Delete", ""),
];
List<RecentFiles> folderBottomSheetList = [
  RecentFiles("assets/images/link.png", "Share Link", ""),
  RecentFiles("assets/images/users.png", "Invite Collaborators", ""),
  RecentFiles("assets/icons/dashboard_icons/printer.png", "Rename", ""),
  RecentFiles("assets/icons/dashboard_icons/delete.png", "Move Folder", ""),
  RecentFiles("assets/icons/dashboard_icons/delete.png", "Delete", ""),
];
