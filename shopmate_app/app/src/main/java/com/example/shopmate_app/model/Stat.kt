package com.example.shopmate_app.model

data class Stat (
    val statId : UInt?,
    val nfollowers : ULong,
    val nfollows : ULong,
    val nsaves : ULong,
    val nyourSaves : ULong
)

class Stats : ArrayList<Stat>()