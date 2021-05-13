package me.artem.ustinov.turnstile.controller

import me.artem.ustinov.common.dto.TurnstileDto
import me.artem.ustinov.turnstile.command.EnterCommand
import me.artem.ustinov.turnstile.command.ExitCommand
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("/turnstile")
class CheckpointController(private val commandGateway: CommandGateway) {
    @PostMapping("/enter")
    fun enter(@RequestBody dto: TurnstileDto): CompletableFuture<String> {
        return commandGateway.send(EnterCommand(dto.uid))
    }

    @PostMapping("/exit")
    fun exit(@RequestBody dto: TurnstileDto): CompletableFuture<String> {
        return commandGateway.send(ExitCommand(dto.uid))
    }
}