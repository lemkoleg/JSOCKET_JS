package sql

import JSOCKETDB.AUFDB
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlin.time.ExperimentalTime

var db: AUFDB? = null

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
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