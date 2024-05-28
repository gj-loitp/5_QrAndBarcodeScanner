package com.mckimquyen.barcodescanner.feature.tabs.settings.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.mckimquyen.barcodescanner.R
import com.mckimquyen.barcodescanner.di.settings
import com.mckimquyen.barcodescanner.extension.applySystemWindowInsets
import com.mckimquyen.barcodescanner.extension.unsafeLazy
import com.mckimquyen.barcodescanner.feature.BaseActivity
import com.mckimquyen.barcodescanner.feature.common.view.SettingsRadioButton
import com.mckimquyen.barcodescanner.model.SearchEngine
import kotlinx.android.synthetic.main.a_choose_search_engine.*

class ChooseSearchEngineActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ChooseSearchEngineActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val buttons by unsafeLazy {
        listOf(buttonNone, buttonAskEveryTime, buttonBing, buttonDuckDuckGo, buttonGoogle, buttonQwant, buttonStartPage, buttonYahoo, buttonYandex)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_choose_search_engine)
        supportEdgeToEdge()
        initToolbar()
        showInitialValue()
        handleSettingsChanged()
    }

    private fun supportEdgeToEdge() {
        rootView.applySystemWindowInsets(applyTop = true, applyBottom = true)
    }

    private fun initToolbar() {
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun showInitialValue() {
        when (settings.searchEngine) {
            SearchEngine.NONE -> buttonNone.isChecked = true
            SearchEngine.ASK_EVERY_TIME -> buttonAskEveryTime.isChecked = true
            SearchEngine.BING -> buttonBing.isChecked = true
            SearchEngine.DUCK_DUCK_GO -> buttonDuckDuckGo.isChecked = true
            SearchEngine.GOOGLE -> buttonGoogle.isChecked = true
            SearchEngine.QWANT -> buttonQwant.isChecked = true
            SearchEngine.STARTPAGE -> buttonStartPage.isChecked = true
            SearchEngine.YAHOO -> buttonYahoo.isChecked = true
            SearchEngine.YANDEX -> buttonYandex.isChecked = true
        }
    }

    private fun handleSettingsChanged() {
        buttonNone.setCheckedChangedListener(SearchEngine.NONE)
        buttonAskEveryTime.setCheckedChangedListener(SearchEngine.ASK_EVERY_TIME)
        buttonBing.setCheckedChangedListener(SearchEngine.BING)
        buttonDuckDuckGo.setCheckedChangedListener(SearchEngine.DUCK_DUCK_GO)
        buttonGoogle.setCheckedChangedListener(SearchEngine.GOOGLE)
        buttonQwant.setCheckedChangedListener(SearchEngine.QWANT)
        buttonStartPage.setCheckedChangedListener(SearchEngine.STARTPAGE)
        buttonYahoo.setCheckedChangedListener(SearchEngine.YAHOO)
        buttonYandex.setCheckedChangedListener(SearchEngine.YANDEX)
    }

    private fun SettingsRadioButton.setCheckedChangedListener(searchEngine: SearchEngine) {
        setCheckedChangedListener { isChecked ->
            if (isChecked) {
                uncheckOtherButtons(this)
                settings.searchEngine = searchEngine
            }
        }
    }

    private fun uncheckOtherButtons(checkedButton: View) {
        buttons.forEach { button ->
            if (checkedButton !== button) {
                button.isChecked = false
            }
        }
    }
}
