// ktlint-disable filename
package utils

data class Mutable<T>(var v: T) {
    override fun toString(): String {
        return v.toString()
    }

    fun modify(f: (T) -> T): T {
        this.v = f(v)
        return this.v
    }

    fun get(): T {
        return v
    }
}
