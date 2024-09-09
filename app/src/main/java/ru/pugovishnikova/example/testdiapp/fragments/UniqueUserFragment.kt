package ru.pugovishnikova.example.testdiapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.pugovishnikova.example.testdiapp.databinding.FragmentUniqueUserBinding
import ru.pugovishnikova.example.testdiapp.utils.IdClass
import ru.pugovishnikova.example.testdiapp.viewmodels.UniqueUserViewModel

@AndroidEntryPoint
class UniqueUserFragment : Fragment() {

    private var _binding: FragmentUniqueUserBinding? = null
    private val binding get() = _binding!!

    private val uniqueUserViewModel by lazy {
        val viewModel: UniqueUserViewModel by viewModels()
        val id = UniqueUserFragmentArgs.fromBundle(requireArguments()).id
        viewModel.id = IdClass(id)
        viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUniqueUserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }




}