package com.chhotelalpal.themoviedb.ui.activities
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chhotelalpal.themoviedb.R
import com.chhotelalpal.themoviedb.api.ServiceBuilder
import com.chhotelalpal.themoviedb.api.TmdbEndpoints
import com.chhotelalpal.themoviedb.data.PopularMovies
import com.chhotelalpal.themoviedb.ui.adapters.MoviesAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class TopRatedFragment : Fragment() {




    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.activity_main, container, false)

        if (isNetworkConnected()) {
            val request = ServiceBuilder.buildService(TmdbEndpoints::class.java)

            val call = request.getTopRated(getString(R.string.api_key))
            call.enqueue(object : Callback<PopularMovies> {
                override fun onResponse(
                    call: Call<PopularMovies>,
                    response: Response<PopularMovies>
                ) {
                    if (response.isSuccessful) {
                        Log.e("resulttttt", "" + response.body()!!.results)
                        progress_bar.visibility = View.GONE
                        recyclerView.apply {
                            setHasFixedSize(true)
                            layoutManager = LinearLayoutManager(activity)
                            adapter = MoviesAdapter(response.body()!!.results)
                        }
                    }
                }

                override fun onFailure(call: Call<PopularMovies>, t: Throwable) {
                    Toast.makeText(activity, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            AlertDialog.Builder(activity).setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again")
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
        return root
    }
    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager //1
        val networkInfo = connectivityManager.activeNetworkInfo //2
        return networkInfo != null && networkInfo.isConnected //3
    }
}