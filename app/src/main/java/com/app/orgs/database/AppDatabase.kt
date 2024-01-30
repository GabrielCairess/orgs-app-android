package com.app.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.orgs.database.converter.Converters
import com.app.orgs.database.dao.ProductDao
import com.app.orgs.model.Product

@Database(entities = [Product::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {

        @Volatile private var db: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return db ?: Room.databaseBuilder(context, AppDatabase::class.java, "orgs.db").build()
                .also {
                    db = it
                }
        }
    }
}