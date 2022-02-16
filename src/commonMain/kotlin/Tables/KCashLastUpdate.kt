package Tables

import p_jsocket.myConnectionsID

class KCashLastUpdate {

    var CASH_SUM: Long = 0
    var LAST_USE: Long = 0
    var COUNT_OF_ROWS: Int = 0
    var CONNECTION_ID: Long = 0

    private constructor()

    constructor(L_CASH_SUM: Long,
                L_LAST_USE: Long,
                L_COUNT_OF_ROWS: Int){
        CASH_SUM = L_CASH_SUM
        LAST_USE = L_LAST_USE
        COUNT_OF_ROWS = L_COUNT_OF_ROWS
        CONNECTION_ID = myConnectionsID.value
    }
}