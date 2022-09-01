package com.example.mvvmtutorial

import java.util.*

data class MainModel (val id : String = UUID.randomUUID().toString(), val title : String = "")
