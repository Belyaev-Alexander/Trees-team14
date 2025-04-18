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
    fun delete(key: K) { /* ... */ }
    fun search(key: K): V? { /* ... */ }
}
