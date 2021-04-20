package the.treetable.login.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import the.treetable.R
import the.treetable.login.fragments.LoginIdentityFragment
import the.treetable.login.fragments.RegisterIdentityStep1Fragment
import the.treetable.login.fragments.RegisterIdentityStep2Fragment
import the.treetable.login.fragments.RegisterIdentityStep3Fragment
import java.io.Serializable

interface OnFragmentWorkCallback {
    fun getRegisterWork(): AddIdentityActivity.RegisterData;
    fun getLoginWork(): AddIdentityActivity.LoginData;
    fun onFragmentWorkDone();
}

class AddIdentityActivity : FragmentActivity(), OnFragmentWorkCallback {

    private lateinit var navigationView: BottomNavigationView;
    private var registerData = RegisterData();
    private var loginData = LoginData();
    private var currentFragmentStr = "";

    interface WorkDoneFragment {
        var onFragmentWorkCallback: OnFragmentWorkCallback;
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_identity_activity_layout);
        navigationView = findViewById(R.id.add_identity_methods_nav);
        navigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        //var fragmentContainerId = R.id.add_identity_fragment_container;
        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .add(fragmentContainerId, RegisterIdentityStep1Fragment(), "identity_register_1")
//                .add(fragmentContainerId, RegisterIdentityStep2Fragment(), "identity_register_2")
//                .add(fragmentContainerId, RegisterIdentityStep1Fragment(), "identity_register_3")
//                .add(fragmentContainerId, LoginIdentityFragment(), "identity_login")
//                .commit();
            goToFragment("identity_login", LoginIdentityFragment());
            navigationView.selectedItemId = R.id.sign_in;
        } else {

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("current_fragment", currentFragmentStr);
        outState.putSerializable("login_data", loginData);
        outState.putSerializable("register_data", registerData);
        super.onSaveInstanceState(outState);
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState);
        registerData = savedInstanceState.getSerializable("register_data") as RegisterData;
        loginData = savedInstanceState.getSerializable("login_data") as LoginData;
        currentFragmentStr = savedInstanceState.getString("current_fragment") as String;
//        val activity = this;
//        (supportFragmentManager.findFragmentByTag(currentFragmentStr) as WorkDoneFragment).onFragmentWorkCallback = object : OnFragmentWorkCallback {
//            override fun getLoginWork(): LoginData = activity.loginData;
//            override fun getRegisterWork(): RegisterData = activity.registerData;
//            override fun onFragmentWorkDone() = activity.onFragmentWorkDone();
//        };
        //goToFragment(savedInstanceState.getString("current_fragment") as String);
    }

    override fun getLoginWork(): LoginData {
        return loginData;
    }

    override fun getRegisterWork(): RegisterData {
        return registerData;
    }

    private fun goToFragment(name: String, fragment: Fragment) {
//        val activity = this;
//        //val fragment = supportFragmentManager.findFragmentByTag(currentFragmentStr) as Fragment;
//        (fragment as WorkDoneFragment).onFragmentWorkCallback = object : OnFragmentWorkCallback {
//            override fun getLoginWork(): LoginData = activity.loginData;
//            override fun getRegisterWork(): RegisterData = activity.registerData;
//            override fun onFragmentWorkDone() = activity.onFragmentWorkDone();
//        };
        val transaction = supportFragmentManager.beginTransaction();
        if (currentFragmentStr == "") {
            transaction.add(R.id.add_identity_fragment_container, fragment, name);
        } else {
            transaction.replace(R.id.add_identity_fragment_container, fragment, name);
        }
        transaction.addToBackStack("frag_$name").commit();
        currentFragmentStr = name;
    }

    override fun onFragmentWorkDone() {
        when (currentFragmentStr) {
            "identity_login" -> {
                // we have all done, exit with login result
                onIdentityAdded();
            }
            "identity_register_1" -> {
                goToFragment("identity_register_2", RegisterIdentityStep2Fragment());
            }
            "identity_register_2" -> {
                goToFragment("identity_register_3", RegisterIdentityStep3Fragment());
            }
            "identity_register_3" -> {
                // we have all done, exit with register result or redirect to login screen
                goToFragment("identity_login", LoginIdentityFragment());
                navigationView.selectedItemId = R.id.sign_in;
                Toast.makeText(
                    this,
                    "New user is registered : $registerData",
                    Toast.LENGTH_LONG
                ).show();
            }
        }
    }

    private fun onIdentityAdded() {
        val intent = Intent();
        intent.putExtra("username", loginData.username);
        intent.putExtra("password", loginData.password);
        intent.putExtra("server", loginData.server);
        setResult(0, intent)
        finish();
    }

    override fun onBackPressed() {
        if (currentFragmentStr != "identity_login" && currentFragmentStr != "identity_register_1") {
            supportFragmentManager.popBackStack();
            if (currentFragmentStr == "identity_register_2") {
                currentFragmentStr = "identity_register_1";
            }
            if (currentFragmentStr == "identity_register_3") {
                currentFragmentStr = "identity_register_2";
            }
        } else {
            if (currentFragmentStr == "identity_login") {
                finish()
            }
        }
    }


    class RegisterData() : Serializable {
        var server: String = "";
        var username: String = "";
        var name: String = "";
        var password: String = "";
        var activated: Boolean = false;
        override fun toString(): String {
            return "RegisterData(server='$server', username='$username', name='$name', password='$password', activated=$activated)"
        }
    }

    class LoginData() : Serializable {
        var server: String = "";
        var username: String = "";
        var password: String = "";
    }

    override fun onDestroy() {
        super.onDestroy();
    }

    private fun onNavigationItemSelected(item: MenuItem): Boolean {
        println(item);
        if (item.itemId == R.id.sign_in) {
            goToFragment("identity_login", LoginIdentityFragment());
        } else {
            goToFragment("identity_register_1", RegisterIdentityStep1Fragment())
        }
        return true;
    }
}