class ServiceException implements Exception {
  final String code;
  final String message;

  ServiceException(this.message, this.code);

  @override
  String toString() => "ServiceException $code: $message";
}