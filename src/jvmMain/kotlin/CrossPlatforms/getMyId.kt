@file:Suppress("UNREACHABLE_CODE", "DEPRECATED_IDENTITY_EQUALS", "RedundantSuspendModifier")

package CrossPlatforms


var result = ""

actual fun getMyOS(): String {
    return System.getProperty("os.name") + " - " + System.getProperty("os.version")
}

actual val slash: String = System.getProperty("file.separator")


actual suspend fun getMyDeviceId(): String {

    /*
    if (result.trim().isEmpty()) {
        when (System.getProperty("os.name").trim().uppercase(Locale.getDefault()).substring(0, 3)) {
                "WIN" -> serialNumberWindows
                "LIN" -> serialNumberLinux
                "MAC" -> serialNumberMacOS
                else -> result = ""
            }
        }
        if (result.trim().isEmpty()) {
            generateLicenseKeyOSHI()
        }
     */
        return result
    }

/*
    ////////////////////////////////////////////////////////////////////////////////
    private fun generateLicenseKeyOSHI() {
        try {
            result = ""
            val systemInfo = SystemInfo()
            val hardwareAbstractionLayer: HardwareAbstractionLayer = systemInfo.hardware
            val centralProcessor: CentralProcessor = hardwareAbstractionLayer.processor
            val processorSerialNumber: String = centralProcessor.processorID
            result = processorSerialNumber
        } catch (e: Exception) {
            result = ""
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    private val serialNumberWindows: Unit
    get() {
        try {
            result = ""
            val os: OutputStream
            val `is`: InputStream
            val runtime: Runtime = Runtime.getRuntime()
            val process: Process = runtime.exec(arrayOf("wmic", "bios", "get", "serialnumber"))
            os = process.outputStream
            `is` = process.inputStream
            try {
                os.close()
            } catch (e: IOException) {
            }
            val sc = Scanner(`is`)
            try {
                while (sc.hasNext()) {
                    val next: String = sc.next()
                    if ("SerialNumber" == next) {
                        result = sc.next().trim()
                        break
                    }
                }
            } finally {
                try {
                    `is`.close()
                } catch (e1: Exception) {
                }
            }
        } catch (e: Exception) {
            result = ""
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    private val serialNumberLinux: Unit
    get() {
        result = ""
        if (result.trim().isEmpty()) {
            readDmidecode()
        }
        if (result.trim().isEmpty()) {
            readLshal()
        }
    }

    private fun read(command: String): BufferedReader {
        val os: OutputStream
        val `is`: InputStream
        val runtime: Runtime = Runtime.getRuntime()
        val process: Process
        val arr = command.split(" ").toTypedArray()
        process = try {
            runtime.exec(arr)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        os = process.outputStream
        `is` = process.inputStream
        try {
            os.close()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        return BufferedReader(InputStreamReader(`is`))
    }

    private fun readDmidecode() {
        try {
            var line: String
            val marker = "Serial Number:"
            var br: BufferedReader? = null
            try {
                br = read("dmidecode -t system")
                while (br.readLine().also { line = it } != null) {
                    if (line.indexOf(marker) !== -1) {
                        result = line.split(marker)[1].trim()
                        break
                    }
                }
            } finally {
                if (br != null) {
                    try {
                        br.close()
                    } catch (e: Exception) {
                    }
                }
            }
        } catch (e: Exception) {
            result = ""
        }
    }

    private fun readLshal() {
        try {
            var line: String
            val marker = "system.hardware.serial ="
            var br: BufferedReader? = null
            try {
                br = read("lshal")
                while (br.readLine().also { line = it } != null) {
                    if (line.indexOf(marker) !== -1) {
                        result =
                            line.split(marker)[1].replace("\\(string\\)|(\\')", "").trim()
                        break
                    }
                }
            } finally {
                if (br != null) {
                    try {
                        br.close()
                    } catch (e1: Exception) {
                    }
                }
            }
        } catch (e: Exception) {
            result = ""
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    private val serialNumberMacOS: Unit
    get() {
        try {
            result = ""
            val os: OutputStream?
            val `is`: InputStream?
            val runtime: Runtime = Runtime.getRuntime()
            val process: Process = runtime.exec(arrayOf("/usr/sbin/system_profiler", "SPHardwareDataType"))
            os = process.outputStream
            `is` = process.inputStream
            try {
                os.close()
            } catch (e: Exception) {
            }
            val br = BufferedReader(InputStreamReader(`is` as InputStream))
            var line: String
            val marker = "Serial Number"
            try {
                while (br.readLine().also { line = it } != null) {
                    if (line.contains(marker)) {
                        result = line.split(":")[1].trim()
                        break
                    }
                }
            } finally {
                try {
                    `is`.close()
                } catch (e: Exception) {
                }
            }
        } catch (e: Exception) {
            result = ""
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    private fun GetBiosSerialNumber() {
        result = try {
            val process: Process = Runtime.getRuntime().exec(arrayOf("wmic", "bios", "get", "serialnumber"))
            process.outputStream.close()
            val sc = Scanner(process.inputStream)
            sc.next()
            val serial: String = sc.next()
            serial
        } catch (e: Exception) {
            ""
        }
    }
 */

actual val lineSeparator: String = " \n"
