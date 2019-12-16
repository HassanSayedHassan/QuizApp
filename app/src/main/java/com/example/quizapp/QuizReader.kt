package com.example.quizapp
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.gson.*
import java.io.*


class QuizReader {

    companion object {
        val gson = GsonBuilder().create()
        val QS_SUBFOLDER_NAME = "QuestionsSets";

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

            val directory = File(context.filesDir, QS_SUBFOLDER_NAME)
            if(!directory.exists()) {
                Log.d("mkdir",directory.path )
                directory.mkdir()
            }
            Log.d("directory", directory.path)

            val file = File(directory, name)
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

            val result = ArrayList<QuestionsSet>()
            val dirList = activity.filesDir.listFiles().filter { file -> file.name == QS_SUBFOLDER_NAME}

            if(dirList.isNotEmpty()) {
                val directory: File = dirList[0]
                val files = directory.listFiles()
                Log.d("directory", directory.path)
                var n = 0
                if (files?.size != null) {
                    n = files.size
                }

                var i = 0
                while (i < n) {
                    var name = ""
                    if (files?.get(i) != null) {
                        name = files[i].name
                    }
                    val file = File(directory, name)

                    val jsonString: String = file.readText(Charsets.UTF_8)
                    val newSet: QuestionsSet? = parseJSONString(jsonString) ?: null
                    if (newSet != null)
                        result.add(newSet)
                    i++
                }
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
                val directory:File = activity.filesDir.listFiles().filter { file -> file.name == QS_SUBFOLDER_NAME}[0]

                if(directory.exists()) {
                    val files = directory.listFiles()
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

}