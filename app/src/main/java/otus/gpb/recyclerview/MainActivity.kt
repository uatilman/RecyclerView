package otus.gpb.recyclerview

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import otus.gpb.recyclerview.databinding.ActivityMainBinding
import otus.gpb.recyclerview.model.ChatItem
import otus.gpb.recyclerview.stat.PageEventHelper
import otus.gpb.recyclerview.view_model.ChatViewModel
import java.util.UUID

class MainActivity : AppCompatActivity(), ItemListener {

    lateinit var activityMainBinding: ActivityMainBinding

    private val chatDiffAdapter: ChatDiffAdapter by lazy { ChatDiffAdapter(this) }

    private val viewModel: ChatViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        val recyclerView = activityMainBinding.chatListView
        with(recyclerView) {
            addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayout.VERTICAL))
            ItemTouchHelper(ChatItemTouchHelper(this@MainActivity)).attachToRecyclerView(this)
            adapter = chatDiffAdapter
            viewModel.content.observe(this@MainActivity) { it: MutableList<ChatItem>? ->
                chatDiffAdapter.submitList(it)
            }

            addOnScrollListener(OnScrollListener { viewModel.loadNextPage() })
        }
        val app = application as? App
        app?.let {
            lifecycle.addObserver(PageEventHelper((application as App).statService))
        }

    }

     class OnScrollListener(private val action: () -> Unit) : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(
            recyclerView: RecyclerView,
            newState: Int
        ) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1)) {
                action.invoke()
            }
        }
    }

    override fun onItemClick(id: UUID) {
        Toast.makeText(this, "Item clicked: $id", Toast.LENGTH_SHORT).show()
    }

    override fun onSwipe(id: UUID) {
        viewModel.removeItem(id)
        Toast.makeText(this, "Item swiped: $id", Toast.LENGTH_SHORT).show()
    }

}

