package the.treetable.node.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.project_item.view.*
import the.treetable.R
import the.treetable.data.dto.Node
import the.treetable.data.dto.NodeProp
import the.treetable.node.NodeOperationsListener

class NodeItemAdapter(val operationsListener: NodeOperationsListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listOfNodes = listOf<Node>()
    private var listOfProps = listOf<NodeProp>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        println("creating view holder " + viewType)
        return if (viewType == 0) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.node_prop_item, parent, false)
            val holder = PropModelHolder(view)
            view.edit_button.setOnClickListener { operationsListener.onPropsEditionClicked(holder.item!!) }
            view.delete_button.setOnClickListener { operationsListener.onPropsDeletionClicked(holder.item!!) }
                return holder
        } else if (viewType == 1) {
            NodeModelHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.node_item, parent, false)
            )
        } else {
            HeaderHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.node_header_item, parent, false)
            )
        }
    }

    override fun getItemCount(): Int = listOfNodes.size + listOfProps.size + 2

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return 2
        if (position == listOfProps.size + 1) return 2
        return if (position < listOfProps.size + 1) {
            0
        } else {
            1
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 0) {
            val nodePropHolder = viewHolder as PropModelHolder
            nodePropHolder.bindView(listOfProps[position - 1])
        } else if (getItemViewType(position) == 1) {
            val nodeViewHolder = viewHolder as NodeModelHolder
            nodeViewHolder.bindView(listOfNodes[position - listOfProps.size - 2])
        } else {
            val sectionViewHolder = viewHolder as HeaderHolder
            if (position == 0) {
                // bind props header
                sectionViewHolder.bind("Props", this::addProp)
            } else {
                // bind children header
                sectionViewHolder.bind("Children", this::addChildrenNode)
            }
        }
    }

    private fun addProp(button: View) {
        operationsListener.onPropsCreationClicked()
    }

    private fun addChildrenNode(button: View) {
        operationsListener.onNodeCreationClicked();
    }

    fun setPropsList(listOfProps: List<NodeProp>) {
        this.listOfProps = listOfProps
        notifyDataSetChanged()
    }

    fun setNodesList(listOfNodes: List<Node>) {
        this.listOfNodes = listOfNodes
        notifyDataSetChanged()
    }
}