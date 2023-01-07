package com.example.realmdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.realmdatabase.databinding.ActivityMainBinding
import io.realm.Realm

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    //Initialize realm object
    var realm: Realm? = null

    //Initialize DataModel
    val dataModel = DataModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        Realm.init(this)
        realm = Realm.getDefaultInstance()
        binding.btnInsertdata.setOnClickListener(this)
        binding.btnReaddata.setOnClickListener(this)
        binding.btnUpdatedata.setOnClickListener(this)
        binding.btnDeletedata.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.btn_insertdata -> {
                addData()
            }

            R.id.btn_readdata -> {
                readData()
            }

            R.id.btn_updatedata -> {
                updateData()
            }

            R.id.btn_deletedata -> {
                deleteData()
            }
        }
    }

    fun deleteData() {

        try {
            val id: Long = 1
            val dataModel =
                realm!!.where(DataModel::class.java).equalTo("id", id).findFirst()
            realm!!.executeTransaction {
                dataModel?.deleteFromRealm()
            }
            clearFields()

            Log.d("Status", "Data deleted !!!")

        } catch (e: Exception) {
            Log.d("Status", "Something went Wrong !!!")
        }
    }

    fun updateData() {

        try {

            val id: Long = 1
            val dataModel =
                realm!!.where(DataModel::class.java).equalTo("id", id).findFirst()

            binding.edtName.setText(dataModel?.name)
            binding.edtEmail.setText(dataModel?.email)

            Log.d("Status", "Data Fetched !!!")
        } catch (e: Exception) {
            Log.d("Status", ""+e.message)
        }
    }

    fun addData() {

        try {

            realm?.beginTransaction()
            val correntNumber:Number?= realm?.where(DataModel::class.java)?.max("id")
            val nextId:Int

            nextId=if (correntNumber==null){
                1
            }
            else{
                correntNumber.toInt()+1
            }



            dataModel.name=binding.edtEmail.toString()
            dataModel.email=binding.edtEmail.toString()
            dataModel.id=nextId

            realm?.copyToRealmOrUpdate(dataModel)
            realm?.commitTransaction()

            clearFields()

            Toast.makeText(this,"success",Toast.LENGTH_LONG).show()

//            dataModel.id = binding.edtId.text.toString().toInt()
//            dataModel.name = binding.edtName.text.toString()
//            dataModel.email = binding.edtEmail.text.toString()
//
//            realm!!.executeTransaction { realm -> realm.copyToRealm(dataModel) }
//
//            clearFields()
//
//            Log.d("Status", "Data Inserted !!!")
//            Toast.makeText(this,"success",Toast.LENGTH_LONG).show()

        } catch (e: Exception) {
            Log.d("Status", ""+e.message)
            Toast.makeText(this,"error",Toast.LENGTH_LONG).show()

        }
    }

    fun readData() {

        try {

            val dataModels: List<DataModel> =
                realm!!.where(DataModel::class.java).findAll()

            for (i in dataModels.) {
                binding.edtId?.setText("" + dataModels[i].id)
                binding.edtName?.setText(dataModels[i].name)
                binding.edtEmail?.setText(dataModels[i].email)
            }

            Log.d("Status", "Data Fetched !!!")

        } catch (e: Exception) {
            Log.d("Status", "Something went Wrong !!!")
        }
    }

    fun clearFields() {

        binding.edtId.setText("")
        binding.edtName.setText("")
        binding.edtEmail.setText("")
    }
}
