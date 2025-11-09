package otus.gpb.recyclerview

import android.app.Application
import otus.gpb.recyclerview.stat.StatService
import otus.gpb.recyclerview.prefs.KeyValueStorage
import otus.gpb.recyclerview.prefs.SharedPreferencesStorage


class App : Application() {
    /**
     * Stat service
     */
    val statService = StatService.Logger()

    /**
     * Preferences storage
     */
    val preferences: KeyValueStorage by lazy {
        SharedPreferencesStorage(this, "prefs")
    }
}