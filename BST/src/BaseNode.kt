package tree.node

abstract class BaseNode<K: Comparable<K>, V, Node>(
    val key: K,
    var value: V,
    var left: Node? = null,
    var right: Node? = null,
    var parent: Node? = null
)