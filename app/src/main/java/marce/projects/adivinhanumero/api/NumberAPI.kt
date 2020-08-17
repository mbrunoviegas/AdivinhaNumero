package marce.projects.adivinhanumero.api

import marce.projects.adivinhanumero.data.AdivinhaData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NumberAPI {

    @GET("/rand")
    fun getRandomNumber(
        @Query("min") min: Int,
        @Query("max") max: Int
    ): Call<AdivinhaData>

}