package com.jio.siops_ngo.ngo.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.jio.siops_ngo.R
import com.jio.siops_ngo.databinding.FragmentNgoOiHistoryBinding
import com.jio.siops_ngo.databinding.FragmentNgoOpenIncidentBinding
import com.jio.siops_ngo.databinding.FragmentNgoServiceGlanceDetailsBinding
import com.jio.siops_ngo.databinding.NgoOiHistoryRvBinding
import com.jio.siops_ngo.ngo.adapter.NgoOpenIncidentItemAdapter

/**
 * A simple [Fragment] subclass.
 */
class NgoOpenIncidentHistoryItemFragment : Fragment() {

    var msg: HashMap<String, Any>? = null
    var mBinding: NgoOiHistoryRvBinding? = null
    var key: String? = ""
    var title: String? = ""
    var count: String? = ""
    var severityList: ArrayList<HashMap<String, Any>>? = null


    companion object {
        fun newInstance() =
            NgoOpenIncidentHistoryItemFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.ngo_oi_history_rv, container, false)
        return mBinding!!.root
        // return inflater.inflate(R.layout.fragment_ngo_service_glance_details, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding!!.txtSeverityHeader.text = title
        mBinding!!.txtSeverityCount.text = count

        if (msg!!.containsKey(key) && msg!![key] != null) {
            severityList = msg!![key] as ArrayList<HashMap<String, Any>>

            mBinding!!.recyclerViewOi.apply {
                layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                adapter = NgoOpenIncidentItemAdapter(severityList!!)
            }

        }
//        fetchData()

        /*if (selection == 0) {
            mBinding!!.txtHeader.text =
                (activity as MainActivity).resources.getString(R.string.open_business_impacting_incidents)
            mBinding!!.card3Oi.visibility = View.GONE
            mBinding!!.card4Oi.visibility = View.GONE

            if (msg != null) {
                if (msg!!.containsKey("busiOI") && msg!!["busiOI"] != null) {
                    if (msg!!.containsKey("businessImpactingCount") && msg!!["businessImpactingCount"] != null) {
                        mBinding!!.txtHeader.text =
                            (activity as MainActivity).resources.getString(R.string.open_business_impacting_incidents) + " (" + msg!!["businessImpactingCount"].toString() + ")"
                    }
                    var busiOI = msg!!["busiOI"] as ArrayList<Map<String, Any>>
                    if (Utils.hasIndex(0, busiOI!!)) {
                        if (busiOI[0]!!.containsKey("outlierCount") && busiOI[0]!!["outlierCount"] != null) {
                            mBinding!!.txtSeverityCount1.text =
                                busiOI[0]!!["outlierCount"].toString()
                        }

                        if (busiOI[0]!!.containsKey("featureName") && busiOI[0]!!["featureName"] != null) {
                            mBinding!!.txtSeverity1.text = busiOI[0]!!["featureName"].toString()
                        }

                    }
                    if (Utils.hasIndex(1, busiOI!!)) {
                        if (busiOI[1]!!.containsKey("outlierCount") && busiOI[1]!!["outlierCount"] != null) {
                            mBinding!!.txtSeverityCount2.text =
                                busiOI[1]!!["outlierCount"].toString()
                        }

                        if (busiOI[1]!!.containsKey("featureName") && busiOI[1]!!["featureName"] != null) {
                            mBinding!!.txtSeverity2.text = busiOI[1]!!["featureName"].toString()
                        }
                    }

                }
            }
        } else {
            mBinding!!.txtHeader.text =
                (activity as MainActivity).resources.getString(R.string.open_non_business_impacting_incidents)

            if (msg != null) {
                if (msg!!.containsKey("nonBusiOI") && msg!!["nonBusiOI"] != null) {
                    if (msg!!.containsKey("nonBusinessImpactingCount") && msg!!["nonBusinessImpactingCount"] != null) {
                        mBinding!!.txtHeader.text =
                            (activity as MainActivity).resources.getString(R.string.open_non_business_impacting_incidents) + " (" + msg!!["nonBusinessImpactingCount"].toString() + ")"
                    }
                    var nonBusiOI = msg!!["nonBusiOI"] as ArrayList<Map<String, Any>>

                    if (Utils.hasIndex(0, nonBusiOI!!)) {
                        if (nonBusiOI[0]!!.containsKey("outlierCount") && nonBusiOI[0]!!["outlierCount"] != null) {
                            mBinding!!.txtSeverityCount1.text =
                                nonBusiOI[0]!!["outlierCount"].toString()
                        }

                        if (nonBusiOI[0]!!.containsKey("featureName") && nonBusiOI[0]!!["featureName"] != null) {
                            mBinding!!.txtSeverity1.text = nonBusiOI[0]!!["featureName"].toString()
                        }

                    }
                    if (Utils.hasIndex(1, nonBusiOI!!)) {
                        if (nonBusiOI[1]!!.containsKey("outlierCount") && nonBusiOI[1]!!["outlierCount"] != null) {
                            mBinding!!.txtSeverityCount2.text =
                                nonBusiOI[1]!!["outlierCount"].toString()
                        }

                        if (nonBusiOI[1]!!.containsKey("featureName") && nonBusiOI[1]!!["featureName"] != null) {
                            mBinding!!.txtSeverity2.text = nonBusiOI[1]!!["featureName"].toString()
                        }

                    }
                    if (Utils.hasIndex(2, nonBusiOI!!)) {
                        if (nonBusiOI[2]!!.containsKey("outlierCount") && nonBusiOI[2]!!["outlierCount"] != null) {
                            mBinding!!.txtSeverityCount3.text =
                                nonBusiOI[2]!!!!["outlierCount"].toString()
                        }

                        if (nonBusiOI[2]!!.containsKey("featureName") && nonBusiOI[2]!!["featureName"] != null) {
                            mBinding!!.txtSeverity3.text = nonBusiOI[2]!!["featureName"].toString()
                        }

                    }
                    if (Utils.hasIndex(3, nonBusiOI!!)) {
                        if (nonBusiOI[3]!!.containsKey("outlierCount") && nonBusiOI[3]!!["outlierCount"] != null) {
                            mBinding!!.txtSeverityCount4.text =
                                nonBusiOI[3]!!["outlierCount"].toString()
                        }

                        if (nonBusiOI[3]!!.containsKey("featureName") && nonBusiOI[3]!!["featureName"] != null) {
                            mBinding!!.txtSeverity4.text = nonBusiOI[3]!!["featureName"].toString()
                        }

                    }


                }
            }

        }


        mBinding!!.card1Oi.setOnClickListener {
            if (selection == 0) {

                if (mBinding!!.txtSeverityCount1.text != "0") {
                    mBinding!!.cnstrntLOiSeverityData.visibility = View.VISIBLE
                    mBinding!!.recyclerViewOi.visibility = View.VISIBLE
                    if (msg!!.containsKey("busiS1") && msg!!["busiS1"] != null) {
                        mBinding!!.txtSeverityHeader.text =
                            activity!!.resources.getString(R.string.open_severity_incident)
                                .replace("%", "1")
                        mBinding!!.txtSeverityCount.text = mBinding!!.txtSeverityCount1.text
                        severityList = msg!!["busiS1"] as ArrayList<HashMap<String, Any>>

                        mBinding!!.recyclerViewOi.apply {
                            layoutManager =
                                LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                            adapter = NgoOpenIncidentItemAdapter(severityList!!)
                        }

                    }
                } else {
                    mBinding!!.cnstrntLOiSeverityData.visibility = View.GONE
                    mBinding!!.recyclerViewOi.visibility = View.GONE
                }

            } else {
                if (mBinding!!.txtSeverityCount1.text != "0") {
                    mBinding!!.cnstrntLOiSeverityData.visibility = View.VISIBLE
                    mBinding!!.recyclerViewOi.visibility = View.VISIBLE
                    mBinding!!.txtSeverityHeader.text =
                        activity!!.resources.getString(R.string.open_severity_incident)
                            .replace("%", "1")
                    mBinding!!.txtSeverityCount.text = mBinding!!.txtSeverityCount1.text
                    if (msg!!.containsKey("nonBusiS1") && msg!!["nonBusiS1"] != null) {
                        severityList = msg!!["nonBusiS1"] as ArrayList<HashMap<String, Any>>
                        mBinding!!.txtSeverityHeader.text =
                            activity!!.resources.getString(R.string.open_severity_incident)
                                .replace("%", "2")
                        mBinding!!.txtSeverityCount.text = mBinding!!.txtSeverityCount2.text
                        mBinding!!.recyclerViewOi.apply {
                            layoutManager =
                                LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                            adapter = NgoOpenIncidentItemAdapter(severityList!!)
                        }

                    }
                } else {
                    mBinding!!.cnstrntLOiSeverityData.visibility = View.GONE
                    mBinding!!.recyclerViewOi.visibility = View.GONE
                }

            }
        }

        mBinding!!.card2Oi.setOnClickListener {
            if (selection == 0) {
                if (mBinding!!.txtSeverityCount2.text != "0") {
                    mBinding!!.cnstrntLOiSeverityData.visibility = View.VISIBLE
                    mBinding!!.recyclerViewOi.visibility = View.VISIBLE
                    mBinding!!.txtSeverityHeader.text =
                        activity!!.resources.getString(R.string.open_severity_incident)
                            .replace("%", "2")
                    mBinding!!.txtSeverityCount.text = mBinding!!.txtSeverityCount2.text
                    if (msg!!.containsKey("busiS2") && msg!!["busiS2"] != null) {
                        severityList = msg!!["busiS2"] as ArrayList<HashMap<String, Any>>

                        mBinding!!.recyclerViewOi.apply {
                            layoutManager =
                                LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                            adapter = NgoOpenIncidentItemAdapter(severityList!!)
                        }

                    }
                } else {
                    mBinding!!.cnstrntLOiSeverityData.visibility = View.GONE
                    mBinding!!.recyclerViewOi.visibility = View.GONE

                }

            } else {
                if (mBinding!!.txtSeverityCount2.text != "0") {
                    mBinding!!.cnstrntLOiSeverityData.visibility = View.VISIBLE
                    mBinding!!.recyclerViewOi.visibility = View.VISIBLE
                    mBinding!!.txtSeverityHeader.text =
                        activity!!.resources.getString(R.string.open_severity_incident)
                            .replace("%", "2")
                    mBinding!!.txtSeverityCount.text = mBinding!!.txtSeverityCount2.text
                    if (msg!!.containsKey("nonBusiS2") && msg!!["nonBusiS2"] != null) {
                        severityList = msg!!["nonBusiS2"] as ArrayList<HashMap<String, Any>>

                        mBinding!!.recyclerViewOi.apply {
                            layoutManager =
                                LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                            adapter = NgoOpenIncidentItemAdapter(severityList!!)
                        }

                    }
                } else {
                    mBinding!!.cnstrntLOiSeverityData.visibility = View.GONE
                    mBinding!!.recyclerViewOi.visibility = View.GONE
                    *//*if(severityList!=null) {
                        severityList!!.clear()

                        mBinding!!.recyclerViewOi.apply {
                            layoutManager =
                                LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                            adapter = NgoOpenIncidentItemAdapter(severityList!!)
                        }
                    }*//*

                }

            }
        }

        mBinding!!.card3Oi.setOnClickListener {
            if (mBinding!!.txtSeverityCount3.text != "0") {
                mBinding!!.cnstrntLOiSeverityData.visibility = View.VISIBLE
                mBinding!!.recyclerViewOi.visibility = View.VISIBLE
                mBinding!!.txtSeverityHeader.text =
                    activity!!.resources.getString(R.string.open_severity_incident)
                        .replace("%", "3")
                mBinding!!.txtSeverityCount.text = mBinding!!.txtSeverityCount3.text
                if (msg!!.containsKey("nonBusiS3") && msg!!["nonBusiS3"] != null) {
                    severityList = msg!!["nonBusiS3"] as ArrayList<HashMap<String, Any>>

                    mBinding!!.recyclerViewOi.apply {
                        layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                        adapter = NgoOpenIncidentItemAdapter(severityList!!)
                    }

                }
            } else {
                mBinding!!.cnstrntLOiSeverityData.visibility = View.GONE
                mBinding!!.recyclerViewOi.visibility = View.GONE

            }

        }

        mBinding!!.card4Oi.setOnClickListener {
            if (mBinding!!.txtSeverityCount4.text != "0") {
                mBinding!!.cnstrntLOiSeverityData.visibility = View.VISIBLE
                mBinding!!.recyclerViewOi.visibility = View.VISIBLE
                mBinding!!.txtSeverityHeader.text =
                    activity!!.resources.getString(R.string.open_severity_incident)
                        .replace("%", "4")
                mBinding!!.txtSeverityCount.text = mBinding!!.txtSeverityCount4.text
                if (msg!!.containsKey("nonBusiS4") && msg!!["nonBusiS4"] != null) {
                    severityList = msg!!["nonBusiS4"] as ArrayList<HashMap<String, Any>>

                    mBinding!!.recyclerViewOi.apply {
                        layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                        adapter = NgoOpenIncidentItemAdapter(severityList!!)
                    }

                }
            } else {
                mBinding!!.cnstrntLOiSeverityData.visibility = View.GONE
                mBinding!!.cnstrntLOiSeverityData.visibility = View.GONE
                mBinding!!.recyclerViewOi.visibility = View.GONE

            }
        }*/


    }


    fun setData(msg: HashMap<String, Any>?, title: String, key: String, count: String) {
        this.key = key
        this.title = title
        this.count = count
        this.msg = msg
    }
}
