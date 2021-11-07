package ru.sber.rdbms

class LockOnBaseException(private val msg: String) : Exception(msg)