package the.treetable.auth

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import the.treetable.R
import the.treetable.TreetableApplication

class AuthActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.auth_activity)
        navController = findNavController(R.id.auth_nav_host_fragment);
        findViewById<BottomNavigationView>(R.id.auth_methods_navigation_view)
            .setOnNavigationItemSelectedListener(this::onNavigationItemSelected)
        (application as TreetableApplication).appComponent.inject(this);
    }

    private fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sign_up -> {
                println("navigating to sign up")
                navController.navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
                return true;
            }
            R.id.sign_in -> {
                println("navigating to sign in")
                navController.navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
                return true;
            }
        }
        return false;
    }

    fun onCredentialsReceived(credentials: Credentials) {
        val intent = this.intent;
        intent.putExtra("credentials", credentials)
        setResult(0, intent);
        finish();
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}