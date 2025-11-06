package otus.gpb.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import otus.gpb.recyclerview.databinding.ChatItemBinding
import otus.gpb.recyclerview.model.ChatItem

class ChatDiffAdapter(
    private val itemListener: ItemListener,
) : ListAdapter<ChatItem, ChatViewHolder>(DiffUtilItem()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatViewHolder {
        val chatItemBinding =
            ChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(chatItemBinding, itemListener)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) =
        holder.bind(getItem(position))

}

private class DiffUtilItem : DiffUtil.ItemCallback<ChatItem>() {
    override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
        return oldItem::class == newItem::class && oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
        return oldItem == newItem
    }

}