package otus.gpb.recyclerview.stat

import androidx.annotation.VisibleForTesting
import java.time.Instant
import kotlin.time.ExperimentalTime

data class PageEvent @OptIn(ExperimentalTime::class) constructor(
    private val page: String,
    private val action: String,
    private val time: Instant
) : Event {
    override val name: String get() = PAGE

    override val properties: Map<String, String>
        get() = mapOf(
            NAME to page,
            TIME to time.toString(),
            ACTION to action
        )

    @VisibleForTesting
    companion object {
        const val PAGE = "page"
        const val NAME = "name"
        const val TIME = "time"
        const val ACTION = "action"
    }
}

fun createPageEvent(
    name: String,
    action: String,
    time: Instant = Instant.now()
) = PageEvent(name, action, time)

fun <T : Any> T.createPageEvent(
    action: String,
    time: Instant = Instant.now()
) = createPageEvent(requireNotNull(this::class.simpleName), action, time)