package com.school_project.superHeroesApp.data.storage.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.school_project.superHeroesApp.data.models.superhero.Content

@Dao
interface CardDao {

    @Query("select * from content")
    suspend fun getAll(): List<Content>

    @Query("select * from content where id=:id")
    suspend fun getById(id: kotlin.Int): Content?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(heroCard: Content)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(heroCard: List<Content>)

}