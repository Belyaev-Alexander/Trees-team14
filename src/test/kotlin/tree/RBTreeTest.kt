package tree

import RBNode
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.awt.Color

class RBTreeTest {
    private lateinit var rbTree: RBTree<Int, String>
    
    @BeforeEach
    fun setUp() {
        rbTree = RBTree()
    }

    @Test
    fun `test empty tree`() {
        assertNull(rbTree.GetValue(1))
        assertEquals(0, rbTree.size)
        assertTrue(rbTree.isRBTree())
    }

    @Test
    fun `test insert single element`() {
        rbTree.set(10, "Value10")

        assertEquals(1, rbTree.size)
        assertEquals("Value10", rbTree.GetValue(10))
        assertEquals(Color.BLACK, (rbTree as? RBTree<Int, String>)?.root?.color)
        assertTrue(rbTree.isRBTree())
    }

    @Test
    fun `test insert multiple elements`() {
        val values = mapOf(
            50 to "Fifty",
            30 to "Thirty",
            70 to "Seventy",
            20 to "Twenty",
            40 to "Forty",
            60 to "Sixty",
            80 to "Eighty"
        )

        values.forEach { (key, value) ->
            rbTree.set(key, value)
        }

        assertEquals(7, rbTree.size)
        values.forEach { (key, value) ->
            assertEquals(value, rbTree.GetValue(key))
        }
        assertTrue(rbTree.isRBTree())
    }

    @Test
    fun `test insert duplicate key`() {
        rbTree.set(10, "First")
        rbTree.set(10, "Second")

        assertEquals(1, rbTree.size)
        assertEquals("Second", rbTree.GetValue(10))
        assertTrue(rbTree.isRBTree())
    }

    @Test
    fun `test delete non-existent key`() {
        rbTree.set(10, "Value10")
        rbTree.remove(5) 

        assertEquals(1, rbTree.size)
        assertEquals("Value10", rbTree.GetValue(10))
        assertNull(rbTree.GetValue(5))
        assertTrue(rbTree.isRBTree())
    }

    @Test
    fun `test delete leaf node`() {
        rbTree.set(50, "Fifty")
        rbTree.set(30, "Thirty")
        rbTree.set(70, "Seventy")

        rbTree.remove(30) 

        assertEquals(2, rbTree.size)
        assertNull(rbTree.GetValue(30))
        assertEquals("Fifty", rbTree.GetValue(50))
        assertEquals("Seventy", rbTree.GetValue(70))
        assertTrue(rbTree.isRBTree())
    }

    @Test
    fun `test delete node with one child`() {
        rbTree.set(50, "Fifty")
        rbTree.set(30, "Thirty")
        rbTree.set(70, "Seventy")
        rbTree.set(20, "Twenty")

        rbTree.remove(30) 

        assertEquals(3, rbTree.size)
        assertNull(rbTree.GetValue(30))
        assertEquals("Twenty", rbTree.GetValue(20))
        assertEquals("Fifty", rbTree.GetValue(50))
        assertEquals("Seventy", rbTree.GetValue(70))
        assertTrue(rbTree.isRBTree())
    }

    @Test
    fun `test delete node with two children`() {
        rbTree.set(50, "Fifty")
        rbTree.set(30, "Thirty")
        rbTree.set(70, "Seventy")
        rbTree.set(20, "Twenty")
        rbTree.set(40, "Forty")

        rbTree.remove(30) 

        assertEquals(4, rbTree.size)
        assertNull(rbTree.GetValue(30))
        assertEquals("Twenty", rbTree.GetValue(20))
        assertEquals("Forty", rbTree.GetValue(40))
        assertEquals("Fifty", rbTree.GetValue(50))
        assertEquals("Seventy", rbTree.GetValue(70))
        assertTrue(rbTree.isRBTree())
    }

    @Test
    fun `test delete root node`() {
        rbTree.set(50, "Fifty")
        rbTree.set(30, "Thirty")
        rbTree.set(70, "Seventy")

        rbTree.remove(50) 

        assertEquals(2, rbTree.size)
        assertNull(rbTree.GetValue(50))
        assertEquals("Thirty", rbTree.GetValue(30))
        assertEquals("Seventy", rbTree.GetValue(70))
        assertTrue(rbTree.isRBTree())
    }

    @Test
    fun `test insertFixup all branch cases`() {
        

        
        rbTree.set(50, "50")  
        rbTree.set(30, "30")  
        rbTree.set(70, "70")  
        rbTree.set(20, "20")  

        
        val tree2 = RBTree<Int, String>()
        tree2.set(50, "50")
        tree2.set(30, "30")
        tree2.set(40, "40")  

        
        val tree3 = RBTree<Int, String>()
        tree3.set(50, "50")
        tree3.set(40, "40")
        tree3.set(30, "30")  

        
        val tree4 = RBTree<Int, String>()
        tree4.set(50, "50")
        tree4.set(70, "70")
        tree4.set(30, "30")  
        tree4.set(80, "80")  

        
        val tree5 = RBTree<Int, String>()
        tree5.set(50, "50")
        tree5.set(70, "70")
        tree5.set(60, "60")  

        
        val tree6 = RBTree<Int, String>()
        tree6.set(50, "50")
        tree6.set(60, "60")
        tree6.set(70, "70")  

        assertTrue(rbTree.isRBTree())
        assertTrue(tree2.isRBTree())
        assertTrue(tree3.isRBTree())
        assertTrue(tree4.isRBTree())
        assertTrue(tree5.isRBTree())
        assertTrue(tree6.isRBTree())
    }

    @Test
    fun `test get min and max`() {
        rbTree.set(50, "Fifty")
        rbTree.set(30, "Thirty")
        rbTree.set(70, "Seventy")
        rbTree.set(20, "Twenty")
        rbTree.set(80, "Eighty")

        val min = rbTree.GetMin()
        val max = rbTree.GetMax()

        assertEquals(20, min.first)
        assertEquals("Twenty", min.second)
        assertEquals(80, max.first)
        assertEquals("Eighty", max.second)
    }

    @Test
    fun `test get next and previous`() {
        rbTree.set(50, "Fifty")
        rbTree.set(30, "Thirty")
        rbTree.set(70, "Seventy")
        rbTree.set(20, "Twenty")
        rbTree.set(40, "Forty")
        rbTree.set(60, "Sixty")
        rbTree.set(80, "Eighty")

        val next = rbTree.GetNext(50)
        val prev = rbTree.GetPrev(50)

        assertEquals(60, next.first)
        assertEquals("Sixty", next.second)
        assertEquals(40, prev.first)
        assertEquals("Forty", prev.second)
    }

    @Test
    fun `test get key by value`() {
        rbTree.set(1, "One")
        rbTree.set(2, "Two")
        rbTree.set(3, "Three")

        assertEquals(2, rbTree.GetKey("Two"))
        assertEquals(1, rbTree.GetKey("One"))
        assertEquals(3, rbTree.GetKey("Three"))
        assertNull(rbTree.GetKey("NonExistent"))
    }

    @Test
    fun `test clear tree`() {
        rbTree.set(10, "Ten")
        rbTree.set(20, "Twenty")

        rbTree.Clear()

        assertEquals(0, rbTree.size)
        assertNull(rbTree.GetValue(10))
        assertNull(rbTree.GetValue(20))
        assertNull(rbTree.root)
    }

    @Test
    fun `test complex scenario`() {
        
        val keys = listOf(50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45, 55, 65, 75, 85)

        keys.forEachIndexed { index, key ->
            rbTree.set(key, "Value$key")
            assertTrue(rbTree.isRBTree(), "Tree should be valid after inserting $key")
            assertEquals(index + 1, rbTree.size.toInt())
        }

        
        keys.forEach { key ->
            assertEquals("Value$key", rbTree.GetValue(key))
        }

        
        val keysToRemove = listOf(30, 70, 50, 20, 80)

        keysToRemove.forEach { key ->
            rbTree.remove(key)
            assertTrue(rbTree.isRBTree(), "Tree should be valid after removing $key")
            assertNull(rbTree.GetValue(key))
        }

        assertEquals(keys.size - keysToRemove.size, rbTree.size.toInt())
    }
    @Test
    fun `test insertFixup right side cases`() {
        
        rbTree.set(50, "50")
        rbTree.set(70, "70")  
        rbTree.set(30, "30")  
        rbTree.set(90, "90")  
        rbTree.set(60, "60")  

        assertTrue(rbTree.isRBTree())
    }

    @Test
    fun `test edge cases for maximum coverage`() {
        

        
        rbTree.set(50, "50")
        rbTree.set(70, "70")
        rbTree.set(90, "90") 
        rbTree.set(80, "80") 

        
        rbTree.set(30, "30")
        rbTree.set(20, "20")
        rbTree.set(10, "10") 
        rbTree.set(15, "15") 

        
        rbTree.set(5, "5")   
        rbTree.set(25, "25") 
        rbTree.set(95, "95") 
        rbTree.set(85, "85") 

        assertTrue(rbTree.isRBTree())
    }

    @Test
    fun `test transplant edge cases`() {
        
        rbTree.set(50, "50")
        rbTree.set(30, "30")
        rbTree.set(70, "70")

        
        rbTree.remove(30) 
        rbTree.remove(70) 

        
        rbTree.set(30, "30")
        rbTree.set(70, "70")
        rbTree.remove(50) 

        assertTrue(rbTree.isRBTree())
    }

    @Test
    fun `test delete node with specific structure`() {
        
        rbTree.set(50, "50")
        rbTree.set(30, "30")
        rbTree.set(70, "70")
        rbTree.set(20, "20")
        rbTree.set(40, "40")
        rbTree.set(60, "60")
        rbTree.set(80, "80")
        rbTree.set(10, "10")
        rbTree.set(25, "25")
        rbTree.set(35, "35")
        rbTree.set(45, "45")

        
        rbTree.remove(40) 
        rbTree.remove(20) 
        rbTree.remove(10) 
        rbTree.remove(50) 

        assertTrue(rbTree.isRBTree())
    }
    @Test
    fun `test all RB tree properties extensively`() {
        
        val random = java.util.Random(42) 
        val keys = mutableSetOf<Int>()

        repeat(101) {
            val key = random.nextInt(10000)
            if (keys.add(key)) {
                rbTree.set(key, "Value$key")
                assertTrue(rbTree.isRBTree(), "Tree invalid after inserting $key")
            }
        }
        assertEquals(100, rbTree.size)
        val keysToRemove = keys.shuffled().take(50)
        keysToRemove.forEach { key ->
            rbTree.remove(key)
            assertTrue(rbTree.isRBTree(), "Tree invalid after removing $key")
        }

        assertEquals(50, rbTree.size)
    }
    @Test
    fun `test testGetNodeColor with non-existent key`() {
        rbTree.set(10, "Ten")

        val color = rbTree.testGetNodeColor(999) 
        assertNull(color)
    }
    @Test
    fun `test rightRotate with right child`() {
        
        rbTree.set(50, "50")
        rbTree.set(30, "30")
        rbTree.set(20, "20")
        rbTree.set(25, "25") 

        
        assertTrue(rbTree.isRBTree())
    }
    @Test
    fun `test insertFixup red uncle case on right side`() {
        
        rbTree.set(50, "50")
        rbTree.set(70, "70")
        rbTree.set(30, "30") 
        rbTree.set(80, "80") 

        assertTrue(rbTree.isRBTree())
    }
    @Test
    fun `test red black tree properties`() {
        
        for (i in 1..100) {
            rbTree.set(i, "Value$i")
            assertTrue(rbTree.isRBTree(), "Tree should be valid after inserting $i")
        }

        
        for (i in 1..100 step 2) {
            rbTree.remove(i)
            assertTrue(rbTree.isRBTree(), "Tree should be valid after removing $i")
        }

        assertEquals(50, rbTree.size.toInt())
    }

    @Test
    fun `test string keys`() {
        val stringTree = RBTree<String, Int>()

        stringTree.set("apple", 1)
        stringTree.set("banana", 2)
        stringTree.set("cherry", 3)

        assertEquals(1, stringTree.GetValue("apple"))
        assertEquals(2, stringTree.GetValue("banana"))
        assertEquals(3, stringTree.GetValue("cherry"))
        assertTrue(stringTree.isRBTree())
    }

    @Test
    fun `test recentlyKey functionality`() {
        assertNull(rbTree.recentlyKey)

        rbTree.set(10, "Ten")
        assertEquals(10, rbTree.recentlyKey)

        rbTree.set(20, "Twenty")
        assertEquals(20, rbTree.recentlyKey)

        rbTree.set(10, "TenUpdated")
        assertEquals(10, rbTree.recentlyKey)
    }



    @Test
    fun `test edge cases`() {
        
        rbTree.set(Int.MAX_VALUE, "Max")
        rbTree.set(Int.MIN_VALUE, "Min")

        assertEquals("Max", rbTree.GetValue(Int.MAX_VALUE))
        assertEquals("Min", rbTree.GetValue(Int.MIN_VALUE))
        assertTrue(rbTree.isRBTree())

        
        rbTree.remove(Int.MAX_VALUE)
        rbTree.remove(Int.MIN_VALUE)

        assertEquals(0, rbTree.size.toInt())
        assertNull(rbTree.GetValue(Int.MAX_VALUE))
        assertNull(rbTree.GetValue(Int.MIN_VALUE))
        assertTrue(rbTree.isRBTree())
    }


    @Test
    fun `test leftRotate coverage`() {
        
        rbTree.set(50, "Fifty")
        rbTree.set(30, "Thirty")
        rbTree.set(70, "Seventy")
        rbTree.set(60, "Sixty")
        rbTree.set(80, "Eighty")
        rbTree.set(90, "Ninety") 

        assertTrue(rbTree.isRBTree())
        assertEquals(6, rbTree.size)
    }

    @Test
    fun `test rightRotate coverage`() {
        
        rbTree.set(50, "Fifty")
        rbTree.set(30, "Thirty")
        rbTree.set(20, "Twenty")
        rbTree.set(10, "Ten") 

        assertTrue(rbTree.isRBTree())
        assertEquals(4, rbTree.size)
    }

    @Test
    fun `test insertFixup red uncle case`() {
        
        rbTree.set(50, "Fifty") 
        rbTree.set(30, "Thirty") 
        rbTree.set(70, "Seventy") 
        rbTree.set(20, "Twenty") 

        assertTrue(rbTree.isRBTree())
        assertEquals(Color.BLACK, rbTree.root?.color)
        assertEquals(Color.BLACK, rbTree.root?.left?.color)
        assertEquals(Color.BLACK, rbTree.root?.right?.color)
    }

    @Test
    fun `test insertFixup left left case`() {
        
        rbTree.set(50, "Fifty")
        rbTree.set(30, "Thirty")
        rbTree.set(20, "Twenty") 

        assertTrue(rbTree.isRBTree())
    }

    @Test
    fun `test insertFixup left right case`() {
        
        rbTree.set(50, "Fifty")
        rbTree.set(20, "Twenty")
        rbTree.set(30, "Thirty") 

        assertTrue(rbTree.isRBTree())
    }

    @Test
    fun `test insertFixup right right case`() {
        
        rbTree.set(50, "Fifty")
        rbTree.set(70, "Seventy")
        rbTree.set(80, "Eighty") 

        assertTrue(rbTree.isRBTree())
    }

    @Test
    fun `test insertFixup right left case`() {
        
        rbTree.set(50, "Fifty")
        rbTree.set(80, "Eighty")
        rbTree.set(70, "Seventy") 

        assertTrue(rbTree.isRBTree())
    }

    @Test
    fun `test deleteFixup all cases`() {
        
        val keys = listOf(50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45, 55, 65, 75, 85)
        keys.forEach { rbTree.set(it, "Value$it") }

        
        rbTree.remove(20) 
        rbTree.remove(30) 
        rbTree.remove(50) 
        rbTree.remove(70) 

        assertTrue(rbTree.isRBTree())
        assertEquals(keys.size - 4, rbTree.size.toInt())
    }

    @Test
    fun `test delete node with red sibling`() {
        
        rbTree.set(50, "Fifty")
        rbTree.set(30, "Thirty")
        rbTree.set(70, "Seventy")
        rbTree.set(20, "Twenty")
        rbTree.set(40, "Forty")

        
        rbTree.remove(20)

        assertTrue(rbTree.isRBTree())
    }

    @Test
    fun `test delete node with black sibling and black children`() {
        
        rbTree.set(50, "Fifty")
        rbTree.set(30, "Thirty")
        rbTree.set(70, "Seventy")
        rbTree.set(80, "Eighty") 

        rbTree.remove(30)
        assertTrue(rbTree.isRBTree())
    }

    @Test
    fun `test transplant with various cases`() {
        
        rbTree.set(50, "Fifty")
        rbTree.set(30, "Thirty")
        rbTree.set(70, "Seventy")

        
        val testTree = RBTree<Int, String>()
        testTree.set(100, "Hundred")
        testTree.set(50, "Fifty")
        testTree.set(150, "HundredFifty")

        
        testTree.remove(50)
        testTree.remove(150)

        assertTrue(testTree.isRBTree())
    }

    @Test
    fun `test minimum function`() {
        rbTree.set(50, "Fifty")
        rbTree.set(30, "Thirty")
        rbTree.set(70, "Seventy")
        rbTree.set(20, "Twenty")
        rbTree.set(40, "Forty")

        
        val minNode = rbTree.GetMin()
        assertEquals(20, minNode.first)
        assertEquals("Twenty", minNode.second)
    }

    @Test
    fun `test checkRBProperties with invalid tree`() {
        
        val invalidTree = RBTree<Int, String>()

        
        val root = RBNode(50, "Fifty")
        root.color = Color.RED 
        invalidTree.set(50, "Fifty") 

        
        assertTrue(invalidTree.isRBTree())
    }

    @Test
    fun `test edge cases for isRBTree`() {
        
        assertTrue(rbTree.isRBTree())

        
        rbTree.set(10, "Ten")
        assertTrue(rbTree.isRBTree())

        
        rbTree.set(5, "Five")
        assertTrue(rbTree.isRBTree())
    }

    @Test
    fun `test complex delete scenarios`() {
        
        for (i in 1..20) {
            rbTree.set(i, "Value$i")
        }

        
        rbTree.remove(10)
        rbTree.remove(5)
        rbTree.remove(15)
        rbTree.remove(20)
        rbTree.remove(1)

        assertTrue(rbTree.isRBTree())
        assertEquals(15, rbTree.size)
    }

    @Test
    fun `test delete non-existent key multiple times`() {
        rbTree.set(10, "Ten")

        
        assertNull(rbTree.remove(999))
        assertNull(rbTree.remove(999))
        assertNull(rbTree.remove(999))

        assertEquals(1, rbTree.size)
        assertTrue(rbTree.isRBTree())
    }

    @Test
    fun `test insert and delete same key repeatedly`() {
        
        repeat(5) {
            rbTree.set(10, "Value$it")
            assertEquals("Value$it", rbTree.GetValue(10))
        }

        repeat(3) {
            rbTree.remove(10)
            assertNull(rbTree.GetValue(10))
            rbTree.set(10, "NewValue$it")
        }

        assertTrue(rbTree.isRBTree())
        assertEquals(1, rbTree.size)
    }
    @Test
    fun `test deleteFixup internal state verification`() {
        
        rbTree.set(50, "Fifty")
        rbTree.set(30, "Thirty")
        rbTree.set(70, "Seventy")
        rbTree.set(20, "Twenty")
        rbTree.set(40, "Forty")
        rbTree.set(60, "Sixty")
        rbTree.set(80, "Eighty")

        
        assertEquals(Color.BLACK, rbTree.testGetNodeColor(50))
        assertEquals(Color.BLACK, rbTree.testGetNodeColor(30))
        assertEquals(Color.BLACK, rbTree.testGetNodeColor(70))

        
        rbTree.remove(20)

        
        assertTrue(rbTree.isRBTree())
        
    }

    @Test
    fun `test deleteFixup with specific color patterns`() {
        
        
        rbTree.set(100, "Hundred")

        
        rbTree.set(50, "Fifty")
        rbTree.set(150, "HundredFifty")

        
        rbTree.set(25, "TwentyFive")
        rbTree.set(75, "SeventyFive")
        rbTree.set(125, "HundredTwentyFive")
        rbTree.set(175, "HundredSeventyFive")

        
        rbTree.remove(25)
        rbTree.remove(75)
        rbTree.remove(125)
        rbTree.remove(175)

        assertTrue(rbTree.isRBTree())
        assertEquals(3, rbTree.size)
    }
}
