package com.john.openweatherapp.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.john.openweatherapp.R
import com.john.openweatherapp.data.local.SharedPreferencesManager
import com.john.openweatherapp.databinding.ActivityLocationDeniedBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A UI class that renders UI for granting the Location permissions
 *
 */
@AndroidEntryPoint
class LocationDeniedActivity : AppCompatActivity(), LocationDeniedCallback {
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var binding: ActivityLocationDeniedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_location_denied
        )
        binding.callback = this
        setUpToolBar()
        sharedPreferencesManager = SharedPreferencesManager(this)
    }

    private fun setUpToolBar() {
        setSupportActionBar(binding.toolbar)
        title = getString(R.string.location_access_denied)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.location_denied_screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.exit_option) {
            val returnIntent = Intent()
            setResult(RESULT_OK, returnIntent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onOpenSettingButtonClick() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onCancelButtonClick() {
        val returnIntent = Intent()
        setResult(RESULT_OK, returnIntent)
        finish()
    }

    override fun onBackPressed() {
        setResult(RESULT_OK)
        super.onBackPressed()
    }
}