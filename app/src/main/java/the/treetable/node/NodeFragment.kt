package the.treetable.node

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import the.treetable.R
import the.treetable.data.dto.Node
import the.treetable.data.dto.NodeProp
import the.treetable.databinding.NodeFragmentBinding
import the.treetable.node.recycler.NodeItemAdapter
import the.treetable.node.recycler.VerticalItemDecorator

class NodeFragment : Fragment() {

    private lateinit var binding: NodeFragmentBinding

    private lateinit var viewModel: NodeViewModel

    private lateinit var viewModelFactory: NodeViewFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.node_fragment,
            container,
            false
        )

        val nodeFragmentArgs by navArgs<NodeFragmentArgs>()

        viewModelFactory = NodeViewFactory(nodeFragmentArgs.node, nodeFragmentArgs.name, nodeFragmentArgs.projectId)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(NodeViewModel::class.java)

        viewModel.name.observe(this.viewLifecycleOwner, { node ->
            activity?.title = node
        })

        val adapter = NodeItemAdapter(object: NodeOperationsListener{
            override fun onNodeCreationClicked() {
                createChildren()
            }

            override fun onPropsCreationClicked() {
                createProp()
            }

            override fun onPropsEditionClicked(prop: NodeProp) {
                editProp(prop)
            }

            override fun onPropsDeletionClicked(prop: NodeProp) {
                viewModel.deleteProp(prop.name)
            }

        })
        binding.nodeListView .addItemDecoration(
            VerticalItemDecorator(
                5,
                true
            )
        )
        binding.nodeListView.adapter = adapter
        binding.nodeListView.layoutManager = LinearLayoutManager(context)
        binding.viewModel = viewModel

        viewModel.children.observeForever { list ->
            Log.d("debug", list.toString())
            setChildren(list)
        }
        viewModel.props.observeForever { list ->
            setProps(list)
        }
        binding.floatingActionButton.setOnClickListener {
            createChildren()
        }
        binding.textLabel.text = nodeFragmentArgs.name

        return binding.root
    }

    private fun createChildren() {
        val dialogView =
            LayoutInflater.from(activity).inflate(R.layout.edit_node_dialog, null, false);
        val nodeNameEditText = dialogView.findViewById<EditText>(R.id.node_name_edit_text);
        AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("Create children node")
            .setPositiveButton("Create", DialogInterface.OnClickListener { a, b ->
                viewModel.createChildren(nodeNameEditText.text.toString())
            })
            .create()
            .show();
    }

    private fun createProp() {
        val dialogView =
        LayoutInflater.from(activity).inflate(R.layout.edit_prop_dialog ,null, false);
        val nameEditText = dialogView.findViewById<EditText>(R.id.prop_name);
        val aliasEditText = dialogView.findViewById<EditText>(R.id.prop_alias);
        val valueEditText = dialogView.findViewById<EditText>(R.id.prop_value);
        AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("Create property")
            .setPositiveButton("Create", DialogInterface.OnClickListener { a, b ->
                viewModel.createProp(nameEditText.text.toString(),aliasEditText.text.toString(),valueEditText.text.toString())
            })
            .create()
            .show();
    }

    private fun editProp(prop: NodeProp) {
        val dialogView =
            LayoutInflater.from(activity).inflate(R.layout.edit_prop_dialog ,null, false);
        val nameEditText = dialogView.findViewById<EditText>(R.id.prop_name);
        val aliasEditText = dialogView.findViewById<EditText>(R.id.prop_alias);
        val valueEditText = dialogView.findViewById<EditText>(R.id.prop_value);
        nameEditText.setText(prop.name,TextView.BufferType.EDITABLE)
        nameEditText.isEnabled = false
        aliasEditText.setText(prop.alias,TextView.BufferType.EDITABLE)
        valueEditText.setText(prop.value.toString(),TextView.BufferType.EDITABLE)
        AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("Edit property")
            .setPositiveButton("Save", DialogInterface.OnClickListener { a, b ->
                viewModel.saveProp(nameEditText.text.toString(),aliasEditText.text.toString(),valueEditText.text.toString())
            })
            .create()
            .show();
    }

    private fun setProps(props: List<NodeProp>) {
        (binding.nodeListView.adapter as NodeItemAdapter).setPropsList(props)
    }

    private fun setChildren(children: List<Node>) {
        (binding.nodeListView.adapter as NodeItemAdapter).setNodesList(children)
    }
}