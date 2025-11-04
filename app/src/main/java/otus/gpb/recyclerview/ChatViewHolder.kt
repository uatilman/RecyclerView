package otus.gpb.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import otus.gpb.recyclerview.databinding.ChatItemBinding
import otus.gpb.recyclerview.model.ChatItem

class ChatViewHolder(
    private val cartItemBinding: ChatItemBinding,
    private val itemListener: ItemListener
) : RecyclerView.ViewHolder(cartItemBinding.root) {

    val chatImage: ImageView = cartItemBinding.chatImage
    val chatNameText: TextView = cartItemBinding.chatName
    val lastUserNameText: TextView = cartItemBinding.lastUserName
    val lastMessageText: TextView = cartItemBinding.lastMessage
    val verifiedImage: ImageView = cartItemBinding.verified
    val muteImage: ImageView = cartItemBinding.mute
    val messageStatusImage: ImageView = cartItemBinding.messageStatus
    val lastMessageTimeText: TextView = cartItemBinding.lastMessageTime
    val chatPinnedImage: ImageView = cartItemBinding.pinned



    fun bind(chatItem: ChatItem) {
        with(chatItem) {
            chatImage.setImageResource(imageId)
            chatNameText.text = chatName
            lastUserNameText.text = lastUserName
            lastMessageText.text = lastMessage
            verifiedImage.visibility = if (isVerified) View.VISIBLE else View.GONE
            muteImage.visibility = if (isMuted) View.VISIBLE else View.GONE

            messageStatusImage.setImageResource(messageStatus.iconId)
            messageStatusImage.setColorFilter(ContextCompat.getColor(cartItemBinding.root.context, messageStatus.colorId),
                android.graphics.PorterDuff.Mode.SRC_IN)
            lastMessageTimeText.text = lastMessageTime
            chatPinnedImage.visibility = if (isPinned) View.VISIBLE else View.GONE

        }

    }

}