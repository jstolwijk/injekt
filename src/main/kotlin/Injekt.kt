import java.lang.RuntimeException

private val registeredDependencies = mutableMapOf<String, Any>()

const val DEFAULT_QUALIFIER = "default"

inline fun <reified T : Any> inject(qualifier: String = DEFAULT_QUALIFIER): T = inject(T::class.java, qualifier)

@Suppress("UNCHECKED_CAST")
fun <T : Any> inject(clazz: Class<T>, qualifier: String = DEFAULT_QUALIFIER): T {
    return registeredDependencies[getKey(qualifier, clazz)] as? T ?: throw RuntimeException("Dependency is not registered (class: ${clazz.toGenericString()}, qualifier: $qualifier)")
}

private fun <T : Any> getKey(qualifier: String, clazz: Class<T>): String {
    return listOfNotNull(
        qualifier,
        clazz.`package`?.name,
        clazz.name
    ).joinToString(separator = "")
}

fun <T: Any> dependency(qualifier: String = DEFAULT_QUALIFIER, classInitializer: () -> T) {
    val instance = classInitializer()
    val key = getKey(qualifier, instance::class.java)

    if(key in registeredDependencies) {
        throw RuntimeException("Class ${instance.javaClass} with qualifier: $qualifier is already registered")
    }

    registeredDependencies[key] = instance
}
