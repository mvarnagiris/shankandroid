package memoizrlabs.com.shankandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import life.shank.ShankModule
import life.shank.android.AutoScoped
import life.shank.scoped
import memoizrlabs.com.shankandroid.MainModule.foo
import java.util.*


class FirstActivity : AppCompatActivity(), AutoScoped {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        foo() {
            println("$this ${it.yo}")
        }

        launchself.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
        println("fragment being attached")
    }
}

class SecondActivity : AppCompatActivity(), AutoScoped {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        foo {
            println("$this getting presenter instance: ${it.yo}")
        }

        launchself.setOnClickListener {
            startActivity(Intent(this, FirstActivity::class.java))
        }
    }
}


object MainModule : ShankModule {
    val foo = scoped { -> MyObject() }
}

class MyObject() {
    val yo = Random().nextInt(100)

    init {
        println("creating presenter instance: $yo")
    }
}

