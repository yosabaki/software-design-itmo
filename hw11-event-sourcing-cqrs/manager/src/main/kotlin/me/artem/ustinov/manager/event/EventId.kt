package me.artem.ustinov.manager.event

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.GeneratedValue

@Embeddable
class EventId(
        @Column(name = "user_id")
        private var uid: String,
        @Column(name= "user_event_id")
        @GeneratedValue(generator = "sequence-generator")
        @GenericGenerator(
                name= "sequence-generator",
                strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
                parameters = [
                    Parameter(name = "sequence_name", value = "user_sequence"),
                    Parameter(name = "initial_value", value = "1"),
                    Parameter(name = "increment_size", value = "1")
                ]
        )
        private var id: Int
) : Serializable
