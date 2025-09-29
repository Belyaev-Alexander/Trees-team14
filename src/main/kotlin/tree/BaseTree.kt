package tree
import tree.node.BaseNode

abstract class BaseTree<K: Comparable<K>, V, Node : BaseNode<K, V, Node>>() {
    var root: Node? = null
    var size: Long = 0
    var recentlyKey: K? = null
        private set
    

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
        var currentNode = root
        while (currentNode != null) {
            val currentKey = currentNode.key
            when {
                currentKey != null && key < currentKey -> currentNode = currentNode.left
                currentKey != null && key > currentKey -> currentNode = currentNode.right
                else -> return currentNode
            }
        }
        return null
    }

    fun set(key: K, value: V?): V? {
        recentlyKey = key

        if (value == null) {
            
            val node = Find(key)
            return if (node != null) {
                Delete(node)
                size--
                node.value
            } else {
                null
            }
        } else {
            
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
    }

    fun GetKey(value: V): K? {
        var result: K? = null
        var found = false

        fun traverse(node: Node?) {
            if (node == null || found) return

            
            if (node.value?.let { it == value } == true) {
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
        var currentNode = root
        while (currentNode?.left != null) {
            currentNode = currentNode.left
        }
        return Pair(currentNode?.key, currentNode?.value)
    }

    fun GetMax(): Pair<K?, V?> {
        var currentNode = root
        while (currentNode?.right != null) {
            currentNode = currentNode.right
        }
        return Pair(currentNode?.key, currentNode?.value)
    }

    fun GetNext(key: K): Pair<K?, V?> {
        var currentNode = root
        var next: Node? = null

        while (currentNode != null) {
            val currentKey = currentNode.key
            when {
                currentKey != null && currentKey > key -> {
                    next = currentNode
                    currentNode = currentNode.left
                }
                else -> {
                    currentNode = currentNode.right
                }
            }
        }
        return Pair(next?.key, next?.value)
    }
    fun GetPrev(key: K): Pair<K?, V?> {
        var currentNode = root
        var prev: Node? = null

        while (currentNode != null) {
            val currentKey = currentNode.key
            when {
                currentKey != null && currentKey < key -> {
                    prev = currentNode
                    currentNode = currentNode.right
                }
                else -> {
                    currentNode = currentNode.left
                }
            }
        }
        return Pair(prev?.key, prev?.value)
    }

    fun remove(key: K): V? = set(key, null)
}
