package com.example.projetremise01.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.time.LocalDateTime

@Dao
interface MailDatabaseDao {

    @Insert
    fun insert(mail: Mail)

    @Update
    fun update(registrationTime: LocalDateTime)

    @Query("SELECT * from daily_sleep_quality_table WHERE mail = :mailAdress")
    fun get(mailAdress: CharSequence): Mail?

    @Query("DELETE FROM daily_sleep_quality_table")
    fun clear()
}
