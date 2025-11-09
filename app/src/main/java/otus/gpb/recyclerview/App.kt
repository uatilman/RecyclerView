package otus.gpb.recyclerview

import android.app.Application
import otus.gpb.recyclerview.prefs.KeyValueStorage
import otus.gpb.recyclerview.prefs.SharedPreferencesStorage
import otus.gpb.recyclerview.repository.ChatRepository
import otus.gpb.recyclerview.stat.StatService


class App : Application() {
    /**
     * Stat service
     */
    val statService = StatService.Logger()

    /**
     * Preferences storage
     * Можно подписаться через LiveData на изменение по ключу и получасть изменения в разных activities
     */
    val preferences: KeyValueStorage by lazy {
        SharedPreferencesStorage(this, "prefs")
    }

    val chatRepository by lazy {
        ChatRepository()
    }

}