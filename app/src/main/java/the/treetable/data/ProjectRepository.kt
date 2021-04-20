package the.treetable.data

import io.reactivex.schedulers.Schedulers
import the.treetable.data.db.ProjectDao

class ProjectRepository(
    private val projectDao: ProjectDao,
    var service: TreeService
) {

    fun testNetwork(): Boolean = service.getProjects()
        .subscribeOn(Schedulers.io())
        .map {
            true
        }.onErrorReturn {
            false
        }.blockingGet()
}