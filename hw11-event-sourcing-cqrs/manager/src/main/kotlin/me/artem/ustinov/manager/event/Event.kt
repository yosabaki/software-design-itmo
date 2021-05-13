package me.artem.ustinov.manager.event

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import java.io.Serializable
import javax.persistence.*

@Entity(name = "Events")
@IdClass(Event::class)
class Event(
        @Id
        @Column(name = "user_id") var uid: String,
        @Id
        @Column(name = "user_event_id")
        @GeneratedValue(generator = "sequence-generator")
        @GenericGenerator(
                name = "sequence-generator",
                strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
                parameters = [
                    Parameter(name = "sequence_name", value = "user_sequence"),
                    Parameter(name = "initial_value", value = "1"),
                    Parameter(name = "increment_size", value = "1")
                ]
        ) var id: Int = 0
) : Serializable