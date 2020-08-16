package marce.projects.adivinhanumero.extras

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import marce.projects.adivinhanumero.R

class CustomDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val customLayout = layoutInflater.inflate(R.layout.slider_text_formatter, null)
        setContentView(customLayout)
    }
}