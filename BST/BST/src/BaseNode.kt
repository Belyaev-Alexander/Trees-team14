package tree.node

abstract class BaseNode<K: Comparable<K>, V, Node>(
    val key: K,          // Обобщённый ключ
    var value: V,
    var left: Node? = null,
    var right: Node? = null,
    var parent: Node? = null
)