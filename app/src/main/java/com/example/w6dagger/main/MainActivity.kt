package com.example.w6dagger.main

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
import com.example.w6dagger.gallery.GalleryActivity
import com.example.w6dagger.R
import com.example.w6dagger.dataBase.User
import com.example.w6dagger.databinding.ActivityMainBinding
import com.example.w6dagger.gallery.internalGallery.InternalGalleryActivity
import com.example.w6dagger.request.ShowRequestsActivity
import com.facebook.stetho.Stetho
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.content_main.view.*
import java.util.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    lateinit var adapter: UserListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    lateinit var userViewModel: UserViewModel
    lateinit var binding: ActivityMainBinding

    val EXTRA_REPLY = "com.example.android.w7dagger.REPLY"
    val NEW_User_ACTIVITY_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Stetho.initializeWithDefaults(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.appBar.toolbar)

        adapter = UserListAdapter()

        userViewModel = injectViewModel(viewModelFactory)

        binding.contantMain.recyclerview.adapter = adapter

        userViewModel = injectViewModel(viewModelFactory)
        userViewModel.getAllUsers()
        userViewModel.allUsers.observe(this, observer)

        val helper = itemTouchHelper(adapter)
        helper.attachToRecyclerView(binding.contantMain.recyclerview)
    }

    private fun itemTouchHelper(adapter: UserListAdapter?): ItemTouchHelper {
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
                val myUser = adapter!!.getUserAtPosition(position)
                Toast.makeText(
                    this@MainActivity, "Deleting " +
                            myUser.userName, Toast.LENGTH_LONG
                ).show()

                // Delete user
                userViewModel.deleteUser(myUser)
            }
        })
    }

    private val observer: Observer<List<User>> =
        Observer { users -> // Update the cached copy of the words in the adapter.
            adapter.setUsers(users)
        }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clear_data -> {

                // Add a toast just for confirmation
                Toast.makeText(
                    this, "Deleting all users...",
                    Toast.LENGTH_SHORT
                ).show()

                // Delete the existing data
                userViewModel.deleteAllUsers()
                Thread.sleep(10)
                recreate()
                return true
            }
            R.id.get_gallery -> {
                run {
                    Toast.makeText(
                        this, "opening a Gallery...",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, GalleryActivity::class.java)
                    startActivity(intent)
                }
            }
            R.id.get_internal_gallery -> {
                Toast.makeText(
                    this, "opening a Gallery...",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this, InternalGalleryActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun clickToFab() {
        val intent = Intent(this@MainActivity, AddUserActivity::class.java)
        startActivityForResult(intent, NEW_User_ACTIVITY_REQUEST_CODE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_User_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            val user = User(
                Objects.requireNonNull(
                    data!!.getStringExtra(AddUserActivity.EXTRA_REPLY)!!
                )
            )
            userViewModel.insertUser(user)
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
        recreate()
    }

    fun onClick(view: View) {
        val tv: TextView = view as TextView;
        val intent: Intent = Intent(this, ShowRequestsActivity::class.java)
        intent.putExtra(EXTRA_REPLY, tv.getText().toString());
        startActivity(intent);
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