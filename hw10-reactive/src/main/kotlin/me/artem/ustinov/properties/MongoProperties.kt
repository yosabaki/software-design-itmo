package me.artem.ustinov.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct


@Component
@ConfigurationProperties(prefix = "mongo")
class MongoProperties {
    private lateinit var url: String
    private var port: Int = 0
    @PostConstruct
    private fun init() {}
}