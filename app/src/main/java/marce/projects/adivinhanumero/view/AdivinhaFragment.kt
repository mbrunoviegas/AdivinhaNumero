package marce.projects.adivinhanumero.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_adivinha.*
import marce.projects.adivinhanumero.R
import marce.projects.adivinhanumero.viewmodel.AdivinhaViewModel


class AdivinhaFragment : Fragment() {

    private lateinit var viewModel: AdivinhaViewModel
    private var guessNumber = 0

    companion object {
        @JvmStatic
        fun newInstance(viewModel: AdivinhaViewModel): AdivinhaFragment {
            val fragment = AdivinhaFragment()
            fragment.viewModel = viewModel
            return fragment
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.load()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_adivinha, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupFragment()
    }

    private fun setupFragment() {
        edtNumber.addTextChangedListener { text ->
            setSizeCount(text.toString().length)
        }

        btnEnviar.setOnClickListener {
            val text = edtNumber.text.toString()
            if (text != "") {
                viewModel.prepareNumber(text.toInt())
                viewModel.compareNumber(text.toInt())
            }
        }

        btnNovaPartidade.setOnClickListener {
            hideNewGameButton()
            viewModel.load()
            viewModel.prepareNumber(0)
            enableButton()
            hideAnswer()
        }

        val observerNumber = Observer<Int> {
            if (it > 300) {
                disableButton()
                viewModel.prepareNumber(it)
                showMessage("Erro")
                showNewGameButton()
            } else {
                guessNumber = it
                Log.i("guessNumber", " $guessNumber")
            }
        }
        viewModel.number.observe(viewLifecycleOwner, observerNumber)

        val observerDisplayDigits = Observer<ArrayList<Int>> { displayDigits ->
            showDigits(displayDigits)
        }
        viewModel.displayDigits.observe(viewLifecycleOwner, observerDisplayDigits)

        val observerAnswer = Observer<String> { answer ->
            showMessage(answer)
        }
        viewModel.message.observe(viewLifecycleOwner, observerAnswer)

        val observerSucess = Observer<Boolean> { success ->
            if (success) {
                showNewGameButton()
                disableButton()
                showNewGameButton()
            }
        }
        viewModel.success.observe(viewLifecycleOwner, observerSucess)
    }

    private fun showDigits(displayDigits: ArrayList<Int>) {
        when (displayDigits.size) {
            3 -> {
                digit1.setDigit(displayDigits[2])
                digit2.setDigit(displayDigits[1])
                digit3.setDigit(displayDigits[0])
                digit2.visibility = View.VISIBLE
                digit3.visibility = View.VISIBLE
            }
            2 -> {
                digit1.setDigit(displayDigits[1])
                digit2.setDigit(displayDigits[0])
                digit2.visibility = View.VISIBLE
                digit3.visibility = View.GONE
            }
            1 -> {
                digit1.setDigit(displayDigits[0])
                digit2.visibility = View.GONE
                digit3.visibility = View.GONE
            }
        }
    }

    private fun setSizeCount(size: Int) {
        val text = "$size/3"
        txtNumSize.text = text
    }

    private fun showMessage(message: String) {
        txtAnswer.visibility = View.VISIBLE
        txtAnswer.text = message
    }

    private fun disableButton() {
        btnEnviar.isEnabled = false
    }

    private fun enableButton() {
        btnEnviar.isEnabled = true
    }

    private fun hideAnswer() {
        txtAnswer.visibility = View.INVISIBLE
    }

    private fun showNewGameButton() {
        btnNovaPartidade.visibility = View.VISIBLE
    }

    private fun hideNewGameButton() {
        btnNovaPartidade.visibility = View.INVISIBLE
    }

    fun setDisplaySize(size: Int) {
        val layoutParamsDigit1 = digit1.layoutParams as ConstraintLayout.LayoutParams
        val layoutParamsDigit2 = digit2.layoutParams as ConstraintLayout.LayoutParams
        val layoutParamsDigit3 = digit3.layoutParams as ConstraintLayout.LayoutParams

        when (size) {
            1 -> {
                layoutParamsDigit1.matchConstraintPercentWidth = 0.2f
                layoutParamsDigit2.matchConstraintPercentWidth = 0.2f
                layoutParamsDigit3.matchConstraintPercentWidth = 0.2f
            }
            2 -> {
                layoutParamsDigit1.matchConstraintPercentWidth = 0.22f
                layoutParamsDigit2.matchConstraintPercentWidth = 0.22f
                layoutParamsDigit3.matchConstraintPercentWidth = 0.22f
            }
            3 -> {
                layoutParamsDigit1.matchConstraintPercentWidth = 0.24f
                layoutParamsDigit2.matchConstraintPercentWidth = 0.24f
                layoutParamsDigit3.matchConstraintPercentWidth = 0.24f
            }
            4 -> {
                layoutParamsDigit1.matchConstraintPercentWidth = 0.26f
                layoutParamsDigit2.matchConstraintPercentWidth = 0.26f
                layoutParamsDigit3.matchConstraintPercentWidth = 0.26f
            }
            5 -> {
                layoutParamsDigit1.matchConstraintPercentWidth = 0.28f
                layoutParamsDigit2.matchConstraintPercentWidth = 0.28f
                layoutParamsDigit3.matchConstraintPercentWidth = 0.28f
            }
        }

        digit1.requestLayout()
        digit2.requestLayout()
        digit3.requestLayout()
    }

    fun setDisplayColor(color: Int) {
        digit1.setDigitColor(color)
        digit2.setDigitColor(color)
        digit3.setDigitColor(color)
    }
}
