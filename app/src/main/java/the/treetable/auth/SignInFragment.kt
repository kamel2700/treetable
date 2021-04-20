package the.treetable.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import the.treetable.R
import the.treetable.TreetableApplication
import the.treetable.data.AuthService
import the.treetable.data.dto.SignInData
import the.treetable.data.filter.HostSelectorMiddleware
import the.treetable.data.response.RestResponse
import the.treetable.databinding.SignInFragmentBinding
import javax.inject.Inject

class SignInFragment : Fragment() {

    private lateinit var binding: SignInFragmentBinding

    @Inject
    lateinit var viewModel: SignInViewModel

    @Inject
    lateinit var authService: AuthService

    @Inject
    lateinit var hostSelectorMiddleware: HostSelectorMiddleware

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TreetableApplication.APPLICATION.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.sign_in_fragment, container, false)
        binding.model = viewModel
        binding.controller = this
        return binding.root
    }

    fun onSignInClicked() {
        hostSelectorMiddleware.apiBasePath = viewModel.serverApiPath.get()!!
        //GlobalScope.launch {
            authService.doSignIn(
                SignInData(
                    viewModel.username.get()!!,
                    viewModel.password.get()!!
                )
            ).subscribeOn(Schedulers.io())
                .doOnError(this::onSignInError)
                .doOnSuccess(this::onSignInSuccess)
                .subscribe()
                //.blockingGet();
        //}
    }

    private fun onSignInError(throwable: Throwable) {

    }

    private fun onSignInSuccess(response: RestResponse<String>) {
        println(response.status + " " + response.data)
        (activity as AuthActivity).onCredentialsReceived(
            Credentials(
                apiBaseUrl = viewModel.serverApiPath.get()!!,
                username = viewModel.username.get()!!,
                password = viewModel.password.get()!!,
                token = response.data!!
            )
        )
    }
}