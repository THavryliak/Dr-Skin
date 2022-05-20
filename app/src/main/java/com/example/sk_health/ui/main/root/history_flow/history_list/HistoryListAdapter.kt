package com.example.sk_health.ui.main.root.history_flow.history_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sk_health.R
import com.example.sk_health.ui.main.root.history_flow.history.HistoryItemViewData
import com.google.android.material.card.MaterialCardView

class HistoryListAdapter(private val onClick: (HistoryItemViewData) -> Unit) : ListAdapter<HistoryItemViewData, HistoryListAdapter.ViewHolder>(HistoryDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.history_item_card, parent, false), onClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(historyView: View, val onClick: (HistoryItemViewData) -> Unit) : RecyclerView.ViewHolder(historyView) {
        private val historyCard: MaterialCardView = itemView.findViewById(R.id.history_card)
        private val diseaseImage: ImageView = itemView.findViewById(R.id.history_card_disease_image)
        private val diseaseName: TextView = itemView.findViewById(R.id.history_card_disease_name)
        private val dateOfCreation: TextView = itemView.findViewById(R.id.history_card_date_of_creation_text)

        private var history: HistoryItemViewData? = null

        init {
            historyView.setOnClickListener {
                history?.let {
                    onClick(it)
                }
            }
        }

        fun bind(history: HistoryItemViewData) {
            this.history = history
            historyCard.strokeColor = history.disease.color.toColorInt()
            diseaseName.text = history.disease.disease
            diseaseImage.setImageResource(history.disease.resId)
            dateOfCreation.text = history.dateOfCreation
        }
    }
}

private object HistoryDiffCallback : DiffUtil.ItemCallback<HistoryItemViewData>() {
    override fun areItemsTheSame(oldItem: HistoryItemViewData, newItem: HistoryItemViewData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: HistoryItemViewData, newItem: HistoryItemViewData): Boolean {
        return oldItem.id == newItem.id
    }
}