package tree

import tree.node.BinNod

class BinTree<K : Comparable<K>, V> : BaseTree<K, V, BinNode<K, V>>() {

    // Добавляем узел
    override fun Add(node: BinNode<K, V>) {
        var current = root
        var parent: BinNode<K, V>? = null

        while (current != null) {
            parent = current
            current = when {
                node.key < current.key -> current.left
                node.key > current.key -> current.right
                else -> return  // Просто выходим, ничего не делая
            }
        }

        node.parent = parent
        if (parent == null) {
            root = node
        } else if (node.key < parent.key) {
            parent.left = node
        } else {
            parent.right = node
        }
    }

    // Удаление
    override fun Delete(node: BinNode<K, V>) {
        val y = if (node.left == null || node.right == null) node else findSuccessor(node)
        val x = y.left ?: y.right

        // Обновляем родителя у x
        x?.parent = y.parent

        // Удаляем y из дерева
        when {
            y.parent == null -> root = x
            y == y.parent?.left -> y.parent?.left = x
            y == y.parent?.right -> y.parent?.right = x
        }

        // Если удаляли successor, копируем его данные в node
        if (y != node) {
            node.key = y.key
            node.value = y.value
        }
    }

    // Поиск преемника
    private fun findSuccessor(node: BinNode<K, V>): BinNode<K, V> {
        node.right?.let { return findMin(it) }

        var current = node
        var parent = node.parent
        while (parent != null && current == parent.right) {
            current = parent
            parent = parent.parent
        }
        return parent ?: throw IllegalStateException("No successor found")
    }
}