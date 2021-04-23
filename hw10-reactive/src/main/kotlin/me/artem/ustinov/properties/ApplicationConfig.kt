package me.artem.ustinov.properties

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.tcp.TcpClient

import java.util.concurrent.TimeUnit

const val TIMEOUT = 1000

@Configuration
class ApplicationConfig {
    @Bean
    fun webClient(): WebClient {
        val tcpClient = TcpClient
            .create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
            .doOnConnected { connection ->
                connection.addHandlerLast(
                    ReadTimeoutHandler(
                        TIMEOUT.toLong(),
                        TimeUnit.MILLISECONDS
                    )
                )
                connection.addHandlerLast(
                    WriteTimeoutHandler(
                        TIMEOUT.toLong(),
                        TimeUnit.MILLISECONDS
                    )
                )
            }
        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(HttpClient.from(tcpClient)))
            .build()
    }
}