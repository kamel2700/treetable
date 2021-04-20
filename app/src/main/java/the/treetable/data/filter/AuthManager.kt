package the.treetable.data.filter

class AuthManager() {
    companion object {
        private var authToken = "";
    }

    fun setToken(token: String) {
        authToken = token;
    }

    fun getToken(): String {
        return authToken;
    }
}