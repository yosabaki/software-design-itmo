package me.artem.ustinov.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id

data class User(
    @Id
    val id: Long,
    @JsonProperty("login")
    val password: String,
    @JsonProperty("currency")
    val currency: Currency
)