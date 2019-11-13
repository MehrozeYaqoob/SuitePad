package com.suitepad.datasource.repository

import android.content.Context
import com.suitepad.datasource.api.MenuItemsAPI
import com.suitepad.datasource.db.SuitePadDatabase
import com.suitepad.datasource.db.dao.MenuItemDao
import com.suitepad.datasource.db.entity.MenuItemEntity
import com.suitepad.datasource.executor.AppExecutor
import com.suitepad.datasource.networking.RetrofitProvider
import io.reactivex.Single

/**
 * Created by Mehroze on 11/9/2019.
 */
class DataSourceRepository(application: Context , private val api: MenuItemsAPI = RetrofitProvider.createAPI(
    MenuItemsAPI::class.java)) {

    private lateinit var menuItemDao: MenuItemDao

    init {
        val suitePadDatabase = SuitePadDatabase.getInstance(application, AppExecutor())
        suitePadDatabase?.let { menuItemDao = it.menuItemDao() }
    }

    fun fetchDataFromServer(): Single<List<MenuItemEntity>> {
        return api.fetchMenuItemData()
    }

    fun fetchMenuItemsFromDB(): Single<List<MenuItemEntity>>  {
        return menuItemDao.getAllMenuItems()
    }

}