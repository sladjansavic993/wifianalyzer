

package com.sladjan.wifianalyzer;


import androidx.test.espresso.ViewInteraction;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.sladjan.wifianalyzer.InstrumentedUtils.ChildAtPosition;
import static com.sladjan.wifianalyzer.InstrumentedUtils.pauseLong;
import static com.sladjan.wifianalyzer.InstrumentedUtils.pauseShort;
import static org.hamcrest.Matchers.allOf;

class Scanner implements Runnable {
    private static final int SCANNER_BUTTON = 2;
    private static final int SCANNER_ACTION = 1;
    private static final String SCANNER_PAUSE_TAG = "Pause";
    private static final String SCANNER_RESUME_TAG = "Play";

    @Override
    public void run() {
        scannerAction(SCANNER_PAUSE_TAG);
        pauseLong();
        scannerAction(SCANNER_RESUME_TAG);
    }

    private void scannerAction(String tag) {
        pauseShort();
        ViewInteraction actionMenuItemView = onView(
            allOf(withId(R.id.action_scanner), withContentDescription(tag),
                new ChildAtPosition(new ChildAtPosition(withId(R.id.toolbar), SCANNER_BUTTON),
                    SCANNER_ACTION),
                isDisplayed()));
        actionMenuItemView.perform(click());
    }

}
