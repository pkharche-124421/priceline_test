package com.pricelinetest.features.books.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pricelinetest.databinding.LayoutBooksRecyclerviewItemBinding
import com.pricelinetest.models.BookDetails


class BooksAdapter(private var booksList: List<BookDetails>)
    :RecyclerView.Adapter<BooksAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksAdapter.ItemViewHolder {
        val binding = LayoutBooksRecyclerviewItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount() = booksList.size

    fun setList(list: List<BookDetails>) {
        booksList = list
    }

    override fun onBindViewHolder(holder: BooksAdapter.ItemViewHolder, position: Int) {
        with(holder) {
            with(booksList[position]) {
                val viewModel = BooksItemViewModel()
                viewModel.bookDetailsItem = booksList[position]
                holder.bind(viewModel)
            }
        }
    }

    inner class ItemViewHolder(var binding: LayoutBooksRecyclerviewItemBinding)
        :RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: BooksItemViewModel) {
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }
}
