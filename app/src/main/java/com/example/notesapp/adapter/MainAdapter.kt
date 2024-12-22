package com.example.notesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.data.Note
import com.example.notesapp.databinding.ItemLayoutBinding
import com.example.notesapp.my_interfaceinterface.NoteClickInterface

class MainAdapter(private val listNote: List<Note>,
                  val onItemClick: NoteClickInterface): RecyclerView.Adapter<MainAdapter.NoteViewHolder>(){
    inner class NoteViewHolder (private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        val cardViewLayout : CardView = binding.noteLayout

        fun bindNoteView(note : Note){

            binding.noteTitle.text = note.title
            binding.noteContent.text = note.content

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listNote.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentItem = listNote[position]
        holder.bindNoteView(currentItem)

        holder.cardViewLayout.setOnClickListener{
            onItemClick.OnClickItemListener(currentItem)
        }
    }
}