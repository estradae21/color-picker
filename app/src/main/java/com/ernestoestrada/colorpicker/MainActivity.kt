package com.ernestoestrada.colorpicker

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.split
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.Toast
import com.ernestoestrada.colorpicker.R.id.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileReader
import java.io.InputStream
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var red = 0
        var green = 0
        var blue = 0

        var actionBar = getSupportActionBar()
        actionBar!!.setDisplayShowHomeEnabled(true)
        actionBar.setIcon(R.mipmap.ic_launcher)
        actionBar.setDisplayShowTitleEnabled(false)

        colorView.setBackgroundColor(Color.rgb(red, green, blue))

        redSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
           override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
               red = progress
               redProgress.text = progress.toString()
               colorView.setBackgroundColor(Color.rgb(red, green, blue))
           }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
           override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        greenSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                green = progress
                greenProgress.text = progress.toString()
                colorView.setBackgroundColor(Color.rgb(red, green, blue))
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        blueSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                blue = progress
                blueProgress.text = progress.toString()
                colorView.setBackgroundColor(Color.rgb(red, green, blue))
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {} })

    }

    fun writeValues(str: String) {
        try {
            var fos = openFileOutput("dataStorage.txt", Context.MODE_APPEND)
            fos.write(str.toByteArray())
            fos.write("\n". toByteArray())
            fos.close()
        } catch (ex:Exception){
            print(ex.message)}
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
                writeValues(str)
                Toast.makeText(applicationContext,
                        "Data written to internal storage",
                        Toast.LENGTH_SHORT).show()

                return true}
            R.id.recall_color ->{
                readValues()
                return true}

            else -> super.onOptionsItemSelected(item)
        }
    }
}




