package the.treetable.node

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.schedulers.Schedulers
import the.treetable.TreetableApplication
import the.treetable.data.TreeService
import the.treetable.data.dto.Node
import the.treetable.data.dto.NodeProp
import the.treetable.data.response.RestResponse
import javax.inject.Inject

class NodeViewModel(
    root_id: Long, root_name: String, project_id: Int
) : ViewModel() {

    @Inject
    lateinit var treeService: TreeService

    private val _node = MutableLiveData<Long>()
    val node: LiveData<Long>
        get() = _node

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _projectId = MutableLiveData<Int>()
    val projectId: LiveData<Int>
        get() = _projectId

    private val _children = MutableLiveData<List<Node>>()
    val children: LiveData<List<Node>>
        get() = _children

    private val _props = MutableLiveData<List<NodeProp>>()
    val props: LiveData<List<NodeProp>> get() = _props

    init {
        TreetableApplication.APPLICATION.appComponent.inject(this)

        _node.value = root_id

        _name.value = root_name

        _projectId.value = project_id

        treeService.getProps(root_id).subscribeOn(Schedulers.io())
            .doOnError(this::onFetchError)
            .doOnSuccess(this::onFetchPropsSuccess)
            .subscribe()
        treeService.getChildrenByParentId(root_id).subscribeOn(Schedulers.io())
            .doOnError(this::onFetchError)
            .doOnSuccess(this::onFetchNodesSuccess)
            .subscribe()
    }

    private fun onFetchError(throwable: Throwable) {
    }

    private fun onFetchPropsSuccess(response: RestResponse<List<NodeProp>>) {
        Log.d("debug props", response.toString())
        _props.postValue(response.data)
    }

    private fun onFetchNodesSuccess(response: RestResponse<List<Node>>) {
        Log.d("debug nodes", response.toString())
        _children.postValue(response.data)
    }

    fun createChildren(name: String) {
        treeService.createNode(Node(null,name,_node.value,projectId.value, listOf(), listOf()))
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                treeService.getChildrenByParentId(_node.value!!).subscribeOn(Schedulers.io())
                    .doOnError(this::onFetchError)
                    .doOnSuccess(this::onFetchNodesSuccess)
                    .subscribe()
            }
            .subscribe()
    }

    fun createProp(name: String, alias: String, value: String) {
        treeService.addProps(_node.value!!, listOf(NodeProp(name, alias, value)))
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                treeService.getProps(_node.value!!).subscribeOn(Schedulers.io())
                    .doOnError(this::onFetchError)
                    .doOnSuccess(this::onFetchPropsSuccess)
                    .subscribe()
            }
            .subscribe()
    }

    fun saveProp(name: String, alias: String, value: String) {
        treeService.updateProps(_node.value!!, listOf(NodeProp(name, alias, value)))
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                treeService.getProps(_node.value!!).subscribeOn(Schedulers.io())
                    .doOnError(this::onFetchError)
                    .doOnSuccess(this::onFetchPropsSuccess)
                    .subscribe()
            }
            .subscribe()
    }

    fun deleteProp(name: String) {
        treeService.deleteProps(_node.value!!, listOf(name))
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                treeService.getProps(_node.value!!).subscribeOn(Schedulers.io())
                    .doOnError(this::onFetchError)
                    .doOnSuccess(this::onFetchPropsSuccess)
                    .subscribe()
            }
            .subscribe()
    }
}