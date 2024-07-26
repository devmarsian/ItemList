package com.testtask.ideaplatform

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM item WHERE name LIKE :name")
    fun findItemsByName(name: String): Flow<List<Item>>

    @Query("SELECT * FROM item")
    fun getAllItems(): Flow<List<Item>>

    @Update
    suspend fun updateItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)
}

class ItemRepositoryImpl (private val itemDao: ItemDao) {

     fun findItemsByName(name: String): Flow<List<Item>> {
         return itemDao.findItemsByName(name)
     }

    fun getAllItems(): Flow<List<Item>> {
        return itemDao.getAllItems()
    }

    suspend fun updateItem(item: Item) {
        itemDao.updateItem(item)
    }

    suspend fun deleteItem(item: Item) {
        itemDao.deleteItem(item)
    }
}