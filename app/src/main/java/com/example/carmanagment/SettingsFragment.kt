package com.example.carmanagment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_settings.view.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class SettingsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        view.logOutButton.setOnClickListener(View.OnClickListener {
            signOutAlert()
        })

        view.deleteUserButton.setOnClickListener(View.OnClickListener {
            deleteAccountAlert()
        })

        view.changePasswordButton.setOnClickListener(View.OnClickListener {
            changePassword()
        })
        return view
    }

    private fun changePassword(){
        val mBuilder =
            AlertDialog.Builder(context)

        val mView: View = layoutInflater.inflate(R.layout.new_password, null)
        var tryToChangePassword = mView.findViewById<View>(R.id.tryToChangePassword) as Button
        var oldPassword = mView.findViewById<View>(R.id.oldPassword) as EditText
        var newPass = mView.findViewById<View>(R.id.newPass) as EditText
        mBuilder.setView(mView)

        val changePassword = mBuilder.create()

        changePassword.show()


        tryToChangePassword.setOnClickListener(View.OnClickListener {

            if(oldPassword.text.toString().isEmpty() || newPass.text.toString().isEmpty()){
                Toast.makeText(context, "Wypełnij oba pola", Toast.LENGTH_SHORT).show()
            }
            else {
                val user: FirebaseUser?
                user = FirebaseAuth.getInstance().currentUser

                val oldpass = oldPassword.text.toString()
                val newpass = newPass.text.toString()
                val email = user!!.email
                val credential = EmailAuthProvider.getCredential(email!!, oldpass)
                user.reauthenticate(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            user.updatePassword(newpass).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Pomyslnie zmieniono haslo",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    changePassword.dismiss()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Nieudana zmiana hasla",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Coś poszło nie tak",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        })
    }

    private fun isPasswordValid(pass: String): Boolean {
        var valid = false
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$"
        pattern = Pattern.compile(PASSWORD_REGEX)

        matcher = pattern.matcher(pass)
        valid = matcher.matches()
        return valid
    }

    private fun signOutAlert() {
        val deleteAlert = AlertDialog.Builder(context)
        deleteAlert.setMessage("Na pewno chcesz się wylogować?")
        deleteAlert.setPositiveButton(
            "Tak"
        ) { dialog, which -> FirebaseAuth.getInstance().signOut()
            updateUI()}
        deleteAlert.setNegativeButton(
            "Nie"
        ) { dialog, which -> }
        deleteAlert.show()

    }

    private fun deleteAccountAlert() {
        val deleteAlert = AlertDialog.Builder(context)
        deleteAlert.setMessage("Na pewno usunąć konto i wszystkie powiązane z nim rzeczy?")
        deleteAlert.setPositiveButton(
            "Tak"
        ) { dialog, which -> deleteUserAccount() }
        deleteAlert.setNegativeButton(
            "Nie"
        ) { dialog, which -> }
        deleteAlert.show()
    }

    private fun deleteUserAccount(){

        var firebaseAuth : FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            currentUser.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        updateUI()
                    }
                    else{
                        Toast.makeText(context, "Coś poszło nie tak", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun updateUI(){

        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
    }
}