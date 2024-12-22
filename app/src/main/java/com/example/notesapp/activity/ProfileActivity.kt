package com.example.notesapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.databinding.ProfileLayoutBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ProfileLayoutBinding
    private lateinit var username : String

    private lateinit var reference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ProfileLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent;
        val bundle = intent.extras;
        username = bundle?.get("username") as String

        binding.profileBack.setOnClickListener {
            finish()
        }


        reference = FirebaseDatabase.getInstance().getReference("User").child(username)

        reference.get().addOnCompleteListener { task ->
            var dataSnapshot : DataSnapshot = task.result

            binding.profileName.text = "Tên: " + dataSnapshot.child("name").value.toString()
            binding.profileEmail.text = "Email: " + dataSnapshot.child("email").value.toString()
        }




        binding.Logout.setOnClickListener {
            val logIntent = Intent(this,LoginActivity::class.java)
            startActivity(logIntent)
            finish()
        }

        binding.changePassword.setOnClickListener {
            val chgIntent = Intent(this,ChangePasswordActivity::class.java)
            val bundle : Bundle = Bundle()
            bundle.putString("username",username)
            chgIntent.putExtras(bundle)
            startActivity(chgIntent)

        }

//        binding.profileName.text = reference.child("name").get().result.toString()



    }

}