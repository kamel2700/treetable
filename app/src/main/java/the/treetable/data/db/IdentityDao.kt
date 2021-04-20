package the.treetable.data.db

import androidx.room.*

@Dao
interface IdentityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(identity: IdentityModel): Long

    @Update
    fun update(identity: IdentityModel)

    @Delete
    fun delete(identity: IdentityModel)

    @Query("SELECT * FROM local_identities")
    fun getAll(): List<IdentityModel>

    @Query("SELECT * FROM local_identities WHERE id = :id")
    fun getById(id: Long): IdentityModel
}