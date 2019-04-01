package com.sycho.rxapps.leanback

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.leanback.widget.OnItemViewClickedListener
import com.sycho.rxapps.Constants
import com.sycho.rxapps.R
import com.sycho.rxapps.fragments.*

class SubActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        val subFragment = supportFragmentManager.findFragmentById(R.id.sub_containers) as SubFragment
        subFragment.setOnItemViewClickedListener(OnItemViewClickedListener { itemViewHolder, item, rowViewHolder, row ->
            if (item is SubContentData) {
                replaceFragment(item.id)
            }
        })
    }

    private fun replaceFragment(type: String) {
        var fragment: Fragment? = null
        when (type) {
            Constants.SUB_FRAGMENT_ID_ON_CLICK -> {
                fragment = OnClickFragment()
            }
            Constants.SUB_FRAGMENT_ID_DEBOUNCE_SEARCH -> {
                fragment = DebounceSearchFragment()
            }
            Constants.SUB_FRAGMENT_ID_RECYCLER_VIEW -> {
                fragment = RecyclerViewFragment()
            }
            Constants.SUB_FRAGMENT_ID_POLLING -> {
                fragment = PollingFragment()
            }
            Constants.SUB_FRAGMENT_ID_OKHTTP -> {
                fragment = OkHttpFragment()
            }
        }
        fragment?.let {
            supportFragmentManager.beginTransaction().replace(R.id.sub_contents, fragment).commit()
        }
    }
}
