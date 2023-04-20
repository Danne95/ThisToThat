package com.example.thistothat

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.io.InputStream

private var relativeLayouts = mutableListOf<RelativeLayout>()
private var favorite_status = mutableListOf<Int>()

class Fragment2 : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        readValues()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    @SuppressLint("InflateParams")
    private fun readValues(){
        val inputStream: InputStream = resources.openRawResource(R.raw.values)
        val lines: List<String> = inputStream.bufferedReader().readLines()
        val inflater = LayoutInflater.from(context)
        val parentLayout = view?.findViewById<LinearLayout>(R.id.frag2_linearLayout)

        lines.forEach {line ->
            val fields = line.split(",")
            val relativeLayout = inflater.inflate(R.layout.list_item_template, null) as RelativeLayout
            val starButton = relativeLayout.findViewById<ImageButton>(R.id.template1_imageButton)
            val textButton = relativeLayout.findViewById<Button>(R.id.template1_button)

            relativeLayout.id = fields[1].toInt()
            starButton.id = fields[1].toInt()
            textButton.id = fields[1].toInt()

            // Add the newly created RelativeLayout object to the relativeLayouts list
            relativeLayouts.add(relativeLayout)
            favorite_status.add(fields[0].toInt())
            parentLayout?.addView(relativeLayout)

            // set buttons from values file
            when(fields[0].toInt()){
                1 -> starButton.setImageDrawable(ContextCompat.getDrawable(requireContext(),android.R.drawable.btn_star_big_on))
                else -> starButton.setImageDrawable(ContextCompat.getDrawable(requireContext(),android.R.drawable.btn_star_big_off))
            }
            val title = fields[2] + " to " + fields[3]
            textButton.text = title

            // setting listeners
            starButton.setOnClickListener{
                Toast.makeText(requireContext(), "You clicked me, star ID: ${starButton.id}", Toast.LENGTH_SHORT).show()
            }
            textButton.setOnClickListener{
                Toast.makeText(requireContext(), "You clicked me, text ID: ${textButton.id}", Toast.LENGTH_SHORT).show()
            }
       }
    }
}