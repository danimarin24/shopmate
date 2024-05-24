package com.example.shopmate_app.ui.fragments.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shopmate_app.R

open class SwipeToDeleteCallback(context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private val deleteIcon: Drawable = ContextCompat.getDrawable(context, R.drawable.trash_can_outline)!!
    private val assignIcon: Drawable = ContextCompat.getDrawable(context, R.drawable.account_convert)!!
    private val intrinsicWidth = deleteIcon.intrinsicWidth
    private val intrinsicHeight = deleteIcon.intrinsicHeight
    private val backgroundDelete = ColorDrawable(Color.RED)
    private val backgroundAssign = ColorDrawable(Color.GREEN)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // This method will be overridden in the fragment
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top

        if (dX > 0) {
            // Swiping to the right
            backgroundAssign.setBounds(itemView.left, itemView.top, itemView.left + dX.toInt(), itemView.bottom)
            backgroundAssign.draw(c)

            val assignIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
            val assignIconMargin = (itemHeight - intrinsicHeight) / 2
            val assignIconLeft = itemView.left + assignIconMargin
            val assignIconRight = itemView.left + assignIconMargin + intrinsicWidth
            val assignIconBottom = assignIconTop + intrinsicHeight

            assignIcon.setBounds(assignIconLeft, assignIconTop, assignIconRight, assignIconBottom)
            assignIcon.draw(c)
        } else if (dX < 0) {
            // Swiping to the left
            backgroundDelete.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
            backgroundDelete.draw(c)

            val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
            val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
            val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
            val deleteIconRight = itemView.right - deleteIconMargin
            val deleteIconBottom = deleteIconTop + intrinsicHeight

            deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
            deleteIcon.draw(c)
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}
