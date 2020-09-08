

package com.sladjan.wifianalyzer.export

import android.content.Intent
import com.sladjan.annotation.OpenClass

@OpenClass
class ExportIntent {

    internal fun intent(title: String, data: String): Intent {
        val intentSend: Intent = intentSend()
        intentSend.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intentSend.type = "text/plain"
        intentSend.putExtra(Intent.EXTRA_TITLE, title)
        intentSend.putExtra(Intent.EXTRA_SUBJECT, title)
        intentSend.putExtra(Intent.EXTRA_TEXT, data)
        return intentChooser(intentSend, title)
    }

    internal fun intentSend(): Intent = Intent(Intent.ACTION_SEND)

    internal fun intentChooser(intent: Intent, title: String): Intent = Intent.createChooser(intent, title)
}