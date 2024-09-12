package ru.pugovishnikova.example.testdiapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.pugovishnikova.example.testdiapp.adapters.PostAdapter
import ru.pugovishnikova.example.testdiapp.data.dto.UserData
import ru.pugovishnikova.example.testdiapp.databinding.FragmentUniqueUserBinding
import ru.pugovishnikova.example.testdiapp.utils.IdClass
import ru.pugovishnikova.example.testdiapp.utils.State
import ru.pugovishnikova.example.testdiapp.viewmodels.UniqueUserViewModel

@AndroidEntryPoint
class UniqueUserFragment : Fragment() {

    private var _binding: FragmentUniqueUserBinding? = null
    private val binding get() = _binding!!

    private val uniqueUserViewModel by lazy {
        val viewModel: UniqueUserViewModel by viewModels()
        val id = UniqueUserFragmentArgs.fromBundle(requireArguments()).id
        viewModel.idClass = IdClass(id)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uniqueUserViewModel.requireStateUserInfo().onEach(::handleStateUserInfo)
            .launchIn(viewLifecycleOwner.lifecycleScope)
        if (savedInstanceState == null) {
            uniqueUserViewModel.getInfoAboutUser()
        }
    }


    private fun handleStateUserInfo(state: State<UserData>) {
        when (state) {
            is State.Idle -> Unit
            is State.Loading -> {
                binding.progressbar.isVisible = true
                binding.whereText.isVisible = false
                binding.userId.isVisible = false
                binding.userName.isVisible = false
                binding.userSurname.isVisible = false
                binding.rv.isVisible = false
            }

            is State.Success -> {
                binding.progressbar.isVisible = false
                binding.whereText.isVisible = true
                binding.userId.isVisible = true
                binding.userName.isVisible = true
                binding.userSurname.isVisible = true
                binding.rv.isVisible = true
                binding.whereText.text = "я все получил"
                binding.userId.text = state.data.user.id.toString()
                binding.userName.text = state.data.user.firstName
                binding.userSurname.text = state.data.user.lastName
                val adapter = PostAdapter(state.data.posts)
                val rv = binding.rv
                rv.adapter = adapter
                rv.layoutManager = LinearLayoutManager(requireContext())
            }

            is State.Fail -> {
                binding.progressbar.isVisible = false
                binding.whereText.isVisible = true
                binding.whereText.text = "ИНТЕРНЕТА НЕТ((( ДАННЫЕ С БД"
            }

        }

    }
}