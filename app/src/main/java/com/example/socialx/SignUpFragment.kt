package com.example.socialx

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.socialx.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentSignUpBinding
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        auth = Firebase.auth

        binding.btnRegister.setOnClickListener {
            registerUser()
        }

        return binding.root
    }

    private fun registerUser() {
        val name: String = binding.etNameSignUp.text.toString()
        val email: String = binding.etEmailSignUp.text.toString()
        val password: String = binding.etPasswordSignUp.text.toString()
        val phone: String = binding.etNumberSignUp.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {task->
                    if(task.isSuccessful){
                        Log.d("TAG", "createUserWithEmail:success")
                        val user: FirebaseUser = task.result!!.user!!
                        Toast.makeText(context, " Registered Successfully. Please sign in!!.",
                            Toast.LENGTH_SHORT).show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(context, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                    }
                }
    }
