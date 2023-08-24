package com.example.snakegame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.snakegame.databinding.FragmentLeaderboardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LeaderboardFragment : Fragment() {

    private lateinit var binding: FragmentLeaderboardBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    val usersStringList = mutableListOf<String>("test1")

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
            R.layout.fragment_leaderboard,
            container,
            false
        )

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_leaderboardFragment_to_gameFragment)
        }

        db.collection("users").get().addOnSuccessListener { result ->
            for (doc in result.documents) {
                if (doc["email"] == auth.currentUser!!.email) {
                    usersStringList.add("kdbvlkfdvjldfnv")
                }
            }
        }

        db.collection("users").get().addOnCompleteListener { res -> usersStringList.add(res.toString()) }

        binding.listViewLeaderboard.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, usersStringList)

        return binding.root
    }

}