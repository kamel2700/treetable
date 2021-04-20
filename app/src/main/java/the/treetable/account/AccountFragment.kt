package the.treetable.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import the.treetable.R
import the.treetable.data.db.IdentityDao
import the.treetable.databinding.AccountFragmentBinding

class AccountFragment : Fragment() {

    private lateinit var binding : AccountFragmentBinding

    private lateinit var viewModel: AccountViewModel

    private lateinit var viewModelFactory: AccountModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.account_fragment,
                container,
                false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelFactory = AccountModelFactory(
            activity?.getSharedPreferences("default", AppCompatActivity.MODE_PRIVATE)?.getLong("current_identity", -1L) ?: -1L
        )
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(AccountViewModel::class.java)

        viewModel.username.observeForever {
            binding.username.text = it
        }

        viewModel.server.observeForever {
            binding.server.text = it
        }
    }

}