package sql

import JSOCKET.AvaClubDB

var db: AvaClubDB? = null

class MySQLConnection(DBName:String){

    val dbName = DBName

    fun createStatement():SQLStatement{
        return SQLStatement()
    }

    fun isConnected():Boolean {
        return true
    }

    fun connect():Boolean {
        return isConnected()
    }

    fun close(){

    }
}