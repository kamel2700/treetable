package the.treetable.node

import the.treetable.data.dto.Node
import the.treetable.data.dto.NodeProp

interface NodeOperationsListener {

    fun onNodeCreationClicked()

    fun onPropsCreationClicked()

    fun onPropsEditionClicked(prop: NodeProp)

    fun onPropsDeletionClicked(prop: NodeProp)
}