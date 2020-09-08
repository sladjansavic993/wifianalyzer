//SS
package com.sladjan.wifianalyzer.about

import android.content.pm.PackageInfo
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sladjan.util.readFile
import com.sladjan.wifianalyzer.MainContextHelper.INSTANCE
import com.sladjan.wifianalyzer.R
import com.sladjan.wifianalyzer.RobolectricUtil
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import org.robolectric.shadows.ShadowAlertDialog
import java.text.SimpleDateFormat
import java.util.*

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class AboutFragmentTest {
    private val mainActivity = RobolectricUtil.INSTANCE.activity
    private val configuration = INSTANCE.configuration
    private val fixture = AboutFragment()

    @Before
    fun setUp() {
        whenever(configuration.sizeAvailable).thenReturn(true)
        whenever(configuration.largeScreen).thenReturn(true)
        RobolectricUtil.INSTANCE.startFragment(fixture)
        RobolectricUtil.INSTANCE.clearLooper()
    }

    @After
    fun tearDown() {
        INSTANCE.restore()
        verify(configuration, atLeastOnce()).sizeAvailable
        verify(configuration).largeScreen
    }

    @Test
    fun testOnCreateView() {
        assertNotNull(fixture.view)
    }

    @Test
    fun testVersionNumber() {
        // setup
        val expected: String = version() + "SL" + " (" + Build.VERSION.RELEASE + "-" + Build.VERSION.SDK_INT + ")"
        // execute
        val actual = fixture.requireView().findViewById<TextView>(R.id.about_version_info)
        // validate
        assertNotNull(actual)
        assertEquals(expected, actual.text)
    }

    @Test
    fun testPackageName() {
        // execute
        val actual = fixture.requireView().findViewById<TextView>(R.id.about_package_name)
        // validate
        assertNotNull(actual)
        assertEquals(fixture.requireActivity().packageName, actual.text)
    }

    @Test
    fun testApplicationName() {
        // setup
        val expectedName = fixture.getString(R.string.app_full_name)
        // execute
        val actual = fixture.requireView().findViewById<TextView>(R.id.about_application_name)
        // validate
        assertNotNull(actual)
        assertEquals(expectedName, actual.text)
    }

    @Test
    fun testCopyright() {
        // setup
        val expectedName = (fixture.getString(R.string.app_copyright) + SimpleDateFormat("yyyy").format(Date()))
        // execute
        val actual = fixture.requireView().findViewById<TextView>(R.id.about_copyright)
        // validate
        assertNotNull(actual)
        assertEquals(expectedName, actual.text)
    }

    @Test
    fun testWriteReview() {
        // setup
        val view = fixture.requireView().findViewById<View>(R.id.writeReview)
        // execute
        val actual = view.performClick()
        //
        assertTrue(actual)
    }

    @Test
    fun testAlertDialogClickListener() {
        validateAlertDialogClickListener(R.id.contributors, R.string.about_contributor_title, R.raw.contributors)
        validateAlertDialogClickListener(R.id.license, R.string.gpl, R.raw.gpl)
        validateAlertDialogClickListener(R.id.graphViewLicense, R.string.al, R.raw.al)
        validateAlertDialogClickListener(R.id.materialDesignIconsLicense, R.string.al, R.raw.al)
    }

    private fun version(): String {
        val packageInfo: PackageInfo = fixture.requireActivity().packageManager.getPackageInfo(mainActivity.packageName, 0)
        return packageInfo.versionName + " - " + packageInfo.longVersionCode
    }

    private fun validateAlertDialogClickListener(viewId: Int, titleId: Int, messageId: Int) {
        // setup
        val view = fixture.view!!.findViewById<View>(viewId)
        val expectedTitle = fixture.requireActivity().applicationContext.getString(titleId)
        val expectedMessage = readFile(fixture.requireActivity().resources, messageId)
        // execute
        view.performClick()
        // validate
        val alertDialog = ShadowAlertDialog.getLatestAlertDialog()
        val shadowAlertDialog = shadowOf(alertDialog)
        assertEquals(expectedTitle, shadowAlertDialog.title.toString())
        assertEquals(expectedMessage, shadowAlertDialog.message.toString())
    }
}