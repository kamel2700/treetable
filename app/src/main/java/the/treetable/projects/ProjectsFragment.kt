package the.treetable.projects

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import the.treetable.R
import the.treetable.TreetableApplication
import the.treetable.data.TreeService
import the.treetable.data.dto.Project
import the.treetable.databinding.ProjectsFragmentBinding
import the.treetable.projects.recycler.ProjectItemAdapter
import the.treetable.projects.recycler.VerticalItemDecorator
import javax.inject.Inject

class ProjectsFragment : Fragment() {

    private lateinit var binding: ProjectsFragmentBinding

    @Inject
    lateinit var treeService: TreeService

    @Inject
    lateinit var viewModel: ProjectsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TreetableApplication.APPLICATION.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.projects_fragment,
            container,
            false
        )

        val adapter = ProjectItemAdapter(object : ProjectOperationsListener {
            override fun onItemEditClicked(project: Project) {
                //viewModel.onItemEditClicked(project)
                editProject(project)
            }

            override fun onItemDeleteClicked(project: Project) {
                viewModel.onItemDeleteClicked(project)
            }
        })
        binding.nodeListView.addItemDecoration(
            VerticalItemDecorator(
                5,
                true
            )
        )
        binding.nodeListView.adapter = adapter
        binding.nodeListView.layoutManager = LinearLayoutManager(context)
        binding.viewModel = viewModel

        viewModel.projects.observeForever { list ->
            Log.d("debug", list.toString())
            setProjects(list)
            //(binding.recyclerView.adapter as ProjectItemAdapter).setProjectsList(list)
        }

        binding.floatingActionButton.setOnClickListener {
            createProject()
        }

        return binding.root
    }

    private fun setProjects(projects: List<Project>) {
        (binding.nodeListView.adapter as ProjectItemAdapter).setProjectsList(projects)
    }

    private fun createProject() {
        val dialogView =
            LayoutInflater.from(activity).inflate(R.layout.edit_project_dialog, null, false);
        val projectNameEditText = dialogView.findViewById<EditText>(R.id.project_name_edit_text);
        AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("Create project")
            .setPositiveButton("Create", DialogInterface.OnClickListener { a, b ->
                viewModel.createProject(
                    Project(null, projectNameEditText.text.toString(), null)
                )
            })
            .create()
            .show();
    }

    private fun editProject(project: Project) {
        val dialogView =
            LayoutInflater.from(activity).inflate(R.layout.edit_project_dialog, null, false);
        val projectNameEditText = dialogView.findViewById<EditText>(R.id.project_name_edit_text);
        projectNameEditText.setText(project.name, TextView.BufferType.EDITABLE)
        AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("Update project")
            .setPositiveButton("Save", DialogInterface.OnClickListener { a, b ->
                val project2 =
                    Project(project.id, projectNameEditText.text.toString(), project.rootId)
                viewModel.saveProject(project2)
            })
            .create()
            .show();
    }
}