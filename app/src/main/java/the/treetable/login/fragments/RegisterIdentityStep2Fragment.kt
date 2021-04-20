package the.treetable.login.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import the.treetable.R
import the.treetable.login.activities.AddIdentityActivity
import the.treetable.login.activities.OnFragmentWorkCallback

class RegisterIdentityStep2Fragment() : Fragment(), AddIdentityActivity.WorkDoneFragment {

    override lateinit var onFragmentWorkCallback: OnFragmentWorkCallback

    lateinit var password1EditText: EditText;
    lateinit var password2EditText: EditText;
    lateinit var passwordInputLayout: TextInputLayout;
    lateinit var passwordRepeatInputLayout: TextInputLayout;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.register_identity_2_layout, container, false);
        password1EditText = view.findViewById(R.id.register_password1_edit_text);
        password2EditText = view.findViewById(R.id.register_password2_edit_text);
        passwordInputLayout = view.findViewById(R.id.register_password_inputlayout);
        passwordRepeatInputLayout = view.findViewById(R.id.register_repeat_password_inputlayout);
        password1EditText.doOnTextChanged(this::removeErrorOnTextChanged);
        password2EditText.doOnTextChanged(this::removeErrorOnTextChanged);
        view.findViewById<Button>(R.id.register_step_next)
            .setOnClickListener(this::onNextStepCalled);
        if (savedInstanceState == null) {
            password1EditText.setText(onFragmentWorkCallback.getRegisterWork().password);
            password2EditText.setText(onFragmentWorkCallback.getRegisterWork().password);
        }
        return view;
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onFragmentWorkCallback = (context as OnFragmentWorkCallback);
    }

    private fun removeErrorOnTextChanged(s: CharSequence?, start: Int, end: Int, count: Int) {
        passwordInputLayout.error = "";
        passwordRepeatInputLayout.error = "";
    }

    private fun onNextStepCalled(nextStepButton: View) {
        var errors = 0;
        val password = password1EditText.text.toString();
        if (password.length < 8) {
            if (password == "") {
                passwordInputLayout.error = "Password must not be empty";
            } else {
                passwordInputLayout.error = "Password must contain at least 8 symbols";
            }
            errors++;
        }
        if (password2EditText.text.toString() != password) {
            passwordRepeatInputLayout.error = "Passwords does not match";
            errors++
        }
        if (errors == 0) {
            onFragmentWorkCallback.getRegisterWork().password = password1EditText.text.toString();
            onFragmentWorkCallback.onFragmentWorkDone();
        }
    }
}