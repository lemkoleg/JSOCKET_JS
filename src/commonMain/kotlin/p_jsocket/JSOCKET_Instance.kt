/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("DuplicatedCode", "unused")

package p_jsocket

import CrossPlatforms.CrossPlatformFile
import CrossPlatforms.getMyOS
import CrossPlatforms.slash
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


/**
 *
 * @author Oleg
 */

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
object JSOCKET_Instance {
    lateinit var RootPath: String

    @JsName("pathErrors")
    var pathErrors: String = ""
        private set

    @JsName("pathLogs")
    var pathLogs: String = ""
        private set

    @JsName("pathTemp")
    var pathTemp: String = ""
        private set

    @JsName("pathMusic14")
    var pathMusic14: String = ""
        private set

    @JsName("pathMusic15")
    var pathMusic15: String = ""
        private set

    @JsName("pathVideo16")
    var pathVideo16: String = ""
        private set

    @JsName("pathVideo17")
    var pathVideo17: String = ""
        private set

    @JsName("pathPicture18")
    var pathPicture18: String = ""
        private set

    @JsName("pathPicture19")
    var pathPicture19: String = ""
        private set

    @JsName("pathTempMusic14")
    var pathTempMusic14: String = ""
        private set

    @JsName("pathTempMusic15")
    var pathTempMusic15: String = ""
        private set

    @JsName("pathTempVideo16")
    var pathTempVideo16: String = ""
        private set

    @JsName("pathTempVideo17")
    var pathTempVideo17: String = ""
        private set

    @JsName("pathTempPicture18")
    var pathTempPicture18: String = ""
        private set

    @JsName("pathFile50")
    var pathFile50: String = ""
        private set

    @JsName("pathTempFile50")
    var pathTempFile50: String = ""
        private set

    @JsName("pathTempPicture19")
    var pathTempPicture19: String = ""
        private set
    private const val time_wait = 10000L


    @JsName("initDirectories")
    suspend fun initDirectories(
        L_RootPath: String
    ) {
        var file = CrossPlatformFile(L_RootPath)
        if(!file.exists() || file.isFile()){
            file.CreateDirectory()
        }
        RootPath = L_RootPath.trim().plus(slash)

        file = CrossPlatformFile(RootPath.plus("Errors"))
        if(!file.exists() || file.isFile()){
            file.CreateDirectory()
        }
        pathErrors = RootPath.plus("Errors").plus(slash)

        file = CrossPlatformFile(RootPath.plus("Logs"))
        if(!file.exists() || file.isFile()){
            file.CreateDirectory()
        }
        pathLogs = RootPath.plus("Logs").plus(slash)

        file = CrossPlatformFile(RootPath.plus("Temp"))
        if(!file.exists() || file.isFile()){
            file.CreateDirectory()
        }
        pathTemp = RootPath.plus("Temp").plus(slash)

        file = CrossPlatformFile(RootPath.plus("14"))
        if(!file.exists() || file.isFile()){
            file.CreateDirectory()
        }
        pathMusic14 = RootPath.plus("14").plus(slash)

        file = CrossPlatformFile(RootPath.plus("15"))
        if(!file.exists() || file.isFile()){
            file.CreateDirectory()
        }
        pathMusic15 = RootPath.plus("15").plus(slash)

        file = CrossPlatformFile(RootPath.plus("16"))
        if(!file.exists() || file.isFile()){
            file.CreateDirectory()
        }
        pathVideo16 = RootPath.plus("16").plus(slash)

        file = CrossPlatformFile(RootPath.plus("17"))
        if(!file.exists() || file.isFile()){
            file.CreateDirectory()
        }
        pathVideo17 = RootPath.plus("17").plus(slash)

        file = CrossPlatformFile(RootPath.plus("18"))
        if(!file.exists() || file.isFile()){
            file.CreateDirectory()
        }
        pathPicture18 = RootPath.plus("18").plus(slash)

        file = CrossPlatformFile(RootPath.plus("19"))
        if(!file.exists() || file.isFile()){
            file.CreateDirectory()
        }
        pathPicture19 = RootPath.plus("19").plus(slash)

        file = CrossPlatformFile(pathTemp.plus("14"))
        if(!file.exists() || file.isFile()){
            file.CreateDirectory()
        }
        pathTempMusic14 = pathTemp.plus("14").plus(slash)

        file = CrossPlatformFile(pathTemp.plus("15"))
        if(!file.exists() || file.isFile()){
            file.CreateDirectory()
        }
        pathTempMusic15 = pathTemp.plus("15").plus(slash)

        file = CrossPlatformFile(pathTemp.plus("16"))
        if(!file.exists() || file.isFile()){
            file.CreateDirectory()
        }
        pathTempVideo16 = pathTemp.plus("16").plus(slash)

        file = CrossPlatformFile(pathTemp.plus("17"))
        if(!file.exists() || file.isFile()){
            file.CreateDirectory()
        }
        pathTempVideo17 = pathTemp.plus("17").plus(slash)

        file = CrossPlatformFile(pathTemp.plus("18"))
        if(!file.exists() || file.isFile()){
            file.CreateDirectory()
        }
        pathTempPicture18 = pathTemp.plus("18").plus(slash)

        file = CrossPlatformFile(pathTemp.plus("19"))
        if(!file.exists() || file.isFile()){
            file.CreateDirectory()
        }
        pathTempPicture19 = pathTemp.plus("19").plus(slash)

        file = CrossPlatformFile(RootPath.plus("50"))
        if(!file.exists() || file.isFile()){
            file.CreateDirectory()
        }
        pathFile50 = RootPath.plus("50").plus(slash)

        file = CrossPlatformFile(pathTemp.plus("50"))
        if(!file.exists() || file.isFile()){
            file.CreateDirectory()
        }
        pathTempFile50 = pathTemp.plus("50").plus(slash)


        Constants.myConnectionContext = getMyOS()
    }
}