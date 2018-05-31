package com.aruna.kliknelayanwakatobi.tools

import com.aruna.kliknelayanwakatobi.pojo.ModelAnggota
import com.aruna.kliknelayanwakatobi.pojo.ModelUserData2

/**
 * Created by marzellaalfamega on 12/3/17.
 *
 * zendesk info on https://wakatobi.zendesk.com
 */

const val SPLASH_DELAY = 2700L

const val REQUEST_IMAGE_CAPTURE = 1000

const val ENTITY_APP = "mobile"

const val SUPER_ADMIN = "65536"
const val ADMIN = "131072"
const val USER = "196608"
const val TPI = "262144"
const val UPI = "327680"
const val BUDIDAYA = "393216"
const val TANGKAP = "458752"

const val API_TOKEN = "API_TOKEN"

const val EXTRA_MODEL_USER = "EXTRA_MODEL_USER"
const val EXTRA_FORM_TYPE = "EXTRA_FORM_TYPE"
const val IS_REFRESH = "EXTRA_FORM_TYPE"

const val S3_BUCKET = "wakatobi"

const val WAIT = 1000L

const val COGNITO_IDENTITY_POOL = "ap-southeast-2:1c231bf7-b18a-45b1-892a-8f06cddd9130"
const val COGNITO_REGION = "ap-southeast-2"
const val AWS_FILE_URL = "https://s3-ap-southeast-1.amazonaws.com/wakatobi/fish/"

var USER_DATA: ModelUserData2? = null
var FISHERMAN_DATA: ModelAnggota? = null
//https://s3-ap-southeast-1.amazonaws.com/wakatobi/1513139106514WKP.jpg

var SERVICE_SYNC = "SERVICE_SYNC"