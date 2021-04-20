package the.treetable.node

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import the.treetable.data.dto.Node

class NodeViewFactory (
    private val node_id: Long,
    private val node_name: String,
    private val project_id: Int
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NodeViewModel::class.java)) {
                return NodeViewModel(node_id, node_name, project_id) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}