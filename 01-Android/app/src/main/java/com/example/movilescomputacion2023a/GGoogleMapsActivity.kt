package com.example.movilescomputacion2023a

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

private lateinit var mapa:GoogleMap
var permisos = false

class GGoogleMapsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ggoogle_maps_activity)
        solicitarPermisos()
        iniciarLogicaMapa()

        val boton = findViewById<Button>(R.id.btn_ir_carolina)
        boton.
                setOnClickListener {
                    irCarolina()
                }
    }

    fun irCarolina(){
        val carolina = LatLng(-0.1825684318486696,
            -78.48447277600916)
        val zoom = 17f
        moverCamaraConZoom(carolina, zoom)
    }

    fun solicitarPermisos(){
        val contexto = this.applicationContext
        val nombrePermiso = android.Manifest.permission.ACCESS_FINE_LOCATION
        val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
        val permisosFineLocation = ContextCompat
            .checkSelfPermission(
                contexto,
                nombrePermiso
            )
        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
        if(tienePermisos){
            permisos = true
        } else{
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    nombrePermiso, nombrePermisoCoarse
                ),
                1
            )
        }
    }

    fun iniciarLogicaMapa(){
        val fragmentoMapa = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync{ googleMap ->
            //with(x) => if(x !=null)
            with(googleMap){
                mapa = googleMap
                establecerConfiguracionMapa()
                moverQuicentro()
                anadirPoligono()
                anadirPoliLinea()
                escucharListeners()
            }
        }
    }

    fun escucharListeners(){
        mapa.setOnPolygonClickListener {
            Log.i("mapa", "setOnPolygonClickListener ${it}")
            it.tag //ID
        }
        mapa.setOnPolygonClickListener {
            Log.i("mapa", "setOnPolylineClickListener ${it}")
            it.tag //ID
        }
        mapa.setOnMarkerClickListener {
            Log.i("mapa", "setOnMarkerClickListener ${it}")
            it.tag //ID
            return@setOnMarkerClickListener true
        }
        mapa.setOnCameraMoveListener {
            Log.i("mapa", "setOnCamerMoveListener")
        }
        mapa.setOnCameraMoveStartedListener {
            Log.i("mapa", "setOnCameraMoveStartedListener ${it}")
        }
        mapa.setOnCameraIdleListener {
            Log.i("mapa", "setOnCameraIdListener")
        }
    }



    fun anadirPoliLinea(){
        with(mapa){
            val poliLinea = mapa
                .addPolyline(
                    PolylineOptions()
                        .clickable(true)
                        .add(
                            LatLng(-0.1759187040647396,
                            -78.48506472421384),
                            LatLng(-0.17032408492902104,
                                    -78.48265589308046),
                            LatLng(-0.17746143130181483,
                            -78.4770555507815)
                        )
                )
            poliLinea.tag = "linea-1" // <- ID
        }
    }

    fun anadirPoligono(){
        with(mapa){
            //POLIGONO
            val poligonoUno = mapa
                .addPolygon(
                    PolygonOptions()
                        .clickable(true)
                        .add(
                            LatLng(-0.1771546902239471,
                                -75.48544981495214),
                            LatLng(-0.17968981486125768,
                            -78.48269198043828),
                            LatLng(-0.17716958124147777,
                            -78.48142892291516)
                        )
                )
            poligonoUno.fillColor = -0xc771c4
            poligonoUno.tag = "poligono-2" //<-ID
        }
    }


        fun moverQuicentro(){
            val zoom = 17f
            val quicentro = LatLng(
                -0.17556708490271092, -78.48014901143776
            )
            val titulo = "Quicentro"
            val markQuicentro = anadirMarcador(quicentro, titulo)
            markQuicentro.tag = titulo
            moverCamaraConZoom(quicentro, zoom)
        }

        fun anadirMarcador(latLng: LatLng, title: String): Marker {
            return mapa.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(title)
            )!!
        }

        fun moverCamaraConZoom(latLng: LatLng, zoom: Float = 10f){
            mapa.moveCamera(
                CameraUpdateFactory
                    .newLatLngZoom(latLng, zoom)
            )
        }

    fun establecerConfiguracionMapa(){
        val contexto = this.applicationContext
        with(mapa){
            val permisosFineLocation = ContextCompat
                .checkSelfPermission(
                    contexto,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
            if(tienePermisos){
                mapa.isMyLocationEnabled = true //tenemos permisos
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true //Por defecto
        }
    }



}