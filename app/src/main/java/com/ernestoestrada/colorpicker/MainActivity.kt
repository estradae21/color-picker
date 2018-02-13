package com.ernestoestrada.colorpicker

import android.content.ClipData
import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import com.ernestoestrada.colorpicker.R.color.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.main.*
import java.io.FileOutputStream
import java.io.File
import android.content.Context.MODE_PRIVATE
import java.io.IOException
import java.io.OutputStreamWriter


class MainActivity : AppCompatActivity() {

    private val FILE_NAME = "storagefile.txt"

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
                var message = ""
                message = redProgress.getText().toString() + greenProgress.getText().toString() +
                        blueProgress.getText().toString()
                val fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
                val outputWriter = OutputStreamWriter(fos)
                outputWriter.write(message)
                outputWriter.close()
                Toast.makeText(applicationContext,
                        "Data written to internal storage",
                        Toast.LENGTH_SHORT).show()

                // editTextContent.setText("")

                //Toast.makeText(applicationContext, "Your color has been saved",
                  //      Toast.LENGTH_SHORT).show()

                return true}
            R.id.recall_color ->{true}
            else -> super.onOptionsItemSelected(item)
        }
    }
}




