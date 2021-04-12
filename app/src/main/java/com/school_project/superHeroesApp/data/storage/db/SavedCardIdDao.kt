package com.school_project.superHeroesApp.data.storage.db

import androidx.room.*
import com.school_project.superHeroesApp.data.storage.db.entities.SavedCardIdEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedCardIdDao {

    @Query("select * from SavedCardIdEntity")
    fun getSavedCards(): List<SavedCardIdEntity>


    @Query("select * from SavedCardIdEntity")
    fun getSavedCardFlow(): Flow<List<SavedCardIdEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(ids: List<SavedCardIdEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(id: SavedCardIdEntity)

    @Delete
    fun delete(vararg ids: SavedCardIdEntity)

    @Query("delete from SavedCardIdEntity")
    fun deleteAll()

}