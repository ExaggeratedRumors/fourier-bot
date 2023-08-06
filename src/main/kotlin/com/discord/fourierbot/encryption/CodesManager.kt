package com.discord.fourierbot.encryption

import com.discord.fourierbot.dto.UserEntity
import com.discord.fourierbot.utils.Resources
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.KeyGenerator
import kotlin.system.exitProcess

class CodesManager() {
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

    fun addEntity(userId: Long, userName: String): String{
        val key = generateKey(Resources.configuration.key_length)
        Resources.keys.modify {
            this.add(UserEntity(userId, userName, key))
        }
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
}