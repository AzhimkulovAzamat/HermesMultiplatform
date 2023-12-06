package net.breez.hermes.data.rest.exception

class UnknownHttpException(code: Int): Throwable("Unknown HttpException with $code code")