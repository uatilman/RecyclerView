package otus.gpb.recyclerview

import java.util.UUID

interface ItemListener {
    fun onItemClick(id: UUID)
    fun onSwipe(id: UUID)

}