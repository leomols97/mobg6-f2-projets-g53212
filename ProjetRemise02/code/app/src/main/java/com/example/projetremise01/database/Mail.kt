package com.example.projetremise01.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "daily_sleep_quality_table")
data class Mail(

    @ColumnInfo(name= "registrationTime")
    var registrationTime: LocalDateTime = LocalDateTime.now(),

    @ColumnInfo(name="mail")
    var mail: String = ""
)