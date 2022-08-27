import java.util.*

// Class Node
internal class Node @JvmOverloads constructor(// elements, and sValue are the variables in class Node
    var element: Int, // class has two parameters
    var left: Node? = null, right: Node? = null
) {
    var sValue: Int
    var right: Node?

    // Function Node where we are using this keyword
    // Which will help us to avoid confusion if we are having
    // same elements
    init {
        left = left
        this.right = right
        sValue = 0
    }
} // Class Left heap

internal class LeftHeap() {
    // Now parameter is created named head.
    private var head: Node?

    // Its constructor is created named left heap
    // Returns null
    init {
        head = null
    }// If head is null returns true

    // Now we will write function to check if the list is
    // empty
    val isEmpty: Boolean
        get() =// If head is null returns true
            head == null

    // Now we will write a function clear
    fun clear() {
        // We will put head is null
        head = null
    }

    // Now let us create a function merge which will
    // help us merge
    fun merge(rhs: LeftHeap) {
        // If the present function is rhs
        // then we return it
        if (this === rhs) return

        // Here we call the function merge
        // And make rhs is equal to null
        head = merge(head, rhs.head)
        rhs.head = null
    }

    // Function merge with two Nodes a and b
    fun merge(a: Node?, b: Node?): Node? {
        // If A is null
        // We return b
        var a = a
        var b = b
        if (a == null) return b

        // If b is null
        // we return A
        if (b == null) return a

        // If we put a element greater than b element
        if (a.element > b.element) {

            // We write the swap code
            val temp: Node = a
            a = b
            b = temp
        }

        // Now we call the function merge to merge a and b
        a.right = merge(a.right, b)

        // If a is null we swap right with left and empty
        // right
        if (a.left == null) {
            a.left = a.right
            a.right = null
        } else {
            if (a.left!!.sValue < a.right!!.sValue) {
                val temp = a.left
                a.left = a.right
                a.right = temp
            }

            // we store the value in a s Value of right
            // SValue
            a.sValue = a.right!!.sValue + 1
        }

        // We now return the value of a
        return a
    }

    // Function insert
    fun insert(a: Int) {
        // This root will help us insert a new variable
        head = merge(Node(a), head)
    }

    // The below function will help us delete minimum
    // function present in the Heap
    fun del(): Int {
        // If is empty return -1
        if (isEmpty) return -1

        // Now we will store the element in variable and
        // Call the merge function to del that is converging
        // to head then  we return min
        val min = head!!.element
        head = merge(head!!.left, head!!.right)
        return min
    }

    // Function order
    // will print the starting and ending points in order.
    fun order() {
        order(head)
        println()
    }

    // Function order with Node r
    // If r not equal to r
    // It prints all the elements iterating from order left
    // to right
    private fun order(r: Node?) {
        if (r != null) {
            order(r.left)
            print(r.element.toString() + " ")
            order(r.right)
        }
    }
} // Class gfg

internal object GFG {
    @JvmStatic
    fun main(args: Array<String>) {

        // Creating the scanner object
        val sc = Scanner(System.`in`)
        println("LEFTIST HEAP")

        // Creating object for class LeftHeap
        val h = LeftHeap()

        // Char ch
        var ch: Char

        // Now taking the loop
        do {
            // Now writing down all the functions
            println("Functions to do")
            println("1. insert")
            println("2. delete min")
            println("3. check empty")
            println("4. clear")

            // Scanning the choice to be used in switch
            val choice = sc.nextInt()
            when (choice) {
                1 -> {
                    println("Enter integer element to insert")
                    h.insert(sc.nextInt())
                }

                2 -> h.del()
                3 -> println(
                    "Empty status = "
                            + h.isEmpty
                )

                4 -> h.clear()
                else -> println("Wrong entry")
            }

            // Prints the inorder traversal
            // Calling the func
            print("\n Inorder Traversal: ")
            h.order()

            // Whether to continue or not
            println("\n If you wish to continue type Y or y")
            ch = sc.next()[0]
        } while (ch == 'Y' || ch == 'y')
    }
}