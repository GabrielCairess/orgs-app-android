package com.app.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.app.orgs.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM PRODUCT")
    fun getAll(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg product: Product)

    @Delete
    fun delete(vararg product: Product)

    @Query("SELECT * FROM PRODUCT WHERE ID = :id")
    fun getById(id: Long): Product?

    @Query("SELECT * FROM PRODUCT ORDER BY name ASC")
    fun getAllOrderByNameAsc(): List<Product>

    @Query("SELECT * FROM PRODUCT ORDER BY name DESC")
    fun getAllOrderByNameDesc(): List<Product>

    @Query("SELECT * FROM PRODUCT ORDER BY description ASC")
    fun getAllOrderByDescriptionAsc(): List<Product>

    @Query("SELECT * FROM PRODUCT ORDER BY description DESC")
    fun getAllOrderByDescriptionDesc(): List<Product>

    @Query("SELECT * FROM PRODUCT ORDER BY value ASC")
    fun getAllOrderByValueAsc(): List<Product>

    @Query("SELECT * FROM PRODUCT ORDER BY value DESC")
    fun getAllOrderByValueDesc(): List<Product>
}