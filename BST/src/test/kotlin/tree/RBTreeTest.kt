
package tree

import org.junit.jupiter.api.Test
import kotlin.test.*

class RBTreeTest {
    @Test fun create empty tree() {
        val tree = RBTree<Int, String>()
        assertEquals(0, tree.size)
    }
    @Test
    fun insert into empty tree() {
        tree.insert(10, "A")

        assertNotNull(tree.root)
        assertEquals("A", tree.root?.value)
        assertEquals(Color.BLACK, tree.root?.color) // Корень всегда черный
        assertEquals(1, tree.size)
    }

    @Test
    fun insert multiple nodes() {
        tree.apply {
            insert(10, "A")
            insert(5, "B")
            insert(15, "C")
        }

        assertEquals(3, tree.size)
        assertTrue(tree.root?.color == Color.BLACK)
        assertTrue(tree.root?.left?.color == Color.RED || tree.root?.right?.color == Color.RED)
    }

    @Test
    fun insert with recoloring() {
        tree.apply {
            insert(10, "A") // Черный (корень)
            insert(5, "B")  // Красный
            insert(15, "C") // Красный
            insert(3, "D")  // Должен вызвать перекрашивание
        }

        // Проверяем цвета после балансировки
        assertEquals(Color.BLACK, tree.root?.color)
        assertEquals(Color.BLACK, tree.root?.left?.color)
        assertEquals(Color.BLACK, tree.root?.right?.color)
        assertEquals(Color.RED, tree.root?.left?.left?.color)
    }

    @Test
    fun insert duplicate key throws exception() {
        tree.insert(10, "A")
        assertFailsWith<IllegalArgumentException> {
            tree.insert(10, "B")
        }
    }

    @Test
    fun check red-black properties after insert() {
        val keys = listOf(10, 5, 15, 3, 7, 12, 20)
        keys.forEach { tree.insert(it, "Value$it") }

        // 1. Корень черный
        assertEquals(Color.BLACK, tree.root?.color)

        // 2. Нет двух красных узлов подряд
        fun checkRedNodes(node: RedBlackTree.RBNode<Int, String>?) {
            node ?: return
            if (node.color == Color.RED) {
                assertNotEquals(Color.RED, node.left?.color)
                assertNotEquals(Color.RED, node.right?.color)
            }
            checkRedNodes(node.left)
            checkRedNodes(node.right)
        }
        checkRedNodes(tree.root)

        // 3. Все пути содержат одинаковое число черных узлов
        fun countBlackNodes(node: RedBlackTree.RBNode<Int, String>?): Int {
            if (node == null) return 1
            val leftBlacks = countBlackNodes(node.left)
            val rightBlacks = countBlackNodes(node.right)
            assertEquals(leftBlacks, rightBlacks)
            return leftBlacks + if (node.color == Color.BLACK) 1 else 0
        }
        countBlackNodes(tree.root)
    }
}

