package com.discord.fourierbot.encryption

import com.discord.fourierbot.dto.UserEntity
import com.discord.fourierbot.utils.KEYS_PATH
import com.discord.fourierbot.utils.Resources
import com.discord.fourierbot.utils.YamlManager
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
                .replace("\\", "o")
                .replace("/", "i")
        } catch(e: NoSuchAlgorithmException) {
            println("ENGINE: Entered key generating algorithm is incorrect.")
            exitProcess(1)
        }
    }

    private fun serialize() {
        try {
            YamlManager.writeYamlObject(KEYS_PATH, Resources.keys)
        } catch (e: Exception) {
            println("ENGINE: Cannot save list object.")
            e.printStackTrace()
        }
    }

    fun deserialize(): CodesList {
        val keysFile = File(KEYS_PATH)
        if(!keysFile.exists()) return CodesList()
        return try {
            YamlManager.readYamlObject(KEYS_PATH, CodesList::class.java)
        } catch (e: Exception) {
            println("ENGINE: Error occurred with reading keys file.")
            e.printStackTrace()
            CodesList()
        }
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
        var usersList = String()
        Resources.keys.userEntities.forEach {
            usersList = usersList.plus("${it.userName} ${it.userId} ${it.code}\n")
        }
        return usersList
    }

    fun clearUsersList() {
        Resources.keys.userEntities.clear()
        serialize()
    }
}