package com.begoml.androidmvi.view

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.begoml.androidmvi.mvi.MviViewModel
import javax.inject.Inject

//abstract class ViewStateFragment<ViewModel : MviViewModel>(
//    layoutRes: Int,
//    clazzViewModel: Class<ViewModel>
//) : Fragment(layoutRes) {
//
//    @Inject
//    lateinit var viewModelFactory: ViewModelProvider.Factory
//
//    val viewModel: ViewModel by lazy {
//        ViewModelProvider({ this.viewModelStore }, viewModelFactory).get(clazzViewModel)
//    }
//
////    open val watcher: ViewStateWatcher<ViewState> = initializeViewStateWatcher {
////
////    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        injectDependencies()
//    }
//
//    override fun onStart() {
//        super.onStart()
//
//        viewModel.apply {
//
//            viewState.observe(viewLifecycleOwner, Observer { stateView ->
//                stateView?.let {
//                    renderViewState(it as ViewState)
//                }
//            })
//        }
//    }
//
//    override fun onStop() {
//        super.onStop()
//
//        viewModel.apply {
//            viewState.removeObservers(viewLifecycleOwner)
//        }
//    }
//
//
//    private fun renderViewState(viewModel: ViewState) {
//        watcher.render(viewModel)
//    }
//
//    @CallSuper
//    override fun onDestroyView() {
//        watcher.clear()
//        super.onDestroyView()
//    }
//
//    abstract fun injectDependencies()
//
//}