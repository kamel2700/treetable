package the.treetable.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import the.treetable.data.db.IdentityDao
import the.treetable.data.db.TreetableDatabase
import javax.inject.Singleton

@Module
class RoomModule(context: Context) {

    private val _treetableDatabase: TreetableDatabase =
        Room.databaseBuilder(context, TreetableDatabase::class.java, "treetable_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun treetableDatabase(): TreetableDatabase {
        return _treetableDatabase
    }

    @Provides
    @Singleton
    fun identityDao(): IdentityDao = _treetableDatabase.identityDao();

}