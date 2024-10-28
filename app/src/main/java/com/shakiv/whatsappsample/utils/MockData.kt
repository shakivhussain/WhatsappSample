package com.shakiv.whatsappsample.utils

import com.shakiv.whatsappsample.data.model.Message
import com.shakiv.whatsappsample.data.model.User
import kotlin.random.Random

object MockData {

    fun getMockUsers(): List<User> {
        val users = mutableListOf<User>()
        val random = Random

        repeat(20) {
            val username = generateRandomUsername(it)
            val phoneNumber = generateRandomPhoneNumber()
            val profileUrl = generateRandomProfileUrl(it)

            val lastMessage = Message(
                username = username,
                date = System.currentTimeMillis() - random.nextLong(1_000_000_000),
                text = generateRandomMessage(),
                userId = random.nextLong(1000, 9999)
            )

            val user = User(
                username = username,
                phoneNumber = phoneNumber,
                profileUrl = profileUrl,
                lastMessage = lastMessage
            )
            users.add(user)
        }

        return users
    }

    private fun generateRandomUsername(index: Int): String {
        return "User $index"
    }

    private fun generateRandomPhoneNumber(): String {
        val areaCode = "971"
        val number = (100000000..999999999).random()
        return "$areaCode$number"
    }

    private fun generateRandomProfileUrl(index: Int): String {
        return "https://picsum.photos/200/300?random=$index"
    }

    private fun generateRandomMessage(): String {
        val messages = listOf(
            "Hey, how are you?",
            "Are you free this weekend?",
            "Let's grab lunch sometime!",
            "Did you finish the report?",
            "What do you think about the project?",
            "Can't wait to see you again!",
            "Have you seen the latest news?",
            "How was your vacation?",
            "Do you want to catch a movie?",
            "Let's plan a trip together!",
            "Remember our last meeting?",
            "I have a new recipe to share!",
            "What time works for you?",
            "Thanks for your help!",
            "Looking forward to our next chat!"
        )
        return messages.random()
    }
}


