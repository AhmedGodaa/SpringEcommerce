package com.godadev.cartservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CartServiceApplication

fun main(args: Array<String>) {
	runApplication<CartServiceApplication>(*args)
}
