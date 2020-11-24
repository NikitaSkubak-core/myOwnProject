package com.example.w6dagger.request

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.w6dagger.R
import com.example.w6dagger.databinding.ActivityShowRequestsBinding
import com.example.w6dagger.images.FavoriteImagesActivity
import com.example.w6dagger.images.ImagesOfRequestActivity
import com.example.w6dagger.main.ViewModelProviderFactory
import com.example.w6dagger.map.MapActivity
import dagger.android.support.DaggerAppCompatActivity
import java.util.*
import javax.inject.Inject

class ShowRequestsActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    lateinit var requestViewModel: RequestViewModel

    lateinit var binding: ActivityShowRequestsBinding
    lateinit var username: String
    lateinit var adapter: RequestListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_requests)

        setSupportActionBar(binding.appBar.toolbar)

        username = intent.getStringExtra(EXTRA_REPLY)!!

        binding.contentShow.userName.text = username
        binding.fab.setOnClickListener(clickListener())

        adapter = RequestListAdapter()
        binding.contentShow.requestRecyclerView.adapter = adapter

        requestViewModel = injectViewModel(viewModelFactory)
        requestViewModel.getRequestOfUser(username)
        requestViewModel.requests.observe(this, observe)

        val helper = itemTouchHelper(adapter)
        helper.attachToRecyclerView(binding.contentShow.requestRecyclerView)
    }

    private fun itemTouchHelper(adapter: RequestListAdapter): ItemTouchHelper {
        return ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                val position = viewHolder.adapterPosition
                val myRequest = adapter.getRequestAtPosition(position)
                Toast.makeText(
                    this@ShowRequestsActivity, "Deleting " +
                            myRequest, Toast.LENGTH_LONG
                ).show()

                // Delete the request
                requestViewModel.deleteRequest(
                    binding.contentShow.userName.text.toString(),
                    myRequest
                )
            }
        })
    }

    private val observe: Observer<List<String>> =
        Observer { requests -> // Update the cached copy of the words in the adapter.
            adapter.setRequests(requests)
        }

    private fun clickListener(): View.OnClickListener {
        return View.OnClickListener {
            val intent = Intent(this@ShowRequestsActivity, NewRequestActivity::class.java)
            startActivityForResult(intent, NEW_Request_ACTIVITY_REQUEST_CODE)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_request, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.clear_requests) {
            // Add a toast just for confirmation
            // Delete the existing data
            requestViewModel.deleteRequests(username)
            recreate()
            return true
        }
        if (id == R.id.favorite_images) {
            // Add a toast just for confirmation
            val intent = Intent(this, FavoriteImagesActivity::class.java)
            intent.putExtra(EXTRA_REPLY, username)
            startActivity(intent)
            return true
        }
        if (id == R.id.open_map) {
            // Add a toast just for confirmation
            Toast.makeText(
                this, "open map",
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra(EXTRA_REPLY, username)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_Request_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            val request =
                Objects.requireNonNull(data!!.getStringExtra(NewRequestActivity.EXTRA_REPLY_REQUEST))!!
            requestViewModel.insertRequest(username, request)
            recreate()
        }
    }

    fun onClickRequest(view: View) {
        val intent = Intent(this, ImagesOfRequestActivity::class.java)
        val data: ArrayList<String?> = ArrayList()
        data.add(username)
        data.add((view as TextView).text.toString())
        intent.putStringArrayListExtra(EXTRA_REPLY, data)
        startActivity(intent)
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.w7dagger.REPLY"
        const val NEW_Request_ACTIVITY_REQUEST_CODE = 1
    }

    inline fun <reified T : ViewModel> injectViewModel(factory: ViewModelProvider.Factory): T {
        return ViewModelProviders.of(this, factory)[T::class.java]
    }

    fun changeTheme(view: View) {
        val nightMode = AppCompatDelegate.getDefaultNightMode()
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        recreate()
    }
}