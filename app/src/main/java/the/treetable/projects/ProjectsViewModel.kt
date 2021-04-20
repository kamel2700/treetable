package the.treetable.projects

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.schedulers.Schedulers
import the.treetable.data.TreeService
import the.treetable.data.dto.Project
import the.treetable.data.response.RestResponse
import javax.inject.Inject

class ProjectsViewModel @Inject constructor(
    private val treeService: TreeService
) : ViewModel(), ProjectOperationsListener {

    //private var treeService : TreeService = _treeService

    private val _projects: MutableLiveData<List<Project>> = MutableLiveData()
    val projects: LiveData<List<Project>> get() = _projects

    init {
        treeService.getProjects().subscribeOn(Schedulers.io())
            .doOnError(this::onFetchError)
            .doOnSuccess(this::onFetchSuccess)
            .subscribe()
    }

    private fun onFetchError(throwable: Throwable) {
    }

    private fun onFetchSuccess(response: RestResponse<List<Project>>) {
        Log.d("debug", response.toString())
        _projects.postValue(response.data)
    }

    fun createProject(project: Project) {
        treeService.createProject(project).subscribeOn(Schedulers.io())
            .doOnSuccess {
                treeService.getProjects().subscribeOn(Schedulers.io())
                    .doOnSuccess(this::onFetchSuccess)
                    .subscribe()
            }
            .doOnError(this::onFetchError)
            .subscribe()
    }

    fun saveProject(project: Project) {
        treeService.updateProject(project.id!!, project)
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                treeService.getProjects().subscribeOn(Schedulers.io())
                    .doOnSuccess(this::onFetchSuccess)
                    .subscribe()
            }
            .doOnError(this::onFetchError)
            .subscribe()
    }

    override fun onItemDeleteClicked(project: Project) {
        treeService.deleteProject(project.id!!).subscribeOn(Schedulers.io())
            .doOnSuccess {
                treeService.getProjects().subscribeOn(Schedulers.io())
                    .doOnSuccess(this::onFetchSuccess)
                    .subscribe()
            }
            .doOnError { }
            .subscribe()
    }

    override fun onItemEditClicked(project: Project) {

    }
}