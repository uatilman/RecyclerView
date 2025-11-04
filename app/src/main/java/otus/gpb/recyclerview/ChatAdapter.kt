package otus.gpb.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import otus.gpb.recyclerview.databinding.ChatItemBinding
import otus.gpb.recyclerview.model.ChatItem

class ChatAdapter(
    private val dataSet: MutableList<ChatItem>,
    private val itemListener: ItemListener,
) : RecyclerView.Adapter<ChatViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatViewHolder {
        val chatItemBinding =
            ChatItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ChatViewHolder(chatItemBinding, itemListener)
    }

    override fun onBindViewHolder(
        holder: ChatViewHolder,
        position: Int
    ) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size
}