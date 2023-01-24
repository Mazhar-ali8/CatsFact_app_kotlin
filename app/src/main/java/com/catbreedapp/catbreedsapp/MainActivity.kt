package com.catbreedapp.catbreedsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.catbreedapp.catbreedsapp.bottom_fragment.CatInformationFragment
import com.catbreedapp.catbreedsapp.top_fragment.BreedNameSpinnerFragment

class MainActivity : AppCompatActivity() {
    var displayFragment: Fragment?=null
    var calculaterFragment: Fragment?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        displayFragment= BreedNameSpinnerFragment()
        calculaterFragment= CatInformationFragment()
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container_a, displayFragment as BreedNameSpinnerFragment)
        transaction.add(R.id.container_b, calculaterFragment as CatInformationFragment)
        transaction.commit()

    }
}