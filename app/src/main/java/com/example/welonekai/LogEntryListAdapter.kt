package com.example.welonekai

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LogEntryListAdapter : ListAdapter<LogEntry, LogEntryListAdapter.LogEntryViewHolder>(LogEntriesComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogEntryViewHolder {
        return LogEntryViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: LogEntryViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class LogEntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val timestampView: TextView = itemView.findViewById(R.id.log_timestamp)
        private val phView: TextView = itemView.findViewById(R.id.log_ph)
        private val waterChangedView: TextView = itemView.findViewById(R.id.log_water_changed)
        private val notesView: TextView = itemView.findViewById(R.id.log_notes)

        fun bind(logEntry: LogEntry) {
            timestampView.text = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(logEntry.timestamp))
            phView.text = "pH: ${logEntry.ph}"
            waterChangedView.text = "Water Changed: ${if (logEntry.waterChanged) "Yes" else "No"}"
            notesView.text = logEntry.notes
        }

        companion object {
            fun create(parent: ViewGroup): LogEntryViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_log_entry, parent, false)
                return LogEntryViewHolder(view)
            }
        }
    }

    class LogEntriesComparator : DiffUtil.ItemCallback<LogEntry>() {
        override fun areItemsTheSame(oldItem: LogEntry, newItem: LogEntry): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: LogEntry, newItem: LogEntry): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
