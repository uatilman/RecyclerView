package otus.gpb.recyclerview.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import otus.gpb.recyclerview.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

private val formatter = DateTimeFormatter.ofPattern("d MMM HH:mm")

data class ChatItem(
    val id: UUID,
    val imageId: Int,
    val chatName: String,
    val lastUserName: String,
    val lastMessage: String,
    val title: String,
    var isVerified: Boolean,
    val isMuted: Boolean,
    val messageStatus: MessageStatus,
    var lastMessageTime: LocalDateTime,
    var lastMessageViewTime: String = lastMessageTime.format(formatter)
        .toString(),
    val isPinned: Boolean
) {
    fun setTime(time: LocalDateTime) {
        lastMessageTime = time
        lastMessageViewTime = time.format(formatter).toString()
    }
}

enum class MessageStatus(
    @param:ColorRes val colorId: Int,
    @param:DrawableRes val iconId: Int
) {
    SENT(R.color.grey, R.drawable.check),
    DELIVERED(R.color.grey, R.drawable.read),
    READ(R.color.green, R.drawable.read);//    @ColorRes colorId:Int,
}