package com.example.projetremise02.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "mail_infos")
data class MailInfos(
    @PrimaryKey
    @ColumnInfo(name = "mailAdress")
    var mail: String = "",

    @ColumnInfo(name= "registrationTime")
    var registrationTime: LocalDateTime = LocalDateTime.now(),

    @ColumnInfo(name= "password")
    var password: String = ""
)