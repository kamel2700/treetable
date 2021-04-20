package the.treetable

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import the.treetable.node.NodeFragmentDirections

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = this.findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        when (item.itemId) {
            R.id.action_home -> {
                navController.popBackStack(R.id.projects_fragment, false)
            }
            R.id.settings -> {
                if (navController.currentBackStackEntry?.destination?.id != R.id.account_fragment)
                    navController.navigate(R.id.account_fragment)
            }
            /*R.id.switch_user -> {
                switchUser()
            }*/
            R.id.sign_out -> {
                signOut()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun signOut() {
        sharedPreferences = getSharedPreferences("default", MODE_PRIVATE)
        sharedPreferences.edit().putLong("current_identity", -1L).apply()
        val intent = Intent(this, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }
}