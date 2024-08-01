package com.example.banktrack.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.banktrack.data.dao.BankDetailDao
import com.example.banktrack.data.models.BankDetail

@Database(entities = [BankDetail::class], version = 1, exportSchema = false)
public abstract class BankTrackDB: RoomDatabase () {
    abstract fun detailsDao(): BankDetailDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: BankTrackDB? = null

        fun getDatabase(context: Context): BankTrackDB {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BankTrackDB::class.java,
                    "bank_track"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}