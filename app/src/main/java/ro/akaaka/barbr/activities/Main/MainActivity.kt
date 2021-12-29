package ro.akaaka.barbr.activities.Main

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import ro.akaaka.barbr.R
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    HomeFragment.OnFragmentInteractionListener {

    override fun onFragmentInteraction(uri: Uri) {
    }

    lateinit var homeFragment : HomeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeFragment = HomeFragment.newInstance()

        val bottomNavigation = bottom_navigation

        val item1 = AHBottomNavigationItem("Account", R.drawable.account)
        val item2 = AHBottomNavigationItem("Home", R.drawable.home)
        val item3 = AHBottomNavigationItem("Search", R.drawable.magnify)

        bottom_navigation.addItem(item1)
        bottom_navigation.addItem(item2)
        bottom_navigation.addItem(item3)
        bottomNavigation.isBehaviorTranslationEnabled = false

        bottom_navigation.currentItem = 1
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        bottom_navigation.setOnTabSelectedListener { position, wasSelected ->
            if (!wasSelected)
                when (position) {
                    0 -> Toast.makeText(this, "Account fragment", Toast.LENGTH_SHORT).show()
                    1 -> supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, homeFragment)
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .commit()
                    2 -> Toast.makeText(this, "Search fragment", Toast.LENGTH_SHORT).show()
                }

            true
        }
    }
}