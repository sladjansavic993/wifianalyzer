//SS

package com.sladjan.wifianalyzer;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static com.sladjan.wifianalyzer.InstrumentedUtils.pauseShort;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testNavigation() {
        pauseShort();
        new Navigation().run();
        pauseShort();
    }

    @Test
    public void testScanner() {
        pauseShort();
        new Scanner().run();
        pauseShort();
    }

    @Test
    public void testFilter() {
        pauseShort();
        new Filter().run();
        pauseShort();
    }

}