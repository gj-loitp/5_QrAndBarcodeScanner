package com.mckimquyen.barcodescanner.feature.tabs.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mckimquyen.barcodescanner.BuildConfig
import com.mckimquyen.barcodescanner.R
import com.mckimquyen.barcodescanner.di.barcodeDatabase
import com.mckimquyen.barcodescanner.di.settings
import com.mckimquyen.barcodescanner.extension.applySystemWindowInsets
import com.mckimquyen.barcodescanner.extension.packageManager
import com.mckimquyen.barcodescanner.extension.showError
import com.mckimquyen.barcodescanner.feature.common.dialog.DeleteConfirmationDialogFragment
import com.mckimquyen.barcodescanner.feature.tabs.settings.camera.ChooseCameraActivity
import com.mckimquyen.barcodescanner.feature.tabs.settings.formats.SupportedFormatsActivity
import com.mckimquyen.barcodescanner.feature.tabs.settings.permissions.AllPermissionsActivity
import com.mckimquyen.barcodescanner.feature.tabs.settings.search.ChooseSearchEngineActivity
import com.mckimquyen.barcodescanner.feature.tabs.settings.theme.ChooseThemeActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.f_settings.*


class SettingsFragment : Fragment(), DeleteConfirmationDialogFragment.Listener {
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
        buttonChooseTheme.setOnClickListener { ChooseThemeActivity.start(requireActivity()) }
        buttonChooseCamera.setOnClickListener { ChooseCameraActivity.start(requireActivity()) }
        buttonSelectSupportedFormats.setOnClickListener { SupportedFormatsActivity.start(requireActivity()) }
        buttonClearHistory.setOnClickListener { showDeleteHistoryConfirmationDialog() }
        buttonChooseSearchEngine.setOnClickListener { ChooseSearchEngineActivity.start(requireContext()) }
        buttonPermissions.setOnClickListener { AllPermissionsActivity.start(requireActivity()) }
        buttonCheckUpdates.setOnClickListener { showAppInMarket() }
        buttonSourceCode.setOnClickListener { showSourceCode() }
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
        val dialog = DeleteConfirmationDialogFragment.newInstance(R.string.dialog_delete_clear_history_message)
        dialog.show(childFragmentManager, "")
    }

    private fun showAppInMarket() {
        val uri = Uri.parse("market://details?id=" + requireContext().packageName)
        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        }
        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun showSourceCode() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/wewewe718/QrAndBarcodeScanner"))
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun showAppVersion() {
        buttonAppVersion.hint = BuildConfig.VERSION_NAME
    }
}