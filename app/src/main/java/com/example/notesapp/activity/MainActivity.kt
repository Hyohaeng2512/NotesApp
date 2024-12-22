package com.example.notesapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.notesapp.adapter.MainAdapter
import com.example.notesapp.data.Note
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.my_interfaceinterface.NoteClickInterface
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var reference: DatabaseReference

    private lateinit var mainAdapter: MainAdapter

    private var listNote : ArrayList<Note> = ArrayList()

    private lateinit var username : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val intent = intent;
        val bundle = intent.extras;
        username = bundle?.get("username") as String

        NotifyDataSetChanged@
        reference = FirebaseDatabase.getInstance().getReference("User").child(username)

        reference.child("Note").get().addOnCompleteListener{ task ->
            if (task.isSuccessful){
                if (task.result.exists()) {
                    val dataSnapshot : DataSnapshot = task.result
                    for (issue in dataSnapshot.children){

                        //xử lý data
                        var tempNote : Note = Note(issue.child("title").value.toString(),
                            issue.child("content").value.toString())
                        listNote.add(tempNote)
                    }


                    // đưa list vào recycleview
                    if (listNote.isNotEmpty()){
                        binding.rcvMain.layoutManager = LinearLayoutManager(this)
                        mainAdapter = MainAdapter(listNote,object : NoteClickInterface{
                            override fun OnClickItemListener(info: Note) {
                                onClickGotoDetail(info)
                            }
                        })
                        binding.rcvMain.adapter = mainAdapter
                    }
                }
            }
        }

        //thêm ghi chú
        binding.addButton.setOnClickListener {
            val intent = Intent(this,AddActivity::class.java)
            var bundle = Bundle()
            bundle.putString("username",username)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        binding.profile.setOnClickListener {
            val intent = Intent(this,ProfileActivity::class.java)
            var bundle = Bundle()
            bundle.putString("username",username)
            intent.putExtras(bundle)
            startActivity(intent)
        }



    }

    private fun onClickGotoDetail(info: Note) {
        val intent = Intent(this,DetailActivity::class.java)
        var bundle = Bundle()
        bundle.putSerializable("object_detail",info)
        bundle.putString("username",username)
        intent.putExtras(bundle)
        startActivity(intent)
    }


}