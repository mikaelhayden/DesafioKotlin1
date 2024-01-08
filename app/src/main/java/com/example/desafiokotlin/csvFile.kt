import com.opencsv.CSVWriter
import java.io.FileWriter
import java.io.IOException

fun main() {
    // Seu conjunto de dados (pode ser uma lista de objetos ou arrays)
    val data = listOf(
        arrayOf("Nome", "Idade", "Cidade"),
        arrayOf("João", "25", "São Paulo"),
        arrayOf("Maria", "30", "Rio de Janeiro"),
        arrayOf("Carlos", "22", "Belo Horizonte")
    )

    // Caminho do arquivo CSV que você deseja gerar
    val csvFilePath = "C:/Users/mikae/OneDrive/Documentos/GitHub/DesafioKotlin/app/csvs"
    try {
        CSVWriter(FileWriter(csvFilePath)).use { csvWriter ->
            // Escreve cada linha de dados no arquivo CSV
            csvWriter.writeAll(data)
        }
        println("Arquivo CSV gerado com sucesso em: $csvFilePath")
    } catch (e: IOException) {
        e.printStackTrace()
        println("Erro ao gerar o arquivo CSV: ${e.message}")
    }
}
