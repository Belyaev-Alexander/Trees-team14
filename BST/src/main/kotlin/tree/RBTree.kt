package tree

class RBTree<K : Comparable<K>, V> {
    private var root: RBNode<K, V>? = null
    var size = 0
        private set

    fun insert(key: K, value: V) { /* ... */ }
    fun delete(key: K) { /* ... */ }
    fun search(key: K): V? { /* ... */ }
}
