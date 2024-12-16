package com.mckimquyen.barcodescanner.feature.tabs

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.applovin.mediation.ads.MaxAdView
import com.mckimquyen.barcodescanner.BuildConfig
import com.mckimquyen.barcodescanner.R
import com.mckimquyen.barcodescanner.extension.applySystemWindowInsets
import com.mckimquyen.barcodescanner.feature.ActivityBase
import com.mckimquyen.barcodescanner.feature.tabs.create.FragmentCreateBarcode
import com.mckimquyen.barcodescanner.feature.tabs.history.FragmentBarcodeHistory
import com.mckimquyen.barcodescanner.feature.tabs.scan.FragmentScanBarcodeFromCamera
import com.mckimquyen.barcodescanner.feature.tabs.setting.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mckimquyen.barcodescanner.extension.ext.createAdBanner
import com.mckimquyen.barcodescanner.extension.ext.destroyAdBanner
import com.mckimquyen.barcodescanner.feature.rateAppInApp
import kotlinx.android.synthetic.main.a_bottom_tabs.*

class ActivityBottomTabs : ActivityBase(), BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {
        private const val ACTION_CREATE_BARCODE = "${BuildConfig.APPLICATION_ID}.CREATE_BARCODE"
        private const val ACTION_HISTORY = "${BuildConfig.APPLICATION_ID}.HISTORY"
    }

    private var adView: MaxAdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_bottom_tabs)

        supportEdgeToEdge()
        initBottomNavigationView()

        if (savedInstanceState == null) {
            showInitialFragment()
        }
        adView = this.createAdBanner(
            logTag = ActivityBottomTabs::class.simpleName,
            viewGroup = flAd,
            isAdaptiveBanner = true,
        )
    }

    override fun onDestroy() {
        flAd.destroyAdBanner(adView)
        super.onDestroy()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == bottomNavigationView.selectedItemId) {
            return false
        }
        showFragment(item.itemId)
        Log.d("roy93~", "onNavigationItemSelected");
        rateAppInApp(BuildConfig.DEBUG)
        return true
    }

    private var doubleBackToExitPressedOnce = false

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (bottomNavigationView.selectedItemId == R.id.itemScan) {
//            super.onBackPressed()
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        } else {
            bottomNavigationView.selectedItemId = R.id.itemScan
        }
    }

    private fun supportEdgeToEdge() {
        bottomNavigationView.applySystemWindowInsets(applyBottom = true)
    }

    private fun initBottomNavigationView() {
        bottomNavigationView.apply {
            setOnNavigationItemSelectedListener(this@ActivityBottomTabs)
        }
    }

    private fun showInitialFragment() {
        when (intent?.action) {
            ACTION_CREATE_BARCODE -> bottomNavigationView.selectedItemId = R.id.itemCreate
            ACTION_HISTORY -> bottomNavigationView.selectedItemId = R.id.itemHistory
            else -> showFragment(R.id.itemScan)
        }
    }

    private fun showFragment(bottomItemId: Int) {
        val fragment = when (bottomItemId) {
            R.id.itemScan -> FragmentScanBarcodeFromCamera()
            R.id.itemCreate -> FragmentCreateBarcode()
            R.id.itemHistory -> FragmentBarcodeHistory()
            R.id.itemSettings -> SettingsFragment()
            else -> null
        }
        fragment?.apply(::replaceFragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.layoutFragmentContainer, fragment)
            .setReorderingAllowed(true)
            .commit()
    }
}
