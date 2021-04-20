package the.treetable.account

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import the.treetable.TreetableApplication
import the.treetable.data.db.IdentityDao
import javax.inject.Inject

class AccountViewModel(id: Long): ViewModel() {

    @Inject
    lateinit var identityDao: IdentityDao

    private val _username: MutableLiveData<String> = MutableLiveData()
    val username: LiveData<String> get() = _username

    private val _server: MutableLiveData<String> = MutableLiveData()
    val server: LiveData<String> get() = _server

    init {
        TreetableApplication.APPLICATION.appComponent.inject(this)
        GlobalScope.launch {
            val identityModel = identityDao.getById(id)
            Log.d("debug", identityModel.toString())
            _username.postValue(identityModel.username)
            _server.postValue(identityModel.apiBaseUrl)
        }
    }
}