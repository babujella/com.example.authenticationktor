package com.example

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import me.liuwj.ktorm.database.Database

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(ContentNegotiation){
            json()
        }
        val url="jdbc:mysql://localhost:3306/ktor"
        val driver="com.mysql.cj.jdbc.Driver"
        val user="root"
        val password="root"
        val database=Database.connect(url,driver, user, password)
        configureRouting()
    }.start(wait = true)
}
