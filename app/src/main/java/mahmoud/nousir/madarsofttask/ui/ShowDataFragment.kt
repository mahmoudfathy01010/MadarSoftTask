package mahmoud.nousir.madarsofttask.ui

import android.os.Bundle
import android.text.Spanned
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import mahmoud.nousir.madarsofttask.R
import mahmoud.nousir.madarsofttask.UserViewModel
import mahmoud.nousir.madarsofttask.UserViewModelFactory
import mahmoud.nousir.madarsofttask.databinding.FragmentShowDataBinding
import mahmoud.nousir.madarsofttask.db.UserDataBase
import java.util.*

class ShowDataFragment : Fragment() {
    private lateinit var binding:FragmentShowDataBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataSource = UserDataBase.getInstance(requireContext()).dao
        viewModel = ViewModelProvider(this,
            UserViewModelFactory(dataSource)
        ).get(UserViewModel::class.java)
        viewModel.getUserFromDataBase()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_show_data,container,false)
        viewModel.userLiveData.observe(viewLifecycleOwner, Observer { user->
            binding.userName.text = changeTextFormat(user.userName,
                R.string.user_name_show
            )
            binding.age.text = changeTextFormat(user.age,
                R.string.age_show
            )
            binding.jobTitle.text = changeTextFormat(user.jobTile,
                R.string.job_title_show
            )
            binding.gender.text = changeTextFormat(user.gender?.name?.toLowerCase(Locale.ROOT),
                R.string.gender_show
            )
            binding.loading.visibility=View.GONE

        })
        return binding.root
    }

    private fun changeTextFormat(text:String?, stringID: Int): Spanned {
        return HtmlCompat.fromHtml(String.format(getString(stringID),text), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

}