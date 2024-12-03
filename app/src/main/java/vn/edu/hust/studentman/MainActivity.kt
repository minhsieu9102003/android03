package vn.edu.hust.studentman

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

  private lateinit var navController: NavController
  private val TAG = "MainActivity"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Retrieve NavHostFragment from FragmentManager
    val navHostFragment = supportFragmentManager
      .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment

    if (navHostFragment == null) {
      Log.e(TAG, "NavHostFragment not found in the layout.")
      return
    }

    // Get NavController from NavHostFragment
    navController = navHostFragment.navController

    // Setup ActionBar with NavController
    setupActionBarWithNavController(navController)

    Log.d(TAG, "NavController successfully set up.")
  }

  // Handle Up navigation
  override fun onSupportNavigateUp(): Boolean {
    return navController.navigateUp() || super.onSupportNavigateUp()
  }
}
