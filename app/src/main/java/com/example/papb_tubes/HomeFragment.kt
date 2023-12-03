package com.example.papb_tubes

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.papb_tubes.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var apiService: ApiService
    private lateinit var weatherAdapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiService = ApiConfig.getApiService("London") // Set initial city

        weatherAdapter = WeatherAdapter(object : WeatherAdapter.OnClickListener {
            override fun onClickItem(data: WeatherResponse) {
                // Handle item click if needed
            }
        })

        binding.rvHomepage.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = weatherAdapter
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Trigger search when text changes
                searchWeather(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.ivPerson.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

        // Fetch initial weather data for London
        val londonWeather = fetchAllData()
        if (londonWeather != null) {
            showlist(londonWeather)
        }
    }

    private fun searchWeather(city: String) {
        apiService = ApiConfig.getApiService(city)

        apiService.getAllProvinsi(city).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    if (weatherResponse != null) {
                        Log.d("WeatherResponse", weatherResponse.toString())
                        showlist(weatherResponse)
                    } else {
                        Log.e("WeatherResponse", "Response body is null")
                    }
                } else {
                    Log.e("WeatherResponse", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun showlist(data: WeatherResponse?) {
        val weatherList = mutableListOf<WeatherResponse>()
        if (data != null) {
            weatherList.add(data)
            weatherAdapter.submitData(weatherList)
        }
    }

    private fun fetchAllData(): WeatherResponse? {
        var londonWeather: WeatherResponse? = null
        ApiConfig.getApiService("London").getAllProvinsi("London").enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                val body = response.body()
                val code = response.code()
                if (code == 200) {
                    londonWeather = body
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.d("MainActivity", "onFailure: ${t.message}")
            }
        })
        return londonWeather
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
