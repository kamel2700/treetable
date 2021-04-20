package the.treetable.login.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import the.treetable.R
import the.treetable.login.activities.AddIdentityActivity
import the.treetable.login.activities.OnFragmentWorkCallback

class RegisterIdentityStep1Fragment() : Fragment(), AddIdentityActivity.WorkDoneFragment {

    override lateinit var onFragmentWorkCallback: OnFragmentWorkCallback;

    lateinit var serverInputLayout: TextInputLayout;
    lateinit var usernameInputLayout: TextInputLayout;
    lateinit var nameInputLayout: TextInputLayout;

    lateinit var serverEditText: EditText;
    lateinit var usernameEditText: EditText;
    lateinit var nameEditText: EditText;


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.register_identity_1_layout, container, false)
        serverInputLayout = view.findViewById(R.id.register_server_inputlayout);
        usernameInputLayout = view.findViewById(R.id.register_username_inputlayout);
        nameInputLayout = view.findViewById(R.id.register_name_inputlayout);
        serverEditText = view.findViewById(R.id.register_server_edit_text);
        usernameEditText = view.findViewById(R.id.register_username_edit_text);
        nameEditText = view.findViewById(R.id.register_name_edit_text);
        serverEditText.doOnTextChanged(this::removeErrorOnTextChanged)
        usernameEditText.doOnTextChanged(this::removeErrorOnTextChanged);
        nameEditText.doOnTextChanged(this::removeErrorOnTextChanged);
        view.findViewById<Button>(R.id.register_step_next)
            .setOnClickListener(this::onNextStepCalled);
        if (savedInstanceState == null) {
            serverEditText.setText(onFragmentWorkCallback.getRegisterWork().server);
            usernameEditText.setText(onFragmentWorkCallback.getRegisterWork().username);
            nameEditText.setText(onFragmentWorkCallback.getRegisterWork().name);
        }
        return view;
    }

    private fun removeErrorOnTextChanged(s: CharSequence?, start: Int, end: Int, count: Int) {
        serverInputLayout.error = "";
        nameInputLayout.error = "";
        usernameInputLayout.error = "";
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onFragmentWorkCallback = (context as OnFragmentWorkCallback);
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        println("on save reg 1");
    }

    private fun onNextStepCalled(nextStepButton: View) {
        var errCount = 0;
        val serverStr = serverEditText.text.toString();
        if (serverStr == "") {
            serverInputLayout.error = "Server must not be empty";
            errCount++;
        }
        val usernameStr = usernameEditText.text.toString();
        if (usernameStr == "") {
            usernameInputLayout.error = "Username must not be empty";
            errCount++;
        }
        val nameStr = nameEditText.text.toString();
        if (nameStr == "") {
            nameInputLayout.error = "Name must not be empty";
            errCount++;
        }
        if (errCount == 0) {
            Toast.makeText(context, "Of course your server does exists", Toast.LENGTH_SHORT).show();
            onFragmentWorkCallback.getRegisterWork().server = serverStr;
            onFragmentWorkCallback.getRegisterWork().username = usernameStr;
            onFragmentWorkCallback.getRegisterWork().name = nameStr;
            onFragmentWorkCallback.onFragmentWorkDone();
        }
    }

}