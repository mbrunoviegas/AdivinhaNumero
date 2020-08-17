package marce.projects.adivinhanumero.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.slider_text_formatter.*
import marce.projects.adivinhanumero.R
import marce.projects.adivinhanumero.addFragmentTo
import marce.projects.adivinhanumero.data.AdivinhaDataSource
import marce.projects.adivinhanumero.data.AdivinhaRepository
import marce.projects.adivinhanumero.api.NumberAPI
import marce.projects.adivinhanumero.extras.CustomDialog
import marce.projects.adivinhanumero.viewmodel.AdivinhaViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import top.defaults.colorpicker.ColorPickerPopup

/**
 * Usei o pattern MVVM para estruturar o projeto
 * A lógica baseia-se em reeber o input do usuário, encaminhá-lo à ViewMoldel para pegar
 * seus digitos utilizando o operador %, então armazenar os digitos em um array, que
 * será enviado para a classe customizada SevenSegmentsCanvas
 * Para o código do display, utilzei a bibliotea de desenho Canvas
 * na classe eu tenho 10 arrays, representos do digito 0 a 9
 * cada array, possui numeros que representam os segmentos de cada digito
 * todos os segmentos sao baseados no segmento 0, que o é centro de toda a criação
 * depois das operações necessárias, chamo o metodo para digitar o numeros
 * que irá analisar o numero atual e verificar qual array devera usar pra desenha o digito
 */

class AdivinhaActivity : AppCompatActivity() {

    private var formatterOption = 1f
    private lateinit var adivinhaFragment: AdivinhaFragment
    private var actualColor = R.color.colorPrimary

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        addFragmentTo(R.id.frameDigits, createFragment())
    }

    private fun createFragment(): AdivinhaFragment {
        adivinhaFragment =
            AdivinhaFragment.newInstance(
                createViewModel()
            )
        return adivinhaFragment
    }

    private fun createViewModel(): AdivinhaViewModel {
        val retrofit =
            Retrofit.Builder().baseUrl("https://us-central1-ss-devops.cloudfunctions.net")
                .addConverterFactory(
                    GsonConverterFactory.create()
                ).build()
        val adivinhaDataSource =
            AdivinhaDataSource(
                retrofit.create(NumberAPI::class.java)
            )
        val repository = AdivinhaRepository(
            adivinhaDataSource
        )
        return AdivinhaViewModel(
            repository
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.formatSize ->
                showCustomDialog()
            R.id.colorPallete ->
                showColorPicker()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showCustomDialog() {
        val dialog = CustomDialog(this@AdivinhaActivity)
        dialog.create()
        dialog.run {
            sliderFormatter.apply {
                value = formatterOption
                addOnChangeListener { _, value, _ ->
                    formatterOption = value
                    adivinhaFragment.setDisplaySize(value.toInt())
                }
            }
        }
        dialog.show()
    }

    private fun showColorPicker() {
        ColorPickerPopup.Builder(this@AdivinhaActivity)
            .initialColor(actualColor)
            .enableBrightness(true)
            .enableAlpha(true)
            .okTitle("Confirmar")
            .cancelTitle("Cancelar")
            .showIndicator(false)
            .showValue(false)
            .build()
            .show(object : ColorPickerPopup.ColorPickerObserver() {
                override fun onColorPicked(color: Int) {
                    actualColor = color
                    adivinhaFragment.setDisplayColor(color)
                }
            })
    }
}
