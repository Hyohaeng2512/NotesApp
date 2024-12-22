package com.example.notesapp.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.databinding.ChangePasswordLayoutBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ChangePasswordLayoutBinding

    private lateinit var reference : DatabaseReference
    private lateinit var chgPasswordReference: DatabaseReference

    private lateinit var username : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ChangePasswordLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent;
        val bundle = intent.extras;
        username = bundle?.get("username") as String

        binding.changePasswordButton.setOnClickListener {

            val oldPassword = binding.chgPasswordOld.text.toString()
            val newPassword = binding.chgPassword.text.toString()
            val confNewPassword = binding.chgConfirmPassword.text.toString()

            if(oldPassword.isNotEmpty() && newPassword.isNotEmpty() && confNewPassword.isNotEmpty()){
                reference = FirebaseDatabase.getInstance().getReference("User").child(username)
                reference.get().addOnCompleteListener { task ->
                    var dataSnapshot : DataSnapshot = task.result
                    if (oldPassword == dataSnapshot.child("password").value.toString()){
                        if(newPassword == confNewPassword){
                            chgPasswordReference = reference.child("password")
                            chgPasswordReference.setValue(newPassword)
                            Toast.makeText(this,"Đổi mật khẩu thành công",Toast.LENGTH_SHORT).show()
                            finish()
                        }

                        else {
                            Toast.makeText(this,"Mật khẩu chưa trùng khớp",Toast.LENGTH_SHORT).show()
                        }

                    }

                    else{
                        Toast.makeText(this,"Mật khẩu chưa chính xác",Toast.LENGTH_SHORT).show()
                    }

                }


            }
            else {
                Toast.makeText(this,"Vui lòng điền đầy đủ thông tin",Toast.LENGTH_SHORT).show()
            }
        }

    }

}