package com.example.projetremise02.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.time.LocalDateTime

@Dao
interface MailDatabaseDao {

    @Insert
    fun insert(mailInfos: MailInfos)

    @Update
    fun update(mailInfos: MailInfos)

    @Query("SELECT * from mail_infos WHERE mailAdress = :mailAdress")
    fun get(mailAdress: String): MailInfos?

    @Query("DELETE FROM mail_infos")
    fun clear()
}
