package com.app.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.orgs.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM PRODUCT")
    fun getAll(): List<Product>

    @Insert
    fun save(vararg product: Product)

    @Delete
    fun delete(vararg product: Product)

    @Update
    fun update(vararg product: Product)
}