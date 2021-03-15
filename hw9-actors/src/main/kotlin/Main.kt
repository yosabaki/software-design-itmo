import actors.SearchSupervisor
import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.javadsl.AskPattern
import commands.SearchRequest
import commands.SearchResponse
import search.StubSearchEngine
import java.io.File
import java.time.Duration

fun main(args: Array<String>) {
    val engines = listOf(
        StubSearchEngine(
            "Google", Duration.ofMillis(1000),
            File(RESOURCE_FILE).reader()
        ),
        StubSearchEngine(
            "Yahoo!!!", Duration.ofMillis(2000),
            File(RESOURCE_FILE).reader()
        )
    )
    val timeout: Long = 1500
    val searchActor =
        ActorSystem.create(SearchSupervisor.create(timeoutMillis = timeout, engines = engines), "search-engine")
    println("<<<WAITING FOR INPUT>>> Search query: ")
    val query = readLine()!!
    val reply = AskPattern.ask(
        searchActor,
        { replyTo: ActorRef<SearchResponse> -> SearchRequest(query, replyTo) },
        Duration.ofMillis(timeout + 100),
        searchActor.scheduler()
    ).toCompletableFuture().join()
    reply.engineResults.forEach { (name, response) ->
        println("$name:")
        println(response)
    }
}

const val RESOURCE_FILE = "hw9-actors\\src\\main\\resources\\stub_data.json"