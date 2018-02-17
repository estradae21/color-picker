package com.ernestoestrada.colorpicker

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony.Mms.Part.FILENAME
import android.support.v7.app.AlertDialog
import android.text.TextUtils.split
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import com.ernestoestrada.colorpicker.R.color.*
import com.ernestoestrada.colorpicker.R.id.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileReader
import java.io.InputStream
import java.nio.charset.Charset
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    var namedColors = ""
    var colors:IntArray = intArrayOf(0,0,0)
    var savedColor = arrayListOf(0,0,0,"")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            updatetxt()
            colorView.setBackgroundColor(Color.rgb(colors[0], colors[1], colors[2]))
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    })}

    private fun updatetxt(){
        redProgress.text = colors[0].toString()
        greenProgress.text = colors[1].toString()
        blueProgress.text = colors[2].toString()
    }

    private fun showColorList(){

    }

    private fun saveColor(str: String) {
        val line = "red, green, blue, name \n"
        val f = File("dataStorage.txt", FILENAME)
        f.appendText(line)
        /*
        try {

            var fos = openFileOutput("dataStorage.txt", Context.MODE_APPEND)
            fos.write(str.toByteArray())
            fos.write("\n". toByteArray())
            fos.close()
        } catch (ex:Exception){
            print(ex.message)}
            */
    }

    private fun pickColor(){
        val fin = File("dataStorage.txt", FILENAME)
        val sc = Scanner(fin)
        var r:Int = -1
        var g:Int = -1
        var b:Int = -1
        var cName = "unnamed"
        var line = ""
       // namedColors.clear()
        while (sc.hasNextInt()){
            r = sc.nextInt()
            if (sc.hasNextInt()){
                g = sc.nextInt()
            }
            if (sc.hasNextInt()){
                b = sc.nextInt()
            }
        }



    }

    fun readValues(){
        try {
            val fos = openFileInput("dataStorage.txt")
            val bufferedReader = fos.bufferedReader()
            val line: String = bufferedReader.readLine()
            Toast.makeText(applicationContext, line,
                    Toast.LENGTH_SHORT).show()
        }
        catch (ex:Exception){
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
                var str = (redProgress.text.toString() + ", " + greenProgress.text.toString()
                        + ", "+ blueProgress.text.toString())
                //writeValues(str)
                Toast.makeText(applicationContext,
                        "Data written to internal storage",
                        Toast.LENGTH_SHORT).show()

                return true}
            R.id.recall_color ->{
                //displayAlert()
                return true}
            else -> super.onOptionsItemSelected(item)
        }
    }
}




