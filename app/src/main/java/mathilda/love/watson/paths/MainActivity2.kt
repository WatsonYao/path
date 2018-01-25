package mathilda.love.watson.paths

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val drawable = PolygonLapsDrawable()
//        drawable.dotProgress = 100f
//        drawable.progress = 50f
        image.setImageDrawable(drawable)
    }
}
