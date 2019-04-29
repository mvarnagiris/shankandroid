package memoizrlabs.com.shankandroid

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import life.shank.ShankModule
import life.shank.scoped
import memoizrlabs.com.shankandroid.MainModule.foo
import life.shank.android.AutoScoped
import java.util.*


class FirstActivity : AppCompatActivity(), AutoScoped {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("$this ${foo().yo}")

        launchself.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java).nestedScopeExtra())
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

        println("$this getting presenter instance: ${foo().yo}")

        launchself.setOnClickListener {
            startActivity(Intent(this, FirstActivity::class.java).nestedScopeExtra())
        }
    }
}

class CustomFragment : Fragment(), AutoScoped {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("creating custom fragment")

//        println("$this getting presenter instance: ${foo().yo}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragmentcontent, container)
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

