//SS
package com.sladjan.wifianalyzer

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric.buildActivity
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class SplashActivityTest {
    @Test
    fun testSplashActivity() {
        // execute
        val splashActivity = buildActivity(SplashActivity::class.java)
                .create()
                .resume()
                .get()
        // validate
        assertNotNull(splashActivity)
    }
}