package com.example.apinews

import org.json.JSONArray
import org.json.JSONObject

operator fun JSONArray.iterator():Iterator<JSONObject>
=(0 until length()).asSequence().map{get(it) as  JSONObject}.iterator()