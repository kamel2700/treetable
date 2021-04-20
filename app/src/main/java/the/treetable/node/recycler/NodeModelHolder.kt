package the.treetable.node.recycler

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.node_item.view.*
import the.treetable.data.dto.Node
import the.treetable.node.NodeFragmentDirections
import the.treetable.projects.ProjectsFragmentDirections

class NodeModelHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(item: Node) {
        itemView.node_name.text = item.name
        itemView.setOnClickListener {
            val action = NodeFragmentDirections.actionNodeToNode(item.id!!, item.name, item.projectId!!)
            it.findNavController().navigate(action)
        }
    }
}