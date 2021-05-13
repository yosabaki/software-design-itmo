package me.artem.ustinov.report.controller

import me.artem.ustinov.common.dto.StatResponse
import me.artem.ustinov.report.statistics.StatService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/report")
class StatController(
        @Autowired private val statService: StatService
) {
    @GetMapping("/{uid}")
    fun getStatVisit(@PathVariable("uid") uid: String): ResponseEntity<StatResponse> {
        val statResponse: StatResponse = statService.getStatForUserById(uid)
        return ResponseEntity.ok(statResponse)
    }
}