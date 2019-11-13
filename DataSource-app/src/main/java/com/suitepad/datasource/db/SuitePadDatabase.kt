package com.suitepad.datasource.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.suitepad.datasource.db.dao.MenuItemDao
import com.suitepad.datasource.db.entity.MenuItemEntity
import com.suitepad.datasource.executor.AppExecutor
import java.io.InputStream

/**
 * Created by Mehroze on 11/9/2019.
 */
@Database(entities = [MenuItemEntity::class], version = 1, exportSchema = false)
abstract class SuitePadDatabase : RoomDatabase() {

    private val isDatabaseCreated = MutableLiveData<Boolean>()
    val databaseCreated: LiveData<Boolean>
        get() = isDatabaseCreated


    abstract fun menuItemDao(): MenuItemDao

    companion object {
        private var instance: SuitePadDatabase? = null

        val DATABASE_NAME = "suite_pad_db"
        fun getInstance(context: Context, executors: AppExecutor): SuitePadDatabase? {
            if (instance == null) {
                synchronized(SuitePadDatabase::class.java) {
                    if (instance == null) {
                        instance = executors.buildDatabase(context.applicationContext)
                        instance?.updateDatabaseCreated(context.applicationContext)
                        val list = instance?.prepopulateDB(context.applicationContext)
                        insertData(getInstance(context.applicationContext,executors),list!!)
                    }
                }
            }
            return instance
        }

        private fun insertData(database: SuitePadDatabase?, list: List<MenuItemEntity>) {
            database?.menuItemDao()?.insertAll(list)
        }

        private fun AppExecutor.buildDatabase(appContext: Context): SuitePadDatabase {
            return Room.databaseBuilder(appContext, SuitePadDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        ioThread {
                            val database =
                                getInstance(
                                    appContext,
                                    this@buildDatabase
                                )
                            database?.setDatabaseCreated()
                        }
                    }
                }).build()
        }
    }

    /**
     * Check whether the database already exists and expose it via [.getDatabaseCreated]
     */
    private fun updateDatabaseCreated(context: Context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated()
        }
    }

    private fun setDatabaseCreated() {
        isDatabaseCreated.postValue(true)
    }

    fun prepopulateDB(context: Context) : List<MenuItemEntity> {
        val fileName = "menu_items.json"
        val menuItemList = ArrayList<MenuItemEntity>()
        try {
            val inputStream: InputStream = context.assets.open(fileName)
            val inputString = inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JsonParser().parse(inputString)
            for (i in jsonArray.asJsonArray) {
                val type = object : TypeToken<MenuItemEntity>() {}.type
                val menuItemEntity = Gson().fromJson(i.asJsonObject, type) as MenuItemEntity
                menuItemList.add(menuItemEntity)
            }
            return menuItemList
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return menuItemList
    }

}