package the.treetable.projects.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.project_item.view.*
import the.treetable.R
import the.treetable.data.TreeService
import the.treetable.data.dto.Project
import the.treetable.projects.ProjectOperationsListener

class ProjectItemAdapter(val operationsListener: ProjectOperationsListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listOfProjects = listOf<Project>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("debug", "We're being inflated!")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.project_item, parent, false)
        val viewHolder = ProjectViewHolder(view)
        view.edit_button.setOnClickListener {
            operationsListener.onItemEditClicked(viewHolder.item!!)
        }
        view.delete_button.setOnClickListener {
            operationsListener.onItemDeleteClicked(viewHolder.item!!)
        }
        return viewHolder;
    }



    override fun getItemCount(): Int = listOfProjects.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val projectViewHolder = viewHolder as ProjectViewHolder
        projectViewHolder.bindView(listOfProjects[position])
    }

    fun setProjectsList(listOfProjects: List<Project>) {
        this.listOfProjects = listOfProjects
        notifyDataSetChanged()
    }
}