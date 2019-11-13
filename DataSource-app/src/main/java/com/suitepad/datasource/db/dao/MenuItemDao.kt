package com.suitepad.datasource.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.suitepad.datasource.db.entity.MenuItemEntity
import io.reactivex.Single

/**
 * Created by Mehroze on 11/9/2019.
 */
@Dao
interface MenuItemDao : BaseDao<MenuItemEntity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<MenuItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingle(menuItemEntity: MenuItemEntity)

    @Query("SELECT * from menu_item")
    fun getAllMenuItems(): Single<List<MenuItemEntity>>

    @Query("Delete from menu_item WHERE id = :id")
    fun deleteMenuItem(id: Long)

    @Query("Delete from menu_item")
    fun deleteAllMenuItems()

    @Query("SELECT * from menu_item WHERE id = :id")
    fun getMenuItemById(id: Long): Single<MenuItemEntity>

}