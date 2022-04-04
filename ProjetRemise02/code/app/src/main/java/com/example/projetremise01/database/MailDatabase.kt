package com.example.projetremise01.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MailInfos::class], version = 1, exportSchema = false)
abstract class MailDatabase : RoomDatabase() {

    abstract val mailDatabaseDao : MailDatabaseDao
    companion object{
        @Volatile
        private var INSTANCE: MailDatabase? = null

        fun getInstance(context: Context): MailDatabase {
            synchronized(this){
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MailDatabase::class.java,
                        "mail_infos_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}