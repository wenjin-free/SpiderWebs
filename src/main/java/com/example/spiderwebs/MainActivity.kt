package com.example.spiderwebs

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val maxValue = 300
    var beans = ArrayList<PathBean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        beans.add(PathBean("物理攻击", maxValue, 100))
        beans.add(PathBean("物理防御", maxValue, 10))
        beans.add(PathBean("魔法攻击", maxValue, 80))
        beans.add(PathBean("魔法防御", maxValue, 200))
        beans.add(PathBean("物理穿透", maxValue, 150))
        beans.add(PathBean("魔法穿透", maxValue, 190))
        spider_view.setData(beans)
    }
}
