
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

    @Test
    fun left-case1 - red sibling becomes black() {
        val tree = RedBlackTree<Int, String>().apply {
            insert(50, "Root")  // B
            insert(70, "A")     // B
            insert(30, "B")     // R (брат красный)
            insert(40, "C")     // B
        }

        tree.delete(70) // Вызовет левый случай 1
        assertEquals(30, tree.root?.left?.key)
        assertEquals(Color.BLACK, tree.root?.left?.color)
    }

    @Test
    fun left-case2 - black sibling with black children() {
        val tree = RedBlackTree<Int, String>().apply {
            insert(50, "Root")  // B
            insert(70, "A")     // B
            insert(30, "B")     // B (брат и дети чёрные)
        }

        tree.delete(70) // Вызовет левый случай 2
        assertEquals(30, tree.root?.left?.key)
        assertEquals(Color.RED, tree.root?.left?.color)
    }

    @Test
    fun right-case1 - red sibling becomes black() {
        val tree = RedBlackTree<Int, String>().apply {
            insert(50, "Root")  // B
            insert(30, "A")     // B
            insert(70, "B")     // R (брат красный)
            insert(60, "C")     // B
        }

        tree.delete(30) // Вызовет правый случай 1
        assertEquals(70, tree.root?.right?.key)
        assertEquals(Color.BLACK, tree.root?.right?.color)
    }

    @Test
    fun right-case2 - black sibling with black children() {
        val tree = RedBlackTree<Int, String>().apply {
            insert(50, "Root")  // B
            insert(30, "A")     // B
            insert(70, "B")     // B (брат и дети чёрные)
        }

        tree.delete(30) // Вызовет правый случай 2
        assertEquals(70, tree.root?.right?.key)
        assertEquals(Color.RED, tree.root?.right?.color)
    }

    @Test
    fun left-cases3-4 - rotations() {
        val tree = RedBlackTree<Int, String>().apply {
            insert(50, "Root")  // B
            insert(70, "A")     // B
            insert(30, "B")     // B
            insert(35, "C")     // R (вызовет случай 3 → 4)
        }

        tree.delete(70)
        assertEquals(35, tree.root?.left?.key)
        assertEquals(Color.BLACK, tree.root?.left?.color)
    }

    @Test
    fun right-cases3-4 - mirror rotations() {
        val tree = RedBlackTree<Int, String>().apply {
            insert(50, "Root")  // B
            insert(30, "A")     // B
            insert(70, "B")     // B
            insert(65, "C")     // R (вызовет зеркальные 3 → 4)
        }

        tree.delete(30)
        assertEquals(65, tree.root?.right?.key)
        assertEquals(Color.BLACK, tree.root?.right?.color)
    }

    @Test
    fun verify invariants after all cases() {
        val tree = RedBlackTree<Int, String>().apply {
            insert(50, "Root")
            insert(30, "A")
            insert(70, "B")
            insert(20, "C")
            insert(40, "D")
            insert(60, "E")
        }

        // Последовательно вызываем все случаи
        tree.delete(20) // Может вызвать левый случай 1/2
        tree.delete(60) // Может вызвать правый случай 1/2
        tree.delete(40) // Может вызвать повороты

        assertTrue(verifyInvariants(tree.root))
    }
    @Test
    fun search returns correct values or null() {
        val tree = RedBlackTree<Int, String>().apply {
            insert(50, "A")
            insert(30, "B")
            insert(70, "C")
        }

        assertAll(
            { assertEquals("A", tree.search(50)) },  // Корень
            { assertEquals("B", tree.search(30)) },  // Левый потомок
            { assertNull(tree.search(99)) }          // Несуществующий ключ
        )
    }
}

