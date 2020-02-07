fun main() {
    registerDependencies()

    val userService = inject<UserService>()
    val users = userService.getAllUsers()
    println("FirstNames: " + users.joinToString { it.firstName })
}

fun registerDependencies() {
    dependency { UserRepository() }
    dependency { UserService(inject()) }
}

class UserService(private val userRepository: UserRepository) {

    fun getAllUsers() = userRepository.selectAllUsers()

}

class UserRepository {
    fun selectAllUsers(): List<User> = listOf(
        User(
            firstName = "Lee",
            lastName = "Callahan"
        ),
        User(
            firstName = "Faith",
            lastName = "Harrington"
        )
    )
}

data class User(
    val firstName: String,
    val lastName: String
)