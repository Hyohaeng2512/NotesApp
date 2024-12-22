package com.example.notesapp.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.data.User
import com.example.notesapp.databinding.RegisterLayoutBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : RegisterLayoutBinding
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RegisterLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.regBack.setOnClickListener{
            finish()
        }

        binding.regButton.setOnClickListener{

            val username = binding.rgUsername.text.toString()
            val email = binding.rgEmail.text.toString()
            val password = binding.rgPassword.text.toString()
            val cfPassword = binding.rgConfirmPassword.text.toString()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && cfPassword.isNotEmpty()){
                if (password == cfPassword) {
                    val user : User = User()
                    user.name = username
                    user.password = password
                    user.email = email
                    val database = Firebase.database
                    reference = database.getReference("User")
                    reference.child(username).get().addOnCompleteListener{ task ->
                        if(task.result.exists()){
                            Toast.makeText(this,"Tài khoản đã tồn tại",Toast.LENGTH_SHORT).show()
                        }
                        else {
                            reference.child(username).setValue(user).addOnCompleteListener{
                                Toast.makeText(this,"Đăng ký thành công, vui lòng đăng nhập",Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        }
                    }

                } else {
                    Toast.makeText(this,"Mật khẩu chưa trùng khớp", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this,"Hãy nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            }

        }

    }
}