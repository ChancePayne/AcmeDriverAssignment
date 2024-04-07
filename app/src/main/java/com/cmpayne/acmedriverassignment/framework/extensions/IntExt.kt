package com.cmpayne.acmedriverassignment.framework.extensions

/* This is used to check the factors of the lengths of strings for the "secret algorithm".
 Non One because everything shares a 1. I could change this to include 1 and remove it
 upstream, but */
fun Int.nonOneFactors() = (2..this).filter { this % it == 0 }.toSet()