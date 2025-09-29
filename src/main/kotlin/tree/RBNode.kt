import tree.node.BaseNode
import java.awt.Color


class RBNode<K: Comparable<K>, V: Comparable<V>>(k: K? = null, v: V? = null) : BaseNode<K, V, RBNode<K, V>>(k, v) {
    var color: Color = Color.RED
}

