package the.treetable.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AccountModelFactory(private val id: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            return AccountViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}