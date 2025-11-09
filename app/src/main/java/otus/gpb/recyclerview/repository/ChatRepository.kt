package otus.gpb.recyclerview.repository

import otus.gpb.recyclerview.R
import otus.gpb.recyclerview.model.ChatItem
import otus.gpb.recyclerview.model.MessageStatus
import java.time.LocalDateTime
import java.util.Random
import java.util.UUID

class ChatRepository() {

    private val random = Random()
    val pageSize = 10
    private var page = 1
    private var testData: MutableList<ChatItem>

    private val listeners: MutableList<ChatServerListener> = mutableListOf()

    fun subscribe(listener: ChatServerListener) {
        listeners.add(listener)
    }

    init {
        testData = generateTestData()

        // имитация подгрузки данных через с сервера через socket
        object : Thread() {
            override fun run() {
                while (true) {
                    sleep(10_000)
                    val item = testData[random.nextInt(testData.size)]
                        item.setTime(LocalDateTime.now())
                    item.isVerified = false
                    testData =
                        testData.toMutableList().apply { sortByDescending { it.lastMessageTime } }
                    notifyListeners()
                }
            }
        }.start()

    }

    private fun notifyListeners() {
        listeners.forEach { it.onChatItemUpdated(getDataPage()) }
    }

    fun loadNextItems() {
        page++
        notifyListeners()
    }

    fun removeItem(id: UUID) {
        testData = testData.filter { it.id != id }
            .toMutableList() // удаляем элемент по id и возвращаем новый список  testData.removeIf { it.id != id }
        notifyListeners()
    }

    fun getDataPage(): MutableList<ChatItem> {
        val toIndex = (page * pageSize).coerceAtMost(testData.size) // не выйти за пределы
        return if (toIndex in 0 until  testData.size) {
            testData.subList(0, toIndex)
        } else {
           testData.toMutableList()
        }
    }

    private fun generateTestData(): MutableList<ChatItem> {
        val now = LocalDateTime.now()
        return Array(25) { index: Int ->
            val time: LocalDateTime = now.minusSeconds(random.nextInt(3600 * 24 * 7).toLong())
            ChatItem(
                id = UUID.randomUUID(),
                imageId = R.drawable.chat_item_icon,
                chatName = "Chat Name " + index,
                lastUserName = "User Name " + index,
                lastMessage = "Last Message " + index,
                title = "Title " + index,
                isVerified = random.nextBoolean(),
                isMuted = random.nextBoolean(),
                messageStatus = MessageStatus.entries[random.nextInt(3)],
                lastMessageTime = time,
                isPinned = random.nextBoolean()
            )
        }
            .apply { sortByDescending { it.lastMessageTime } }
            .toMutableList()
    }
}

interface ChatServerListener {
    fun onChatItemUpdated(testData: MutableList<ChatItem>)
}