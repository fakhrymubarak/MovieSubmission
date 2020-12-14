package com.fakhry.movie.ui.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fakhry.movie.R
import com.fakhry.movie.data.source.local.entity.TvShowEntity
import com.fakhry.movie.ui.details.DetailsActivity
import com.fakhry.movie.viewmodel.ViewModelFactory
import com.fakhry.movie.vo.Status
import kotlinx.android.synthetic.main.fragment_tv_show.*


class TvShowFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showLoading(true)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val tvShowViewModel = ViewModelProvider(
                this, factory
            )[TvShowViewModel::class.java]
//            EspressoIdlingResource.increment()
            tvShowViewModel.getPopularTvShows().observe(this, { tvShows ->
                if (tvShows != null) {
                    when (tvShows.status) {
                        Status.LOADING -> showLoading(true)
                        Status.SUCCESS -> {
                            showLoading(false)
                            if (tvShows.data != null) {
                                showRecyclerView(tvShows.data)
                            }
                        }
                        Status.ERROR -> {
                            showLoading(false)
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
//                EspressoIdlingResource.decrement()
            })

        }
    }

    private fun showRecyclerView(tvShows: List<TvShowEntity>) {
        rv_tv_show.setHasFixedSize(true)
        val tvShowAdapter = TvShowAdapter()
        tvShowAdapter.setTvShows(tvShows)
        tvShowAdapter.notifyDataSetChanged()

        rv_tv_show.layoutManager = LinearLayoutManager(context)
        rv_tv_show.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        rv_tv_show.adapter = tvShowAdapter
        tvShowAdapter.setOnItemClickCallback(object : TvShowAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TvShowEntity) {
                showSelectedUser(data.tvShowId)
            }
        })
    }

    private fun showSelectedUser(itemsId: Int?) {
        val intent = Intent(requireActivity(), DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.EXTRA_TV, itemsId)
        startActivity(intent)
    }

    private fun showLoading(state: Boolean) =
        if (state) progressBar.visibility = View.VISIBLE else progressBar.visibility = View.GONE
}
