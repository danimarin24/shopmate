package com.example.shopmate_app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopmate_app.R
import com.example.shopmate_app.domain.entities.dbEntities.DbBoardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity
import com.google.android.material.textview.MaterialTextView

class BoardAdapter(var boardList: List<DbBoardEntity>?)
    : RecyclerView.Adapter<BoardAdapter.CardViewHolder>() {

    private var cardSeleccionada: Int = -1

    class CardViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtBoardTitle = view.findViewById<MaterialTextView>(R.id.txtBoardTitle)
        val rcvCards : RecyclerView = view.findViewById(R.id.rcvCards)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardAdapter.CardViewHolder {
        var layout = LayoutInflater.from(parent.context)

        return CardViewHolder(layout.inflate(R.layout.board_item_rcv, parent, false))
    }

    override fun onBindViewHolder(holder: BoardAdapter.CardViewHolder, position: Int) {
        val board = boardList?.get(position)

        holder.txtBoardTitle.text = board?.title
        // Configurar el RecyclerView de las cards

        holder.rcvCards.layoutManager = LinearLayoutManager(holder.view.context, LinearLayoutManager.VERTICAL, false)
        holder.rcvCards.adapter = CardAdapter(board?.cards ?: emptyList())
    }

    override fun getItemCount(): Int = boardList!!.size

    override fun getItemViewType(position: Int): Int {
        return if (position == cardSeleccionada) 1 else 0
    }
}