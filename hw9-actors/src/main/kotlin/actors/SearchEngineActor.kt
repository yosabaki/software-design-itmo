package actors

import akka.actor.typed.Behavior
import akka.actor.typed.PostStop
import akka.actor.typed.javadsl.AbstractBehavior
import akka.actor.typed.javadsl.ActorContext
import akka.actor.typed.javadsl.Behaviors
import akka.actor.typed.javadsl.Receive
import commands.Command
import commands.SearchEngineRequest
import commands.SearchEngineResponse
import commands.SearchEngineResponseSuccess
import search.SearchEngine

class SearchEngineActor private constructor(
    context: ActorContext<Command>,
    private val searchEngine: SearchEngine,
) : AbstractBehavior<Command>(context) {

    override fun createReceive(): Receive<Command> {
        return newReceiveBuilder()
            .onMessage(SearchEngineRequest::class.java) { onSearchRequest(it) }
            .onSignal(PostStop::class.java) { onPostStop() }
            .build()
    }

    private fun onSearchRequest(r: SearchEngineRequest): Behavior<Command> {
        r.replyTo.tell(
            SearchEngineResponseSuccess(
                r.requestId,
                searchEngine.query(
                    r.searchQuery,
                    r.returnResultSize,
                    r.timeout
                )
            )
        )
        return this
    }

    private fun onPostStop(): SearchEngineActor {
        context.log.info("Device actor {} stopped", searchEngine.name)
        return this
    }

    companion object {
        fun create(searchEngine: SearchEngine): Behavior<Command> {
            return Behaviors.setup { context ->
                SearchEngineActor(
                    context,
                    searchEngine,
                )
            }
        }
    }

    init {
        context.log.info("Search Engine actor {} started", searchEngine.name)
    }
}