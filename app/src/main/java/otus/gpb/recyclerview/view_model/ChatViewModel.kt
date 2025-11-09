package otus.gpb.recyclerview.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import otus.gpb.recyclerview.model.ChatItem
import otus.gpb.recyclerview.repository.ChatRepository
import otus.gpb.recyclerview.repository.ChatServerListener
import java.util.UUID

class ChatViewModel : ViewModel(), ChatServerListener {

    val chatRepository = ChatRepository(this)

    private val _content: MutableLiveData<MutableList<ChatItem>> =
        MutableLiveData(chatRepository.getDataPage())

    val content: LiveData<MutableList<ChatItem>> get() = _content

    fun loadNextPage() {
        chatRepository.loadNextItems()
    }

    fun removeItem(id: UUID) {
        chatRepository.removeItem(id)
    }

    override fun onChatItemUpdated(testData: MutableList<ChatItem>) {
        _content.postValue(testData)
    }


}