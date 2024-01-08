package com.example.desafiokotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.opencsv.CSVWriter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.FileWriter
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var ObserverConnectivity: observerConnectivity

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenCreated {
            ObserverConnectivity = NetworkConnectivityObserver(applicationContext)
            generateCsvFile()
        }

        setContent {
            val status by rememberUpdatedState(
                ObserverConnectivity.observe().collectAsState(initial = observerConnectivity.Status.Unavailable)
            )

            if (::ObserverConnectivity.isInitialized) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_network_status),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Status de Conexão",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = status.toString(),
                        style = TextStyle(fontSize = 16.sp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

    }

    private suspend fun generateCsvFile() {
        // Caminho do arquivo CSV que você deseja gerar
        val csvFilePath = getExternalFilesDir(null)?.absolutePath + "/arquivo.csv"
        Log.d("Tag", "Caminho do arquivo: $csvFilePath")

        try {
            val status = ObserverConnectivity.observe().firstOrNull() ?: observerConnectivity.Status.Unavailable

            // Abre o arquivo CSV para escrita
            CSVWriter(FileWriter(csvFilePath)).use { csvWriter ->
                // Escreve o conteúdo da variável $status no arquivo CSV
                csvWriter.writeNext(arrayOf("Status de Conexão"))
                csvWriter.writeNext(arrayOf(status.toString()))
            }

            Log.d("foi", "Arquivo CSV gerado com sucesso em: $csvFilePath")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("naofoi", "Erro ao gerar o arquivo CSV: ${e.message}")
        }
    }
}
