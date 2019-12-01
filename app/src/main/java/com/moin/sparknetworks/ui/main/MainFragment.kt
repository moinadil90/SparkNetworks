package com.moin.sparknetworks.ui.main

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.moin.sparknetworks.R
import io.realm.Realm
import kotlinx.android.synthetic.main.main_fragment.*
import org.json.JSONObject
import java.io.InputStream


class MainFragment : Fragment() {
    private var realm: Realm = Realm.getDefaultInstance()

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var mainAdapter: MainAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainAdapter = context?.let { MainAdapter(it) }
        val obj = JSONObject(readJSONFromAsset() ?:"") ?: JSONObject()
        if (viewModel.getEntity() == null){
            realm.executeTransaction {
                it.createOrUpdateObjectFromJson(MyPojo::class.java, obj)
            }
        }

        mainAdapter?.setData(viewModel.getEntity(), viewModel)

        item_rv.layoutManager = LinearLayoutManager(activity)
        item_rv.itemAnimator = DefaultItemAnimator()

        item_rv.adapter = mainAdapter
    }

    private fun readJSONFromAsset(): String? {
        val json: String?
        try {
            val inputStream: InputStream? = context?.assets?.open("myassets.json")
            json = inputStream?.bufferedReader().use { it?.readText() }

        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        return json
    }

}
