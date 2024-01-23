package com.app.orgs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.orgs.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM PRODUCT")
    fun getAll(): List<Product>

    @Insert
    fun save(vararg product: Product)
}