package com.sycho.rxapps.leanback

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.leanback.widget.OnItemViewClickedListener
import androidx.leanback.widget.OnItemViewSelectedListener
import com.sycho.rxapps.R
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

/**
 * Main gateway activity
 *
 * @author Cho Seong-yong
 * @since 2019.03.19
 */
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainFragment = supportFragmentManager.findFragmentById(R.id.containers) as MainFragment
        mainFragment.setOnItemViewClickedListener(OnItemViewClickedListener { itemViewHolder, item, rowViewHolder, row ->
            launchActivity(item as MainContentData)
        })
        mainFragment.setOnItemViewSelectedListener(OnItemViewSelectedListener { itemViewHolder, item, rowViewHolder, row ->
            if (item is MainContentData) {
                main_content_title.text = item.title
                main_content_description.text = item.description
            }
        })
    }

    private fun launchActivity(item: MainContentData) {
        try {
            val intent = Intent()
            intent.component = ComponentName(packageName, item.component)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
