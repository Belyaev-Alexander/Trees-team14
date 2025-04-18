
package tree

import org.junit.jupiter.api.Test
import kotlin.test.*

class RBTreeTest {
    @Test fun create empty tree() {
        val tree = RBTree<Int, String>()
        assertEquals(0, tree.size)
    }
}

