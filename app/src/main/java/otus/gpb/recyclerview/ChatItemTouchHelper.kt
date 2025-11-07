package otus.gpb.recyclerview

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.util.UUID

class ChatItemTouchHelper(private val listener: ItemListener) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ) = makeMovementFlags(
        ItemTouchHelper.ACTION_STATE_IDLE,
        ItemTouchHelper.LEFT
    )

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onSwiped(
        viewHolder: RecyclerView.ViewHolder,
        direction: Int
    ) {
        viewHolder.getElementId()?.let { listener.onSwipe(it) }
    }

    private fun RecyclerView.ViewHolder.getElementId(): UUID? {
        return bindingAdapter
            ?.asClass<ChatDiffAdapter>()
            ?.currentList
            ?.getOrNull(bindingAdapterPosition)?.id
    }
}