package com.example.plugins

import com.example.plugins.models.User
import com.example.plugins.models.UserCredentials
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.liuwj.ktorm.dsl.*
import org.mindrot.jbcrypt.BCrypt

fun Application.AuthenticationRouting(){
    val db=DataBaseConnection.database
        routing {
            post ("/registration"){
                val userCredentials=call.receive<UserCredentials>()

                // validation for username and password...
                if(!userCredentials.isvalidCredentials()){
                    call.respond("The username must be greater than 3 and password should be greater than 6")
                    return@post
                }
                val username=userCredentials.username
                val password=userCredentials.hashedpassword()

                //Validation to Username exists or not
                val userInput=db.from(UserEntity).select().
                where { UserEntity.username eq username }.
                map {it[UserEntity.username]}.firstOrNull()

                if(userInput!=null){
                    call.respond("The username is already Exists...")
                    return@post
                }
                db.insert(UserEntity){
                    set(it.username,username)
                    set(it.password,password)
                }
                call.respond("User values are inserted...")
            }
            get ("/Babu"){
                call.respond("Helloooo")
            }
            post ("/login"){
                val userCredentials=call.receive<UserCredentials>()

                // validation for username and password...
              /*  if(!userCredentials.isvalidCredentials()){
                    call.respond("The username must be greater than 3 and password should be greater than 6")
                    return@post
                }*/
                val username=userCredentials.username
                val password=userCredentials.password
                // login

                val userinput=db.from(UserEntity).select().
                        where { UserEntity.username eq username }.
                        map { val id=it[UserEntity.id]!!
                            val username=it[UserEntity.username]!!
                             val password=it[UserEntity.password]!!
                           User(id,username,password)
                        }.firstOrNull()

                if (userinput==null){
                    call.respond("Invalid Username or Password")
                    return@post
                }
                val doesPasswordcheck=BCrypt.checkpw(password,userinput?.password)
                if(!doesPasswordcheck){
                    call.respond("Invalid Username or Password")
                    return@post
                }
                call.respond("Succesfully Login")
            }
        }
}