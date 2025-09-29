package tree

import RBNode
import java.awt.Color

class RBTree<K : Comparable<K>, V : Comparable<V>> : BaseTree<K, V, RBNode<K, V>>() {
    fun testGetNodeColor(key: K): Color? = Find(key)?.color


    override fun Insert(node: RBNode<K, V>) {
        
        insertFixup(node)
    }

    override fun Delete(node: RBNode<K, V>) {
        var y = node
        var yOriginalColor = y.color
        var x: RBNode<K, V>?

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
                y = minimum(node.right as RBNode<K, V>)
                yOriginalColor = y.color
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
        if (yOriginalColor == Color.BLACK) {
            x?.color = Color.BLACK
        }
    }

    override fun Add(key: K, value: V): RBNode<K, V> {
        var parent: RBNode<K, V>? = null
        var current = root

        while (current != null) {
            parent = current
            current = when {
                key < current.key!! -> current.left
                key > current.key!! -> current.right
                else -> {
                    current.value = value
                    return current
                }
            }
        }

        val newNode = RBNode(key, value)
        newNode.parent = parent

        when {
            parent == null -> root = newNode
            key < parent.key!! -> parent.left = newNode
            else -> parent.right = newNode
        }

        return newNode
    }

    private fun insertFixup(z: RBNode<K, V>) {
        var node = z
        while (node.parent?.color == Color.RED) {
            if (node.parent == node.parent?.parent?.left) {
                val y = node.parent?.parent?.right
                when {
                    y?.color == Color.RED -> {
                        node.parent?.color = Color.BLACK
                        y!!.color = Color.BLACK
                        node.parent?.parent?.color = Color.RED
                        node = node.parent?.parent ?: break
                    }
                    node == node.parent?.right -> {
                        node = node.parent ?: break
                        leftRotate(node)
                    }
                    else -> {
                        node.parent?.color = Color.BLACK
                        node.parent?.parent?.color = Color.RED
                        rightRotate(node.parent?.parent ?: break)
                    }
                }
            } else {
                val y = node.parent?.parent?.left
                when {
                    y?.color == Color.RED -> {
                        node.parent?.color = Color.BLACK
                        y!!.color = Color.BLACK
                        node.parent?.parent?.color = Color.RED
                        node = node.parent?.parent ?: break
                    }
                    node == node.parent?.left -> {
                        node = node.parent ?: break
                        rightRotate(node)
                    }
                    else -> {
                        node.parent?.color = Color.BLACK
                        node.parent?.parent?.color = Color.RED
                        leftRotate(node.parent?.parent ?: break)
                    }
                }
            }
        }
        root?.color = Color.BLACK
    }


    private fun leftRotate(x: RBNode<K, V>) {
        val y = x.right ?: return
        x.right = y.left
        y.left?.parent = x
        y.parent = x.parent

        when {
            x.parent == null -> root = y
            x == x.parent?.left -> x.parent?.left = y
            else -> x.parent?.right = y
        }

        y.left = x
        x.parent = y
    }

    private fun rightRotate(y: RBNode<K, V>) {
        val x = y.left ?: return
        y.left = x.right
        x.right?.parent = y
        x.parent = y.parent

        when {
            y.parent == null -> root = x
            y == y.parent?.right -> y.parent?.right = x
            else -> y.parent?.left = x
        }

        x.right = y
        y.parent = x
    }

    private fun transplant(u: RBNode<K, V>, v: RBNode<K, V>?) {
        when {
            u.parent == null -> root = v
            u == u.parent?.left -> u.parent?.left = v
            else -> u.parent?.right = v
        }
        v?.parent = u.parent
    }

    private fun minimum(node: RBNode<K, V>): RBNode<K, V> {
        var current = node
        while (current.left != null) {
            current = current.left!!
        }
        return current
    }

    
    fun isRBTree(): Boolean {
        if (root == null) return true
        if (root?.color != Color.BLACK) return false

        val blackCount = mutableListOf<Int>()
        return checkRBProperties(root, 0, blackCount)
    }

    private fun checkRBProperties(
        node: RBNode<K, V>?,
        currentBlackCount: Int,
        blackCount: MutableList<Int>
    ): Boolean {
        if (node == null) {
            blackCount.add(currentBlackCount)
            return true
        }
        if (node.color == Color.RED &&
            (node.left?.color == Color.RED || node.right?.color == Color.RED)) {
            return false
        }

        val newBlackCount = currentBlackCount + if (node.color == Color.BLACK) 1 else 0

        return checkRBProperties(node.left, newBlackCount, blackCount) &&
                checkRBProperties(node.right, newBlackCount, blackCount)
    }
}