package com.aruna.kliknelayanwakatobi.customui.form

import android.content.Context
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.TextView
import com.aruna.kliknelayanwakatobi.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONObject


/**
 * Created by marzellaalfamega on 12/5/17.
 */
class FormMap(context: Context, title: String, fragmentManager: FragmentManager?) : FormBase(context, title), OnMapReadyCallback {

    val lat = -5.3264
    val lon = 123.5952

    override fun onMapReady(googleMap: GoogleMap?) {
        val wakatobi = LatLng(lat, lon)
        marker = googleMap?.addMarker(MarkerOptions().position(wakatobi)
                .title("Marker in Sydney"))
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(wakatobi))

        googleMap?.setOnMapClickListener { latLng ->
            marker?.position = latLng
        }

        googleMap?.uiSettings?.isZoomControlsEnabled = true
        googleMap?.uiSettings?.isZoomGesturesEnabled = true

        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(wakatobi))
        googleMap?.animateCamera(CameraUpdateFactory.zoomTo(11F))
    }

    var marker: Marker? = null

    init {

        val view = LayoutInflater.from(context).inflate(R.layout.form_map, null, false)

        val tvTitle = view.findViewById<TextView>(R.id.tvTitleFormMap)
        tvTitle.text = title

        val mapFragment = fragmentManager
                ?.findFragmentById(R.id.mapLocationFormMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val mapDispatcherFormMap = view.findViewById<View>(R.id.mapDispatcherFormMap)
        mapDispatcherFormMap.setOnTouchListener(OnTouchListener { v, event ->
            val action = event.action
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    // Disallow ScrollView to intercept touch events.
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                    // Disable touch on transparent view
                    false
                }

                MotionEvent.ACTION_UP -> {
                    // Allow ScrollView to intercept touch events.
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                    false
                }

                else -> true
            }
        })

        addView(view)
    }

    override fun getValue(): JSONObject {
        val latLon = marker?.position
        val json = JSONObject()
        if (latLon != null) {
            json.put("longitude", latLon.longitude.toString())
            json.put("latitude", latLon.latitude.toString())
        }
        return json
    }

}