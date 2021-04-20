package the.treetable.login.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import the.treetable.R
import the.treetable.login.activities.AddIdentityActivity
import the.treetable.login.activities.OnFragmentWorkCallback

class RegisterIdentityStep3Fragment : Fragment(), AddIdentityActivity.WorkDoneFragment {

    override lateinit var onFragmentWorkCallback: OnFragmentWorkCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.register_identity_3_layout, container, false);
        view.findViewById<Button>(R.id.register_step_next)
            .setOnClickListener(this::onNextStepCalled);
        view.findViewById<TextView>(R.id.reg_confirm_server_textview).text =
            onFragmentWorkCallback.getRegisterWork().server;
        view.findViewById<TextView>(R.id.reg_confirm_username_textview).text =
            onFragmentWorkCallback.getRegisterWork().username;
        view.findViewById<TextView>(R.id.reg_confirm_name_textview).text =
            onFragmentWorkCallback.getRegisterWork().name;
        val passwordLength = onFragmentWorkCallback.getRegisterWork().password.length;
        val passwordString = StringBuilder(passwordLength);
        for (i in 0..passwordLength) {
            passwordString.append("*")
        }
        view.findViewById<TextView>(R.id.reg_confirm_password_textview).text =
            passwordString.toString();
        return view;
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onFragmentWorkCallback = (context as OnFragmentWorkCallback);
    }

    private fun onNextStepCalled(nextStepButton: View) {
        onFragmentWorkCallback.getRegisterWork().activated = true;
        onFragmentWorkCallback.onFragmentWorkDone();
    }

}