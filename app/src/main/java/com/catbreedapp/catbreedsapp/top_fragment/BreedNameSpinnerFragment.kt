package com.catbreedapp.catbreedsapp.top_fragment

import android.R
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.catbreedapp.catbreedsapp.SharedViewModel
import com.catbreedapp.catbreedsapp.databinding.FragmentBreedNameSpinnerBinding
import org.json.JSONObject


class BreedNameSpinnerFragment : Fragment() {
    private lateinit var viewBinding:FragmentBreedNameSpinnerBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var mContext: Context
    lateinit var spinner: Spinner
    lateinit var progressbar: ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentBreedNameSpinnerBinding.inflate(inflater, container, false)
        viewBinding.viewModel = viewModel
        mContext = requireActivity()

        spinner = viewBinding.catNameSpinner
        progressbar = viewBinding.progressBar
        progressbar.visibility = View.VISIBLE
        val catNameList: ArrayList<String> = ArrayList()
        val url = "https://api.thecatapi.com/v1/breeds"

        val queue = Volley.newRequestQueue(requireContext())
        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                Log.d("TAG", "onCreate: successfull")
                Toast.makeText(requireContext(), "Cats Breed fetched Successfully", Toast.LENGTH_SHORT)
                    .show()
                progressbar.visibility = View.GONE

                for (i in 0 until response.length()) {
                    val breed: JSONObject = response.getJSONObject(i)

                    val firstName: String = breed.getString("name")
//                    val lastName: String = breed.getString("temperament")
//                    val age: String = breed.getString("origin")
                    Log.e("TAG", "Name of cats: $firstName")
//                    if(breed.has("image")){
//                        val breedImges: JSONObject = breed.getJSONObject("image")
//                        for(i in 0 until  breedImges.length()){
//                            var imagesBreed:String=breedImges.getString("id")
//                            Log.e("TAG", "Images Id"+imagesBreed )
//                        }
//                    }

                    catNameList.add(firstName)

                    val dataAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                        requireContext(),
                        R.layout.simple_spinner_item, catNameList
                    )
                    dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                    spinner.setAdapter(dataAdapter)
                }
                Log.d("TAG", "firstName: ${catNameList.size}")


            },
            Response.ErrorListener { error ->
                Log.d("error", " ")
                progressbar.visibility = View.GONE
                Toast.makeText(requireContext(), "Internal Server Error", Toast.LENGTH_SHORT)
                    .show()
            }
        )

        queue.add(jsonObjectRequest)




        Log.d("TAG", "onCreate: ${catNameList.size}")










        return viewBinding.root
    }


}