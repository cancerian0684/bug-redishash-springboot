package com.example.demo

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.repository.CrudRepository

@SpringBootApplication
class DemoApplication {

    @Bean
    fun startupRunner(otpCacheRepository: OtpCacheRepository): CommandLineRunner {
        return CommandLineRunner { args ->
            val optional = otpCacheRepository.findById("unknown")
            println("optional = " + optional.isPresent)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

@RedisHash(value = "OtpCache", timeToLive = 5 * 60)
data class OtpCache(@Id val mobile: String,
                    val otp: String)

interface OtpCacheRepository : CrudRepository<OtpCache, String> {

}

