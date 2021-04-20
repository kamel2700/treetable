package the.treetable

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import the.treetable.auth.AuthActivity
import the.treetable.auth.Credentials
import the.treetable.data.db.IdentityDao
import the.treetable.data.db.IdentityModel
import the.treetable.data.filter.AuthManager
import the.treetable.data.filter.HostSelectorMiddleware
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var identityDao: IdentityDao;

    @Inject
    lateinit var hostSelectorMiddleware: HostSelectorMiddleware

    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TreetableApplication.APPLICATION.appComponent.inject(this);
        sharedPreferences = getSharedPreferences("default", MODE_PRIVATE);
        val currentIdentityId = sharedPreferences.getLong("current_identity", -1L);
        if (currentIdentityId == -1L) {
            val intent = Intent(this, AuthActivity::class.java)
            startActivityForResult(intent, 100);
        } else {
            GlobalScope.launch {
                val identityModel = identityDao.getById(currentIdentityId)
                runOnUiThread {
                    useIdentity(identityModel)
                }
            }
        }
    }

    private fun useIdentity(identityModel: IdentityModel) {
        hostSelectorMiddleware.apiBasePath = identityModel.apiBaseUrl
        AuthManager().setToken(identityModel.token)
        sharedPreferences.edit()
            .putLong("current_identity", identityModel.id).apply()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent);
    }

    private fun addIdentity(identityModel: IdentityModel) {
        identityModel.id = identityDao.insert(identityModel)
        println("identity id = ${identityModel.id}")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != 0) {
            finish();
        }
        val credentials = data?.getParcelableExtra<Credentials>("credentials")!!
        GlobalScope.launch {
            val identityModel = IdentityModel(
                id = 0,
                apiBaseUrl = credentials.apiBaseUrl,
                username = credentials.username,
                password = credentials.password,
                token = credentials.token
            );
            addIdentity(identityModel)
            runOnUiThread {
                useIdentity(identityModel)
            }
        }
    }
}