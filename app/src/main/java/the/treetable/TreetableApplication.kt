package the.treetable

import android.app.Application
import the.treetable.data.RoomModule

class TreetableApplication : Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        APPLICATION = this;
        appComponent = DaggerApplicationComponent.builder()
            .roomModule(RoomModule(this))
            .applicationModule(ApplicationModule(this))
            .build();
    }

    companion object {
        lateinit var APPLICATION: TreetableApplication
    }
}