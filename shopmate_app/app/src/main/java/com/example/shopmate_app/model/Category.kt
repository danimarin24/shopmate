package com.example.shopmate_app.model

import java.sql.Time

data class Category(
    // PK
    val id: Int,
    val name: String,
    val icon: String
)


class Categories : ArrayList<Category>()