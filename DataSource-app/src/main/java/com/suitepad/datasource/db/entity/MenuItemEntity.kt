package com.suitepad.datasource.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Mehroze on 11/9/2019.
 */
@Entity(tableName = "menu_item")
data class MenuItemEntity (@PrimaryKey
                           @ColumnInfo(name = "id") var id: String = "",
                           @ColumnInfo(name = "name") var name: String = "",
                           @ColumnInfo(name = "price") var price: Int = 0,
                           @ColumnInfo(name = "type") var type: String = "") {


}