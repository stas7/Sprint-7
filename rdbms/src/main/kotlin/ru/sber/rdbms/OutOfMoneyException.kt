package ru.sber.rdbms

class OutOfMoneyException(private val msg: String) : Exception(msg)