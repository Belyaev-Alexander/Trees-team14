package tree.node

abstract class BaseNode<K: Comparable<K>, V, Node>(var k: K? = null, var v: V? = null){
    val key: K? = k
    var value: V? = v
    var left: Node? = null
    var right: Node? = null
    var parent: Node? = null
}