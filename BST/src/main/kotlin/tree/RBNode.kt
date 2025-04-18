class RBNode<K: Comparable<K>, V>(
    key: K,
    value: V,
    left: RBNode<K, V>? = null,
    right: RBNode<K, V>? = null,
    parent: RBNode<K, V>? = null,
    color: Color = Color.RED
) : BaseNode<K, V, RBNode<K, V>>(key, value, left, right, parent, color)