//自定义权限异常
class UnauthorizedAccessException implements Exception {
  final String message = "Unauthorized access, redirecting to login.";

  @override
  String toString() => "UnauthorizedAccessException: $message";
}