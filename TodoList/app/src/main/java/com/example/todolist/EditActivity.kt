package com.example.todolist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.util.*

class EditActivity : AppCompatActivity() {

    val realm = Realm.getDefaultInstance()!!

    var calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        // 업데이트 조건
        val id = intent.getLongExtra("id", -1L)
        if (id != -1L) {
            val todo = realm.where<Todo>().equalTo("id", id).findFirst()!!
            todoEditText.setText(todo.text)
            calendarView.date = todo.date
        } else {
            deleteFab.visibility = View.GONE
        }

        fab.setOnClickListener {
            if (id == -1L) {
                insertTodo()
            } else {
                updateTodo(id)
            }
        }

        deleteFab.setOnClickListener {
            deleteTodo(id)
        }

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendar = GregorianCalendar(year, month, dayOfMonth)
        }
    }

    private fun updateTodo(id: Long) {
        realm.beginTransaction()
        val todo = realm.where<Todo>().equalTo("id", id).findFirst()!!
        todo.text = todoEditText.getText().toString()
        todo.date = calendar.time.time
        alert("내용이 변경되었습니다.") {
            yesButton { finish() }
        }.show()
        realm.commitTransaction()
    }

    private fun insertTodo() {
        realm.beginTransaction()
        val todo = realm.createObject<Todo>(nextId())
        todo.text = todoEditText.getText().toString()
        todo.date = calendar.time.time
        alert("내용이 추가되었습니다.") {
            yesButton { finish() }
        }.show()
        realm.commitTransaction()
    }

    private fun deleteTodo(id: Long) {
        realm.beginTransaction()
        val todo = realm.where<Todo>().equalTo("id", id).findFirst()!!
        todo.deleteFromRealm()
        realm.commitTransaction()

        alert("내용이 삭제되었습니다.") {
            yesButton { finish() }
        }.show()
    }

    private fun nextId(): Int {
        val maxId = realm.where<Todo>().max("id")
        if (maxId != null) {
            return maxId.toInt() + 1
        }
        return 0
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
