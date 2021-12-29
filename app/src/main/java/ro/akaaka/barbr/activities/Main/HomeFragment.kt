package ro.akaaka.barbr.activities.Main

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ro.akaaka.barbr.R
import ro.akaaka.barbr.activities.Main.controller.AppointmentAdapter
import ro.akaaka.barbr.activities.Main.model.AppointmentData
import ro.akaaka.barbr.activities.Main.model.jsonToAppointment
import ro.akaaka.barbr.network.NetworkUtils
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONArray
import java.util.*

class HomeFragment : Fragment() {
    private lateinit var parentActivity: Activity
    private val appointmentList = ArrayList<AppointmentData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_main.layoutManager = LinearLayoutManager(parentActivity, RecyclerView.VERTICAL, false)
        rv_main.adapter = AppointmentAdapter(appointmentList, parentActivity)

        val reqAppointments = NetworkUtils.httpRequest("get", "figaro/appointment")
        NetworkUtils.makeRefreshingRequest(reqAppointments, ::setAppointments, parentActivity, false)
    }

    private fun setAppointments(responseData: String) {
        if (rv_main == null)
            return

        appointmentList.removeAll(appointmentList)

        val arr = JSONArray(responseData)
        for(i: Int in 0 until arr.length())
            appointmentList.add(jsonToAppointment(arr.getJSONObject(i)))

        rv_main.adapter = AppointmentAdapter(appointmentList, parentActivity)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is Activity)
            parentActivity = context
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
