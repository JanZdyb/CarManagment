package com.example.carmanagment

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.get
import kotlinx.android.synthetic.main.fragment_add_car.*
import kotlinx.android.synthetic.main.fragment_add_car.view.*

class AddCarFragment : Fragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_add_car, container, false)

        view.addNewCarButton.setOnClickListener(View.OnClickListener {
            if(isValid()) {
                Toast.makeText(activity?.applicationContext, "dodano", Toast.LENGTH_LONG).show()
            }
        })
        return view
    }

    private fun isValid(): Boolean {
        var valid = false
        var carname = editTextCarName.text.toString()
        var carmodel = editTextModelName.text.toString()
        var carengine = editTextEngine.text.toString()
        var carproduction = editTextYearOfProduction.text.toString()
        var horsepower = editTextHorsePower.text.toString()
        var carmileage = editTextMileage.text.toString()
        var VIN = editTextVIN.text.toString()

        var radioGroup = fuelGroup
        var radioButton = radioButton9
        var radioButtonID = radioGroup.checkedRadioButtonId

        if(radioButtonID != -1){
            radioButton = view?.findViewById<RadioButton>(radioButtonID)
            val text: String = radioButton.text.toString()
        }

        if(TextUtils.isEmpty(carname) || TextUtils.isEmpty(carmodel) ||
                TextUtils.isEmpty(carengine) || TextUtils.isEmpty(carproduction) ||
                TextUtils.isEmpty(horsepower) || TextUtils.isEmpty(carmileage)){
            Toast.makeText(activity?.applicationContext, "Coś poszło nie tak", Toast.LENGTH_SHORT).show()
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
            else{
                valid = true
            }
        }
        return valid
    }
}