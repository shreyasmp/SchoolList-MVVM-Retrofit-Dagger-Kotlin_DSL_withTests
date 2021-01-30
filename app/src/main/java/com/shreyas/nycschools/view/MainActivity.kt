package com.shreyas.nycschools.view

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shreyas.nycschools.R
import com.shreyas.nycschools.databinding.ActivityMainBinding
import com.shreyas.nycschools.model.School
import com.shreyas.nycschools.utils.Constants.SCHOOL_DETAILS
import dagger.android.AndroidInjection

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, SchoolListFragment(), SchoolListFragment.TAG).commit()
        }
    }

    fun showSchoolDetailFragment(school: School) {
        val schoolDetailsFragment = SchoolDetailsFragment()
        val bundle = Bundle()
        bundle.putSerializable(SCHOOL_DETAILS, school)
        schoolDetailsFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .addToBackStack("school")
            .replace(R.id.fragment_container, schoolDetailsFragment, SchoolDetailsFragment.TAG)
            .commit()
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            Log.i(TAG, "Popping BackStack")
            fragmentManager.popBackStackImmediate()
        } else {
            Log.i(TAG, "Nothing on stack, Calling Super")
            super.onBackPressed()
        }
    }

    // Enables Toolbar back
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}