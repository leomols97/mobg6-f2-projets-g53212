package com.example.projetremise01.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "mail_infos")
data class MailInfos(

    @ColumnInfo(name= "registrationTime")
    var registrationTime: LocalDateTime = LocalDateTime.now(),

    @PrimaryKey
    @ColumnInfo(name="mail")
    var mail: String = ""
)