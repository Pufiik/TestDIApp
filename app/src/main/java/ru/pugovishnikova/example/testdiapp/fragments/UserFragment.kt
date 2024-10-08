package ru.pugovishnikova.example.testdiapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.pugovishnikova.example.testdiapp.adapters.UserAdapter
import ru.pugovishnikova.example.testdiapp.data.model.User
import ru.pugovishnikova.example.testdiapp.databinding.FragmentUserBinding
import ru.pugovishnikova.example.testdiapp.utils.State
import ru.pugovishnikova.example.testdiapp.viewmodels.UserViewModel

@AndroidEntryPoint
class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private val userViewModel by viewModels<UserViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.requireStateUsers().onEach(::handleStateUsers)
            .launchIn(viewLifecycleOwner.lifecycleScope)
        if (savedInstanceState == null) {
            userViewModel.getAllUsersFromServer()
        }



        binding.button.setOnClickListener {
            val id: String = binding.editTextId.text.toString()
            if (isPositiveNumber(id)) {
                    val action = UserFragmentDirections.actionUserFragmentToUniqueUserFragment(id.toInt())
                    view.findNavController().navigate(action)
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun handleStateUsers(state: State<List<User>>) {
        when (state) {
            is State.Idle -> Unit
            is State.Loading -> {
                binding.rv.isVisible = false
                binding.button.isVisible = false
                binding.editTextId.isVisible = false
                binding.progressbar.isVisible = true
                binding.textView.isVisible = false
            }

            is State.Success -> {
                binding.button.isVisible = true
                binding.textView.isVisible = true
                binding.textView.text = "ИНТЕРНЕТ ЕСТЬ! ДАННЫЕ С СЕРВЕРА"
                binding.editTextId.isVisible = true
                binding.progressbar.isVisible = false
                binding.rv.isVisible = true
                val adapter = UserAdapter(state.data)
                val rv = binding.rv
                rv.adapter = adapter
                rv.layoutManager = LinearLayoutManager(requireContext())
            }

            is State.Fail -> {
                binding.progressbar.isVisible = false
                binding.textView.isVisible = true
                binding.textView.text = "ИНТЕРНЕТА НЕТ((( ДАННЫЕ С БД"
            }

        }
    }

    private fun isPositiveNumber(value: String): Boolean {
        val pattern = "^(0|[1-9][0-9]*)(\\.[0-9]+)?$"
        return value.matches(Regex(pattern))
    }

}