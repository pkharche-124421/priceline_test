package com.pricelinetest.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pricelinetest.data.DataRepositoryImpl
import com.pricelinetest.databinding.FragmentHomeBinding
import com.pricelinetest.features.ActivityMain
import com.pricelinetest.features.home.recyclerview.NameAdapter
import com.pricelinetest.models.Name

class FragmentHome : Fragment(), NameAdapter.OnItemClickListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    companion object {
        fun newInstance(): FragmentHome {
            return FragmentHome()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            val viewModelProviderFactory = HomeViewModelProviderFactory(
                it.application,
                DataRepositoryImpl()
            )
            viewModel = ViewModelProvider(
                this, viewModelProviderFactory
            ).get(HomeViewModel::class.java)
            binding.viewModel = viewModel
        }

        initObserveData()
    }

    private fun initObserveData() {
        viewModel.nameList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                loadList(it)
            }
        })

        viewModel.errorMsg.observe(viewLifecycleOwner, Observer {
            (activity as ActivityMain).showErrorSnackBar(it)
        })

        viewModel.isNetworkError.observe(viewLifecycleOwner, Observer {
            if (it) {
                (activity as ActivityMain).showNetworkErrorSnackBar()
            }
        })
    }

    private fun loadList(list: List<Name>) {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        val nameAdapter = NameAdapter(list, this)
        binding.recyclerView.adapter = nameAdapter
    }

    override fun onItemClick(nameItem: Name?) {
        (activity as ActivityMain).showBooks(nameItem)
    }

}