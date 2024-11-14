package com.libraryapp.keycloakauthservice

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.stream.Collectors
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.system.measureTimeMillis

// Declaration-site covariant class: E is used only as a return type (out).
class MyImmutablePair<out E>(val a: E, val b: E)

// Declaration-site contravariant class: E is used only as an input type (in).
class MyConsumer<in E> {
    fun consume(p: E) {
        println(p)
    }
}

class KotlinTest {

    // Stream API is faster for computationally intensive operations
    // or when parallelism can be used to speed up processing.
    @Test
    fun `stream filtering large range should be faster than sequence filtering`() {
        val list = (1..10_000_000).toList()

        val streamApiTime = measureTimeMillis {
            list.parallelStream()
                .filter { el -> el < 9_000_000 }
                .toList()

        }
        println("stream api time: $streamApiTime")

        val sequenceApiTime = measureTimeMillis {
            list.asSequence()
                .filter { el -> el < 9_000_000 }
                .toList()
        }
        println("sequence time: $sequenceApiTime")
    }

    @Test
    fun `sequence can be reused while stream cannot`() {
        val list = listOf("apple", "banana", "avocado", "cherry")

        val sequence = list.asSequence()

        sequence
            .filter { it.startsWith("a") }
            .map { it.uppercase() }
            .toList()

        sequence
            .filter { it.startsWith("b") }
            .map { it.uppercase() }
            .toList()


        val stream = list.stream()

        stream
            .filter { it.startsWith("a") }
            .map { it.uppercase() }
            .collect(Collectors.toList())


        assertThrows<IllegalStateException> {
            stream
                .filter { it.startsWith("b") }
                .map { it.uppercase() }
                .collect(Collectors.toList())
        }

    }

    @Test
    fun `test of class delegation`() {
        val simplePrinter = SimplePrinter()
        val smartPrinter = SmartPrinter(simplePrinter)
        smartPrinter.checkAndPrint("Hello, world!")
    }

    @Test
    fun `should increase and print counter three times because of property delegation`() {
        val myClass = MyClass()
        println(myClass.myProperty)
        println(myClass.myProperty)
        println(myClass.myProperty)
    }

    @Test
    fun `should print result of operations with operators`() {
        val p1 = Point(3, 4)
        val p2 = Point(1, 2)

        val sum = p1 + p2
        println("p1 + p2 = $sum")

        val difference = p1 - p2
        println("p1 - p2 = $difference")
    }


    // when and extension function usage
    private fun Int.describeNumber(): String {
        return when {
            this in 1..10 -> "Between 1 and 10"
            this in 11..20 -> "Between 11 and 20"
            this % 2 == 0 -> "Even number"
            else -> "Unknown"
        }
    }

    @Test
    fun `test when with ranges and conditions`() {
        val number = 15
        val result = number.describeNumber()
        println(result)
    }

    @Test
    fun `lateinit and lazy test`() {
        val lateinitLazyExample = LateinitLazyExample()
        lateinitLazyExample.demonstrate()
    }

    @Test
    fun `companion object test`() {
        CompanionObjectExample.printCompanion()
    }

    @Test
    fun `object test`() {
        SingletonExample.increment()
        SingletonExample.increment()
    }

    @Test
    fun `scope function test`() {
        val scopeFunctionsExample = ScopeFunctionsExample()
        scopeFunctionsExample.demonstrateLetWithNullable()
    }

}


class LateinitLazyExample {

    private var name: String = "Default Name"
        get() {
            println("Getting name: $field")
            return field
        }
        set(value) {
            println("Setting name to: $value")
            field = value
        }

    private lateinit var description: String

    private val lazyValue: String by lazy {
        println("Initializing lazyValue")
        "Lazy Initialized Value"
    }

    private val immediateValue: String = "Immediately Initialized Value"

    private fun initializeDescription() {
        description = "Lateinit description initialized"
    }

    fun demonstrate() {
        println(name)
        name = "New Name"
        println(name)

        initializeDescription()
        println(description)

        println(immediateValue)
        println(lazyValue)
        println(lazyValue)
    }
}

class ReadCountDelegate<T>(initialValue: T) : ReadOnlyProperty<Any?, T> {
    private var count = 0
    private var value = initialValue


    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        count++
        println("Property '${property.name}' has been read $count times.")
        return value
    }
}

class MyClass {
    val myProperty: String by ReadCountDelegate("Initial value")
}

class ScopeFunctionsExample {

    private var name: String? = null

    fun demonstrateLetWithNullable() {
        name?.let {
            println("Let: $it")
        } ?: println("Name is null, setting default value")

        name = name ?: "Default Name"

        name?.let {
            println("New name: $it")
        }
    }
}

class CompanionObjectExample {

    companion object {
        private const val DEFAULT_DESCRIPTION = "This is a companion object description"

        fun printCompanion() {
            println("Companion object: $DEFAULT_DESCRIPTION")
        }
    }
}

object SingletonExample {
    private var counter: Int = 0

    fun increment() {
        counter++
        println("Counter incremented to: $counter")
    }
}

data class Point(val x: Int, val y: Int) {

    operator fun plus(other: Point): Point {
        return Point(this.x + other.x, this.y + other.y)
    }

    operator fun minus(other: Point): Point {
        return Point(this.x - other.x, this.y - other.y)
    }

}

interface Printer {
    fun printMessage(message: String)
}

class SimplePrinter : Printer {
    override fun printMessage(message: String) {
        println("Printing: $message")
    }
}

class SmartPrinter(printer: Printer) : Printer by printer {
    fun checkAndPrint(message: String) {
        if (message.isNotBlank()) {
            printMessage(message)
        } else {
            println("Message is blank, not printing.")
        }
    }
}

