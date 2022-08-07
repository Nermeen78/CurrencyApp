package com.task.currency.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.task.currency.app.data.model.CurrencyInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [CurrencyInfo::class], version = 1)
abstract class HistoricalCurrencyDatabase : RoomDatabase() {

    abstract fun historicalCurrencyInfoDao(): HistoricalCurrencyInfoDao

    companion object {
        @Volatile
        private var INSTANCE: HistoricalCurrencyDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): HistoricalCurrencyDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoricalCurrencyDatabase::class.java,
                    "historical_currency_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(HistoricalCurrencyDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class HistoricalCurrencyDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.historicalCurrencyInfoDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(historicalCurrencyInfoDao: HistoricalCurrencyInfoDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            historicalCurrencyInfoDao.deleteHistoricalCurrencyInfoBefore3Days()

        }
    }
}
