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
    suspend fun getAll(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(vararg product: Product)

    @Delete
    suspend fun delete(vararg product: Product)

    @Query("SELECT * FROM PRODUCT WHERE ID = :id")
    suspend fun getById(id: Long): Product?

    @Query("SELECT * FROM PRODUCT ORDER BY name ASC")
    suspend fun getAllOrderByNameAsc(): List<Product>

    @Query("SELECT * FROM PRODUCT ORDER BY name DESC")
    suspend fun getAllOrderByNameDesc(): List<Product>

    @Query("SELECT * FROM PRODUCT ORDER BY description ASC")
    suspend fun getAllOrderByDescriptionAsc(): List<Product>

    @Query("SELECT * FROM PRODUCT ORDER BY description DESC")
    suspend fun getAllOrderByDescriptionDesc(): List<Product>

    @Query("SELECT * FROM PRODUCT ORDER BY value ASC")
    suspend fun getAllOrderByValueAsc(): List<Product>

    @Query("SELECT * FROM PRODUCT ORDER BY value DESC")
    suspend fun getAllOrderByValueDesc(): List<Product>
}