import actors.SearchSupervisor
import akka.actor.typed.ActorSystem
import commands.SearchRequest
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
    val searchActor =
        ActorSystem.create(SearchSupervisor.create(timeoutMillis = 1500, engines = engines), "search-engine")
    println("<<<WAITING FOR INPUT>>> Search query: ")
    val query = readLine()!!
    searchActor.tell(SearchRequest(query))
}

const val RESOURCE_FILE = "hw9-actors\\src\\main\\resources\\stub_data.json"