import 'dart:convert';
import 'dart:io';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_document_scanner/flutter_document_scanner.dart';
import 'package:minyun/api/UserApi.dart';
import 'package:minyun/config/ServiceException.dart';
import 'package:minyun/config/UnauthorizedAccessException.dart';
import 'package:minyun/constant.dart';
// import 'package:minyun/screen/TabBarSignInScreen.dart';
import 'package:minyun/utils/SecureStorage.dart';
import 'package:get/get.dart';
import 'package:http/http.dart' as http;

import 'HttpStatusCodes.dart';


class HttpUtil {

  static const Map<String, String> _defaultHeaders = {
    "Content-Type": "application/json; charset=utf-8", // 修正这里
    "change": "modile", // 修正这里
  };

  static Future<Map<String, dynamic>> get(String path, [Map<String, String>? queryParams]) async {
    if (_isPathForAdmin(path) && !await _hasValidToken()) {
      throw UnauthorizedAccessException(); // 自定义异常
    }
    var urlPath=path;
    if(!path.startsWith("https")){
      urlPath=baseUrl + path;
    }
    final Uri uri = Uri.parse(urlPath).replace(queryParameters: queryParams);
    var headers = await _getHeadersWithToken();
    var response =  await http.get(uri, headers: headers);
    return _processResponse(response);
  }

  static Future<Map<String, dynamic>> getSource(String path, [Map<String, String>? queryParams]) async {
    if (_isPathForAdmin(path) && !await _hasValidToken()) {
      throw UnauthorizedAccessException(); // 自定义异常
    }
    var urlPath=path;
    if(!path.startsWith("https")){
      urlPath=baseUrl + path;
    }
    final Uri uri = Uri.parse(urlPath).replace(queryParameters: queryParams);
    var headers = await _getHeadersWithToken();
    var response =  await http.get(uri, headers: headers);
    var data = json.decode(response.body);
    return data;
  }


  static Future<Map<String, dynamic>> post(String path, Map<String, dynamic> body) async {
    if (_isPathForAdmin(path) && !await _hasValidToken()) {
      throw UnauthorizedAccessException(); // 自定义异常
    }
    var urlPath=path;
    if(!path.startsWith("https")){
      urlPath=baseUrl + path;
    }
    final Uri uri = Uri.parse(urlPath);
    var headers = await _getHeadersWithToken();
    var response =  await http.post(uri, body: jsonEncode(body), headers: headers);
    return _processResponse(response);
  }
  static Map<String, dynamic> _processResponse(http.Response response) {
    var data = json.decode(response.body);
    if (response.statusCode == 200 ) {
      if (data['code'] != 200) {
       // print(data['msg']);
       //  Get.dialog(
          AlertDialog(
            title: Text("错误"),
            content: Text(data['msg']),
            actions: <Widget>[
              TextButton(
                child: Text("确定"),
                onPressed: () {
                  Get.back(); // 关闭对话框
                },
              ),
            ],
          );
        //   barrierDismissible: false, // 点击对话框外部不关闭对话框
        // );
        throw ServiceException(data['code'].toString(),data['msg']);
      }
    }else{
      if(response.statusCode == 401) {
        UserApi.logout();
        // Get.to(TabBarSignInScreen(0));
      }else{

        Get.dialog(
          AlertDialog(
            title: Text("错误"),
            content: Text("服务器连接异常"),
            actions: <Widget>[
              TextButton(
                child: Text("确定"),
                onPressed: () {
                  Get.back(); // 关闭对话框
                },
              ),
            ],
          ),
          barrierDismissible: false, // 点击对话框外部不关闭对话框
        );
        throw ServiceException('500','服务器连接异常');
      }
    }
    return data;
  }


  static Future<Map<String, String>> _getHeadersWithToken() async {
    var headers = Map<String, String>.from(_defaultHeaders);
    String? token = await SecureStorage().getLoginToken();
    if (token != null && token.isNotEmpty) {
       headers['change'] = 'modile';
      headers['Authorization'] = 'Bearer $token';
    }
    return headers;
  }

  static bool _isPathForAdmin(String path) {
    return path.startsWith('/admin');
  }

  static Future<bool> _hasValidToken() async {
    String? token = await SecureStorage().getLoginToken();
    return token != null && token.isNotEmpty;
  }

  static Map<String, dynamic> processResponse(http.Response response) {
    if (response.statusCode == HttpStatusCodes.ok) {
      return json.decode(response.body);
    } else {
      return {'code': response.statusCode, 'message': 'Error', 'data': null};
    }
  }

  static Future<Map<String, dynamic>> uploadFlie(String path, File file, {int isFree = 1, int aid = 0}) async {
    var uri = Uri.parse(baseUrl + path); // 上传图片的目标URL
    var headers = await _getHeadersWithToken(); // 获取带有Token的头部

    var request = http.MultipartRequest('POST', uri)
      ..headers.addAll(headers) // 添加头部到请求中
      ..fields['isFree'] = isFree.toString() // 添加 isFree 字段
      ..fields['aid'] = aid.toString() // 添加 aid 字段
      ..files.add(
        await http.MultipartFile.fromPath(
          'file', // 在服务器端接收图像的字段名称
          file.path,
        ),
      );

    var response = await request.send();
    if (response.statusCode == HttpStatus.ok) {
      // 将响应数据转换为字符串
      String responseBody = await response.stream.bytesToString();
      // 解析响应数据为JSON
      Map<String, dynamic> responseData = json.decode(responseBody);
      // 成功逻辑
      return responseData;
    } else {
      return {'code': response.statusCode, 'message': 'Error', 'data': null};
    }
  }

  static Future<Map<String, dynamic>> uploadImage(String path, XFile image, {int isFree = 1, int aid = 0}) async {
    var uri = Uri.parse(baseUrl + path);
    var headers = await _getHeadersWithToken(); // 获取带有Token的头部

    var request = http.MultipartRequest('POST', uri)
      ..headers.addAll(headers) // 添加头部到请求中
      ..fields['isFree'] = isFree.toString() // 添加 isFree 字段
      ..fields['aid'] = aid.toString() // 添加 aid 字段
      ..files.add(await http.MultipartFile.fromPath(
        'file', // 'file' 是后端期望的字段名
        image.path,
      ));
    try {
      var response = await request.send();

      if (response.statusCode == 200) {
        // 将响应数据转换为字符串
        String responseBody = await response.stream.bytesToString();
        // 解析响应数据为JSON
        Map<String, dynamic> responseData = json.decode(responseBody);
        // 成功逻辑
        return responseData;
      } else {
        // 失败逻辑
        return {'code': response.statusCode, 'message': 'Error', 'data': null};
      }
    } catch (e) {
      // 异常处理
      debugPrint('上传异常: $e');
      return {'code': 500, 'message': 'Error', 'data': null};
    }
  }
}
