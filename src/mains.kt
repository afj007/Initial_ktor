package br.com.estudo.ktor

import br.com.estudo.ktor.extension.formatForBrazilianCurrency
import java.math.BigDecimal

fun main() {
    val  names = arrayListOf<String>()
    names.add("Alan")
    names.add("Franca")
    names.add("Junior")

    val filtered = names.filter {
        it.startsWith('A', true) || it.endsWith('A', true)
    }

    println(filtered)
}
