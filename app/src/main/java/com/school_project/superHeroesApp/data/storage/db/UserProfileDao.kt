package com.school_project.superHeroesApp.data.storage.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.school_project.superHeroesApp.data.models.user.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {

    @Query("select * from userprofile where id = 1 ")
    fun getUserProfile(): Flow<UserProfile?>

    @Insert
    fun insert(userProfile: UserProfile)

    @Query("delete from userprofile")
    fun delete()
}