package tree
import tree.node.BaseNode

abstract class BaseTree<V, Node : BaseNode<V, Node>>() {
    protected var root: Node? = null
    var size: Long = 0
        private set
    var recentlyKey: Int? = null
        private set

    constructor(key: Int, value: V) : this() {
        set(key, value)
    }
    fun Clear() {
        root = null
        size = 0
    }
    protected abstract fun Insert(node: Node)
    protected abstract fun Delete(node: Node)
    protected abstract fun Add(key: Int, value: V): Node
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
    protected fun Find(key: Int): Node? {
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

    fun set(key: Int, value: V): V? {
        recentlyKey = key
        val node = Find(key)
        if (node == null) {
            Insert(Add(key, value))
            size++
            return null
        }
        val result = node.value
        node.value = value
        return result
    }

    fun GetKey(value: V): Int? {
        var found = false
        var result: Int? = null

        fun meow(node: Node?) {
            if (node == null || found) return
            if (node.value == value) {
                result = node.key
                found = true
                return
            }
            meow(node.left)
            meow(node.right)
        }

        meow(root)
        return result
    }


    fun GetValue(key: Int): V? {
        return Find(key)?.value
    }

    fun GetMin(): Pair<Int?, V?> {
        var node = root
        while (node?.left != null) {
            node = node.left
        }
        return Pair(node?.key, node?.value)
    }

    fun GetMax(): Pair<Int?, V?> {
        var node = root
        while (node?.right != null) {
            node = node.right
        }
        return Pair(node?.key, node?.value)
    }

    fun GetNext(key: Int): Pair<Int?, V?> {
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

    fun GetPrev(key: Int): Pair<Int?, V?> {
        var node = root
        var previous: Node? = null

        while (node != null) {
            if (node.key < key) {
                previous = node
                node = node.right
            } else {
                node = node.left
            }
        }

        return Pair(previous?.key, previous?.value)
    }


}