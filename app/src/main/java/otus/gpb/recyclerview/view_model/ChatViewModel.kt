package otus.gpb.recyclerview.view_model

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import otus.gpb.recyclerview.model.ChatItem
import otus.gpb.recyclerview.repository.ChatRepository
import otus.gpb.recyclerview.repository.ChatServerListener
import java.util.UUID

class ChatViewModel(
    private val chatRepository: ChatRepository
) : ViewModel(), ChatServerListener {

    init {
        chatRepository.subscribe(this)
    }

    private val _content: MutableLiveData<MutableList<ChatItem>> =
        MutableLiveData(chatRepository.getDataPage())

    val content: LiveData<MutableList<ChatItem>> get() = _content

    fun loadNextPage() {
        chatRepository.loadNextItems()
    }

    fun removeItem(id: UUID) {
        chatRepository.removeItem(id)
    }

    /**
     * Подписались на информацию в репозитории
     * */
    override fun onChatItemUpdated(testData: MutableList<ChatItem>) {
        _content.postValue(testData)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val chatRepository: ChatRepository) :
        AbstractSavedStateViewModelFactory() {
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
                return ChatViewModel(chatRepository) as? T
                    ?: throw IllegalArgumentException("Can`t create ChatViewModel by factory")
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}