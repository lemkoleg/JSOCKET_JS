/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("DuplicatedCode", "unused")

package p_jsocket

import CrossPlatforms.MyFile
import CrossPlatforms.slash
import kotlin.js.JsName


/**
 *
 * @author Oleg
 */
private lateinit var RootPath: String

@JsName("rootPath")
var rootPath: String = ""
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
@JsName("pathTempPicture19")
var pathTempPicture19: String = ""
    private set
private const val time_wait = 10000L



@JsName("initDirectories")
fun initDirectories(
        L_RootPath: String
    ) {
        RootPath = L_RootPath.trim()
        pathErrors = rootPath.plus("Errors")
        pathLogs = rootPath.plus("Logs")
        pathTemp = rootPath.plus("Temp")
        pathMusic14 = rootPath.plus("14")
        pathMusic15 = rootPath.plus("15")
        pathVideo16 = rootPath.plus("16")
        pathVideo17 = rootPath.plus("17")
        pathPicture18 = rootPath.plus("18")
        pathPicture19 = rootPath.plus("19")

        var file = MyFile(rootPath)
        if (!file.isExists() || !file.isPath()) {
            file.createPath()
        }
        file.close()

        file = MyFile(pathErrors)
        if (!file.isExists() || !file.isPath()) {
            file.createPath()
        }
        file.close()

        file = MyFile(pathLogs)
        if (!file.isExists() || !file.isPath()) {
            file.createPath()
        }
        file.close()

        file = MyFile(pathTemp)
        if (!file.isExists() || !file.isPath()) {
            file.createPath()
        }
        file.close()

        pathTemp =
            pathTemp.plus(slash)
        pathTempMusic14 =
            pathTemp.plus("14")
        pathTempMusic15 =
            pathTemp.plus("15")
        pathTempVideo16 =
            pathTemp.plus("16")
        pathTempVideo17 =
            pathTemp.plus("17")
        pathTempPicture18 =
            pathTemp.plus("18")
        pathTempPicture19 =
            pathTemp.plus("19")

        file = MyFile(pathMusic14)
        if (!file.isExists() || !file.isPath()) {
            file.createPath()
        }
        file.close()

        file = MyFile(pathMusic15)
        if (!file.isExists() || !file.isPath()) {
            file.createPath()
        }
        file.close()

        file = MyFile(pathVideo16)
        if (!file.isExists() || !file.isPath()) {
            file.createPath()
        }
        file.close()

        file = MyFile(pathVideo17)
        if (!file.isExists() || !file.isPath()) {
            file.createPath()
        }
        file.close()

        file = MyFile(pathPicture18)
        if (!file.isExists() || !file.isPath()) {
            file.createPath()
        }
        file.close()

        file = MyFile(pathPicture19)
        if (!file.isExists() || !file.isPath()) {
            file.createPath()
        }
        file.close()

        file = MyFile(pathTempMusic14)
        if (!file.isExists() || !file.isPath()) {
            file.createPath()
        }
        file.close()

        file = MyFile(pathTempMusic15)
        if (!file.isExists() || !file.isPath()) {
            file.createPath()
        }
        file.close()

        file = MyFile(pathTempVideo16)
        if (!file.isExists() || !file.isPath()) {
            file.createPath()
        }
        file.close()

        file = MyFile(pathTempVideo17)
        if (!file.isExists() || !file.isPath()) {
            file.createPath()
        }
        file.close()

        file = MyFile(pathTempPicture18)
        if (!file.isExists() || !file.isPath()) {
            file.createPath()
        }
        file.close()

        file = MyFile(pathTempPicture19)
        if (!file.isExists() || !file.isPath()) {
            file.createPath()
        }
        file.close()

        pathTempMusic14 =
            pathTempMusic14.plus(slash)
        pathTempMusic15 =
            pathTempMusic15.plus(slash)
        pathTempVideo16 =
            pathTempVideo16.plus(slash)
        pathTempVideo17 =
            pathTempVideo17.plus(slash)
        pathTempPicture18 =
            pathTempPicture18.plus(slash)
        pathTempPicture19 =
            pathTempPicture19.plus(slash)
    }
