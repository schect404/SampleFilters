package com.example.samplearchitecture

import android.os.Build
import android.view.View
import androidx.test.core.app.ApplicationProvider
import com.example.samplearchitecture.utils.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class ViewExtensionsTest {

    val view = View(ApplicationProvider.getApplicationContext())

    @Test
    fun checkViewGone() {
        view.gone()
        assert(view.visibility == View.GONE)
    }

    @Test
    fun checkViewVisible() {
        view.visible()
        assert(view.visibility == View.VISIBLE)
    }

    @Test
    fun checkViewInvisible() {
        view.invisible()
        assert(view.visibility == View.INVISIBLE)
    }

    @Test
    fun checkViewVisibleIfOrGone() {
        view.visibleIfOrGone { true }
        assert(view.visibility == View.VISIBLE)
        view.visibleIfOrGone { false }
        assert(view.visibility == View.GONE)
    }

    @Test
    fun checkViewVisibleIfOrInvisible() {
        view.visibleIfOrInvisible { true }
        assert(view.visibility == View.VISIBLE)
        view.visibleIfOrInvisible { false }
        assert(view.visibility == View.INVISIBLE)
    }

    @Test
    fun checkEnabled() {
        view.enable()
        assert(view.alpha == 1f)
        assert(view.isClickable)
        assert(view.isFocusable)
    }

    @Test
    fun checkDisabled() {
        view.disable()
        assert(view.alpha == 0.7f)
        assert(!view.isClickable)
        assert(!view.isFocusable)
    }

    @Test
    fun checkEnableIfOrDisable() {
        view.enableIf { true }
        assert(view.alpha == 1f)
        assert(view.isClickable)
        assert(view.isFocusable)
        view.enableIf { false }
        assert(view.alpha == 0.7f)
        assert(!view.isClickable)
        assert(!view.isFocusable)
    }

}