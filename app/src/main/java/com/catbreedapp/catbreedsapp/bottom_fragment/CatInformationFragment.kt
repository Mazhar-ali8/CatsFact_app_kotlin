package com.catbreedapp.catbreedsapp.bottom_fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.catbreedapp.catbreedsapp.R
import com.catbreedapp.catbreedsapp.SharedViewModel
import com.catbreedapp.catbreedsapp.databinding.FragmentCatInformationBinding
import com.catbreedapp.catbreedsapp.utils.BUTTON_ONE_CLICKED
import org.json.JSONArray
import org.json.JSONObject

class CatInformationFragment : Fragment() {
    private lateinit var viewBinding:FragmentCatInformationBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var mContext: Context
    private lateinit var catImage: ImageView
    private lateinit var catName: TextView
    private lateinit var catTemprament: TextView
    private lateinit var catOrigin: TextView
    lateinit var progressbar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentCatInformationBinding.inflate(inflater, container, false)
        viewBinding.viewModel = viewModel
        mContext = requireActivity()
        catImage = viewBinding.catImage
        catName = viewBinding.catNameTv
        catTemprament = viewBinding.catTemprament
        catOrigin = viewBinding.catOrigin
        progressbar = viewBinding.progressBar
        progressbar.visibility = View.VISIBLE
        bindObservers()
        return viewBinding.root
    }

    private fun bindObservers() {
        viewModel.clickEvents.observe(viewLifecycleOwner, Observer { clickEvent ->
            when (clickEvent) {
                BUTTON_ONE_CLICKED -> {
                    val catNameViewModel = viewModel.selectedCatName.get().toString()
                    catName.setText(catNameViewModel)

                    try {
                        if (!viewModel.selectedCatName.get().toString().isNullOrEmpty()) {
                            val breedId = viewModel.selectedCatName.get().toString().substring(0, 4)
                            Log.e("TAG", "breed_id: $breedId")
                            val url =
                                "https://api.thecatapi.com/v1/images/search?breed_id=" + breedId
                            val queue = Volley.newRequestQueue(requireContext())
                            val jsonObjectRequest = JsonArrayRequest(
                                Request.Method.GET, url, null,
                                Response.Listener { response ->
                                    progressbar.visibility = View.GONE
                                    var imageURL: String? = null
                                    var origin: String? = null
                                    var temprament: String? = null
                                    if (response != null) {
                                        Toast.makeText(
                                            requireContext(),
                                            "Breed Information fetched Successfully",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                        for (i in 0 until response.length()) {
                                            val breed: JSONObject = response.getJSONObject(i)
                                            val imageId: String = breed.getString("id")
                                            imageURL = breed.getString("url")
                                            val breeds: JSONArray = breed.getJSONArray("breeds")

                                            for (i in 0 until breeds.length()) {
                                                val breedinformation: JSONObject = breeds.getJSONObject(i)
                                                origin = breedinformation.getString("origin")
                                                temprament =breedinformation.getString("temperament")
                                            }
                                        }
                                        if (imageURL !== null) {

                                            Glide.with(this)
                                                .load(imageURL)
                                                .into(catImage)
                                        } else {
                                            catImage.setImageResource(R.drawable.no_data_found)
                                        }

                                        if (origin != null) {
                                            catOrigin.setText(origin)

                                        } else {
                                            catOrigin.setText("No data found")

                                        }
                                        if (temprament != null) {
                                            catTemprament.setText(temprament)

                                        } else {
                                            catTemprament.setText("no data found")

                                        }

                                    } else {
                                        Toast.makeText(
                                            requireContext(),
                                            "This cats Does not have information",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }

                                    Log.e("TAG", "response:$response")
                                },
                                Response.ErrorListener { error ->
                                    progressbar.visibility = View.GONE
                                    Toast.makeText(
                                        requireContext(),
                                        "Internal Server Error",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            )
                            queue.add(jsonObjectRequest)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Internal Server error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }


            }
        })
    }

}