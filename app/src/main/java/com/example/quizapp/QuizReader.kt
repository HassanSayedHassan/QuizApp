package com.example.quizapp
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import org.json.JSONObject
import com.google.gson.*
import java.io.*


class QuizReader {

    companion object {
        val gson = GsonBuilder().create()

        fun parseJSONString(json: String): QuestionsSet? {
            return gson.fromJson(json, QuestionsSet::class.java) ?: null
        }

        fun readFile(context: Context, uri: Uri?): String? {

            var stringResult: String? = null

            if (uri != null) {
                Log.d("uri:   ", uri.path.toString())
                val inputStream = context.contentResolver.openInputStream(uri)
                val isReader = InputStreamReader(inputStream)
                val reader = BufferedReader(isReader)
                val stringBuffer = StringBuffer()
                var str: String?

                str = reader.readLine()
                while (str != null) {
                    stringBuffer.append(str)
                    str = reader.readLine()
                }


                Log.d("Content: ", stringBuffer.toString())
                stringResult = stringBuffer.toString()
            }

            return stringResult
        }

        fun saveStringAsFileInFilesDir(context: Context, name: String, string: String) {
            //val file_count = context.filesDir?.listFiles()?.size
            val file = File(context.filesDir, name)
            file.writeText(string)
        }

        fun writeStringToUri(context: Context, string: String, uri: Uri?) {

            if (uri != null) {
                try {
                    val os = context.contentResolver.openOutputStream(uri)
                    if (os != null) {
                        Log.d("string", string)
                        val ow = OutputStreamWriter(os)
                        ow.write(string)
                        ow.close()
                        os.close()
                    }
                } catch (exception: Exception) {
                    Log.d("Exception", "Could't write file")
                }

            }
        }

        fun readSets(activity: Activity): ArrayList<QuestionsSet> {
            val files = activity.filesDir.listFiles()

            var n = 0
            if (files?.size != null) {
                n = files.size
            }
            val result = ArrayList<QuestionsSet>()
            var i = 0
            while (i < n) {
                var name = ""
                if (files?.get(i) != null) {
                    name = files[i].name
                }
                val file = File(activity.filesDir, name)

                val jsonString: String = file.readText(Charsets.UTF_8)
                val newSet: QuestionsSet? = parseJSONString(jsonString) ?: null
                if (newSet != null)
                    result.add(newSet)
                i++
            }

            Log.d("Result size:", result.size.toString())

            return result
        }

        fun stringOfQuestionsSet(questionsSet: QuestionsSet?): String {
            return gson.toJson(questionsSet)
        }

        fun getNameFromString(string: String):String?
        {
            val questionsSet = gson.fromJson(string,QuestionsSet::class.java)?:null
            return questionsSet?.name
        }
        fun deleteQuestionsSet(activity: Activity, name: String?)
        {

            if(name!=null) {
                val files = activity.filesDir.listFiles()
                var indexToDelete = Int.MIN_VALUE
                for (i in 0..files.size - 1) {
                    if (files[i].name == name)
                        indexToDelete = i;
                }

                if (indexToDelete != Int.MIN_VALUE) {
                    files[indexToDelete].delete()
                }
            }
        }
    }

}