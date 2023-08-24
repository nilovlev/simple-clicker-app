package com.example.snakegame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.snakegame.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        db = Firebase.firestore
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register,
            container,
            false
        )

        binding.goToLoginButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.submitRegistrationButton.setOnClickListener {
            registerUser(
                binding.registerEmailTextEdit.text.toString(),
                binding.registerPasswordTextEdit.text.toString()
            )
        }

        return binding.root
    }

    private fun registerUser(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(context, "Empty email or password", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!it.isSuccessful) {
                Toast.makeText(context, it.exception?.message.toString(), Toast.LENGTH_SHORT).show()
            } else {
                db.collection("users").add(hashMapOf(
                    "email" to email,
                    "score" to 0
                )).addOnSuccessListener {
                    Toast.makeText(context, "Successfully registered", Toast.LENGTH_SHORT).show()

                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }.addOnFailureListener { ex ->
                    Toast.makeText(context, ex.message, Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

}
