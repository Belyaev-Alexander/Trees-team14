package tree

import tree.node.BaseNode
import tree.node.Color

class RedBlackTree<K : Comparable<K>, V> : BaseTree<K, V, RedBlackTree.RBNode<K, V>>() {

    class RBNode<K : Comparable<K>, V>(
        key: K,
        value: V,
        left: RBNode<K, V>? = null,
        right: RBNode<K, V>? = null,
        parent: RBNode<K, V>? = null,
        var color: Color = Color.RED
    ) : BaseNode<K, V, RBNode<K, V>>(key, value, left, right, parent)

    // Основные операции
    override fun Add(key: K, value: V): RBNode<K, V> = RBNode(key, value)

    override fun Insert(node: RBNode<K, V>) {
        standardBSTInsert(node)
        fixViolationAfterInsert(node)
    }

    override fun Delete(node: RBNode<K, V>) {
        val (deletedNode, fixNode) = prepareForDelete(node)
        if (deletedNode.color == Color.BLACK) {
            fixViolationAfterDelete(fixNode)
        }
    }

    // Базовые операции
    private fun standardBSTInsert(node: RBNode<K, V>) {
        var parent: RBNode<K, V>? = null
        var current = root
        while (current != null) {
            parent = current
            current = if (node.key < current.key) current.left else current.right
        }
        node.parent = parent
        when {
            parent == null -> root = node
            node.key < parent.key -> parent.left = node
            else -> parent.right = node
        }
        size++
    }

    private fun prepareForDelete(node: RBNode<K, V>): Pair<RBNode<K, V>, RBNode<K, V>?> {
        var y = node
        var originalColor = y.color
        val x: RBNode<K, V>?

        when {
            node.left == null -> {
                x = node.right
                transplant(node, node.right)
            }
            node.right == null -> {
                x = node.left
                transplant(node, node.left)
            }
            else -> {
                y = findMinimum(node.right!!)
                originalColor = y.color
                x = y.right
                if (y.parent == node) {
                    x?.parent = y
                } else {
                    transplant(y, y.right)
                    y.right = node.right
                    y.right?.parent = y
                }
                transplant(node, y)
                y.left = node.left
                y.left?.parent = y
                y.color = node.color
            }
        }
        size--
        return Pair(y, x)
    }

    // Балансировочные функции
    private fun fixViolationAfterInsert(node: RBNode<K, V>) {
        var current = node
        while (current.parent?.color == Color.RED) {
            val parent = current.parent!!
            val grandparent = parent.parent ?: break

            if (parent == grandparent.left) {
                handleLeftCases(current, parent, grandparent)
            } else {
                handleRightCases(current, parent, grandparent)
            }
        }
        (root as? RBNode<K, V>)?.color = Color.BLACK
    }

    private fun fixViolationAfterDelete(node: RBNode<K, V>?) {
        var x = node
        while (x != root && x?.color == Color.BLACK) {
            if (x == x.parent?.left) {
                handleLeftDeleteCases(x)
            } else {
                handleRightDeleteCases(x)
            }
        }
        x?.color = Color.BLACK
    }

    // Вспомогательные функции
    private fun handleLeftCases(node: RBNode<K, V>, parent: RBNode<K, V>, grandparent: RBNode<K, V>) {
        val uncle = grandparent.right
        when {
            uncle?.color == Color.RED -> {
                handleRedUncle(parent, uncle, grandparent)
                node = grandparent
            }
            node == parent.right -> {
                rotateLeft(parent)
                node = parent
            }
            else -> {
                parent.color = Color.BLACK
                grandparent.color = Color.RED
                rotateRight(grandparent)
            }
        }
    }
    private fun handleRightCases(node: RBNode<K, V>, parent: RBNode<K, V>, grandparent: RBNode<K, V>) {
        val uncle = grandparent.left
        when {
            uncle?.color == Color.RED -> {
                handleRedUncle(parent, uncle, grandparent)
                node = grandparent
            }
            node == parent.left -> {
                rotateRight(parent)
                node = parent
            }
            else -> {
                parent.color = Color.BLACK
                grandparent.color = Color.RED
                rotateLeft(grandparent)
            }
        }
    }

    private fun handleRedUncle(parent: RBNode<K, V>, uncle: RBNode<K, V>, grandparent: RBNode<K, V>) {
        parent.color = Color.BLACK
        uncle.color = Color.BLACK
        grandparent.color = Color.RED
    }

    private fun handleLeftDeleteCases(x: RBNode<K, V>) {
        var sibling = x.parent?.right
        if (sibling?.color == Color.RED) {
            sibling.color = Color.BLACK
            x.parent?.color = Color.RED
            rotateLeft(x.parent!!)
            sibling = x.parent?.right
        }
        if (sibling?.left?.color == Color.BLACK && sibling.right?.color == Color.BLACK) {
            sibling.color = Color.RED
            x = x.parent!!
        } else {
            if (sibling?.right?.color == Color.BLACK) {
                sibling.left?.color = Color.BLACK
                sibling.color = Color.RED
                rotateRight(sibling)
                sibling = x.parent?.right
            }
            sibling?.color = x.parent?.color ?: Color.BLACK
            x.parent?.color = Color.BLACK
            sibling?.right?.color = Color.BLACK
            rotateLeft(x.parent!!)
            x = root!!
        }
    }

    private fun handleRightDeleteCases(x: RBNode<K, V>) {
        // Зеркальная реализация handleLeftDeleteCases
    }

    private fun findMinimum(node: RBNode<K, V>): RBNode<K, V> {
        var current = node
        while (current.left != null) {
            current = current.left!!
        }
        return current
    }

    private fun transplant(u: RBNode<K, V>, v: RBNode<K, V>?) {
        when {
            u.parent == null -> root = v
            u == u.parent?.left -> u.parent?.left = v
            else -> u.parent?.right = v
        }
        v?.parent = u.parent
    }
}