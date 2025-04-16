package core.config

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabase() {
//    val url = System.getenv("DB_URL")
//    val driver = environment.config.property("storage.driver").getString()
//    val user = System.getenv("DB_USER")
//    val password = System.getenv("DB_PASSWORD")
//
//    println("DB connecting:\n")
//    println("-url:$url\n")
//    println("-driver:$driver\n")
//    println("-user:$user\n")
//    println("-password:$password\n")
//    Database.connect(
//        url = url,
//        driver = driver,
//        user = user,
//        password = password
//    )
    Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        user = "root",
        driver = "org.h2.Driver",
        password = "",
    )
}