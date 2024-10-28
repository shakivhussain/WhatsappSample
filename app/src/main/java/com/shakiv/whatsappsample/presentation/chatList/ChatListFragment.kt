package com.shakiv.whatsappsample.presentation.chatList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.shakiv.whatsappsample.R
import com.shakiv.whatsappsample.data.model.User
import com.shakiv.whatsappsample.databinding.FragmentUserListBinding
import com.shakiv.whatsappsample.presentation.adapterLoader.MainLoadStateAdapter
import com.shakiv.whatsappsample.presentation.base.BaseFragment
import com.shakiv.whatsappsample.utils.MockData
import com.shakiv.whatsappsample.utils.hideKeyboard
import com.shakiv.whatsappsample.utils.openCamera
import com.shakiv.whatsappsample.utils.showKeyboard
import com.shakiv.whatsappsample.utils.textChanges
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ChatListFragment : BaseFragment() {

    private val userListViewModel: UserListViewModel by viewModels()

    private lateinit var binding: FragmentUserListBinding
    private lateinit var usersAdapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserListBinding.inflate(inflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usersAdapter = UsersAdapter(::onUserClicked, this)
        insertMockData()
        getAllUsers()
    }

    private fun insertMockData() {

        lifecycleScope.launch {
            val user = userListViewModel.getUserById("1")
            if (user == null ) {
                userListViewModel.addUser(
                    MockData.getMockUsers()
                )

                getAllUsers()
                usersAdapter.refresh()
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindUI()
        bindListener()
        bindObserver()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override fun bindListener() {
        super.bindListener()


        binding.swipeToReferesh.setOnRefreshListener {
            binding.swipeToReferesh.isRefreshing = false
            usersAdapter.refresh()
        }

        binding.mainHeader.icSearch.setOnClickListener {
            binding.mainHeader.searchContainer.apply {
                root.isVisible = true
                etSearch.showKeyboard()
            }
        }

        binding.mainHeader.searchContainer.ivClear.setOnClickListener {
            binding.mainHeader.searchContainer.apply {
                root.isVisible = false
                etSearch.hideKeyboard()
                etSearch.setText("")
                getAllUsers()
            }
        }



        binding.mainHeader.searchContainer.etSearch.textChanges()
            .filterNot { it.isNullOrBlank() }
            .debounce(500)
            .onEach { searchUser(it) }
            .launchIn(lifecycleScope)


        binding.mainHeader.icCamera.setOnClickListener {
            openCamera()
        }

    }

    private fun searchUser(it: CharSequence?) {
        userListViewModel.getAllUserData(it.toString())
    }


    override fun bindObserver() {
        super.bindObserver()

        lifecycleScope.launch {
            userListViewModel.usersStateFlow.collectLatest {
                usersAdapter.submitData(it)
            }
        }

        usersAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Loading -> {
                }

                is LoadState.NotLoading -> {
                    val isEmpty = usersAdapter.snapshot().items.isEmpty()
                    binding.tvEmpty.isVisible = isEmpty
                }

                is LoadState.Error -> {
                }
            }
        }
    }


    override fun bindUI() {
        super.bindUI()

        binding.apply {
            usersRV.adapter = usersAdapter.withLoadStateFooter(MainLoadStateAdapter())
        }
    }

    private fun onUserClicked(user: User) {
        val bundle = Bundle().also {
            it.putSerializable(KEY_USER, user)
        }
        findNavController().navigate(R.id.action_chatListFragment_to_chatFragment, bundle)
    }

    private fun getAllUsers() {
        userListViewModel.getAllUserData("")
    }

    companion object {
        const val KEY_USER = "key_user"
    }
}
