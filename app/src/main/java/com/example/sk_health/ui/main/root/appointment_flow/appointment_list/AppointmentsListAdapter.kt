package com.example.sk_health.ui.main.root.appointment_flow.appointment_list

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sk_health.R
import com.google.android.material.card.MaterialCardView

class AppointmentsListAdapter(private val onClick: (AppointmentItemViewData) -> Unit) : ListAdapter<AppointmentItemViewData, AppointmentsListAdapter.ViewHolder>(AppointmentDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.appointment_item_card, parent, false), onClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(appointmentView: View,  val onClick: (AppointmentItemViewData) -> Unit) : RecyclerView.ViewHolder(appointmentView) {

        private val doctor: TextView = itemView.findViewById(R.id.doctor)
        private val doctorType: TextView = itemView.findViewById(R.id.doctor_type)
        private val therapyText: TextView = itemView.findViewById(R.id.therapy_card_text)
        private val statusText: TextView = itemView.findViewById(R.id.status_card_text)
        private val statusCard: CardView = itemView.findViewById(R.id.status_card)
        private val therapyCard: CardView = itemView.findViewById(R.id.therapy_card)
        private val appointmentItemCard: MaterialCardView = itemView.findViewById(R.id.appointment_item_card)
        private val appointmentDate: TextView = itemView.findViewById(R.id.appointment_date)
        private val dateOfCreation: TextView = itemView.findViewById(R.id.creation_date)
        private var appointment: AppointmentItemViewData? = null

        init {
            appointmentView.setOnClickListener {
                appointment?.let {
                    onClick(it)
                }
            }
        }

        fun bind(appointment: AppointmentItemViewData) {
            this.appointment = appointment
            doctor.text = appointment.doctor
            doctorType.text = appointment.doctorType
            therapyText.text = appointment.therapyType.visitType
            therapyCard.backgroundTintList = ColorStateList.valueOf(Color.parseColor(appointment.therapyType.color))
            statusCard.backgroundTintList = ColorStateList.valueOf(Color.parseColor(appointment.statusType.color))
            appointmentItemCard.strokeColor = appointment.therapyType.color.toColorInt()
            statusText.text = appointment.statusType.status
            appointmentDate.text = appointment.dateOfAppointment
            dateOfCreation.text = appointment.dateOfCreation
        }
    }
}


private object AppointmentDiffCallback : DiffUtil.ItemCallback<AppointmentItemViewData>() {
    override fun areItemsTheSame(oldItem: AppointmentItemViewData, newItem: AppointmentItemViewData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: AppointmentItemViewData, newItem: AppointmentItemViewData): Boolean {
        return oldItem.id == newItem.id
    }
}