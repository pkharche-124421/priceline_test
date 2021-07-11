package com.pricelinetest.features.home.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pricelinetest.databinding.LayoutHomeRecyclerviewHeaderBinding
import com.pricelinetest.databinding.LayoutHomeRecyclerviewItemBinding
import com.pricelinetest.models.Name


class NameAdapter(private var nameList: List<Name>, private val listener: OnItemClickListener?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_ITEM = 1

    interface OnItemClickListener {
        fun onItemClick(nameItem: Name?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType === ITEM_VIEW_TYPE_HEADER) {
            val binding = LayoutHomeRecyclerviewHeaderBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return HeaderViewHolder(binding)
        }
        val binding = LayoutHomeRecyclerviewItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount() = nameList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == ITEM_VIEW_TYPE_HEADER) {
            with(holder) {
                with(nameList[position]) {
                    (holder as HeaderViewHolder).binding.tvHeader.text = displayName
                }
            }

        } else {
            with(holder) {
                with(nameList[position]) {
                    val viewModel = NameItemViewModel()
                    viewModel.nameItem = nameList[position]
                    (holder as ItemViewHolder).bind(viewModel)

                    holder.itemView.setOnClickListener {
                        listener?.onItemClick(this)
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val nameItem = nameList[position]
        if (nameItem.isHeader) {
            return ITEM_VIEW_TYPE_HEADER
        } else {
            return ITEM_VIEW_TYPE_ITEM
        }
    }

    inner class ItemViewHolder(var binding: LayoutHomeRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: NameItemViewModel) {
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }

    inner class HeaderViewHolder(var binding: LayoutHomeRecyclerviewHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)

}
