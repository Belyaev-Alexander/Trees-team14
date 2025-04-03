package tree.node

abstract class BaseNode<V, Node>(
    val key: Int,
    var value: V,
    var right: Node? = null,
    var left: Node? = null
)