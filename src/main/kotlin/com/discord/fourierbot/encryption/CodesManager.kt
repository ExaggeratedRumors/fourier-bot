package com.discord.fourierbot.encryption

import com.discord.fourierbot.dto.UserEntity
import com.discord.fourierbot.utils.KEYS_PATH
import com.discord.fourierbot.utils.Resources
import java.io.*
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.KeyGenerator
import kotlin.system.exitProcess

class CodesManager {
    fun generateKey(keyLength: Int): String {
        try {
            val key = KeyGenerator
                .getInstance(Resources.keyAlgorithm)
                .apply { this.init(256) }
                .generateKey()

            return Base64
                .getEncoder()
                .encodeToString(key.encoded)
                .substring(0, keyLength)
        } catch(e: NoSuchAlgorithmException) {
            println("ENGINE: Entered key generating algorithm is incorrect.")
            exitProcess(1)
        }
    }

    private fun serialize() {
        ObjectOutputStream(FileOutputStream(KEYS_PATH)).let {
            it.writeObject(Resources.keys)
            it.flush()
            it.close()
        }
    }

    fun deserialize(): CodesList {
        val keysFile = File(KEYS_PATH)
        if(!keysFile.exists()) return CodesList()
        val stream = ObjectInputStream(FileInputStream(keysFile))
        val codeList = stream.readObject() as CodesList
        stream.close()
        return codeList
    }

    fun addEntity(userId: Long, userName: String): String{
        val key = generateKey(Resources.configuration.key_length)
        Resources.keys.modify {
            this.add(UserEntity(userId, userName, key))
        }
        serialize()
        return key
    }

    fun getUserNameByCode(userCode: String): String? {
        Resources.keys.userEntities.forEach {
            if(it.code == userCode) return it.userName
        }
        return null
    }

    fun getUserCodeById(userId: Long): String? {
        Resources.keys.userEntities.forEach {
            if(it.userId == userId) return it.code
        }
        return null
    }

    fun getUsersList(): String {
        val usersList = String()
        Resources.keys.userEntities.forEach {
            usersList.plus("${it.userId} ${it.userName} ${it.code}\n")
        }
        return usersList
    }

    fun clearUsersList() {
        Resources.keys.userEntities.clear()
        serialize()
    }
}