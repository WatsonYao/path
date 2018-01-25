package mathilda.love.watson.paths

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var handler = Handler()
    lateinit var runnable: Runnable
    var progressInt:Float = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        seekBar2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                progress.text = "progress:$p1"

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        runnable = object : Runnable {
            override fun run() {
                // TODO Auto-generated method stub
                //要做的事情，这里再次调用此Runnable对象，以实现每两秒实现一次的定时器操作
                progressInt += 0.1f
                handler.postDelayed(this, 30)
                runOnUiThread {
                    pathView.setProgress(progressInt)
                    progress.text = "progress:$progressInt"
                }
            }
        }

        start.setOnClickListener {
            handler.postDelayed(runnable, 100)
        }

        show.setOnClickListener {
            pathView.setShowBG(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable);
    }


}
