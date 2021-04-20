package the.treetable.node.recycler

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.node_item.view.*
import the.treetable.R
import the.treetable.data.dto.Node
import the.treetable.node.NodeFragmentDirections

class HeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(text: String, addListener: View.OnClickListener) {
        itemView.findViewById<TextView>(R.id.section_header_text).setText(text,TextView.BufferType.NORMAL)
        itemView.findViewById<ImageButton>(R.id.section_add_button).setOnClickListener {
            addListener.onClick(it)
        }
    }
}