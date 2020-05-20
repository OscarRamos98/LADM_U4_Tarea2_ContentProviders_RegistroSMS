package mx.edu.ittepic.ladm_u4_tarea2_contentproviders_registrosms

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val siLecturaSMS = 1

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_SMS),siLecturaSMS)
        }

        button.setOnClickListener {
            leerRegistroSMS()
        }
    }


    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == siLecturaSMS){
            Toast.makeText(this,"Permiso otorgado",Toast.LENGTH_LONG).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun leerRegistroSMS() {
        var resultado = ""
        val cursorSMS = contentResolver.query(Telephony.Sms.CONTENT_URI,null,null,null,null)
        var bandeja = ""
        val origen = cursorSMS!!.getColumnIndex(Telephony.Sms.ADDRESS)
        val mensaje = cursorSMS.getColumnIndex(Telephony.Sms.BODY)
        val tipo = cursorSMS.getColumnIndex(Telephony.Sms.TYPE)

        while (cursorSMS.moveToNext()) {
            var direccionC = cursorSMS.getString(origen)
            var textoC = cursorSMS.getString(mensaje)
            var tipoC = cursorSMS.getString(tipo)
            if(tipoC == "1") {
                bandeja = "Entrada"
            }else {
                bandeja = "Enviados"
            }
            resultado +=
                    "Numero celular: " + direccionC +"\n"+
                            "Mensaje: "+textoC +"\n" +
                            "Bandeja: "+bandeja+"\n"+
                            "--------------------------\n"
        }
        mensajes.setText("Mensajes :\n"+resultado)
    }

}
