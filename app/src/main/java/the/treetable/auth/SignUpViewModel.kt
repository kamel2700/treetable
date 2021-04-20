package the.treetable.auth

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import the.treetable.BuildConfig
import javax.inject.Inject

class SignUpViewModel @Inject constructor() : ViewModel() {

    var serverApiPath: ObservableField<String> = ObservableField(BuildConfig.BASE_URL)

    var username: ObservableField<String> = ObservableField("")

    var password: ObservableField<String> = ObservableField("")

}