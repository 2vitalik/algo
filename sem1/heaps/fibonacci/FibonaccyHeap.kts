// Operations on Fibonacci Heap in Java
// Node creation
class node() {
    var parent: node? = null
    var left: node?
    var right: node?
    var child: node?
    var degree = 0
    var mark = false
    var key: Int

    init {
        left = this
        right = this
        child = null
        key = Int.MAX_VALUE
    }

    constructor(x: Int) : this() {
        key = x
    }

    fun set_parent(x: node?) {
        parent = x
    }

    fun get_parent(): node? {
        return parent
    }

    fun set_left(x: node?) {
        left = x
    }

    fun get_left(): node? {
        return left
    }

    fun set_right(x: node?) {
        right = x
    }

    fun get_right(): node? {
        return right
    }

    fun set_child(x: node?) {
        child = x
    }

    fun get_child(): node? {
        return child
    }

    fun set_degree(x: Int) {
        degree = x
    }

    fun get_degree(): Int {
        return degree
    }

    fun set_mark(m: Boolean) {
        mark = m
    }

    fun get_mark(): Boolean {
        return mark
    }

    fun set_key(x: Int) {
        key = x
    }

    fun get_key(): Int {
        return key
    }
}

class fibHeap internal constructor() {
    var min: node? = null
    var n = 0
    var trace = false
    var found: node? = null
    fun get_trace(): Boolean {
        return trace
    }

    fun set_trace(t: Boolean) {
        trace = t
    }

    private fun insert(x: node) {
        if (min == null) {
            min = x
            x.set_left(min)
            x.set_right(min)
        } else {
            x.set_right(min)
            x.set_left(min!!.get_left())
            min!!.get_left()!!.set_right(x)
            min!!.set_left(x)
            if (x.get_key() < min!!.get_key()) min = x
        }
        n += 1
    }

    fun insert(key: Int) {
        insert(node(key))
    }

    fun display() {
        display(min)
        println()
    }

    private fun display(c: node?) {
        print("(")
        if (c == null) {
            print(")")
            return
        } else {
            var temp = c
            do {
                print(temp!!.get_key())
                val k = temp.get_child()
                display(k)
                print("->")
                temp = temp.get_right()
            } while (temp !== c)
            print(")")
        }
    }

    fun find_min(): Int {
        return min!!.get_key()
    }

    private fun display_node(z: node) {
        println("right: " + if (z.get_right() == null) "-1" else z.get_right()!!.get_key())
        println("left: " + if (z.get_left() == null) "-1" else z.get_left()!!.get_key())
        println("child: " + if (z.get_child() == null) "-1" else z.get_child()!!.get_key())
        println("degree " + z.get_degree())
    }

    fun extract_min(): Int {
        val z = min
        if (z != null) {
            var c = z.get_child()
            val k = c
            var p: node?
            if (c != null) {
                do {
                    p = c!!.get_right()
                    insert(c)
                    c.set_parent(null)
                    c = p
                } while (c != null && c !== k)
            }
            z.get_left()!!.set_right(z.get_right())
            z.get_right()!!.set_left(z.get_left())
            z.set_child(null)
            if (z === z.get_right()) min = null else {
                min = z.get_right()
                consolidate()
            }
            n -= 1
            return z.get_key()
        }
        return Int.MAX_VALUE
    }

    fun consolidate() {
        val phi = (1 + Math.sqrt(5.0)) / 2
        val Dofn = (Math.log(n.toDouble()) / Math.log(phi)).toInt()
        val A = arrayOfNulls<node>(Dofn + 1)
        for (i in 0..Dofn) A[i] = null
        var w = min
        if (w != null) {
            var check = min
            do {
                var x = w
                var d = x!!.get_degree()
                while (A[d] != null) {
                    var y = A[d]
                    if (x!!.get_key() > y!!.get_key()) {
                        val temp = x
                        x = y
                        y = temp
                        w = x
                    }
                    fib_heap_link(y, x)
                    check = x
                    A[d] = null
                    d += 1
                }
                A[d] = x
                w = w!!.get_right()
            } while (w != null && w !== check)
            min = null
            for (i in 0..Dofn) {
                if (A[i] != null) {
                    insert(A[i]!!)
                }
            }
        }
    }

    // Linking operation
    private fun fib_heap_link(y: node?, x: node?) {
        y!!.get_left()!!.set_right(y.get_right())
        y.get_right()!!.set_left(y.get_left())
        val p = x!!.get_child()
        if (p == null) {
            y.set_right(y)
            y.set_left(y)
        } else {
            y.set_right(p)
            y.set_left(p.get_left())
            p.get_left()!!.set_right(y)
            p.set_left(y)
        }
        y.set_parent(x)
        x.set_child(y)
        x.set_degree(x.get_degree() + 1)
        y.set_mark(false)
    }

    // Search operation
    private fun find(key: Int, c: node?) {
        if (found != null || c == null) return else {
            var temp = c
            do {
                if (key == temp!!.get_key()) found = temp else {
                    val k = temp!!.get_child()
                    find(key, k)
                    temp = temp!!.get_right()
                }
            } while (temp !== c && found == null)
        }
    }

    fun find(k: Int): node? {
        found = null
        find(k, min)
        return found
    }

    fun decrease_key(key: Int, nval: Int) {
        val x = find(key)
        decrease_key(x, nval)
    }

    // Decrease key operation
    private fun decrease_key(x: node?, k: Int) {
        if (k > x!!.get_key()) return
        x.set_key(k)
        val y = x.get_parent()
        if (y != null && x.get_key() < y.get_key()) {
            cut(x, y)
            cascading_cut(y)
        }
        if (x.get_key() < min!!.get_key()) min = x
    }

    // Cut operation
    private fun cut(x: node, y: node) {
        x.get_right()!!.set_left(x.get_left())
        x.get_left()!!.set_right(x.get_right())
        y.set_degree(y.get_degree() - 1)
        x.set_right(null)
        x.set_left(null)
        insert(x)
        x.set_parent(null)
        x.set_mark(false)
    }

    private fun cascading_cut(y: node) {
        val z = y.get_parent()
        if (z != null) {
            if (y.get_mark() == false) y.set_mark(true) else {
                cut(y, z)
                cascading_cut(z)
            }
        }
    }

    // Delete operations
    fun delete(x: node?) {
        decrease_key(x, Int.MIN_VALUE)
        val p = extract_min()
    }

    companion object {
        fun create_heap(): fibHeap {
            return fibHeap()
        }

        fun merge_heap(H1: fibHeap, H2: fibHeap, H3: fibHeap) {
            H3.min = H1.min
            if (H1.min != null && H2.min != null) {
                val t1 = H1.min!!.get_left()
                val t2 = H2.min!!.get_left()
                H1.min!!.set_left(t2)
                t1!!.set_right(H2.min)
                H2.min!!.set_left(t1)
                t2!!.set_right(H1.min)
            }
            if (H1.min == null || H2.min != null && H2.min!!.get_key() < H1.min!!.get_key()) H3.min = H2.min
            H3.n = H1.n + H2.n
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val obj = create_heap()
            obj.insert(7)
            obj.insert(26)
            obj.insert(30)
            obj.insert(39)
            obj.insert(10)
            obj.display()
            println(obj.extract_min())
            obj.display()
            println(obj.extract_min())
            obj.display()
            println(obj.extract_min())
            obj.display()
            println(obj.extract_min())
            obj.display()
            println(obj.extract_min())
            obj.display()
        }
    }
}