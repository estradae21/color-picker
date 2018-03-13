package com.ernestoestrada.colorpicker

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony.Mms.Part.FILENAME
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.selector
import org.jetbrains.anko.toast
import java.io.File
import java.io.OutputStreamWriter
import java.util.*


class MainActivity : AppCompatActivity() {
    var namedColors  = arrayListOf<CharSequence>()
    var colors:IntArray = intArrayOf(0,0,0)
    var color1: IntArray = intArrayOf(0,0,0)
    var color2: IntArray = intArrayOf(0,0,0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val info = intent.extras
        if (info != null) {

            Color1.visibility = View.VISIBLE
            Color2.visibility = View.VISIBLE
            returnColor.visibility = View.VISIBLE

            Color1.setOnClickListener {
                color1[0] = colors[0]
                color1[1] = colors[1]
                color1[2] = colors[2]
                Log.i("Color 1", "${color1[0]}, ${color1[1]}, ${color1[2]}")
            }

            Color2.setOnClickListener{
                color2[0] = colors[0]
                color2[1] = colors[1]
                color2[2] = colors[2]
                Log.i("Color 2", "${color2[0]}, ${color2[1]}, ${color2[2]}")
            }
            returnColor.setOnClickListener {
                finish()
            }
        }

        var actionBar = getSupportActionBar()
        actionBar!!.setDisplayShowHomeEnabled(true)
        actionBar.setIcon(R.mipmap.ic_launcher)
        actionBar.setDisplayShowTitleEnabled(false)

        setupSeekBarListener(redSeekBar, 0)
        setupSeekBarListener(greenSeekBar, 1)
        setupSeekBarListener(blueSeekBar,2)


    }

    private fun setupSeekBarListener(seekBar: SeekBar, idx:Int){
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                colors[idx] = progress
                seekBarProgress()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })}

    private fun seekBarProgress(){
        redProgress.text = colors[0].toString()
        greenProgress.text = colors[1].toString()
        blueProgress.text = colors[2].toString()
        colorView.setBackgroundColor(Color.rgb(colors[0], colors[1], colors[2]))
    }

    private fun saveAlertbox(){
        val f = openFileOutput("dataStorage.txt", Context.MODE_APPEND)
        val fOut =  OutputStreamWriter(f)
        var colorName: EditText = EditText(this)
        var str:String = colors[0].toString() + " " + colors[1].toString() + " " +
                colors[2].toString()
        val simpleAlert = AlertDialog.Builder(this@MainActivity).create()
        simpleAlert.setTitle("Save Color")
        simpleAlert.setMessage(str)
        simpleAlert.setView(colorName)

        simpleAlert.setButton(AlertDialog.BUTTON_POSITIVE, "Save", {
            dialogInterface, i ->
            var result = colorName.text.toString()
            val line = "${colors[0]} ${colors[1]} ${colors[2]} $result\n"
            fOut.append(line)
            fOut.close()
            println(result)
            Toast.makeText(applicationContext, "Saved to internal Storage",
                    Toast.LENGTH_SHORT).show()
        })

        simpleAlert.show()
    }

    private fun showColorList() {
        readValues()
        selector("Saved Colors",namedColors, {dialogInterface, i ->
            pickColor(i)
            var name = namedColors[i].split( " ")
            toast("Your color is ${name[3]}!") })
    }


    private fun pickColor(i: Int) {
        val choosen = namedColors[i].split(" ")
        var r:Int = choosen[0].toInt()
        var g:Int = choosen[1].toInt()
        var b:Int = choosen[2].toInt()
        colors[0] = r
        colors[1] = g
        colors[2] = b
        redSeekBar.progress = r
        greenSeekBar.progress = g
        blueSeekBar.progress = b
        seekBarProgress()
    }

    fun readValues() {
        try {
            val f = openFileInput("dataStorage.txt")
            val br = f.bufferedReader()
            namedColors.clear()
            br.forEachLine {
                namedColors.add(it)
            }
            f.close()
        } catch (ex:Exception){
            print(ex.message)}
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_help -> {

                Toast.makeText(applicationContext, "You dont need help",
                        Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.save_color -> {
                saveAlertbox()

                return true}
            R.id.recall_color ->{
                showColorList()
                return true}
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun finish() {
        val data = Intent()
        data.putExtra("color_1", "${color1[0]} ${color1[1]} ${color1[2]} ")
        data.putExtra("color_2","${color2[0]} ${color2[1]} ${color2[2]}")
        setResult(RESULT_OK, data)
        Log.i("Color 1", "${color1[0]}, ${color1[1]}, ${color1[2]}")
        Log.i("Color 2", "${color2[0]}, ${color2[1]}, ${color2[2]}")
        super.finish()
    }
}




