package com.example.projetremise02.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.projetremise02.database.MailInfos
import java.time.LocalDateTime

@Dao
interface MailDatabaseDao {

    @Insert
    fun insert(mailInfos: MailInfos)

    @Update
    fun update(registrationTime: LocalDateTime)

    @Query("SELECT * from mail_infos WHERE mail = :mailAdress")
    fun get(mailAdress: String): MailInfos

    @Query("DELETE FROM mail_infos")
    fun clear()
}
