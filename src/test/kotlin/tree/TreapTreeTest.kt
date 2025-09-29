package tree

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class TreapTreeTest {

    private lateinit var treap: TreapTree<Int, String>

    @BeforeEach
    fun setUp() {
        treap = TreapTree()
    }

    @Test
    fun `test empty treap`() {
        assertTrue(treap.isEmpty())
        assertEquals(0, treap.size)
        assertNull(treap.root)
    }

    @Test
    fun `test insert and find`() {
        treap.set(10, "Ten")
        assertFalse(treap.isEmpty())
        assertEquals(1, treap.size)
        assertEquals("Ten", treap.GetValue(10))
    }

    @Test
    fun `test insert multiple elements`() {
        treap.set(50, "Fifty")
        treap.set(30, "Thirty")
        treap.set(70, "Seventy")
        treap.set(20, "Twenty")
        treap.set(40, "Forty")

        assertEquals(5, treap.size)
        assertEquals("Twenty", treap.GetValue(20))
        assertEquals("Thirty", treap.GetValue(30))
        assertEquals("Forty", treap.GetValue(40))
        assertEquals("Fifty", treap.GetValue(50))
        assertEquals("Seventy", treap.GetValue(70))
    }

    @Test
    fun `test update existing key`() {
        treap.set(10, "Ten")
        treap.set(20, "Twenty")

        val oldValue = treap.set(10, "NewTen")

        assertEquals("Ten", oldValue)
        assertEquals("NewTen", treap.GetValue(10))
        assertEquals(2, treap.size) 
    }

    @Test
    fun `test remove existing key`() {
        treap.set(10, "Ten")
        treap.set(20, "Twenty")

        val removedValue = treap.remove(10)

        assertEquals("Ten", removedValue)
        assertNull(treap.GetValue(10))
        assertEquals("Twenty", treap.GetValue(20))
        assertEquals(1, treap.size)
    }

    @Test
    fun `test remove non-existing key`() {
        treap.set(10, "Ten")

        val removedValue = treap.remove(20)

        assertNull(removedValue)
        assertEquals("Ten", treap.GetValue(10))
        assertEquals(1, treap.size)
    }

    @Test
    fun `test get min and max`() {
        treap.set(50, "Fifty")
        treap.set(30, "Thirty")
        treap.set(70, "Seventy")
        treap.set(20, "Twenty")
        treap.set(40, "Forty")

        val (minKey, minValue) = treap.GetMin()
        val (maxKey, maxValue) = treap.GetMax()

        assertEquals(20, minKey)
        assertEquals("Twenty", minValue)
        assertEquals(70, maxKey)
        assertEquals("Seventy", maxValue)
    }

    @Test
    fun `test get min and max in empty treap`() {
        val (minKey, minValue) = treap.GetMin()
        val (maxKey, maxValue) = treap.GetMax()

        assertNull(minKey)
        assertNull(minValue)
        assertNull(maxKey)
        assertNull(maxValue)
    }

    @Test
    fun `test get next key`() {
        treap.set(50, "Fifty")
        treap.set(30, "Thirty")
        treap.set(70, "Seventy")
        treap.set(20, "Twenty")
        treap.set(40, "Forty")

        val (nextKey, nextValue) = treap.GetNext(35)

        assertEquals(40, nextKey)
        assertEquals("Forty", nextValue)
    }

    @Test
    fun `test get next key for largest element`() {
        treap.set(10, "Ten")
        treap.set(20, "Twenty")
        treap.set(30, "Thirty")

        val (nextKey, nextValue) = treap.GetNext(30)

        assertNull(nextKey)
        assertNull(nextValue)
    }

    @Test
    fun `test get prev key`() {
        treap.set(50, "Fifty")
        treap.set(30, "Thirty")
        treap.set(70, "Seventy")
        treap.set(20, "Twenty")
        treap.set(40, "Forty")

        val (prevKey, prevValue) = treap.GetPrev(35)

        assertEquals(30, prevKey)
        assertEquals("Thirty", prevValue)
    }

    @Test
    fun `test get prev key for smallest element`() {
        treap.set(10, "Ten")
        treap.set(20, "Twenty")
        treap.set(30, "Thirty")

        val (prevKey, prevValue) = treap.GetPrev(10)

        assertNull(prevKey)
        assertNull(prevValue)
    }

    @Test
    fun `test contains key`() {
        treap.set(10, "Ten")
        treap.set(20, "Twenty")

        assertTrue(treap.contains(10))
        assertTrue(treap.contains(20))
        assertFalse(treap.contains(30))
    }

    @Test
    fun `test clear treap`() {
        treap.set(10, "Ten")
        treap.set(20, "Twenty")
        treap.set(30, "Thirty")

        treap.Clear()

        assertTrue(treap.isEmpty())
        assertEquals(0, treap.size)
        assertNull(treap.root)
        assertNull(treap.GetValue(10))
    }

    @Test
    fun `test get key by value`() {
        treap.set(1, "One")
        treap.set(2, "Two")
        treap.set(3, "Three")

        assertEquals(1, treap.GetKey("One"))
        assertEquals(2, treap.GetKey("Two"))
        assertEquals(3, treap.GetKey("Three"))
        assertNull(treap.GetKey("Four"))
    }

    @Test
    fun `test get key by value with duplicates`() {
        
        
        treap.set(1, "Duplicate")
        treap.set(2, "Duplicate")

        val foundKey = treap.GetKey("Duplicate")
        assertTrue(foundKey == 1 || foundKey == 2) 
    }

    @Test
    fun `test treap properties after operations`() {
        
        treap.set(50, "Fifty")
        treap.set(30, "Thirty")
        treap.set(70, "Seventy")
        treap.set(20, "Twenty")
        treap.set(40, "Forty")
        treap.set(60, "Sixty")
        treap.set(80, "Eighty")

        
        assertEquals("Twenty", treap.GetValue(20))
        assertEquals("Thirty", treap.GetValue(30))
        assertEquals("Forty", treap.GetValue(40))
        assertEquals("Fifty", treap.GetValue(50))
        assertEquals("Sixty", treap.GetValue(60))
        assertEquals("Seventy", treap.GetValue(70))
        assertEquals("Eighty", treap.GetValue(80))

        
        treap.remove(30)
        treap.remove(70)

        
        assertNull(treap.GetValue(30))
        assertNull(treap.GetValue(70))

        
        assertEquals("Twenty", treap.GetValue(20))
        assertEquals("Forty", treap.GetValue(40))
        assertEquals("Fifty", treap.GetValue(50))

        assertEquals(5, treap.size)
    }

    @Test
    fun `test string keys`() {
        val stringTreap = TreapTree<String, Int>()

        stringTreap.set("apple", 1)
        stringTreap.set("banana", 2)
        stringTreap.set("cherry", 3)

        assertEquals(1, stringTreap.GetValue("apple"))
        assertEquals(2, stringTreap.GetValue("banana"))
        assertEquals(3, stringTreap.GetValue("cherry"))
        assertEquals(3, stringTreap.size)
    }

    @Test
    fun `test recentlyKey functionality`() {
        treap.set(10, "Ten")
        assertEquals(10, treap.recentlyKey)

        treap.set(20, "Twenty")
        assertEquals(20, treap.recentlyKey)

        treap.remove(10)
        assertEquals(10, treap.recentlyKey)

        treap.GetValue(20)
        
        assertEquals(10, treap.recentlyKey)
    }

    @Test
    fun `test traverse empty tree`() {
        
        assertDoesNotThrow {
            treap.Traverse()
        }
    }
    @Test
    fun `test printTree with empty tree`() {
        
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        treap.printTree()

        val output = outputStream.toString().trim()
        
        assertTrue(output.isEmpty() || output == "\n")

        
        System.setOut(System.out)
    }

    @Test
    fun `test printTree with single node`() {
        treap.set(10, "Ten")

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        treap.printTree()

        val output = outputStream.toString()
        
        assertTrue(output.contains("10") && output.contains("p:"))

        System.setOut(System.out)
    }

    @Test
    fun `test printTree with complex tree`() {
        treap.set(50, "Fifty")
        treap.set(30, "Thirty")
        treap.set(70, "Seventy")
        treap.set(20, "Twenty")
        treap.set(40, "Forty")

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        treap.printTree()

        val output = outputStream.toString()
        
        assertTrue(output.contains("20"))
        assertTrue(output.contains("30"))
        assertTrue(output.contains("40"))
        assertTrue(output.contains("50"))
        assertTrue(output.contains("70"))

        System.setOut(System.out)
    }
    @Test
    fun `test delete key greater than root key`() {
        
        treap.set(50, "Fifty")
        treap.set(30, "Thirty")
        treap.set(70, "Seventy") 

        
        val result = treap.remove(70)

        assertEquals("Seventy", result)
        assertNull(treap.GetValue(70))
        assertEquals("Fifty", treap.GetValue(50))
        assertEquals("Thirty", treap.GetValue(30))
        assertEquals(2, treap.size)
    }
    @Test
    fun `test merge left priority greater than right`() {
        
        val leftNode = TreapNode(5, "Five")
        leftNode.priority = 100  

        val rightNode = TreapNode(10, "Ten")
        rightNode.priority = 50   

        
        
        
        
        
        
        

        val result = treap.testMerge(leftNode, rightNode)

        
        assertEquals(leftNode, result)
        assertEquals(5, result?.key)

        
        assertEquals(rightNode, result?.right)
        assertEquals(10, result?.right?.key)

        
        assertEquals(leftNode, result?.right?.parent)
    }
    @Test
    fun `test force merge left priority greater than right`() {
        val leftNode = TreapNode(5, "Five").apply { priority = 100 }
        val rightNode = TreapNode(10, "Ten").apply { priority = 50 }

        val result = treap.testMerge(leftNode, rightNode)

        
        assertEquals(5, result?.key)
        assertEquals(10, result?.right?.key)
    }

    @Test
    fun `test force merge right priority greater than left`() {
        val leftNode = TreapNode(5, "Five").apply { priority = 50 }
        val rightNode = TreapNode(10, "Ten").apply { priority = 100 }

        val result = treap.testMerge(leftNode, rightNode)

        
        assertEquals(10, result?.key)
        assertEquals(5, result?.left?.key)
    }
}