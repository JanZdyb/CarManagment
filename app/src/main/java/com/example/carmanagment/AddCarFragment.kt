package com.example.carmanagment

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_add_car.*

class AddCarFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_add_car, container, false)

        return view
    }

    private fun isValid(): Boolean {
        var valid = false
        var carname = spinnerCarName.selectedItem.toString()
        var carmodel = spinnerCarModel.selectedItem.toString()
        var carengine = editTextEngine.text.toString()
        var carproduction = editTextYearOfProduction.text.toString()
        var horsepower = editTextHorsePower.text.toString()
        var carmileage = editTextMileage.text.toString()
        var VIN = editTextVIN.text.toString()


        if(TextUtils.isEmpty(carname) || TextUtils.isEmpty(carmodel) ||
                TextUtils.isEmpty(carengine) || TextUtils.isEmpty(carproduction) ||
                TextUtils.isEmpty(horsepower) || TextUtils.isEmpty(carmileage)){
            Toast.makeText(activity?.applicationContext, "Coś poszło nie tak", Toast.LENGTH_LONG).show()
            valid = false
        }
        else{
            if(!TextUtils.isEmpty(VIN)){
                if(VIN.length == 17){
                    editTextVIN.setError(null)
                    valid = true
                }
                else{
                    editTextVIN.setError("Numer VIN powinien zawierać 17 znaków")
                }
            }
        }
        return valid
    }
}