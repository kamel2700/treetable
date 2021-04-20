package the.treetable.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
interface ProjectDao {
    @Query("SELECT * from project_table ORDER BY name ASC")
    fun getAlphabetizedProjects(): LiveData<List<ProjectDbModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(projectDbModel: ProjectDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectDbModels: List<ProjectDbModel>)

    @Query("DELETE FROM project_table")
    fun deleteAll()
}
