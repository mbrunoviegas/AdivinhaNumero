package marce.projects.adivinhanumero.data

import marce.projects.adivinhanumero.data.AdivinhaData
import marce.projects.adivinhanumero.data.AdivinhaDataSource
import marce.projects.adivinhanumero.data.NumberDataSource

class AdivinhaRepository(private val adivinhaDataSource: AdivinhaDataSource) :
    NumberDataSource {
    override fun receiveNumber(
        success: (AdivinhaData) -> Unit,
        failure: (statusCode: Int) -> Unit
    ) {
        adivinhaDataSource.receiveNumber(success, failure)
    }

}