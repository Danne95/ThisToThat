package com.example.thistothat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import java.io.InputStream

private var readyRows = mutableListOf<RowClass>()

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

    private fun readValues(){
        val inputStream: InputStream = resources.openRawResource(R.raw.values)
        val lines: List<String> = inputStream.bufferedReader().readLines()
        lines.forEach {
            val fields = it.split(",")
            // [a, b, a2b, b2a] ver1(4)
            readyRows.add(
                RowClass(
                    fields[0],
                    fields[1],
                    fields[2].toFloat(),
                    fields[3].toFloat()
                )
            )
        }
    }
}