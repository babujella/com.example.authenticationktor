package com.example.plugins.models
import kotlinx.serialization.Serializable
import org.mindrot.jbcrypt.BCrypt

@Serializable
data class UserCredentials(val username:String,val password:String){
    fun hashedpassword():String{
        return BCrypt.hashpw(password,BCrypt.gensalt())
    }
    fun isvalidCredentials():Boolean{
        return username.length>=3 && password.length>=6
    }
}
