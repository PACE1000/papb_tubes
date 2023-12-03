package com.example.binartujuh

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.binartujuh.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding:FragmentProfileBinding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString("username")

        lifecycleScope.launch {
            val datastore = DataStoreManager(requireContext())
            val alamatstore = datastore.getAlamat(username)
            val tanggalstore = datastore.getTanggal(username)
            val namastore = datastore.getNama(username)

            val usernameEditable: Editable = Editable.Factory.getInstance().newEditable(username ?: "")
            val namaEditable: Editable = Editable.Factory.getInstance().newEditable(namastore ?: "")
            val tanggalEditable: Editable = Editable.Factory.getInstance().newEditable(tanggalstore ?: "")
            val alamatEditable: Editable = Editable.Factory.getInstance().newEditable(alamatstore ?: "")


            binding.etUsername.text = usernameEditable
            binding.etNamalengkap.text = namaEditable
            binding.etTanggallahir.text = tanggalEditable
            binding.etAlamat.text = alamatEditable
        }

        binding.btnLogout.setOnClickListener{
            it.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }

        binding.btnUpdate.setOnClickListener{
            lifecycleScope.launch {
                val datastore = DataStoreManager(requireContext())
                val username = binding.etNamalengkap.text.toString()
                val tanggal = binding.etTanggallahir.text.toString()
                val alamat = binding.etAlamat.text.toString()
                datastore.updateUser(username,tanggal, alamat = alamat)
                Toast.makeText(requireContext(),"Data telah Diperbaharui", Toast.LENGTH_SHORT).show()
            }
        }
    }
}