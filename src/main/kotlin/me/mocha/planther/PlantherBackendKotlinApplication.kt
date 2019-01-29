package me.mocha.planther

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PlantherBackendKotlinApplication

fun main(args: Array<String>) {
    runApplication<PlantherBackendKotlinApplication>(*args)
}

