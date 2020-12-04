package com.fakhry.movie.ui.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fakhry.movie.R
import com.fakhry.movie.data.source.local.entity.MovieAndTvShowEntity
import com.fakhry.movie.ui.details.DetailsActivity
import com.fakhry.movie.viewmodel.ViewModelFactory
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
            val factory = ViewModelFactory.getInstance()
            val viewModel = ViewModelProvider(
                this, factory
            )[TvShowViewModel::class.java]
//            viewModel.getTvShow().observe(this, { tvShows ->
//                showRecyclerView(tvShows)
//            })

        }
    }

    private fun showRecyclerView(tvShows: List<MovieAndTvShowEntity>) {
        rv_tv_show.setHasFixedSize(true)
        val tvShowAdapter = TvShowAdapter()
        tvShowAdapter.setTvShows(tvShows)
        tvShowAdapter.notifyDataSetChanged()

        rv_tv_show.layoutManager = LinearLayoutManager(context)
        rv_tv_show.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        rv_tv_show.adapter = tvShowAdapter
        tvShowAdapter.setOnItemClickCallback(object : TvShowAdapter.OnItemClickCallback {
            override fun onItemClicked(data: MovieAndTvShowEntity) {
                showSelectedUser(data.id)
            }
        })
        showLoading(false)
    }

    private fun showSelectedUser(itemsId: Int?) {
        val intent = Intent(requireActivity(), DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.EXTRA_TV, itemsId)
        startActivity(intent)
    }

    private fun showLoading(state: Boolean) =
        if (state) progressBar.visibility = View.VISIBLE else progressBar.visibility = View.GONE
}
