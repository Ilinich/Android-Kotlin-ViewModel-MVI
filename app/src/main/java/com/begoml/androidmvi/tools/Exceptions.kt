package com.begoml.androidmvi.tools

import java.lang.Exception

class InitComponentException: RuntimeException("You must call 'init' method")

class NotImplementedException : Exception()