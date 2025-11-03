package otus.gpb.recyclerview.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import otus.gpb.recyclerview.R

data class ChatItem(
    val id: Int,
    val imageId:Int,
    val chatName: String,
    val lastUserName: String,
    val lastMessage: String,
    val title: String,
    val isVerified: Boolean,
    val isMuted: Boolean,
    val messageStatus: MessageStatus,
    val lastMessageTime: String,
    val isPinned: Boolean
)

enum class MessageStatus(
    @param:ColorRes val colorId:Int,
    @param:DrawableRes val iconId: Int
) {
    SENT(R.color.grey,R.drawable.check),
    DELIVERED(R.color.grey, R.drawable.read),
    READ(R.color.green, R.drawable.read);//    @ColorRes colorId:Int,
}