package com.github.minimalisticclient.presenter.user_detail

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.minimalisticclient.R
import com.github.minimalisticclient.domain.entities.DomainResult
import com.github.minimalisticclient.utils.MiscUtils
import kotlinx.android.synthetic.main.activity_user_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UserDetailActivity: AppCompatActivity() {

    companion object {
        private const val USER_LOGIN = "USER_LOGIN"

        fun startActivity(activity: Activity, userLogin: String) {
            Intent(activity, UserDetailActivity::class.java).apply {
                putExtra(USER_LOGIN, userLogin)
                activity.startActivity(this)
            }
        }
    }

    private val userLogin by lazy { intent.getStringExtra(USER_LOGIN) ?: "" }
    private val viewModel: UserDetailViewModel by viewModel { parametersOf(userLogin) }
    private val reposAdapter by lazy { UserReposAdapter() }
    private val reposItemDecoration by lazy {
        object: RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val space = MiscUtils.dpToPx(this@UserDetailActivity, 8f).toInt()
                outRect.top = space
                outRect.bottom = space
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        setUpViews()
        setUpViewModels()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpViews() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setTitle(R.string.activity_user_detail_title)
        }

        rvRepos.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvRepos.adapter = reposAdapter
        rvRepos.addItemDecoration(reposItemDecoration)
    }

    private fun setUpViewModels() {
        viewModel.userDetail.observe(this, { userResult ->
            when (userResult.status) {
                DomainResult.Status.ERROR -> {
                    // show error view
                }
                DomainResult.Status.LOADING -> {
                    // show loading view
                }
                DomainResult.Status.SUCCESS -> {
                    val user = userResult.data
                    if (user != null) {
                        Glide.with(this).load(user.avatarUrl).into(imvAvatar)
                        val follower = user.followers ?: 0
                        val publicRepos = user.publicRepos ?: 0
                        val publicGists = user.publicGists ?: 0
                        txtName.text = user.login
                        txtFollowers.text = resources.getQuantityString(R.plurals.follower_count, follower, follower)
                        txtFollowing.text = getString(R.string.following_count, user.following ?: 0)
                        txtPublicRepos.text = resources.getQuantityString(R.plurals.repos_count, publicRepos, publicRepos)
                        txtPublicGist.text = resources.getQuantityString(R.plurals.gists_count, publicGists, publicGists)
                    } else {
                        txtName.text = userLogin
                        txtFollowers.text = resources.getQuantityString(R.plurals.follower_count, 0, 0)
                        txtFollowing.text = getString(R.string.following_count, 0)
                        txtPublicRepos.text = resources.getQuantityString(R.plurals.repos_count, 0, 0)
                        txtPublicGist.text = resources.getQuantityString(R.plurals.gists_count, 0, 0)
                    }
                }
            }
        })
        viewModel.userRepos.observe(this, { reposResult ->
            when (reposResult.status) {
                DomainResult.Status.ERROR -> {
                    // show error view
                }
                DomainResult.Status.LOADING -> {
                    // show loading view
                }
                DomainResult.Status.SUCCESS -> {
                    if (!reposResult.data.isNullOrEmpty()) {
                        reposAdapter.submitData(reposResult.data)
                    }
                }
            }
        })
    }
}
