package tree

import tree.node.BaseNode
import kotlin.random.Random

class TreapNode<K: Comparable<K>, V: Comparable<V>>(k: K? = null, v: V? = null) : BaseNode<K, V, TreapNode<K, V>>(k, v) {
    var priority: Int = Random.nextInt()
}