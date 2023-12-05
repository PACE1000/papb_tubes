package com.example.papb_tubes

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.papb_tubes.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment(), WeatherAdapter.OnClickListener {
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        LocalBroadcastManager.getInstance(requireActivity())
            .registerReceiver(searchBroadcastReceiver, IntentFilter("SEARCH_ACTION"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivPerson.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

        binding.rvHomepage.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = WeatherAdapter(this@HomeFragment)
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                fetchAllData(city = s.toString())
                sendSearchBroadcast(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun fetchAllData(city: String) {
        ApiConfig.getApiService(city).getAllProvinsi(city)
            .enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    val body = response.body()
                    val code = response.code()
                    if (code == 200) {
                        showList(body)
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Log.d("MainActivity", "onFailure: ${t.message}")
                }
            })
    }

    private fun showList(data: WeatherResponse?) {
        val adapter = WeatherAdapter(this)
        if (data != null) {
            adapter.submitData(listOf(data))
        }
        binding.rvHomepage.adapter = adapter
    }

    private fun sendSearchBroadcast(city: String) {
        val intent = Intent("SEARCH_ACTION")
        intent.putExtra("CITY", city)
        LocalBroadcastManager.getInstance(requireActivity()).sendBroadcast(intent)
    }

    private val searchBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "SEARCH_ACTION") {
                val searchedCity = intent.getStringExtra("CITY")
                Log.d("Notification", "Searched City: $searchedCity")
                showNotification(context, searchedCity)
            }
        }
    }


    private fun showNotification(context: Context?, searchedCity: String?) {
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "search_channel",
                "Search Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, "search_channel")
            .setSmallIcon(R.drawable.ic_favorite)
            .setContentTitle("Search Notification")
            .setContentText("Searched for: $searchedCity")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        notificationManager.notify(1, builder.build())
    }

    override fun onClickItem(data: WeatherResponse) {
        // Handle item click as needed
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        LocalBroadcastManager.getInstance(requireActivity())
            .unregisterReceiver(searchBroadcastReceiver)
    }
}
