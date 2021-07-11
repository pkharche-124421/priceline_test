package com.pricelinetest.features.books

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pricelinetest.data.DataRepositoryImpl
import com.pricelinetest.databinding.FragmentBooksBinding
import com.pricelinetest.features.ActivityMain
import com.pricelinetest.features.books.recyclerview.BooksAdapter
import com.pricelinetest.models.BookDetails
import com.pricelinetest.models.Name

const val NAME_SELECTED = "name_selected"

class FragmentBooks : Fragment() {
    private lateinit var binding: FragmentBooksBinding
    private lateinit var viewModel: BooksViewModel

    private var nameItem: Name? = null

    companion object {
        @JvmStatic
        fun newInstance(name: Name?): FragmentBooks {

            val fragment = FragmentBooks()
            fragment.arguments = Bundle().apply {
                putParcelable(NAME_SELECTED, name)
            }
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getParcelable<Name?>(NAME_SELECTED)?.let {
            nameItem = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBooksBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            val viewModelProviderFactory = BooksViewModelProviderFactory(
                it.application,
                DataRepositoryImpl()
            )
            viewModel = ViewModelProvider(
                this, viewModelProviderFactory
            ).get(BooksViewModel::class.java)
            binding.viewModel = viewModel

            if(savedInstanceState == null) {
                viewModel.setName(nameItem)
            }

            initObserveData()
        }
    }

    private fun initObserveData() {
        viewModel.booksList.observe(viewLifecycleOwner, {
            if (it != null) {
                loadList(it)
            }
        })

        viewModel.errorMsg.observe(viewLifecycleOwner, {
            (activity as ActivityMain).showErrorSnackBar(it)
        })

        viewModel.isNetworkError.observe(viewLifecycleOwner, {
            if (it) {
                (activity as ActivityMain).showNetworkErrorSnackBar()
            }
        })
    }

    private fun loadList(list: List<BookDetails>) {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        val booksAdapter = BooksAdapter(list)
        binding.recyclerView.adapter = booksAdapter
    }
}