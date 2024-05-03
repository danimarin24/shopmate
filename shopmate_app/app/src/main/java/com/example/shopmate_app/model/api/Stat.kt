package com.example.shopmate_app.model.api

data class Stat (
    val statId : UInt?,
    val nfollowers : ULong,
    val nfollows : ULong,
    val nsaves : ULong,
    val nyourSaves : ULong
)

data class StatId(
    val statId: String
)


class Stats : ArrayList<Stat>()