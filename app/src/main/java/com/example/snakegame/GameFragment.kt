package com.example.snakegame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.snakegame.databinding.FragmentGameBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var counter = 0
    private var docId = ""

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
            R.layout.fragment_game,
            container,
            false
        )


        db.collection("users").get().addOnSuccessListener { result ->
            for (doc in result.documents) {
                if (doc["email"] == auth.currentUser!!.email) {
                    counter = doc["score"].toString().toInt()
                    binding.counterTextView.text = counter.toString()
                    docId = doc.id
                }
            }
        }

        binding.clickButton.setOnClickListener {
            ++counter
            binding.counterTextView.text = counter.toString()

        }

        binding.goToLeaderboard.setOnClickListener {
            findNavController().navigate(R.id.action_gameFragment_to_leaderboardFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        db.collection("users").document(docId).update("email", auth.currentUser!!.email!!, "score", counter)
    }

}