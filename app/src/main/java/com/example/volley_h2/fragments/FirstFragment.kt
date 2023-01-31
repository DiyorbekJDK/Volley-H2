package com.example.volley_h2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.Volley
import com.example.volley_h2.R
import com.example.volley_h2.adapter.ListUserAdapter
import com.example.volley_h2.databinding.FragmentFirstBinding
import com.example.volley_h2.model.Data
import com.example.volley_h2.network.ResponseHandler
import com.example.volley_h2.network.VolleyInstance
import com.example.volley_h2.util.NetworkUtils
import com.example.volley_h2.util.snackBar
import com.google.gson.Gson


class FirstFragment : Fragment(R.layout.fragment_first) {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val volleyInstance by lazy { VolleyInstance() }
    private val dataAdapter by lazy { ListUserAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allCode()

    }

    private fun allCode() {
        binding.rv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = dataAdapter
        }
        volleyInstance.get(object : ResponseHandler {
            override fun onResponse(string: String) {
                val dataList = Gson().fromJson(string, Array<Data>::class.java)
                dataAdapter.submitList(dataList.toMutableList())
                binding.progressBar.isVisible = false
            }

            override fun onError(string: String?) {
                snackBar(string!!)
            }
        })
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        dataAdapter.onClick = {
            val bundle = bundleOf("id" to it)
            findNavController().navigate(R.id.action_FirstFragment_to_thirdFragment, bundle)
        }
        dataAdapter.onLongClick = {
            volleyInstance.delete(it, object : ResponseHandler {
                override fun onResponse(string: String) {
                    snackBar("Deleted")
                    dataAdapter.notifyDataSetChanged()
                }

                override fun onError(string: String?) {
                    snackBar(string!!)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}