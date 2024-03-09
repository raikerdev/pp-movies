package com.raikerdev.petproject.movies.ui.main

import android.Manifest
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.raikerdev.petproject.movies.R
import com.raikerdev.petproject.movies.data.Error
import com.raikerdev.petproject.movies.data.database.Movie
import com.raikerdev.petproject.movies.ui.common.PermissionRequester
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.buildMainState(
    context: Context = requireContext(),
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    navController: NavController = findNavController(),
    locationPermissionRequester: PermissionRequester =
        PermissionRequester(this, Manifest.permission.ACCESS_COARSE_LOCATION),
) = MainState(context, scope, navController, locationPermissionRequester)

class MainState(
    private val context: Context,
    private val scope: CoroutineScope,
    private val navController: NavController,
    private val locationPermissionRequester: PermissionRequester,
) {

    fun onMovieClicked(movie: Movie) {
        val navAction = MainFragmentDirections.actionMainToDetail(movie.id)
        navController.navigate(navAction)
    }

    fun requestLocationPermission(afterRequest: (Boolean) -> Unit) {
        scope.launch {
            val result = locationPermissionRequester.request()
            afterRequest(result)
        }
    }

    fun errorToString(error: Error) = when(error) {
        is Error.Connectivity -> context.getString(R.string.connectivity_error)
        is Error.Server -> context.getString(R.string.server_error) + error.code
        is Error.Unknown -> context.getString(R.string.unknown_error) + error.message
    }

}
