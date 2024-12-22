package com.example.notesapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.data.Note
import com.example.notesapp.databinding.DetailLayoutBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailActivity : AppCompatActivity(){

    private lateinit var binding: DetailLayoutBinding
    private lateinit var reference: DatabaseReference

    private lateinit var titleRef: DatabaseReference
    private lateinit var contentRef: DatabaseReference

    private lateinit var deleteRef:DatabaseReference

    private var keycontent : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DetailLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.detailBack.setOnClickListener {
            finish()
        }

        var intent = intent;
        val bundle = intent.extras;
        val note = bundle?.get("object_detail") as Note

        val username = bundle.get("username") as String

        binding.detailTittle.setText(note.title)
        binding.detailContent.setText(note.content)

        binding.changeButton.setOnClickListener {

            // Tìm key của đối tượng
            reference = FirebaseDatabase.getInstance().getReference("User").child(username)
            reference.child("Note").get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result.exists()) {
                        val dataSnapshot: DataSnapshot = task.result
                        for (issue in dataSnapshot.children) {
                            var temptitle = issue.child("title").value.toString()
                            var tempcontent = issue.child("content").value.toString()
                            if((note.title==temptitle)&&note.content==tempcontent){
                                keycontent = issue.key.toString()
                                break
                            }
                        }
                        Log.d("keycontent",keycontent)

                        titleRef = FirebaseDatabase.getInstance().getReference("User")
                            .child(username).child("Note").child(keycontent).child("title")
                        titleRef.setValue(binding.detailTittle.text.toString())
                        contentRef = FirebaseDatabase.getInstance().getReference("User")
                            .child(username).child("Note").child(keycontent).child("content")
                        contentRef.setValue(binding.detailContent.text.toString())

                        val mainIntent : Intent = Intent(this,MainActivity::class.java)
                        startActivity(mainIntent)
                        finish()

                    }
                }
            }

        }

        binding.deleteButton.setOnClickListener {

            // Tìm key của đối tượng
            reference = FirebaseDatabase.getInstance().getReference("User").child(username)
            reference.child("Note").get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result.exists()) {
                        val dataSnapshot: DataSnapshot = task.result
                        for (issue in dataSnapshot.children) {
                            var temptitle = issue.child("title").value.toString()
                            var tempcontent = issue.child("content").value.toString()
                            if ((note.title == temptitle) && note.content == tempcontent) {
                                keycontent = issue.key.toString()
                                break
                            }
                        }
                        Log.d("keycontent", keycontent)

                        deleteRef = FirebaseDatabase.getInstance().getReference("User")
                            .child(username).child("Note").child(keycontent)
                        deleteRef.removeValue { error, ref ->
                            Toast.makeText(applicationContext, "Xóa thành công", Toast.LENGTH_SHORT).show()
                        }

                        val mainIntent : Intent = Intent(this,MainActivity::class.java)
                        startActivity(mainIntent)
                        finish()

                    }
                }
            }



        }
    }

}