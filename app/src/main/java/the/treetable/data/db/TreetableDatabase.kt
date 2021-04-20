package the.treetable.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [IdentityModel::class], version = 1)
abstract class TreetableDatabase : RoomDatabase(){

    abstract fun identityDao(): IdentityDao
}