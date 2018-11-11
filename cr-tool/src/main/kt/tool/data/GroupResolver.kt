package tool.data

class GroupResolver(data: List<Groupable>) {

    private val groupedData: Map<String, List<Groupable>> =
            data.groupBy { it.groupId }

    fun countItemsGroups(): Map<String, Int> {
        return groupedData.map {(key, values) ->  key to values.size }.toMap()
    }

}