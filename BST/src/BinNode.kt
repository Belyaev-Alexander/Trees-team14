package tree.node

class BinNode<K : Comparable<K>, V>(key: K, value: V) : BaseNode<K, V, BinNode<K, V>>(key, value)