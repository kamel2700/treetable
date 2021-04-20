package the.treetable.login.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import the.treetable.R

class HomeActivity : Activity() {

    lateinit var addIdentityButton: Button;

    lateinit var sharedPreferences: SharedPreferences;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("identity_username")) {
            loadHomeLayout();
        } else {
            loadStubLayout();
        }
    }

    private fun loadHomeLayout() {
        setContentView(R.layout.home_activity_layout);
        findViewById<TextView>(R.id.identity_display_textview).text = StringBuilder()
            .append("You are logged in \nat server = ")
            .append(sharedPreferences.getString("identity_server", ""))
            .append("\nas user = ")
            .append(sharedPreferences.getString("identity_username", ""))
            .append("\nwith password = ")
            .append(sharedPreferences.getString("identity_password", ""))
            .toString();
        findViewById<Button>(R.id.delete_identity_button).setOnClickListener(this::onRemoveIdentityClick);
    }

    private fun loadStubLayout() {
        setContentView(R.layout.home_activity_login_layout);
        addIdentityButton = findViewById(R.id.add_identity_button);
        addIdentityButton.setOnClickListener(this::onAddIdentityClick);
    }

    private fun onAddIdentityClick(view: View) {
        val intent = Intent(this, AddIdentityActivity::class.java);
        startActivityForResult(intent, 100);
    }

    private fun onRemoveIdentityClick(view: View) {
        AlertDialog.Builder(this)
            .setTitle("Confirm action")
            .setMessage("Remove current user identity?")
            .setPositiveButton("Yep") { _, _ ->
                sharedPreferences.edit()
                    .remove("identity_username")
                    .remove("identity_password")
                    .remove("identity_server")
                    .apply();
                loadStubLayout();
            }
            .setNegativeButton("Nope") { _, _ -> }
            .create().show();
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null) {
            return;
        }
        if (requestCode == 100) {
            //set identity and show
            sharedPreferences.edit()
                .putString("identity_username", data.extras!!.getString("username"))
                .putString("identity_password", data.extras!!.getString("password"))
                .putString("identity_server", data.extras!!.getString("server"))
                .apply();
            loadHomeLayout();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}