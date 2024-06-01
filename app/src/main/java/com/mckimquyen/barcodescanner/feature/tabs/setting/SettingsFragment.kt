package com.mckimquyen.barcodescanner.feature.tabs.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mckimquyen.barcodescanner.BuildConfig
import com.mckimquyen.barcodescanner.R
import com.mckimquyen.barcodescanner.di.barcodeDatabase
import com.mckimquyen.barcodescanner.di.settings
import com.mckimquyen.barcodescanner.extension.applySystemWindowInsets
import com.mckimquyen.barcodescanner.extension.ext.moreApp
import com.mckimquyen.barcodescanner.extension.ext.openBrowserPolicy
import com.mckimquyen.barcodescanner.extension.ext.openUrlInBrowser
import com.mckimquyen.barcodescanner.extension.ext.rateApp
import com.mckimquyen.barcodescanner.extension.ext.shareApp
import com.mckimquyen.barcodescanner.extension.packageManager
import com.mckimquyen.barcodescanner.extension.showError
import com.mckimquyen.barcodescanner.feature.common.dlg.DialogFragmentDeleteConfirmation
import com.mckimquyen.barcodescanner.feature.tabs.setting.camera.ChooseCameraActivityBase
import com.mckimquyen.barcodescanner.feature.tabs.setting.formats.SupportedFormatsActivityBase
import com.mckimquyen.barcodescanner.feature.tabs.setting.permissions.AllPermissionsActivityBase
import com.mckimquyen.barcodescanner.feature.tabs.setting.search.ChooseSearchEngineActivityBase
import com.mckimquyen.barcodescanner.feature.tabs.setting.theme.ChooseThemeActivityBase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.f_settings.*


class SettingsFragment : Fragment(), DialogFragmentDeleteConfirmation.Listener {
    private val disposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.f_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        supportEdgeToEdge()
    }

    override fun onResume() {
        super.onResume()
        handleButtonCheckedChanged()
        handleButtonClicks()
        showSettings()
        showAppVersion()
    }

    override fun onDeleteConfirmed() {
        clearHistory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.clear()
    }

    fun supportEdgeToEdge() {
        appBarLayout.applySystemWindowInsets(applyTop = true)
    }

    private fun handleButtonCheckedChanged() {
        buttonInverseBarcodeColorsInDarkTheme.setCheckedChangedListener { settings.areBarcodeColorsInversed = it }
        buttonOpenLinksAutomatically.setCheckedChangedListener { settings.openLinksAutomatically = it }
        buttonCopyToClipboard.setCheckedChangedListener { settings.copyToClipboard = it }
        buttonSimpleAutoFocus.setCheckedChangedListener { settings.simpleAutoFocus = it }
        buttonFlashlight.setCheckedChangedListener { settings.flash = it }
        buttonVibrate.setCheckedChangedListener { settings.vibrate = it }
        buttonContinuousScanning.setCheckedChangedListener { settings.continuousScanning = it }
        buttonConfirmScansManually.setCheckedChangedListener { settings.confirmScansManually = it }
        buttonSaveScannedBarcodes.setCheckedChangedListener { settings.saveScannedBarcodesToHistory = it }
        buttonSaveCreatedBarcodes.setCheckedChangedListener { settings.saveCreatedBarcodesToHistory = it }
        buttonDoNotSaveDuplicates.setCheckedChangedListener { settings.doNotSaveDuplicates = it }
        buttonEnableErrorReports.setCheckedChangedListener { settings.areErrorReportsEnabled = it }
    }

    private fun handleButtonClicks() {
        buttonChooseTheme.setOnClickListener {
            ChooseThemeActivityBase.start(requireActivity())
        }
        buttonChooseCamera.setOnClickListener {
            ChooseCameraActivityBase.start(requireActivity())
        }
        buttonSelectSupportedFormats.setOnClickListener {
            SupportedFormatsActivityBase.start(requireActivity())
        }
        buttonClearHistory.setOnClickListener {
            showDeleteHistoryConfirmationDialog()
        }
        buttonChooseSearchEngine.setOnClickListener {
            ChooseSearchEngineActivityBase.start(requireContext())
        }
        buttonPermissions.setOnClickListener {
            AllPermissionsActivityBase.start(requireActivity())
        }
        buttonCheckUpdates.setOnClickListener {
            showAppInMarket()
        }
        buttonSourceCode.setOnClickListener {
            showSourceCode()
        }
        btRateApp.setOnClickListener {
            requireContext().rateApp(requireContext().packageName)
        }
        btMoreApp.setOnClickListener {
            requireContext().moreApp()
        }
        btShareApp.setOnClickListener {
            requireContext().shareApp()
        }
        btPolicy.setOnClickListener {
            requireContext().openBrowserPolicy()
        }
    }

    private fun clearHistory() {
        buttonClearHistory.isEnabled = false

        barcodeDatabase.deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    buttonClearHistory.isEnabled = true
                },
                { error ->
                    buttonClearHistory.isEnabled = true
                    showError(error)
                }
            )
            .addTo(disposable)
    }

    private fun showSettings() {
        settings.apply {
            buttonInverseBarcodeColorsInDarkTheme.isChecked = areBarcodeColorsInversed
            buttonOpenLinksAutomatically.isChecked = openLinksAutomatically
            buttonCopyToClipboard.isChecked = copyToClipboard
            buttonSimpleAutoFocus.isChecked = simpleAutoFocus
            buttonFlashlight.isChecked = flash
            buttonVibrate.isChecked = vibrate
            buttonContinuousScanning.isChecked = continuousScanning
            buttonConfirmScansManually.isChecked = confirmScansManually
            buttonSaveScannedBarcodes.isChecked = saveScannedBarcodesToHistory
            buttonSaveCreatedBarcodes.isChecked = saveCreatedBarcodesToHistory
            buttonDoNotSaveDuplicates.isChecked = doNotSaveDuplicates
            buttonEnableErrorReports.isChecked = areErrorReportsEnabled
        }
    }

    private fun showDeleteHistoryConfirmationDialog() {
        val dialog = DialogFragmentDeleteConfirmation.newInstance(R.string.dialog_delete_clear_history_message)
        dialog.show(childFragmentManager, "")
    }

    private fun showAppInMarket() {
        val uri = Uri.parse("market://details?id=" + requireContext().packageName)
        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            flags =
                Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        }
        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun showSourceCode() {
        if (BuildConfig.DEBUG) {
//            val intent =
//                Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/gj-loitp/5_QrAndBarcodeScanner/tree/dev"))
//            if (intent.resolveActivity(packageManager) != null) {
//                startActivity(intent)
//            }
            context.openUrlInBrowser("https://github.com/gj-loitp/5_QrAndBarcodeScanner/tree/dev")
        } else {
            Toast.makeText(context, "This feature is only available in debug version", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showAppVersion() {
        buttonAppVersion.hint = BuildConfig.VERSION_NAME
    }
}