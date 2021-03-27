package com.shreyas.nycschools.base

import android.os.Bundle
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import dagger.android.support.AndroidSupportInjection
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

/**
 *  Base Fragment that does viewModel injection as parameter
 */
abstract class BaseFragment<M : BaseViewModel> : Fragment() {

    lateinit var viewModel: M

    @Inject
    lateinit var viewModelsFactory: ViewModelProvider.Factory

    open fun getViewModelFactory(): ViewModelProvider.Factory = viewModelsFactory

    open fun getViewModelOwner(): ViewModelStoreOwner = requireActivity()

    @Suppress("UNCHECKED_CAST")
    open fun viewModelClass(): Class<M> =
        ((javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<M>)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(getViewModelOwner(), getViewModelFactory()).get(viewModelClass())
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Either Response is empty or Service Error")
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.cancel()
            }
        val alert = builder.create()
        alert.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeUI()
    }

    private fun subscribeUI() {
        viewModel.isError.observe(viewLifecycleOwner, { isError ->
            if (isError) {
                showDialog()
            }
        })
        // Reset the isError to false so dialog is not shown again on success list item click
        viewModel._isError.value = false
    }

    override fun onStart() {
        super.onStart()
        setFragmentTitle()
    }

    private fun setFragmentTitle() {
        val screenTitle = getTitle()
        if (activity is AppCompatActivity) {
            val supportActionBar = (activity as AppCompatActivity).supportActionBar
            if (supportActionBar != null) {
                if (screenTitle.isNotEmpty()) {
                    supportActionBar.title = screenTitle
                }
                supportActionBar.setDisplayHomeAsUpEnabled(true)
            }
        }
        if (screenTitle.isNotEmpty()) {
            requireActivity().title = getTitle()
        }
    }

    // Protected method to set title for Fragment toolbar
    @VisibleForTesting
    internal open fun getTitle() = ""
}