package com.cmpayne.acmedriverassignment.framework.extensions

fun String.numberOfVowels(): Int = Regex("[aeiouAEIOU]").findAll(this).count()

fun String.numberOfConsonants(): Int = Regex("[bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ]").findAll(this).count()
