package me.artem.ustinov.manager.repository

import me.artem.ustinov.manager.event.UserStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserStatus?, String?>