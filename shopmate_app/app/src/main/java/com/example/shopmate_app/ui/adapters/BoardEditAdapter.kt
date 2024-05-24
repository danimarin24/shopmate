package com.example.shopmate_app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopmate_app.R
import com.example.shopmate_app.domain.entities.newtworkEntities.BoardEntity
import com.example.shopmate_app.domain.entities.newtworkEntities.CardEntity
import com.google.android.material.textview.MaterialTextView

class BoardEditAdapter(private var boardList: List<BoardEntity>, private val cardsByBoard: Map<Int, List<CardEntity>>)
    : RecyclerView.Adapter<BoardEditAdapter.CardViewHolder>() {

    class CardViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtBoardTitle: MaterialTextView = view.findViewById<MaterialTextView>(R.id.txtBoardTitle)
        val rcvCards : RecyclerView = view.findViewById(R.id.rcvCards)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardEditAdapter.CardViewHolder {
        val layout = LayoutInflater.from(parent.context)
        return CardViewHolder(layout.inflate(R.layout.board_item_rcv_edit, parent, false))
    }

    override fun onBindViewHolder(holder: BoardEditAdapter.CardViewHolder, position: Int) {
        val board = boardList[position]
        holder.txtBoardTitle.text = board.title

        // Configurar el RecyclerView de las cards
        val cards = cardsByBoard[board.boardId] ?: emptyList()
        holder.rcvCards.layoutManager = LinearLayoutManager(holder.view.context, LinearLayoutManager.VERTICAL, false)

        val currentUserId = "1"// obtener el ID del usuario actual
        val profileUserId = "1"// obtener el ID del perfil que se está viendo

        holder.rcvCards.adapter = CardAdapter(cards, currentUserId, profileUserId)
    }

    override fun getItemCount(): Int = boardList.size
}