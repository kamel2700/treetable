package the.treetable.login.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import the.treetable.R
import the.treetable.login.activities.AddIdentityActivity
import the.treetable.login.activities.OnFragmentWorkCallback

class LoginIdentityFragment : Fragment(), AddIdentityActivity.WorkDoneFragment {

    override lateinit var onFragmentWorkCallback: OnFragmentWorkCallback;

    lateinit var serverEditText: EditText;
    lateinit var usernameEditText: EditText;
    lateinit var passwordEditText: EditText;
    lateinit var doLoginButton: Button;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.login_identity_layout, container, false);
        serverEditText = view.findViewById(R.id.login_server_edittext);
        usernameEditText = view.findViewById(R.id.login_username_edittext);
        passwordEditText = view.findViewById(R.id.login_password_edittext);
        doLoginButton = view.findViewById(R.id.do_login_button);
        doLoginButton.setOnClickListener(this::onLoginClicked);
        if (onFragmentWorkCallback.getRegisterWork().activated) {
            serverEditText.setText(onFragmentWorkCallback.getRegisterWork().server);
            usernameEditText.setText(onFragmentWorkCallback.getRegisterWork().username);
            passwordEditText.setText(onFragmentWorkCallback.getRegisterWork().password);
        }
        return view;
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onFragmentWorkCallback = (context as OnFragmentWorkCallback);
    }

    private fun onLoginClicked(nextStepButton: View) {
        val serverStr = serverEditText.text.toString();
        val usernameStr = usernameEditText.text.toString();
        val passwordStr = passwordEditText.text.toString();
        onFragmentWorkCallback.getLoginWork().server = serverStr;
        onFragmentWorkCallback.getLoginWork().username = usernameStr;
        onFragmentWorkCallback.getLoginWork().password = passwordStr;
        onFragmentWorkCallback.onFragmentWorkDone();

    }
}