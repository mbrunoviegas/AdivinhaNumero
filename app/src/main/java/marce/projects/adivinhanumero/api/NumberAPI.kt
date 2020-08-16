package marce.projects.adivinhanumero.api

import marce.projects.adivinhanumero.data.AdivinhaData
import retrofit2.Call
import retrofit2.http.GET

interface NumberAPI {

    @GET("/rand?min=1&max=300")
    fun getRandomNumber(): Call<AdivinhaData>

}