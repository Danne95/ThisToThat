package com.fromepicbrain.thistothat

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.io.InputStream
import java.lang.NumberFormatException

private var popupWindow: PopupWindow? = null
private var relativeLayouts = mutableListOf<RelativeLayout>()
private var values_matrix = mutableListOf<MutableList<String>>()

class Fragment2 : Fragment() {
    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inputStream: InputStream = context?.assets?.open("values.csv")as InputStream
        val lines: List<String> = inputStream.bufferedReader().readLines()
        val inflater = LayoutInflater.from(context)
        val parentLayout = view?.findViewById<LinearLayout>(R.id.frag2_linearLayout)

        lines.forEachIndexed {index, line ->
            if(index > 0) {
                val fields = line.split(",").toMutableList()
                val relativeLayout =
                    inflater.inflate(R.layout.list_item_template, null) as RelativeLayout
                val starButton =
                    relativeLayout.findViewById<ImageButton>(R.id.template1_imageButton)
                val textButton = relativeLayout.findViewById<Button>(R.id.template1_button)

                relativeLayout.id = fields[1].toInt()
                starButton.id = fields[1].toInt()
                textButton.id = fields[1].toInt()

                // Remove the view from its current parent, if any
                val parentViewGroup = relativeLayout.parent as? ViewGroup
                parentViewGroup?.removeView(relativeLayout)

                // Add the newly created RelativeLayout object to the relativeLayouts list
                relativeLayouts.add(relativeLayout)
                values_matrix.add(fields)
                parentLayout?.addView(relativeLayout)

                // set star statues from values file
                /*when (fields[0].toInt()) {
                    1 -> starButton.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            android.R.drawable.btn_star_big_on
                        )
                    )

                    else -> starButton.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            android.R.drawable.btn_star_big_off
                        )
                    )
                }*/

                val title = fields[2] + " to " + fields[3]
                textButton.text = title
                // Set the icon drawable at the end of the button text
                val iconImage: Drawable? = conversionClassIcon(fields[6])
                textButton.setCompoundDrawablesWithIntrinsicBounds(null, null, iconImage, null)

                // set listeners
                starButton.setOnClickListener {
                    starButtonInteraction(starButton)
                }
                textButton.setOnClickListener {
                    textButtonInteraction(textButton)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_page2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // add each row created to parent LinearLayout
        val parentLayout = view.findViewById<LinearLayout>(R.id.frag2_linearLayout)
        relativeLayouts.forEach { relativeLayout ->
            parentLayout.addView(relativeLayout)
        }
    }

    // handles popup window exit
    override fun onDestroyView() {
        super.onDestroyView()
        popupWindow?.dismiss()
        popupWindow = null
    }

    private fun conversionClassIcon(conversionClass: String): Drawable? {
        return when (conversionClass){
            "acceleration" -> ContextCompat.getDrawable(requireContext(), R.drawable.acceleration)
            "area" -> ContextCompat.getDrawable(requireContext(), R.drawable.area)
            "length" -> ContextCompat.getDrawable(requireContext(), R.drawable.length)
            "mass" -> ContextCompat.getDrawable(requireContext(), R.drawable.mass)
            "pressure" -> ContextCompat.getDrawable(requireContext(), R.drawable.pressure)
            "speed" -> ContextCompat.getDrawable(requireContext(), R.drawable.speed)
            "temperature" -> ContextCompat.getDrawable(requireContext(), R.drawable.temperature)
            "volume" -> ContextCompat.getDrawable(requireContext(), R.drawable.volume)
            else -> null
        }
    }

    private fun starButtonInteraction(starButton: ImageButton){
        when(values_matrix[starButton.id][1].toInt()){
            // favorited
            1 -> {
                // change to unckecked (hollow star)
                starButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), android.R.drawable.btn_star_big_off))
                // change value matrix to unfavorited
                values_matrix[starButton.id][1] = 0.toString()
                // change data file to unfavorited
                //val newLine = values_matrix[starButton.id].joinToString(",")
            }
            else -> {
                starButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), android.R.drawable.btn_star_big_on))
                values_matrix[starButton.id][1] = 1.toString()
                //val newLine = values_matrix[starButton.id].joinToString(",")
            }
        }

    }

    @SuppressLint("InflateParams")
    fun textButtonInteraction(textButton: Button){
        val inflater = LayoutInflater.from(requireContext())
        val popupView = inflater.inflate(R.layout.popup_layout, null)

        // get all views
        val firstConversionTitle = popupView.findViewById<TextView>(R.id.convertTitle1_1)
        val firstConversionRatio = popupView.findViewById<TextView>(R.id.convertTitle1_2)
        val firstConversionInput = popupView.findViewById<EditText>(R.id.convertInput1)
        val firstConversionButton = popupView.findViewById<ImageButton>(R.id.convertButton1)
        val firstConversionOutput = popupView.findViewById<TextView>(R.id.convertOutput1)

        val secondConversionTitle = popupView.findViewById<TextView>(R.id.convertTitle2_1)
        val secondConversionRatio = popupView.findViewById<TextView>(R.id.convertTitle2_2)
        val secondConversionInput = popupView.findViewById<EditText>(R.id.convertInput2)
        val secondConversionButton = popupView.findViewById<ImageButton>(R.id.convertButton2)
        val secondConversionOutput = popupView.findViewById<TextView>(R.id.convertOutput2)

        when(textButton.id) {
            // Celsius,Fahrenheit
            28 -> {
                // set widget titles from values_matrix
                var temp =
                    values_matrix[textButton.id][2] + " --> " + values_matrix[textButton.id][3]
                firstConversionTitle.text = temp
                temp = " (x * 9/5) + 32 "
                firstConversionRatio.text = temp

                temp = values_matrix[textButton.id][3] + " --> " + values_matrix[textButton.id][2]
                secondConversionTitle.text = temp
                temp = " (x - 32) * 5/9 "
                secondConversionRatio.text = temp

                // Set up any interactions or listeners
                firstConversionButton.setOnClickListener {
                    try {
                        val inputText = firstConversionInput.text.toString()
                        val inputVal =
                            (inputText.toFloat() * 9 / 5) + 32
                        firstConversionOutput.text = inputVal.toString()
                    } catch (e: NumberFormatException) {
                        Toast.makeText(
                            requireContext(),
                            "Please input a number!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                secondConversionButton.setOnClickListener {
                    try {
                        val inputText = secondConversionInput.text.toString()
                        val inputVal =
                            (inputText.toFloat() - 32) * 5 / 9
                        secondConversionOutput.text = inputVal.toString()
                    } catch (e: NumberFormatException) {
                        Toast.makeText(
                            requireContext(),
                            "Please input a number!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }

            // Celsius,Kelvin
            29 -> {
                // set widget titles from values_matrix
                var temp =
                    values_matrix[textButton.id][2] + " --> " + values_matrix[textButton.id][3]
                firstConversionTitle.text = temp
                temp = " x + 273.15 "
                firstConversionRatio.text = temp

                temp = values_matrix[textButton.id][3] + " --> " + values_matrix[textButton.id][2]
                secondConversionTitle.text = temp
                temp =" x - 273.15 "
                secondConversionRatio.text = temp

                // Set up any interactions or listeners
                firstConversionButton.setOnClickListener {
                    try {
                        val inputText = firstConversionInput.text.toString()
                        val inputVal =
                            inputText.toFloat() + 273.15
                        firstConversionOutput.text = inputVal.toString()
                    } catch (e: NumberFormatException) {
                        Toast.makeText(
                            requireContext(),
                            "Please input a number!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                secondConversionButton.setOnClickListener {
                    try {
                        val inputText = secondConversionInput.text.toString()
                        val inputVal =
                            inputText.toFloat() -273.15
                        secondConversionOutput.text = inputVal.toString()
                    } catch (e: NumberFormatException) {
                        Toast.makeText(
                            requireContext(),
                            "Please input a number!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }

            // Kelvin,Fahrenheit
            30 -> {
                // set widget titles from values_matrix
                var temp =
                    values_matrix[textButton.id][2] + " --> " + values_matrix[textButton.id][3]
                firstConversionTitle.text = temp
                temp = " (x - 273.15) * 9/5 + 32 "
                firstConversionRatio.text = temp

                temp = values_matrix[textButton.id][3] + " --> " + values_matrix[textButton.id][2]
                secondConversionTitle.text = temp
                temp = " (x - 32) * 5/9 + 273.15 "
                secondConversionRatio.text = temp

                // Set up any interactions or listeners
                firstConversionButton.setOnClickListener {
                    try {
                        val inputText = firstConversionInput.text.toString()
                        val inputVal =
                            (inputText.toFloat() - 273.15) * 9 / 5 + 32
                        firstConversionOutput.text = inputVal.toString()
                    } catch (e: NumberFormatException) {
                        Toast.makeText(
                            requireContext(),
                            "Please input a number!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                secondConversionButton.setOnClickListener {
                    try {
                        val inputText = secondConversionInput.text.toString()
                        val inputVal =
                            (inputText.toFloat() - 32) * 5 / 9 + 273.15
                        secondConversionOutput.text = inputVal.toString()
                    } catch (e: NumberFormatException) {
                        Toast.makeText(
                            requireContext(),
                            "Please input a number!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }

            // simple multiplication
            else -> {
                // set widget titles from values_matrix
                var temp =
                    values_matrix[textButton.id][2] + " --> " + values_matrix[textButton.id][3]
                firstConversionTitle.text = temp
                temp = "x * " + values_matrix[textButton.id][4]
                firstConversionRatio.text = temp

                temp = values_matrix[textButton.id][3] + " --> " + values_matrix[textButton.id][2]
                secondConversionTitle.text = temp
                temp = "x * " + values_matrix[textButton.id][5]
                secondConversionRatio.text = temp

                // Set up any interactions or listeners
                firstConversionButton.setOnClickListener {
                    try {
                        val inputText = firstConversionInput.text.toString()
                        val inputVal =
                            inputText.toFloat() * values_matrix[textButton.id][4].toFloat()
                        firstConversionOutput.text = inputVal.toString()
                    } catch (e: NumberFormatException) {
                        Toast.makeText(
                            requireContext(),
                            "Please input a number!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                secondConversionButton.setOnClickListener {
                    try {
                        val inputText = secondConversionInput.text.toString()
                        val inputVal =
                            inputText.toFloat() * values_matrix[textButton.id][5].toFloat()
                        secondConversionOutput.text = inputVal.toString()
                    } catch (e: NumberFormatException) {
                        Toast.makeText(
                            requireContext(),
                            "Please input a number!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        }
        // Create the PopupWindow
        popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)

        popupWindow?.showAtLocation(popupView, Gravity.CENTER and Gravity.TOP, 0, 0)
    }
}