package mahmoud.nousir.madarsofttask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mahmoud.nousir.madarsofttask.*
import mahmoud.nousir.madarsofttask.databinding.FragmentGetDataBinding
import mahmoud.nousir.madarsofttask.db.UserDataBase
import mahmoud.nousir.madarsofttask.model.Gender


class GetDataFragment : Fragment() {

    private lateinit var binding : FragmentGetDataBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_get_data,container,false)
        binding.userNameLayout.editText?.afterTextChanged {userName->
            viewModel.setUserName(userName)
        }

        binding.jobTitleLayout.editText?.afterTextChanged {jobTitle->
            viewModel.setJobTitle(jobTitle)
        }

        binding.ageLayout.editText?.afterTextChanged {age->
            viewModel.setAge(age)
        }
        val dataSource = UserDataBase.getInstance(requireContext()).dao
        viewModel = ViewModelProvider(this,
            UserViewModelFactory(dataSource)
        ).get(UserViewModel::class.java)

        initData()
        binding.enterData.setOnClickListener {
            viewModel.saveData()
        }

        viewModel.isVerified.observe(viewLifecycleOwner, Observer { isVerified->
            binding.enterData.isEnabled = isVerified
        })

        viewModel.isSaved.observe(viewLifecycleOwner, Observer {
                if(it) {
                    findNavController().navigate(R.id.action_getDataFragment_to_showDataFragment)
                    viewModel.navigationIsDone()
                }

        })
        binding.genderGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.male-> viewModel.setGender(
                    Gender.MALE
                )
                R.id.female-> viewModel.setGender(
                    Gender.FEMALE
                )
            }
        }


        binding.userNameLayout.editText?.requestFocus()


        return binding.root
    }

    private fun initData() {
        binding.userNameLayout.editText?.setText(viewModel.getUser().userName)
        binding.ageLayout.editText?.setText(viewModel.getUser().age)
        binding.jobTitleLayout.editText?.setText(viewModel.getUser().jobTile)
        when(viewModel.getUser().gender){
           Gender.FEMALE-> binding.genderGroup.check(R.id.female)
           Gender.MALE-> binding.genderGroup.check(R.id.male)
        }
    }



}

