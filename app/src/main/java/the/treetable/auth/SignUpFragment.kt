package the.treetable.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import io.reactivex.schedulers.Schedulers
import the.treetable.R
import the.treetable.TreetableApplication
import the.treetable.data.AuthService
import the.treetable.data.dto.SignUpData
import the.treetable.data.filter.HostSelectorMiddleware
import the.treetable.data.response.RestResponse
import the.treetable.databinding.SignUpFragmentBinding
import javax.inject.Inject

class SignUpFragment : Fragment() {

    private lateinit var binding: SignUpFragmentBinding

    @Inject
    lateinit var viewModel: SignUpViewModel

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
        println("sign up")
        binding = DataBindingUtil.inflate(inflater, R.layout.sign_up_fragment, container, false)
        binding.model = viewModel
        binding.controller = this
        return binding.root
    }

    fun onSignUpClicked() {
        hostSelectorMiddleware.apiBasePath = viewModel.serverApiPath.get()!!
        authService.doSignUp(
            SignUpData(
                viewModel.username.get()!!,
                viewModel.password.get()!!
            )
        ).subscribeOn(Schedulers.io())
            .doOnError(this::onSignUpError)
            .doOnSuccess(this::onSignUpSuccess)
            .subscribe()
    }

    private fun onSignUpError(throwable: Throwable) {

    }

    private fun onSignUpSuccess(response: RestResponse<String>) {
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