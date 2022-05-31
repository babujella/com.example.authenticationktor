package com.example.plugins

import me.liuwj.ktorm.schema.Table
import me.liuwj.ktorm.schema.int
import me.liuwj.ktorm.schema.varchar

object UserEntity :Table<Nothing>("users"){
    val id=int("id").primaryKey()
    val username=varchar("username")
    val password=varchar("password")
}