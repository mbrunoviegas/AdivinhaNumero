package marce.projects.adivinhanumero.data

import marce.projects.adivinhanumero.data.AdivinhaData

interface NumberDataSource {

    fun receiveNumber(success: (AdivinhaData) -> Unit, failure: (statusCode : Int) -> Unit)
}