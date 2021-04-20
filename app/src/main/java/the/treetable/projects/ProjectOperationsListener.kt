package the.treetable.projects

import io.reactivex.schedulers.Schedulers
import the.treetable.data.dto.Project

interface ProjectOperationsListener {
    fun onItemDeleteClicked(project: Project)

    fun onItemEditClicked(project: Project)
}