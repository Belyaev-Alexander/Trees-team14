package tree



class TreapTree<K: Comparable<K>, V : Comparable<V>> : BaseTree<K, V, TreapNode<K, V>>() {

    override fun Add(key: K, value: V): TreapNode<K, V> {
        return TreapNode(key, value)
    }

    override fun Insert(node: TreapNode<K, V>) {
        root = insert(root, node)
    }

    override fun Delete(node: TreapNode<K, V>) {
        root = delete(root, node.key!!)
    }

    

    private fun insert(root: TreapNode<K, V>?, node: TreapNode<K, V>): TreapNode<K, V>? {
        if (root == null) return node

        return if (node.priority > root.priority) {
            val (left, right) = split(root, node.key!!)
            node.left = left
            node.right = right
            node.left?.parent = node
            node.right?.parent = node
            node
        } else {
            if (node.key!! <= root.key!!) {
                root.left = insert(root.left, node)
                root.left?.parent = root
            } else {
                root.right = insert(root.right, node)
                root.right?.parent = root
            }
            root
        }
    }

    private fun delete(root: TreapNode<K, V>?, key: K): TreapNode<K, V>? {
        if (root == null) return null

        return when {
            key < root.key!! -> {
                root.left = delete(root.left, key)
                root.left?.parent = root
                root
            }
            key > root.key!! -> {
                root.right = delete(root.right, key)
                root.right?.parent = root
                root
            }
            else -> merge(root.left, root.right)
        }
    }

    private fun split(node: TreapNode<K, V>?, key: K): Pair<TreapNode<K, V>?, TreapNode<K, V>?> {
        if (node == null) return Pair(null, null)

        return if (node.key!! <= key) {
            val (left, right) = split(node.right, key)
            node.right = left
            node.right?.parent = node
            Pair(node, right)
        } else {
            val (left, right) = split(node.left, key)
            node.left = right
            node.left?.parent = node
            Pair(left, node)
        }
    }

    private fun merge(left: TreapNode<K, V>?, right: TreapNode<K, V>?): TreapNode<K, V>? {
        if (left == null) return right
        if (right == null) return left

        return if (left.priority > right.priority) {
            left.right = merge(left.right, right)
            left.right?.parent = left
            left
        } else {
            right.left = merge(left, right.left)
            right.left?.parent = right
            right
        }
    }

    

    fun contains(key: K): Boolean = Find(key) != null

    fun isEmpty(): Boolean = root == null

    
    fun printTree() {
        fun printNode(node: TreapNode<K, V>?, level: Int = 0) {
            if (node != null) {
                printNode(node.right, level + 1)
                println(" ".repeat(level * 4) + "${node.key}[p:${node.priority}]")
                printNode(node.left, level + 1)
            }
        }
        printNode(root)
        println()
    }
    internal fun testMerge(left: TreapNode<K, V>?, right: TreapNode<K, V>?): TreapNode<K, V>? {
        return merge(left, right)
    }
}