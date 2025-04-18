package tree
import tree.node.BaseNode

abstract class BaseTree<K: Comparable<K>, V, Node : BaseNode<K, V, Node>>() {
    protected var root: Node? = null
    var size: Long = 0
        private set
    var recentlyKey: K? = null
        private set

    constructor(key: K, value: V) : this() {
        set(key, value)   
    }

    fun Clear() {
        root = null
        size = 0
    }

    protected abstract fun Insert(node: Node)
    protected abstract fun Delete(node: Node)
    protected abstract fun Add(key: K, value: V): Node

    fun Traverse() {
        fun traverseNode(node: Node?) {
            if (node != null) {
                println("Key: ${node.key}, Value: ${node.value}")
                traverseNode(node.left)
                traverseNode(node.right)
            }
        }
        traverseNode(root)
    }

    protected fun Find(key: K): Node? {
        var node = root
        while (node != null) {
            when {
                key < node.key -> node = node.left
                key > node.key -> node = node.right
                else -> return node
            }
        }
        return null
    }

    fun set(key: K, value: V): V? {
        recentlyKey = key
        val node = Find(key)
        return if (node == null) {
            Insert(Add(key, value))
            size++
            null
        } else {
            val result = node.value
            node.value = value
            result
        }
    }

    fun GetKey(value: V): K? {
        var found = false
        var result: K? = null

        fun traverse(node: Node?) {
            if (node == null || found) return
            if (node.value == value) {
                result = node.key
                found = true
                return
            }
            traverse(node.left)
            traverse(node.right)
        }

        traverse(root)
        return result
    }

    fun GetValue(key: K): V? = Find(key)?.value

    fun GetMin(): Pair<K?, V?> {
        var node = root
        while (node?.left != null) {
            node = node.left
        }
        return Pair(node?.key, node?.value)
    }

    fun GetMax(): Pair<K?, V?> {
        var node = root
        while (node?.right != null) {
            node = node.right
        }
        return Pair(node?.key, node?.value)
    }

    fun GetNext(key: K): Pair<K?, V?> {
        var node = root
        var next: Node? = null
        while (node != null) {
            if (node.key > key) {
                next = node
                node = node.left
            } else {
                node = node.right
            }
        }
        return Pair(next?.key, next?.value)
    }

    fun GetPrev(key: K): Pair<K?, V?> {
        var node = root
        var prev: Node? = null
        while (node != null) {
            if (node.key < key) {
                prev = node
                node = node.right
            } else {
                node = node.left
            }
        }
        return Pair(prev?.key, prev?.value)
    }
}