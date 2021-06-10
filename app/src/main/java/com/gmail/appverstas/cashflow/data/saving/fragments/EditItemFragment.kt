package com.gmail.appverstas.cashflow.data.saving.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gmail.appverstas.cashflow.R
import com.gmail.appverstas.cashflow.data.SharedMethods
import com.gmail.appverstas.cashflow.data.saving.SavingViewModel
import com.gmail.appverstas.cashflow.data.saving.models.SavingItem
import kotlinx.android.synthetic.main.fragment_saving_and_investing_edit.*
import kotlinx.android.synthetic.main.fragment_saving_and_investing_edit.view.*


class EditItemFragment : Fragment() {

    val args by navArgs<EditItemFragmentArgs>()
    val savingsViewModel: SavingViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_saving_and_investing_edit, container, false)
        view.et_edit_saving_investing_title.setText(args.currentSavingItem.title)
        view.et_edit_saving_investing_amount.setText(args.currentSavingItem.amount.toString())
        if(args.currentSavingItem.type.equals("Saving")){
            view.radio_btn_edit_saving.isChecked = true
        }else{
            view.radio_btn_edit_investing.isChecked = true
        }
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_fragments, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.app_bar_save -> saveToDb()
            R.id.app_bar_delete -> deleteItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteItem() {
        savingsViewModel.deleteSaving(args.currentSavingItem)
        Toast.makeText(requireContext(), "Deleted!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_editItemFragment_to_savingAndInvestingFragment)
    }

    private fun saveToDb() {
        var title = et_edit_saving_investing_title.text.toString()
        var amount = et_edit_saving_investing_amount.text.toString().toDouble()
        var validation = SharedMethods.verifyDataFormat(title, amount)
        if(validation){
            var updatedItem = SavingItem(
                    args.currentSavingItem.id,
                    title,
                    amount,
                    SavingItem.getSavingType(radio_btn_edit_saving.isChecked)
            )
            savingsViewModel.updateSaving(updatedItem)
            Toast.makeText(requireContext(), "Updated!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_editItemFragment_to_savingAndInvestingFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT)
        }
    }

}