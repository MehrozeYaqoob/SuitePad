package com.suitepad.datasource.api

import com.suitepad.datasource.db.entity.MenuItemEntity
import io.reactivex.Single
import retrofit2.http.GET

interface MenuItemsAPI {
    @GET( "Rio517/5c95cc6402da8c5e37bc579111e14350/raw/b8ac727658a2aae2a4338d1cb7b1e91aca6288db/z_output.json")
    fun fetchMenuItemData( ): Single<List<MenuItemEntity>>
}