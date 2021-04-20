package the.treetable.node.recycler

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.node_item.view.*
import kotlinx.android.synthetic.main.node_prop_item.view.*
import the.treetable.data.dto.Node
import the.treetable.data.dto.NodeProp
import the.treetable.projects.ProjectsFragmentDirections

class PropModelHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var item: NodeProp? = null

    fun bindView(item: NodeProp) {
        this.item = item
        itemView.prop_header_text.text = item.name +":"+item.value.javaClass
        itemView.prop_alias_text.text = item.alias
        itemView.prop_value_text.text = item.value.toString()
//        itemView.setOnClickListener {
//            //val action = ProjectsFragmentDirections.actionProjectsToNode(item.id!!, item.name)
//            //it.findNavController().navigate(action)
//        }
    }
}