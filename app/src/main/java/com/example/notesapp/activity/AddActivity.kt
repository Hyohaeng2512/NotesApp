package com.example.notesapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.data.Note
import com.example.notesapp.databinding.AddLayoutBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddActivity : AppCompatActivity() {

    private lateinit var binding: AddLayoutBinding
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AddLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBack.setOnClickListener {
            intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        val intent = intent;
        val bundle = intent.extras;
        val username = bundle?.get("username") as String

        binding.add.setOnClickListener {
            var content = binding.addContent.text.toString()
            var title = binding.addTittle.text.toString()
            if(content.isNotEmpty() && title.isNotEmpty()) {
                var note : Note = Note(title,content)
                reference = FirebaseDatabase.getInstance().getReference("User").child(username).child("Note")
                reference.push().setValue(note).addOnCompleteListener {

                }

                val mainIntent : Intent = Intent(applicationContext,MainActivity::class.java)
                startActivity(mainIntent)
                finish()

            }
        }

    }

}