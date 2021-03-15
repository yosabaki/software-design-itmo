package commands

import search.SearchResult

data class SearchEngineResponse(val requestId: Long, val searchResults: List<SearchResult>) : Command