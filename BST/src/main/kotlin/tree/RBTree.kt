package tree

class RBTree<K : Comparable<K>, V> {
    private var root: RBNode<K, V>? = null
    var size = 0
        private set

    fun insert(key: K, value: V) {
        // 1. Создаем новый узел (по умолчанию красный)
        val newNode = RBNode(key, value, color = Color.RED)

        // 2. Стандартная вставка как в BST
        var parent: RBNode<K, V>? = null
        var current = root
        while (current != null) {
            parent = current
            current = when {
                key < current.key -> current.left
                key > current.key -> current.right
                else -> throw IllegalArgumentException("Duplicate key: $key")
            }
        }

        // 3. Привязываем новый узел к родителю
        newNode.parent = parent
        when {
            parent == null -> root = newNode
            key < parent.key -> parent.left = newNode
            else -> parent.right = newNode
        }

        // 4. Балансировка дерева
        fixInsertViolation(newNode)

        // 5. Обязательно красим корень в черный
        root?.color = Color.BLACK
        size++
    }

    private fun fixInsertViolation(node: RBNode<K, V>) {
        var current = node
        while (current.parent?.color == Color.RED) {
            val parent = current.parent!!
            val grandparent = parent.parent ?: break

            if (parent == grandparent.left) {
                val uncle = grandparent.right
                when {
                    uncle?.color == Color.RED -> {
                        // Случай 1: Дядя красный - перекрашиваем
                        parent.color = Color.BLACK
                        uncle.color = Color.BLACK
                        grandparent.color = Color.RED
                        current = grandparent
                    }
                    current == parent.right -> {
                        // Случай 2: Треугольник - делаем левый поворот
                        rotateLeft(parent)
                        current = parent
                    }
                    else -> {
                        // Случай 3: Линия - делаем правый поворот
                        parent.color = Color.BLACK
                        grandparent.color = Color.RED
                        rotateRight(grandparent)
                    }
                }
            } else {
                // Зеркальная обработка для правой стороны
                val uncle = grandparent.left
                when {
                    uncle?.color == Color.RED -> {
                        parent.color = Color.BLACK
                        uncle.color = Color.BLACK
                        grandparent.color = Color.RED
                        current = grandparent
                    }
                    current == parent.left -> {
                        rotateRight(parent)
                        current = parent
                    }
                    else -> {
                        parent.color = Color.BLACK
                        grandparent.color = Color.RED
                        rotateLeft(grandparent)
                    }
                }
            }
        }
    }
    fun delete(key: K) {
        val nodeToDelete = searchNode(key) ?: return

        val (deletedNode, fixNode) = bstDelete(nodeToDelete)

        if (deletedNode.color == Color.BLACK) {
            fixDeleteViolation(fixNode)
        }

        root?.color = Color.BLACK
        size--
    }

    private fun bstDelete(node: RBNode<K, V>): Pair<RBNode<K, V>, RBNode<K, V>?> {
        var y = node
        var yOriginalColor = y.color
        var x: RBNode<K, V>? = null

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
                yOriginalColor = y.color
                x = y.right

                if (y.parent != node) {
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

        return Pair(y, x)
    }

    private fun fixDeleteViolation(node: RBNode<K, V>?) {
        var x = node
        while (x != root && x?.color == Color.BLACK) {
            when {
                x == x.parent?.left -> handleLeftCase(x)
                else -> handleRightCase(x)
            }
        }
        x?.color = Color.BLACK
    }

    private fun handleLeftCase(x: RBNode<K, V>?) {
        var sibling = x?.parent?.right
        if (sibling?.color == Color.RED) {
            // Case 1: Брат красный
            sibling.color = Color.BLACK
            x.parent?.color = Color.RED
            rotateLeft(x.parent!!)
            sibling = x.parent?.right
        }

        if (sibling?.left?.color == Color.BLACK && sibling.right?.color == Color.BLACK) {
            // Case 2: Оба ребёнка брата чёрные
            sibling.color = Color.RED
            x = x.parent
        } else {
            if (sibling?.right?.color == Color.BLACK) {
                // Case 3: Правый ребёнок брата чёрный
                sibling.left?.color = Color.BLACK
                sibling.color = Color.RED
                rotateRight(sibling)
                sibling = x.parent?.right
            }
            // Case 4: Правый ребёнок брата красный
            sibling?.color = x.parent?.color ?: Color.BLACK
            x.parent?.color = Color.BLACK
            sibling?.right?.color = Color.BLACK
            rotateLeft(x.parent!!)
            x = root
        }
    }

    private fun handleRightCase(x: RBNode<K, V>?) {
        var sibling = x?.parent?.left
        if (sibling?.color == Color.RED) {
            // Зеркальный Case 1: Брат красный
            sibling.color = Color.BLACK
            x.parent?.color = Color.RED
            rotateRight(x.parent!!)
            sibling = x.parent?.left
        }

        if (sibling?.right?.color == Color.BLACK && sibling.left?.color == Color.BLACK) {
            // Зеркальный Case 2: Оба ребёнка брата чёрные
            sibling.color = Color.RED
            x = x.parent
        } else {
            if (sibling?.left?.color == Color.BLACK) {
                // Зеркальный Case 3: Левый ребёнок брата чёрный
                sibling.right?.color = Color.BLACK
                sibling.color = Color.RED
                rotateLeft(sibling)
                sibling = x.parent?.left
            }
            // Зеркальный Case 4: Левый ребёнок брата красный
            sibling?.color = x.parent?.color ?: Color.BLACK
            x.parent?.color = Color.BLACK
            sibling?.left?.color = Color.BLACK
            rotateRight(x.parent!!)
            x = root
        }
    }

    // Вспомогательные методы
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
    fun search(key: K): V? { /* ... */ }
}
