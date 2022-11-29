package p_jsocket

import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlin.time.ExperimentalTime

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
class AnswerTypeConstants(answerType: ANSWER_TYPE) {


    val IsMessege: Boolean = answerType.RECORD_TYPE == "4" ||
            answerType.RECORD_TYPE == "M"

    val IsComment: Boolean = answerType.RECORD_TYPE == "A" ||
            answerType.RECORD_TYPE == "C" ||
            answerType.RECORD_TYPE == "E" ||
            answerType.RECORD_TYPE == "G"


    val IsFile = if (IsMessege) {
        when (answerType.answerTypeValues.GetMessegeObjectType()) {
            "5" -> {
                true
            }
            else -> {
                false
            }
        }
    } else {
        false //answerType.RECORD_TABLE_ID.substring(0, 2) == "18" || answerType.RECORD_TABLE_ID.substring(0, 2) == "19"
    }

    val IsGif = if (IsFile) {
        when (answerType.answerTypeValues.GetMessegeFileType()) {
            "6" -> {
                true
            }
            else -> {
                false
            }
        }
    } else {
        false
    }

    val IsVoice = if (IsFile) {
        when (answerType.answerTypeValues.GetMessegeFileType()) {
            "4" -> {
                true
            }
            else -> {
                false
            }
        }
    } else {
        false
    }

    val IsAnotherFile = if (IsFile) {
        when (answerType.answerTypeValues.GetMessegeFileType()) {
            "5" -> {
                true
            }
            else -> {
                false
            }
        }
    } else {
        false
    }

    val IsPicture = if (IsMessege) {
        when (answerType.answerTypeValues.GetMessegeObjectType()) {
            "3", "8" -> {
                true
            }
            else -> {
                if (IsFile) {
                    when (answerType.answerTypeValues.GetMessegeFileType()) {
                        "3" -> {
                            true
                        }
                        else -> {
                            false
                        }
                    }
                } else {
                    false
                }
            }
        }
    } else {
        answerType.RECORD_TABLE_ID.substring(0, 2) == "18" || answerType.RECORD_TABLE_ID.substring(0, 2) == "19"
    }

    val IsVideo = if (IsMessege) {
        when (answerType.answerTypeValues.GetMessegeObjectType()) {
            "2" -> {
                true
            }
            else -> {
                if (IsFile) {
                    when (answerType.answerTypeValues.GetMessegeFileType()) {
                        "2" -> {
                            true
                        }
                        else -> {
                            false
                        }
                    }
                } else {
                    false
                }
            }
        }
    } else {
        answerType.RECORD_TABLE_ID.substring(0, 2) == "16" || answerType.RECORD_TABLE_ID.substring(0, 2) == "17"
    }

    val IsMusic = if (IsMessege) {
        when (answerType.answerTypeValues.GetMessegeObjectType()) {
            "1" -> {
                true
            }
            else -> {
                if (IsFile) {
                    when (answerType.answerTypeValues.GetMessegeFileType()) {
                        "1" -> {
                            true
                        }
                        else -> {
                            false
                        }
                    }
                } else {
                    false
                }
            }
        }
    } else {
        answerType.RECORD_TABLE_ID.substring(0, 2) == "14" || answerType.RECORD_TABLE_ID.substring(0, 2) == "15"
    }

    val IsAlbum = if (IsMessege) {
        when (answerType.answerTypeValues.GetMessegeObjectType()) {
            "7" -> {
                true
            }
            else -> {
                false
            }
        }
    } else {
        answerType.RECORD_TABLE_ID.substring(0, 2) == "13"
    }

    val IsDBObject = if (IsMessege) {
        when (answerType.answerTypeValues.GetMessegeObjectType()) {
            "1", "2", "3", "7" -> {
                true
            }
            else -> {
                false
            }
        }
    } else {
        when (answerType.RECORD_TABLE_ID.substring(0, 2)) {
            "10", "13", "14", "15", "16", "17", "18", "19" -> {
                true
            }
            else -> {
                false
            }
        }
    }

    val IsChat = when (answerType.RECORD_TABLE_ID.substring(0, 2)) {
        "77", "78", "79" -> {
            true
        }
        else -> {
            false
        }
    }

    val IsMediaContent = IsMusic || IsVideo || IsVoice
}