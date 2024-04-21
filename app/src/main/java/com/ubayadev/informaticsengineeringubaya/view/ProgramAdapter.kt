package com.ubayadev.informaticsengineeringubaya.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.ubayadev.informaticsengineeringubaya.databinding.CardProgramBinding
import com.ubayadev.informaticsengineeringubaya.model.Program

class ProgramAdapter( val programList:ArrayList<Program>) : RecyclerView.Adapter<ProgramAdapter.programViewHolder>(){
    class programViewHolder(var binding: CardProgramBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):programViewHolder {
        val binding = CardProgramBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return programViewHolder(binding)
    }

    override fun onBindViewHolder(holder: programViewHolder, position: Int) {
        holder.binding.txtProgram.text = programList[position].nama
        holder.binding.btnDetail.setOnClickListener {
            val action = FragmentHomeDirections.actionFragmentHomeToFragmentDetail(programList[position].id)
            Navigation.findNavController(it).navigate(action)
        }
        val builder = Picasso.Builder(holder.itemView.context)
        builder.listener{picasso, uri, exception->exception.printStackTrace()}
        builder.build().load(programList[position].img_profil).into(holder.binding.imgProfile)
    }
    override fun getItemCount(): Int {
        return programList.size
    }
    fun updateprogramList(newprogramList: ArrayList<Program>) {
        programList.clear()
        programList.addAll(newprogramList)
        notifyDataSetChanged()
    }
}