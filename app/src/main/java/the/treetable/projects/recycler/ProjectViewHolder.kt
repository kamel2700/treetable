package the.treetable.projects.recycler

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.project_item.view.*
import the.treetable.data.dto.Project
import the.treetable.projects.ProjectsFragmentDirections

class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var item: Project? = null

    fun bindView(item: Project) {
        this.item = item
        itemView.project_name.text = item.name
        println("project : "+item.toString())
        itemView.setOnClickListener {
            val action = ProjectsFragmentDirections.actionProjectsToNode(item.rootId!!, item.name!!, item.id!!)
            it.findNavController().navigate(action)
        }
    }
}