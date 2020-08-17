package marce.projects.adivinhanumero.data

import marce.projects.adivinhanumero.api.NumberAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdivinhaDataSource(private val numberAPI: NumberAPI) :
    NumberDataSource {
    override fun receiveNumber(success: (AdivinhaData) -> Unit, failure: (Int) -> Unit) {

        val call = numberAPI.getRandomNumber(1, 300)

        call.enqueue(object : Callback<AdivinhaData> {
            override fun onResponse(call: Call<AdivinhaData>, response: Response<AdivinhaData>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        success(it)
                    }
                } else {
                    failure(response.code())
                }
            }

            override fun onFailure(call: Call<AdivinhaData>, t: Throwable) {
                failure(0)
            }

        })
    }
}