package otus.gpb.recyclerview

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import otus.gpb.recyclerview.databinding.ActivityMainBinding
import otus.gpb.recyclerview.model.ChatItem
import otus.gpb.recyclerview.model.MessageStatus
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Random
import java.util.UUID

class MainActivity : AppCompatActivity(), ItemListener {

    lateinit var activityMainBinding: ActivityMainBinding

    private val chatList: MutableList<ChatItem> by lazy { generateTestData() }
    private val chatAdapter: ChatAdapter by lazy { ChatAdapter(chatList, this) }
    private val chatDiffAdapter: ChatDiffAdapter by lazy { ChatDiffAdapter(this) }
    private val random = Random()

    private val formatter = DateTimeFormatter.ofPattern("HH:mm")
    private val now = LocalTime.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        with(activityMainBinding.chatListView) {
            addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayout.VERTICAL))
            ItemTouchHelper(ChatItemTouchHelper(this@MainActivity)).attachToRecyclerView(this)
            adapter = chatDiffAdapter
            chatDiffAdapter.submitList(chatList.toList())
        }
    }


    /* TODO: вынести в модель */
    private fun generateTestData(): MutableList<ChatItem> {
        return Array(15) { index: Int ->
            ChatItem(
                UUID.randomUUID(),
                R.drawable.chat_item_icon,
                "Chat Name " + index,
                "User Name " + index,
                "Last Message " + index,
                "Title " + index,
                random.nextBoolean(),
                random.nextBoolean(),
                MessageStatus.entries[random.nextInt(3)],
                now.minusSeconds(random.nextInt(3600 * 24 * 7).toLong()).format(formatter)
                    .toString(),
                random.nextBoolean()
            )
        }.toMutableList()
    }

    override fun onItemClick(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun onSwipe(id: UUID) {
        chatList.removeIf { it.id == id }
        chatDiffAdapter.submitList(chatList.toList())
        Toast.makeText(this, "Item swiped: $id", Toast.LENGTH_SHORT).show()
    }

}

