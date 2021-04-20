package the.treetable

import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(
    private val _application: TreetableApplication
) {

    @Provides
    fun application() = _application;

    @Provides
    fun context() = _application;
}